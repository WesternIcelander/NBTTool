package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

final class NBTByteArray implements NBTBase {

	static final byte[] empty = new byte[0];
	private byte[] value;

	public NBTByteArray() {
		this(empty);
	}
	public NBTByteArray(byte[] value) {
		if (value == null) value = empty;
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = new byte[in.readInt()];
		in.readFully(value);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(value.length);
		out.write(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.ByteArray;
	}

	public byte[] getValue() {
		return value;
	}

	@Override
	public NBTByteArray copy() {
		return new NBTByteArray(Arrays.copyOf(value, value.length));
	}
}
