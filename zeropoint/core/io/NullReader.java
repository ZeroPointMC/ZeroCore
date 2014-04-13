package zeropoint.core.io;


import java.io.Reader;


/**
 * NullReader objects can be used anywhere that Reader objects are needed, but are always at end-of-stream.
 * 
 * @see NullInputStream
 * @see NullOutputStream
 * @see NullWriter
 * @author Zero Point
 */
public class NullReader extends Reader {
	@Override
	public void close() {}
	@Override
	public int read(char[] cbuf, int off, int len) {
		return -1;
	}
}
