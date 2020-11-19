package org.wendex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;
import java.util.Optional;

public class AirportMapper extends Mapper<LongWritable, Text, AirportWritableComparable, Text> {

	public static final int AIRPORT_COLUMN = 1;
	public static final int AIRPORT_CODE = 0;

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] arr = CsvTools.read(value.toString());

		Optional<Integer> id = safeParseInt(arr[AIRPORT_CODE]);
		id.ifPresent(v -> {
			try {
				context.write(new AirportWritableComparable(v, false), new Text(arr[AIRPORT_COLUMN]));
			} catch (Exception exception) {
				throw new RuntimeException(exception)
			}
		});


	}

	private static Optional<Integer> safeParseInt(String s) {
		try {
			return Optional.of(Integer.parseInt(s));
		} catch (Exception exception) {
			return Optional.empty();
		}
	}
}
