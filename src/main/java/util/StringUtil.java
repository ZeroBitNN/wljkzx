package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {
	/**
	 * 格式化字符串
	 * 
	 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
	 * 
	 * @param str
	 * @param params
	 * @return
	 */
	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}

	/**
	 * 将日期(Date)类型转换为‘xxxx年xx月’格式字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 返回‘xxxx年xx月’格式字符串
	 */
	public static String dateToYMString(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		return year + "年" + month + "月";
	}

	/**
	 * 根据日期(Date)计算本周一和周日日期
	 * 
	 * @param date
	 *            要计算的日期
	 * @return 返回"YYYY年MM月dd日至MM月dd日"格式字符串
	 */
	public static String getWeekStartEnd(Date date) {
		SimpleDateFormat simdf = new SimpleDateFormat("MM月dd日");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);

		// 计算周一日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String weekFirst = simdf.format(c.getTime());

		// 计算周日日期
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 6);
		String weekLast = simdf.format(c.getTime());

		return year + "年" + weekFirst + "至" + weekLast;
	}

}
