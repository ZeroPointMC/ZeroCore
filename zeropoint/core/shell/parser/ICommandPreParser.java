package zeropoint.core.shell.parser;

import zeropoint.core.shell.Shell;

/**
 * ICommandPreParser indicates that an object is capable of preparsing commands from a {@link Shell} object. A preparser can modify the command for later parsers, and is able to cancel parsing entirely. There may only be one preparser per Shell object.
 * 
 * @see ICommandParser
 * @see IShellCommandParser
 * @author Zero Point
 */
public interface ICommandPreParser extends IShellCommandParser {
	public String parse(String command, Shell shell);
}
