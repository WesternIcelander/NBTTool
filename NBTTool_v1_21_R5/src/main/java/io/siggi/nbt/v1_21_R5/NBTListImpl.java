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
import io.siggi.nbt.NBTType;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;

final class NBTListImpl extends NBTList {

	public final ListTag list;

	public NBTListImpl() {
		super(true);
		this.list = new ListTag();
	}

	public NBTListImpl(ListTag list) {
		super(true);
		this.list = list;
	}

	@Override
	public ListTag getNMSList() {
		return list;
	}

	@Override
	public int getTypeId() {
		return ((int) list.identifyRawElementType()) & 0xff;
	}

	@Override
	public void addByte(byte value) {
		list.add(ByteTag.valueOf(value));
	}

	@Override
	public byte getByte(int key) {
		if (list.get(key).getId() != NBTType.Byte.id) {
			return (byte) 0;
		}
		return ((ByteTag) list.get(key)).byteValue();
	}

	@Override
	public void setByte(int key, byte value) {
		list.set(key, ByteTag.valueOf(value));
	}

	@Override
	public void addShort(short value) {
		list.add(ShortTag.valueOf(value));
	}

	@Override
	public short getShort(int key) {
		if (list.get(key).getId() != NBTType.Short.id) {
			return (short) 0;
		}
		return ((ShortTag) list.get(key)).shortValue();
	}

	@Override
	public void setShort(int key, short value) {
		list.set(key, ShortTag.valueOf(value));
	}

	@Override
	public void addInt(int value) {
		list.add(IntTag.valueOf(value));
	}

	@Override
	public int getInt(int key) {
		if (list.get(key).getId() != NBTType.Int.id) {
			return 0;
		}
		return ((IntTag) list.get(key)).intValue();
	}

	@Override
	public void setInt(int key, int value) {
		list.set(key, IntTag.valueOf(value));
	}

	@Override
	public void addLong(long value) {
		list.add(LongTag.valueOf(value));
	}

	@Override
	public long getLong(int key) {
		if (list.get(key).getId() != NBTType.Long.id) {
			return 0L;
		}
		return ((LongTag) list.get(key)).longValue();

	}

	@Override
	public void setLong(int key, long value) {
		list.set(key, LongTag.valueOf(value));
	}

	@Override
	public void addFloat(float value) {
		list.add(FloatTag.valueOf(value));
	}

	@Override
	public float getFloat(int key) {
		if (list.get(key).getId() != NBTType.Float.id) {
			return 0.0F;
		}
		return ((FloatTag) list.get(key)).floatValue();
	}

	@Override
	public void setFloat(int key, float value) {
		list.set(key, FloatTag.valueOf(value));
	}

	@Override
	public void addDouble(double value) {
		list.add(DoubleTag.valueOf(value));
	}

	@Override
	public double getDouble(int key) {
		if (list.get(key).getId() != NBTType.Double.id) {
			return 0.0D;
		}
		return ((DoubleTag) list.get(key)).doubleValue();
	}

	@Override
	public void setDouble(int key, double value) {
		list.set(key, DoubleTag.valueOf(value));
	}

	@Override
	public void addByteArray(byte[] value) {
		list.add(new ByteArrayTag(value));
	}

	@Override
	public byte[] getByteArray(int key) {
		if (list.get(key).getId() != NBTType.ByteArray.id) {
			return new byte[0];
		}
		return ((ByteArrayTag) list.get(key)).getAsByteArray();
	}

	@Override
	public void setByteArray(int key, byte[] value) {
		list.set(key, new ByteArrayTag(value));
	}

	@Override
	public void addString(String value) {
		list.add(StringTag.valueOf(value));
	}

	@Override
	public String getString(int key) {
		if (list.get(key).getId() != NBTType.String.id) {
			return "";
		}
		return ((StringTag) list.get(key)).asString().orElse(null);
	}

	@Override
	public void setString(int key, String value) {
		list.set(key, StringTag.valueOf(value));
	}

	@Override
	public void addList(NBTList value) {
		list.add(value.getNMSList());
	}

	@Override
	public NBTListImpl getList(int key) {
		if (list.get(key).getId() != NBTType.List.id) {
			return new NBTListImpl();
		}
		return new NBTListImpl((ListTag) list.get(key));
	}

	@Override
	public void setList(int key, NBTList value) {
		list.set(key, value.getNMSList());
	}

	@Override
	public void addCompound(NBTCompound value) {
		list.add(value.getNMSCompound());
	}

	@Override
	public NBTCompound getCompound(int key) {
		if (list.get(key).getId() != NBTType.Compound.id) {
			return new NBTCompoundImpl();
		}
		return new NBTCompoundImpl((CompoundTag) list.get(key));
	}

	@Override
	public void setCompound(int key, NBTCompound value) {
		list.set(key, value.getNMSCompound());
	}

	@Override
	public void addIntArray(int[] value) {
		list.add(new IntArrayTag(value));
	}

	@Override
	public int[] getIntArray(int key) {
		if (list.get(key).getId() != NBTType.IntArray.id) {
			return new int[0];
		}
		return ((IntArrayTag) list.get(key)).getAsIntArray();
	}

	@Override
	public void setIntArray(int key, int[] value) {
		list.set(key, new IntArrayTag(value));
	}

	@Override
	public void addLongArray(long[] value) {
		list.add(new LongArrayTag(value));
	}

	@Override
	public long[] getLongArray(int key) {
		if (list.get(key).getId() != NBTType.LongArray.id) {
			return new long[0];
		}
		return ((LongArrayTag) list.get(key)).getAsLongArray();
	}

	@Override
	public void setLongArray(int key, long[] value) {
		list.set(key, new LongArrayTag(value));
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void remove(int key) {
		list.remove(key);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof NBTListImpl) {
			return ((NBTListImpl) object).list.equals(list);
		} else {
			return super.equals(object);
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + list.hashCode();
		return hash;
	}

	@Override
	public NBTList copy() {
		return new NBTListImpl((ListTag) list.copy());
	}
}
