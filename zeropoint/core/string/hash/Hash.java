package zeropoint.core.string.hash;

/**
 * Simple class for hashing utilities to extend
 * 
 * @author Zero Point
 */
public abstract class Hash {
	/**
	 * Convert a <code>byte</code> array to a hex <code>String</code>
	 * 
	 * @param bytes
	 *            - the <code>byte</code> array to convert to hex
	 * @return the <code>String</code> representing the hex encoding of the given bytes
	 */
	protected static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) {
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
}
