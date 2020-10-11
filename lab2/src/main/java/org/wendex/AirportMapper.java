package org.wendex;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;
import java.util.regex.*;

public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Pattern pattern = Pattern.compile("[a-z0-9а-я]+", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(value.toString());
		while(matcher.find()) {
			try {
				context.write(new Text(matcher.group().toLowerCase()), new IntWritable(1));					
			} catch(Exception exception) {
				throw new IOException(exception);			
			}
		}
	}
}
