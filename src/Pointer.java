import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Pointer extends PaintImage {
	private boolean isDown = false;
	private PaintHandler panel;

	// Create pointer
	public Pointer(PaintHandler panel){
		super(panel, "arrow.png", 50, 50, 15, 15); // creates the pointer
		this.panel = panel;
		setRotation(180); // sets to face down
		draw(); // draws to panel
	}

	// Moves the pointer this.x+x, this.y+y
	public void move(int x, int y)
	{
		int px = getX(), py = getY();
		super.move(super.getX()+x, super.getY()+y);

		if(isDown)
			panel.createLine(px, py, getX(), getY());

		panel.render();
	}

	public void forward(int distance)
	{
		double radians = Math.toRadians(getRotation());
		move((int)(distance * sin(radians)), (int)-(distance * cos(radians)));
	}

	public void backward(int distance)
	{
		forward(-distance);
	}

	public void turnRight() {
		setRotation(90);
		panel.render();
	}

	public void turnLeft() {
		setRotation(-90);
		panel.render();
	}

	public void toggleDown() {
		isDown = !isDown;
	}

	public int getX() {
		return super.getX()+(super.getWidth()/2);
	}

	public int getY() {
		return super.getY()+(super.getHeight()/2);
	}

}
