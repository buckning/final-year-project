package com.andrewmcglynn.motiondetection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Loader class is used to display a Loading graphic that animates. It starts its own animation
 * thread and runs until the <code>stop()</code> is called.
 *  
 * @author Andrew McGlynn
 *
 */
public class Loader extends JFrame implements Runnable{
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 200;
	private static final int GRAPHIC_X = FRAME_WIDTH/3;
	private static final int GRAPHIC_Y = 50;
	private boolean running = true;
	private JPanel displayPanel;
	private BufferedImage image;
	
	/**
	 * Create a Loader and start it animating
	 */
	public Loader(){
		super();
		image = loadImage("images/loading_graphics.png");
		
		displayPanel = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -7034227501890410757L;

			public void paintComponent(Graphics g){
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
				g.setColor(Color.black);
				g2.drawString("Loading...", FRAME_WIDTH/3, 10);
				g2.drawImage(image, GRAPHIC_X, GRAPHIC_Y, null);
				g2.dispose();
			}
		};
		setLocation(500, 300);
		add(displayPanel);
		setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * Run the animation
	 */
	@Override
	public void run(){
		while(running){
			image = rotateImage(image, -1);
			repaint();
			try{
				Thread.sleep(100);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Stop the animation and dispose of the display frame
	 */
	public void stop(){
		running = false;
		dispose();
	}
	

	/**
	 * Rotate a buffered image
	 * @param image the image to be rotated
	 * @param theta the angle the image is to be rotated
	 * @return a new buffered image which is rotated
	 */
	private BufferedImage rotateImage(BufferedImage image, double theta){
		
		BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)rotatedImage.getGraphics();
				
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
		double x1 = image.getWidth()/2;
		double y1 = image.getHeight()/2;
		at.rotate(theta, x1, y1);
		g.drawImage(image, at, null);
		g.dispose();
		return rotatedImage;
	}
	
	/**
	 * Load a Buffered Image from a file
	 * @param file The file location
	 * @return a buffered image of the image file
	 */
    public BufferedImage loadImage(String file){
		URL url=null;
        url = getClass().getClassLoader().getResource(file);
        System.out.print("Loading: "+url.getPath() +"...\n" );
		BufferedImage image = null;

		try{
//			image = ImageIO.read(new File(url.getPath()));
			image = ImageIO.read(ClassLoader.getSystemResource(file));
            System.out.println("Loaded successfully");
		}
		catch(Exception ex){
            System.out.println("image load failed: "+url.getPath());
			ex.printStackTrace();
		}
		return image;
	}	
}
