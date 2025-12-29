/*
 * The MIT License
 *
 * Copyright 2018 Siggi.
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
package io.siggi.nbt.v1_20_R4;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import io.siggi.nbt.util.AuthLibProperty;
import io.siggi.nbt.util.NBTUtil;

import java.util.List;
import java.util.Map;

import io.siggi.nbt.util.UserProfile;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.CraftSkull;
import org.bukkit.craftbukkit.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import java.io.DataInput;
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
		return new NBTCompoundImpl((CompoundTag) compound);
	}

	@Override
	public <T> NBTList wrapList(T list) {
		return new NBTListImpl((ListTag) list);
	}

	@Override
	public NBTCompound getTag(ItemStack stack) {
		return getComponent(stack, "minecraft:custom_data");
	}

	@Override
	public ItemStack setTag(ItemStack stack, NBTCompound compound) {
		return setComponent(stack, "minecraft:custom_data", compound);
	}

	@Override
	public NBTCompound getComponent(ItemStack stack, String component) {
		NBTCompound itemCompound = itemToNBT(stack);
		NBTCompound components = itemCompound.getCompound("components");
		if (!components.keySet().contains(component)) return null;
		return components.getCompound(component);
	}

	@Override
	public ItemStack setComponent(ItemStack stack, String component, NBTCompound compound) {
		NBTCompound itemCompound = itemToNBT(stack);
		if (!itemCompound.keySet().contains("components")) itemCompound.setCompound("components", new NBTCompound());
		NBTCompound components = itemCompound.getCompound("components");
		components.setCompound(component, compound);
		return itemFromNBT(itemCompound);
	}

	@Override
	public NBTCompound itemToNBT(ItemStack stack) {
		try {
			net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
			CompoundTag nmsCompound = (CompoundTag) nmsStack.save(registryAccess());
			NBTCompound compound = new NBTCompoundImpl(nmsCompound);
			compound.setInt("DataVersion", getDataVersion());
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
			DataFixer dataConverterManager = getMinecraftServer().fixerUpper;
			NBTCompound tempItem = compound.copy();
			CompoundTag nmsCompound = (CompoundTag) dataConverterManager.update(
					References.ITEM_STACK,
					new Dynamic(NbtOps.INSTANCE, tempItem.getNMSCompound()),
					tempItem.getInt("DataVersion"),
					getDataVersion()
			).getValue();
			net.minecraft.world.item.ItemStack nmsStack = net.minecraft.world.item.ItemStack.parseOptional(registryAccess(), nmsCompound);
			return CraftItemStack.asCraftMirror(nmsStack);
		} catch (Exception e) {
			return null;
		}
	}

	private int dataVersion = -1;

	private int getDataVersion() {
		if (dataVersion == -1) {
			dataVersion = CraftMagicNumbers.INSTANCE.getDataVersion();
		}
		return dataVersion;
	}

	@Override
	public Class<NBTCompound> getCompoundClass() {
		return NBTCompound.class;
	}

	@Override
	public Class<NBTList> getListClass() {
		return NBTList.class;
	}

	@Override
	public Class<CraftItemStack> getCraftItemStack() {
		return CraftItemStack.class;
	}

	@Override
	public void setAI(Entity entity, boolean ai) {
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).setAI(ai);
		}
	}

	@Override
	public boolean hasAI(Entity entity) {
		if (entity instanceof LivingEntity) {
			return ((LivingEntity) entity).hasAI();
		}
		return false;
	}

	private Item getItem(net.minecraft.world.item.ItemStack nmsStack) {
		return nmsStack.getItem();
	}

	@Override
	public String getItemName(ItemStack stack) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Item item = getItem(nmsStack);
		if (item == null) {
			return "null";
		} else {
			return item.getName(nmsStack).getString();
		}
	}

	@Override
	public String getTranslatableItemName(ItemStack stack) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Item item = getItem(nmsStack);
		if (item == null) {
			return "null";
		} else {
			return item.getDescriptionId(nmsStack);
		}
	}

	private net.minecraft.world.item.enchantment.Enchantment getRaw(Enchantment enchantment) {
		if (enchantment instanceof EnchantmentWrapper) {
			enchantment = ((EnchantmentWrapper) enchantment).getEnchantment();
		}

		return enchantment instanceof CraftEnchantment ? ((CraftEnchantment)enchantment).getHandle() : null;
	}

	@Override
	public String getEnchantmentName(Enchantment enchantment, int level) {
		net.minecraft.world.item.enchantment.Enchantment raw = getRaw(enchantment);
		return raw.getFullname(level).getString();
	}

	@Override
	public String getTranslatableEnchantmentName(Enchantment enchantment) {
		net.minecraft.world.item.enchantment.Enchantment raw = getRaw(enchantment);
		return raw.getDescriptionId();
	}

	@Override
	protected Map<String,String> doGetMojangUSEnglishTranslations() {
		return readJsonLanguage(MinecraftServer.class.getResourceAsStream("/assets/minecraft/lang/en_us.json"));
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
		NbtIo.write(compound.getNMSCompound(), (DataOutput) dataOut);
	}

	@Override
	public NBTCompound deserialize(InputStream in) throws IOException {
		DataInputStream dataIn = in instanceof DataInputStream ? ((DataInputStream) in) : new DataInputStream(in);
		return wrapCompound(NbtIo.read((DataInput) dataIn));
	}

	private MinecraftServer minecraftServer;

	private MinecraftServer getMinecraftServer() {
		if (minecraftServer == null) {
			minecraftServer = ((CraftServer) Bukkit.getServer()).getHandle().getServer();
        }
		return minecraftServer;
	}

	private RegistryAccess.Frozen registryAccess() {
		return getMinecraftServer().registryAccess();
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
