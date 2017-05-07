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

public class PaintHandler extends GraphicsPanel implements Serializable {
    public transient Pointer pointer = new Pointer(this);
    private LinkedList<PaintObject> history = new LinkedList<>();
    private LinkedList<PaintObject> redo = new LinkedList<>();
    private boolean hasChanged;

    public PaintHandler() {
        super();
        render();
        hasChanged = false; // sets initial value to false after rendering.
    }

    void render() {
        clear();
        if(history != null)
            for(PaintObject line : history)
                line.draw(image.getGraphics());

        pointer.draw(image.getGraphics()); // draws pointer last so it's always on top
        repaint();
        hasChanged = true; // has been changed after render's been called
    }

    public void newPaint() {
        clear();
        pointer = new Pointer(this);
        if(history != null) history.clear(); // clears lines list if not null
        render();
    }

    public void undo() {
        if(history.isEmpty())
            return;

        redo.add(history.removeLast());
        render();
    }

    public void redo() {
        if(redo.isEmpty())
            return;

        history.add(redo.removeLast());
        render();
    }

    void createLine(Color color, int x1, int y1, int x2, int y2) {
        history.add(new PaintLine(color, x1, y1, x2, y2));
    }

    void createCircle(Color color, int x, int y, int r, boolean fill) {
        history.add(new PaintCircle(color, x, y, r, fill));
    }

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

    public void save(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        fos.close();
        oos.close();
        hasChanged = false;
    }

    public void exportImage(File file) throws IOException {
        BufferedImage tmp = getImageWithoutCursor().getSubimage(0, 0, getBounds().width, getBounds().height);
        ImageIO.write(tmp, "png", file);
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
        hasChanged = false;
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

    public boolean hasChanged() {
        return hasChanged;
    }
}
