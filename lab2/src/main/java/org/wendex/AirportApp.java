package org.wendex;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;

public class AirportApp {
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage: AirportApp <airports input path> <flights input path> <output path>");
			System.exit(-1);
		}
		Job job = Job.getInstance();
		job.setJarByClass(AirportApp.class);
		job.setJobName("Airport");
		MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, AirportMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, FlightMapper.class);
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		job.setPartitionerClass(AirportPartitioner.class);
		job.setReducerClass(AirportReducer.class);
		job.setOutputKeyClass(AirportWritableComparable.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(2);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
