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
package hk.siggi.bukkit.nbt.v1_16_R3;

import java.util.Set;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;

final class NBTCompound extends hk.siggi.bukkit.nbt.NBTCompound<NBTCompound, NBTList> {

	public final NBTTagCompound compound;

	public NBTCompound() {
		this.compound = new NBTTagCompound();
	}

	public NBTCompound(NBTTagCompound compound) {
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
	public NBTList getList(String key) {
		NBTBase base = compound.get(key);
		if (base instanceof NBTTagList) {
			return new NBTList((NBTTagList) base);
		}
		return new NBTList();
	}

	@Override
	public void setList(String key, NBTList list) {
		this.compound.set(key, list.list);
	}

	@Override
	public NBTCompound getCompound(String key) {
		return new NBTCompound(compound.getCompound(key));
	}

	@Override
	public void setCompound(String key, NBTCompound compound) {
		this.compound.set(key, compound.compound);
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
	public Set<String> keySet() {
		return this.compound.getKeys();
	}

	@Override
	public int size() {
		return this.compound.e();
	}

	@Override
	public void remove(String key) {
		this.compound.remove(key);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof NBTCompound) {
			return ((NBTCompound) object).compound.equals(compound);
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
	public NBTCompound clone() {
		return new NBTCompound((NBTTagCompound) compound.clone());
	}
}
