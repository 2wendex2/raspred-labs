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
		try {
			id = Integer.parseInt(arr[0]);
			name = arr[1];
		} catch (NumberFormatException exception) {
			id = -1;
		}
	}

	public boolean isValid() {
		return id >= 0;
	}

	public AirportWritable(Text text) {
		readFromLine(text.toString());
	}

	public AirportWritable() {}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(id);
		dataOutput.writeChars(name);
		dataOutput.writeChar('\n');
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		id = dataInput.readInt();
		name = dataInput.readLine();
	}

	public AirportWritableComparable toAirportWritableComparable() {
		return new AirportWritableComparable(id, false);
	}
}
