package hk.siggi.bukkit.nbt;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

@Deprecated
public class NBTJsonSerializer extends TypeAdapter<NBTCompound> {
	private static io.siggi.nbt.util.NBTJsonSerializer getImpl() {
		return (io.siggi.nbt.util.NBTJsonSerializer) io.siggi.nbt.NBTTool.getNBTCompoundTypeAdapter();
	}

	NBTJsonSerializer() {
	}

	public GsonBuilder registerTo(GsonBuilder builder) {
		builder.registerTypeAdapter(NBTCompound.class, this);
		io.siggi.nbt.NBTTool.registerTo(builder);
		return builder;
	}

	@Override
	public void write(JsonWriter writer, NBTCompound t) throws IOException {
		if (t == null) {
			writer.nullValue();
			return;
		}
		getImpl().write(writer, t.unwrap());
	}

	@Override
	public NBTCompound read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		return new NBTCompound(getImpl().read(reader));
	}
}
