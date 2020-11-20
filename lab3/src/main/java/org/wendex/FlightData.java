package org.wendex;

import java.io.Serializable;

public class FlightData implements Serializable {
    private int flightCount;
    private int cancelledDelayCount;
    private double maxDelay;

    public FlightData(double delay, boolean cancelled) {
        maxDelay = delay;
        if (cancelled || delay > 0)
            cancelledDelayCount = 1;
        else
            cancelledDelayCount = 0;
        flightCount = 1;
    }

    private FlightData() {}

    public FlightData product(FlightData flightData) {
        FlightData r = new FlightData();
        r.flightCount = this.flightCount + flightData.flightCount;
        r.cancelledDelayCount = this.cancelledDelayCount + flightData.cancelledDelayCount;
        r.maxDelay = Double.max(this.maxDelay, flightData.maxDelay);
        return r;
    }

    public double percent() {
        return (double)cancelledDelayCount / (double)flightCount * 100;
    }
}
