package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTShort implements NBTNumber {

	private short value;

	public NBTShort() {
	}

	public NBTShort(short value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readShort();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeShort(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Short;
	}

	@Override
	public byte getAsByte() {
		return (byte) value;
	}

	@Override
	public short getAsShort() {
		return value;
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
		return (double) value;
	}

	@Override
	public NBTShort copy() {
		return new NBTShort(value);
	}
}
