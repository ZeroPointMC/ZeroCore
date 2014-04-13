package zeropoint.core.string;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zeropoint.core.exception.StringSizeOutOfBoundsException;


/**
 * StringUtil is not meant to be instantiated. It offers several static methods intended to make string handling easier.
 * 
 * @author Zero Point
 */
public class StringUtil {
	public static final String[] boolTrue = new String[] {
		"yes",
		"on",
		"true",
		"enable",
		"enabled",
		"active",
		"activate",
		"activated",
		"1"
	};
	public static final String[] boolFalse = new String[] {
		"no",
		"off",
		"false",
		"disable",
		"disabled",
		"deactivate",
		"deactivated",
		"inactive",
		"0"
	};
	public static final String REGEX_TRUE = "(?:" + join("|", boolTrue) + ")";
	public static final String REGEX_FALSE = "(?:" + join("|", boolFalse) + ")";
	public static boolean isTrue(String check) {
		return check.toLowerCase().matches(REGEX_TRUE);
	}
	public static boolean isFalse(String check) {
		return check.toLowerCase().matches(REGEX_FALSE);
	}
	public static boolean isBool(String check) {
		return isTrue(check) || isFalse(check);
	}
	public static String reverse(String original) {
		char[] o = original.toCharArray();
		StringBuffer n = new StringBuffer();
		for (int i = o.length - 1; i >= 0; i-- ) {
			n.append(o[i]);
		}
		return n.toString();
	}
	public static String reverse(char[] original) {
		return reverse(original.toString());
	}
	public static String[] split(String content, String delimiter) {
		if (delimiter.length() != 1) {
			throw new StringSizeOutOfBoundsException().setMessage("StringUtil.split(String, String) requires that argument 2 be string of length 1");
		}
		return split(content, Pattern.compile("(?:\\\\.|[^" + delimiter + "\\\\]++)+"));
	}
	public static String[] split(String content, String delimiter, int flags) {
		if (delimiter.length() != 1) {
			throw new StringSizeOutOfBoundsException().setMessage("StringUtil.split(String, String, int) requires that argument 2 be string of length 1");
		}
		return split(content, Pattern.compile("(?:\\\\.|[^" + delimiter + "\\\\]++)+", flags));
	}
	protected static String[] split(String content, Pattern regex) {
		if (content == null || content.isEmpty()) {
			return null;
		}
		else if (regex == null) {
			return new String[] {
				content
			};
		}
		List<String> matchList = new ArrayList<String>();
		Matcher matcher = regex.matcher(content);
		while (matcher.find()) {
			matchList.add(matcher.group());
		}
		return matchList.toArray(new String[] {});
	}
	public static String join(String glue, String... parts) {
		int l = parts.length;
		if (l == 0) {
			return "";
		}
		else if (l == 1) {
			return parts[0];
		}
		StringBuilder b = new StringBuilder();
		b.append(parts[0]);
		for (int i = 1; i < l; i++ ) {
			b.append(glue).append(parts[i]);
		}
		return b.toString();
	}
	public static String join(String glue, List<String> parts) {
		return join(glue, parts.toArray(new String[] {}));
	}
}
