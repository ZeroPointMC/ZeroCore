package zeropoint.core.config;


import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import zeropoint.core.ILockable;
import zeropoint.core.exception.LockedException;
import zeropoint.core.io.file.ZeroCoreFileBase;


/**
 * Localization makes it easy to create a map of keys
 * to localized string, and apply that map to a string.
 */
public class Localization implements ILockable, Cloneable {
	protected LockableProperties props;
	protected boolean isLocked = false;
	@Override
	public Localization clone() {
		Localization clone = new Localization(props.clone());
		clone.isLocked = false;
		return clone;
	}
	public Localization() {
		props = new LockableProperties();
	}
	public Localization(Properties init) {
		props = (LockableProperties) init;
		isLocked = true;
	}
	public Localization(ConfigFile init) {
		props = init.getProperties();
		isLocked = true;
	}
	public Localization(String file) {
		loadProps(file);
	}
	public Localization(String dir, String locale) {
		loadProps(dir, locale);
	}
	public void loadProps(String file) {
		loadProps("./locale", new ZeroCoreFileBase(file).basename());
	}
	public void loadProps(String dir, String locale) {
		String progName = System.getProperty("sun.java.command", "ZeroCore").split("\\s+")[0];
		ConfigFile jconf = new ConfigFile(dir + "/" + progName.replaceAll("\\s+", "_") + "." + locale.replaceAll("\\s+", "_") + ".lang");
		props = jconf.getProperties();
		isLocked = true;
	}
	public Properties getProps() {
		return props;
	}
	public void setProps(Properties newProps) {
		if (isLocked) {
			throw new LockedException().setMessage("Cannot modify locked Localization object");
		}
		this.props = (LockableProperties) newProps;
		isLocked = true;
	}
	public String localize(String base) {
		if (base == null) {
			return "";
		}
		Set<String> keys = props.stringPropertyNames();
		Iterator<String> iter = keys.iterator();
		String localized = base;
		while (iter.hasNext()) {
			String next = iter.next();
			localized = localized.replaceAll("\\{" + next + "\\}", props.getProperty(next, "[null]"));
		}
		return localized;
	}
	public boolean locked() {
		return isLocked;
	}
	public void lock() {
		isLocked = true;
	}
}
