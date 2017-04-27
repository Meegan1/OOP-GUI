package me.meegan.window;

import me.meegan.paint.PaintHandler;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Console extends JTextArea {
    private PaintHandler painter;
    public Console(PaintHandler painter) {
        super(6, 0);
        this.painter = painter;
        println("Welcome!");
        ((AbstractDocument)getDocument()).setDocumentFilter(new ConsoleDocumentFilter());
        addKeyListener(new listener());
    }

    public void println(String msg) {
        append(msg + "\n");
    }

    class listener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
                String input;
                try {
                    input = getText().split("\n")[getLineCount() - 1];
                    println("");
                }
                catch(ArrayIndexOutOfBoundsException e1) {
                    return;
                }

                if (input.contains("forward")) {
                    if(input.contains(" ")){
                        String[] params = input.split(" ");
                        painter.pointer.forward(Integer.valueOf(params[1]));
                    }
                    else
                        println("USAGE: forward [distance]");
                }
                else if (input.contains("backward")) {
                    if(input.contains(" ")){
                        String[] params = input.split(" ");
                        painter.pointer.backward(Integer.valueOf(params[1]));
                    }
                    else
                        println("USAGE: forward [distance]");
                }
                else if (input.equals("turnleft")) {
                    painter.pointer.turnLeft();
                }
                else if (input.equals("turnright")) {
                    painter.pointer.turnRight();
                }
                else if (input.equals("penup")) {
                    painter.pointer.penUp();
                }
                else if (input.equals(("pendown"))) {
                    painter.pointer.penDown();
                }
                else if (input.equals("undo")) {
                    painter.undo();
                }
                else if (input.equals("clear")) {
                    painter.newPaint();
                }
                else {
                    println("ERROR: That command does not exist.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public class ConsoleDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (offset >= getLineStartOffset(getLineCount()-1)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset >= getLineStartOffset(getLineCount()-1)) {
                super.remove(fb, offset, length);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (offset >= getLineStartOffset(getLineCount()-1)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
