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
package io.siggi.nbt.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import io.siggi.nbt.NBTType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * This class is used to serialize and deserialize {@link NBTCompound}s to and
 * from json.
 *
 * @author Siggi
 */
public class NBTJsonSerializer extends TypeAdapter<NBTCompound> {

	public NBTJsonSerializer() {
	}

	/**
	 * Write an {@link NBTCompound} to a JsonWriter.
	 *
	 * @param writer the {@link JsonWriter} to write to.
	 * @param t the {@link NBTCompound} to write.
	 * @throws IOException if something goes wrong
	 */
	@Override
	public void write(JsonWriter writer, NBTCompound t) throws IOException {
		if (t == null) {
			writer.nullValue();
		}
		writeCompound(writer, t);
	}

	/**
	 * Read an {@link NBTCompound} from a JsonReader.
	 *
	 * @param reader the {@link JsonReader} to read from.
	 * @return the {@link NBTCompound} that was read.
	 * @throws IOException if something goes wrong.
	 */
	@Override
	public NBTCompound read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		return readCompound(reader);
	}

	private NBTCompound readCompound(JsonReader reader) throws IOException {
		NBTCompound compound = new NBTCompound();
		reader.beginObject();
		while (true) {
			JsonToken token = reader.peek();
			if (token == JsonToken.END_OBJECT) {
				break;
			}
			if (token != JsonToken.NAME) {
				reader.skipValue();
				continue;
			}
			String key = reader.nextName();
			token = reader.peek();
			int pos = key.lastIndexOf("::");
			if (pos == -1) {
				continue;
			}
			String typeStr = key.substring(pos + 2);
			key = key.substring(0, pos);
			NBTType type;
			try {
				type = NBTType.valueOf(typeStr);
			} catch (Exception e) {
				continue;
			}
			switch (type) {
				case Byte:
					if (token == JsonToken.NUMBER) {
						compound.setByte(key, (byte) reader.nextInt());
					}
					break;
				case Short:
					if (token == JsonToken.NUMBER) {
						compound.setShort(key, (short) reader.nextInt());
					}
					break;
				case Int:
					if (token == JsonToken.NUMBER) {
						compound.setInt(key, reader.nextInt());
					}
					break;
				case Long:
					if (token == JsonToken.NUMBER) {
						compound.setLong(key, reader.nextLong());
					}
					break;
				case Float:
					if (token == JsonToken.NUMBER) {
						compound.setFloat(key, (float) reader.nextDouble());
					}
					break;
				case Double:
					if (token == JsonToken.NUMBER) {
						compound.setDouble(key, reader.nextDouble());
					}
					break;
				case ByteArray:
					if (token == JsonToken.BEGIN_ARRAY) {
						compound.setByteArray(key, readByteArray(reader));
					}
					break;
				case String:
					if (token == JsonToken.STRING) {
						compound.setString(key, reader.nextString());
					}
					break;
				case List:
					if (token == JsonToken.BEGIN_ARRAY) {
						compound.setList(key, readList(reader));
					}
					break;
				case Compound:
					if (token == JsonToken.BEGIN_OBJECT) {
						compound.setCompound(key, readCompound(reader));
					}
					break;
				case IntArray:
					if (token == JsonToken.BEGIN_ARRAY) {
						compound.setIntArray(key, readIntArray(reader));
					}
					break;
				case LongArray:
					if (token == JsonToken.BEGIN_ARRAY) {
						compound.setLongArray(key, readLongArray(reader));
					}
					break;
				default:
					break;
			}
		}
		reader.endObject();
		return compound;
	}

	private NBTList readList(JsonReader reader) throws IOException {
		NBTList list = new NBTList();
		reader.beginArray();
		JsonToken peek = reader.peek();
		reading:
		{
			if (peek != JsonToken.STRING) {
				break reading;
			}
			String typeStr = reader.nextString();
			NBTType type;
			try {
				type = NBTType.valueOf(typeStr);
			} catch (Exception e) {
				break reading;
			}
			JsonToken requiredToken;
			switch (type) {
				case Byte:
					requiredToken = JsonToken.NUMBER;
					break;
				case Short:
					requiredToken = JsonToken.NUMBER;
					break;
				case Int:
					requiredToken = JsonToken.NUMBER;
					break;
				case Long:
					requiredToken = JsonToken.NUMBER;
					break;
				case Float:
					requiredToken = JsonToken.NUMBER;
					break;
				case Double:
					requiredToken = JsonToken.NUMBER;
					break;
				case ByteArray:
					requiredToken = JsonToken.BEGIN_ARRAY;
					break;
				case String:
					requiredToken = JsonToken.STRING;
					break;
				case List:
					requiredToken = JsonToken.BEGIN_ARRAY;
					break;
				case Compound:
					requiredToken = JsonToken.BEGIN_OBJECT;
					break;
				case IntArray:
					requiredToken = JsonToken.BEGIN_ARRAY;
					break;
				case LongArray:
					requiredToken = JsonToken.BEGIN_ARRAY;
					break;
				default:
					requiredToken = null;
					break;
			}
			if (requiredToken == null) {
				break reading;
			}
			while ((peek = reader.peek()) != JsonToken.END_ARRAY) {
				if (peek != requiredToken) {
					continue;
				}
				switch (type) {
					case Byte:
						list.addByte((byte) reader.nextInt());
						break;
					case Short:
						list.addShort((short) reader.nextInt());
						break;
					case Int:
						list.addInt(reader.nextInt());
						break;
					case Long:
						list.addLong(reader.nextLong());
						break;
					case Float:
						list.addFloat((float) reader.nextDouble());
						break;
					case Double:
						list.addDouble(reader.nextDouble());
						break;
					case ByteArray:
						list.addByteArray(readByteArray(reader));
						break;
					case String:
						list.addString(reader.nextString());
						break;
					case List:
						list.addList(readList(reader));
						break;
					case Compound:
						list.addCompound(readCompound(reader));
						break;
					case IntArray:
						list.addIntArray(readIntArray(reader));
						break;
					case LongArray:
						list.addLongArray(readLongArray(reader));
						break;
					default:
						break;
				}
			}
		}
		while ((peek = reader.peek()) != JsonToken.END_ARRAY) {
			reader.skipValue();
		}
		reader.endArray();
		return list;
	}

	private byte[] readByteArray(JsonReader reader) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonToken peek;
		reader.beginArray();
		while ((peek = reader.peek()) != JsonToken.END_ARRAY) {
			if (peek == JsonToken.NUMBER) {
				baos.write(reader.nextInt());
			} else {
				reader.skipValue();
			}
		}
		reader.endArray();
		return baos.toByteArray();
	}

	private int[] readIntArray(JsonReader reader) throws IOException {
		ArrayList<Integer> intArray = new ArrayList<>();
		JsonToken peek;
		reader.beginArray();
		while ((peek = reader.peek()) != JsonToken.END_ARRAY) {
			if (peek == JsonToken.NUMBER) {
				intArray.add(reader.nextInt());
			} else {
				reader.skipValue();
			}
		}
		reader.endArray();
		int[] arr = new int[intArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = intArray.get(i);
		}
		return arr;
	}

	private long[] readLongArray(JsonReader reader) throws IOException {
		ArrayList<Long> longArray = new ArrayList<>();
		JsonToken peek;
		reader.beginArray();
		while ((peek = reader.peek()) != JsonToken.END_ARRAY) {
			if (peek == JsonToken.NUMBER) {
				longArray.add(reader.nextLong());
			} else {
				reader.skipValue();
			}
		}
		reader.endArray();
		long[] arr = new long[longArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = longArray.get(i);
		}
		return arr;
	}

	private void writeCompound(JsonWriter writer, NBTCompound compound) throws IOException {
		writer.beginObject();
		for (String key : compound.keySet()) {
			NBTType type;
			try {
				type = NBTType.getById(compound.getTypeId(key));
			} catch (Exception e) {
				continue;
			}
			writer.name(key + "::" + type);
			switch (type) {
				case Byte:
					writer.value(((int) compound.getByte(key)) & 0xff);
					break;
				case Short:
					writer.value((int) compound.getShort(key));
					break;
				case Int:
					writer.value(compound.getInt(key));
					break;
				case Long:
					writer.value(compound.getLong(key));
					break;
				case Float:
					writer.value((double) compound.getFloat(key));
					break;
				case Double:
					writer.value(compound.getDouble(key));
					break;
				case ByteArray:
					writeByteArray(writer, compound.getByteArray(key));
					break;
				case String:
					writer.value(compound.getString(key));
					break;
				case List:
					writeList(writer, compound.getList(key));
					break;
				case Compound:
					writeCompound(writer, compound.getCompound(key));
					break;
				case IntArray:
					writeIntArray(writer, compound.getIntArray(key));
					break;
				case LongArray:
					writeLongArray(writer, compound.getLongArray(key));
					break;
				default:
					writer.nullValue();
					break;
			}
		}
		writer.endObject();
	}

	private void writeList(JsonWriter writer, NBTList list) throws IOException {
		writer.beginArray();
		if (list.size() == 0) {
			writer.endArray();
			return;
		}
		NBTType type;
		try {
			type = NBTType.getById(list.getTypeId());
		} catch (Exception e) {
			writer.endArray();
			return;
		}
		writer.value(type.toString());
		int size = list.size();
		for (int i = 0; i < size; i++) {
			switch (type) {
				case Byte:
					writer.value(((int) list.getByte(i)) & 0xff);
					break;
				case Short:
					writer.value((int) list.getShort(i));
					break;
				case Int:
					writer.value(list.getInt(i));
					break;
				case Long:
					writer.value(list.getLong(i));
					break;
				case Float:
					writer.value((double) list.getFloat(i));
					break;
				case Double:
					writer.value(list.getDouble(i));
					break;
				case ByteArray:
					writeByteArray(writer, list.getByteArray(i));
					break;
				case String:
					writer.value(list.getString(i));
					break;
				case List:
					writeList(writer, list.getList(i));
					break;
				case Compound:
					writeCompound(writer, list.getCompound(i));
					break;
				case IntArray:
					writeIntArray(writer, list.getIntArray(i));
					break;
				case LongArray:
					writeLongArray(writer, list.getLongArray(i));
					break;
				default:
					break;
			}
		}
		writer.endArray();
	}

	private void writeByteArray(JsonWriter writer, byte[] array) throws IOException {
		writer.beginArray();
		for (int i = 0; i < array.length; i++) {
			writer.value(((int) array[i]) & 0xff);
		}
		writer.endArray();
	}

	private void writeIntArray(JsonWriter writer, int[] array) throws IOException {
		writer.beginArray();
		for (int i = 0; i < array.length; i++) {
			writer.value(array[i]);
		}
		writer.endArray();
	}

	private void writeLongArray(JsonWriter writer, long[] array) throws IOException {
		writer.beginArray();
		for (int i = 0; i < array.length; i++) {
			writer.value(array[i]);
		}
		writer.endArray();
	}
}
