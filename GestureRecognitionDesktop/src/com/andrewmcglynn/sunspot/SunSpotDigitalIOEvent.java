/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewmcglynn.sunspot;

/**
 *
 * @author andrew
 */
public class SunSpotDigitalIOEvent extends SunSpotEvent{
    private int ioPin;
    private boolean pinState;

    public SunSpotDigitalIOEvent(int ioPin, boolean pinState){
        this.pinState = pinState;
        this.ioPin = ioPin;
    }

    public int getIoPinNumber() {
		return ioPin;
	}
	public boolean pinState() {
		return pinState;
	}
}
