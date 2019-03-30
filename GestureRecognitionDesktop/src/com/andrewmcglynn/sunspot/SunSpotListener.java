package com.andrewmcglynn.sunspot;
/**
 *
 * @author Andrew McGlynn
 * @version 1.0
 *
 * The SunSpotListener interface allows an application receive SunSpotEvents
 * When a radiogram is received from the Sun Spot a specific event is triggered
 * depending on the contents of the @see datagram.
 *
 */
public interface SunSpotListener {

    /*
     * Invoked when a datagram stating the tilt of the Spot is received
     * */
    public void tiltInputReceived(SunSpotTiltEvent ev);
    /*
     * Invoked when the digital IO pin of the SunSPOT changes state. eg high to low
     * */
    public void digitalPinInputChange(SunSpotDigitalIOEvent ev);

    /*
     * Invoked when a switch on the EDemoboard's state is changed.
     * */
    public void pushButtonSwitchChange(SunSpotSwitchEvent ev);

    public static final byte XAXIS         = 1;
    public static final byte YAXIS         = 2;
    public static final byte ZAXIS         = 3;

    public static final byte IOPIN_D0       = 11;
    public static final byte IOPIN_D1       = 12;
    public static final byte IOPIN_D2       = 13;
    public static final byte IOPIN_D3       = 14;
}
