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
package io.siggi.nbt.v1_16_R1;

import io.siggi.nbt.NBTType;
import net.minecraft.server.v1_16_R1.NBTTagByte;
import net.minecraft.server.v1_16_R1.NBTTagByteArray;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagDouble;
import net.minecraft.server.v1_16_R1.NBTTagFloat;
import net.minecraft.server.v1_16_R1.NBTTagInt;
import net.minecraft.server.v1_16_R1.NBTTagIntArray;
import net.minecraft.server.v1_16_R1.NBTTagList;
import net.minecraft.server.v1_16_R1.NBTTagLong;
import net.minecraft.server.v1_16_R1.NBTTagLongArray;
import net.minecraft.server.v1_16_R1.NBTTagShort;
import net.minecraft.server.v1_16_R1.NBTTagString;

final class NBTList extends io.siggi.nbt.NBTList<NBTCompound, NBTList> {

	public final NBTTagList list;

	public NBTList() {
		this.list = new NBTTagList();
	}

	public NBTList(NBTTagList list) {
		this.list = list;
	}

	@Override
	public NBTTagList getNMSList() {
		return list;
	}

	@Override
	public int getType() {
		return ((int) list.d_()) & 0xff;
	}

	@Override
	public void addByte(byte value) {
		list.add(NBTTagByte.a(value));
	}

	@Override
	public byte getByte(int key) {
		if (list.get(key).getTypeId() != NBTType.Byte.id) {
			return (byte) 0;
		}
		return ((NBTTagByte) list.get(key)).asByte();
	}

	@Override
	public void setByte(int key, byte value) {
		list.set(key, NBTTagByte.a(value));
	}

	@Override
	public void addShort(short value) {
		list.add(NBTTagShort.a(value));
	}

	@Override
	public short getShort(int key) {
		if (list.get(key).getTypeId() != NBTType.Short.id) {
			return (short) 0;
		}
		return ((NBTTagShort) list.get(key)).asShort();
	}

	@Override
	public void setShort(int key, short value) {
		list.set(key, NBTTagShort.a(value));
	}

	@Override
	public void addInt(int value) {
		list.add(NBTTagInt.a(value));
	}

	@Override
	public int getInt(int key) {
		if (list.get(key).getTypeId() != NBTType.Int.id) {
			return 0;
		}
		return ((NBTTagInt) list.get(key)).asInt();
	}

	@Override
	public void setInt(int key, int value) {
		list.set(key, NBTTagInt.a(value));
	}

	@Override
	public void addLong(long value) {
		list.add(NBTTagLong.a(value));
	}

	@Override
	public long getLong(int key) {
		if (list.get(key).getTypeId() != NBTType.Long.id) {
			return 0L;
		}
		return ((NBTTagLong) list.get(key)).asLong();

	}

	@Override
	public void setLong(int key, long value) {
		list.set(key, NBTTagLong.a(value));
	}

	@Override
	public void addFloat(float value) {
		list.add(NBTTagFloat.a(value));
	}

	@Override
	public float getFloat(int key) {
		if (list.get(key).getTypeId() != NBTType.Float.id) {
			return 0.0F;
		}
		return ((NBTTagFloat) list.get(key)).asFloat();
	}

	@Override
	public void setFloat(int key, float value) {
		list.set(key, NBTTagFloat.a(value));
	}

	@Override
	public void addDouble(double value) {
		list.add(NBTTagDouble.a(value));
	}

	@Override
	public double getDouble(int key) {
		if (list.get(key).getTypeId() != NBTType.Double.id) {
			return 0.0D;
		}
		return ((NBTTagDouble) list.get(key)).asDouble();
	}

	@Override
	public void setDouble(int key, double value) {
		list.set(key, NBTTagDouble.a(value));
	}

	@Override
	public void addByteArray(byte[] value) {
		list.add(new NBTTagByteArray(value));
	}

	@Override
	public byte[] getByteArray(int key) {
		if (list.get(key).getTypeId() != NBTType.ByteArray.id) {
			return new byte[0];
		}
		return ((NBTTagByteArray) list.get(key)).getBytes();
	}

	@Override
	public void setByteArray(int key, byte[] value) {
		list.set(key, new NBTTagByteArray(value));
	}

	@Override
	public void addString(String value) {
		list.add(NBTTagString.a(value));
	}

	@Override
	public String getString(int key) {
		if (list.get(key).getTypeId() != NBTType.String.id) {
			return "";
		}
		return ((NBTTagString) list.get(key)).asString();
	}

	@Override
	public void setString(int key, String value) {
		list.set(key, NBTTagString.a(value));
	}

	@Override
	public void addList(NBTList value) {
		list.add(value.list);
	}

	@Override
	public NBTList getList(int key) {
		if (list.get(key).getTypeId() != NBTType.List.id) {
			return new NBTList();
		}
		return new NBTList((NBTTagList) list.get(key));
	}

	@Override
	public void setList(int key, NBTList value) {
		list.set(key, value.list);
	}

	@Override
	public void addCompound(NBTCompound value) {
		list.add(value.compound);
	}

	@Override
	public NBTCompound getCompound(int key) {
		if (list.get(key).getTypeId() != NBTType.Compound.id) {
			return new NBTCompound();
		}
		return new NBTCompound((NBTTagCompound) list.get(key));
	}

	@Override
	public void setCompound(int key, NBTCompound value) {
		list.set(key, value.compound);
	}

	@Override
	public void addIntArray(int[] value) {
		list.add(new NBTTagIntArray(value));
	}

	@Override
	public int[] getIntArray(int key) {
		if (list.get(key).getTypeId() != NBTType.IntArray.id) {
			return new int[0];
		}
		return ((NBTTagIntArray) list.get(key)).getInts();
	}

	@Override
	public void setIntArray(int key, int[] value) {
		list.set(key, new NBTTagIntArray(value));
	}

	@Override
	public void addLongArray(long[] value) {
		list.add(new NBTTagLongArray(value));
	}

	@Override
	public long[] getLongArray(int key) {
		if (list.get(key).getTypeId() != NBTType.LongArray.id) {
			return new long[0];
		}
		return ((NBTTagLongArray) list.get(key)).getLongs();
	}

	@Override
	public void setLongArray(int key, long[] value) {
		list.set(key, new NBTTagLongArray(value));
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
		if (object instanceof NBTList) {
			return ((NBTList) object).list.equals(list);
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
		return new NBTList((NBTTagList) list.clone());
	}
}