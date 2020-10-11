package org.wendex;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class AirportComparator extends WritableComparator {
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        return ((AirportWritableComparable)a).getId() - ((AirportWritableComparable)b).getId();
    }
}
