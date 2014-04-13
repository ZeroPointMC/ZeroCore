package zeropoint.core;

/**
 * ILockableToggle indicates that an object may be locked, and can be both locked and unlocked on demand.
 * 
 * @see ILockable
 * @see ILockableLock
 * @see ILockableUnlock
 * @author Zero Point
 * @deprecated
 */
@SuppressWarnings("deprecation")
@Deprecated
public interface ILockableToggle extends ILockableUnlock {
	public void toggleLock();
}
