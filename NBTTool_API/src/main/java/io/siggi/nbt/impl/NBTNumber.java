package io.siggi.nbt.impl;

interface NBTNumber extends NBTBase {
	public byte getAsByte();

	public short getAsShort();

	public int getAsInt();

	public long getAsLong();

	public float getAsFloat();

	public double getAsDouble();
}
