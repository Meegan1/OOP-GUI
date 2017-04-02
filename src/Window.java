import paint.PaintHandler;
import javax.swing.*;
import java.util.Scanner;

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


//===========================================[Debug]==============================


/*               Scanner j = new Scanner(System.in);
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
                            painter.newPaint();
                        }
                    }*/

    }
}
