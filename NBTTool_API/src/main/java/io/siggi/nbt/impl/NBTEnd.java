package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTEnd implements NBTBase {
	@Override
	public void read(DataInput in) throws IOException {
	}

	@Override
	public void write(DataOutput out) throws IOException {
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.End;
	}

	@Override
	public NBTEnd copy() {
		return new NBTEnd();
	}
}
