package zeropoint.core.io;

/**
 * Indicates that an object can be written to
 * 
 * @author Zero Point
 * 
 * @param <T>
 *            the type of content that can be written
 */
public interface IWritable<T> {
	/**
	 * Write to the object
	 * 
	 * @param input
	 *            - the element to write to the object
	 * @return the IWritable object
	 */
	public IWritable<T> write(T input);
}
