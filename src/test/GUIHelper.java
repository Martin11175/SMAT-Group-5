package test;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Auxiliary class to simulate interaction with the GUI.
 * @author gustavohpcarvalho
 */
public class GUIHelper {

	public static final int standardDelay = 600;
	
	/**
	 * Auxiliary method to return the index of the first visible JDialog.
	 * @return
	 */
	private static int getFirsVisibleJDialog() {	
		Window[] windows = JDialog.getWindows();
		for (int i=0; i<windows.length; i++) {
			if (windows[i].isVisible() && windows[i] instanceof JDialog) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Auxiliary method to click on a button with a given text.
	 * @param matchingText
	 */
	private static void clickOnButton(Component component, String matchingText) {
		if (component instanceof JOptionPane) {
			JOptionPane pane = (JOptionPane) component;
			for (Component innerComponent : pane.getComponents()) {
				GUIHelper.clickOnButton(innerComponent, matchingText);
			}
		} else if (component instanceof JPanel) {
			JPanel panel = (JPanel) component;
			String name = panel.getName();
			if (name != null && name.equals("OptionPane.buttonArea")) {
				for (Component innerComponent : panel.getComponents()) {
					GUIHelper.clickOnButton(innerComponent, matchingText);
				}
			}
		} else if (component instanceof JButton) {
			JButton button = (JButton) component;
			String text = button.getText();
			if (text != null && text.equals(matchingText)) {
				button.doClick();
			}
		}
	}
	
	/**
	 * Auxiliary method to set the text field of a JOptionPane
	 * @param matchingText
	 */
	private static void setTextFieldOfJOptionPane(Component component, String value) {
		if (component instanceof JOptionPane) {
			JOptionPane pane = (JOptionPane) component;
			for (Component innerComponent : pane.getComponents()) {
				GUIHelper.setTextFieldOfJOptionPane(innerComponent, value);
			}
		} else if (component instanceof JPanel) {
			JPanel panel = (JPanel) component;
			for (Component innerComponent : panel.getComponents()) {
				GUIHelper.setTextFieldOfJOptionPane(innerComponent, value);
			}
		} else if (component instanceof JTextField) {
			JTextField paneUI = (JTextField) component;
			paneUI.setText(value);
		}
	}	
	
	
	/**
	 * Auxiliary method to simulate interaction with windows.
	 * @param delayInMS
	 * @param windowIndex
	 * @param componentIndexes
	 * @param values
	 * @throws Exception
	 */
	public static void inputToJOptionPane(int delayInMS, String value) {
		try {
			// Some delay to ensure that the window has been opened. 
			Thread.sleep(delayInMS);
				
			int windowIndex = GUIHelper.getFirsVisibleJDialog();
			
			if (windowIndex != -1) {
				// The following code gets both owned and ownerless windows.
				Window[] windows = JDialog.getWindows();
				// Now, we retrieve the desired window.
				JDialog dialog = (JDialog) windows[windowIndex];
				
				// I need to access the JOptionPane within the dialog.
				JOptionPane pane = (JOptionPane) dialog.getContentPane().getComponent(0);

				// Now, I set the value of the text field
				GUIHelper.setTextFieldOfJOptionPane(pane, value);
				// Finally, I click on the "OK" button
				GUIHelper.clickOnButton(pane, "OK");

				// Some delay to ensure that the window has been closed.
				Thread.sleep(delayInMS);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
