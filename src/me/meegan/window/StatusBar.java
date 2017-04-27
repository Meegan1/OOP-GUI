package me.meegan.window;

import javax.swing.*;

public class StatusBar extends JLabel {
    public StatusBar() {
        super();
        setText("Ready");
    }

    public void success(String s) {
        setText("SUCCESS: "+s);
    }
}
