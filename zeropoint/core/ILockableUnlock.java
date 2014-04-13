package zeropoint.core;

/**
 * ILockableUnlock indicates that an object can be locked, but may be unlocked on demand. It is included for completeness, and not recommended for use.
 * 
 * @see ILockable
 * @see ILockableLock
 * @see ILockableToggle
 * @author Zero Point
 * @deprecated
 */
@Deprecated
public interface ILockableUnlock extends ILockable {
	public void unlock();
}
