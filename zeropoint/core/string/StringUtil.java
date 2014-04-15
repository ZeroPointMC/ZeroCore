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
	/**
	 * All strings that are considered to represent boolean <code>true</code>
	 */
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
	/**
	 * All strings that are considered to represent boolean <code>false</code>
	 */
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
	/**
	 * Regex testing whether a string represents boolean <code>true</code>
	 */
	public static final String REGEX_TRUE = "(?:" + join("|", boolTrue) + ")";
	/**
	 * Regex testing whether a string represents boolean <code>false</code>
	 */
	public static final String REGEX_FALSE = "(?:" + join("|", boolFalse) + ")";
	/**
	 * Checks to see if the given string represents boolean <code>true</code>, according to the regex defined in REGEX_TRUE
	 * 
	 * @param check
	 *            - the string to be checked
	 * @return <code>true</code> if the given string represents boolean <code>true</code>, <code>false</code> otherwise
	 */
	public static boolean isTrue(String check) {
		return check.toLowerCase().matches(REGEX_TRUE);
	}
	/**
	 * Checks to see if the given string represents boolean <code>false</code>, according to the regex defined in REGEX_FALSE
	 * 
	 * @param check
	 *            - the string to be checked
	 * @return <code>true</code> if the given string represents boolean <code>false</code>, <code>false</code> otherwise
	 */
	public static boolean isFalse(String check) {
		return check.toLowerCase().matches(REGEX_FALSE);
	}
	/**
	 * Checks to see if the given string represents a boolean value, according to the regexes defined in REGEX_TRUE and REGEX_FALSE
	 * 
	 * @param check
	 *            - the string to be checked
	 * @return <code>true</code> if the given string represents a boolean value, <code>false</code> otherwise
	 */
	public static boolean isBool(String check) {
		return isTrue(check) || isFalse(check);
	}
	/**
	 * Reverses a string; calling on a reversed string returns the original.
	 * 
	 * @param original
	 *            - the string to be reversed
	 * @return the given string, with its characters reversed
	 */
	public static String reverse(String original) {
		char[] o = original.toCharArray();
		StringBuffer n = new StringBuffer();
		for (int i = o.length - 1; i >= 0; i-- ) {
			n.append(o[i]);
		}
		return n.toString();
	}
	/**
	 * Reverses a string contained in a <code>char</code> array
	 * 
	 * @param original
	 *            - the <code>char</code> array to be reversed
	 * @return the string represented by the given array, with its characters reversed
	 */
	public static String reverse(char[] original) {
		return reverse(original.toString());
	}
	/**
	 * Splits a <code>String</code> into an array at the given delimiter, understands escaped delimiters
	 * 
	 * @param content
	 *            - the <code>String</code> to be split
	 * @param delimiter
	 *            - the single character to use as the delimiter
	 * @return a <code>String</code> array
	 */
	public static String[] split(String content, String delimiter) {
		if (delimiter.length() != 1) {
			throw new StringSizeOutOfBoundsException().setMessage("StringUtil.split(String, String) requires that argument 2 be string of length 1");
		}
		return split(content, Pattern.compile("(?:\\\\.|[^" + delimiter + "\\\\]++)+"));
	}
	/**
	 * Splits a <code>String</code> into an array at the given delimiter, understands escaped delimiters. Allows regex flags to be used.
	 * 
	 * @param content
	 *            - the <code>String</code> to be split
	 * @param delimiter
	 *            - the single character to use as the delimiter
	 * @param flags
	 *            - the regex flags you want to use
	 * @return a <code>String</code> array
	 */
	public static String[] split(String content, String delimiter, int flags) {
		if (delimiter.length() != 1) {
			throw new StringSizeOutOfBoundsException().setMessage("StringUtil.split(String, String, int) requires that argument 2 be string of length 1");
		}
		return split(content, Pattern.compile("(?:\\\\.|[^" + delimiter + "\\\\]++)+", flags));
	}
	/**
	 * Splits a <code>String</code> into an array using the given <code>Pattern</code>
	 * 
	 * @param content
	 *            - the <code>String</code> to split
	 * @param regex
	 *            - the <code>Pattern</code> to use for splitting
	 * @return a <code>String</code> array
	 */
	protected static String[] split(String content, Pattern regex) {
		if ((content == null) || content.isEmpty()) {
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
	/**
	 * Combines an array of objects using the given object to separate them; uses <code>String.valueOf(...)</code> on all objects
	 * 
	 * @param glue
	 *            - the object to use as a separator
	 * @param parts
	 *            - the objects to combine
	 * @return a <code>String</code> object
	 */
	public static String join(Object glue, Object... parts) {
		int l = parts.length;
		if (l == 0) {
			return "";
		}
		else if (l == 1) {
			return String.valueOf(parts[0]);
		}
		StringBuilder b = new StringBuilder();
		b.append(parts[0]);
		for (int i = 1; i < l; i++ ) {
			b.append(glue).append(parts[i]);
		}
		return b.toString();
	}
	/**
	 * Combines an array of objects using the given object to separate them; uses <code>String.valueOf(...)</code> on all objects
	 * 
	 * @param glue
	 *            - the object to use as a separator
	 * @param parts
	 *            - the objects to combine
	 * @return a <code>String</code> object
	 */
	public static String join(Object glue, List<Object> parts) {
		return join(glue, parts.toArray());
	}
}
