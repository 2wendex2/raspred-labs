package org.wendex;

import java.io.Serializable;

public class FlightData implements Serializable {
    private int flightCount = 0;
    private int cancelledDelayCount = 0;
    private double maxDelay = Double.NEGATIVE_INFINITY;

    public FlightData() {}

    public void addFlight(boolean cancelled, double delay) {
        if (delay > maxDelay) {
            maxDelay = delay;
        }
        flightCount++;
        if (cancelled || delay > 0) {
            cancelledDelayCount++;
        }
    }
}
