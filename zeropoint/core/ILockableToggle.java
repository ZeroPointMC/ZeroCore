package zeropoint.core;

/**
 * <code>ILockableToggle</code> indicates that an object may be locked, and can be both locked and unlocked on demand.
 * 
 * @see ILockable
 * @see ILockableUnlock
 * @author Zero Point
 * @deprecated Extends the deprecated {@linkplain ILockableUnlock} interface.
 */
@Deprecated
public interface ILockableToggle extends ILockableUnlock {
	/**
	 * Toggle the locked state of the lockable object
	 */
	public void toggleLock();
}
