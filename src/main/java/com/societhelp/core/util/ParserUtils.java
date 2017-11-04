package com.societhelp.core.util;

import java.util.regex.Pattern;

public class ParserUtils {

	public static final String[] INVALID_STRINGS_IN_DES = { "MB:FUND", "INWARD", "CLG", "MB:", "IMPS", "NEFT", "IB:",
			"FUND", "TRANSFER", "from", "Ref", "to", "IB:SI" };

	public static boolean hasCharNum(String value) {

		String n = ".*[0-9].*";
		String a = ".*[A-Za-z].*";
		boolean isNum = value.matches(n);
		if (isNum)
			return true;
		return isNum && value.matches(a);
	}

	public static boolean hasSpecialChar(String value) {
		Pattern regex = Pattern.compile("[^A-Za-z0-9]");
		return regex.matcher(value).find();
	}

	public static boolean isInvalidWord(String value) {

		for (String invalString : INVALID_STRINGS_IN_DES) {
			if (value.toUpperCase().equals(invalString.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public static String getNameFromDescription(String description) {
		String tmp = description.toUpperCase().replaceAll("[=\"]", "");

		String[] tValues = tmp.split(" ");
		tmp = "";
		for (String v : tValues) {
			if (v.length() > 0 && !hasSpecialChar(v)) {
				tmp += v + " ";
			}
		}

		tValues = tmp.split(" ");
		tmp = "";
		for (String v : tValues) {
			if (v.length() > 0 && !hasCharNum(v)) {
				tmp += v + " ";
			}
		}

		tValues = tmp.split(" ");
		tmp = "";
		for (String v : tValues) {
			v = v.trim();
			if (!isInvalidWord(v)) {
				tmp += v + " ";
			}
		}

		return tmp.trim();
	}
}
