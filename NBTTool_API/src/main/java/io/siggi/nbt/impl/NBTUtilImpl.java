package io.siggi.nbt.impl;

import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import io.siggi.nbt.NBTType;
import io.siggi.nbt.util.NBTUtil;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public class NBTUtilImpl extends NBTUtil {
	@Override
	public NBTCompound newCompound() {
		return new NBTCompoundImpl();
	}

	@Override
	public NBTList newList() {
		return new NBTListImpl();
	}

	@Override
	public <T> NBTCompound wrapCompound(T compound) {
		return (NBTCompound) compound;
	}

	@Override
	public <T> NBTList wrapList(T list) {
		return (NBTList) list;
	}

	@Override
	public Class<? extends NBTCompound> getCompoundClass() {
		return NBTCompoundImpl.class;
	}

	@Override
	public Class<? extends NBTList> getListClass() {
		return NBTListImpl.class;
	}

	@Override
	public void serialize(OutputStream out, NBTCompound compound) throws IOException {
		DataOutputStream dos = out instanceof DataOutputStream ? (DataOutputStream) out : new DataOutputStream(out);
		writeNamedTag(dos, "", compound.getNMSCompound());
	}

	@Override
	public NBTCompound deserialize(InputStream in) throws IOException {
		DataInputStream dis = in instanceof DataInputStream ? (DataInputStream) in : new DataInputStream(in);
		return (NBTCompoundImpl) readNamedTag(dis).data;
	}

	@SuppressWarnings("unchecked")
	public static <T extends NBTBase> NamedTag<T> readNamedTag(DataInput in) throws IOException {
		byte type = in.readByte();
		if (type == NBTType.End.id) {
			return new NamedTag<>(null, (T) new NBTEnd());
		}
		T data = create(type);
		String name = in.readUTF();
		data.read(in);
		return new NamedTag<>(name, data);
	}

	public static <T extends NBTBase> void writeNamedTag(DataOutput out, NamedTag<T> tag) throws IOException {
		writeNamedTag(out, tag.name, tag.data);
	}
	public static <T extends NBTBase> void writeNamedTag(DataOutput out, String name, T data) throws IOException {
		out.writeByte(data.getNBTType().id);
		if (data.getNBTType() == NBTType.End)
			return;
		out.writeUTF(name);
		data.write(out);
	}

	public static <T extends NBTBase> T create(NBTType type) {
		if (type == null) {
			throw new NullPointerException();
		}
		return create(type.id);
	}

	@SuppressWarnings("unchecked")
	public static <T extends NBTBase> T create(int type) {
		if (type < 0 || type > suppliers.length) {
			throw new IllegalArgumentException("Unknown NBT type " + type);
		}
		return (T) suppliers[type].get();
	}

	public static class NamedTag<T extends NBTBase> {
		public final String name;
		public final T data;

		public NamedTag(String name, T data) {
			if (name == null) name = "";
			if (data == null) throw new NullPointerException();
			this.name = name;
			this.data = data;
		}
	}

	static final Supplier<? extends NBTBase>[] suppliers;

	static {
		NBTType[] values = NBTType.values();
		@SuppressWarnings("unchecked")
		Supplier<? extends NBTBase>[] s = new Supplier[values.length];
		for (NBTType type : values) {
			Supplier<? extends NBTBase> supplier;
			switch (type) {
				case End:
					supplier = NBTEnd::new;
					break;
				case Byte:
					supplier = NBTByte::new;
					break;
				case Short:
					supplier = NBTShort::new;
					break;
				case Int:
					supplier = NBTInt::new;
					break;
				case Long:
					supplier = NBTLong::new;
					break;
				case Float:
					supplier = NBTFloat::new;
					break;
				case Double:
					supplier = NBTDouble::new;
					break;
				case ByteArray:
					supplier = NBTByteArray::new;
					break;
				case String:
					supplier = NBTString::new;
					break;
				case List:
					supplier = NBTListImpl::new;
					break;
				case Compound:
					supplier = NBTCompoundImpl::new;
					break;
				case IntArray:
					supplier = NBTIntArray::new;
					break;
				case LongArray:
					supplier = NBTLongArray::new;
					break;
				default:
					throw new RuntimeException("New NBTType " + type.name() + " not accounted for in NBTUtilImpl suppliers.");
			}
			s[type.id] = supplier;
		}
		suppliers = s;
	}
}
