package zeropoint.core.logger.handler;

/**
 * LoggingConsoleHandler is a subclass of LoggingStreamHandler that uses STDERR as its output.
 * 
 * @author Zero Point
 */
public class LoggingConsoleHandler extends LoggingStreamHandler implements AutoCloseable {
	public LoggingConsoleHandler() {
		out = System.err;
	}
}
