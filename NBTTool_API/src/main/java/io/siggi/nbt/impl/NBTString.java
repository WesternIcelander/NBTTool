package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

final class NBTString implements NBTBase {
	private String value;

	public NBTString() {
		this("");
	}

	public NBTString(String value) {
		if (value == null) value = "";
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		value = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(value);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.String;
	}

	public String getValue() {
		return value;
	}

	@Override
	public NBTBase copy() {
		return null;
	}
}
