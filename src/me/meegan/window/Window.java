package me.meegan.window;

import me.meegan.paint.PaintHandler;
import me.meegan.window.Console;
import me.meegan.window.MenuBar;

import javax.swing.*;

public class Window {
    public Window()
    {
        JFrame frame = new JFrame("OOP GUI");
        PaintHandler painter = new PaintHandler();
        Console console = new Console(painter);

        frame.getContentPane().add(painter);
        frame.getContentPane().add(new JScrollPane(console), "South");

        MenuBar menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
