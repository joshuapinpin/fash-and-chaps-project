package nz.ac.wgtn.swen225.lc.persistency;

import javax.swing.*;

/**
 * Abstraction (adapter!!) for GUI message dialogs, so they can be used
 * for actually showing dialogs or for mocking in headless tests.
 * @author Thomas Ru - 300658840
 */
public interface Message {

    /**
     * Shows a message to the user.
     * @param message - the message text
     * @param title - the dialog title
     * @param type - the message type e.g. JOptionPane.ERROR_MESSAGE
     */
    void showMessage(String message, String title, int type);

    /**
     * Default adapter that wraps JOptionPane
     */
    class Default implements Message {
        /**
         * Shows a Swing popup message.
         * @param message - the message text
         * @param title - the dialog title
         * @param type - the message type e.g. JOptionPane.ERROR_MESSAGE
         */
        @Override
        public void showMessage(String message, String title, int type) {
            JOptionPane.showMessageDialog(null, message, title, type);
        }
    }

    /**
     * Headless/test adapter that does nothing
     */
    class Stub implements Message {
        /**
         * Does nothing, useful for testing.
         * @param message - the message text
         * @param title - the dialog title
         * @param type - the message type e.g. JOptionPane.ERROR_MESSAGE
         */
        @Override
        public void showMessage(String message, String title, int type) { }
    }
}
