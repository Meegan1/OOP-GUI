package me.meegan.window.paint;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedList;

public class PaintHandler extends GraphicsPanel implements Serializable {
    public transient Pointer pointer = new Pointer(this);
    private LinkedList<PaintObject> history = new LinkedList<>();
    private LinkedList<PaintObject> redo = new LinkedList<>();

    public PaintHandler() {
        super();
        render();
    }

    public void render() {
        clear();
        if(history != null)
            for(PaintObject line : history)
                line.draw(image.getGraphics());

        pointer.draw(image.getGraphics()); // draws pointer last so it's always on top
        repaint();
    }

    public void newPaint() {

        clear();
        pointer = new Pointer(this);
        if(history != null) history.clear(); // clears lines list if not null
        render();
        repaint();
    }

    public boolean undo() {
        if(history.isEmpty())
            return false; // Nothing to undo.

        redo.add(history.removeLast());
        render();
        return true;
    }

    public boolean redo() {
        if(redo.isEmpty())
            return false;

        history.add(redo.removeLast());
        render();
        return true;
    }

    public void createLine(Color color, int x1, int y1, int x2, int y2) {
        history.add(new PaintLine(color, x1, y1, x2, y2));
    }

    public void createCircle(Color color, int x, int y, int r, boolean fill) {
        history.add(new PaintCircle(color, x, y, r, fill));
    }

    public static Color getColorFromString(String string) {
        Field field = null;
        Color color = null;
        try {
            field = Class.forName("java.awt.Color").getField(string);
        } catch (NoSuchFieldException e) {
            return color;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            color = (Color) field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return color;
    }

    public void save(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        fos.close();
        oos.close();
    }

    public void load(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        PaintHandler obj = (PaintHandler) ois.readObject();
        this.history = obj.history;
        this.redo = obj.redo;
        render();
        fis.close();
        ois.close();
    }
}
