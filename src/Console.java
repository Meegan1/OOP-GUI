import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * Created by Jake on 01/04/2017.
 */
public class Console extends JTextArea {
    private LinkedList<String> input = new LinkedList<>();
    public Console() {
        super(6, 0);
        println("Welcome!");
        addKeyListener(new listener());
    }

    public void println(String msg) {
        this.append(msg + "\n");
    }

    class listener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}
