package com.andrewmcglynn.motiondetection;

import java.awt.Robot;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import processing.core.PApplet;
import processing.core.PImage;
import wiiremotej.IRLight;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;
import wiiremotej.event.WRIREvent;
import wiiremotej.event.WiiRemoteAdapter;

public class DrawingApplication extends PApplet{
	//image sources http://www.iconarchive.com/show/circus-icons-by-fixicon/Magic-icon.html
	/**
	 * 
	 */
	private Driver loader;
	private static final long serialVersionUID = 1L;
	private ArrayList<Component> toolbars;
	private RainbowStroke rainbowStroke;
	private TransparentRainbowStroke transparentRainbowStroke;
	private Robot robot;
	private Toolbar toolbar;
	private Toolbar colorToolbar;
	private Toolbar paintbrushToolbar;
	private Toolbar eraserToolbar;
	private Toolbar lineToolbar;
	private Toolbar effectsToolbar;
	private Toolbar customToolbar;
	private Toolbar blankToolbar;
	private Toolbar drawtoolSizeToolbar;
	private InputDevice inputDevice;
	private Canvas canvas;
	private Button paintbrushButton;
	private Button eraserButton;
	private Button effectsButton;
	private Button linesButton;
	private Button lineButton;
	private Button deleteButton;
	private Button circleButton;
	private Button diamondButton;
	private Button pentagonButton;
	private Button squareButton;
	private Button triangleButton;
	private Button circle1Button;
	private Button diamond1Button;
	private Button pentagon1Button;
	private Button square1Button;
	private Button triangle1Button;
	private Button rainbowButton;
	private Button transparentRainbowButton;
	private Button eraser1Button;
	private Button eraser2Button;
	private Button eraser3Button;
	private Button eraser4Button;
	
	private Label inputModeLabel;
	private Scrollbar scroller;
	private static final int TOOLBAR_SIZE = 70;

