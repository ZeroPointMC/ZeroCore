package zeropoint.core.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Allows reading and writing to a {@link StringBuffer} via {@link InputStream} and {@link OutputStream} objects
 * 
 * @author Zero Point
 */
public class IOStream implements IStreamIO, IBuffer {
	/**
	 * The data buffer
	 */
	protected final StringBuffer sb = new StringBuffer();
	/**
	 * The {@link OutputStream} that writes to the buffer
	 */
	protected OutputStream toBuffer = null;
	/**
	 * The {@link InputStream} that reads from the buffer
	 */
	protected InputStream fromBuffer = null;
	/**
	 * The class that does the work of writing to the buffer
	 * 
	 * @author Zero Point
	 */
	protected class WriteToBuffer extends OutputStream {
		@Override
		public void close() {}
		@Override
		public void flush() {}
		@Override
		public void write(int i) {
			try {
				IOStream.this.sb.append((char) i);
			}
			catch (ClassCastException e) {}
		}
	}
	/**
	 * The class that reads the data from the buffer
	 * 
	 * @author Zero Point
	 */
	protected class ReadFromBuffer extends InputStream {
		@Override
		public int read() {
			if (IOStream.this.sb.toString().length() == 0) {
				return -1;
			}
			char c = IOStream.this.sb.charAt(0);
			if (c < 0) {
				return -1;
			}
			IOStream.this.sb.delete(0, 1);
			return c;
		}
	}
	@Override
	public String toString() {
		return this.getBuffer();
	}
	/**
	 * @return the {@link InputStream} that reads from the data buffer
	 */
	public InputStream getReadStream() {
		if (this.fromBuffer == null) {
			this.fromBuffer = new ReadFromBuffer();
		}
		return this.fromBuffer;
	}
	/**
	 * @return the {@link OutputStream} that writes to the data buffer
	 */
	public OutputStream getWriteStream() {
		if (this.toBuffer == null) {
			this.toBuffer = new WriteToBuffer();
		}
		return this.toBuffer;
	}
	@Override
	public IWritable<Character> write(Character c) {
		try {
			this.getWriteStream().write(c);
		}
		catch (IOException e) {}
		return this;
	}
	@Override
	public Integer read() {
		try {
			return this.getReadStream().read();
		}
		catch (IOException e) {
			return -1;
		}
	}
	@Override
	public IBuffer setBuffer(CharSequence buf) {
		this.sb.delete(0, this.sb.length());
		this.sb.append(buf);
		return this;
	}
	@Override
	public IBuffer appendToBuffer(CharSequence buf) {
		this.sb.append(buf);
		return this;
	}
	@Override
	public IBuffer prependToBuffer(CharSequence buf) {
		String old = this.sb.toString();
		this.sb.delete(0, this.sb.length());
		this.sb.append(buf);
		this.sb.append(old);
		return this;
	}
	@Override
	public String getBuffer() {
		return this.sb.toString();
	}
}
