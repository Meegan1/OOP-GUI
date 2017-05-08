package me.meegan.window.paint;

import java.awt.*;
import java.io.Serializable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Pointer extends PaintImage implements Serializable {
	private boolean isDown = false;
	private PaintHandler panel;

	// Create pointer
	public Pointer(PaintHandler panel){
		super(Window.class.getResourceAsStream("/resources/arrow.png"), 300, 300, 15, 15); // creates the pointer
		this.panel = panel;

		rotate(180); // sets to face down
		super.setColor(Color.black); // set to black by default
	}

	/**
	 * @param panel - Sets pointer panel.
	 */
	public void setPanel(PaintHandler panel) {
		this.panel = panel;
	}

	/**
	 * Moves the pointer to this.x+x, this.y+y.
 	 */
	void move(int x, int y)
	{
		int px = getX(), py = getY();
		super.move(super.getX()+x, super.getY()+y);

		if(isDown)
			panel.createLine(getColor(), px, py, getX(), getY());

		panel.render();
	}

	/**
	 * @param distance - Distance to move forwards.
	 */
	public void forward(int distance)
	{
		double radians = Math.toRadians(getRotation());
		move((int)(distance * sin(radians)), (int)-(distance * cos(radians)));
	}

	/**
	 * @param distance - Distance to move backwards.
	 */
	public void backward(int distance)
	{
		forward(-distance);
	}

	/**
	 * Turns the pointer right.
	 */
	public void turnRight() {
		rotate(90);
		panel.render();
	}

	/**
	 * Turns the pointer left.
	 */
	public void turnLeft() {
		rotate(-90);
		panel.render();
	}

	/**
	 * Toggles whether the pen is down or not.
	 */
	public void toggleDown() {
		isDown = !isDown;
	}

	/**
	 * Sets pen to up.
	 */
	public void penUp() { isDown = false; }

	/**
	 * Sets pen to down.
	 */
	public void penDown() { isDown = true; }

	/**
	 * Sets the pen to a specific color.
	 *
	 * @param color - Pen color.
	 */
	public void setColor(Color color) {
		super.setColor(color);
		panel.render();
	}

	int getX() {
		return super.getX()+(super.getWidth()/2);
	}

	int getY() {
		return super.getY()+(super.getHeight()/2);
	}

	/**
	 * Creates a circle. Fills by default.
	 *
	 * @param r - Radius.
	 */
	public void createCircle(int r) { createCircle(r, false); } // don't fill by default
	public void createCircle(int r, boolean fill) {
		panel.createCircle(getColor(), getX(), getY(), r, fill);
		panel.render();
	}

}
