/*
 * The MIT License
 *
 * Copyright 2021 Siggi.
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

import io.siggi.nbt.util.NBTJsonSerializer;
import io.siggi.nbt.util.NBTUtilFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class NBTToolPlugin extends JavaPlugin {

	private static boolean loadSuccessful = false;

	private static String getVersion() {
		String name = Bukkit.getServer().getClass().getName();
		String version = name.substring(name.indexOf(".v") + 1);
		version = version.substring(0, version.indexOf("."));
		if (!version.startsWith("v")) {
			return nmsVersions.getOrDefault(Bukkit.getMinecraftVersion(), latestNmsVersion);
		}
		return version;
	}

	private static final Map<String, String> nmsVersions = new HashMap<>();
	private static final String latestNmsVersion;
	static {
		nmsVersions.put("1.20.5", "v1_20_R4");
		nmsVersions.put("1.20.6", "v1_20_R4");
		nmsVersions.put("1.21", "v1_21_R1");
		nmsVersions.put("1.21.1", "v1_21_R1");
		nmsVersions.put("1.21.2", "v1_21_R2");
		nmsVersions.put("1.21.3", "v1_21_R2");
		nmsVersions.put("1.21.4", "v1_21_R3");
		nmsVersions.put("1.21.5", "v1_21_R4");
		latestNmsVersion = "v1_21_R4";

		tryBlock:
		try {
			NBTUtilFactory nbtUtilFactory = getNBTUtilFactory();
			NBTTool.nbtutil = nbtUtilFactory.newInstance();
			if (NBTTool.nbtutil == null) {
				break tryBlock;
			}
			try {
				NBTTool.additionalSerializers.add(new BukkitSerializer((NBTJsonSerializer) NBTTool.serializer, NBTTool.nbtutil));
			} catch (Throwable t) {
			}
			loadSuccessful = true;
		} catch (Exception e) {
			NBTTool.nbtutil = null;
		}
	}

	@Override
	public void onLoad() {
	}

	/**
	 * Called by Bukkit when the plugin is enabled, you shouldn't call this
	 * method in your own plugin.
	 */
	@Override
	public void onEnable() {
		if (!loadSuccessful) {
			getLogger().severe("NBTTool does not support this server version!");
			setEnabled(false);
		}
	}

	/**
	 * Called by Bukkit when the plugin is disabled, you shouldn't call this
	 * method in your own plugin.
	 */
	@Override
	public void onDisable() {
	}

	static NBTUtilFactory getNBTUtilFactory() {
		try {
			Class<?> factoryClass = Class.forName("io.siggi.nbt." + getVersion() + ".NBTUtilFactoryImpl");
			Constructor<?> factoryConstructor = factoryClass.getDeclaredConstructor();
			return (NBTUtilFactory) factoryConstructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
