package me.meegan.window.console;

import me.meegan.window.paint.PaintHandler;

import java.awt.*;

public class Commands implements CommandInterface {
    private PaintHandler painter;
    public Commands(PaintHandler painter)
    {
        this.painter = painter;
    }

    public void forward(int distance) {
        painter.pointer.forward(distance);
    }

    public void backward(int distance) {
        painter.pointer.backward(distance);
    }

    public String color(String string) {
        Color color = PaintHandler.getColorFromString(string);
        if(color == null)
            return "ERROR: color does not exist."; // print error
        painter.pointer.setColor(color);
        return null; // success
    }

    public void turnLeft() {
        painter.pointer.turnLeft();
    }

    public void turnRight() {
        painter.pointer.turnRight();
    }

    public void penUp() {
        painter.pointer.penUp();
    }

    public void penDown() {
        painter.pointer.penDown();
    }

    public void undo() {
        painter.undo();
    }

    public void redo() {
        painter.redo();
    }

    public void reset() {
        painter.newPaint();
    }

    public void circle(int r) {
        painter.pointer.createCircle(r);
    }

    public void filledcircle(int r) {
        painter.pointer.createCircle(r, true);
    }
}