package zeropoint.core.io;

/**
 * IBuffer is used to show that an object holds some sort of internal string buffer. It allows access to that buffer from external code, through managed methods.
 * 
 * @author Zero Point
 */
public interface IBuffer {
	/**
	 * Overwrite the internal buffer
	 * 
	 * @param buf
	 *            - the new buffer
	 * @return The current object
	 */
	IBuffer setBuffer(CharSequence buf);
	/**
	 * Retrieve the internal buffer
	 * 
	 * @return The current internal buffer
	 */
	String getBuffer();
	/**
	 * Easily append text to the internal buffer
	 * 
	 * @param buf
	 *            - the content to be appended
	 * @return The current object
	 */
	IBuffer appendToBuffer(CharSequence buf);
	/**
	 * Easily prepend text to the internal buffer
	 * 
	 * @param buf
	 *            - the content to be prepended
	 * @return The current object
	 */
	IBuffer prependToBuffer(CharSequence buf);
	/**
	 * Return the current contents of the internal buffer
	 * 
	 * @return The internal buffer, as returned by getBuffer()
	 */
	String toString();
}
