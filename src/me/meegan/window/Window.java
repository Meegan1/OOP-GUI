package me.meegan.window;

import me.meegan.console.Commands;
import me.meegan.paint.PaintHandler;
import me.meegan.console.Console;

import javax.swing.*;
import java.awt.event.*;

public class Window {
    public static int numberOfWindows = 0;
    JFrame frame = new JFrame("OOP GUI");
    PaintHandler painter = new PaintHandler();
    Console console = new Console();

    public Window()
    {
        numberOfWindows++;

        frame.getContentPane().add(painter);
        frame.getContentPane().add(new JScrollPane(console), "South");

        MenuBar menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



        console.addCommands(new Commands(painter));

        frame.addWindowListener(new WindowListener()); // listens for the closing event
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
