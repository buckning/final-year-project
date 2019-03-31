package com.andrewmcglynn.motiondetection;

/**
 * The Colour class is a class that can be used in a PApplet. The Colour can handle transparency.
 * 
 * @author Andrew McGlynn
 *
 */
public class Colour {
	
	/**
	 * the red component of the colour
	 */
	public int r; 
	
	/**
	 * the green component of the colour
	 */
	public int g; 
	
	/**
	 * the blue component of the colour
	 */
	public int b;
	
	/**
	 * The transparency of the Colour
	 */
	private int transparency;
	
	/**
	 * integer value of the colour red
	 */
	public static final int RED = 0xff0000;
	
	/**
	 * integer value of the colour green
	 */
	public static final int GREEN = 0x00ff00;
	
	/**
	 * integer value of the colour blue
	 */
	public static final int BLUE = 0x0000ff;
	
	/**
	 * integer value of the colour black
	 */
	public static final int BLACK = 0x000000;
	
	/**
	 * integer value of the colour yellow 
	 */
	public static final int YELLOW = 0xffff00;
	
	/**
	 * integer value of the colour white
	 */
	public static final int WHITE = 0xffffff;
	
	/**
	 * integer value of the colour gray
	 */
	public static final int GRAY = 0x969696;
	
	/**
	 * integer value of the colour light gray
	 */
	public static final int LIGHT_GRAY = 0xC0C0C0;
	
	/**
	 * integer value of the colour purple
	 */
	public static final int PURPLE = 0xff00ff;
	
	/**
	 * integer value of the colour cyan
	 */
	public static final int CYAN = 0x00ffff;
	
	/**
	 * integer value of the colour brown
	 */
	public static final int BROWN = 0x6C3D02;
	
	/**
	 * integer value of the colour orange
	 */
	public static final int ORANGE = 0xB03800;
	
	/**
	 * integer value of the colour pink
	 */
	public static final int PINK = 0xffafaf;
	
	/**
	 * integer value of the colour dark gray
	 */
	public static final int DARK_GRAY = 0x404040;
	
	/**
	 * Construct a Colour. Colour has no transparency on default.
	 * Can be Constructed by using some of the preset Colour values.
	 * <br><code>
	 * 		Colour c = new Colour(Colour.BLUE);
	 * </code>
	 * 
	 * @param rgb the RGB value of the colour.
	 */
	  public Colour(int rgb){
		  this.r = (0x00ff0000 & rgb) >> 16;
		  this.g =(0x0000ff00 & rgb) >> 8;    	
		  this.b = (0x000000ff & rgb);
		  this.transparency  = 0;
	  }
	  /**
	   * Contruct a Colour. Colour has no transparency on default.
	   * @param r The red component of the colour
	   * @param g The green component of the colour
	   * @param b The blue component of the colour
	   */
	  public Colour(int r, int g, int b){
		  this.r = r;
		  this.g = g;
		  this.b = b;
		  this.transparency  = 0;
	  }
	  /**
	   * Change the colour of the Colour object
	   * @param rgb the new Colour
	   */
	  public void setColour(int rgb){
		  this.r = (0x00ff0000 & rgb) >> 16;
		  this.g =(0x0000ff00 & rgb) >> 8;
		  this.b = (0x000000ff & rgb);
		  this.transparency  = 0;
	  }
	  
	  /**
	   * Get the current colour of the colour object
	   * @return The current colour.
	   */
	  public int getColour(){
		  
		  int temp = 0;
		  
		  temp += this.r << 16;
		  temp += this.g << 8;
		  temp += this.b;
		  
		  return temp;
	  }
	  
	  /**
	   * Set the transparency of the colour.
	   * @param transparency The transparency of the colour.
	   */
	  public void setTransparency(int transparency){
		  this.transparency = transparency;
	  }
	  
	  /**
	   * Get the transparency level of this colour.
	   * @return the transparency level
	   */
	  public int getTransparency(){
		  return this.transparency;
	  }
	  
	  /**
	   * Check if another colour is the same as this colour.
	   */
	  @Override
	  public boolean equals(Object object){
		Colour otherColour = (Colour)object;
		
		return (otherColour.getColour()== this.getColour()?true:false);
	  }
}
