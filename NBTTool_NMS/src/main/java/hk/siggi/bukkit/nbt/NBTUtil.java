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
package hk.siggi.bukkit.nbt;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class provides allows you to construct {@link NBTCompound}s and
 * {@link NBTList}s and provides some utility methods to use with
 * {@link ItemStack}s. Get an instance of this class with
 * {@link NBTTool#getUtil()}.
 *
 * @author Siggi
 * @param <F> the class implementing NBTUtil.
 * @param <C> the class implementing NBTCompound.
 * @param <L> the class implementing NBTList.
 * @param <NMSCompound> the NMS NBTTagCompound.
 * @param <NMSList> the NMS NBTTagList.
 * @param <CItemStack> the CraftBukkit class implementing ItemStack.
 */
public abstract class NBTUtil<F extends NBTUtil, C extends NBTCompound, L extends NBTList, NMSCompound, NMSList, CItemStack> {

	/**
	 * Creates a new {@link NBTCompound} with no items in it.
	 *
	 * @return a new blank {@link NBTCompound}
	 */
	public abstract C newCompound();

	/**
	 * Creates a new {@link NBTList} with no items in it.
	 *
	 * @return a new blank {@link NBTList}
	 */
	public abstract L newList();

	/**
	 * Wraps an NMS NBTTagCompound in an {@link NBTCompound}.
	 *
	 * @param compound NBTTagCompound to wrap
	 * @return an {@link NBTCompound}
	 */
	public abstract C wrapCompound(NMSCompound compound);

	/**
	 * Wraps an NMS NBTTagList in an {@link NBTList}.
	 *
	 * @param list NBTTagList to wrap
	 * @return an {@link NBTList}
	 */
	public abstract L wrapList(NMSList list);

	/**
	 * Gets the NBT tag of the specified {@link ItemStack}, which may be null.
	 *
	 * @param stack the item to get the NBT tag of, or null if the item doesn't
	 * have one.
	 * @return an {@link NBTCompound} containing the NBT tag of the item stack.
	 */
	public abstract C getTag(ItemStack stack);

	/**
	 * Creates a copy of the specified {@link ItemStack} and sets the NBT Tag on
	 * the copy to the specified tag and returns the copy.
	 *
	 * @param stack the item to set a new NBT tag on.
	 * @param compound the NBT tag to set on the item.
	 * @return an {@link ItemStack} with the newly set NBT tag.
	 */
	public abstract ItemStack setTag(ItemStack stack, C compound);

	/**
	 * Serializes an {@link ItemStack} to an {@link NBTCompound}. NBTCompound
	 * representations of ItemStacks are guaranteed to be compatible with
	 * versions of Minecraft newer than ones they were serialized in, but not
	 * the other way around.
	 *
	 * @param stack the {@link ItemStack} to serialize to NBT.
	 * @return the ItemStack in it's NBT representation.
	 */
	public abstract C itemToNBT(ItemStack stack);

	/**
	 * Deserializes an {@link ItemStack} from an {@link NBTCompound}. Guaranteed
	 * to work flawlessly as long as the NBTCompound representation of this
	 * ItemStack was created in the same or older version of Minecraft.
	 *
	 * @param compound the ItemStack in it's NBT representation
	 * @return the {@link ItemStack}
	 */
	public abstract ItemStack itemFromNBT(C compound);

	public abstract Class<C> getCompoundClass();

	public abstract Class<L> getListClass();

	public abstract Class<CItemStack> getCraftItemStack();

	/**
	 * 1.10 or higher only: Summon an entity almost as if done by the
	 * <code>/summon</code> command. You have a bit more control over the
	 * location (You can specify the world, not just the coordinates relative to
	 * a {@link CommandSender}) and can also provide a SpawnReason for Bukkit
	 * events. The entity type has to be specified in a string tag
	 * <code>id</code>.
	 *
	 * @param nbtTag NBT tag for the entity.
	 * @param location the location to spawn this entity in.
	 * @param reason the reason the entity was spawned.
	 * @throws UnsupportedOperationException if this method is called on server
	 * version lower than 1.10.
	 * @return The entity if summoned successfully, or null if it could not be
	 * @since v1_10_R1 summoned.
	 */
	public Entity summonEntity(C nbtTag, Location location, CreatureSpawnEvent.SpawnReason reason) {
		throw new UnsupportedOperationException("Not available on this version.");
	}

	/**
	 * 1.8R2 or higher only: Enable or disable AI for an entity.
	 *
	 * @param entity the entity to change AI for
	 * @param ai whether the AI should be enabled or not
	 * @throws UnsupportedOperationException if this method is called on server
	 * version lower than 1.8R2.
	 * @since v1_8_R2
	 */
	public void setAI(Entity entity, boolean ai) {
		throw new UnsupportedOperationException("Not available on this version.");
	}

	/**
	 * 1.8R2 or higher only: Check whether AI is enabled for an entity.
	 *
	 * @param entity the entity to check AI status of
	 * @throws UnsupportedOperationException if this method is called on server
	 * version lower than 1.8R2.
	 * @return whether the AI is enabled or not
	 * @since v1_8_R2
	 */
	public boolean hasAI(Entity entity) {
		throw new UnsupportedOperationException("Not available on this version.");
	}

	/**
	 * Get the name of an item stack.
	 *
	 * @param itemStack the item stack you want to get the name of.
	 * @return name of the item stack
	 */
	public abstract String getItemName(ItemStack itemStack);

	/**
	 * Get an enchantment name.
	 *
	 * @param enchantment the enchantment you want the name of
	 * @param level the level of the enchantment you want the name of
	 * @return the name of the enchantment
	 */
	public abstract String getEnchantmentName(Enchantment enchantment, int level);

	/**
	 * Create a player head with a GameProfile.
	 *
	 * @param profile the profile to use on the player head
	 * @return
	 */
	public ItemStack createPlayerHead(GameProfile profile) {
		C item = newCompound();
		item.setByte("Count", (byte) 1);
		item.setShort("Damage", (short) 3);
		item.setShort("id", (short) 397);

		C tag = newCompound();
		item.setCompound("tag", tag);

		C skullOwner = newCompound();
		tag.setCompound("SkullOwner", skullOwner);

		UUID id = profile.getId();
		if (id != null) {
			skullOwner.setString("Id", id.toString().toLowerCase());
		}

		String name = profile.getName();
		if (name != null) {
			skullOwner.setString("Name", name);
		}

		C properties = newCompound();
		skullOwner.setCompound("Properties", properties);

		Property textures = null;
		PropertyMap props = profile.getProperties();
		for (Map.Entry<String, Property> entry : props.entries()) {
			if (entry.getKey().equals("textures")) {
				textures = entry.getValue();
				break;
			}
		}

		if (textures != null) {
			L texturesList = newList();
			properties.setList("textures", texturesList);

			C texturesCompound = newCompound();
			texturesList.addCompound(texturesCompound);

			String value = textures.getValue();
			if (value != null) {
				texturesCompound.setString("Value", value);
			}

			String signature = textures.getSignature();
			if (signature != null) {
				texturesCompound.setString("Signature", signature);
			}
		}

		return itemFromNBT(item);
	}

	/**
	 * Set the GameProfile for a player head.
	 *
	 * @param skull the player head to set the GameProfile for.
	 * @param profile the profile to set it to.
	 */
	public abstract void setGameProfile(Skull skull, GameProfile profile);

	public abstract void serialize(OutputStream out, C compound) throws IOException;

	public abstract C deserialize(InputStream in) throws IOException;
}
