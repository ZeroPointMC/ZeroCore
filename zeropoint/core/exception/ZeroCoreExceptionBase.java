package zeropoint.core.exception;


import zeropoint.core.config.Localization;


/**
 * ZeroCoreExceptionBase is the abstract framework for ZeroCore's exceptions.
 */
public abstract class ZeroCoreExceptionBase extends RuntimeException {
	private static final long serialVersionUID = 1155057208935694151L;
	/**
	 * {@linkplain Localization} object used to localize the message
	 */
	protected Localization l;
	/**
	 * The message to be displayed when reporting this exception
	 */
	protected String message;
	/**
	 * The default message to be used when one is not provided.
	 * 
	 * @return The default message for this exception
	 */
	public abstract String getDefaultMessage();
	/**
	 * Construct a new exception with default values:
	 * <ul>
	 * <li>The exception class's default message</li>
	 * <li>No underlying {@linkplain Throwable} cause</li>
	 * <li>Empty {@linkplain Localization}</li>
	 * </ul>
	 */
	public ZeroCoreExceptionBase() {
		this.init(getDefaultMessage(), null, new Localization());
	}
	/**
	 * Construct a new exception with:
	 * <ul>
	 * <li>A specific message</li>
	 * <li>No underlying {@linkplain Throwable} cause</li>
	 * <li>Empty {@linkplain Localization}</li>
	 * </ul>
	 * 
	 * @param msg
	 *            - the message for this exception
	 */
	public ZeroCoreExceptionBase(String msg) {
		this.init(msg, null, new Localization());
	}
	/**
	 * Construct a new exception with:
	 * <ul>
	 * <li>A specific message</li>
	 * <li>No underlying {@linkplain Throwable} cause</li>
	 * <li>Specific {@linkplain Localization}</li>
	 * </ul>
	 * 
	 * @param msg
	 *            - the message for this exception
	 * @param loc
	 *            - the Localization object for this exception
	 */
	public ZeroCoreExceptionBase(String msg, Localization loc) {
		this.init(msg, null, loc);
	}
	/**
	 * Construct a new exception with:
	 * <ul>
	 * <li>The exception class's default message</li>
	 * <li>The given underlying {@linkplain Throwable} cause</li>
	 * <li>Empty {@linkplain Localization}</li>
	 * </ul>
	 * 
	 * @param e
	 *            - the underlying cause of this exception
	 */
	public ZeroCoreExceptionBase(Throwable e) {
		this.init(getDefaultMessage(), e, new Localization());
	}
	/**
	 * Construct a new exception with:
	 * <ul>
	 * <li>The given message</li>
	 * <li>The given underlying {@linkplain Throwable} cause</li>
	 * <li>Empty {@linkplain Localization}</li>
	 * </ul>
	 * 
	 * @param msg
	 *            - the message for this exception
	 * @param e
	 *            - the cause for this exception (if any)
	 */
	public ZeroCoreExceptionBase(String msg, Throwable e) {
		this.init(msg, e, new Localization());
	}
	/**
	 * Construct a new exception with:
	 * <ul>
	 * <li>The given message</li>
	 * <li>The given underlying {@linkplain Throwable} cause</li>
	 * <li>Specific {@linkplain Localization}</li>
	 * </ul>
	 * 
	 * @param msg
	 *            - the message for this exception
	 * @param e
	 *            - the cause for this exception (if any)
	 * @param loc
	 *            - the Localization object for this exception
	 */
	public ZeroCoreExceptionBase(String msg, Throwable e, Localization loc) {
		this.init(msg, e, loc);
	}
	/**
	 * Perform general initialization for this exception object.
	 * This includes setting the message, the cause, and the {@linkplain Localization}.
	 * 
	 * @param msg
	 *            - The message for this exception
	 * @param e
	 *            - The underlying cause (null if none)
	 * @param loc
	 *            - The localization for this exception's message
	 */
	protected final void init(String msg, Throwable e, Localization loc) {
		message = msg;
		if ((e != null) && (this.getCause() == null)) {
			this.initCause(e);
		}
		this.initialize();
	}
	/**
	 * Change the {@linkplain Localization} object
	 * used by this exception
	 * 
	 * @param localizer
	 *            - the new Localization object
	 * @return The exception itself
	 */
	public final ZeroCoreExceptionBase setLocalizationObject(Localization localizer) {
		l = localizer;
		return this;
	}
	/**
	 * Get the current {@linkplain Localization} object
	 * 
	 * @return The current Localization object used by this exception
	 */
	public final Localization getLocalizationObject() {
		return l;
	}
	/**
	 * Indicates whether there is a {@linkplain Localization} object registered with this exception
	 * 
	 * @return <tt>true</tt> if there is a Localization object
	 *         registered (and not <tt>null</tt>),
	 *         <tt>false</tt> otherwise
	 */
	public final boolean isLocalizing() {
		return l != null;
	}
	@Override
	public final String toString() {
		return this.getClass().getCanonicalName() + ": " + this.getLocalizedMessage();
	}
	@Override
	public String getLocalizedMessage() {
		try {
			return l.localize(this.getMessage());
		}
		catch (Exception e) {
			return this.getMessage();
		}
	}
	@Override
	public final String getMessage() {
		return message == null ? "" : message;
	}
	public final ZeroCoreExceptionBase setMessage(String msg) {
		message = msg;
		return this;
	}
	@Override
	public final ZeroCoreExceptionBase initCause(Throwable cause) {
		super.initCause(cause);
		return this;
	}
	protected void initialize() {}
}
