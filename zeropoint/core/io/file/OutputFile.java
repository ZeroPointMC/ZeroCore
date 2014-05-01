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
 * @see FileBase
 * @author Zero Point
 */
public class OutputFile extends FileBase implements Closeable, Flushable {
	/**
	 * The stream to the file
	 */
	protected PrintStream stream;
	/**
	 * Whether or not to append to the file on open
	 */
	protected boolean shouldAppend = false;
	/**
	 * Create a new OutputFile
	 * 
	 * @param path
	 *            - the file path to represent
	 */
	public OutputFile(String path) {
		super(path);
	}
	/**
	 * Set whether or not to append to the file on open
	 * 
	 * @param append
	 *            - <code>true</code> to append, <code>false</code> to overwrite
	 */
	public void setAppend(boolean append) {
		if (this.locked()) {
			throw new LockedException();
		}
		this.shouldAppend = append;
	}
	/**
	 * @return whether or not the file will be appended to on open
	 */
	public boolean willAppend() {
		return this.shouldAppend;
	}
	/**
	 * Open the file for output
	 * 
	 * @param append
	 *            - <code>true</code> to append, <code>false</code> to overwrite
	 * @return <code>true</code> on success, <code>false</code> on failure
	 */
	@SuppressWarnings("deprecation")
	public boolean open(boolean append) {
		if (this.stream == null) {
			this.reloadObjectFile();
			this.lock();
			try {
				this.stream = new PrintStream(new FileOutputStream(this.path(), append));
			}
			catch (FileNotFoundException e) {
				try {
					this.create();
					this.open();
				}
				catch (IOException e1) {
					this.isLocked = false;
					e1.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * @return <code>true</code> on success, <code>false</code> on failure
	 */
	public boolean open() {
		return this.open(this.shouldAppend);
	}
	/**
	 * Print the <code>String</code> representation of the given object to the file
	 * 
	 * @param o
	 *            - the object to stringify and print
	 */
	public void print(Object o) {
		this.open();
		this.stream.print(o);
	}
	/**
	 * Print the <code>String</code> representation of the given object to the file, followed by a line break
	 * 
	 * @param o
	 *            - the object to stringify and print
	 */
	public void println(Object o) {
		this.open();
		this.stream.println(o);
	}
	/**
	 * Write formatted text to the file
	 * 
	 * @param fmt
	 *            - the format specification
	 * @param args
	 *            - the objects to use for formatting
	 */
	public void format(String fmt, Object... args) {
		this.open();
		this.stream.format(fmt, args);
	}
	@Override
	public void flush() {
		this.open();
		this.stream.flush();
	}
	@Override
	@SuppressWarnings("deprecation")
	public void close() {
		this.open();
		this.flush();
		this.stream.close();
		this.stream = null;
		this.isLocked = false;
	}
	/**
	 * Write to the file
	 * 
	 * @param b
	 *            - the ASCII character code to write
	 */
	public void write(int b) {
		this.open();
		this.stream.write(b);
	}
	/**
	 * Write to the file
	 * 
	 * @param b
	 *            - the byte array to write to the file
	 * @throws IOException
	 *             if the file cannot be written to
	 */
	public void write(byte[] b) throws IOException {
		this.open();
		this.stream.write(b);
	}
	/**
	 * Write to the file
	 * 
	 * @param arg0
	 *            - the bytes to write out
	 * @param arg1
	 *            - the offset to start writing from
	 * @param arg2
	 *            - the length to write
	 * @throws IOException
	 *             if the file cannot be written to
	 */
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		this.open();
		this.stream.write(arg0, arg1, arg2);
	}
}
