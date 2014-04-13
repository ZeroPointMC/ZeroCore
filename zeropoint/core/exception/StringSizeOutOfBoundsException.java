package zeropoint.core.exception;

/**
 * Throwing a StringSizeOutOfBoundsException indicates that the size of a string parameter is either to high or too low.
 * 
 * @author Zero Point
 */
public class StringSizeOutOfBoundsException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = 3422820663287437368L;
	@Override
	public String getDefaultMessage() {
		return "Size of string not within acceptable range";
	}
}
