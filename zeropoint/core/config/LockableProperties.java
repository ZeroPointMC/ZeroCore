package zeropoint.core.config;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import zeropoint.core.ILockable;
import zeropoint.core.exception.DeprecatedException;
import zeropoint.core.exception.LockedException;


/**
 * LockableProperties is an extended version of
 * Java's native Properties class. It can be
 * locked, preventing changes.
 * 
 * Once locked, a LockableProperties object can
 * <b>NOT</b> be unlocked.
 * 
 * @author Zero Point
 */
public class LockableProperties extends Properties implements ILockable {
	private static final long serialVersionUID = -8110854083552206754L;
	/**
	 * The message to use when throwing an exception
	 */
	protected static final String ERRMSG = "Cannot load properties to a locked LockableProperties object";
	/**
	 * Indicates whether the object can be modified
	 */
	protected boolean isLocked = false;
	@Override
	public synchronized LockableProperties clone() {
		LockableProperties clone = (LockableProperties) super.clone();
		clone.isLocked = false;
		return clone;
	}
	public void lock() {
		this.isLocked = true;
	}
	public boolean locked() {
		return this.isLocked;
	}
	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		super.load(inStream);
	}
	@Override
	public synchronized void load(Reader reader) throws IOException {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		super.load(reader);
	}
	@Override
	public synchronized void loadFromXML(InputStream in) throws InvalidPropertiesFormatException, IOException {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		super.loadFromXML(in);
	}
	@Override
	@Deprecated
	public void save(OutputStream out, String comments) {
		throw new DeprecatedException();
	}
	@Override
	public synchronized Object setProperty(String key, String value) {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		return super.setProperty(key, value);
	}
}
