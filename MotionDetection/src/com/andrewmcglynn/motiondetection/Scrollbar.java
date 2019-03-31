package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;

/**
 * Scrollbar is a UI object to be used with a PApplet. 
 * It consists of a slider and a base. The slider's value changes 
 * depending on its x position in the base. 
 * 
 * @author Andrew McGlynn
 *
 */
public class Scrollbar implements Component{ 
	private int x;
	private int y;
	private int width;
	private int height;
	private int sliderPosition;  
	private PApplet applet;
	private InputDevice inputDevice;
	private static final int SLIDER_WIDTH = 10;
	boolean selected;
	  
	/**
	 * Create a Scrollbar for use in a PApplet.
	 * 
	 * @param x x-coordinate of the scrollbar
	 * @param y y-coordinate of the scrollbar
	 * @param width the width of the scrollbar
	 * @param height the height of the scrollbar
	 * @param applet the applet that the scrollbar will be displayed on
	 * @param inputDevice The input device to get the x,y position from.(usually mouse)
	 */
   public Scrollbar(int x, int y, int width, int height,PApplet applet, InputDevice inputDevice){
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    sliderPosition = this.width/2;
	    selected = false;  
	    this.applet = applet;
	    this.inputDevice = inputDevice;
	  }
   	
   		/**
   		 * Display method. Used to display the scrollbar on the PApplet
   		 */
	  @Override
	  public void display(){
	    applet.strokeWeight(1);
	    applet.stroke(0);
	    applet.line(x+10,height/2,x+width-10,height/2);  
	    applet.fill(255,255,0);
	    if(containsPoint(inputDevice.getX(),inputDevice.getY())){
	      applet.fill(255,0,0);
	      sliderPosition = inputDevice.getX();
	      //make sure the slider is not greater than the boundaries
	      if(sliderPosition > x+width-SLIDER_WIDTH) sliderPosition = x+width-SLIDER_WIDTH;
	    }  
	    
	    if(sliderPosition > x+width-10){
	      sliderPosition = x+width-10;
	    
	    }
	    applet.rect(sliderPosition,height/2-10,SLIDER_WIDTH,20);
	  }
	  
	  /**
	   * Returns the value that the scrollbar slider is pointing at. It returns an integer
	   * from 0-100
	   * @return The value that the slider is at.
	   */
	  public int getSliderValue(){
		  double sliderPos = (double)sliderPosition; 
		  //get the percentage of the slider, 0 if slider is at the left, 100 if slider is at the right
		  double percentage = ((sliderPos-x)/(width-SLIDER_WIDTH) *100);
	    
		  return (int)percentage;  
	  }
	  
	/**
	 * Add a component to this component. 
	 * Should not be used in the scrollbar class
	 * @param c The component to be added to this component
	 */
	@Override
	public void add(Component c) {}		//not used
	
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
	 * Set the x-coordinate of this component
	 * @param x x-axis coordinate
	 */
	@Override
	public void setX(int x) {	this.x = x;		}
	
	/**
	 * Set the y-coordinate of this component
	 * @param y y-axis coordinate
	 */
	@Override
	public void setY(int y) {	this.y = y;		}
	
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
	 * Method to detect if a the scrollbar contains a point. Detect if an (x,y) point is
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
