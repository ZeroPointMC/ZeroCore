package zeropoint.core.io;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


/**
 * Allows capturing of the data sent to standard out/standard error
 * 
 * @author Zero Point
 * @deprecated Not working properly - keeps crossing the stdout and stderr streams
 */
@Deprecated
public class Capture {
	private static PrintStream STDOUT = System.out;
	private static PrintStream STDERR = System.err;
	private static boolean cappingOut = false;
	private static boolean cappingErr = false;
	/**
	 * Capture text sent to standard output
	 * 
	 * @author Zero Point
	 */
	protected static class CapOut extends OutputStream {
		private static final CapOut instance = new CapOut();
		/**
		 * The log of all data sent to standard out since activation/last reset
		 */
		protected final StringBuffer log = new StringBuffer();
		@Override
		public void write(int c) throws IOException {
			try {
				char ch = (char) c;
				this.log.append(ch);
				STDOUT.append(ch);
			}
			catch (ClassCastException e) {
				Capture.ignoreStandardOutput();
				throw new IOException("Cannot convert " + c + " to character");
			}
		}
		/**
		 * @return the CapOut instance
		 */
		public static final CapOut instance() {
			return instance;
		}
	}
	/**
	 * Capture text sent to standard error
	 * 
	 * @author Zero Point
	 */
	protected static class CapErr extends OutputStream {
		private static final CapErr instance = new CapErr();
		/**
		 * The log of all data sent to standard out since activation/last reset
		 */
		protected final StringBuffer log = new StringBuffer();
		@Override
		public void write(int c) throws IOException {
			try {
				char ch = (char) c;
				this.log.append(ch);
				STDERR.append(ch);
			}
			catch (ClassCastException e) {
				Capture.ignoreStandardError();
				throw new IOException("Cannot convert " + c + " to character");
			}
		}
		/**
		 * @return the CapErr instance
		 */
		public static final CapErr instance() {
			return instance;
		}
	}
	/**
	 * Begin capturing text sent to standard out; no effect if already capturing standard out
	 */
	public static void captureStandardOut() {
		if (cappingOut) {
			return;
		}
		cappingOut = true;
		STDOUT = System.out;
		System.setOut(new PrintStream(CapOut.instance()));
	}
	/**
	 * Begin capturing text sent to standard error; no effect if already capturing standard error
	 */
	public static void captureStandardError() {
		if (cappingErr) {
			return;
		}
		cappingErr = true;
		STDERR = System.err;
		System.setOut(new PrintStream(CapErr.instance()));
	}
	/**
	 * Stop capturing standard output; no effect if not capturing
	 */
	public static void ignoreStandardOutput() {
		if ( !cappingOut) {
			return;
		}
		System.setOut(STDOUT);
		cappingOut = false;
	}
	/**
	 * Stop capturing standard error; no effect if not capturing
	 */
	public static void ignoreStandardError() {
		if ( !cappingErr) {
			return;
		}
		System.setErr(STDERR);
		cappingErr = false;
	}
	/**
	 * @return the contents of the stdout log
	 */
	public static String getOutputLog() {
		return getOutputLog(false);
	}
	/**
	 * @param clear
	 *            - <code>true</code> to clear the log, <code>false</code> to leave the current contents
	 * @return the contents of the stdout log
	 */
	public static String getOutputLog(boolean clear) {
		if (clear) {
			String ret = getOutputLog(false);
			CapOut.instance().log.delete(0, CapOut.instance().log.length());
			return ret;
		}
		return CapOut.instance().log.toString();
	}
	/**
	 * @return the contents of the stderr log
	 */
	public static String getErrorLog() {
		return getErrorLog(false);
	}
	/**
	 * @param clear
	 *            - <code>true</code> to clear the log, <code>false</code> to leave the current contents
	 * @return the contents of the stderr log
	 */
	public static String getErrorLog(boolean clear) {
		if (clear) {
			String ret = getErrorLog(false);
			CapErr.instance().log.delete(0, CapOut.instance().log.length());
			return ret;
		}
		return CapErr.instance().log.toString();
	}
}
