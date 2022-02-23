package hk.siggi.bukkit.nbt;

@Deprecated
public class NBTList {

	private final io.siggi.nbt.NBTList implementation;

	public NBTList() {
		try {
			this.implementation = new io.siggi.nbt.NBTList();
		} catch (NullPointerException e) {
			throw new UnsupportedOperationException();
		}
	}

	public NBTList(io.siggi.nbt.NBTList implementation) {
		this.implementation = implementation;
	}

	public <T> T getNMSList() {
		return implementation.getNMSList();
	}

	public int getType() {
		return implementation.getTypeId();
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
		implementation.addList(value.unwrap());
	}

	public NBTList getList(int key) {
		return new NBTList(implementation.getList(key));
	}

	public void setList(int key, NBTList value) {
		implementation.setList(key, value.unwrap());
	}

	public void addCompound(NBTCompound value) {
		implementation.addCompound(value.unwrap());
	}

	public NBTCompound getCompound(int key) {
		return new NBTCompound(implementation.getCompound(key));
	}

	public void setCompound(int key, NBTCompound value) {
		implementation.setCompound(key, value.unwrap());
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
		return new NBTList(implementation.copy());
	}

	io.siggi.nbt.NBTList unwrap() {
		return implementation;
	}
}
