package me.meegan.window;

import javax.swing.*;

public class StatusBar extends JLabel {
    public StatusBar() {
        super();
        setText("Ready");
    }

    /**
     * Sends a success message to the status bar.
     *
     * @param s - Success message.
     */
    public void success(String s) {
        setText("SUCCESS: " + s);
    }
}
