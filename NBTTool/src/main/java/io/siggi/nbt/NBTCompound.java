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
package io.siggi.nbt;

import java.util.Set;
import java.util.UUID;

/**
 * This class is a wrapper for the NMS NBTTagCompound. All the methods in here
 * should be self explanatory, so I didn't bother writing javadocs for each
 * method.
 *
 * @author Siggi
 */
public class NBTCompound {

	private final NBTCompound implementation;

	public NBTCompound() {
		try {
			this.implementation = NBTTool.nbtutil.newCompound();
		} catch (NullPointerException e) {
			throw new UnsupportedOperationException();
		}
	}

	protected NBTCompound(boolean impl) {
		this.implementation = null;
	}

	public <T> T getNMSCompound() {
		return implementation.getNMSCompound();
	}

	public int getTypeId(String key) {
		return implementation.getTypeId(key);
	}

	public NBTType getType(String key) {
		int id = getTypeId(key);
		if (id == 0)
			return null;
		return NBTType.getById(id);
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
		return implementation.getList(key);
	}

	public void setList(String key, NBTList value) {
		implementation.setList(key, value);
	}

	public NBTCompound getCompound(String key) {
		return implementation.getCompound(key);
	}

	public void setCompound(String key, NBTCompound value) {
		implementation.setCompound(key, value);
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

	/**
	 * Get a UUID by reading it from an int array of 4 ints which is how UUIDs are stored in Minecraft 1.16 and newer.
	 *
	 * @param key the key to read from
	 * @return a UUID or null if the value is not an int array of 4 ints.
	 */
	public UUID getUUID(String key) {
		try {
			int[] intArray = getIntArray(key);
			if (intArray.length != 4)
				return null;
			return new UUID(
					(((long) intArray[0]) << 32) | ((long) intArray[1]),
					(((long) intArray[2]) << 32) | ((long) intArray[3])
			);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Store a UUID by writing it as an int array of 4 ints, which is how UUIDs are stored in Minecraft 1.16 and newer.
	 *
	 * @param key the key to store to
	 * @param value the UUIID to store
	 */
	public void setUUID(String key, UUID value) {
		long mostSignificant = value.getMostSignificantBits();
		long leastSignificant = value.getLeastSignificantBits();
		int[] intArray = new int[]{
				(int) ((mostSignificant >> 32) & 0xffffffffL),
				(int) (mostSignificant & 0xffffffffL),
				(int) ((leastSignificant >> 32) & 0xffffffffL),
				(int) (leastSignificant & 0xffffffffL)
		};
		setIntArray(key, intArray);
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
		return implementation.copy();
	}
}
