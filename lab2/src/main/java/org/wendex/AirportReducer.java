package org.wendex;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;
import java.util.Iterator;

public class AirportReducer extends Reducer<AirportWritableComparable, Text,
		IntWritable, Text> {
	@Override
	protected void reduce(AirportWritableComparable key, Iterable<AirportWritable> values, Context context)
			throws IOException, InterruptedException {

		context.write(key, values.iterator().next());
	}
}
