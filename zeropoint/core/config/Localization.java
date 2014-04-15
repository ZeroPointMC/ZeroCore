package zeropoint.core.config;


import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import zeropoint.core.ILockable;
import zeropoint.core.exception.LockedException;
import zeropoint.core.io.file.FileBase;


/**
 * Localization makes it easy to create a map of keys
 * to localized string, and apply that map to a string.
 */
public class Localization implements ILockable, Cloneable {
	/**
	 * The properties used for localization
	 */
	protected LockableProperties props;
	/**
	 * Flag indicating whether we are locked
	 */
	protected boolean isLocked = false;
	@Override
	public Localization clone() {
		Localization clone = new Localization(this.props.clone());
		clone.isLocked = false;
		return clone;
	}
	/**
	 * Initialize the properties to an empty {@link LockableProperties} object
	 */
	public Localization() {
		this.props = new LockableProperties();
	}
	/**
	 * Copy the given {@link Properties} for use in localization
	 * 
	 * @param init
	 *            - the Properties to use for localization
	 */
	public Localization(Properties init) {
		this.props = (LockableProperties) init;
		this.isLocked = true;
	}
	/**
	 * Load properties from the given {@link ConfigFile}
	 * 
	 * @param init
	 *            - the ConfigFile to load from
	 */
	public Localization(ConfigFile init) {
		this.props = init.getProperties();
		this.isLocked = true;
	}
	/**
	 * Load the ./locale/<file>.lang file as the properties
	 * 
	 * @param file
	 *            - the file name
	 */
	public Localization(String file) {
		loadProps(file);
	}
	/**
	 * Load a .lang file with the given name (locale) from the given directory
	 * 
	 * @param dir
	 *            - the directory to look in
	 * @param locale
	 *            - the basename of the file to load
	 */
	public Localization(String dir, String locale) {
		loadProps(dir, locale);
	}
	/**
	 * Load a .lang file in the ./locale/ directory
	 * 
	 * @param file
	 *            - the file basename to load
	 */
	public void loadProps(String file) {
		loadProps("./locale", new FileBase(file).basename());
	}
	/**
	 * Load a .lang file in the given directory with the given locale as the basename
	 * 
	 * @param dir
	 *            - the directory of the .lang file to load
	 * @param locale
	 *            - the basename of the .lang file to load
	 */
	public void loadProps(String dir, String locale) {
		ConfigFile conf = new ConfigFile(dir + "/" + locale.replaceAll("\\s+", "_") + ".lang");
		this.props = conf.getProperties();
		this.isLocked = true;
	}
	/**
	 * Get the properties used for localization
	 * 
	 * @return the internal properties
	 */
	public Properties getProps() {
		return this.props;
	}
	/**
	 * Overwrite the properties used for localization
	 * 
	 * @param newProps
	 *            - the new {@link Properties} object
	 * @throws LockedException
	 *             if the object cannot be modified
	 */
	public void setProps(Properties newProps) {
		if (this.isLocked) {
			throw new LockedException().setMessage("Cannot modify locked Localization object");
		}
		this.props = (LockableProperties) newProps;
		this.isLocked = true;
	}
	/**
	 * Localize a string according to the properties contained in the current object
	 * 
	 * @param base
	 *            - the string to localize
	 * @return the localized version of the string
	 */
	public String localize(String base) {
		if (base == null) {
			return "";
		}
		Set<String> keys = this.props.stringPropertyNames();
		Iterator<String> iter = keys.iterator();
		String localized = base;
		while (iter.hasNext()) {
			String next = iter.next();
			localized = localized.replaceAll("\\{" + next + "\\}", this.props.getProperty(next, "[null]"));
		}
		return localized;
	}
	public boolean locked() {
		return this.isLocked;
	}
	public void lock() {
		this.isLocked = true;
	}
}
