package zeropoint.core.logger;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * LoggerFactory retrieves and configures a logger automagically. Configured loggers will log everything.
 * 
 * @see Logger
 * @author Zero Point
 */
public class LoggerFactory {
	protected static final Level DEFAULT_LEVEL = Level.ALL;
	protected static final Handler[] DEFAULT_HANDLERS = new Handler[] {
		new ConsoleHandler()
	};
	// Just create/configure the logger with defaults, overridden if
	// the caller provided their own values. Post-creation configuration
	// can be handled by the caller, because we just return the logger.
	protected static Logger create(String className, Handler[] handler, Level lvl) {
		Logger logger = Logger.getLogger(className);
		logger.setUseParentHandlers(false);
		List<Handler> handlers = Arrays.asList(handler);
		Iterator<Handler> iter = handlers.iterator();
		while (iter.hasNext()) {
			logger.addHandler(iter.next());
		}
		logger.setLevel(lvl);
		return logger;
	}
	protected static Logger create(String className, Handler handler, Level lvl) {
		return create(className, new Handler[] {
			handler
		}, lvl);
	}
	protected static Logger create(String className, Handler handler) {
		return create(className, handler, DEFAULT_LEVEL);
	}
	protected static Logger create(String className, Handler[] handler) {
		return create(className, handler, DEFAULT_LEVEL);
	}
	protected static Logger create(String className, Level lvl) {
		return create(className, DEFAULT_HANDLERS, lvl);
	}
	protected static Logger create(String className) {
		return create(className, DEFAULT_HANDLERS, DEFAULT_LEVEL);
	}
	public static Logger create(Handler handler, Level lvl) {
		String className = new Exception().getStackTrace()[1].getClassName();
		return create(className, handler, lvl);
	}
	public static Logger create(Handler[] handler, Level lvl) {
		String className = new Exception().getStackTrace()[1].getClassName();
		return create(className, handler, lvl);
	}
	public static Logger create(Level lvl) {
		String className = new Exception().getStackTrace()[1].getClassName();
		return create(className, DEFAULT_HANDLERS, lvl);
	}
	public static Logger create(Handler handler) {
		String className = new Exception().getStackTrace()[1].getClassName();
		return create(className, handler, DEFAULT_LEVEL);
	}
	public static Logger create(Handler[] handler) {
		String className = new Exception().getStackTrace()[1].getClassName();
		return create(className, handler, DEFAULT_LEVEL);
	}
	public static Logger create() {
		String className = new Exception().getStackTrace()[1].getClassName();
		return create(className, DEFAULT_HANDLERS, DEFAULT_LEVEL);
	}
}
