package org.wendex;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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
		for (int i = 0; i < a.length; i++) {
			if (a[i].charAt(0) == '\"') {
				a[i] = a[i].substring(1, a[i].length() - 1);\
			}
		}
		id = Integer.parseInt(a[0]);
		name = a[1];
	}

	public AirportWritableComparable toAirportWritableComparable() {
		return new AirportWritableComparable(id, 0);
	}
}
