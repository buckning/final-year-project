package com.andrewmcglynn.application;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import com.andrewmcglynn.imageutils.BufferedImageUtils;
import java.awt.Dimension;

public class Photo {
    private BufferedImage loadedImage;
    
	private int x;
	private int y;
	private int width;
	private int height;
	private double theta;	
	private BufferedImage image;
	private boolean selected;
	private float alpha = 0.5f;	
	private Rectangle photoBounds;
	private boolean highlighted;
    private boolean markedForRemoval;

    private Dimension preferredPhotoSize;

	public Photo(BufferedImage image, int x, int y){
		this.x = x;
		this.y = y;
		this.image = image;
        this.markedForRemoval = false;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.theta = 0;
		this.selected = false;
		this.highlighted = false;

        loadedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        

        Graphics2D g = loadedImage.createGraphics();
        g.drawImage(image, null, 0,0);
        g.dispose();
		photoBounds = new Rectangle(x, y, width, height);
        preferredPhotoSize = new Dimension(width, height);
	}
	
	
	public void paint(Graphics2D g){
//		g.fillRect(x, y, width, height);
		BufferedImage shadowImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D shadowGraphics = (Graphics2D)shadowImage.getGraphics();
        shadowGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        Color color = new Color(.1f, .1f, .1f, alpha);

		shadowGraphics.setPaint(color);
        shadowGraphics.fillRect(10, 10, width+10, height+10);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		double x1 = image.getWidth()/2;
		double y1 = image.getHeight()/2;
		at.rotate(theta, x1, y1);

        g.translate(10, 10);
        g.drawImage(shadowImage, at, null);
        g.translate(-10, -10);
        g.drawImage(image, at, null);
	}
	
	public void resizePhoto(int width, int height){
		image = BufferedImageUtils.getScaledVersion(loadedImage, width, height);
		this.width = image.getWidth();
		this.height = image.getHeight();
		photoBounds = new Rectangle(this.x, this.y, this.width, this.height);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		photoBounds = new Rectangle(x, y, width, height);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		photoBounds = new Rectangle(x, y, width, height);
	}

    public BufferedImage getImage(){
        return image;
    }

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean containsPoint(Point p){
		if(photoBounds.contains(p))return true;		
		return false;
	}

    public boolean intersects(Rectangle r){
		if(photoBounds.intersects(r))return true;
		return false;
	}
	

	public boolean isHighlighted() {
		return highlighted;
	}


	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

    public void setAlpha(float f){
        this.alpha = f;
    }
    public boolean isMarkedForRemoval(){
        return markedForRemoval;
    }
    public void setMarkedForRemoval(boolean b){
        this.markedForRemoval = b;
    }
    public Dimension getPreferredSize(){
        return preferredPhotoSize;
    }
    public void setPreferredSize(Dimension d){
        this.preferredPhotoSize = d;
    }
}