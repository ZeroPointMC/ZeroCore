package zeropoint.core.shell.parser;


import zeropoint.core.shell.Shell;


/**
 * DefaultPreParser is used automatically when a {@link Shell} object does not have a specific preparser set.
 * 
 * @see DefaultPostParser
 * @author Zero Point
 */
public class DefaultPreParser implements ICommandPreParser {
	@Override
	public String name() {
		return "DefaultPreParser";
	}
	@Override
	public boolean canParse(String command) {
		return false;
	}
	@Override
	public String preparse(String command, Shell shell) {
		return command;
	}
}
