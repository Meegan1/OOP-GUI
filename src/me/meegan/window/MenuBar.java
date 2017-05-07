package me.meegan.window;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

class MenuBar extends JMenuBar implements ActionListener {
    private final JFileChooser fileChooser = new JFileChooser();

    MenuBar() {

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

        add(file);
        add(help);

        JMenuItem fileNew = new JMenuItem("New", KeyEvent.VK_N);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        JMenuItem fileSave = new JMenuItem("Save", KeyEvent.VK_S);
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem fileLoad = new JMenuItem("Load", KeyEvent.VK_L);
        fileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        JMenuItem filePrint = new JMenuItem("Print", KeyEvent.VK_P);
        filePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        JMenuItem fileExit = new JMenuItem("Exit", KeyEvent.VK_E);
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        JMenuItem helpAbout = new JMenuItem("About", KeyEvent.VK_A);
        helpAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));

        file.add(fileNew);
        file.add(fileSave);
        file.add(fileLoad);
        JMenuItem fileSaveImage = new JMenuItem("Export As Image", KeyEvent.VK_E);
        file.add(fileSaveImage);
        file.add(filePrint);
        file.add(fileExit);

        help.add(helpAbout);

        fileNew.addActionListener(this);
        fileSave.addActionListener(this);
        fileLoad.addActionListener(this);
        fileSaveImage.addActionListener(this);
        filePrint.addActionListener(this);
        fileExit.addActionListener(this);

        helpAbout.addActionListener(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "New") new Window(); // creates new window
        else if(e.getActionCommand() == "Save") save();
        else if(e.getActionCommand() == "Load") load();
        else if(e.getActionCommand() == "Export As Image") exportImage();
        else if(e.getActionCommand() == "Print") print();
        else if(e.getActionCommand() == "Exit") exit();

        else if(e.getActionCommand() == "About") about();
    }

    private void save() {
        fileChooser.setFileFilter(new FileNameExtensionFilter(".paint", "paint"));
        Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
        if(fileChooser.showDialog(this, "Save") == JFileChooser.APPROVE_OPTION) { // shows Save fileChooser
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".paint"))
                file = new File(file.toString() + ".paint");

            try {
                window.painter.save(file); // calls save method and passes the selected file
                window.statusBar.success(file.getAbsolutePath() + " has been saved.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void exportImage() {
        fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
        Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
        if(fileChooser.showDialog(this, "Export As Image") == JFileChooser.APPROVE_OPTION) { // shows Save fileChooser
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".png"))
                file = new File(file.toString() + ".png");

            try {
                window.painter.exportImage(file); // calls save method and passes the selected file
                window.statusBar.success(file.getAbsolutePath() + " has been exported.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void load() {
        if(hasChanged() && !confirm())
            return;
        fileChooser.setFileFilter(new FileNameExtensionFilter(".paint", "paint"));
        Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
        if(fileChooser.showDialog(this, "Load") == JFileChooser.APPROVE_OPTION) { // shows Load fileChooser
            try {
                window.painter.load(fileChooser.getSelectedFile()); // calls load method and passes the selected file
                window.statusBar.success(fileChooser.getSelectedFile().getAbsolutePath() + " has been loaded.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    boolean confirm() {
        int n = JOptionPane.showConfirmDialog(this,
                "The current file has not been saved,\n" +
                        "are you sure you want to do this?",
                "Closing current file",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return n == JOptionPane.YES_OPTION;
    }

    boolean hasChanged() {
        return ((Window) SwingUtilities.getWindowAncestor(this)).painter.hasChanged();
    }

    private void print() {
        Window window = (Window) SwingUtilities.getWindowAncestor(this);
        window.painter.print();
        window.statusBar.success("the image has been printed.");
    }

    private void exit() {
        if (hasChanged() && (!hasChanged() || !confirm())) return;
            SwingUtilities.getWindowAncestor(this).dispose(); // disposes of window
    }


    private void about() {
        JOptionPane.showMessageDialog(this,
                "OOP GUI Project: Turtle Graphics\n" +
                        "Created by: Jake Meegan",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}