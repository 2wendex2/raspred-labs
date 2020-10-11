package org.wendex;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

public class AirportWritable implements Writable {
	private int id;
	private String name;

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeChars(Integer.toString(id));
		dataOutput.writeChars(",\"");
		dataOutput.writeChars(name.replaceAll("\"", "\"\""));
		dataOutput.writeChar('"');
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		String s = dataInput.readLine();
		String[] a = s.replaceAll("\"\"", "\"").split(",");
		for (String b : a) {
			if ()
		}
	}
}
