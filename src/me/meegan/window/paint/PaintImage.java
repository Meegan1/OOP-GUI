package me.meegan.window.paint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PaintImage implements PaintObject {
    private BufferedImage img;
    private int x, y,  rotation, width, height;
    private Color color;


    public PaintImage(InputStream file, int x, int y, int width, int height) {
        initialize(file, x, y, width, height);
    }

    public PaintImage(File file, int x, int y, int width, int height) {
        initialize(file, x, y, width, height);
    }

    private void initialize(Object file, int x, int y, int width, int height) {
        img = null;
        this.x = x;
        this.y = y;
        try {
            if(file instanceof InputStream)
                img = ImageIO.read((InputStream) file);
            else if(file instanceof File)
                img = ImageIO.read((File) file);
        } catch (IOException ignored) {
        }
        resize(width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }


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

    @SuppressWarnings("unused")
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
}
