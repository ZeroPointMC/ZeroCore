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
		StringBufferWriter clone = new StringBufferWriter(lock);
		clone.buffer = new StringBuffer(buffer.toString());
		return clone;
	}
	public StringBufferWriter() {
		super();
	}
	public StringBufferWriter(Object l) {
		super(l);
	}
	public IBuffer setBuffer(CharSequence buf) {
		return this;
	}
	public String getBuffer() {
		return buffer.toString();
	}
	public IBuffer appendToBuffer(CharSequence buf) {
		return this;
	}
	public IBuffer prependToBuffer(CharSequence buf) {
		return this;
	}
	@Override
	public void close() {}
	@Override
	public void flush() {}
	@Override
	public void write(char[] cbuf, int off, int len) {
		buffer.append(cbuf, off, len);
	}
	@Override
	public String toString() {
		return getBuffer();
	}
}
