package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * The Button class is a GUI component which can be used in by a
 * Processing PApplet.  
 * 
 * @author Andrew McGlynn
 */
public 	class Button implements Component{
	private Colour colour;
	private PFont font;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean hasImage;
	private boolean hasText;
	private PImage img;
	private String text;
	private boolean selected = false;
	private PApplet applet;
	
	/**
	 * Construct a new Button.
	 * It's default size is 30x30 with a
	 * grey background. 
	 *  
	 * @param applet The Processing draw applet
	 */
	public Button(PApplet applet){
		this.height = 30;
		this.width = 30;
		hasImage = false;
		hasText = false;
		colour = new Colour(150,150,150);
		this.text = "";
		this.applet = applet;
	}
	
	/**
	 * Construct a Button with a specified colour.
	 * Button will be displayed as a colour box with an
	 * outline.
	 * 
	 * @param c Colour of the Button
	 * @param applet Processing draw applet
	 */
	public Button(Colour c, PApplet applet){
		this.height = 30;
		this.height = 30;
		hasImage = false;
		hasText = false;
		colour = c;
		this.text = "";
		this.applet = applet;
	}
	
	/**
	 * Constructs a Button that displays text.
	 * The Button will be displayed as a box of 30x30
	 * with the passed text in it and an outline. 
	 * 
	 * @param text The display text
	 * @param applet the processing draw applet
	 */
	public Button(String text,PApplet applet){
		this.height = 30;
		this.height = 30;
		hasImage = false;
		hasText = true;
		colour = new Colour(150,150,150);
		this.applet = applet;
		this.text = text;
		this.font = applet.createFont( "Arial",16,true);
	}
	
	/**
	 * Construct a Button that displays an image.
	 * The Button will display the image with an
	 * outline around it.
	 * 
	 * @param img The PImage that will be displayed
	 * @param applet The Processing draw applet
	 */
	public Button(PImage img, PApplet applet){
		
		this.height = 30;
		this.height = 30;
		this.hasText = false;
		if(img != null){
			hasImage = true;
			this.img = img;
		}
		else{
			hasImage = false;
		}
		this.text = "";
		this.applet = applet;
	}
	
	/**
	 * Should not be used for Button
	 * @param c The component to be added to this component
	 */
	@Override
	public void add(Component c) {}

	/**
	 * display method is used to display this button instance on a PApplet
	 */
	@Override
	public void display() {
		applet.strokeWeight(1);
		if(hasImage){
			
			applet.image(this.img,this.x,this.y,this.width,this.height);
			
		}
		else if(hasText){
			applet.fill(0);
			applet.textFont(font,16);
			applet.text(text,x+2,y+height/2);	
		}
		else{
			applet.fill(colour.r,colour.g,colour.b);
			applet.rect(this.x,this.y,this.width,this.height);
		}

		applet.noFill();
		if(selected){
			applet.stroke(255);
		}
		else{
			applet.stroke(0);
		}
		
		applet.rect(this.x,this.y,this.width,this.height);
		
	}
	
	/**
	 * Check if this Button is selected
	 * @return Returns true if selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Set this Button selected. 
	 * @param selected true sets the Button selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Set the Colour of this Button 
	 * @param c the new Colour of this Button
	 */
	public void setColour(Colour c){
		this.colour = c;
	}
	
	/**
	 * Get the current Colour of this Button
	 * @return The Colour information
	 */
	public Colour getColour(){
		return this.colour;		
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
	 * @param x cartesian x axis coordinate
	 */
	@Override
	public void setX(int x) {	this.x = x;		}
	
	/**
	 * Set the y-coordinate of this component
	 * @param y cartesian y axis coordinate
	 */
	@Override
	public void setY(int y) {	this.y = y;		}
	
	/**
	 * Get the current x coordinate of this component
	 * @return the x-axis coordinate of this component
	 */
	@Override
	public int getX(){	return this.x;	}
	
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
