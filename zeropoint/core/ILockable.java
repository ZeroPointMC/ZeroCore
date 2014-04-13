package zeropoint.core;

/**
 * ILockable indicates that an object can be "locked". A locked object should act as read-only, and should throw a LockedException on write access.
 * 
 * @see ILockableLock
 * @see ILockableUnlock
 * @see ILockableToggle
 * @author Zero Point
 */
public interface ILockable {
	public boolean locked();
	public void lock();
}
