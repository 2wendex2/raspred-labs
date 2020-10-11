package org.wendex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, AirportWritableComparable, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		AirportWritable airportWritable = new AirportWritable(value);
		if (airportWritable.isValid()) {
			context.write(airportWritable.toAirportWritableComparable(), airportWritable);
		}
	}
}
