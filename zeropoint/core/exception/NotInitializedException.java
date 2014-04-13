package zeropoint.core.exception;

/**
 * If an object needs to initialized before use, but the initialization method(s) must be called externally, this exception indicates that initialization was not performed. It should not be thrown when you forget to call an internal initialization method.
 * 
 * @author Zero Point
 */
public class NotInitializedException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = 5506064651166235001L;
	@Override
	public String getDefaultMessage() {
		return "Code attempted to use an uninitialized object";
	}
}
