# Summary

This is a Java Micro Edition (ME) MIDlet application. It is intended to run on a Sun SPOT, eSPOT. The eSPOT is a device that can run independently and communicates to a base station.
See the [project report](https://github.com/buckning/final-year-project/blob/master/report.doc) for more information. 

It reads the x, y, z axis data from the onboard accelerometer and broadcasts it as a datagram over the radiogram connection. 
The values on the digital IO pins are then sampled, if there is a change in state in any of the IO pins, the pin number and the pin state is then broadcast. 

# Dependencies

* Java Micro Edition: https://www.oracle.com/technetwork/java/embedded/javame/index.html
* Sun SPOT SDK: http://www.sunspotdev.org/