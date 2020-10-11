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
	protected void reduce(AirportWritableComparable key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Iterator<Text> it = values.iterator();
		if (!it.hasNext()) {
			return;
		}
		String airportName = it.next().toString();
		double sum = 0;
		double cnt = 0;
		double max = Double.POSITIVE_INFINITY;
		double min = Double.NEGATIVE_INFINITY;
		for (Double delay = Double.parseDouble(it.next().toString()); it.hasNext();
			 delay = Double.parseDouble(it.next().toString())) {
			cnt++;
			sum += delay;

		}

		context.write(new IntWritable(key.getId()), .next());
	}
}
