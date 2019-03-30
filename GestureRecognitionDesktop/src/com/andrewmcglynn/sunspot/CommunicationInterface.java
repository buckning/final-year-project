package com.andrewmcglynn.sunspot;
/**
 *
 * @author andrew
 *
 * This interface states the communication protocol, it should be
 * used by the sender and receiver.<br>
 * typical use<br><b>Example 1</b><br>
 * <code>
 *      <p>
 *      if(dg.readByte() == XACCEL){<br>
 *          double sensorValue = dg.readDouble();<br>
 *          //do something with the reading<br>
 *      }
 *      </p>
 * </code>
 * <b>Example 2</b><br>
 * <code>
 *      <p>
 *        length = dg.getLength();<br><br>
 *
 *        while(length > 0){<br>
 *          byte dataType = dg.readByte();<br>
 *          length -= LENGTH_BYTE;<br>
 *          switch(dataType){<br>
 *               case XACCEL:<br>
 *                   dg.readDouble();<br>
 *                   length -= LENGTH_DOUBLE;<br>
 *                   //do something with the data<br>
 *               break;<br>
 *               case XTILT:<br>
 *                   dg.readInt();<br>
 *                   length -= LENGTH_INT;<br>
 *                   //do something with the data<br>
 *               break;<br>
 *          }<br>
 *         }<br>
 *      </p>
 * </code>
 *
 *
 *
 */
public interface CommunicationInterface {
        /**
         * IEEE address of the left Sun SPOT
         */
        public static final String LEFT_SPOT_ADDRESS = "0014.4F01.0000.3CCB";
        /**
         * IEEE address of the right Sun SPOT
         */
        public static final String RIGHT_SPOT_ADDRESS = "0014.4F01.0000.3C71";
        /**
         * IEEE address of the basestation
         */
        public static final String BASESTATION_ADDRESS = "0014.4F01.0000.334C";

        /**
         * Header for the x-axis accelerometer reading
         */
        public static final byte XACCEL         = 1;
        /**
         * Header for the y-axis accelerometer reading
         */
        public static final byte YACCEL         = 2;
        /**
         * Header for the z-axis accelerometer reading
         */
        public static final byte ZACCEL         = 3;
        /**
         * Header for the accelerometer reading of the total of all axes
         */
        public static final byte ACCEL          = 4;
        /**
         * Header for the accelerometer readings of all axes
         */
        public static final byte ACCEL_ALL      = 5;
        /**
         * Header for the x-axis tilt
         */
        public static final byte XTILT          = 6;

        /**
         * Header for the y-axis tilt
         */
        public static final byte YTILT          = 7;
        /**
         * Header for the z-axis tilt
         */
        public static final byte ZTILT          = 8;
        /**
         * Header for switch 1 (sw1)
         */
        public static final byte SWITCH_1       = 9;
        /**
         * Header for switch 2 (sw2)
         */
        public static final byte SWITCH_2       = 10;
        /**
         * Header for IO pin 0 (D0)
         */
        public static final byte IOPIN_D0       = 11;
        /**
         * Header for IO pin 1 (D1)
         */
        public static final byte IOPIN_D1       = 12;
        /**
         * Header for IO pin 2 (D2)
         */
        public static final byte IOPIN_D2       = 13;
        /**
         * Header for IO pin 3 (D3)
         */
        public static final byte IOPIN_D3       = 14;

        /**
         * Port that the left SPOT communicates to
         */
        public static final int LEFT_COMM_PORT       = 67;
        /**
         * Port that the right SPOT communicates to
         */
        public static final int RIGHT_COMM_PORT       = 68;

        //data type lengths
        /*
          //The length of the data in bytes
          //These are used when there is more than one sensor
          //value is in the datagram.

         length = dg.getLength();

         while(length > 0){
           byte dataType = dg.readByte();
           length -= LENGTH_BYTE;
           switch(dataType){
                case XACCEL:
                    dg.readDouble();
                    length -= LENGTH_DOUBLE;
                    //do something with the data
                break;
                case XTILT:
                    dg.readInt();
                    length -= LENGTH_INT;
                    //do something with the data
                break;
           }
          }
         */
        /**
         * Length of a boolean in Bytes
         */
        public static final int LENGTH_BOOLEAN   = 1;
        /**
         * Length of a byte in Bytes
         */
        public static final int LENGTH_BYTE      = 1;
        /**
         * Length of a char in bytes
         */
        public static final int LENGTH_CHAR      = 2;
        /**
         * Length of a double in Bytes
         */
        public static final int LENGTH_DOUBLE    = 8;
        /**
         * Length of a float in Bytes
         */
        public static final int LENGTH_FLOAT     = 4;
        /**
         * Length of an int in Bytes
         */
        public static final int LENGTH_INT       = 4;
        /**
         * Length of a long in Bytes
         */
        public static final int LENGTH_LONG      = 8;
        /**
         * Length of a short in Bytes
         */
        public static final int LENGTH_SHORT     = 2;
}