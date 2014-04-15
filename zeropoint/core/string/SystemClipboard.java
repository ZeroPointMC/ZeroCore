package zeropoint.core.string;


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;


/**
 * Allows manipulation of the user's clipboard
 * 
 * @author Zero Point
 */
public class SystemClipboard {
	/**
	 * @param data
	 *            - the content to save to the clipboard
	 */
	public static void setString(String data) {
		try {
			StringSelection stringselection = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner) null);
		}
		catch (Exception exception) {}
	}
	/**
	 * @return the current contents of the clipboard, or <code>null</code> if the clipboard cannot be represented as a <code>String</code>
	 */
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
