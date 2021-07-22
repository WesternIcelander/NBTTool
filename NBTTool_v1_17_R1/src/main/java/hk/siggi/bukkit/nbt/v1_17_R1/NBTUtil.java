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
package hk.siggi.bukkit.nbt.v1_17_R1;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;

import java.lang.reflect.Field;

import net.minecraft.core.IRegistry;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSkull;
import org.bukkit.craftbukkit.v1_17_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

final class NBTUtil extends hk.siggi.bukkit.nbt.NBTUtil<NBTUtil, NBTCompound, NBTList, NBTTagCompound, NBTTagList, CraftItemStack> {

	@Override
	public NBTCompound newCompound() {
		return new NBTCompound();
	}

	@Override
	public NBTList newList() {
		return new NBTList();
	}

	@Override
	public NBTCompound wrapCompound(NBTTagCompound compound) {
		return new NBTCompound(compound);
	}

	@Override
	public NBTList wrapList(NBTTagList list) {
		return new NBTList(list);
	}

	@Override
	public NBTCompound getTag(ItemStack stack) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		if (nmsStack.hasTag()) {
			return wrapCompound(nmsStack.getTag());
		} else {
			return null;
		}
	}

	@Override
	public ItemStack setTag(ItemStack stack, NBTCompound compound) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
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
			net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
			NBTTagCompound nmsCompound = new NBTTagCompound();
			nmsStack.save(nmsCompound);
			NBTCompound compound = new NBTCompound(nmsCompound);
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
			DataFixer dataConverterManager = ((CraftServer) Bukkit.getServer()).getHandle().getServer().O;
			NBTCompound tempItem = compound.clone();
			NBTTagCompound nmsCompound = (NBTTagCompound) dataConverterManager.update(
					DataConverterTypes.m,
					new Dynamic(DynamicOpsNBT.a, tempItem.compound),
					tempItem.getInt("DataVersion"),
					getDataVersion()
			).getValue();
			net.minecraft.world.item.ItemStack nmsStack = net.minecraft.world.item.ItemStack.a(nmsCompound);
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
	public Entity summonEntity(NBTCompound nbtTag, Location location, CreatureSpawnEvent.SpawnReason reason) {
		WorldServer worldserver = ((CraftWorld) location.getWorld()).getHandle();
		World world = worldserver.getMinecraftWorld();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		net.minecraft.world.entity.Entity nmsEntity = EntityTypes.a(nbtTag.getNMSCompound(), worldserver, (ent) -> {
			ent.setPositionRotation(x, y, z, ent.x/*yaw*/, ent.y/*pitch*/);
			return !worldserver.addEntitySerialized(ent, reason) ? null : ent;
		});
		if (nmsEntity == null) {
			return null;
		}
		nmsEntity.setPositionRotation(x, y, z, nmsEntity.x/*yaw*/, nmsEntity.y/*pitch*/);
		return nmsEntity.getBukkitEntity();
	}

	@Override
	public void setAI(Entity entity, boolean ai) {
		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		if (nmsEntity instanceof EntityInsentient) {
			EntityInsentient entityInsentient = (EntityInsentient) nmsEntity;
			entityInsentient.setNoAI(!ai);
		}
	}

	@Override
	public boolean hasAI(Entity entity) {
		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		if (nmsEntity instanceof EntityInsentient) {
			EntityInsentient entityInsentient = (EntityInsentient) nmsEntity;
			return !entityInsentient.isNoAI();
		} else {
			return false;
		}
	}

	@Override
	public String getItemName(ItemStack stack) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		NBTTagCompound nbttc = new NBTTagCompound();
		nmsStack.save(nbttc);
		NBTCompound compound = wrapCompound(nbttc);
		String id = compound.getString("id");
		Item item = (Item) IRegistry.Z.get(new MinecraftKey(id));
		if (item == null) {
			return "null";
		} else {
			return item.m(nmsStack).getString();
		}
	}

	@Override
	public String getEnchantmentName(Enchantment enchantment, int level) {
		net.minecraft.world.item.enchantment.Enchantment raw = CraftEnchantment.getRaw(enchantment);
		return raw.d(level).getString();
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
}
