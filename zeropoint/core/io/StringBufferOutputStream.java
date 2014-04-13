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
		StringBufferOutputStream clone = new StringBufferOutputStream(buffer.toString());
		return clone;
	}
	public StringBufferOutputStream() {
		buffer = new StringBuffer();
	}
	public StringBufferOutputStream(String init) {
		buffer = new StringBuffer(init);
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
	public void write(int arg0) {
		buffer.append(arg0);
	}
	@Override
	public String toString() {
		return getBuffer();
	}
}
