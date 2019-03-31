package com.andrewmcglynn.motiondetection;

import processing.core.PApplet;

public 	class RainbowStroke implements Shape{
	private int x,y,width,height;
	private Colour currentColour,colour;
	private PApplet applet;
	private int stroke = 1;

	private Colour[] colours = {new Colour(Colour.YELLOW), new Colour(Colour.ORANGE),new Colour(Colour.RED),
			new Colour(Colour.PURPLE),new Colour(Colour.PINK),
			new Colour(Colour.GREEN),new Colour(Colour.CYAN),new Colour(Colour.BLUE)};
	private int i = 0;
	
	public RainbowStroke(int x, int y, int width, int height,PApplet applet){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.applet = applet;
		currentColour = new Colour(Colour.YELLOW);
		this.colour = currentColour;
	}
	
	@Override
	public void display() {
		currentColour = colours[i];
		applet.stroke(currentColour.r, currentColour.g, currentColour.b);
		applet.strokeWeight(this.stroke);
		applet.line(x,y,width,height);
		
		i++;
		if(i >= colours.length){
			i=0;
		}	
	}
	
	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public Colour getColour(){
		return this.colour;
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
