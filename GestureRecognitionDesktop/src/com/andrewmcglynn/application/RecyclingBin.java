/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewmcglynn.application;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author andrew
 */
public class RecyclingBin {
    private int x;
    private int y;
    private BufferedImage image;
    private Rectangle imageBounds;
    
    public RecyclingBin(BufferedImage image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
        this.imageBounds = new Rectangle(65, 100, 100, 100);
//        Graphics2D g = (Graphics2D)image.getGraphics();
//        g.fillRect((int)imageBounds.getX(), (int)imageBounds.getY(), (int)imageBounds.getWidth(),(int)imageBounds.getHeight());
//        g.dispose();
    }
    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }

    public void setImage(BufferedImage image){
        this.image = image;
        this.imageBounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
    public BufferedImage getImage(){
        return image;
    }
    public boolean containsPoint(Point p){
        boolean b = false;
        if(imageBounds.contains(p))b = true;
        return b;
    }
}
