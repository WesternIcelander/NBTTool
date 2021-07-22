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

/**
 * This class is a wrapper for the NMS NBTTagCompound. All the methods in here
 * should be self explanatory, so I didn't bother writing javadocs for each
 * method.
 *
 * @author Siggi
 * @param <C> the class implementing NBTCompound.
 * @param <L> the class implementing NBTList.
 */
public abstract class NBTCompound<C extends NBTCompound, L extends NBTList> implements Cloneable {

	public abstract Object getNMSCompound();

	public abstract int getType(String key);

	public abstract byte getByte(String key);

	public abstract void setByte(String key, byte value);

	public abstract short getShort(String key);

	public abstract void setShort(String key, short value);

	public abstract int getInt(String key);

	public abstract void setInt(String key, int value);

	public abstract long getLong(String key);

	public abstract void setLong(String key, long value);

	public abstract float getFloat(String key);

	public abstract void setFloat(String key, float value);

	public abstract double getDouble(String key);

	public abstract void setDouble(String key, double value);

	public abstract byte[] getByteArray(String key);

	public abstract void setByteArray(String key, byte[] value);

	public abstract String getString(String key);

	public abstract void setString(String key, String value);

	public abstract L getList(String key);

	public abstract void setList(String key, L value);

	public abstract C getCompound(String key);

	public abstract void setCompound(String key, C value);

	public abstract int[] getIntArray(String key);

	public abstract void setIntArray(String key, int[] value);

	public abstract Set<String> keySet();

	public abstract int size();

	public abstract void remove(String key);

	@Override
	public abstract NBTCompound clone();
}
