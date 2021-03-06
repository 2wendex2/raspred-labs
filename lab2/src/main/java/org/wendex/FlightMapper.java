package org.wendex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightMapper extends Mapper<LongWritable, Text, AirportWritableComparable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String[] arr = CsvTools.read(value.toString());
        int id;
        double delay;
        try {
            id = Integer.parseInt(arr[14]);
            delay = Double.parseDouble(arr[18]);
            if (delay == 0) {
                return;
            }
        } catch (NumberFormatException exception) {
            return;
        }

        context.write(new AirportWritableComparable(id, true), new Text(arr[18]));
    }
}