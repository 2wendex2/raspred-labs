package org.wendex;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;

public class AirportApp {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: AirportApp <input path> <output path>");
			System.exit(-1);
		}
		Job job = Job.getInstance();
		job.setJarByClass(AirportApp.class);
		job.setJobName("Airport");
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(AirportMapper.class);
		job.setReducerClass(AirportReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(2);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
