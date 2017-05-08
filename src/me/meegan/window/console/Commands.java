package me.meegan.window.console;

import me.meegan.window.StatusBar;
import me.meegan.window.paint.PaintHandler;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * All commands are listed in here. Console uses reflection to execute the functions.
 * Methods can print a message via returning a string.
 */
@SuppressWarnings("unused")
public class Commands implements CommandInterface {
    private PaintHandler painter;
    private StatusBar statusBar;
    public Commands(PaintHandler painter, StatusBar statusBar) {
        this.painter = painter;
        this.statusBar = statusBar;
    }

    public void forward(int distance) {
        painter.getPointer().forward(distance);
    }

    public void backward(int distance) {
        painter.getPointer().backward(distance);
    }

    public String color(String string) {
        Color color = PaintHandler.getColorFromString(string);
        if(color == null)
            return "ERROR: color does not exist."; // print error
        painter.getPointer().setColor(color);
        return null; // success
    }

    public void black() {
        painter.getPointer().setColor(Color.black);
    }

    public void green() {
        painter.getPointer().setColor(Color.green);
    }

    public void red() {
        painter.getPointer().setColor(Color.red);
    }

    public void turnLeft() {
        painter.getPointer().turnLeft();
    }

    public void turnRight() {
        painter.getPointer().turnRight();
    }

    public void penUp() {
        painter.getPointer().penUp();
    }

    public void penDown() {
        painter.getPointer().penDown();
    }

    public void toggle() {
        painter.getPointer().toggleDown();
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
        painter.getPointer().createCircle(r);
    }

    public void filledCircle(int r) {
        painter.getPointer().createCircle(r, true);
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