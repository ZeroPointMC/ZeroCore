package zeropoint.core.exception;


import zeropoint.core.StackTrace;


/**
 * DeprecatedException indicates that the throwing method should not be called, <i>ever</i>. It's a stronger version of the \@Deprecated annotation.
 * 
 * @author Zero Point
 */
public class DeprecatedException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = -9033782412370087052L;
	@Override
	public String getDefaultMessage() {
		return (StackTrace.getCallingClass() + "." + StackTrace.getCallingMethod() + " is deprecated.");
	}
}
