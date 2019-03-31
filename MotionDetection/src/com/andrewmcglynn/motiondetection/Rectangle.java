package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;

public class Rectangle implements Shape{
	private int x;
	private int width;
	private int y;
	private int height;
	private Colour colour;
	private PApplet applet;
	
	public Rectangle(int x, int y, int width, int height, Colour c, PApplet applet){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.colour = c;
		this.applet = applet;
	}
	
	@Override
	public void display() {
		applet.noStroke();
		if(colour.getTransparency() == 0)applet.fill(colour.r,colour.g,colour.b);
		else applet.fill(colour.r,colour.g,colour.b,colour.getTransparency());
		
		applet.rect(x, y, width, height);
	}


	@Override
	public void setHeight(int height) {		this.height = height;	}
	@Override
	public void setWidth(int width) {		this.width = width;		}
	@Override
	public void setX(int x) {			this.x = x;		}
	@Override
	public void setY(int y) {			this.y = y;		}
	@Override
	public void setColour(Colour colour) {	this.colour = colour;	}
	@Override
	public int getWidth(){      return this.width;		}
	@Override
	public int getHeight(){		return this.height; 	}
}