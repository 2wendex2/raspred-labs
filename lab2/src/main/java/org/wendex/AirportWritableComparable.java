package org.wendex;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AirportWritableComparable implements WritableComparable<AirportWritableComparable> {
    int id;
    boolean flightFlag;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeChar(',');
        dataOutput.writeBoolean(flightFlag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        id = dataInput.readInt();
        dataInput.readChar();
        flightFlag = dataInput.readBoolean();
    }

    @Override
    public int compareTo(AirportWritableComparable o) {
        if (flightFlag) {
            if (!o.flightFlag) {
                return 1;
            }
        }
    }
}
