package ca.hccis.scuba.util;

import javax.swing.JOptionPane;

/**
 * Lets the same code get input and show messages either in the console or using
 * JOptionPane.  This way the console thread and the JOptionPane thread can share
 * the same add / view / edit logic.
 *
 * @author Jonathan Dumaguing
 * @since 20261015
 */
public class InputUtility {

    /**
     * Get a String from the user.
     *
     * @param prompt Prompt for the user
     * @param gui true to use JOptionPane, false to use the console
     * @return The text entered (never null, a cancelled dialog returns "")
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static String getString(String prompt, boolean gui) {
        if (gui) {
            String value = JOptionPane.showInputDialog(prompt);
            if (value == null) {
                return "";
            }
            return value;
        }
        return CisUtility.getInputString(prompt);
    }

    /**
     * Show a message to the user.
     *
     * @param message Message to show
     * @param gui true to use JOptionPane, false to use the console
     * @author Jonathan Dumaguing
     * @since 20261015
     */
    public static void showMessage(String message, boolean gui) {
        if (gui) {
            JOptionPane.showMessageDialog(null, message);
        } else {
            System.out.println(message);
        }
    }
}
