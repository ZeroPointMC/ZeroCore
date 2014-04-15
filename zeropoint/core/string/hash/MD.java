package zeropoint.core.string.hash;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Like {@link SHA}, this is a convenience class for calculating MD* hashes.
 * 
 * @author Zero Point
 */
public class MD extends Hash {
	/**
	 * Calculates the MD2 hash of the given <code>String</code>
	 * 
	 * @param data
	 *            - the <code>String</code> to calculate the hash of
	 * @return a <code>String</code> containing the MD2 hash of the given <code>String</code>
	 * @throws NoSuchAlgorithmException
	 *             if MD2 hashes cannot be calculated
	 */
	public static String hex2(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD2");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	/**
	 * Calculates the MD5 hash of the given <code>String</code>
	 * 
	 * @param data
	 *            - the <code>String</code> to calculate the hash of
	 * @return a <code>String</code> containing the MD5 hash of the given <code>String</code>
	 * @throws NoSuchAlgorithmException
	 *             if MD5 hashes cannot be calculated
	 */
	public static String hex5(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
}
