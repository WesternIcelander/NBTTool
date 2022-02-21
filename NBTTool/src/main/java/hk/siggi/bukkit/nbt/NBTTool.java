package hk.siggi.bukkit.nbt;

/**
 * @deprecated Use {@link io.siggi.nbt.NBTTool} instead.
 */
@Deprecated
public class NBTTool {

	private NBTTool() {
	}

	static NBTUtil nbtutil = new NBTUtil();
	static NBTJsonSerializer serializer = new NBTJsonSerializer();

	public static NBTUtil getUtil() {
		return nbtutil;
	}

	public static NBTJsonSerializer getSerializer() {
		return serializer;
	}
}