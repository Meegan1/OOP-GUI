package paint;

import java.util.LinkedList;

public class PaintHandler extends GraphicsPanel {
    public Pointer pointer = new Pointer(this);
    private LinkedList<PaintLine> lines = new LinkedList<>();

    public PaintHandler() {
        super();
        render();
    }

    public void render() {
        clear();
        pointer.draw(image.getGraphics());
        if(lines != null)
            for(PaintLine line : lines)
                line.draw(image.getGraphics());
        repaint();
    }

    public void newPaint() {

        clear();
        pointer = new Pointer(this);
        if(lines != null) lines.clear(); // clears lines list if not null
        render();
        repaint();
    }

    public void createLine(int x1, int y1, int x2, int y2) {
        lines.add(new PaintLine(x1, y1, x2, y2));
    }


}
