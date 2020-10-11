package org.wendex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, AirportWritableComparable, AirportWritable> {
	@Override
	protected void map(LongWritable key, AirportWritable value, Context context)
			throws IOException, InterruptedException {
		context.write(value.toAirportWritableComparable(), value);
	}
}
