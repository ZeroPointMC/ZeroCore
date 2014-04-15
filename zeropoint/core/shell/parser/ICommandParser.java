package zeropoint.core.shell.parser;


import zeropoint.core.shell.Shell;


/**
 * ICommandParser indicates that an object is intended to handle text lines from a {@link Shell} object.
 * 
 * @see ICommandPreParser
 * @see IShellCommandParser
 * @author Zero Point
 */
public interface ICommandParser extends IShellCommandParser {
	/**
	 * @param command
	 *            - the command <code>String</code> to be parsed
	 * @param shell
	 *            - the {@link Shell} the parser is registered with
	 */
	public void parse(String command, Shell shell);
}
