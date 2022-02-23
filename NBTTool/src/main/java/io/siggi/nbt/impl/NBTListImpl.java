package io.siggi.nbt.impl;

import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

final class NBTListImpl extends NBTList implements NBTBase {

	public NBTListImpl() {
		super(true);
	}

	private final ArrayList<NBTBase> items = new ArrayList<>();

	private void ensureType(NBTType type) {
		if (items.isEmpty())
			return;
		NBTType expected = items.get(0).getNBTType();
		if (type.id != expected.id)
			throw new UnsupportedOperationException("Cannot add " + type.name() + " to a list of " + expected.name());
	}

	@Override
	public void read(DataInput in) throws IOException {
		items.clear();
		byte typeId = in.readByte();
		int size = in.readInt();
		items.ensureCapacity(size);
		for (int i = 0; i < size; i++) {
			NBTBase data = NBTUtilImpl.create(typeId);
			data.read(in);
			items.add(data);
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		int type = getTypeId();
		if (type == NBTType.End.id) {
			type = NBTType.Byte.id;
		}
		out.writeByte(type);
		out.writeInt(items.size());
		for (NBTBase item : items)
			item.write(out);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.List;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getNMSList() {
		return (T) this;
	}

	@Override
	public int getTypeId() {
		if (items.isEmpty()) {
			return NBTType.End.id;
		}
		return items.get(0).getNBTType().id;
	}

	@Override
	public void addByte(byte value) {
		ensureType(NBTType.Byte);
		items.add(new NBTByte(value));
	}

	@Override
	public byte getByte(int key) {
		try {
			return ((NBTNumber) items.get(key)).getAsByte();
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}

	@Override
	public void setByte(int key, byte value) {
		ensureType(NBTType.Byte);
		items.set(key, new NBTByte(value));
	}

	@Override
	public void addShort(short value) {
		ensureType(NBTType.Short);
		items.add(new NBTShort(value));
	}

	@Override
	public short getShort(int key) {
		try {
			return ((NBTNumber) items.get(key)).getAsShort();
		} catch (ClassCastException e) {
			return (short) 0;
		}
	}

	@Override
	public void setShort(int key, short value) {
		ensureType(NBTType.Short);
		items.set(key, new NBTShort(value));
	}

	@Override
	public void addInt(int value) {
		ensureType(NBTType.Int);
		items.add(new NBTInt(value));
	}

	@Override
	public int getInt(int key) {
		try {
			return ((NBTNumber) items.get(key)).getAsInt();
		} catch (ClassCastException e) {
			return 0;
		}
	}

	@Override
	public void setInt(int key, int value) {
		ensureType(NBTType.Int);
		items.set(key, new NBTInt(value));
	}

	@Override
	public void addLong(long value) {
		ensureType(NBTType.Long);
		items.add(new NBTLong(value));
	}

	@Override
	public long getLong(int key) {
		try {
			return ((NBTNumber) items.get(key)).getAsLong();
		} catch (ClassCastException e) {
			return 0L;
		}
	}

	@Override
	public void setLong(int key, long value) {
		ensureType(NBTType.Long);
		items.set(key, new NBTLong(value));
	}

	@Override
	public void addFloat(float value) {
		ensureType(NBTType.Float);
		items.add(new NBTFloat(value));
	}

	@Override
	public float getFloat(int key) {
		try {
			return ((NBTNumber) items.get(key)).getAsFloat();
		} catch (ClassCastException e) {
			return 0.0f;
		}
	}

	@Override
	public void setFloat(int key, float value) {
		ensureType(NBTType.Float);
		items.set(key, new NBTFloat(value));
	}

	@Override
	public void addDouble(double value) {
		ensureType(NBTType.Double);
		items.add(new NBTDouble(value));
	}

	@Override
	public double getDouble(int key) {
		try {
			return ((NBTNumber) items.get(key)).getAsDouble();
		} catch (ClassCastException e) {
			return 0.0;
		}
	}

	@Override
	public void setDouble(int key, double value) {
		ensureType(NBTType.Double);
		items.set(key, new NBTDouble(value));
	}

	@Override
	public void addByteArray(byte[] value) {
		ensureType(NBTType.ByteArray);
		items.add(new NBTByteArray(value));
	}

	@Override
	public byte[] getByteArray(int key) {
		try {
			return ((NBTByteArray) items.get(key)).getValue();
		} catch (ClassCastException e) {
			return NBTByteArray.empty;
		}
	}

	@Override
	public void setByteArray(int key, byte[] value) {
		ensureType(NBTType.ByteArray);
		items.set(key, new NBTByteArray(value));
	}

	@Override
	public void addString(String value) {
		ensureType(NBTType.String);
		items.add(new NBTString(value));
	}

	@Override
	public String getString(int key) {
		try {
			return ((NBTString) items.get(key)).getValue();
		} catch (ClassCastException e) {
			return "";
		}
	}

	@Override
	public void setString(int key, String value) {
		ensureType(NBTType.String);
		items.set(key, new NBTString(value));
	}

	@Override
	public void addList(NBTList value) {
		ensureType(NBTType.List);
		items.add(value.getNMSList());
	}

	@Override
	public NBTList getList(int key) {
		try {
			return (NBTListImpl) items.get(key);
		} catch (ClassCastException e) {
			return new NBTListImpl();
		}
	}

	@Override
	public void setList(int key, NBTList value) {
		ensureType(NBTType.List);
		items.set(key, value.getNMSList());
	}

	@Override
	public void addCompound(NBTCompound value) {
		ensureType(NBTType.Compound);
		items.add(value.getNMSCompound());
	}

	@Override
	public NBTCompound getCompound(int key) {
		try {
			return (NBTCompoundImpl) items.get(key);
		} catch (ClassCastException e) {
			return new NBTCompoundImpl();
		}
	}

	@Override
	public void setCompound(int key, NBTCompound value) {
		ensureType(NBTType.Compound);
		items.set(key, value.getNMSCompound());
	}

	@Override
	public void addIntArray(int[] value) {
		ensureType(NBTType.IntArray);
		items.add(new NBTIntArray(value));
	}

	@Override
	public int[] getIntArray(int key) {
		try {
			return ((NBTIntArray) items.get(key)).getValue();
		} catch (ClassCastException e) {
			return NBTIntArray.empty;
		}
	}

	@Override
	public void setIntArray(int key, int[] value) {
		ensureType(NBTType.IntArray);
		items.set(key, new NBTIntArray(value));
	}

	@Override
	public void addLongArray(long[] value) {
		ensureType(NBTType.LongArray);
		items.add(new NBTLongArray(value));
	}

	@Override
	public long[] getLongArray(int key) {
		try {
			return ((NBTLongArray) items.get(key)).getValue();
		} catch (ClassCastException e) {
			return NBTLongArray.empty;
		}
	}

	@Override
	public void setLongArray(int key, long[] value) {
		ensureType(NBTType.LongArray);
		items.set(key, new NBTLongArray(value));
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public void remove(int key) {
		items.remove(key);
	}

	@Override
	public NBTListImpl copy() {
		NBTListImpl newList = new NBTListImpl();
		newList.items.ensureCapacity(items.size());
		for (NBTBase item : items) {
			newList.items.add(item.copy());
		}
		return newList;
	}
}
