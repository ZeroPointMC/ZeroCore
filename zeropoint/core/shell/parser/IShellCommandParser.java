package zeropoint.core.shell.parser;


import zeropoint.core.shell.Shell;


/**
 * IShellCommandParser should not be directly implemented. It is only a base for {@link ICommandParser} and {@link ICommandPreParser}.
 * 
 * @see Shell
 * @author Zero Point
 */
public interface IShellCommandParser {
	public String name();
	public boolean canParse(String command);
}
