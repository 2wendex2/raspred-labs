package org.wendex;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AirportWritableComparable implements WritableComparable<AirportWritableComparable> {
    private int id;
    private boolean flightFlag;

    public AirportWritableComparable() {}

    public AirportWritableComparable(int id, boolean flightFlag) {
        this.id = id;
        this.flightFlag = flightFlag;
    }

    public int getId() {
        return id;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeBoolean(flightFlag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        id = dataInput.readInt();
        flightFlag = dataInput.readBoolean();
    }

    @Override
    public int compareTo(AirportWritableComparable o) {
        int dif = id - o.id;
        if (dif != 0) {
            return dif;
        }

        if (flightFlag) {
            if (!o.flightFlag) {
                return 1;
            }
        } else {
            if (o.flightFlag) {
                return -1;
            }
        }

        return 0;
    }
}
