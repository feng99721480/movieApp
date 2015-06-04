/**
 * 此类中存放一些常用的方法
 */
package com.wiseweb.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Util {
	private static Bitmap bitmap = null;

	/**
	 * 根据图片的url获取图片
	 * 
	 * @param path
	 *            图片的url 地址
	 * @return Bitmap类型
	 * @throws IOException
	 */
	public static Bitmap getBitmap(final String path) throws IOException {
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;

	}

	// public static Bitmap getBitmap(final String path) throws IOException {
	// Thread t = new Thread(){
	// public void run(){
	// URL url;
	// try {
	// url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setConnectTimeout(5000);
	// conn.setRequestMethod("GET");
	// if (conn.getResponseCode() == 200) {
	// InputStream inputStream = conn.getInputStream();
	// Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
	// }
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (ProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// };
	// t.start();
	// try {
	// t.join();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return bitmap;
	// }

	/**
	 * 获得当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 格式的String类型的时间 取得时分
	 * 
	 * @param date
	 * @return hour:min
	 * @throws ParseException
	 */
	public static String getHourAndMin(String date) throws ParseException {
		date = date.replace("T", " ").replace(".000Z", "");
		Date da = new Date();
		SimpleDateFormat format;
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		da = (Date) format.parse(date);
		int hour = da.getHours();
		int min = da.getMinutes();
		if (min < 10) {
			return hour + ":" + "0" + min;
		}

		return (hour + ":" + min);
	}

	/**
	 * @desc 获得此刻时间到1970年1月1号的毫秒数 即使用时间戳参数时使用
	 * @return long
	 */
	public static long getTimeStamp() {
		Date date = new Date();
		return date.getTime();
	}

	/**
	 * 获取系统当前年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSystemYearMonthDay() {
		// 24小时制
		SimpleDateFormat dateFormat24 = new SimpleDateFormat("yyyy-MM-dd");
		// 12小时制
		// SimpleDateFormat dateFormat12 = new
		// SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dateFormat24.format(Calendar.getInstance().getTime());
	}

	/**
	 * 判断一个给定的String类型日期是不是比当前日期早
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isTimeLater(String date) {
		try {
			date = date.replace("T", " ").replace(".000Z", "");
			if (getTimeMillis(ConverToDate(date)) > getTimeStamp()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String formatDate(String date) {
		date = date.replace("T", " ").replace(".000Z", "");
		return date;

	}

	/**
	 * @desc 将String类型的日期转换为Date类型
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Date ConverToDate(String strDate) throws Exception {
		strDate = strDate.replace("T", " ").replace(".000Z", "");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(strDate);
	}

	/**
	 * 判断某个日期到1970年的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getTimeMillis(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// cal.set(Calendar.YEAR, date.getYear());
		// cal.set(Calendar.MONTH, date.getMonth());
		// cal.set(Calendar.DAY_OF_MONTH, date.getDay());
		// cal.set(Calendar.HOUR, date.getHours());
		// cal.set(Calendar.MINUTE, date.getMinutes());
		// cal.set(Calendar.SECOND, date.getSeconds());
		// cal.set(Calendar.MILLISECOND, date.getmi);
		return cal.getTimeInMillis();
	}

	/**
	 * 删除ArrayList中的重复值
	 * 
	 * @param list
	 */
	public static ArrayList<String> removeDuplicateFromArrayList(
			ArrayList<String> list) {
		HashSet<String> h = new HashSet<String>(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	/**
	 * 判断是否是合法的手机号
	 */
	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 将Bitmap转换成字节
	 */
	public static byte[] imgToByteArray(Bitmap b) {
		if (b != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			b.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();
		}
		return null;
	}
}
