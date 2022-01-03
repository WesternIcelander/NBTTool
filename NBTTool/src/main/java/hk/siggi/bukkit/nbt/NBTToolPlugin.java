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
package hk.siggi.bukkit.nbt;

import org.bukkit.plugin.java.JavaPlugin;

public class NBTToolPlugin extends JavaPlugin {

	/**
	 * Called by Bukkit when the plugin is enabled, you shouldn't call this
	 * method in your own plugin.
	 */
	@Override
	public void onEnable() {
		enable:
		{
			try {
				NBTTool.nbtutil = NBTUtil.get();
				if (NBTTool.nbtutil == null) {
					break enable;
				}
				try {
					NBTTool.serializer = new NBTJsonSerializer(NBTTool.nbtutil);
				} catch (Exception e) {
					System.err.println("Gson is not available, add Gson to the classpath to enable this feature!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				break enable;
			}
			return;
		}
		System.err.println("NBTTool does not support this server version!");
		setEnabled(false);
	}

	/**
	 * Called by Bukkit when the plugin is disabled, you shouldn't call this
	 * method in your own plugin.
	 */
	@Override
	public void onDisable() {
	}
}
