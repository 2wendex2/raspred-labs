package org.wendex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AirportWritable implements Writable {
	private int id;
	private String name;

	private void readFromLine(String line) {
		String[] arr = line.replaceAll("\"\"", "\"").split(",");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].charAt(0) == '\"') {
				arr[i] = arr[i].substring(1, arr[i].length() - 1);
			}
		}
		id = Integer.parseInt(arr[0]);
		name = arr[1];
	}

	public AirportWritable(Text text) {
		readFromLine(text.toString());
	}

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
		readFromLine(s);
	}

	public AirportWritableComparable toAirportWritableComparable() {
		return new AirportWritableComparable(id, false);
	}
}
