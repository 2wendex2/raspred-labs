package org.wendex;

import java.io.Serializable;

public class FlightData implements Serializable {
    private int flightCount = 1;
    private int cancelledDelayCount;
    private double maxDelay;

    public FlightData(double delay, boolean cancelled) {
        maxDelay = delay;
        if (cancelled || delay >= 0)
            cancelledDelayCount = 1;
        else
            cancelledDelayCount = 0;
    }

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
