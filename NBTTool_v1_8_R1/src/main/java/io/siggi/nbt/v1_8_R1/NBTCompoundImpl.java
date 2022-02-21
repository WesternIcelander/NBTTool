/*
 * The MIT License
 *
 * Copyright 2017 Siggi.
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
package io.siggi.nbt.v1_8_R1;

import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import net.minecraft.server.v1_8_R1.NBTBase;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import java.util.Set;

final class NBTCompoundImpl extends NBTCompound {

	public final NBTTagCompound compound;

	public NBTCompoundImpl() {
		super(true);
		this.compound = new NBTTagCompound();
	}

	public NBTCompoundImpl(NBTTagCompound compound) {
		super(true);
		this.compound = compound;
	}

	@Override
	public NBTTagCompound getNMSCompound() {
		return compound;
	}

	@Override
	public int getType(String key) {
		return compound.get(key).getTypeId();
	}

	@Override
	public byte getByte(String key) {
		return compound.getByte(key);
	}

	@Override
	public void setByte(String key, byte value) {
		compound.setByte(key, value);
	}

	@Override
	public short getShort(String key) {
		return compound.getShort(key);
	}

	@Override
	public void setShort(String key, short value) {
		compound.setShort(key, value);
	}

	@Override
	public int getInt(String key) {
		return compound.getInt(key);
	}

	@Override
	public void setInt(String key, int value) {
		compound.setInt(key, value);
	}

	@Override
	public long getLong(String key) {
		return compound.getLong(key);
	}

	@Override
	public void setLong(String key, long value) {
		compound.setLong(key, value);
	}

	@Override
	public float getFloat(String key) {
		return compound.getFloat(key);
	}

	@Override
	public void setFloat(String key, float value) {
		compound.setFloat(key, value);
	}

	@Override
	public double getDouble(String key) {
		return compound.getDouble(key);
	}

	@Override
	public void setDouble(String key, double value) {
		compound.setDouble(key, value);
	}

	@Override
	public byte[] getByteArray(String key) {
		return compound.getByteArray(key);
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		compound.setByteArray(key, value);
	}

	@Override
	public String getString(String key) {
		return compound.getString(key);
	}

	@Override
	public void setString(String key, String value) {
		compound.setString(key, value);
	}

	@Override
	public NBTListImpl getList(String key) {
		NBTBase base = compound.get(key);
		if (base instanceof NBTTagList) {
			return new NBTListImpl((NBTTagList) base);
		}
		return new NBTListImpl();
	}

	@Override
	public void setList(String key, NBTList list) {
		this.compound.set(key, list.getNMSList());
	}

	@Override
	public NBTCompoundImpl getCompound(String key) {
		return new NBTCompoundImpl(compound.getCompound(key));
	}

	@Override
	public void setCompound(String key, NBTCompound compound) {
		this.compound.set(key, compound.getNMSCompound());
	}

	@Override
	public int[] getIntArray(String key) {
		return this.compound.getIntArray(key);
	}

	@Override
	public void setIntArray(String key, int[] value) {
		this.compound.setIntArray(key, value);
	}

	@Override
	public long[] getLongArray(String key) {
		throw new UnsupportedOperationException("LongArray is only available in Minecraft 1.13 and newer.");
	}

	@Override
	public void setLongArray(String key, long[] value) {
		throw new UnsupportedOperationException("LongArray is only available in Minecraft 1.13 and newer.");
	}

	@Override
	public Set<String> keySet() {
		return this.compound.c();
	}

	@Override
	public int size() {
		// 1.8 did not have a size() method yet.
		return keySet().size();
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
	public NBTCompoundImpl copy() {
		return new NBTCompoundImpl((NBTTagCompound) compound.clone());
	}
}
