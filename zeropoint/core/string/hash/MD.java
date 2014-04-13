package zeropoint.core.string.hash;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Like {@link SHA}, this is a convenience class for calculating MD* hashes.
 * 
 * @author Zero Point
 */
public class MD {
	protected static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) {
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
	public static String hex2(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD2");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	public static String hex5(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
}
