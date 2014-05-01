package zeropoint.core.io;


import java.io.OutputStream;


/**
 * A StringBufferInputStream is a special OutputStream that uses an internal string buffer, and allows ongoing access to that buffer.
 * 
 * @see StringBufferInputStream
 * @see StringBufferReader
 * @see StringBufferWriter
 * @author Zero Point
 */
public class StringBufferOutputStream extends OutputStream implements Cloneable, IBuffer {
	private StringBuffer buffer = new StringBuffer();
	@Override
	public StringBufferOutputStream clone() {
		StringBufferOutputStream clone = new StringBufferOutputStream(this.buffer.toString());
		return clone;
	}
	/**
	 * Create a new StringBufferOutputStream with a blank buffer
	 */
	public StringBufferOutputStream() {
		this.buffer = new StringBuffer();
	}
	/**
	 * Create a new StringBufferOutputStream with the given contents
	 * 
	 * @param init
	 *            - the initial contents of the buffer
	 */
	public StringBufferOutputStream(String init) {
		this.buffer = new StringBuffer(init);
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
	public void write(int arg0) {
		this.buffer.append(arg0);
	}
	@Override
	public String toString() {
		return this.getBuffer();
	}
}
