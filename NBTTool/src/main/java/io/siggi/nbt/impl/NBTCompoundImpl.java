package io.siggi.nbt.impl;

import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import io.siggi.nbt.NBTType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class NBTCompoundImpl extends NBTCompound implements NBTBase {

	public NBTCompoundImpl() {
		super(true);
	}

	private final Map<String, NBTBase> items = new LinkedHashMap<>();

	@Override
	public void read(DataInput in) throws IOException {
		items.clear();
		NBTUtilImpl.NamedTag<? extends NBTBase> tag;
		while ((tag = NBTUtilImpl.readNamedTag(in)).data.getNBTType() != NBTType.End) {
			items.put(tag.name, tag.data);
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		for (Map.Entry<String, NBTBase> entry : items.entrySet()) {
			NBTUtilImpl.writeNamedTag(out, entry.getKey(), entry.getValue());
		}
		out.writeByte(NBTType.End.id);
	}

	@Override
	public NBTType getNBTType() {
		return NBTType.Compound;
	}

	public <T> T getNMSCompound() {
		return (T) this;
	}

	@Override
	public int getTypeId(String key) {
		try {
			return items.get(key).getNBTType().id;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public byte getByte(String key) {
		try {
			return ((NBTNumber) items.get(key)).getAsByte();
		} catch (ClassCastException | NullPointerException e) {
			return (byte) 0;
		}
	}

	@Override
	public void setByte(String key, byte value) {
		items.put(key, new NBTByte(value));
	}

	@Override
	public short getShort(String key) {
		try {
			return ((NBTNumber) items.get(key)).getAsShort();
		} catch (ClassCastException | NullPointerException e) {
			return (short) 0;
		}
	}

	@Override
	public void setShort(String key, short value) {
		items.put(key, new NBTShort(value));
	}

	@Override
	public int getInt(String key) {
		try {
			return ((NBTNumber) items.get(key)).getAsInt();
		} catch (ClassCastException | NullPointerException e) {
			return 0;
		}
	}

	@Override
	public void setInt(String key, int value) {
		items.put(key, new NBTInt(value));
	}

	@Override
	public long getLong(String key) {
		try {
			return ((NBTNumber) items.get(key)).getAsLong();
		} catch (ClassCastException | NullPointerException e) {
			return 0L;
		}
	}

	@Override
	public void setLong(String key, long value) {
		items.put(key, new NBTLong(value));
	}

	@Override
	public float getFloat(String key) {
		try {
			return ((NBTNumber) items.get(key)).getAsFloat();
		} catch (ClassCastException | NullPointerException e) {
			return 0.0f;
		}
	}

	@Override
	public void setFloat(String key, float value) {
		items.put(key, new NBTFloat(value));
	}

	@Override
	public double getDouble(String key) {
		try {
			return ((NBTNumber) items.get(key)).getAsDouble();
		} catch (ClassCastException | NullPointerException e) {
			return 0.0;
		}
	}

	@Override
	public void setDouble(String key, double value) {
		items.put(key, new NBTDouble(value));
	}

	@Override
	public byte[] getByteArray(String key) {
		try {
			return ((NBTByteArray) items.get(key)).getValue();
		} catch (ClassCastException | NullPointerException e) {
			return NBTByteArray.empty;
		}
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		items.put(key, new NBTByteArray(value));
	}

	@Override
	public String getString(String key) {
		try {
			return ((NBTString) items.get(key)).getValue();
		} catch (ClassCastException | NullPointerException e) {
			return "";
		}
	}

	@Override
	public void setString(String key, String value) {
		items.put(key, new NBTString(value));
	}

	@Override
	public NBTList getList(String key) {
		try {
			return ((NBTList) items.get(key)).getNMSList();
		} catch (ClassCastException | NullPointerException e) {
			return new NBTListImpl();
		}
	}

	@Override
	public void setList(String key, NBTList value) {
		items.put(key, value.getNMSList());
	}

	@Override
	public NBTCompound getCompound(String key) {
		try {
			return ((NBTCompound) items.get(key)).getNMSCompound();
		} catch (ClassCastException | NullPointerException e) {
			return new NBTCompoundImpl();
		}
	}

	@Override
	public void setCompound(String key, NBTCompound value) {
		items.put(key, value.getNMSCompound());
	}

	@Override
	public int[] getIntArray(String key) {
		try {
			return ((NBTIntArray) items.get(key)).getValue();
		} catch (ClassCastException | NullPointerException e) {
			return NBTIntArray.empty;
		}
	}

	@Override
	public void setIntArray(String key, int[] value) {
		items.put(key, new NBTIntArray(value));
	}

	@Override
	public long[] getLongArray(String key) {
		try {
			return ((NBTLongArray) items.get(key)).getValue();
		} catch (ClassCastException | NullPointerException e) {
			return NBTLongArray.empty;
		}
	}

	@Override
	public void setLongArray(String key, long[] value) {
		items.put(key, new NBTLongArray(value));
	}

	@Override
	public Set<String> keySet() {
		return items.keySet();
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public void remove(String key) {
		items.remove(key);
	}

	@Override
	public NBTCompoundImpl copy() {
		NBTCompoundImpl newCompound = new NBTCompoundImpl();
		for (Map.Entry<String,NBTBase> entry : items.entrySet()) {
			newCompound.items.put(entry.getKey(), entry.getValue().copy());
		}
		return newCompound;
	}
}
