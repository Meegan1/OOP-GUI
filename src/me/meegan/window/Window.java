package me.meegan.window;

import me.meegan.window.console.Commands;
import me.meegan.window.paint.PaintHandler;
import me.meegan.window.console.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    private static int numberOfWindows = 0;
    protected PaintHandler painter = new PaintHandler();
    private Console console = new Console();
    private MenuBar menuBar = new MenuBar();
    protected StatusBar statusBar = new StatusBar();

    private JPanel bottomPanel = new JPanel();

    public Window()
    {
        super("OOP GUI");
        numberOfWindows++;

        getContentPane().add(painter);
        getContentPane().add(bottomPanel, "South");

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(new JScrollPane(console));
        bottomPanel.add(statusBar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);


        console.addCommands(new Commands(painter));

        addWindowListener(new WindowListener()); // listens for the closing event
    }


    class WindowListener extends WindowAdapter {
        /**
         * Invoked when a window has been closed as the result
         * of calling dispose on the window.
         *
         * @param e
         */
        @Override
        public void windowClosed(WindowEvent e) {
            numberOfWindows--;
            if(numberOfWindows == 0)
                System.exit(0); // if no windows, exit program.
        }
    }
}
