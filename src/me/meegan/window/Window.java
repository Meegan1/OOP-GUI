package me.meegan.window;

import me.meegan.window.console.Commands;
import me.meegan.window.paint.PaintHandler;
import me.meegan.window.console.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    private static int numberOfWindows = 0; // number of current windows open.
    PaintHandler painter = new PaintHandler();
    private MenuBar menuBar = new MenuBar();
    StatusBar statusBar = new StatusBar();

    public Window()
    {
        super("OOP GUI");
        numberOfWindows++;

        getContentPane().add(painter);

        JPanel bottomPanel = new JPanel();
        getContentPane().add(bottomPanel, "South");
        bottomPanel.setLayout(new BorderLayout());

        Console console = new Console();
        bottomPanel.add(new JScrollPane(console));
        bottomPanel.add(statusBar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);

        console.addCommands(new Commands(painter, statusBar)); // sets the CommandInterface class to Commands

        addWindowListener(new WindowListener()); // listens for the closing event
    }


    class WindowListener extends WindowAdapter {
        /**
         * Invoked when a window is closing.
         *
         * @param e
         */
        @Override
        public void windowClosing(WindowEvent e) {
            if (menuBar.hasChanged() && (!menuBar.hasChanged() || !menuBar.confirm())) return;

            numberOfWindows--;
            if (numberOfWindows == 0)
                System.exit(0); // if no windows, exit program.
            dispose(); // disposes of window
        }
    }
}
