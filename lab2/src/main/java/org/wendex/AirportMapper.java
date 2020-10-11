package org.wendex;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.io.IOException;
import java.util.regex.*;

public class AirportMapper extends Mapper<LongWritable, AirportWritable, AirportWritableComparable, AirportWritable> {
	@Override
	protected void map(LongWritable key, AirportWritable value, Context context)
			throws IOException, InterruptedException {
		AirportWritableComparable airportWritableComparable = new AirportWritableComparable(va)
		context.write(new Text(matcher.group().toLowerCase()), new IntWritable(1));
		}
	}
}
