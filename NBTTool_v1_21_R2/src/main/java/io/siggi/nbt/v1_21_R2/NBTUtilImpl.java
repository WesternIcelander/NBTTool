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
package io.siggi.nbt.v1_21_R2;

import com.mojang.authlib.GameProfile;
import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import io.siggi.nbt.util.NBTUtil;
import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_21_R2.CraftServer;
import org.bukkit.craftbukkit.v1_21_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R2.block.CraftSkull;
import org.bukkit.craftbukkit.v1_21_R2.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_21_R2.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
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
		return new NBTCompoundImpl((NBTTagCompound) compound);
	}

	@Override
	public <T> NBTList wrapList(T list) {
		return new NBTListImpl((NBTTagList) list);
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
			NBTTagCompound nmsCompound = (NBTTagCompound) nmsStack.b(registryAccess());
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
			DataFixer dataConverterManager = getMinecraftServer().L;
			NBTCompound tempItem = compound.copy();
			NBTTagCompound nmsCompound = (NBTTagCompound) dataConverterManager.update(
					DataConverterTypes.t,
					new Dynamic(DynamicOpsNBT.a, tempItem.getNMSCompound()),
					tempItem.getInt("DataVersion"),
					getDataVersion()
			).getValue();
			net.minecraft.world.item.ItemStack nmsStack = net.minecraft.world.item.ItemStack.a(registryAccess(), nmsCompound);
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
		return nmsStack.h();
	}

	@Override
	public String getItemName(ItemStack stack) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Item item = getItem(nmsStack);
		if (item == null) {
			return "null";
		} else {
			return item.a(nmsStack).getString();
		}
	}

	@Override
	public String getTranslatableItemName(ItemStack stack) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Item item = getItem(nmsStack);
		if (item == null) {
			return "null";
		} else {
			return item.l();
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
		IChatMutableComponent base = IChatBaseComponent.c(getTranslatableEnchantmentName(enchantment));
		if (level != 1 || getRaw(enchantment).e() != 1) {
			base.b(CommonComponents.v).b(IChatBaseComponent.c("enchantment.level." + level));
		}
		return base.getString();
	}

	@Override
	public String getTranslatableEnchantmentName(Enchantment enchantment) {
		NamespacedKey key = enchantment.getKey();
		return "enchantment." + key.getNamespace() + "." + (key.getKey().replace("/", "."));
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
			profileField.set(skull, new ResolvableProfile(profile));
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
		return wrapCompound(NBTCompressedStreamTools.a((DataInput) dataIn));
	}

	private MinecraftServer minecraftServer;

	private MinecraftServer getMinecraftServer() {
		if (minecraftServer == null) {
			minecraftServer = ((CraftServer) Bukkit.getServer()).getHandle().b();
        }
		return minecraftServer;
	}

	private IRegistryCustom.Dimension registryAccess() {
		return getMinecraftServer().ba();
	}
}
