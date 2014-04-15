package zeropoint.core;

/**
 * <code>ILockable</code> indicates that an object can be "locked". A locked object should act as read-only, and should throw a LockedException on write access.
 * 
 * @author Zero Point
 */
public interface ILockable {
	/**
	 * @return <code>true</code> if the object is locked and cannot be modified, <code>false</code> otherwise
	 */
	public boolean locked();
	/**
	 * Locks the object, making it non-modifiable
	 */
	public void lock();
}
