package service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MenuDaoI;
import dao.OrderCategoryDaoI;
import dao.OrderDaoI;
import dao.OrderTypeDaoI;
import dao.PerfParamDaoI;
import dao.ResourcesDaoI;
import dao.RolesDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TOrderCategory;
import model.TOrderType;
import model.TPerfParam;
import model.TResources;
import model.TRoles;
import model.Tmenu;
import service.RepairServiceI;
import util.Encrypt;

@Service(value = "repairService")
public class RepairServiceImpl implements RepairServiceI {
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private static final String FILEPATH = "initDataBase.xml";

	private MenuDaoI menuDao;
	private UserDaoI userDao;
	private OrderDaoI orderDao;
	private OrderCategoryDaoI orderCategoryDao;
	private OrderTypeDaoI orderTypeDao;
	private RolesDaoI rolesDao;
	private ResourcesDaoI resourcesDao;
	private PerfParamDaoI perfParamDao;

	public PerfParamDaoI getPerfParamDao() {
		return perfParamDao;
	}

	@Autowired
	public void setPerfParamDao(PerfParamDaoI perfParamDao) {
		this.perfParamDao = perfParamDao;
	}

	public ResourcesDaoI getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(ResourcesDaoI resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	public RolesDaoI getRolesDao() {
		return rolesDao;
	}

	@Autowired
	public void setRolesDao(RolesDaoI rolesDao) {
		this.rolesDao = rolesDao;
	}

	public OrderTypeDaoI getOrderTypeDao() {
		return orderTypeDao;
	}

	@Autowired
	public void setOrderTypeDao(OrderTypeDaoI orderTypeDao) {
		this.orderTypeDao = orderTypeDao;
	}

	public OrderCategoryDaoI getOrderCategoryDao() {
		return orderCategoryDao;
	}

	@Autowired
	public void setOrderCategoryDao(OrderCategoryDaoI orderCategoryDao) {
		this.orderCategoryDao = orderCategoryDao;
	}

	public OrderDaoI getOrderDao() {
		return orderDao;
	}

	@Autowired
	public void setOrderDao(OrderDaoI orderDao) {
		this.orderDao = orderDao;
	}

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public MenuDaoI getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(MenuDaoI menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public void init() {
		logger.info("==========开始初始化数据============");
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));

			initResources(document);
			initRoles(document);
			initMenu(document);
			initCategory(document);
			initOrderType(document);
			//repairUser(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void initResources(Document document) {
		logger.info("初始化资源......");
		List<Node> childNodes = document.selectNodes("//resources/resource");
		if (childNodes.size() > 0) {
			for (Node node : childNodes) {
				TResources t = new TResources();
				t.setId(node.valueOf("@id"));
				t.setName(node.valueOf("@name"));
				t.setUrl(node.valueOf("@url"));
				t.setDescription(node.valueOf("@description"));
				t.setTResources(new TResources(node.valueOf("@pid")));
				resourcesDao.saveOrUpdate(t);
			}
		}
	}

	@Override
	public void repairAdmin() {
		logger.info("==========开始修复用户============");
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
			initRoles(document);
			repairUser(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void initRoles(Document document) {
		logger.info("初始化角色......");
		List<Node> childNodes = document.selectNodes("//roles/role");
		if (childNodes.size() > 0) {
			for (Node node : childNodes) {
				TRoles t = new TRoles();
				t.setId(node.valueOf("@id"));
				t.setName(node.valueOf("@name"));
				t.setResourcesIds(node.valueOf("@resources_ids"));
				try {
					rolesDao.saveOrUpdate(t);
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
			}
		}
	}

	private void initOrderType(Document document) {
		logger.info("初始化管控类型......");
		List<Node> childNodes = document.selectNodes("//ordertypes/ordertype");
		if (childNodes.size() > 0) {
			for (Node node : childNodes) {
				TOrderType t = new TOrderType();
				t.setId(node.valueOf("@id"));
				t.setName(node.valueOf("@name"));
				orderTypeDao.saveOrUpdate(t);
			}
		}

	}

	private void initCategory(Document document) {
		logger.info("初始化专业类别......");
		List<Node> childNodes = document.selectNodes("//categorys/category");
		if (childNodes.size() > 0) {
			for (Node node : childNodes) {
				TOrderCategory t = new TOrderCategory();
				t.setId(node.valueOf("@id"));
				t.setName(node.valueOf("@name"));
				orderCategoryDao.saveOrUpdate(t);
			}
		}

	}

	private void repairUser(Document document) {
		logger.info("修复超级管理员密码......");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("username", "admin");
		TAccount t = userDao.get("from TAccount t where t.username = :username and t.id != '0'", m);
		if (t != null) {
			t.setUsername(UUID.randomUUID().toString());
		}

		List<Node> childNodes = document.selectNodes("//users/user");
		if (childNodes.size() > 0) {
			for (Node node : childNodes) {
				// logger.info(node.valueOf("@id"));
				// logger.info(node.valueOf("@username"));
				// logger.info(node.valueOf("@pwd"));

				TAccount admin = new TAccount();
				admin.setId(node.valueOf("@id"));
				admin.setUsername(node.valueOf("@username"));
				admin.setPwd(Encrypt.e(node.valueOf("@pwd")));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				admin.setCreatetime(df.format(new Date()));
				admin.setModifytime(df.format(new Date()));
				admin.setTRoles(new TRoles(node.valueOf("@roles_id")));
				userDao.saveOrUpdate(admin);
			}

		}

	}

	private void initMenu(Document document) {
		logger.info("初始化菜单......");
		List<Node> childNodes = document.selectNodes("//menus/menu");
		Map<String, Object> menus = new HashMap<String, Object>();

		for (Node node : childNodes) {
			Tmenu m = new Tmenu();
			m.setId(node.valueOf("@id"));
			m.setText(node.valueOf("@text"));
			m.setIconCls(node.valueOf("@iconcls"));
			m.setUrl(node.valueOf("@url"));
			if (menus != null && menus.get(node.valueOf("@pid")) != null) {
				m.setTmenu((Tmenu) menus.get(node.valueOf("@pid")));
			}
			menus.put(node.valueOf("@id"), m);
		}

		for (Map.Entry<String, Object> entry : menus.entrySet()) {
			menuDao.saveOrUpdate((Tmenu) entry.getValue());
		}

	}

	@Override
	public void initMenu() {
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
			initMenu(document);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initRes() {
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
			initResources(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void repairUser() {
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
			repairUser(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initPerfParam() {
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
			logger.info("初始化绩效参数......");
			List<Node> childNodes = document.selectNodes("//perfParams/perfParam");
			if (childNodes.size() > 0) {
				for (Node node : childNodes) {
					TPerfParam t = new TPerfParam();
					t.setId(node.valueOf("@id"));
					t.setName(node.valueOf("@itemname"));
					t.setPercent(new BigDecimal(node.valueOf("@percent")));
					t.setType(node.valueOf("@type"));
					perfParamDao.saveOrUpdate(t);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
