package jp.reu.util;


public class Str
{
	public static String makeStrNTimes(int n, String str) {
		StringBuffer buff = new StringBuffer();

		for (int i=0; i<n; i++) {
			buff.append(str);
		}

		return buff.toString();
	}
}
