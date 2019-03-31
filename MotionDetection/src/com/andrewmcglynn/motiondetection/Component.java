package com.andrewmcglynn.motiondetection;

/**
 * This interface provides definitions for GUI components that are
 * to be used with the Processing PApplet class. Since UI components such
 * as swing or awt components cannot be used,components must be created from
 * scratch.  
 * @author Andrew McGlynn
 *
 */
public interface Component{
	/**
	 * display method is used to display itself on a PApplet
	 */
	public void display();
	
	/**
	 * Add a component to this component. Useful for adding
	 * buttons, labels, sliders to Toolbars
	 * @param c The component to be added to this component
	 */
	public void add(Component c);
	
	/**
	 * Set the x-coordinate of this component
	 * @param x x-axis coordinate
	 */
	public void setX(int x);
	
	/**
	 * Set the y-coordinate of this component
	 * @param y y-axis coordinate
	 */
	public void setY(int y);
	
	/**
	 * Get the current x coordinate of this component
	 * @return the x-axis coordinate of this component
	 */
	public int getX();
	
	/**
	 * Get the current y coordinate of this component
	 * @return the y-axis coordinate of this component
	 */
	public int getY();
	
	/**
	 * Set the width of this component
	 * @param width the new width of the component
	 */
	public void setWidth(int width);
	
	/**
	 * Set a new height of this component
	 * @param height the new height of the component
	 */
	public void setHeight(int height);
	
	/**
	 * Get the current height of this component
	 * @return the current height
	 */
	public int getHeight();
	
	/**
	 * Get the current width of this component
	 * @return the current width
	 */
	public int getWidth();
	
	/**
	 * Method used for collision detection. Detect if an (x,y) point is
	 * contained within the bounds of this component.
	 *  
	 * @param px x-coordinate of the point 
	 * @param py y-coordinate of the point
	 * @return true if the point is contained within this component and false otherwise
	 */
	public boolean containsPoint(int px,int py);
}