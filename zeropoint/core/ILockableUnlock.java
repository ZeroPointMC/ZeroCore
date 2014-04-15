package zeropoint.core;

/**
 * <code>ILockableUnlock</code> indicates that an object can be locked, but may be unlocked on demand. It is included for completeness, and not recommended for use.
 * 
 * @see ILockable
 * @see ILockableToggle
 * @author Zero Point
 * @deprecated Defeats most of the point of implementing ILockable in the first place.
 */
@Deprecated
public interface ILockableUnlock extends ILockable {
	/**
	 * Unlock the locked object
	 */
	public void unlock();
}
