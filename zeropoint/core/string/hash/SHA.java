package zeropoint.core.string.hash;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * SHA is simply a convenience class for calculating SHA hashes.
 * 
 * @see MD
 * @author Zero Point
 */
public class SHA {
	protected static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) {
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
	public static String hex1(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	public static String hex256(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	public static String hex384(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	public static String hex512(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
}
