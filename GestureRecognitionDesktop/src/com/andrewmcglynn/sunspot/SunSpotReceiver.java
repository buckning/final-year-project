package com.andrewmcglynn.sunspot;


import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;

/**
 * Thread that receives data from a Sun Spot and generates events when received.
 * 
 * @author Andrew McGlynn
 * @version 1.0
 */
public class SunSpotReceiver extends Thread implements CommunicationInterface{
        private RadiogramConnection rCon = null;
        private Radiogram dg = null;
        private int port;
        private boolean finished = false;

        private SunSpotListener listener;

        private static final int sensitivity = 20;

        /**
         * Constructs a new Receiver.
         *
         * @param port	the port that a Sun Spot is communicating to
         */
        public SunSpotReceiver(int port){
            this.port = port;            
        }
        /**
         * Start receiving data from the Sun Spot
         */
        @Override
        public void run(){
            int xaccel = 0;
            int yaccel = 0;

            int inputGain = sensitivity;

            setupConnections();
            while(!finished){
                try{
                    rCon.receive(dg);
                    byte inputType = dg.readByte();

                    //readContents of packet
                    switch(inputType){
                        case ACCEL_ALL:
                            xaccel = (int)(dg.readDouble()*inputGain);
                            yaccel = (int)(dg.readDouble()*inputGain);
                            if(listener != null)listener.tiltInputReceived(new SunSpotTiltEvent(xaccel, yaccel, 0, port));
                        break;
                        case IOPIN_D0:{
                            boolean d0State = dg.readBoolean();
                            if(listener != null)listener.digitalPinInputChange(new SunSpotDigitalIOEvent(0, d0State));
                        }
                        break;
                        case IOPIN_D1:{
                            boolean d1State = dg.readBoolean();
                            if(listener != null)listener.digitalPinInputChange(new SunSpotDigitalIOEvent(1, d1State));
                        }
                        break;
                        case IOPIN_D2:{
                            boolean d2State = dg.readBoolean();
                            if(listener != null)listener.digitalPinInputChange(new SunSpotDigitalIOEvent(2, d2State));
                        }
                        break;
                        case IOPIN_D3:
                            boolean d3State = dg.readBoolean();
                            if(listener != null)listener.digitalPinInputChange(new SunSpotDigitalIOEvent(3, d3State));
                        break;
                        case SWITCH_1:{
                            boolean switchState = dg.readBoolean();
                            if(listener != null)listener.pushButtonSwitchChange(new SunSpotSwitchEvent(1,switchState));
                        }
                        break;
                        case SWITCH_2:{
                            boolean switchState = dg.readBoolean();
                            if(listener != null)listener.pushButtonSwitchChange(new SunSpotSwitchEvent(2,switchState));
                        }
                        break;

                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    /*
     * Setup the Radiogram and datagram to be used for Input/Output
     * */
    private void setupConnections(){
        try {
            rCon = (RadiogramConnection) Connector.open("radiogram://:" + port );
            dg = (Radiogram)rCon.newDatagram(rCon.getMaximumLength());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds the specified Sun Spot Listener to receive IO, tilt events from this thread.
     * If the listener is null, no action will be performed when a Sun Spot Event has occured.
     *
     * @param  listener	the Sun Spot Listener
     */
    public void addSunSpotListener(SunSpotListener listener){
        this.listener = listener;
    }

    public void finish(){
        this.finished = true;
    }
}