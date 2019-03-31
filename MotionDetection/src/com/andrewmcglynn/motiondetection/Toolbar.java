package com.andrewmcglynn.motiondetection;

import java.util.ArrayList;

import processing.core.PApplet;
/**
 * Toolbar is a UI component to be used in a PApplet. It can contain other components
 * such as scrollbars, buttons, label. It uses its own layout manager that can be configured as
 * horizontal layout or vertical.  
 * 
 * @author Andrew McGlynn
 *
 */
public class Toolbar implements Component{
	ArrayList<Component> components;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int componentX;
	private int componentY;
	private Component selectedComponent;
	
	private int spacing = 10;
	private int layout = 0;
	private InputDevice inputDevice;
	private PApplet applet; 
	
	public static final int HORIZONTAL_LAYOUT = 0;
	public static final int VERTICAL_LAYOUT = 1;
	
	/**
	 * Create a Toolbar.
	 * 
	 * @param x the x-position of the toolbar
	 * @param y the y-position of the toolbar
	 * @param width the width of the toolbar
	 * @param height the height of the toolbar
	 * @param layout the layout of the toolbar. Can be <code>HORIZONTAL_LAYOUT</code> or
	 * <code>HORIZONTAL_LAYOUT</code>
	 * @param applet The PApplet that the Toolbar will be displayed on.
	 * @param inputDevice The input device to get the x,y position from.(usually mouse) 
	 */
	public Toolbar(int x,int y, int width, int height, int layout,PApplet applet,InputDevice inputDevice){
		components = new ArrayList<Component>();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		componentX = x+spacing;
		componentY = y+spacing;
		this.layout = layout;
		this.selectedComponent = null;
		this.applet = applet;
		this.inputDevice = inputDevice;
	}
	
	/**
	 * Get the selected component from the toolbar.
	 * @return The selected component
	 */
	public Component getSelectedComponent(){
		return this.selectedComponent;
	}
	
	/**
	 * Add a component to the toolbar. Component can be buttons, labels, buttons or other toolbars.
	 */
	@Override
	public void add(Component c){
		c.setX(componentX);
		c.setY(componentY);
		int temp;
		if(width > height)temp = height;
		else temp = width;
		c.setWidth(temp-spacing*2);
		c.setHeight(temp-spacing*2);
		if(layout == VERTICAL_LAYOUT)componentY += spacing+c.getHeight();
		if(layout == HORIZONTAL_LAYOUT)componentX += spacing+c.getWidth();
		components.add(c);
	}
	
	/**
	 * Display the Toolbar on a PApplet 
	 */
	@Override
	public void display(){
		
		applet.fill(130);
		applet.noStroke();
		applet.rect(this.x,this.y,this.width,this.height);
		
		//draw the shading on the borders
		for(int i = 0; i < 5; i++){
			applet.fill(180-i*20);
			applet.rect(this.x+i,this.y,1,this.height);
			applet.rect(this.x+this.width-i,this.y,1,this.height);

			applet.rect(this.x,this.y+i,this.width,1);
			applet.rect(this.x,this.y+this.height-i,this.width,1);
		}
		
		for(Component c: components){
			c.display();
			
			if(c.containsPoint(inputDevice.getX(), inputDevice.getY())){
				selectedComponent = c;
			}
			else{
				if(c instanceof Button){
					((Button) c).setSelected(false);
				}
			}
		}
	}
	/**
	 * Set the x-coordinate of this component
	 * @param x x-axis coordinate
	 */
	@Override
	public void setX(int x){
		this.x = x;
		for(Component c: components){
			c.setX(x+spacing);
		}
	}
	
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
	 * Set the y-coordinate of this component
	 * @param y y-axis coordinate
	 */
	@Override
	public void setY(int y){ this.y = y; }

	/**
	 * Get the current height of this component
	 * @return the current height
	 */
	@Override
	public int getHeight() { return this.height; }
	
	/**
	 * Get the current width of this component
	 * @return the current width
	 */
	@Override
	public int getWidth() {	return this.width;	}

	/**
	 * Set a new height of this component
	 * @param height the new height of the component
	 */
	@Override
	public void setHeight(int height) {	this.height = height;	}

	/**
	 * Set the width of this component
	 * @param width the new width of the component
	 */
	@Override
	public void setWidth(int width) {	this.width = width;		}
	public boolean containsPoint(int px,int py){
		if(px > x && px < x + this.width && py > y && py < y + this.height){
			for(Component c: this.components){
				if(c.containsPoint(px, py)){
					selectedComponent = c;
				}
			}
			return true;
	    }  
	    else{
	      return false; 
	    }
	}
		
	/**
	 * Set the component selected in the toolbar to a specific component
	 * @param selectedComponent The new selected component
	 */
	public void setSelectedComponent(Component selectedComponent) {
		this.selectedComponent = selectedComponent;
	}
}
