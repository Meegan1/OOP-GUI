import javax.swing.*;
import java.util.LinkedList;

/**
 * Created by Jake on 15/03/2017.
 */
public class PaintHandler extends GraphicsPanel {
    public Pointer pointer = new Pointer(this);
    private LinkedList<PaintLine> lines = new LinkedList<>();

    public void render() {
        super.clear();
        pointer.draw();

        for(PaintLine line : lines) {
            line.draw();
        }
    }

    public void clear() {
        super.clear();
        pointer = new Pointer(this);
        if(lines != null) lines.clear(); // clears lines list if not null
    }

    public void createLine(int x1, int y1, int x2, int y2) {
        lines.add(new PaintLine(this, x1, y1, x2, y2));
    }


}
