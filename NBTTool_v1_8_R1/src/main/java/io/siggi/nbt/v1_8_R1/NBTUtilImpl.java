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
package io.siggi.nbt.v1_8_R1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import io.siggi.nbt.util.AuthLibProperty;
import io.siggi.nbt.util.NBTUtil;

import java.util.List;
import java.util.Map;

import io.siggi.nbt.util.UserProfile;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftSkull;
import org.bukkit.craftbukkit.v1_8_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

final class NBTUtilImpl extends NBTUtil {

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
		return new NBTCompoundImpl((NBTTagCompound) compound);
	}

	@Override
	public <T> NBTList wrapList(T list) {
		return new NBTListImpl((NBTTagList) list);
	}

	@Override
	public NBTCompound getTag(ItemStack stack) {
		net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		if (nmsStack.hasTag()) {
			return wrapCompound(nmsStack.getTag());
		} else {
			return null;
		}
	}

	@Override
	public ItemStack setTag(ItemStack stack, NBTCompound compound) {
		net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		if (compound == null) {
			nmsStack.setTag(null);
		} else {
			nmsStack.setTag(compound.getNMSCompound());
		}
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	@Override
	public NBTCompound itemToNBT(ItemStack stack) {
		try {
			net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
			NBTTagCompound nmsCompound = new NBTTagCompound();
			nmsStack.save(nmsCompound);
			NBTCompound compound = wrapCompound(nmsCompound);
			return compound;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ItemStack itemFromNBT(NBTCompound compound) {
		if (compound == null) {
			return null;
		}
		try {
			NBTTagCompound nmsCompound = compound.getNMSCompound();
			net.minecraft.server.v1_8_R1.ItemStack nmsStack = net.minecraft.server.v1_8_R1.ItemStack.createStack(nmsCompound);
			return CraftItemStack.asCraftMirror(nmsStack);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Class<NBTCompoundImpl> getCompoundClass() {
		return NBTCompoundImpl.class;
	}

	@Override
	public Class<NBTListImpl> getListClass() {
		return NBTListImpl.class;
	}

	@Override
	public Class<CraftItemStack> getCraftItemStack() {
		return CraftItemStack.class;
	}

	private Item getItem(net.minecraft.server.v1_8_R1.ItemStack nmsStack) {
		NBTTagCompound nbttc = new NBTTagCompound();
		nmsStack.save(nbttc);
		NBTCompound compound = wrapCompound(nbttc);
		String id = compound.getString("id");
		return Item.d(id);
	}

	@Override
	public String getItemName(ItemStack stack) {
		net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Item item = getItem(nmsStack);
		if (item == null) {
			return "null";
		} else {
			return item.a(nmsStack);
		}
	}

	@Override
	public String getTranslatableItemName(ItemStack stack) {
		net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Item item = getItem(nmsStack);
		if (item == null) {
			return "null";
		} else {
			return item.e_(nmsStack) + ".name";
		}
	}

	@Override
	public String getEnchantmentName(Enchantment enchantment, int level) {
		net.minecraft.server.v1_8_R1.Enchantment raw = CraftEnchantment.getRaw(enchantment);
		return raw.d(level);
	}

	@Override
	public String getTranslatableEnchantmentName(Enchantment enchantment) {
		net.minecraft.server.v1_8_R1.Enchantment raw = CraftEnchantment.getRaw(enchantment);
		return raw.a();
	}

	@Override
	protected Map<String,String> doGetMojangUSEnglishTranslations() {
		return readPropertiesLanguage(MinecraftServer.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang"));
	}

	@Override
	public void setGameProfile(Skull skull, GameProfile profile) {
		try {
			Field profileField = CraftSkull.class.getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(skull, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException t) {
			throw new RuntimeException(t);
		}
	}

	@Override
	public void serialize(OutputStream out, NBTCompound compound) throws IOException {
		DataOutputStream dataOut = out instanceof DataOutputStream ? ((DataOutputStream) out) : new DataOutputStream(out);
		NBTCompressedStreamTools.a(compound.getNMSCompound(), (DataOutput) dataOut);
	}

	@Override
	public NBTCompound deserialize(InputStream in) throws IOException {
		DataInputStream dataIn = in instanceof DataInputStream ? ((DataInputStream) in) : new DataInputStream(in);
		return wrapCompound(NBTCompressedStreamTools.a(dataIn));
	}

	@Override
	public AuthLibProperty wrapProperty(Property property) {
		return new AuthLibProperty(property.getName(), property.getValue(), property.getSignature());
	}

	@Override
	public UserProfile toUserProfile(GameProfile profile) {
		UserProfile userProfile = new UserProfile(profile.getId(), profile.getName());
		for (Property property : profile.getProperties().values()) {
			userProfile.addProperty(wrapProperty(property));
		}
		return userProfile;
	}

	@Override
	public GameProfile toGameProfile(UserProfile profile) {
		GameProfile gp = new GameProfile(profile.getId(), profile.getName());
		PropertyMap pm = gp.getProperties();
		for (Map.Entry<String, List<AuthLibProperty>> propEntry : profile.getProperties().entrySet()) {
			for (AuthLibProperty prop : propEntry.getValue()) {
				pm.put(prop.name(), unwrapProperty(prop));
			}
		}
		return gp;
	}
}
