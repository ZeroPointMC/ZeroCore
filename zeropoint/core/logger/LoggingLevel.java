package zeropoint.core.logger;


import java.util.logging.Level;


/**
 * ZeroCoreLoggingLevel extends the native Java class {@link Level}.
 * 
 * @author Zero Point
 */
public class LoggingLevel extends Level {
	private static final long serialVersionUID = 2914850555938268104L;
	/**
	 * New logging level indicating that a message is for tracing code execution
	 */
	public static final Level TRACE = new LoggingLevel("TRACE", 600);
	/**
	 * @param name
	 *            - the name of the new logging level
	 * @param value
	 *            - the <code>int</code> value of the new logging level
	 */
	protected LoggingLevel(String name, int value) {
		super(name, value);
	}
	/**
	 * @param name
	 *            - the name of the new logging level
	 * @param value
	 *            - the <code>int</code> value of the new logging level
	 * @param resourceBundleName
	 *            - the resource bundle for the new logging level
	 */
	protected LoggingLevel(String name, int value, String resourceBundleName) {
		super(name, value, resourceBundleName);
	}
}
