package zeropoint.core.shell.parser;


import zeropoint.core.shell.Shell;


/**
 * IShellCommandParser should not be directly implemented. It is only a base for {@link ICommandParser} and {@link ICommandPreParser}.
 * 
 * @see Shell
 * @author Zero Point
 */
public interface IShellCommandParser {
	/**
	 * @return the name of the parser
	 */
	public String name();
	/**
	 * @param command
	 *            - the command to be tested
	 * @return <code>true</code> if the parser can handle the given command, <code>false</code> otherwise
	 */
	public boolean canParse(String command);
}
