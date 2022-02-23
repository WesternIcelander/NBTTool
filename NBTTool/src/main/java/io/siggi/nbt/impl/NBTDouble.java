package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTDouble implements NBTNumber {

	private double value;

	public NBTDouble() {
	}

	public NBTDouble(double value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Double;
	}

	@Override
	public byte getAsByte() {
		return (byte) value;
	}

	@Override
	public short getAsShort() {
		return (short) value;
	}

	@Override
	public int getAsInt() {
		return (int) value;
	}

	@Override
	public long getAsLong() {
		return (long) value;
	}

	@Override
	public float getAsFloat() {
		return (float) value;
	}

	@Override
	public double getAsDouble() {
		return value;
	}

	@Override
	public NBTDouble copy() {
		return new NBTDouble(value);
	}
}
