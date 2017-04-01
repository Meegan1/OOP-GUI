import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * Created by Jake on 15/03/2017.
 */
public class Window {
    public Window()
    {
        JFrame frame = new JFrame("OOP GUI");
        PaintHandler painter = new PaintHandler();
        Console console = new Console();

        frame.getContentPane().add(painter);
        frame.getContentPane().add(new JScrollPane(console), "South");

        initializeMenuBar(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


//===========================================[Debug]==============================



                    Scanner j = new Scanner(System.in);
                    while(true) {
                        String i = j.nextLine();
                        if (i.contains("forward")) {
                            String[] k = i.split(" ");
                            painter.pointer.forward(Integer.valueOf(k[1]));
                        }
                        else if (i.contains("backward")) {
                            String[] k = i.split(" ");
                            painter.pointer.backward(Integer.valueOf(k[1]));
                        }
                        else if (i.equals("turnleft")) {
                            painter.pointer.turnLeft();
                        }
                        else if (i.equals("turnright")) {
                            painter.pointer.turnRight();
                        }
                        else if (i.equals("toggle")) {
                            painter.pointer.toggleDown();
                        }
                        else if (i.equals("clear")) {
                            painter.clear();
                        }
                    }

    }




    public static void initializeMenuBar(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

        menuBar.add(file);
        menuBar.add(help);

        //a group of JMenuItems
        JMenuItem fileNew = new JMenuItem("New", KeyEvent.VK_N);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        JMenuItem fileLoad = new JMenuItem("Load", KeyEvent.VK_L);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        JMenuItem fileSave = new JMenuItem("Save", KeyEvent.VK_S);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        JMenuItem fileExit = new JMenuItem("Exit", KeyEvent.VK_E);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

        JMenuItem helpAbout = new JMenuItem("About", KeyEvent.VK_A);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));

        // fileNew.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");




        file.add(fileNew);
        file.add(fileLoad);
        file.add(fileSave);
        file.add(fileExit);

        help.add(helpAbout);

        frame.setJMenuBar(menuBar);
    }
}
