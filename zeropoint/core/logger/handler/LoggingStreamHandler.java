package zeropoint.core.logger.handler;


import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.LogRecord;


/**
 * LoggingStreamHandler is a modified StreamHandler for logging.
 * 
 * @author Zero Point
 */
public class LoggingStreamHandler extends LoggingHandlerBase implements Closeable, Flushable {
	protected OutputStream out;
	public LoggingStreamHandler() {
		out = null;
	}
	@Override
	protected void finalize() {
		if (out != null) {
			try {
				out.close();
			}
			catch (Exception e) {
				reportError("Could not close output stream", e, e.hashCode());
			}
		}
	}
	@Override
	public void close() throws SecurityException {
		flush();
		try {
			out.close();
			out = null;
		}
		catch (IOException e) {
			reportError("Could not close output stream", e, e.hashCode());
		}
	}
	@Override
	public void flush() {
		try {
			out.flush();
		}
		catch (IOException e) {
			reportError("Could not flush output stream", e, e.hashCode());
		}
	}
	@Override
	public void publish(LogRecord msg) {
		if (this.isLoggable(msg)) {
			String tmp = getFormattedMessage(msg);
			byte[] outLine = tmp.getBytes();
			try {
				out.write(outLine);
			}
			catch (IOException e) {
				reportError("Could not write to output stream", e, e.hashCode());
			}
		}
	}
}
