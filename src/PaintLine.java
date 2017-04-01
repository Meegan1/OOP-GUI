import java.awt.*;

/**
 * Created by Jake on 15/03/2017.
 */
public class PaintLine {
    private GraphicsPanel panel;
    private int x1, y1, x2, y2;

    public PaintLine(GraphicsPanel panel, int x1, int y1, int x2, int y2) {
        this.panel = panel;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

    }

    public void draw()
    {
        panel.drawLine(Color.black, x1, y1, x2, y2);
    }
}
