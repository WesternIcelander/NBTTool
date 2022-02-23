package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

final class NBTIntArray implements NBTBase {

	static final int[] empty = new int[0];
	private int[] value;

	public NBTIntArray() {
		this(empty);
	}
	public NBTIntArray(int[] value) {
		if (value == null) value = empty;
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = new int[in.readInt()];
		for (int i = 0; i < value.length; i++) {
			value[i] = in.readInt();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(value.length);
		for (int v : value) {
			out.writeInt(v);
		}
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.IntArray;
	}

	public int[] getValue() {
		return value;
	}

	@Override
	public NBTIntArray copy() {
		return new NBTIntArray(Arrays.copyOf(value, value.length));
	}
}
