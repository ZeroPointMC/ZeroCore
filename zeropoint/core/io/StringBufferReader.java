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
		StringBufferReader clone = new StringBufferReader(this.lock);
		clone.buffer = new StringBuffer(this.buffer.toString());
		return clone;
	}
	/**
	 * Create a new StringBufferReader
	 */
	public StringBufferReader() {
		super();
	}
	/**
	 * Create a new StringBufferReader that locks on the given object
	 * 
	 * @param initLock
	 *            - the object to lock with
	 */
	public StringBufferReader(Object initLock) {
		super(initLock);
	}
	@Override
	public IBuffer setBuffer(CharSequence buf) {
		if (buf != null) {
			this.buffer.append(buf);
		}
		return this;
	}
	@Override
	public String getBuffer() {
		return this.buffer.toString();
	}
	@Override
	public IBuffer appendToBuffer(CharSequence buf) {
		if (buf != null) {
			this.buffer.append(buf);
		}
		return this;
	}
	@Override
	public IBuffer prependToBuffer(CharSequence buf) {
		if (buf != null) {
			this.buffer = new StringBuffer(buf).append(this.buffer);
		}
		return this;
	}
	@Override
	public void close() {
		// Eh. Might as well, I suppose.
		this.buffer = new StringBuffer();
	}
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		for (int i = off, l = 0; l <= len; i++ , l++ ) {
			if (this.buffer.toString().isEmpty()) {
				return l - 1;
			}
			try {
				cbuf[i] = this.buffer.charAt(0);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				return l;
			}
			this.buffer.replace(0, 1, "");
		}
		return 0;
	}
	@Override
	public boolean ready() {
		return !this.buffer.toString().isEmpty();
	}
	@Override
	public String toString() {
		return this.getBuffer();
	}
}
