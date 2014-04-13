package zeropoint.core.io;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


/**
 * A StringBufferReader is a special {@link StringReader} that allows ongoing access to its internal buffer.
 * 
 * @see StringBufferInputStream
 * @see StringBufferOutputStream
 * @see StringBufferWriter
 * @author Zero Point
 */
public class StringBufferReader extends Reader implements Cloneable, IBuffer {
	private StringBuffer buffer = new StringBuffer();
	@Override
	public StringBufferReader clone() {
		StringBufferReader clone = new StringBufferReader(lock);
		clone.buffer = new StringBuffer(buffer.toString());
		return clone;
	}
	public StringBufferReader() {
		super();
	}
	public StringBufferReader(Object initLock) {
		super(initLock);
	}
	public IBuffer setBuffer(CharSequence buf) {
		if (buf != null) {
			buffer.append(buf);
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
	public void close() {
		// Eh. Might as well, I suppose.
		buffer = new StringBuffer();
	}
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		for (int i = off, l = 0; l <= len; i++ , l++ ) {
			if (buffer.toString().isEmpty()) {
				return l - 1;
			}
			try {
				cbuf[i] = buffer.charAt(0);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				return l;
			}
			buffer.replace(0, 1, "");
		}
		return 0;
	}
	@Override
	public boolean ready() {
		return !buffer.toString().isEmpty();
	}
	@Override
	public String toString() {
		return getBuffer();
	}
}
