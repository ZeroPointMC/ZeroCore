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
	protected static final String ERRMSG = "Cannot load properties to a locked LockableProperties object";
	protected boolean isLocked = false;
	@Override
	public LockableProperties clone() {
		LockableProperties clone = (LockableProperties) super.clone();
		clone.isLocked = false;
		return clone;
	}
	public void lock() {
		isLocked = true;
	}
	public boolean locked() {
		return isLocked;
	}
	@Override
	public void load(InputStream inStream) throws IOException {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		super.load(inStream);
	}
	@Override
	public void load(Reader reader) throws IOException {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		super.load(reader);
	}
	@Override
	public void loadFromXML(InputStream in) throws InvalidPropertiesFormatException, IOException {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		super.loadFromXML(in);
	}
	@Override
	@SuppressWarnings("deprecation")
	@Deprecated
	public void save(OutputStream out, String comments) {
		throw new DeprecatedException();
	}
	@Override
	public Object setProperty(String key, String value) {
		if (locked()) {
			throw new LockedException().setMessage(ERRMSG);
		}
		return super.setProperty(key, value);
	}
}
