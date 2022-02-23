package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTByte implements NBTNumber {

	private byte value;

	public NBTByte() {
	}

	public NBTByte(byte value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readByte();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeByte(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Byte;
	}

	@Override
	public byte getAsByte() {
		return value;
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
		return (double) value;
	}

	@Override
	public NBTByte copy() {
		return new NBTByte(value);
	}
}
