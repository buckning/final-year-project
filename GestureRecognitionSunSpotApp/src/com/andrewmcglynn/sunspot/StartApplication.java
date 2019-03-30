/*
 * StartApplication.java
 *
 * This has a basic functionality of reading the accelerometer and switches and sends the information
 * Created on 20-Jan-2010 23:45:17;
 */

package com.andrewmcglynn.sunspot;

import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.sensorboard.io.IIOPin;
import com.sun.spot.sensorboard.peripheral.ISwitch;
import com.sun.spot.sensorboard.peripheral.LIS3L02AQAccelerometer;
import com.sun.spot.util.*;
import javax.microedition.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class StartApplication extends MIDlet implements CommunicationInterface{
       
    private static final int SAMPLE_PERIOD = 1 * 100;  // in milliseconds
    private boolean ioPinState[] = {true, true, true, true, true};
    private boolean oldIoPinState[] = {true, true, true, true, true};
    private boolean switchState[] = {true,true};
    private boolean oldSwitchState[] = {true, true};

    public LIS3L02AQAccelerometer accelerometer;

    private RadiogramConnection rCon = null;
    private Datagram dg = null;
    private IIOPin[] pins = EDemoBoard.getInstance().getIOPins();
    private ISwitch[] switches = EDemoBoard.getInstance().getSwitches();

    protected void startApp() throws MIDletStateChangeException {
        String ourAddress = System.getProperty("IEEE_ADDRESS");
        long now = 0L;

        accelerometer = (LIS3L02AQAccelerometer)EDemoBoard.getInstance().getAccelerometer();
        accelerometer.setScale(LIS3L02AQAccelerometer.SCALE_2G);

        System.out.println("Starting sensor sampler application on " + ourAddress + " ...");
        new com.sun.spot.util.BootloaderListener().start();       // Listen for downloads/commands over USB connection
        pins[0].setHigh();
        pins[0].setLow();
       //reset all variables
       for(int i = 0; i < pins.length-1; i++){
           if(pins[i].isLow()) ioPinState[i] = true;
           oldIoPinState[i] = ioPinState[i];
       }


        //reset all switch variables
       for(int i = 0; i < switches.length; i++){
           if(switches[i].isOpen()) switchState[i] = true;
           oldSwitchState[i] = switchState[i];
       }
       
       //setup radio connection and datagrams, if this spot is the left spot connect to left port else connect to right port
        try {
            long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
            int port;
            if(IEEEAddress.toDottedHex(ourAddr).equals(LEFT_SPOT_ADDRESS)) port = LEFT_COMM_PORT;
            else port = RIGHT_COMM_PORT;
            // Open up a broadcast connection to the correct port
            // where the 'on Desktop' portion of this project is listening
            rCon = (RadiogramConnection) Connector.open("radiogram://broadcast:" + port);
            dg = rCon.newDatagram(50);  // only sending 12 bytes of data
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
            System.exit(1);
        }

 
        double xaccelReading,yaccelReading,zaccelReading;

        while (true) {
            try {
                // Get the current time and sensor reading
                now = System.currentTimeMillis();

                xaccelReading = accelerometer.getAccelX();
                yaccelReading = accelerometer.getAccelY();
                zaccelReading = accelerometer.getAccelZ();

                //send the accelerometer readings
                dg.reset();
                dg.writeByte(ACCEL_ALL);
                dg.writeDouble(xaccelReading);
                dg.writeDouble(yaccelReading);
                dg.writeDouble(zaccelReading);
                rCon.send(dg);

                //read IO pins
                for(int i = 0; i < pins.length-1; i++){
                    if(pins[i].isLow()){
                        ioPinState[i] = false;
                    }else ioPinState[i] = true;
                }
                //send IOPin state if it is changed
                for(int i = 0; i < pins.length-1; i++){
                    if(ioPinState[i] != oldIoPinState[i]){
                        sendIOPinState(ioPinState[i], i);
                        System.out.println("Pin "+i+": Switch state '"+ioPinState[i]+"' being sent");
                    }
                    oldIoPinState[i] = ioPinState[i];
                }

                //switch state
                for(int i = 0; i < switches.length; i++){
                    if(switches[i].isClosed()){
                        switchState[i] = true;
                    }else switchState[i] = false;
                }
                //send switch state if changed
                for(int i = 0; i < switches.length; i++){
                    if(switchState[i] != oldSwitchState[i]){
                        sendSwitchState(switchState[i], i+1);
                        System.out.println("Switch "+i+": Switch state '"+switchState[i]+"' being sent");
                    }
                    oldSwitchState[i] = switchState[i];
                }

                // Go to sleep to conserve battery
                Utils.sleep(SAMPLE_PERIOD - (System.currentTimeMillis() - now));
            } catch (Exception e) {
                System.err.println("Caught " + e + " while collecting/sending sensor sample.");
            }
        }
    }
    private void sendIOPinState(boolean b,int switchNum) {
           try{
            Utils.sleep(50);
            dg.reset();
            dg.writeByte((switchNum +IOPIN_D0));
            dg.writeBoolean(b);
            rCon.send(dg);

         }
         catch(Exception e){
             e.printStackTrace();
         }

    }

    private void sendSwitchState(boolean b,int switchNum) {
           try{
            dg.reset();
            dg.writeByte(switchNum);
            dg.writeBoolean(b);
            rCon.send(dg);
         }
         catch(Exception e){
             e.printStackTrace();
         }
    }

    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // Only called if startApp throws any exception other than MIDletStateChangeException
    }
}