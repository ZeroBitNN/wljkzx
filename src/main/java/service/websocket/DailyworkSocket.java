package service.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import dao.DailyworkDaoI;
import dao.DailyworkDetailsDaoI;
import dao.impl.DailyworkDaoImpl;
import dao.impl.DailyworkDetailsDaoImpl;
import model.TDailywork;
import model.TDailyworkDetails;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/dailyworkSocket")
public class DailyworkSocket extends QuartzJobBean {
	private static final Logger log = Logger.getLogger(DailyworkSocket.class);
	// 获取Bean传入的SessionFactory用以操作数据库
	private SessionFactory sessionFactory;

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<DailyworkSocket> webSocketSet = new CopyOnWriteArraySet<DailyworkSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		log.info("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("来自客户端的消息:" + message);
		for (DailyworkSocket item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				log.info(e.getMessage());
				continue;
			}
		}
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.info("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		DailyworkSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		DailyworkSocket.onlineCount--;
	}

	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
				.getResource(getSessionFactory());
		boolean existingTransaction = sessionHolder != null;
		org.hibernate.Session sessionH;
		if (existingTransaction) {
			log.error("Found thread-bound Session for HibernateInterceptor");
			sessionH = sessionHolder.getSession();
		} else {
			sessionH = openSession();
			TransactionSynchronizationManager.bindResource(getSessionFactory(), new SessionHolder(sessionH));
		}
		try {
			executeTransactional(ctx);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (existingTransaction) {
				log.debug("Not closing pre-bound Hibernate Session after TransactionalQuartzTask");
			} else {
				SessionFactoryUtils.closeSession(sessionH);
				TransactionSynchronizationManager.unbindResource(getSessionFactory());
			}
		}
	}

	public void executeTransactional(JobExecutionContext ctx) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(sdf.format(new Date())+"===检查该时间是否有未处理日管控工作===");
		for (DailyworkSocket item : webSocketSet) {
			try {
				String message = getUntreated();
				if (message != null) {
					item.sendMessage(message);
				}
			} catch (IOException e) {
				log.info(e.getMessage());
				continue;
			}
		}
	}

	private String getUntreated() {
		DailyworkDaoI dailyworkDao = new DailyworkDaoImpl(sessionFactory);
		DailyworkDetailsDaoI dailyworkDetailsDao = new DailyworkDetailsDaoImpl(sessionFactory);
		StringBuffer message = new StringBuffer();
		message.append("<b>当前时间以下日管控工作尚未处理：</b><br>");

		// 获取当前时间的 时:分
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String nowTime = sdf.format(new Date());
		// log.info("当前---时：分---" + nowTime);

		// 获取当前时间记录未处理的记录
		Map<String, Object> params1 = new HashMap<String, Object>();
		String hql = "from TDailywork t where t.timepoint=:timepoint";
		params1.put("timepoint", nowTime);
		List<TDailywork> tdList = dailyworkDao.find(hql, params1);
		if (tdList != null && tdList.size() > 0) {
			Map<String, Object> params2 = new HashMap<String, Object>();
			for (TDailywork td : tdList) {
				hql = "from TDailyworkDetails t where t.dailydate=:dailydate and t.TDailywork.id=:id and t.status=:status";
				params2.put("dailydate", new Date());
				params2.put("id", td.getId());
				params2.put("status", "未处理");
				List<TDailyworkDetails> tddList = dailyworkDetailsDao.find(hql, params2);
				if (tddList != null && tddList.size() > 0) {
					message.append("<b>" + td.getTimepoint() + "---" + td.getContent() + "</b><br>");
				} else {
					return null;
				}
			}
		} else {
			return null;
		}

		return message.toString();
	}

	protected org.hibernate.Session openSession() throws DataAccessResourceFailureException {
		try {
			org.hibernate.Session sessionH = getSessionFactory().openSession();
			sessionH.setFlushMode(FlushMode.MANUAL);
			return sessionH;
		} catch (HibernateException ex) {
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
