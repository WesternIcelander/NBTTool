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
package io.siggi.nbt;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import io.siggi.nbt.impl.NBTUtilImpl;
import io.siggi.nbt.util.AdditionalSerializer;
import io.siggi.nbt.util.NBTJsonSerializer;
import io.siggi.nbt.util.NBTUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class NBTTool {

	private NBTTool() {
	}

	static NBTUtil nbtutil = new NBTUtilImpl();
	static final Object serializer;
	static final List<AdditionalSerializer> additionalSerializers = new ArrayList<>();
	static {
		Object s = null;
		try {
			s = new NBTJsonSerializer();
		} catch (Throwable t) {
		}
		serializer = s;
	}

	static NBTUtil getUtil() {
		if (nbtutil == null)
			throw new UnsupportedOperationException();
		return nbtutil;
	}

	/**
	 * Get the {@link TypeAdapter} that can serialize and deserialize {@link NBTCompound}s.
	 *
	 * @return a {@link TypeAdapter}
	 */
	@SuppressWarnings("unchecked")
	public static TypeAdapter<NBTCompound> getNBTCompoundTypeAdapter() {
		return (TypeAdapter<NBTCompound>) serializer;
	}

	/**
	 * Register this NBTJsonSerializer to the specified {@link GsonBuilder} so
	 * that it can serialize and deserialize {@link NBTCompound}s and
	 * bukkit ItemStacks when NBTTool is loaded as a Bukkit plugin.
	 *
	 * @param builder the GsonBuilder to register this
	 * @return the same GsonBuilder passed in, for convenience.
	 */
	public static GsonBuilder registerTo(GsonBuilder builder) {
		builder.registerTypeAdapter(NBTCompound.class, serializer);
		builder.registerTypeAdapter(nbtutil.getCompoundClass(), serializer);
		for (AdditionalSerializer additionalSerializer : additionalSerializers) {
			additionalSerializer.registerTo(builder);
		}
		return builder;
	}

	public static void serialize(OutputStream out, NBTCompound compound) throws IOException {
		getUtil().serialize(out, compound);
	}

	public static NBTCompound deserialize(InputStream in) throws IOException {
		return getUtil().deserialize(in);
	}
}
