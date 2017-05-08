package me.meegan.window.paint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Extension of GraphicsPanel, controls and creates objects on the canvas.
 */
public class PaintHandler extends GraphicsPanel {
    private Pointer pointer = new Pointer(this);
    private LinkedList<PaintObject> history = new LinkedList<>();
    private LinkedList<PaintObject> redo = new LinkedList<>();
    private boolean hasChanged;

    public PaintHandler() {
        super();
        render();
        hasChanged = false; // sets initial value to false after rendering.
    }

    /**
     * Renders all objects to the graphics panel.
     */
    void render() {
        clear();
        if(history != null)
            for(PaintObject line : history)
                line.draw(image.getGraphics());

        pointer.draw(image.getGraphics()); // draws pointer last so it's always on top
        repaint();
        hasChanged = true; // has been changed after render's been called
    }

    /**
     * Creates a new canvas, resetting all objects.
     */
    public void newPaint() {
        clear();
        pointer = new Pointer(this);
        if(history != null) history.clear(); // clears history if not null
        if(redo != null) redo.clear(); // clears redo list if not null
        render();
        hasChanged = false;
    }

    /**
     * Undoes any change made to the canvas.
     */
    public void undo() {
        if(history.isEmpty())
            return;

        redo.add(history.removeLast());
        render();
    }

    /**
     * Redoes any change made to the canvas.
     */
    public void redo() {
        if(redo.isEmpty())
            return;

        history.add(redo.removeLast());
        render();
    }

    /**
     * Creates a line and adds it to the canvas.
     *
     * @param color - Color of the line.
     * @param x1 - X1 co-ord.
     * @param y1 - Y1 co-ord.
     * @param x2 - X2 co-ord.
     * @param y2 - Y2 co-ord.
     */
    void createLine(Color color, int x1, int y1, int x2, int y2) {
        history.add(new PaintLine(color, x1, y1, x2, y2));
    }

    /**
     * Creates a circle on the canvas.
     *
     * @param color - Color of the circle.
     * @param x - X co-ord.
     * @param y - Y co-ord.
     * @param r - Radius.
     * @param fill - Filled: true or false.
     */
    void createCircle(Color color, int x, int y, int r, boolean fill) {
        history.add(new PaintCircle(color, x, y, r, fill));
    }

    /**
     * Gets a color from a string.
     *
     * @param string - Name of color.
     * @return Color.
     */
    public static Color getColorFromString(String string) {
        Field field = null;
        Color color = null;
        try {
            field = Class.forName("java.awt.Color").getField(string);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert field != null;
            color = (Color) field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return color;
    }

    /**
     * Saves the project/serialized class as a .paint file
     *
     * @param file - Save file.
     * @throws IOException
     */
    public void save(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        fos.close();
        oos.close();
        hasChanged = false;
    }

    /**
     * Exports the image as a png file, cropped to the current window size.
     *
     * @param file - Export image file.
     * @throws IOException
     */
    public void exportImage(File file) throws IOException {
        BufferedImage tmp = getImageWithoutCursor().getSubimage(0, 0, getBounds().width, getBounds().height);
        ImageIO.write(tmp, "png", file);
    }

    /**
     * Loads a .paint file into the window.
     *
     * @param file - .paint file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        PaintHandler obj = (PaintHandler) ois.readObject();
        this.history = obj.history;
        this.redo = obj.redo;
        this.pointer = obj.pointer;
        this.pointer.setPanel(this);
        fis.close();
        ois.close();
        render();

        hasChanged = false;
    }

    /**
     * Imports an image into the canvas.
     *
     * @param file - Any image file.
     * @throws IOException
     */
    public void importImage(File file) throws IOException {
        history.add(new PaintImage(file, 0, 0));
        render();
    }

    // Prints the image
    public void print() {
        PrinterJob job = PrinterJob.getPrinterJob(); // creates printer job
        boolean ok = job.printDialog(); // shows settings screen
        if (ok) {
            job.setPrintable((graphics, pageFormat, pageIndex) -> { // fits image to size and prints
                if (pageIndex != 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                // get the bounds of the image
                Rectangle dim = getBounds();
                double iHeight = dim.getHeight();
                double iWidth = dim.getWidth();

                // get the bounds of the printable area
                double pHeight = pageFormat.getImageableHeight();
                double pWidth = pageFormat.getImageableWidth();

                double new_height;
                double new_width;

                //scale width to fit
                new_width = pWidth;
                //scale height to maintain aspect ratio
                new_height = (new_width * iHeight) / iWidth;

                // check if need to scale with the new height
                if (new_height > pHeight) {
                    //scale height to fit
                    new_height = pHeight;
                    //scale width to maintain aspect ratio
                    new_width = (new_height * iWidth) / iHeight;
                }

                // draws current image in view to printer
                graphics.drawImage(getImageWithoutCursor().getSubimage(0, 0, getBounds().width, getBounds().height), 0, 0, (int) new_width, (int) new_height,null);

                return Printable.PAGE_EXISTS;
            });
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The canvas image without the cursor.
     */
    private BufferedImage getImageWithoutCursor() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage image = new BufferedImage((int) screenSize.getWidth(), (int) screenSize.getHeight(), BufferedImage.TYPE_INT_RGB); // set resolution as screen resolution
        Graphics g = image.getGraphics();

        g.setColor(BACKGROUND_COL);

        g.fillRect(0, 0, image.getWidth(),  image.getHeight());
        if(history != null)
            for(PaintObject line : history)
                line.draw(image.getGraphics());
        return image;
    }

    public Pointer getPointer() {
        return pointer;
    }

    public boolean hasChanged() {
        return hasChanged;
    }
}
