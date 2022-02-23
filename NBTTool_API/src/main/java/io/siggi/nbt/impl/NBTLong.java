package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTLong implements NBTNumber {

	private long value;

	public NBTLong() {
	}

	public NBTLong(long value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Long;
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
		return value;
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
	public NBTLong copy() {
		return new NBTLong(value);
	}
}
