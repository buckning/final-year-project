package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;
import processing.core.PFont;
/**
 * Label is a Graphical User Interface(GUI) Component that can be used
 * in a Processing PApplet. It displays text and has an x,y position and
 * width, height size.  
 * 
 * @author Andrew McGlynn
 *
 */
public class Label implements Component{
	private PFont font; 
	private int x;
	private int y;
	private int width;
	private int height;
	private String message;
	private PApplet applet;
	
	/**
	 * Create a Label for use in a Processing applet.
	 * 
	 * @param s The text displayed in the Label
	 * @param applet The draw applet
	 */
	public Label(String s,PApplet applet){
		this.height = 30;
		this.height = 30;
		this.message = s;
		this.applet = applet;
		this.font = this.applet.createFont( "Arial",16,true);
	}
	
	/**
	 * Not used for Label class
	 */
	@Override
	public void add(Component c) {}

	/**
	 * display method is used to display the Label on a PApplet
	 */
	@Override
	public void display() {
		applet.fill(0);
		applet.textFont(font,16);
		applet.text(message,x,y+height/2);
	}
	
	/**
	 * Set the display text of the PApplet
	 * @param text the display text
	 */
	public void setText(String text){
		this.message = text;
	}

	/**
	 * Get the current height of this component
	 * @return the current height
	 */
	@Override
	public int getHeight() {	return this.height;		}
	
	/**
	 * Get the current width of this component
	 * @return the current width
	 */
	@Override
	public int getWidth() {		return this.width;		}
	
	/**
	 * Set the x-coordinate of this component
	 * @param x x axis coordinate
	 */
	@Override
	public void setX(int x) {	this.x = x;		}
	
	/**
	 * Set the y-coordinate of this component
	 * @param y y axis coordinate
	 */
	@Override
	public void setY(int y) {	this.y = y;		}
	
	/**
	 * Get the current x coordinate of this component
	 * @return the x-axis coordinate of this component
	 */
	@Override
	public int getX(){	return this.y;	}
	
	/**
	 * Get the current y coordinate of this component
	 * @return the y-axis coordinate of this component
	 */
	@Override
	public int getY(){	return this.y;	}
	
	/**
	 * Set the width of this component
	 * @param width the new width of the component
	 */
	@Override
	public void setWidth(int width){	this.width = width;		}
	
	/**
	 * Set a new height of this component
	 * @param height the new height of the component
	 */
	@Override
	public void setHeight(int height){	this.height = height;	}
	
	/**
	 * Method used for collision detection. Detect if an (x,y) point is
	 * contained within the bounds of this component.
	 *  
	 * @param px x-coordinate of the point 
	 * @param py y-coordinate of the point
	 * @return true if the point is contained within this component and false otherwise
	 */
	@Override
	public boolean containsPoint(int px,int py){
		if(px > x && px < x + this.width && py > y && py < y + this.height){
	      return true;
	    }  
	    else{
	      return false; 
	    }
	}
}

