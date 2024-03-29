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
package hk.siggi.bukkit.nbt;

import java.util.Set;

@Deprecated
public class NBTCompound {

	private final io.siggi.nbt.NBTCompound implementation;

	public NBTCompound() {
		try {
			this.implementation = new io.siggi.nbt.NBTCompound();
		} catch (NullPointerException e) {
			throw new UnsupportedOperationException();
		}
	}

	public NBTCompound(io.siggi.nbt.NBTCompound implementation) {
		this.implementation = implementation;
	}

	public <T> T getNMSCompound() {
		return implementation.getNMSCompound();
	}

	public int getType(String key) {
		return implementation.getTypeId(key);
	}

	public byte getByte(String key) {
		return implementation.getByte(key);
	}

	public void setByte(String key, byte value) {
		implementation.setByte(key, value);
	}

	public short getShort(String key) {
		return implementation.getShort(key);
	}

	public void setShort(String key, short value) {
		implementation.setShort(key, value);
	}

	public int getInt(String key) {
		return implementation.getInt(key);
	}

	public void setInt(String key, int value) {
		implementation.setInt(key, value);
	}

	public long getLong(String key) {
		return implementation.getLong(key);
	}

	public void setLong(String key, long value) {
		implementation.setLong(key, value);
	}

	public float getFloat(String key) {
		return implementation.getFloat(key);
	}

	public void setFloat(String key, float value) {
		implementation.setFloat(key, value);
	}

	public double getDouble(String key) {
		return implementation.getDouble(key);
	}

	public void setDouble(String key, double value) {
		implementation.setDouble(key, value);
	}

	public byte[] getByteArray(String key) {
		return implementation.getByteArray(key);
	}

	public void setByteArray(String key, byte[] value) {
		implementation.setByteArray(key, value);
	}

	public String getString(String key) {
		return implementation.getString(key);
	}

	public void setString(String key, String value) {
		implementation.setString(key, value);
	}

	public NBTList getList(String key) {
		return new NBTList(implementation.getList(key));
	}

	public void setList(String key, NBTList value) {
		implementation.setList(key, value.unwrap());
	}

	public NBTCompound getCompound(String key) {
		return new NBTCompound(implementation.getCompound(key));
	}

	public void setCompound(String key, NBTCompound value) {
		implementation.setCompound(key, value.unwrap());
	}

	public int[] getIntArray(String key) {
		return implementation.getIntArray(key);
	}

	public void setIntArray(String key, int[] value) {
		implementation.setIntArray(key, value);
	}

	public long[] getLongArray(String key) {
		return implementation.getLongArray(key);
	}

	public void setLongArray(String key, long[] value) {
		implementation.setLongArray(key, value);
	}

	public Set<String> keySet() {
		return implementation.keySet();
	}

	public int size() {
		return implementation.size();
	}

	public void remove(String key) {
		implementation.remove(key);
	}

	public NBTCompound copy() {
		return new NBTCompound(implementation.copy());
	}

	io.siggi.nbt.NBTCompound unwrap() {
		return implementation;
	}
}
