package zeropoint.core.logger;


import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Quick class written to simplify configuring a {@link Logger} object
 * 
 * @author Zero Point
 */
public class LoggerConfig {
	/**
	 * Flag indicating that the logger should use parent handlers
	 */
	public static final int USE_PARENT_HANDLERS = 1;
	/**
	 * Configures a {@link Logger} object
	 * 
	 * @param l
	 *            - the <code>Logger</code> to configure
	 * @param flags
	 *            - the flags to use
	 * @param formatter
	 *            - the {@link Formatter} to apply to the handler(s)
	 * @param lvl
	 *            - the {@link Level} of messages that should be logged
	 * @param handlers
	 *            - one or more {@link Handler} objects to register with the <code>Logger</code>
	 * @return the configured <code>Logger</code> object
	 */
	public static Logger config(Logger l, int flags, Formatter formatter, Level lvl, Handler... handlers) {
		if ((flags & USE_PARENT_HANDLERS) != 0) {
			l.setUseParentHandlers(true);
		}
		else {
			l.setUseParentHandlers(false);
		}
		for (Handler h : handlers) {
			if (h != null) {
				if (formatter != null) {
					h.setFormatter(formatter);
				}
				l.addHandler(h);
				if (lvl != null) {
					h.setLevel(lvl);
				}
			}
		}
		if (lvl != null) {
			l.setLevel(lvl);
		}
		return l;
	}
	/**
	 * Throws together a logger configured the way I always set them up. Convenience method.
	 * 
	 * @param loggerName
	 *            - the name of the logger, passed to Logger.gerLogger(...)
	 * @return a Logger ready for use
	 * @author Zero Point
	 */
	public static Logger generate(String loggerName) {
		return config(Logger.getLogger(loggerName), 0, new LoggingFormatter(LoggingFormatter.FLAG_TIME_STANDARD, true), Level.ALL, new ConsoleHandler());
	}
}
