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

/**
 * This class is a wrapper for the NMS NBTTagList. All the methods in here
 * should be self explanatory, so I didn't bother writing javadocs for each
 * method.
 *
 * @author Siggi
 * @param <C> the class implementing NBTCompound.
 * @param <L> the class implementing NBTList.
 */
public abstract class NBTList<C extends NBTCompound, L extends NBTList> implements Cloneable {

	public abstract Object getNMSList();

	public abstract int getType();

	public abstract void addByte(byte value);

	public abstract byte getByte(int key);

	public abstract void setByte(int key, byte value);

	public abstract void addShort(short value);

	public abstract short getShort(int key);

	public abstract void setShort(int key, short value);

	public abstract void addInt(int value);

	public abstract int getInt(int key);

	public abstract void setInt(int key, int value);

	public abstract void addLong(long value);

	public abstract long getLong(int key);

	public abstract void setLong(int key, long value);

	public abstract void addFloat(float value);

	public abstract float getFloat(int key);

	public abstract void setFloat(int key, float value);

	public abstract void addDouble(double value);

	public abstract double getDouble(int key);

	public abstract void setDouble(int key, double value);

	public abstract void addByteArray(byte[] value);

	public abstract byte[] getByteArray(int key);

	public abstract void setByteArray(int key, byte[] value);

	public abstract void addString(String value);

	public abstract String getString(int key);

	public abstract void setString(int key, String value);

	public abstract void addList(L value);

	public abstract L getList(int key);

	public abstract void setList(int key, L value);

	public abstract void addCompound(C value);

	public abstract C getCompound(int key);

	public abstract void setCompound(int key, C value);

	public abstract void addIntArray(int[] value);

	public abstract int[] getIntArray(int key);

	public abstract void setIntArray(int key, int[] value);

	public abstract int size();

	public abstract void remove(int key);

	@Override
	public abstract NBTList clone();
}
