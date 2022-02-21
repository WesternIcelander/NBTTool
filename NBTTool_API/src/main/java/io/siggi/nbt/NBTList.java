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

/**
 * This class is a wrapper for the NMS NBTTagList. All the methods in here
 * should be self explanatory, so I didn't bother writing javadocs for each
 * method.
 *
 * @author Siggi
 */
public class NBTList {

	private final NBTList implementation;

	public NBTList() {
		try {
			this.implementation = NBTTool.nbtutil.newList();
		} catch (NullPointerException e) {
			throw new UnsupportedOperationException();
		}
	}

	protected NBTList(boolean impl) {
		this.implementation = null;
	}

	public <T> T getNMSList() {
		return implementation.getNMSList();
	}

	public int getTypeId() {
		return implementation.getTypeId();
	}

	public NBTType getType() {
		return NBTType.getById(getTypeId());
	}

	public void addByte(byte value) {
		implementation.addByte(value);
	}

	public byte getByte(int key) {
		return implementation.getByte(key);
	}

	public void setByte(int key, byte value) {
		implementation.setByte(key, value);
	}

	public void addShort(short value) {
		implementation.addShort(value);
	}

	public short getShort(int key) {
		return implementation.getShort(key);
	}

	public void setShort(int key, short value) {
		implementation.setShort(key, value);
	}

	public void addInt(int value) {
		implementation.addInt(value);
	}

	public int getInt(int key) {
		return implementation.getInt(key);
	}

	public void setInt(int key, int value) {
		implementation.setInt(key, value);
	}

	public void addLong(long value) {
		implementation.addLong(value);
	}

	public long getLong(int key) {
		return implementation.getLong(key);
	}

	public void setLong(int key, long value) {
		implementation.setLong(key, value);
	}

	public void addFloat(float value) {
		implementation.addFloat(value);
	}

	public float getFloat(int key) {
		return implementation.getFloat(key);
	}

	public void setFloat(int key, float value) {
		implementation.setFloat(key, value);
	}

	public void addDouble(double value) {
		implementation.addDouble(value);
	}

	public double getDouble(int key) {
		return implementation.getDouble(key);
	}

	public void setDouble(int key, double value) {
		implementation.setDouble(key, value);
	}

	public void addByteArray(byte[] value) {
		implementation.addByteArray(value);
	}

	public byte[] getByteArray(int key) {
		return implementation.getByteArray(key);
	}

	public void setByteArray(int key, byte[] value) {
		implementation.setByteArray(key, value);
	}

	public void addString(String value) {
		implementation.addString(value);
	}

	public String getString(int key) {
		return implementation.getString(key);
	}

	public void setString(int key, String value) {
		implementation.setString(key, value);
	}

	public void addList(NBTList value) {
		implementation.addList(value);
	}

	public NBTList getList(int key) {
		return implementation.getList(key);
	}

	public void setList(int key, NBTList value) {
		implementation.setList(key, value);
	}

	public void addCompound(NBTCompound value) {
		implementation.addCompound(value);
	}

	public NBTCompound getCompound(int key) {
		return implementation.getCompound(key);
	}

	public void setCompound(int key, NBTCompound value) {
		implementation.setCompound(key, value);
	}

	public void addIntArray(int[] value) {
		implementation.addIntArray(value);
	}

	public int[] getIntArray(int key) {
		return implementation.getIntArray(key);
	}

	public void setIntArray(int key, int[] value) {
		implementation.setIntArray(key, value);
	}

	public void addLongArray(long[] value) {
		implementation.addLongArray(value);
	}

	public long[] getLongArray(int key) {
		return implementation.getLongArray(key);
	}

	public void setLongArray(int key, long[] value) {
		implementation.setLongArray(key, value);
	}

	public int size() {
		return implementation.size();
	}

	public void remove(int key) {
		implementation.remove(key);
	}

	public NBTList copy() {
		return implementation.copy();
	}
}
