package com.andrewmcglynn.sunspot;
/**
 *
 * @author andrew
 *
 * This interface states the communication protocol, it should be
 * used by the sender and receiver
 */
public interface CommunicationInterface {
        //IEEE addresses of the sun spots
        public static final String LEFT_SPOT_ADDRESS = "0014.4F01.0000.3CCB";
        public static final String RIGHT_SPOT_ADDRESS = "0014.4F01.0000.3C71";
        public static final String BASESTATION_ADDRESS = "0014.4F01.0000.334C";


        /*Typical use
         * if(dg.readByte() == XACCEL){
         *      double sensorValue = dg.readDouble();
         *      //do something with the reading
         * }
         */
        public static final byte XACCEL         = 1;
        public static final byte YACCEL         = 2;
        public static final byte ZACCEL         = 3;
        public static final byte ACCEL          = 4;
        public static final byte ACCEL_ALL      = 5;
        public static final byte XTILT          = 6;
        public static final byte YTILT          = 7;
        public static final byte ZTILT          = 8;
        public static final byte SWITCH_1       = 9;
        public static final byte SWITCH_2       = 10;
        public static final byte IOPIN_D0       = 11;
        public static final byte IOPIN_D1       = 12;
        public static final byte IOPIN_D2       = 13;
        public static final byte IOPIN_D3       = 14;
        public static final byte IOPIN_D4       = 15;

        public static final int LEFT_COMM_PORT       = 67;
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
        public static final int LENGTH_BOOLEAN   = 1;
        public static final int LENGTH_BYTE      = 1;
        public static final int LENGTH_CHAR      = 2;
        public static final int LENGTH_DOUBLE    = 8;
        public static final int LENGTH_FLOAT     = 4;
        public static final int LENGTH_INT       = 4;
        public static final int LENGTH_LONG      = 8;
        public static final int LENGTH_SHORT     = 2;
}