package com.andrewmcglynn.motiondetection;
/**
 * This interface provides definitions for displayable
 * shapes. The shape must be able to display itself and
 * provide basic accessors and mutators to the shape.
 * This interface was designed to be used when for main
 * application that uses the Processing PApplet class.
 *   
 * @author Andrew McGlynn
 *
 */
public interface Shape {
	
	/**
	 * Display a Shape.
	 */
	public void display();
	
	/**
	 * Set the x-position of the shape 
	 * @param x x-position
	 */
	public void setX(int x);
	
	/**
	 * Set the y-position of the shape 
	 * @param y y-position
	 */
	public void setY(int y);
	
	/**
	 * Get the height of the shape 
	 * @return the height
	 */
	public int getHeight();
	
	/**
	 * Get the width of the shape 
	 * @return the width
	 */
	public int getWidth();
	
	/**
	 * Set the width of the shape
	 * @param width the new width
	 */
	public void setWidth(int width);
	
	/**
	 * Set the height of the shape
	 * @param height the new height
	 */
	public void setHeight(int height);
	
	/**
	 * Set the Colour of the shape
	 * @param c the new Colour
	 */
	public void setColour(Colour c);
}
