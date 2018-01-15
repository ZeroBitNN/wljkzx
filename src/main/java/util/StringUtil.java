package util;

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

}
