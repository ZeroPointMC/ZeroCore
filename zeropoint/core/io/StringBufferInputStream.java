package zeropoint.core.io;


import java.io.InputStream;


/**
 * A StringBufferInputStream is a special InputStream that uses an internal string buffer, and allows ongoing access to that buffer.
 * 
 * @see StringBufferOutputStream
 * @see StringBufferReader
 * @see StringBufferWriter
 * @author Zero Point
 */
public class StringBufferInputStream extends InputStream implements IBuffer, Cloneable {
	private StringBuffer buffer = new StringBuffer();
	@Override
	public StringBufferInputStream clone() {
		StringBufferInputStream clone = new StringBufferInputStream(buffer.toString());
		return clone;
	}
	public StringBufferInputStream() {
		super();
	}
	public StringBufferInputStream(String init) {
		super();
		buffer = new StringBuffer(init);
	}
	public IBuffer setBuffer(CharSequence buf) {
		if (buf != null) {
			buffer = new StringBuffer(buf);
		}
		return this;
	}
	public String getBuffer() {
		return buffer.toString();
	}
	public IBuffer appendToBuffer(CharSequence buf) {
		if (buf != null) {
			buffer.append(buf);
		}
		return this;
	}
	public IBuffer prependToBuffer(CharSequence buf) {
		if (buf != null) {
			buffer = new StringBuffer(buf).append(buffer);
		}
		return this;
	}
	@Override
	public int read() {
		if (buffer == null) {
			buffer = new StringBuffer();
		}
		if (buffer.toString().isEmpty()) {
			return -1;
		}
		int c = buffer.charAt(0);
		buffer.replace(0, 1, "");
		return c;
	}
	@Override
	public String toString() {
		return getBuffer();
	}
}
