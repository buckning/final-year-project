/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewmcglynn.sunspot;

/**
 *
 * @author andrew
 */
public class SunSpotSwitchEvent extends SunSpotEvent{
    private int switchNumber;
    private boolean switchState;
    
    public static final int SWITCH_1 = 1;
    public static final int SWITCH_2 = 2;

    public SunSpotSwitchEvent(int switchNumber, boolean switchState){
        this.switchNumber = switchNumber;
        this.switchState = switchState;
    }

    public int getSwitchNumber(){
        return this.switchNumber;
    }

    public boolean getSwitchState(){
        return this.switchState;
    }
}
