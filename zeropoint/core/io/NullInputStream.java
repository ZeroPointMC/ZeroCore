package zeropoint.core.io;


import java.io.InputStream;


/**
 * NullInputStream objects can be used anywhere that InputStream objects are needed, but act as though they are constantly at the end of their stream.
 * 
 * @see NullOutputStream
 * @see NullReader
 * @see NullWriter
 * @author Zero Point
 */
public class NullInputStream extends InputStream {
	@Override
	public int read() {
		return -1;
	}
}
