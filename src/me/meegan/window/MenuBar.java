package me.meegan.window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar implements ActionListener {
    final JFileChooser dialog = new JFileChooser();

    public JMenu file = new JMenu("File");
    public JMenu help = new JMenu("Help");

    //File
    public JMenuItem fileNew = new JMenuItem("New", KeyEvent.VK_N);
    public JMenuItem fileLoad = new JMenuItem("Load", KeyEvent.VK_L);
    public JMenuItem fileSave = new JMenuItem("Save", KeyEvent.VK_S);
    public JMenuItem fileExit = new JMenuItem("Exit", KeyEvent.VK_E);

    //Help
    public JMenuItem helpAbout = new JMenuItem("About", KeyEvent.VK_A);

    public MenuBar() {

        file.setMnemonic(KeyEvent.VK_F);
        help.setMnemonic(KeyEvent.VK_H);

        add(file);
        add(help);

        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        fileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

        helpAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));

        file.add(fileNew);
        file.add(fileLoad);
        file.add(fileSave);
        file.add(fileExit);

        help.add(helpAbout);

        fileNew.addActionListener(this);
        fileSave.addActionListener(this);
        fileLoad.addActionListener(this);
        fileExit.addActionListener(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "New")
            new Window(); // creates new window
        else if(e.getActionCommand() == "Save") {
            Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
            if(dialog.showDialog(window.painter, "Save") == JFileChooser.APPROVE_OPTION) { // shows Save dialog
                window.painter.save(dialog.getSelectedFile()); // calls save method and passes the selected file
            }
        }
        else if(e.getActionCommand() == "Load") {
            Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
            if(dialog.showDialog(window.painter, "Load") == JFileChooser.APPROVE_OPTION) { // shows Load dialog
                window.painter.load(dialog.getSelectedFile()); // calls load method and passes the selected file
            }
        }
        else if(e.getActionCommand() == "Exit") {
            SwingUtilities.getWindowAncestor(this).dispose(); // disposes of window
        }

    }
}