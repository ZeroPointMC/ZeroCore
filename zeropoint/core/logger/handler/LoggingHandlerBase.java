package zeropoint.core.logger.handler;


import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


/**
 * LoggingHandlerBase is a modified base class for logging handlers. It logs all messages by default, and changes the output to the format <tt>{@literal [<class>.<method> <level>] <message>}</tt>
 * 
 * @author Zero Point
 */
public abstract class LoggingHandlerBase extends Handler {
	private static final boolean logSource = false;
	protected Level lvl = Level.ALL;
	protected Formatter fmt = null;
	protected Filter filter = null;
	@Override
	public void setLevel(Level newLvl) {
		lvl = newLvl;
	}
	@Override
	public final Level getLevel() {
		return lvl;
	}
	@Override
	public void setFilter(Filter newFilter) {
		filter = newFilter;
	}
	@Override
	public final Filter getFilter() {
		return filter;
	}
	@Override
	public void setFormatter(Formatter newFmt) {
		fmt = newFmt;
	}
	@Override
	public final Formatter getFormatter() {
		return fmt;
	}
	@Override
	public final boolean isLoggable(LogRecord test) {
		return (test.getLevel().intValue() >= lvl.intValue()) && (filter != null ? filter.isLoggable(test) : true);
	}
	public String getFormattedMessage(LogRecord msg) {
		String tmp = "";
		String[] classParts = msg.getSourceClassName().split("\\.");
		String className = "";
		String methodName = msg.getSourceMethodName();
		String origin = "";
		if (logSource) {
			try {
				className = classParts[classParts.length - 1];
			}
			catch (ArrayIndexOutOfBoundsException e) {
				className = classParts[0];
			}
			if (className.isEmpty()) {
				className = "????";
			}
			if (methodName.isEmpty()) {
				methodName = "????";
			}
			origin = className + "." + methodName + "()";
		}
		tmp += "[";
		if (logSource) {
			tmp += origin + " ";
		}
		tmp += msg.getLevel().getName();
		tmp += "] ";
		if (fmt != null) {
			tmp += fmt.formatMessage(msg);
		}
		else {
			tmp += msg.getMessage();
		}
		if (msg.getThrown() != null) {
			Throwable e = msg.getThrown();
			tmp += " [" + e.toString();
			while (e.getCause() != null) {
				e = e.getCause();
				tmp += "; caused by " + e;
			}
			tmp += "]";
		}
		tmp += "\n";
		return tmp;
	}
	public final int getByteLength(LogRecord msg) {
		return getFormattedMessage(msg).getBytes().length;
	}
	public final int getStringLength(LogRecord msg) {
		return getFormattedMessage(msg).length();
	}
}
