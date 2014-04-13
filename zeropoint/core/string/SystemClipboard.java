package zeropoint.core.string;


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;


public class SystemClipboard {
	public static void setString(String data) {
		try {
			StringSelection stringselection = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner) null);
		}
		catch (Exception exception) {}
	}
	public static String getString() {
		try {
			String data = null;
			Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			if (clip.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
				data = clip.getData(DataFlavor.stringFlavor).toString();
			}
			return data;
		}
		catch (Exception e) {
			return null;
		}
	}
}
