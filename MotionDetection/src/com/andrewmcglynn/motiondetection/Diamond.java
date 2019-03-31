package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;

public class Diamond implements Shape{
	
	private int x;
	private int width;
	private int y;
	private int height;
	private Colour colour;
	private PApplet applet;
	
	public Diamond(int x, int y, int width, int height, Colour c, PApplet applet){
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
		applet.fill(colour.r,colour.g,colour.b);
		//push and pop matrix only allow the one shapes in between them to be transformed
		applet.pushMatrix();
		//draw a rectangle but rotate it to look like a diamond
		applet.translate(x,y);
		applet.rotate(PApplet.PI/4);
		applet.rect(0,0,width,height);
		applet.popMatrix();
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
