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
		StringBufferInputStream clone = new StringBufferInputStream(this.buffer.toString());
		return clone;
	}
	/**
	 * Create a new StringBufferInputStream with a blank buffer
	 */
	public StringBufferInputStream() {
		super();
		this.buffer = new StringBuffer();
	}
	/**
	 * Create a new StringBufferInputStream with the given contents
	 * 
	 * @param init
	 *            - the initial contents of the buffer
	 */
	public StringBufferInputStream(String init) {
		super();
		this.buffer = new StringBuffer(init);
	}
	/**
	 * Create a new StringBufferInputStream with the given buffer. Note that the buffer is used directly, not copied!
	 * 
	 * @param init
	 *            - the buffer to use
	 */
	public StringBufferInputStream(StringBuffer init) {
		super();
		this.buffer = init;
	}
	@Override
	public IBuffer setBuffer(CharSequence buf) {
		if (buf != null) {
			this.buffer = new StringBuffer(buf);
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
	public int read() {
		if (this.buffer == null) {
			this.buffer = new StringBuffer();
		}
		if (this.buffer.toString().isEmpty()) {
			return -1;
		}
		int c = this.buffer.charAt(0);
		this.buffer.replace(0, 1, "");
		return c;
	}
	@Override
	public String toString() {
		return this.getBuffer();
	}
}
