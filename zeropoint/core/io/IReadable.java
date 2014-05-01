package zeropoint.core.io;

/**
 * Indicates that an object can be read from
 * 
 * @author Zero Point
 * 
 * @param <T>
 *            the type of content that can be read
 */
public interface IReadable<T> {
	/**
	 * Read from the object
	 * 
	 * @return one element from the object
	 */
	public T read();
}
