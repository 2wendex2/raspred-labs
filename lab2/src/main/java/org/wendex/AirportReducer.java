package org.wendex;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;
import java.util.Iterator;

public class AirportReducer extends Reducer<AirportWritableComparable, AirportWritable, Text, LongWritable> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		long count=0;
		Iterator iter = values.iterator();
		while(iter.hasNext()) {
			iter.next();
			count++;
		}
		context.write(key, new LongWritable(count));
	}
}
