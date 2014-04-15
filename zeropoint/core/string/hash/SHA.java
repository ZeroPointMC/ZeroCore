package zeropoint.core.string.hash;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * SHA is simply a convenience class for calculating SHA hashes.
 * 
 * @see MD
 * @author Zero Point
 */
public class SHA extends Hash {
	/**
	 * Calculates the SHA1 hash of the given <code>String</code>
	 * 
	 * @param data
	 *            - the <code>String</code> to calculate the hash of
	 * @return a <code>String</code> containing the SHA1 hash of the given <code>String</code>
	 * @throws NoSuchAlgorithmException
	 *             if SHA1 hashes cannot be calculated
	 */
	public static String hex1(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	/**
	 * Calculates the SHA256 hash of the given <code>String</code>
	 * 
	 * @param data
	 *            - the <code>String</code> to calculate the hash of
	 * @return a <code>String</code> containing the SHA256 hash of the given <code>String</code>
	 * @throws NoSuchAlgorithmException
	 *             if SHA256 hashes cannot be calculated
	 */
	public static String hex256(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	/**
	 * Calculates the SHA384 hash of the given <code>String</code>
	 * 
	 * @param data
	 *            - the <code>String</code> to calculate the hash of
	 * @return a <code>String</code> containing the SHA384 hash of the given <code>String</code>
	 * @throws NoSuchAlgorithmException
	 *             if SHA384 hashes cannot be calculated
	 */
	public static String hex384(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	/**
	 * Calculates the SHA512 hash of the given <code>String</code>
	 * 
	 * @param data
	 *            - the <code>String</code> to calculate the hash of
	 * @return a <code>String</code> containing the SHA512 hash of the given <code>String</code>
	 * @throws NoSuchAlgorithmException
	 *             if SHA512 hashes cannot be calculated
	 */
	public static String hex512(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.reset();
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
}
