package zeropoint.core;

/**
 * StackTrace is a convenience class for dealing with stack traces. It makes it easier to find the class name, method name, and line number that your method was called from. It also includes a simple method to get an entire stack trace, from the perspective of the caller.
 * 
 * @author Zero Point
 */
public class StackTrace {
	public static String getCallingMethod() {
		return trace()[2].getMethodName();
	}
	public static String getCallingClass() {
		try {
			return Class.forName(trace()[2].getClassName()).getSimpleName();
		}
		catch (ClassNotFoundException e) {
			return "<unknown class>";
		}
	}
	public static String getLongCallingClass() {
		return trace()[2].getClassName();
	}
	public static int getCallingLine() {
		return trace()[2].getLineNumber();
	}
	public static StackTraceElement[] trace() {
		StackTraceElement[] stack = new Exception().getStackTrace();
		StackTraceElement[] trace = new StackTraceElement[stack.length - 1];
		for (int i = 1; i < stack.length; i++ ) {
			trace[i - 1] = stack[i];
		}
		return trace;
	}
}
