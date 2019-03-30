package com.andrewmcglynn.sunspot;

public class SunSpotTiltEvent extends SunSpotEvent{
	private int xTilt;
	private int yTilt;
	private int zTilt;
	

	public SunSpotTiltEvent(){
		xTilt = 0;
		yTilt = 0;
		zTilt = 0;
		spotAddress = 0;
		port = 68;
	}

    public SunSpotTiltEvent(int xTilt, int yTilt, int zTilt, int port){
		this.xTilt = xTilt;
		this.yTilt = yTilt;
		this.zTilt = zTilt;
		this.spotAddress = 0;
		this.port = port;
	}

	public SunSpotTiltEvent(int xTilt, int yTilt, int zTilt, int port, long spotAddress){
		this.xTilt = xTilt;
		this.yTilt = yTilt;
		this.zTilt = zTilt;
		this.spotAddress = spotAddress;
		this.port = port;
	}
	public int getxTilt() {
		return xTilt;
	}
	public void setxTilt(int xTilt) {
		this.xTilt = xTilt;
	}
	public int getyTilt() {
		return yTilt;
	}
	public void setyTilt(int yTilt) {
		this.yTilt = yTilt;
	}
	public int getzTilt() {
		return zTilt;
	}
	public void setzTilt(int zTilt) {
		this.zTilt = zTilt;
	}
	
}
