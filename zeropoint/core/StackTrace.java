package zeropoint.core;

/**
 * StackTrace is a convenience class for dealing with stack traces. It makes it easier to find the class name, method name, and line number that your method was called from. It also includes a simple method to get an entire stack trace, from the perspective of the caller.
 * 
 * @author Zero Point
 */
public class StackTrace {
	/**
	 * The stack trace leading up to the call to <code>new StackTrace()</code>
	 */
	protected StackTraceElement[] trace;
	/**
	 * @return the parent method of the method that called getCallingMethod()
	 */
	public static String getCallingMethod() {
		return trace()[2].getMethodName();
	}
	/**
	 * @return the parent class of the method that called getCallingClass()
	 */
	public static String getCallingClass() {
		return simplifyClassName(trace()[2].getClassName());
	}
	/**
	 * @return the long (fully qualified) parent class of the method that called getLongCallingClass()
	 */
	public static String getLongCallingClass() {
		return trace()[2].getClassName();
	}
	/**
	 * @return the line at which the caller of getCallingLine() was called
	 */
	public static int getCallingLine() {
		return trace()[2].getLineNumber();
	}
	/**
	 * @return an array of StackTraceElements leading up to the caller of trace()
	 */
	public static StackTraceElement[] trace() {
		StackTraceElement[] stack = new Exception().getStackTrace();
		StackTraceElement[] trace = new StackTraceElement[stack.length - 1];
		for (int i = 1; i < stack.length; i++ ) {
			trace[i - 1] = stack[i];
		}
		return trace;
	}
	/**
	 * @param frame
	 *            - the StackTraceElement to get the simple class name from
	 * @return the simple (unqualified) class name specified by the stack frame
	 */
	public static String simpleClassName(StackTraceElement frame) {
		return simplifyClassName(frame.getClassName());
	}
	/**
	 * @param fullName
	 *            - the full name of the class
	 * @return the simplified name of the given class
	 */
	protected static String simplifyClassName(String fullName) {
		return fullName.replaceFirst("\\.+$", "").replaceFirst("^.*\\.", "");
	}
	/**
	 * @return the line number of the call to <code>getLine()</code>
	 */
	public static int getLine() {
		return trace()[1].getLineNumber();
	}
	/**
	 * A StackTrace object holds a stack trace up to the point at which it was created
	 */
	public StackTrace() {
		// This ensures that the constructor's call is the first element on the stack trace
		StackTraceElement[] stack = trace();
		this.trace = new StackTraceElement[stack.length - 1];
		for (int i = 1; i < stack.length; i++ ) {
			this.trace[i - 1] = stack[i];
		}
	}
	/**
	 * Get a specific frame from the stored stack trace
	 * 
	 * @param pos
	 *            - the frame to retrieve
	 * @return a {@link StackTraceElement} representing the requested frame
	 */
	public StackTraceElement frame(int pos) {
		try {
			return this.trace[pos];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	/**
	 * @return the fully qualified class name of the method that created this object
	 */
	public String ownerClass() {
		return this.trace[0].getClassName();
	}
	/**
	 * @return the short class name of the method that created this object
	 */
	public String ownerSimpleClass() {
		return simplifyClassName(this.trace[0].getClassName());
	}
	/**
	 * @return the name of the method that created this object
	 */
	public String ownerMethod() {
		return this.trace[0].getMethodName();
	}
}
