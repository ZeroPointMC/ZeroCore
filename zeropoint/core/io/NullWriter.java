package zeropoint.core.io;


import java.io.Writer;


/**
 * A NullWriter object can be used anywhere a Writer object is allowed, but discards all data sent to it.
 * 
 * @see NullInputStream
 * @see NullOutputStream
 * @see NullReader
 * @author Zero Point
 */
public class NullWriter extends Writer {
	public NullWriter() {}
	@Override
	public Writer append(char c) {
		return this;
	}
	@Override
	public Writer append(CharSequence csq) {
		return this;
	}
	@Override
	public Writer append(CharSequence csq, int start, int end) {
		return this;
	}
	@Override
	public void write(int c) {}
	@Override
	public void write(char[] cbuf) {}
	@Override
	public void write(char[] arg0, int arg1, int arg2) {}
	@Override
	public void write(String str) {}
	@Override
	public void write(String str, int off, int len) {}
	@Override
	public void close() {}
	@Override
	public void flush() {}
}
