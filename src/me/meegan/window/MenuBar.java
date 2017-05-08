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

    // Creates the menu bar and assigns listeners, icons and shortcuts to each menu item
    MenuBar() {

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);


        JMenuItem fileNew = new JMenuItem("New", new ImageIcon(getClass().getResource("/resources/new_file.png")));
        JMenuItem fileNewWindow = new JMenuItem("New Window", new ImageIcon(getClass().getResource("/resources/new_window.png")));
        JMenuItem fileSave = new JMenuItem("Save", new ImageIcon(getClass().getResource("/resources/save_file.png")));
        JMenuItem fileLoad = new JMenuItem("Load", new ImageIcon(getClass().getResource("/resources/load_file.png")));
        JMenuItem fileSaveImage = new JMenuItem("Export As Image", new ImageIcon(getClass().getResource("/resources/export_image.png")));
        JMenuItem fileImportImage = new JMenuItem("Import Image", new ImageIcon(getClass().getResource("/resources/import_image.png")));
        JMenuItem filePrint = new JMenuItem("Print", new ImageIcon(getClass().getResource("/resources/print.png")));
        JMenuItem fileExit = new JMenuItem("Exit", new ImageIcon(getClass().getResource("/resources/exit.png")));

        JMenuItem helpAbout = new JMenuItem("About", new ImageIcon(getClass().getResource("/resources/about.png")));


        fileNew.setMnemonic(KeyEvent.VK_N);
        fileNewWindow.setMnemonic(KeyEvent.VK_N);
        fileSave.setMnemonic(KeyEvent.VK_S);
        fileLoad.setMnemonic(KeyEvent.VK_L);
        fileSaveImage.setMnemonic(KeyEvent.VK_E);
        fileImportImage.setMnemonic(KeyEvent.VK_I);
        filePrint.setMnemonic(KeyEvent.VK_P);
        fileExit.setMnemonic(KeyEvent.VK_E);

        helpAbout.setMnemonic(KeyEvent.VK_A);

        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        fileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        filePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        helpAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));


        add(file);
        add(help);

        file.add(fileNew);
        file.add(fileNewWindow);
        file.add(fileSave);
        file.add(fileLoad);
        file.add(fileSaveImage);
        file.add(fileImportImage);
        file.add(filePrint);
        file.add(fileExit);

        help.add(helpAbout);


        fileNew.addActionListener(this);
        fileNewWindow.addActionListener(this);
        fileSave.addActionListener(this);
        fileLoad.addActionListener(this);
        fileSaveImage.addActionListener(this);
        fileImportImage.addActionListener(this);
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
        if(e.getActionCommand() == "New") reset();
        else if(e.getActionCommand() == "New Window") new Window(); // creates new window
        else if(e.getActionCommand() == "Save") save();
        else if(e.getActionCommand() == "Load") load();
        else if(e.getActionCommand() == "Export As Image") exportImage();
        else if(e.getActionCommand() == "Import Image") importImage();
        else if(e.getActionCommand() == "Print") print();
        else if(e.getActionCommand() == "Exit") exit();

        else if(e.getActionCommand() == "About") about();
    }

    private void reset() {
        if(hasChanged() && !confirm())
            return;
        Window window = (Window) SwingUtilities.getWindowAncestor(this);
        window.painter.newPaint();
        window.statusBar.success("the paint panel has been reset.");
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
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportImage() {
        fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
        Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
        if(fileChooser.showDialog(this, "Export As Image") == JFileChooser.APPROVE_OPTION) { // shows export fileChooser
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".png"))
                file = new File(file.toString() + ".png");

            try {
                window.painter.exportImage(file); // calls export method and passes the selected file
                window.statusBar.success(file.getAbsolutePath() + " has been exported.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void importImage() {
        if(hasChanged() && !confirm())
            return;
        fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
        Window window = (Window) SwingUtilities.getWindowAncestor(this); // gets parent Window class reference
        if(fileChooser.showDialog(this, "Import Image") == JFileChooser.APPROVE_OPTION) { // shows import fileChooser
            try {
                window.painter.importImage(fileChooser.getSelectedFile()); // calls import method and passes the selected file
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            window.statusBar.success(fileChooser.getSelectedFile().getAbsolutePath() + " has been loaded.");
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