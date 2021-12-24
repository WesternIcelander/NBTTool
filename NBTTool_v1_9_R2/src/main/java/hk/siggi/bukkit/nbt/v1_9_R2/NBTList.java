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
package hk.siggi.bukkit.nbt.v1_9_R2;

import hk.siggi.bukkit.nbt.NBTType;
import net.minecraft.server.v1_9_R2.NBTTagByte;
import net.minecraft.server.v1_9_R2.NBTTagByteArray;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.minecraft.server.v1_9_R2.NBTTagDouble;
import net.minecraft.server.v1_9_R2.NBTTagFloat;
import net.minecraft.server.v1_9_R2.NBTTagInt;
import net.minecraft.server.v1_9_R2.NBTTagIntArray;
import net.minecraft.server.v1_9_R2.NBTTagList;
import net.minecraft.server.v1_9_R2.NBTTagLong;
import net.minecraft.server.v1_9_R2.NBTTagShort;
import net.minecraft.server.v1_9_R2.NBTTagString;

final class NBTList extends hk.siggi.bukkit.nbt.NBTList<NBTCompound, NBTList> {

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
		return list.d();
	}

	@Override
	public void addByte(byte value) {
		list.add(new NBTTagByte(value));
	}

	@Override
	public byte getByte(int key) {
		if (list.h(key).getTypeId() != NBTType.Byte.id) {
			return (byte) 0;
		}
		return ((NBTTagByte) list.h(key)).f();
	}

	@Override
	public void setByte(int key, byte value) {
		list.a(key, new NBTTagByte(value));
	}

	@Override
	public void addShort(short value) {
		list.add(new NBTTagShort(value));
	}

	@Override
	public short getShort(int key) {
		if (list.h(key).getTypeId() != NBTType.Short.id) {
			return (short) 0;
		}
		return ((NBTTagShort) list.h(key)).e();
	}

	@Override
	public void setShort(int key, short value) {
		list.a(key, new NBTTagShort(value));
	}

	@Override
	public void addInt(int value) {
		list.add(new NBTTagInt(value));
	}

	@Override
	public int getInt(int key) {
		if (list.h(key).getTypeId() != NBTType.Int.id) {
			return 0;
		}
		return ((NBTTagInt) list.h(key)).d();
	}

	@Override
	public void setInt(int key, int value) {
		list.a(key, new NBTTagInt(value));
	}

	@Override
	public void addLong(long value) {
		list.add(new NBTTagLong(value));
	}

	@Override
	public long getLong(int key) {
		if (list.h(key).getTypeId() != NBTType.Long.id) {
			return 0L;
		}
		return ((NBTTagLong) list.h(key)).c();

	}

	@Override
	public void setLong(int key, long value) {
		list.a(key, new NBTTagLong(value));
	}

	@Override
	public void addFloat(float value) {
		list.add(new NBTTagFloat(value));
	}

	@Override
	public float getFloat(int key) {
		if (list.h(key).getTypeId() != NBTType.Float.id) {
			return 0.0F;
		}
		return ((NBTTagFloat) list.h(key)).h();
	}

	@Override
	public void setFloat(int key, float value) {
		list.a(key, new NBTTagFloat(value));
	}

	@Override
	public void addDouble(double value) {
		list.add(new NBTTagDouble(value));
	}

	@Override
	public double getDouble(int key) {
		if (list.h(key).getTypeId() != NBTType.Double.id) {
			return 0.0D;
		}
		return ((NBTTagDouble) list.h(key)).g();
	}

	@Override
	public void setDouble(int key, double value) {
		list.a(key, new NBTTagDouble(value));
	}

	@Override
	public void addByteArray(byte[] value) {
		list.add(new NBTTagByteArray(value));
	}

	@Override
	public byte[] getByteArray(int key) {
		if (list.h(key).getTypeId() != NBTType.ByteArray.id) {
			return new byte[0];
		}
		return ((NBTTagByteArray) list.h(key)).c();
	}

	@Override
	public void setByteArray(int key, byte[] value) {
		list.a(key, new NBTTagByteArray(value));
	}

	@Override
	public void addString(String value) {
		list.add(new NBTTagString(value));
	}

	@Override
	public String getString(int key) {
		if (list.h(key).getTypeId() != NBTType.String.id) {
			return "";
		}
		return ((NBTTagString) list.h(key)).a_();
	}

	@Override
	public void setString(int key, String value) {
		list.a(key, new NBTTagString(value));
	}

	@Override
	public void addList(NBTList value) {
		list.add(value.list);
	}

	@Override
	public NBTList getList(int key) {
		if (list.h(key).getTypeId() != NBTType.List.id) {
			return new NBTList();
		}
		return new NBTList((NBTTagList) list.h(key));
	}

	@Override
	public void setList(int key, NBTList value) {
		list.a(key, value.list);
	}

	@Override
	public void addCompound(NBTCompound value) {
		list.add(value.compound);
	}

	@Override
	public NBTCompound getCompound(int key) {
		if (list.h(key).getTypeId() != NBTType.Compound.id) {
			return new NBTCompound();
		}
		return new NBTCompound((NBTTagCompound) list.h(key));
	}

	@Override
	public void setCompound(int key, NBTCompound value) {
		list.a(key, value.compound);
	}

	@Override
	public void addIntArray(int[] value) {
		list.add(new NBTTagIntArray(value));
	}

	@Override
	public int[] getIntArray(int key) {
		if (list.h(key).getTypeId() != NBTType.IntArray.id) {
			return new int[0];
		}
		return ((NBTTagIntArray) list.h(key)).c();
	}

	@Override
	public void setIntArray(int key, int[] value) {
		list.a(key, new NBTTagIntArray(value));
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