	/**
	 * Constructor to create a Drawing Application. 
	 * The Drawing application extends the Processing's
	 * PApplet component. 
	 * 
	 * @param loader Instance of a Driver class to load resources.
	 */
	public DrawingApplication(Driver loader){
		super();
		this.loader = loader;
	}
	
	
	/**
	 * Setup contains initialisation code to setup the GUI and 
	 * connect the Wii Remote via Bluetooth. A Loading graphic is displayed
	 * at the start of this method and animates until the end of the method
	 * and is then disposed of. This method should not be invoked directly from
	 * an application. 	
	 */
	@Override
	public void setup(){
		Loader loadingFrame = new Loader();
		try{
			robot = new Robot();
		}
		catch(Exception e){
			e.printStackTrace();
			System.err.println("Could not create robot");
		}
		inputModeLabel = new Label("",this);
		WiiConnectionThread connectionThread = new WiiConnectionThread();
		connectionThread.start();
		rainbowStroke = new RainbowStroke(0, 0, 0, 0,this);
		transparentRainbowStroke = new TransparentRainbowStroke(0, 0, 0, 0, this);
		
		toolbars = new ArrayList<Component>();		
		robot.mouseMove(0, 0);
		inputDevice = new InputDevice(mouseX, mouseY);
		
		background(0);
		size(screen.width,screen.height);
		strokeWeight(1);
		//main tool bar buttons
		paintbrushButton = new Button(loadPImage("paintbrush.png"),this);
		eraserButton = new Button(loadPImage("eraser.png"),this);
		effectsButton = new Button(loadPImage("MagicIcon.png"),this);
		linesButton = new Button(loadPImage("pencil.png"),this);
		deleteButton = new Button(loadPImage("trash.png"),this);

		//line tool bar buttons
		lineButton = new Button(loadPImage("line.png"),this);
		circleButton   = new Button(loadPImage("circle_f.png"),this);
		diamondButton  = new Button(loadPImage("diamond_f.png"),this);
		pentagonButton = new Button(loadPImage("pentagon_f.png"),this);
		squareButton   = new Button(loadPImage("square_f.png"),this);
		triangleButton = new Button(loadPImage("triangle_f.png"),this);
		
		//paint brush tool bar buttons
		circle1Button   = new Button(loadPImage("circle_f.png"),this);
		diamond1Button  = new Button(loadPImage("diamond_f.png"),this);
		pentagon1Button = new Button(loadPImage("pentagon_f.png"),this);
		square1Button   = new Button(loadPImage("square_f.png"),this);
		triangle1Button = new Button(loadPImage("triangle_f.png"),this);
		circle1Button.setSelected(true);
		
		//effects tool bar buttons
		rainbowButton =   new Button(loadPImage("rainbow2.png"),this);
		transparentRainbowButton =   new Button(loadPImage("rainbow.png"),this);
		
		//eraser tool bar buttons
		eraser1Button = new Button(loadPImage("rectangle_f.png"),this);
		eraser2Button = new Button(loadPImage("rectangle_t.png"),this);
		eraser3Button = new Button(loadPImage("circle_f.png"),this);
		eraser4Button = new Button(loadPImage("circle_t.png"),this);
		
		toolbar = new Toolbar(0,TOOLBAR_SIZE,TOOLBAR_SIZE,height-2*TOOLBAR_SIZE,Toolbar.VERTICAL_LAYOUT,this,inputDevice);
		toolbar.add(new Label("Tools",this));
		toolbar.add(linesButton);
		toolbar.add(paintbrushButton);
		toolbar.add(effectsButton);
		toolbar.add(eraserButton);
		toolbar.add(new Label("",this));
		toolbar.add(deleteButton);
		toolbar.add(inputModeLabel);
		
		colorToolbar = new Toolbar(TOOLBAR_SIZE,height-TOOLBAR_SIZE,width-2*TOOLBAR_SIZE,TOOLBAR_SIZE,Toolbar.HORIZONTAL_LAYOUT,this,inputDevice);
		Button b = new Button(new Colour(Colour.BLACK),this);
		b.setSelected(true);
		colorToolbar.add(new Label("Colours",this));
		colorToolbar.add(b);
		colorToolbar.add(new Button(new Colour(Colour.DARK_GRAY),this));
		colorToolbar.add(new Button(new Colour(Colour.GRAY),this));
		colorToolbar.add(new Button(new Colour(Colour.LIGHT_GRAY),this));
		colorToolbar.add(new Button(new Colour(Colour.WHITE),this));
		colorToolbar.add(new Button(new Colour(Colour.GREEN),this));
		colorToolbar.add(new Button(new Colour(Colour.BLUE),this));
		colorToolbar.add(new Button(new Colour(Colour.CYAN),this));
		colorToolbar.add(new Button(new Colour(Colour.PINK),this));
		colorToolbar.add(new Button(new Colour(Colour.PURPLE),this));
		colorToolbar.add(new Button(new Colour(Colour.RED),this));
		colorToolbar.add(new Button(new Colour(Colour.BROWN),this));
		colorToolbar.add(new Button(new Colour(Colour.ORANGE),this));
		colorToolbar.add(new Button(new Colour(Colour.YELLOW),this));
		
		lineToolbar = new Toolbar(width-TOOLBAR_SIZE,TOOLBAR_SIZE,TOOLBAR_SIZE,height-2*TOOLBAR_SIZE,Toolbar.VERTICAL_LAYOUT,this,inputDevice);
		paintbrushToolbar = new Toolbar(width-TOOLBAR_SIZE,TOOLBAR_SIZE,TOOLBAR_SIZE,height-2*TOOLBAR_SIZE,Toolbar.VERTICAL_LAYOUT,this,inputDevice);
		eraserToolbar = new Toolbar(width-TOOLBAR_SIZE,TOOLBAR_SIZE,TOOLBAR_SIZE,height-2*TOOLBAR_SIZE,Toolbar.VERTICAL_LAYOUT,this,inputDevice);
		effectsToolbar = new Toolbar(width-TOOLBAR_SIZE,TOOLBAR_SIZE,TOOLBAR_SIZE,height-2*TOOLBAR_SIZE,Toolbar.VERTICAL_LAYOUT,this,inputDevice);
		blankToolbar = new Toolbar(width-TOOLBAR_SIZE, TOOLBAR_SIZE,TOOLBAR_SIZE,height-2*TOOLBAR_SIZE, Toolbar.VERTICAL_LAYOUT, this, inputDevice);
		lineToolbar.add(new Label("Line",this));
		lineToolbar.add(lineButton);
		lineToolbar.add(circleButton);
		lineToolbar.add(diamondButton);
		lineToolbar.add(pentagonButton);
		lineToolbar.add(triangleButton);
		lineToolbar.add(squareButton);
		
		paintbrushToolbar.add(new Label("Brush",this));
		paintbrushToolbar.add(circle1Button);
		paintbrushToolbar.add(diamond1Button);
		paintbrushToolbar.add(pentagon1Button);
		paintbrushToolbar.add(triangle1Button);
		paintbrushToolbar.add(square1Button);
		
		eraserToolbar.add(new Label("Eraser",this));
		eraserToolbar.add(eraser1Button);
		eraserToolbar.add(eraser2Button);
		eraserToolbar.add(eraser3Button);
		eraserToolbar.add(eraser4Button);
		eraser1Button.setSelected(true);
		
		drawtoolSizeToolbar = new Toolbar(TOOLBAR_SIZE,0,width-2*TOOLBAR_SIZE,TOOLBAR_SIZE,Toolbar.HORIZONTAL_LAYOUT,this,inputDevice);
		scroller = new Scrollbar(TOOLBAR_SIZE+10, 0, width-TOOLBAR_SIZE*2+10, TOOLBAR_SIZE,this,inputDevice);
		drawtoolSizeToolbar.add(scroller);
		scroller.setWidth(drawtoolSizeToolbar.getWidth()-10);
		
		effectsToolbar.add(new Label("FX",this));
		effectsToolbar.add(rainbowButton);
		effectsToolbar.add(transparentRainbowButton);
		
		canvas = new Canvas(TOOLBAR_SIZE,TOOLBAR_SIZE,width-2*TOOLBAR_SIZE,height-2*TOOLBAR_SIZE);
		customToolbar = lineToolbar;
		toolbars.add(canvas);
		toolbars.add(toolbar);
		toolbars.add(drawtoolSizeToolbar);
		toolbars.add(colorToolbar);
		toolbars.add(customToolbar);
				
		linesButton.setSelected(true);
		toolbar.setSelectedComponent(linesButton);
		lineToolbar.setSelectedComponent(lineButton);
		paintbrushToolbar.setSelectedComponent(circle1Button);
		effectsToolbar.setSelectedComponent(rainbowButton);
		eraserToolbar.setSelectedComponent(eraser1Button);
		effectsButton.setSelected(true);
		paintbrushButton.setSelected(true);
		applySelectedDrawColour();
		applySuitableEffectsTool();
		applySuitableLineDrawingTool();
		applySuitablePaintbrushTool();
		background(255);
		loadingFrame.stop();
	}
	
	
	/**
	 * This is not to be invoked by an application.
	 * The draw method is called by the PApplets main loop. The draw method contains
	 * code to read the and update GUI components and perform whatever drawing to the 
	 * canvas that is required.  
	 */
	@Override
	public void draw(){

		//draw the tool bars and buttons
		for(Component c: toolbars){
			c.display();	
		}
		//fill in the corners of the GUI
		noStroke();
		fill(100);
		rect(0,0,TOOLBAR_SIZE,TOOLBAR_SIZE);
		rect(width-TOOLBAR_SIZE,0,TOOLBAR_SIZE,TOOLBAR_SIZE);
		rect(width-TOOLBAR_SIZE,height-TOOLBAR_SIZE,TOOLBAR_SIZE,TOOLBAR_SIZE);
		rect(0,height-TOOLBAR_SIZE,TOOLBAR_SIZE,TOOLBAR_SIZE);
		
		applySelectedDrawColour();
		updateToolbarCustomToolbar();
		
		if(customToolbar.equals(eraserToolbar)){
			applySuitableEraserTool();
		}
		if(customToolbar.equals(paintbrushToolbar)){
			applySuitableLineDrawingTool();
		}
		if(customToolbar.equals(lineToolbar)){
			applySuitablePaintbrushTool();
		}
		if(customToolbar.equals(effectsToolbar)){
			applySuitableEffectsTool();
		}

		inputDevice.setX(mouseX);
		inputDevice.setY(mouseY);
	}

