package com.andrewmcglynn.application;

import java.awt.image.BufferedImage;

/**
 *
 * @author andrew
 */
public class SunSpotCursor {
     private int x,y;
    private BufferedImage cursorImage;
    private boolean button1State;
    private boolean button2State;
    private boolean button3State;
    private boolean button4State;
    private boolean hasPhotoSelected;

    private BufferedImage idleImage;
    private BufferedImage moveImage;
    private BufferedImage rotateImage;
    private BufferedImage resizeImage;
    private BufferedImage function4Image;

    private Photo selectedPhoto;

    public static final int MODE_IDLE = 1;
    public static final int MODE_RESIZE = 2;
    public static final int MODE_ROTATE = 3;
    public static final int MODE_MOVE = 4;
    private int mode;

    public SunSpotCursor(BufferedImage image){
        this.x = 0;
        this.y = 0;
        mode = MODE_IDLE;
        hasPhotoSelected = false;
        this.cursorImage = image;
        moveImage = image;
        idleImage = image;
        rotateImage = image;
        resizeImage = image;
    }

    public void setMode(int mode){
        this.mode = mode;
        if(mode == MODE_MOVE)cursorImage = moveImage;
        if(mode == MODE_RESIZE)cursorImage = resizeImage;
        if(mode == MODE_ROTATE)cursorImage = rotateImage;
        if(mode == MODE_IDLE) cursorImage = idleImage;

    }

    public int getMode(){
        return this.mode;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public void moveY(int dy){
		this.y += dy;
	}

	public void moveX(int dx){
		this.x += dx;
	}

    public BufferedImage getImage() {
		return cursorImage;
	}

	public void setImage(BufferedImage image) {
		this.cursorImage = image;
	}

    public boolean isButton1Pressed() {
		return button1State;
	}

	public void setButton1Pressed(boolean button1Pressed) {
		this.button1State = button1Pressed;
	}

    public boolean isButton2Pressed() {
		return button2State;
	}

	public void setButton2Pressed(boolean buttonPressed) {
		this.button2State = buttonPressed;
	}

    public boolean isButton3Pressed() {
		return button3State;
	}

	public void setButton3Pressed(boolean buttonPressed) {
		this.button3State = buttonPressed;
	}

    public boolean isButton4Pressed() {
		return button4State;
	}

	public void setButton4Pressed(boolean buttonPressed) {
		this.button4State = buttonPressed;
	}

    public boolean hasPhotoSelected(){
        return hasPhotoSelected;
    }
    public void photoSelected(boolean b){
        hasPhotoSelected = b;
    }

    public Photo getSelectedPhoto(){
        return selectedPhoto;
    }

    public void setSelectedPhoto(Photo p){
        if(this.selectedPhoto != null){
            this.selectedPhoto.setSelected(false);
        }
		//select the new photo
		this.selectedPhoto = p;
		if(this.selectedPhoto != null){
            this.selectedPhoto.setSelected(true);
            hasPhotoSelected = true;
        }
        else{
            hasPhotoSelected = false;
        }
    }
    public void setRotateImage(BufferedImage image){
        this.rotateImage = image;
    }

    public void setResizeImage(BufferedImage image){
        this.resizeImage = image;
    }
    public void setMoveImage(BufferedImage image){
        this.moveImage = image;
    }
}
