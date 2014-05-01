// Apr 30, 2014 2:11:24 PM
package zeropoint.core.exception;


import zeropoint.core.config.Localization;


@SuppressWarnings("javadoc")
public class WrappedException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = -1517405625299700945L;
	public WrappedException(Throwable e) {
		this.init("[wrapped] " + e.getLocalizedMessage(), e, new Localization());
	}
	public WrappedException(Throwable e, Localization loc) {
		this.init("[wrapped] " + e.getLocalizedMessage(), e, loc);
	}
	@Override
	public String getDefaultMessage() {
		return "";
	}
}
