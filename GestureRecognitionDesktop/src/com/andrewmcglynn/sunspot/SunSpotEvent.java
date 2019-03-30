package com.andrewmcglynn.sunspot;

/**
 *
 * @author andrew
 */
public class SunSpotEvent {
    protected long spotAddress;
	protected int port;

    public long getSpotAddress() {
		return spotAddress;
	}
	public void setSpotAddress(long spotAddress) {
		this.spotAddress = spotAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
