package zeropoint.core.exception;


import zeropoint.core.Version;


/**
 * Indicates that the version string given to the {@link Version} constructor is not valid
 * 
 * @author Zero Point
 */
public class InvalidVersionException extends ZeroCoreExceptionBase {
	private static final long serialVersionUID = -974388645585361274L;
	@Override
	public String getDefaultMessage() {
		return "Version string not valid!";
	}
}
