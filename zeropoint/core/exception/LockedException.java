package zeropoint.core.exception;


import zeropoint.core.ILockable;


/**
 * A LockedException should be thrown when code attempts to modify a locked {@link ILockable} object, and you don't want to fail quietly.
 * 
 * @see ILockable
 * @author Zero Point
 */
public class LockedException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = -7401705319401027485L;
	@Override
	public String getDefaultMessage() {
		return "Code attempted to modify a locked ILockable object";
	}
}
