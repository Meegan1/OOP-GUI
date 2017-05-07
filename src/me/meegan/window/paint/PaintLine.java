package me.meegan.window.paint;

import java.awt.*;
import java.io.Serializable;

@SuppressWarnings("unused")
public class PaintLine implements PaintObject, Serializable {
    private int x1, y1, x2, y2;
    private Color color = Color.black;

    public PaintLine(Color color, int x1, int y1, int x2, int y2) {
        this.color = color;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    protected PaintLine() {} // Used for serialization

    public void draw(Graphics g)
    {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
}
