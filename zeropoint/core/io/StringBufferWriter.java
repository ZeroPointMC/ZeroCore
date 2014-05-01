package zeropoint.core.io;


import java.io.StringWriter;
import java.io.Writer;


/**
 * A StringBufferWriter is a special {@link StringWriter} that allows ongoing access to its internal buffer.
 * 
 * @see StringBufferInputStream
 * @see StringBufferOutputStream
 * @see StringBufferReader
 * @author Zero Point
 */
public class StringBufferWriter extends Writer implements Cloneable, IBuffer {
	private StringBuffer buffer = new StringBuffer();
	@Override
	public StringBufferWriter clone() {
		StringBufferWriter clone = new StringBufferWriter(this.lock);
		clone.buffer = new StringBuffer(this.buffer.toString());
		return clone;
	}
	/**
	 * Create a new StringBufferWriter that locks on the given object
	 */
	public StringBufferWriter() {
		super();
	}
	/**
	 * Create a new StringBufferWriter that locks on the given object
	 * 
	 * @param initLock
	 *            - the object to lock with
	 */
	public StringBufferWriter(Object initLock) {
		super(initLock);
	}
	@Override
	public IBuffer setBuffer(CharSequence buf) {
		return this;
	}
	@Override
	public String getBuffer() {
		return this.buffer.toString();
	}
	@Override
	public IBuffer appendToBuffer(CharSequence buf) {
		return this;
	}
	@Override
	public IBuffer prependToBuffer(CharSequence buf) {
		return this;
	}
	@Override
	public void close() {}
	@Override
	public void flush() {}
	@Override
	public void write(char[] cbuf, int off, int len) {
		this.buffer.append(cbuf, off, len);
	}
	@Override
	public String toString() {
		return this.getBuffer();
	}
}
