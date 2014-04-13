package zeropoint.core.io.file;


import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.PrintStream;

import zeropoint.core.exception.LockedException;


/**
 * OutputFile makes it easier to deal with writing to a file.
 * 
 * @see ZeroCoreFileBase
 * @author Zero Point
 */
public class OutputFile extends ZeroCoreFileBase implements Closeable, Flushable {
	protected PrintStream stream;
	protected boolean shouldAppend = false;
	public OutputFile(String fpath) {
		super(fpath);
	}
	public void setAppend(boolean append) {
		if (locked()) {
			throw new LockedException();
		}
		shouldAppend = append;
	}
	public boolean willAppend() {
		return shouldAppend;
	}
	public boolean open(boolean append) {
		if (stream == null) {
			reloadObjectFile();
			lock();
			try {
				stream = new PrintStream(new FileOutputStream(path(), append));
			}
			catch (FileNotFoundException e) {
				try {
					create();
					open();
				}
				catch (IOException e1) {
					isLocked = false;
					e1.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	public boolean open() {
		return open(shouldAppend);
	}
	public void print(Object o) {
		open();
		stream.print(o);
	}
	public void println(Object o) {
		open();
		stream.println(o);
	}
	public void format(String fmt, Object... args) {
		open();
		stream.format(fmt, args);
	}
	public void flush() {
		open();
		stream.flush();
	}
	public void close() {
		open();
		flush();
		stream.close();
		stream = null;
		isLocked = false;
	}
	public void write(int b) {
		open();
		stream.write(b);
	}
	public void write(byte[] b) throws IOException {
		open();
		stream.write(b);
	}
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		stream.write(arg0, arg1, arg2);
	}
}
