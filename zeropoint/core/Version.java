package zeropoint.core;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zeropoint.core.exception.InvalidVersionException;


/**
 * The Version class implements comparable <a href="http://semver.org/">Semantic Versioning</a>. Pre-release identifiers are case-insensitive, as defined on the github issues page.
 * 
 * @author Zero Point
 */
public class Version implements Comparable<Version> {
	/**
	 * The major version of the Semantic Version string this object represents
	 */
	public final int major;
	/**
	 * The minor version of the Semantic Version string this object represents
	 */
	public final int minor;
	/**
	 * The patch version of the Semantic Version string this object represents
	 */
	public final int patch;
	/**
	 * The pre-release string for Semantic Version string this object represents
	 */
	public final String pre;
	/**
	 * The build metadata string for Semantic Version string this object represents
	 */
	public final String build;
	/**
	 * Construct a new Version object from the given string
	 * 
	 * @param ver
	 *            - the Semantic Version string this object should represent
	 */
	public Version(String ver) {
		Pattern pattern = Pattern.compile("(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<patch>\\d+)(?<pre>(?:-[a-z0-9-]+(?:\\.[a-z0-9-]+)?)?)(?<meta>(?:\\+[a-z0-9-]+(?:\\.[a-z0-9-]+)?)?)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(ver);
		if ( !matcher.matches()) {
			throw new InvalidVersionException().setMessage(ver + " is not a valid version string!");
		}
		this.major = Integer.parseInt(matcher.group("major"));
		this.minor = Integer.parseInt(matcher.group("minor"));
		this.patch = Integer.parseInt(matcher.group("patch"));
		String p = matcher.group("pre");
		String b = matcher.group("meta");
		if ( !p.isEmpty()) {
			this.pre = p.substring(1);
		}
		else {
			this.pre = "";
		}
		if ( !b.isEmpty()) {
			this.build = b.substring(1);
		}
		else {
			this.build = "";
		}
	}
	/**
	 * Construct a new Version object with the given major, minor, and patch versions
	 * 
	 * @param major
	 *            - the major version to use
	 * @param minor
	 *            - the minor version to use
	 * @param patch
	 *            - the patch version to use
	 */
	public Version(int major, int minor, int patch) {
		this(major + "." + minor + "." + patch);
	}
	/**
	 * Construct a new Version object with the given major, minor, and patch versions
	 * 
	 * @param major
	 *            - the major version to use
	 * @param minor
	 *            - the minor version to use
	 * @param patch
	 *            - the patch version to use
	 * @param pre
	 *            - the pre-release string to use
	 */
	public Version(int major, int minor, int patch, String pre) {
		this(major + "." + minor + "." + patch + "-" + pre);
	}
	/**
	 * Construct a new Version object with the given major, minor, and patch versions
	 * 
	 * @param major
	 *            - the major version to use
	 * @param minor
	 *            - the minor version to use
	 * @param patch
	 *            - the patch version to use
	 * @param pre
	 *            - the pre-release string to use
	 * @param meta
	 *            - the build meta string to use
	 */
	public Version(int major, int minor, int patch, String pre, String meta) {
		this(major + "." + minor + "." + patch + ((pre != null) && !pre.isEmpty() ? "-" + pre : "") + ((pre != null) && !pre.isEmpty() ? "+" + meta : ""));
	}
	public int compareTo(Version that) {
		if (this.major > that.major) {
			return 1;
		}
		if (this.major < that.major) {
			return -1;
		}
		if (this.minor > that.minor) {
			return 1;
		}
		if (this.minor < that.minor) {
			return -1;
		}
		if (this.patch > that.patch) {
			return 1;
		}
		if (this.patch < that.patch) {
			return -1;
		}
		if (this.pre.isEmpty() && !that.pre.isEmpty()) {
			return 1;
		}
		if ( !this.pre.isEmpty() && that.pre.isEmpty()) {
			return -1;
		}
		String[] mpre = this.pre.toLowerCase().split("\\.");
		String[] ypre = that.pre.toLowerCase().split("\\.");
		Pattern numeric = Pattern.compile("(?:0|[1-9](?:[0-9]*))");
		for (int mi = 0; mi < mpre.length; mi++ ) {
			try {
				if (ypre[mi] == null) {
					return 1;
				}
			}
			catch (ArrayIndexOutOfBoundsException e) {
				return 1;
			}
			if (numeric.matcher(mpre[mi]).matches() && numeric.matcher(ypre[mi]).matches()) {
				// Numeric comparison
				if (Integer.parseInt(mpre[mi]) > Integer.parseInt(ypre[mi])) {
					return 1;
				}
				if (Integer.parseInt(mpre[mi]) < Integer.parseInt(ypre[mi])) {
					return -1;
				}
			}
			else {
				// Alphanumeric comparison
				char[] mchr = mpre[mi].toCharArray();
				char[] ychr = ypre[mi].toCharArray();
				for (int mci = 0; mci < mchr.length; mci++ ) {
					try {
						if (ychr[mci] == -1) {
							return 1;
						}
					}
					catch (ArrayIndexOutOfBoundsException e) {
						return 1;
					}
					if (mchr[mci] > ychr[mci]) {
						return 1;
					}
					if (mchr[mci] < ychr[mci]) {
						return -1;
					}
				}
			}
		}
		return 0;
	}
	@Override
	public String toString() {
		return toString(this.major, this.minor, this.patch, this.pre, this.build);
	}
	/**
	 * Construct a Semantic Version string from the given parts
	 * 
	 * @param major
	 *            - the major version
	 * @param minor
	 *            - the minor version
	 * @param patch
	 *            - the patch version
	 * @param release
	 *            - the pre-release string (empty <code>String</code> or <code>null</code> if you don't want one)
	 * @param meta
	 *            - the build meta string (empty <code>String</code> or <code>null</code> if you don't want one)
	 * @return a <code>String</code> containing the Semantic Version string representing the given parts
	 */
	public static String toString(int major, int minor, int patch, String release, String meta) {
		StringBuffer buf = new StringBuffer();
		buf.append(major);
		buf.append(".");
		buf.append(minor);
		buf.append(".");
		buf.append(patch);
		if ((release != null) && !release.isEmpty()) {
			buf.append("-");
			buf.append(release);
		}
		if ((meta != null) && !meta.isEmpty()) {
			buf.append("+");
			buf.append(meta);
		}
		return buf.toString();
	}
}