	/**
	 * Read what button is selected from the Effects toolbar and
	 * apply it as the new draw tool
	 */
	private void applySuitableEffectsTool() {
		Component selectedCustomComponent = effectsToolbar.getSelectedComponent();
		if(selectedCustomComponent != null){
			if(selectedCustomComponent instanceof Button){
				((Button) selectedCustomComponent).setSelected(true);
				
				if(rainbowButton.equals((Button)selectedCustomComponent)){
					rainbowStroke.setStroke(scroller.getSliderValue());
					canvas.setShape(rainbowStroke);
				}
				if(transparentRainbowButton.equals((Button)selectedCustomComponent)){
					transparentRainbowStroke.setStroke(scroller.getSliderValue());
					canvas.setShape(transparentRainbowStroke);
				}
			}
		}		
	}
	
	/**
	 * Read what button is selected from the paintbrush toolbar and
	 * apply it as the new draw tool
	 */
	private void applySuitablePaintbrushTool() {
		Component selectedCustomComponent = customToolbar.getSelectedComponent();
		if(selectedCustomComponent != null){
			
			if(selectedCustomComponent instanceof Button){
				((Button) selectedCustomComponent).setSelected(true);
				
				applySelectedDrawColour();
				
				if(squareButton.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Rectangle(10, 10,scroller.getSliderValue(), scroller.getSliderValue(), canvas.getPaintColour(),this ));
				}
				if(lineButton.equals((Button)selectedCustomComponent)){
					canvas.setShape(null);
				}
				if(circleButton.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Circle(10, 10, scroller.getSliderValue(), canvas.getPaintColour(),this));
				}
				if(triangleButton.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Triangle(10, 10, scroller.getSliderValue(), scroller.getSliderValue() ,canvas.getPaintColour(),this));
				}
				if(diamondButton.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Diamond(0, 0, scroller.getSliderValue(), scroller.getSliderValue() ,canvas.getPaintColour(),this));
				}
				if(pentagonButton.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Pentagon(0, 0, scroller.getSliderValue(), scroller.getSliderValue() ,canvas.getPaintColour(),this));
				}
			}
		}		
	}
	
	/**
	 * Read what button is selected from the line toolbar and
	 * apply it as the new draw tool
	 */
	private void applySuitableLineDrawingTool() {
		Component selectedCustomComponent = customToolbar.getSelectedComponent();
		if(selectedCustomComponent != null){
			applySelectedDrawColour();
			if(selectedCustomComponent instanceof Button){
				((Button) selectedCustomComponent).setSelected(true);
	
				if( square1Button.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Rectangle(10, 10,scroller.getSliderValue(), scroller.getSliderValue(), canvas.getPaintColour() ,this));
				}
				if( circle1Button.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Circle(10, 10, scroller.getSliderValue(), canvas.getPaintColour(),this));
				}
				if( triangle1Button.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Triangle(10, 10, scroller.getSliderValue(), scroller.getSliderValue() ,canvas.getPaintColour(),this));
				}
				if( diamond1Button.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Diamond(0, 0, scroller.getSliderValue(), scroller.getSliderValue() ,canvas.getPaintColour(),this));
				}
				if(pentagon1Button.equals((Button)selectedCustomComponent)){
					canvas.setShape(new Pentagon(0, 0, scroller.getSliderValue(), scroller.getSliderValue() ,canvas.getPaintColour(),this));
				}
			}
		}
	}

	/**
	 * Read what button is selected from the eraser toolbar and
	 * apply it as the new draw tool
	 */
	private void applySuitableEraserTool() {
		//read what colour is selected from the colour tool bar
		Component selectedEraserComponent = eraserToolbar.getSelectedComponent();
		if(selectedEraserComponent != null){
			if(selectedEraserComponent instanceof Button){
				((Button) selectedEraserComponent).setSelected(true);
				Colour eraserColour = canvas.backgroundColour;
				
				canvas.setPaintColour(eraserColour);
				if( eraser1Button.equals((Button)selectedEraserComponent)){
					eraserColour.setTransparency(0);
					canvas.setShape(new Rectangle(10, 10, scroller.getSliderValue(), scroller.getSliderValue(), eraserColour,this ));
					
				}
				if( eraser2Button.equals((Button)selectedEraserComponent)){
					eraserColour.setTransparency(20);
					canvas.setShape(new Rectangle(10, 10, scroller.getSliderValue(), scroller.getSliderValue(), eraserColour,this ));
				
				}
				if( eraser3Button.equals((Button)selectedEraserComponent)){
					eraserColour.setTransparency(0);
					canvas.setShape(new Circle(10,10,scroller.getSliderValue(),eraserColour,this));
				
				}
				if( eraser4Button.equals((Button)selectedEraserComponent)){
					eraserColour.setTransparency(20);
					canvas.setShape(new Circle(10, 10, scroller.getSliderValue(), eraserColour,this ));			
				}
			}
		}
	}
	
	/**
	 * Read what button is selected from the colour toolbar and
	 * apply it as the new draw colour
	 */
	private void applySelectedDrawColour(){
		Component selectedColorComponent = colorToolbar.getSelectedComponent();
		if(selectedColorComponent != null){
			if(selectedColorComponent instanceof Button){
				canvas.setPaintColour(((Button) selectedColorComponent).getColour());
				((Button) selectedColorComponent).setSelected(true);
				canvas.getPaintColour().setTransparency(0);
			}
		}
	}
	
	/**
	 * Used to update the custom toolbar. Depending on what button is pressed
	 * on the main toolbar the custom toolbar will change eg. if the button pressed
	 * on main toolbar is the paint brush button then the custom toolbar will be changed
	 * to the paint brush toolbar.  
	 */
	private void updateToolbarCustomToolbar(){
		Component selectedToolComponent = toolbar.getSelectedComponent();
		if(selectedToolComponent != null){
			if(selectedToolComponent instanceof Button){
				((Button) selectedToolComponent).setSelected(true);
				if(paintbrushButton.equals((Button)selectedToolComponent)){
					toolbars.remove(customToolbar);
					customToolbar = paintbrushToolbar;
					toolbars.add(customToolbar);
				}
				if(linesButton.equals((Button)selectedToolComponent)){
					toolbars.remove(customToolbar);
					customToolbar = lineToolbar;
					toolbars.add(customToolbar);
				}
				if(eraserButton.equals((Button)selectedToolComponent)){
					toolbars.remove(customToolbar);
					customToolbar = eraserToolbar;
					toolbars.add(customToolbar);
				}
				if(deleteButton.equals((Button)selectedToolComponent)){
					Component selectedColorComponent = colorToolbar.getSelectedComponent();
					if(selectedColorComponent != null){
						if(selectedColorComponent instanceof Button){
							canvas.setBackgroundColour(((Button) selectedColorComponent).getColour());
						}
					}
					toolbars.remove(customToolbar);
					customToolbar = blankToolbar;
					toolbars.add(customToolbar);
					canvas.clear();
				}
				if(effectsButton.equals((Button)selectedToolComponent)){
					toolbars.remove(customToolbar);
					customToolbar = effectsToolbar;
					toolbars.add(customToolbar);
				}
			}
		}
	}
	
	/**
	 * Load an image from a file and return it as a PImage object. Note that the 
	 * image must be stored in the data folder to work outside of the Eclipse IDE, 
	 * such as a jar file.
	 * 
	 * @param path The path of the image
	 * @return A PImage containing the file image
	 */
	public PImage loadPImage(String path){
		PImage p = loadImage(path);
		return p;
	}
	
	class Canvas implements Component{
		private int x;
		private int y;
		private int width;
		private int height;
		private Colour paintColour;
		private Shape shape;
		private Colour backgroundColour;
		
		/**
		 * Create a new Draw canvas. 
		 * @param x The x-position of the Draw Canvas
		 * @param y The y-position of the Draw Canvas
		 * @param width The width of the Draw Canvas
		 * @param height The height of the Draw Canvas
		 */
		public Canvas(int x, int y,int width, int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.paintColour = new Colour(Colour.BLACK);
			this.backgroundColour = new Colour(Colour.WHITE);
		}
		
		/**
		 * Set the paint colour of the canvas. 
		 * @param c Colour object of the new paint colour
		 */
		public void setPaintColour(Colour c){
			this.paintColour = c;
		}
		
		/**
		 * Get the current paint colour of the draw canvas
		 * @return
		 */
		public Colour getPaintColour(){
			return this.paintColour;
		}
		
		//not implemented
		@Override
		public void add(Component c) {	}

		@Override
		public boolean containsPoint(int px, int py) {
			if(px > x && px < x + this.width && py > y && py < y + this.height){
		      return true;
		    }  
		    else{
		      return false; 
		    }
		}

		@Override
		public void display() {
			stroke(paintColour.r,paintColour.g,paintColour.b);
			fill(paintColour.r,paintColour.g,paintColour.b);
			int cx = inputDevice.getX();
			int cy = inputDevice.getY();
			
			int w = 0;
			int h = 0;
			if(shape != null){
				w = shape.getWidth();
				h = shape.getHeight();
			}
			
			if(customToolbar.equals(paintbrushToolbar)){
				cx = inputDevice.getX();
				cy = inputDevice.getY();
				
				w = inputDevice.getX() - inputDevice.getPreviousX();
				h = inputDevice.getY() - inputDevice.getPreviousY();
			}
			
			if(customToolbar.equals(effectsToolbar)){
				cx = inputDevice.getPreviousX();
				cy = inputDevice.getPreviousY();
				
				w = inputDevice.getX();
				h = inputDevice.getY();
			}
			
			
			if(this.containsPoint(inputDevice.getX(), inputDevice.getY())){
				
				if(shape != null){
						shape.setColour(paintColour);
						shape.setX(cx);
						shape.setY(cy);
						shape.setWidth(w);
						shape.setHeight(h);
						shape.display();
				}
				else{
					int px = inputDevice.getPreviousX();
					int py = inputDevice.getPreviousY();
					strokeWeight(scroller.getSliderValue());
					line(px,py,cx,cy);
				}
			}
		}
		
		public void clear(){
			noStroke();
			fill(backgroundColour.r,backgroundColour.g,backgroundColour.b);
			rect(this.x,this.y,this.width,this.height);
		}
		
		public void setShape(Shape s){
			this.shape = s;
		}
		
		@Override
		public int getX(){	return this.y;	}
		@Override
		public int getY(){	return this.y;	}
		@Override
		public int getHeight() {		return this.height;		}
		@Override
		public int getWidth() {			return this.width;		}
		@Override
		public void setHeight(int height) {	this.height = height;		}
		@Override
		public void setWidth(int width) {	this.width = width;		}
		@Override
		public void setX(int x) {			this.x =x;		}
		@Override
		public void setY(int y) {			this.y = y;		}

		public void setBackgroundColour(Colour colour) { this.backgroundColour = colour;		}		
	}

	class WiiListener extends WiiRemoteAdapter{
		@Override
		public void IRInputReceived(WRIREvent evt){
			IRLight[] lightArray = evt.getIRLights();

    		if(lightArray[0] != null){
    			robot.mouseMove(screen.width-(int)(screen.width*lightArray[0].getX()), screen.height - (int)(screen.height*lightArray[0].getY()));
    		}
	    }
		@Override
		public void disconnected(){
			inputModeLabel.setText("");
			    int response = JOptionPane.showConfirmDialog(null, "Wii remote was disconnected\n" +
			    		"Do you want to try and reconnect?", "Confirm",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.NO_OPTION) {
			    	inputModeLabel.setText("");
			    	JOptionPane.showMessageDialog(null, "Mouse now used for drawing");
			    } else if (response == JOptionPane.YES_OPTION) {
			      WiiConnectionThread connectionThread = new WiiConnectionThread();
			      connectionThread.start();
			    } else if (response == JOptionPane.CLOSED_OPTION) {
			    	inputModeLabel.setText("");
			      	JOptionPane.showMessageDialog(null, "Mouse now used for drawing");
			    }
		}
	}
	WiiRemote wii;
	
	@Override
	public void destroy(){
		if(wii != null){
			wii.disconnect();
		}
		super.destroy();
	}

	class WiiConnectionThread extends Thread{
		@Override
		public void run(){
			try{
				Thread okThread = new Thread(){
					public void run(){
						JOptionPane.showMessageDialog(null, "Searching for Wii Remote...");
					}
				};
				okThread.start();
				wii = WiiRemoteJ.findRemote();
				
				if(wii != null){
					JOptionPane.showMessageDialog(null, "Wii Remote Connected");
					wii.setIRSensorEnabled(true, WRIREvent.FULL);
					wii.addWiiRemoteListener(new WiiListener());
					inputModeLabel.setText("Wii");
				}
				else{
					JOptionPane.showMessageDialog(null, "Wii Remote Connection Failed! \nMouse now used as draw tool", "Error", JOptionPane.ERROR_MESSAGE);
					inputModeLabel.setText("");
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Wii Remote Connection Failed! \nMouse now used as draw tool", "Error", JOptionPane.ERROR_MESSAGE);					
				inputModeLabel.setText("");
			}
		}
	}
	@Override
	public void keyPressed(){
		if(keyCode==ESC || key == ESC){
		    this.destroy();  
		 }
	}
	
	@Override
	public void keyReleased() {
		 if(keyCode==ESC || key == ESC){
			 this.destroy();  
		 }
	}

	@Override
	public void keyTyped() {
		if(keyCode==ESC || key == ESC){
			this.destroy();  
		}
	}
}
