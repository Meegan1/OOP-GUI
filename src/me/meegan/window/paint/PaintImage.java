package me.meegan.window.paint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;

public class PaintImage implements PaintObject, Serializable {
    private transient BufferedImage img;
    private int x, y,  rotation, width, height;
    private Color color;

    /**
     * Creates the image from an InputStream.
     *
     * @param file - Resource file.
     * @param x - X co-ord.
     * @param y - Y co-ord.
     * @param width - Width.
     * @param height - Height.
     */
    public PaintImage(InputStream file, int x, int y, int width, int height) {
        try {
            initialize(file, x, y, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the image from a File. With either default width and height or set by params.
     *
     * @param file - File.
     * @param x - X co-ord.
     * @param y - Y co-ord.
     * @throws IOException
     */
    public PaintImage(File file, int x, int y) throws IOException { this(file, x, y, 0, 0); }
    public PaintImage(File file, int x, int y, int width, int height) throws IOException {
        initialize(file, x, y, width, height);
    }

    /**
     * Creates the image from an InputStream or File and resizes if a width and height is given.
     *
     * @param file - InputStream or File.
     * @param x - X co-ord.
     * @param y - Y co-ord.
     * @param width - Width.
     * @param height - Height.
     * @throws IOException
     */
    private void initialize(Object file, int x, int y, int width, int height) throws IOException {
        img = null;
        this.x = x;
        this.y = y;

        if(file instanceof InputStream)
            img = ImageIO.read((InputStream) file);
        else if(file instanceof File)
            img = ImageIO.read((File) file);

        if(width != 0 && height != 0) // if no width and height given, use normal size as (0, 0) would be invisible
            resize(width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }


    /**
     * Resizes the current image to the given parameters.
     *
     * @param width - Width of the new size.
     * @param height - Height of the new size.
     */
    private void resize(int width, int height) {
        this.width = width;
        this.height = height;


        Image tmp = img.getScaledInstance(width, height, 2);
        BufferedImage tmpBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tmpG2 = tmpBuffer.createGraphics();
        tmpG2.drawImage(tmp, 0, 0, null);
        tmpG2.dispose();

        img = tmpBuffer;
    }

    /**
     * Moves the image to the given parameters.
     *
     * @param x - X co-ord.
     * @param y - Y co-ord.
     */
    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void rotate(int rotation) {
        this.rotation += (this.rotation+rotation >= 0 && this.rotation+rotation < 360) ? rotation : (this.rotation+rotation >= 0) ? rotation-360 : rotation+360; // sets rotation between 0-359

        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotation), img.getWidth(null) / 2, img.getHeight(null) / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        img = op.filter(img, null);
    }

    /**
     * Changes all pixels in the image to the given color (excluding transparent pixels).
     * Used for the pointer.
     *
     * @param color - Image color.
     */
    void setColor(Color color) {
        WritableRaster raster = img.getRaster();
        for (int xx = 0; xx < getWidth()-1; xx++) {
            for (int yy = 0; yy < getHeight()-1; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
        this.color = color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getRotation() {
        return rotation;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Color getColor() { return color; }

    // ============= Serialization ================

    /**
     * Allows the image to be serialized.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(img, "png", out);
    }

    /**
     * Used for reading the serialized image.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        img = ImageIO.read(in);
    }
}
