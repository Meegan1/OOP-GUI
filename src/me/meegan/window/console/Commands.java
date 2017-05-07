package me.meegan.window.console;

import me.meegan.window.StatusBar;
import me.meegan.window.paint.PaintHandler;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings("unused")
public class Commands implements CommandInterface {
    private PaintHandler painter;
    private StatusBar statusBar;
    public Commands(PaintHandler painter, StatusBar statusBar) {
        this.painter = painter;
        this.statusBar = statusBar;
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

    public void black() {
        painter.pointer.setColor(Color.black);
    }

    public void green() {
        painter.pointer.setColor(Color.green);
    }

    public void red() {
        painter.pointer.setColor(Color.red);
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

    public void toggle() {
        painter.pointer.toggleDown();
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

    public void filledCircle(int r) {
        painter.pointer.createCircle(r, true);
    }

    public void save(String filename) {
        try {
            painter.save(new File(filename));
            statusBar.success(filename + " has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String load(String filename) {
        try {
            painter.load(new File(filename));
            statusBar.success(filename + " has been loaded.");
        } catch (FileNotFoundException e) {
            return "ERROR: " + filename + " does not exist.";
        }catch (Exception e) {
            return "ERROR: there was an error whilst loading " + filename + ".";
        }
        return null;
    }

    public void print() {
        painter.print();
    }
}