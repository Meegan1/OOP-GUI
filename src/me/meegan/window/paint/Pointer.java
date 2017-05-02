package me.meegan.window.paint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Pointer extends PaintImage {
	private boolean isDown = false;
	private PaintHandler panel;

	// Create pointer
	public Pointer(PaintHandler panel){
		super(Window.class.getResourceAsStream("/resources/arrow.png"), 50, 50, 15, 15); // creates the pointer
		this.panel = panel;

		rotate(180); // sets to face down
		super.setColor(Color.black); // set to black by default
	}

	// Moves the pointer this.x+x, this.y+y
	public void move(int x, int y)
	{
		int px = getX(), py = getY();
		super.move(super.getX()+x, super.getY()+y);

		if(isDown)
			panel.createLine(getColor(), px, py, getX(), getY());

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
		rotate(90);
		panel.render();
	}

	public void turnLeft() {
		rotate(-90);
		panel.render();
	}

	public void toggleDown() {
		isDown = !isDown;
	}

	public void penUp() { isDown = false; }
	public void penDown() { isDown = true; }

	public void setColor(Color color) {
		super.setColor(color);
		panel.render();
	}

	public int getX() {
		return super.getX()+(super.getWidth()/2);
	}

	public int getY() {
		return super.getY()+(super.getHeight()/2);
	}

	public void createCircle(int r) { createCircle(r, false); } // don't fill by default
	public void createCircle(int r, boolean fill) {
		panel.createCircle(getColor(), getX(), getY(), r, fill);
		panel.render();
	}

}
