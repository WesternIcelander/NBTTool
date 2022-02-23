package io.siggi.nbt.impl;

import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

interface NBTBase {
	public void read(DataInput in) throws IOException;

	public void write(DataOutput out) throws IOException;

	public NBTType getNBTType();

	public NBTBase copy();
}
