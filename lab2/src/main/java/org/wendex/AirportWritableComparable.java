package org.wendex;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirportWritableComparable that = (AirportWritableComparable) o;
        return id == that.id &&
                flightFlag == that.flightFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightFlag);
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
