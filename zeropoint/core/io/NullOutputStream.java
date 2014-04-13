package zeropoint.core.io;


import java.io.OutputStream;


/**
 * NullOutputStream objects can be used anywhere that OutputStream objects are needed, but completely discard any and all data sent to them.
 * 
 * @see NullInputStream
 * @see NullReader
 * @see NullWriter
 * @author Zero Point
 */
public class NullOutputStream extends OutputStream {
	@Override
	public void close() {}
	@Override
	public void flush() {}
	@Override
	public void write(byte[] b) {}
	@Override
	public void write(byte[] b, int off, int len) {}
	@Override
	public void write(int b) {}
}
