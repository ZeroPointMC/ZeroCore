package zeropoint.core.logger;


import java.util.logging.Level;


/**
 * ZeroCoreLoggingLevel extends the native Java class {@link Level}.
 * 
 * @author Zero Point
 */
public class ZeroCoreLoggingLevel extends Level {
	private static final long serialVersionUID = 2914850555938268104L;
	public static final Level TRACE = new ZeroCoreLoggingLevel("TRACE", 600);
	protected ZeroCoreLoggingLevel(String name, int value) {
		super(name, value);
	}
	protected ZeroCoreLoggingLevel(String name, int value, String resourceBundleName) {
		super(name, value, resourceBundleName);
	}
}
