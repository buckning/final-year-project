package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;

public 	class Pentagon implements Shape{
	private int x;
	private int width;
	private int y;
	private int height;
	private Colour colour;
	private PApplet applet;
	
	public Pentagon(int x, int y, int width, int height, Colour c,PApplet applet){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.colour = c;
		this.applet = applet;
	}
	
	@Override
	public void display() {
//		noStroke();
		applet.fill(colour.r,colour.g,colour.b);
		applet.quad(x+width/3,y+height,x+width*2/3,y+height,x+width/2,y+height/3,x,y+height/3);
		applet.quad(x+width/3,y+height,x+width*2/3,y+height,x+width,y+height/3,x+width/2,y+height/3);
		applet.triangle(x+2,y+height/3,x+width-2,y+height/3,x+width/2,y);
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

