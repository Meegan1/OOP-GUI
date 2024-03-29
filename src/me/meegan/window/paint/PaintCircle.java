package me.meegan.window.paint;

import java.awt.*;
import java.io.Serializable;

@SuppressWarnings("unused")
public class PaintCircle implements PaintObject, Serializable {
    private boolean fill;
    private int x, y, r;
    private Color color;

    public PaintCircle(Color color, int x, int y, int r, boolean fill) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.r = r;
        this.fill = fill;
    }

    protected PaintCircle() {} // Used for serialization

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if(fill)
            g.fillOval(x-r, y-r, r*2, r*2);
        else
            g.drawOval(x-r, y-r, r*2, r*2);
    }
}
