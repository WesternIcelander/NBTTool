/*
 * The MIT License
 *
 * Copyright 2018 Siggi.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.siggi.nbt.v1_21_R5;

import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.Set;

final class NBTCompoundImpl extends NBTCompound {

	private static final byte[] emptyByteArray = new byte[0];
	private static final int[] emptyIntArray = new int[0];
	private static final long[] emptyLongArray = new long[0];

	public final CompoundTag compound;

	public NBTCompoundImpl() {
		super(true);
		this.compound = new CompoundTag();
	}

	public NBTCompoundImpl(CompoundTag compound) {
		super(true);
		this.compound = compound;
	}

	@Override
	public CompoundTag getNMSCompound() {
		return compound;
	}

	@Override
	public int getTypeId(String key) {
		try {
			return compound.get(key).getId();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public byte getByte(String key) {
		return compound.getByteOr(key, (byte) 0);
	}

	@Override
	public void setByte(String key, byte value) {
		compound.putByte(key, value);
	}

	@Override
	public short getShort(String key) {
		return compound.getShortOr(key, (byte) 0);
	}

	@Override
	public void setShort(String key, short value) {
		compound.putShort(key, value);
	}

	@Override
	public int getInt(String key) {
		return compound.getIntOr(key, 0);
	}

	@Override
	public void setInt(String key, int value) {
		compound.putInt(key, value);
	}

	@Override
	public long getLong(String key) {
		return compound.getLongOr(key, 0L);
	}

	@Override
	public void setLong(String key, long value) {
		compound.putLong(key, value);
	}

	@Override
	public float getFloat(String key) {
		return compound.getFloatOr(key, 0.0f);
	}

	@Override
	public void setFloat(String key, float value) {
		compound.putFloat(key, value);
	}

	@Override
	public double getDouble(String key) {
		return compound.getDoubleOr(key, 0.0);
	}

	@Override
	public void setDouble(String key, double value) {
		compound.putDouble(key, value);
	}

	@Override
	public byte[] getByteArray(String key) {
		return compound.getByteArray(key).orElse(emptyByteArray);
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		compound.putByteArray(key, value);
	}

	@Override
	public String getString(String key) {
		return compound.getStringOr(key, "");
	}

	@Override
	public void setString(String key, String value) {
		compound.putString(key, value);
	}

	@Override
	public NBTListImpl getList(String key) {
		Tag base = compound.get(key);
		if (base instanceof ListTag) {
			return new NBTListImpl((ListTag) base);
		}
		return new NBTListImpl();
	}

	@Override
	public void setList(String key, NBTList list) {
		this.compound.put(key, (ListTag) list.getNMSList());
	}

	@Override
	public NBTCompound getCompound(String key) {
		return new NBTCompoundImpl(compound.getCompoundOrEmpty(key));
	}

	@Override
	public void setCompound(String key, NBTCompound compound) {
		this.compound.put(key, (CompoundTag) compound.getNMSCompound());
	}

	@Override
	public int[] getIntArray(String key) {
		return this.compound.getIntArray(key).orElse(emptyIntArray);
	}

	@Override
	public void setIntArray(String key, int[] value) {
		this.compound.putIntArray(key, value);
	}

	@Override
	public long[] getLongArray(String key) {
		return this.compound.getLongArray(key).orElse(emptyLongArray);
	}

	@Override
	public void setLongArray(String key, long[] value) {
		this.compound.putLongArray(key, value);
	}

	@Override
	public Set<String> keySet() {
		return this.compound.keySet();
	}

	@Override
	public int size() {
		return this.compound.size();
	}

	@Override
	public void remove(String key) {
		this.compound.remove(key);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof NBTCompoundImpl) {
			return ((NBTCompoundImpl) object).compound.equals(compound);
		} else {
			return super.equals(object);
		}
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + compound.hashCode();
		return hash;
	}

	@Override
	public NBTCompound copy() {
		return new NBTCompoundImpl((CompoundTag) compound.copy());
	}
}
