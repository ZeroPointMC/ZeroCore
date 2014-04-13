package zeropoint.core.shell.parser;


import java.util.regex.Pattern;

import zeropoint.core.Test;
import zeropoint.core.shell.Shell;


/**
 * ShellTestParser is used in ZeroCore's self test. You should never have any valid reason to instantiate it.
 * 
 * @author Zero Point
 */
public final class ShellTestParser extends DefaultPostParser {
	@Override
	public String name() {
		return "ShellTestParser";
	}
	@Override
	public boolean canParse(String command) {
		command = command.toLowerCase();
		if (super.canParse(command)) {
			return true;
		}
		if (command.matches("(?:do|run)\\s+test")) {
			return true;
		}
		return false;
	}
	@Override
	public void parse(String command, Shell shell) {
		command = command.toLowerCase();
		Pattern pattern = Pattern.compile("(?:do|run)\\s+test");
		if (pattern.matcher(command).matches()) {
			Test.logger().fine("ShellTestParser successfully parsed command");
		}
		else {
			super.parse(command, shell);
		}
	}
}
