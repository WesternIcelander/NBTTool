package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTInt implements NBTNumber {

	private int value;

	public NBTInt() {
	}

	public NBTInt(int value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Int;
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
		return value;
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
	public NBTInt copy() {
		return new NBTInt(value);
	}
}
