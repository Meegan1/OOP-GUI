package me.meegan.paint;

import java.awt.*;

public class PaintLine {
    private int x1, y1, x2, y2;
    private Color color = Color.black;

    public PaintLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public void draw(Graphics g)
    {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
}
