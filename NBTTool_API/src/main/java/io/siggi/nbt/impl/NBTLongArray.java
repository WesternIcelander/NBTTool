package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

final class NBTLongArray implements NBTBase {

	static final long[] empty = new long[0];
	private long[] value;

	public NBTLongArray() {
		this(empty);
	}
	public NBTLongArray(long[] value) {
		if (value == null) value = empty;
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = new long[in.readInt()];
		for (int i = 0; i < value.length; i++) {
			value[i] = in.readLong();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(value.length);
		for (long v : value) {
			out.writeLong(v);
		}
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.LongArray;
	}

	public long[] getValue() {
		return value;
	}

	@Override
	public NBTLongArray copy() {
		return new NBTLongArray(Arrays.copyOf(value, value.length));
	}
}
