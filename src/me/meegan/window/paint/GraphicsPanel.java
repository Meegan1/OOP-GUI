package me.meegan.window.paint;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Represents the graphics display panel within the turtle program. This panel contains an image which is updated to reflect user commands.
 * 
 * @author mdixon
 * 
 */
@SuppressWarnings("serial")
class GraphicsPanel extends JPanel {

	/**
	 * The default BG colour of the image.
	 */
	final static Color BACKGROUND_COL = Color.DARK_GRAY;
	
	/**
	 * The underlying image used for drawing. This is required so any previous drawing activity is persistent on the panel.
	 */
	transient BufferedImage image;
		
	/**
	 * Clears the image contents.
	 */
	void clear() {

		Graphics g = image.getGraphics();

		g.setColor(BACKGROUND_COL);

		g.fillRect(0, 0, image.getWidth(),  image.getHeight());
	}
	
	@Override
	public void paint(Graphics g) {

		// render the image on the panel.
		g.drawImage(image, 0, 0, null);
	}

/*	public void resizePanel() {
		Image tmp = Toolkit.getDefaultToolkit().createImage(image.getSource());

		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		clear();
		image.getGraphics().draw(tmp, 0, 0, null);
		this.repaint();
	}*/
	/**
	 * Constructor.
	 */
	GraphicsPanel() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		image = new BufferedImage((int) screenSize.getWidth(), (int) screenSize.getHeight(), BufferedImage.TYPE_INT_RGB); // set resolution as screen resolution
		
		// Set max size of the panel, so that is matches the max size of the image.
		setMaximumSize(new Dimension(image.getWidth(), image.getHeight()));
		setPreferredSize(new Dimension(600, 600));
		clear();
	}



}