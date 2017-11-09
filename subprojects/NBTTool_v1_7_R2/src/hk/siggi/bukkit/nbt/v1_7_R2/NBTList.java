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
package hk.siggi.bukkit.nbt.v1_7_R2;

import hk.siggi.bukkit.nbt.NBTType;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.server.v1_7_R2.NBTBase;
import net.minecraft.server.v1_7_R2.NBTTagByte;
import net.minecraft.server.v1_7_R2.NBTTagByteArray;
import net.minecraft.server.v1_7_R2.NBTTagDouble;
import net.minecraft.server.v1_7_R2.NBTTagFloat;
import net.minecraft.server.v1_7_R2.NBTTagInt;
import net.minecraft.server.v1_7_R2.NBTTagIntArray;
import net.minecraft.server.v1_7_R2.NBTTagList;
import net.minecraft.server.v1_7_R2.NBTTagLong;
import net.minecraft.server.v1_7_R2.NBTTagShort;
import net.minecraft.server.v1_7_R2.NBTTagString;

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
		if (getIdx(key).getTypeId() != NBTType.Byte.id) {
			return (byte) 0;
		}
		return ((NBTTagByte) getIdx(key)).f();
	}

	@Override
	public void setByte(int key, byte value) {
		setIdx(key, new NBTTagByte(value));
	}

	@Override
	public void addShort(short value) {
		list.add(new NBTTagShort(value));
	}

	@Override
	public short getShort(int key) {
		if (getIdx(key).getTypeId() != NBTType.Short.id) {
			return (short) 0;
		}
		return ((NBTTagShort) getIdx(key)).e();
	}

	@Override
	public void setShort(int key, short value) {
		setIdx(key, new NBTTagShort(value));
	}

	@Override
	public void addInt(int value) {
		list.add(new NBTTagInt(value));
	}

	@Override
	public int getInt(int key) {
		if (getIdx(key).getTypeId() != NBTType.Int.id) {
			return 0;
		}
		return ((NBTTagInt) getIdx(key)).d();
	}

	@Override
	public void setInt(int key, int value) {
		setIdx(key, new NBTTagInt(value));
	}

	@Override
	public void addLong(long value) {
		list.add(new NBTTagLong(value));
	}

	@Override
	public long getLong(int key) {
		if (getIdx(key).getTypeId() != NBTType.Long.id) {
			return 0L;
		}
		return ((NBTTagLong) getIdx(key)).c();

	}

	@Override
	public void setLong(int key, long value) {
		setIdx(key, new NBTTagLong(value));
	}

	@Override
	public void addFloat(float value) {
		list.add(new NBTTagFloat(value));
	}

	@Override
	public float getFloat(int key) {
		return list.e(key);
	}

	@Override
	public void setFloat(int key, float value) {
		setIdx(key, new NBTTagFloat(value));
	}

	@Override
	public void addDouble(double value) {
		list.add(new NBTTagDouble(value));
	}

	@Override
	public double getDouble(int key) {
		return list.d(key);
	}

	@Override
	public void setDouble(int key, double value) {
		setIdx(key, new NBTTagDouble(value));
	}

	@Override
	public void addByteArray(byte[] value) {
		list.add(new NBTTagByteArray(value));
	}

	@Override
	public byte[] getByteArray(int key) {
		if (getIdx(key).getTypeId() != NBTType.ByteArray.id) {
			return new byte[0];
		}
		return ((NBTTagByteArray) getIdx(key)).c();
	}

	@Override
	public void setByteArray(int key, byte[] value) {
		setIdx(key, new NBTTagByteArray(value));
	}

	@Override
	public void addString(String value) {
		list.add(new NBTTagString(value));
	}

	@Override
	public String getString(int key) {
		return list.f(key);
	}

	@Override
	public void setString(int key, String value) {
		setIdx(key, new NBTTagString(value));
	}

	@Override
	public void addList(NBTList value) {
		list.add(value.list);
	}

	@Override
	public NBTList getList(int key) {
		if (getIdx(key).getTypeId() != NBTType.List.id) {
			return new NBTList();
		}
		return new NBTList((NBTTagList) getIdx(key));
	}

	@Override
	public void setList(int key, NBTList value) {
		setIdx(key, value.list);
	}

	@Override
	public void addCompound(NBTCompound value) {
		list.add(value.compound);
	}

	@Override
	public NBTCompound getCompound(int key) {
		return new NBTCompound(list.get(key));
	}

	@Override
	public void setCompound(int key, NBTCompound value) {
		setIdx(key, value.compound);
	}

	@Override
	public void addIntArray(int[] value) {
		list.add(new NBTTagIntArray(value));
	}

	@Override
	public int[] getIntArray(int key) {
		return list.c(key);
	}

	@Override
	public void setIntArray(int key, int[] value) {
		setIdx(key, new NBTTagIntArray(value));
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void remove(int key) {
		deleteIdx(key);
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
	public NBTList clone() {
		return new NBTList((NBTTagList) list.clone());
	}
	
	// A lot of reflection below.
	// I know I said there's no reflection in the plugin, but to ensure the
	// same behaviour of NBTTool API across all versions of Minecraft, I had
	// to use reflection in 1.7.

	private static Field listField = null;
	private List getListCache = null;
	private static Field typeField = null;

	private List getList() {
		if (getListCache == null) {
			getListCache = getList(list);
		}
		return getListCache;
	}

	private static List getList(NBTTagList nbtlist) {
		try {
			if (listField == null) {
				try {
					listField = NBTTagList.class.getDeclaredField("list");
				} catch (Exception e) {
				}
				if (listField == null) {
					return null;
				}
			}
			boolean oA = listField.isAccessible();
			List list = null;
			try {
				listField.setAccessible(true);
				list = (List) listField.get(nbtlist);
			} finally {
				listField.setAccessible(oA);
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	private void setType(byte type) {
		Field f = getTypeField();
		if (f == null) {
			return;
		}
		try {
			boolean oA = f.isAccessible();
			try {
				f.setAccessible(true);
				f.set(list, type);
			} finally {
				f.setAccessible(oA);
			}
		} catch (Exception e) {
		}
	}

	private static Field getTypeField() {
		if (typeField == null) {
			try {
				typeField = NBTTagList.class.getDeclaredField("type");
			} catch (Exception e) {
			}
		}
		return typeField;
	}

	private void setIdx(int index, NBTBase item) {
		byte currentType = (byte) getType();
		if (currentType == 0) {
			setType(item.getTypeId());
		} else if (currentType != item.getTypeId()) {
			// fail silently
			return;
		}
		getList().set(index, item);
	}

	private NBTBase getIdx(int index) {
		return (NBTBase) (getList().get(index));
	}

	private void deleteIdx(int index) {
		getList().remove(index);
	}
}
