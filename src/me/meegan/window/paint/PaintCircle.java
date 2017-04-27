package me.meegan.window.paint;

import java.awt.*;

public class PaintCircle implements PaintObject {
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

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if(fill)
            g.fillOval(x-r, y-r, r*2, r*2);
        else
            g.drawOval(x-r, y-r, r*2, r*2);
    }
}
