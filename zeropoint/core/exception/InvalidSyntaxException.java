package zeropoint.core.exception;


import zeropoint.core.shell.Shell;


/**
 * Thrown by a {@linkplain Shell} object when a
 * given command cannot be parsed
 * 
 * @author Zero Point
 * 
 */
public class InvalidSyntaxException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = -6002664378045532660L;
	@Override
	public String getDefaultMessage() {
		return "Shell object unable to parse command due to invalid syntax";
	}
}
