package com.andrewmcglynn.motiondetection;

/**
 * Input device is a Generic object that stores information such as 
 * x,y position and previous x,y position. A program would then only 
 * refer to the hardware in one specific place and use the InputDevice 
 * everywhere else. 
 * 
 * @author Andrew McGlynn
 *
 */
public class InputDevice {
	private int x;
	private int y;
	private int px;
	private int py;
	
	/**
	 * Construct an InputDevice with position (0,0).
	 */
	public InputDevice(){
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Construct an InputDevice with position (x,y).
	 */
	public InputDevice(int x, int y){
		this.px = x;
		this.py = y;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the x-position of the InputDevice
	 * @param x x-position
	 */
	public void setX(int x){
		this.px = this.x;
		this.x = x;
	}
	
	/**
	 * Get the current x-position of the InputDevice
	 * @return the x-position
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Get the current y-position of the InputDevice
	 * @return the y-posiiton
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Get the previous x-position of the InputDevice
	 * @return the previous x-position
	 */
	public int getPreviousX(){
		return px;
	}
	
	/**
	 * Get the previous y-position of the InputDevice
	 * @return the previous y-position
	 */
	public int getPreviousY() {
		return py;
	}
	
	/**
	 * Set the y-position of the InputDevice
	 * @param y y-position
	 */
	public void setY(int y) {
		this.py = this.y;
		this.y = y;
	}
	
}
