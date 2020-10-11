package org.wendex;

import org.apache.hadoop.mapreduce.Reducer;
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
		String airportName = it.next().toString();
		if (!it.hasNext()) {
			return;
		}
		double sum = 0;
		double cnt = 0;
		double max = Double.NEGATIVE_INFINITY;
		double min = Double.POSITIVE_INFINITY;
		for (Double delay = Double.parseDouble(it.next().toString()); it.hasNext();
			 delay = Double.parseDouble(it.next().toString())) {
			cnt++;
			sum += delay;
			if (delay < min) {
				min = delay;
			}

			if (delay > max) {
				max = delay;
			}
		}

		sum = sum / cnt;
		StringBuilder sb = new StringBuilder(airportName);
		sb.append("\t\t");
		sb.append(min);
		sb.append('\t');
		sb.append(sum);
		sb.append('\t');
		sb.append(max);
		Text result = new Text(sb.toString());
		context.write(new IntWritable(key.getId()), result);
	}
}
