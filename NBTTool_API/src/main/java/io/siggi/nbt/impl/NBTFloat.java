package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTFloat implements NBTNumber {

	private float value;

	public NBTFloat() {
	}

	public NBTFloat(float value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readFloat();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeFloat(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Float;
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
		return value;
	}

	@Override
	public double getAsDouble() {
		return (double) value;
	}

	@Override
	public NBTFloat copy() {
		return new NBTFloat(value);
	}
}
