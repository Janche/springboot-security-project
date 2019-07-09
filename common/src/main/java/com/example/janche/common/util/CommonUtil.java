package com.example.janche.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonUtil {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String firstToLow(String str) {
		String s = "";
		s = str.substring(0, 1).toLowerCase() + str.substring(1);

		return s;
	}

	public static String firstToUpper(String str) {
		String s = "";
		s = str.substring(0, 1).toUpperCase() + str.substring(1);

		return s;
	}

	public static List<String> str2list(String s) throws IOException {
		List<String> list = new ArrayList<>();
		if ((s != null) && (!s.equals(""))) {
			StringReader fr = new StringReader(s);
			BufferedReader br = new BufferedReader(fr);
			String aline = "";
			while ((aline = br.readLine()) != null) {
				list.add(aline);
			}
		}

		return list;
	}

	public static Date formatDate(String s) {
		Date d = null;
		try {
			d = dateFormat.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public static Date formatDate(String s, String format) {
		Date d = null;
		try {
			SimpleDateFormat dFormat = new SimpleDateFormat(format);
			d = dFormat.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public static String formatDate(Date date, String format) {
		String s = null;
		try {
			SimpleDateFormat dFormat = new SimpleDateFormat(format);
			s = dFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	public static String formatTime(String format, Object v) {
		if (v == null)
			return null;
		if (v.equals(""))
			return "";
		SimpleDateFormat df = new SimpleDateFormat(format);

		return df.format(v);
	}

	public static String formatLongDate(Object v) {
		if ((v == null) || (v.equals("")))
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return df.format(v);
	}

	public static String formatShortDate(Object v) {
		if (v == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		return df.format(v);
	}

	// UTF-8解码
	public static String decode(String s) {
		String ret = s;
		try {
			ret = URLDecoder.decode(s.trim(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	// UTF-8编码
	public static String encode(String s) {
		String ret = s;
		try {
			ret = URLEncoder.encode(s.trim(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static String convert(String str, String coding) {
		String newStr = "";
		if (str != null)
			try {
				newStr = new String(str.getBytes("ISO-8859-1"), coding);
			} catch (Exception e) {
				return newStr;
			}

		return newStr;
	}

	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		return v;
	}

	public static float null2Float(Object s) {
		float v = 0.0F;
		if (s != null)
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception localException) {
			}

		return v;
	}

	public static double null2Double(Object s) {
		double v = 0.0D;
		if (s != null)
			try {
				v = Double.parseDouble(null2String(s));
			} catch (Exception e) {
				e.printStackTrace();
			}

		return v;
	}

	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null)
			try {
				v = Boolean.parseBoolean(s.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		return v;
	}

	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	public static Long null2Long(Object s) {
		Long v = Long.valueOf(-1L);
		if (s != null)
			try {
				v = Long.valueOf(Long.parseLong(s.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		return v;
	}

	public static double mul(Object a, Object b) {
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		double ret = e.multiply(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");

		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static boolean stringIsNull(String str){
		return str==null||"".equals(str);
	}

	// 前台到后台时区转换，北京时区-8小时
	public static Date getLocalDate(Date date) {
		long time = date.getTime();
		return new Date(time - 1000 * 60 * 60 * 8);
	}

	// 后台到前台时区转换，北京时区+8小时
	public static Date returnLocalDate(Date date) {
		long time = date.getTime();
		return new Date(time + 1000 * 60 * 60 * 8);
	}
}
