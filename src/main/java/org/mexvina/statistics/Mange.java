package org.mexvina.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Mange {
	private static ArrayList<String> allowToken = new ArrayList<String>();//允许访问的cookie
	private static HashMap<String, Date> disToken = new HashMap<>();//禁止访问的cookie

	private static HashMap<String, Date> che = new HashMap<>();//签到二维码的值
	
	public static void addToken(String token) {
		allowToken.add(token);
	}
	
	public static boolean checkToken(String token) {
		if(allowToken.contains(token)) {
			return true;
		}
		return false;
	}
	
	public static void delToken(String token) {
		allowToken.remove(token);
	}
	
	public static void addDisToken(String token) {
		Date date = new Date();
		disToken.put(token,date);
	}
	
	public static boolean checkDisToken(String token) {//检查是否是黑名单
		if(disToken.get(token) != null) {
			Date datesave = disToken.get(token);
			Date datenow = new Date();
			if(datenow.getTime()-datesave.getTime()>=10000) {//如果大于10秒
				delDisToken(token);
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	
	public static void delDisToken(String token) {
		disToken.remove(token);
	}
	
	public static void addChe(String cheCode) {
		Date date = new Date();
		che.put(cheCode,date);
	}
	
	public static boolean checkChe(String cheCode) {//检查是否是黑名单
		if(che.get(cheCode) != null) {
			Date datesave = che.get(cheCode);
			Date datenow = new Date();
			if(datenow.getTime()-datesave.getTime()>=10000) {//如果大于10秒
				delChe(cheCode);
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	
	public static void delChe(String cheCode) {
		che.remove(cheCode);
	}
	
	public static int numChe() {
		return che.size();
	}
	
	public static String getLastChe() {
		int cheKeyLenght = che.keySet().size();
		String str = che.keySet().toArray(new String[cheKeyLenght])[cheKeyLenght-1];
		return str;
	}
	
	public static void clearChe() {
		if(!che.isEmpty())
			che.clear();
	}
}
