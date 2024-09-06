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
package io.siggi.nbt.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTList;
import io.siggi.nbt.NBTTool;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * This class provides allows you to construct {@link NBTCompound}s and
 * {@link NBTList}s and provides some utility methods to use with
 * {@link ItemStack}s. Get an instance of this class with
 * {@link NBTTool#getUtil()}.
 *
 * @author Siggi
 */
public abstract class NBTUtil {

	private Map<String,String> enUsTranslations;

	/**
	 * Creates a new {@link NBTCompound} with no items in it.
	 *
	 * @deprecated Just instantiate {@link NBTCompound} directly.
	 * @return a new blank {@link NBTCompound}
	 */
	@Deprecated
	public abstract NBTCompound newCompound();

	/**
	 * Creates a new {@link NBTList} with no items in it.
	 *
	 * @deprecated Just instantiate {@link NBTList} directly.
	 * @return a new blank {@link NBTList}
	 */
	@Deprecated
	public abstract NBTList newList();

	/**
	 * Wraps an NMS NBTTagCompound in an {@link NBTCompound}.
	 *
	 * @param compound NBTTagCompound to wrap
	 * @return an {@link NBTCompound}
	 */
	public abstract <T> NBTCompound wrapCompound(T compound);

	/**
	 * Wraps an NMS NBTTagList in an {@link NBTList}.
	 *
	 * @param list NBTTagList to wrap
	 * @return an {@link NBTList}
	 */
	public abstract <T> NBTList wrapList(T list);

	/**
	 * Gets the NBT tag of the specified {@link ItemStack}, which may be null.
	 *
	 * @param stack the item to get the NBT tag of, or null if the item doesn't
	 * have one.
	 * @return an {@link NBTCompound} containing the NBT tag of the item stack.
	 */
	public NBTCompound getTag(ItemStack stack) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a copy of the specified {@link ItemStack} and sets the NBT Tag on
	 * the copy to the specified tag and returns the copy.
	 *
	 * @param stack the item to set a new NBT tag on.
	 * @param compound the NBT tag to set on the item.
	 * @return an {@link ItemStack} with the newly set NBT tag.
	 */
	public ItemStack setTag(ItemStack stack, NBTCompound compound) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets an NBT component of the specified {@link ItemStack}, which may be null.
	 *
	 * @param stack the item to get the NBT tag of, or null if the item doesn't
	 * have one.
	 * @param component the name of the component to get.
	 * @return an {@link NBTCompound} containing the NBT tag of the item stack.
	 */
	public NBTCompound getComponent(ItemStack stack, String component) {
		if (component.equals("minecraft:custom_data")) {
			return getTag(stack);
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a copy of the specified {@link ItemStack} and sets the NBT component on
	 * the copy to the specified tag and returns the copy.
	 *
	 * @param stack the item to set a new NBT tag on.
	 * @param component the name of the component to set.
	 * @param compound the NBT component to set on the item.
	 * @return an {@link ItemStack} with the newly set NBT tag.
	 */
	public ItemStack setComponent(ItemStack stack, String component, NBTCompound compound) {
		if (component.equals("minecraft:custom_data")) {
			return setTag(stack, compound);
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * Serializes an {@link ItemStack} to an {@link NBTCompound}. NBTCompound
	 * representations of ItemStacks are guaranteed to be compatible with
	 * versions of Minecraft newer than ones they were serialized in, but not
	 * the other way around.
	 *
	 * @param stack the {@link ItemStack} to serialize to NBT.
	 * @return the ItemStack in it's NBT representation.
	 */
	public NBTCompound itemToNBT(ItemStack stack) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Deserializes an {@link ItemStack} from an {@link NBTCompound}. Guaranteed
	 * to work flawlessly as long as the NBTCompound representation of this
	 * ItemStack was created in the same or older version of Minecraft.
	 *
	 * @param compound the ItemStack in it's NBT representation
	 * @return the {@link ItemStack}
	 */
	public ItemStack itemFromNBT(NBTCompound compound) {
		throw new UnsupportedOperationException();
	}

	public abstract Class<? extends NBTCompound> getCompoundClass();

	public abstract Class<? extends NBTList> getListClass();

	public Class<? extends ItemStack> getCraftItemStack() {
		throw new UnsupportedOperationException();
	}

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
	public Entity summonEntity(NBTCompound nbtTag, Location location, CreatureSpawnEvent.SpawnReason reason) {
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
	 * Get the name of an item stack in US English. If you need the name in another language, use
	 * {@link #getTranslatableItemName(ItemStack)} to get a translatable string and pass that to an I18n library.
	 *
	 * @param itemStack the item stack you want to get the name of.
	 * @return name of the item stack
	 * @see #getTranslatableItemName(ItemStack)
	 */
	public String getItemName(ItemStack itemStack) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the translatable string of an item name. If you need a quicker way to get an item name in US English, you can
	 * use {@link #getItemName(ItemStack)}.
	 *
	 * @param itemStack the item stack you want to get the translatable string of.
	 * @return translatable string of the item stack
	 * @see #getItemName(ItemStack) 
	 */
	public String getTranslatableItemName(ItemStack itemStack) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get an enchantment name in US English. If you need the name in another language, use
	 * {@link #getTranslatableEnchantmentName(Enchantment)} to get a translatable string and pass that to an I18n
	 * library.
	 *
	 * @param enchantment the enchantment you want the name of
	 * @param level the level of the enchantment you want the name of
	 * @return the name of the enchantment
	 * @see #getTranslatableEnchantmentName(Enchantment)
	 */
	public String getEnchantmentName(Enchantment enchantment, int level) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the translatable string of an enchantment name. If you need a quicker way to get an enchantment name in US
	 * English, you can use {@link #getEnchantmentName(Enchantment, int)}.
	 *
	 * @param enchantment the enchantment you want to get the translatable string of.
	 * @return the translatable string of the enchantment
	 * @see #getEnchantmentName(Enchantment, int)
	 */
	public String getTranslatableEnchantmentName(Enchantment enchantment) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the Minecraft translations in US English.
	 *
	 * @return the Minecraft translations in US English.
	 */
	public Map<String,String> getMojangUSEnglishTranslations() {
		if (enUsTranslations == null) {
			enUsTranslations = Collections.unmodifiableMap(doGetMojangUSEnglishTranslations());
		}
		return enUsTranslations;
	}

	protected Map<String,String> doGetMojangUSEnglishTranslations() {
		throw new UnsupportedOperationException();
	}

	protected Map<String,String> readJsonLanguage(InputStream in) {
		Map<String,String> map = new HashMap<>();
		JsonObject object = new JsonParser().parse(new InputStreamReader(in)).getAsJsonObject();
		for (String key : object.keySet()) {
			try {
				String value = object.get(key).getAsString();
				map.put(key, value);
			} catch (Exception ignored) {
			}
		}
		return map;
	}

	protected Map<String,String> readPropertiesLanguage(InputStream in) {
		Map<String,String> map = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				int equalPos = line.indexOf("=");
				if (equalPos == -1) continue;
				String key = line.substring(0, equalPos);
				String value = line.substring(equalPos + 1);
				map.put(key, value);
			}
		} catch (IOException ignored) {
		}
		return map;
	}

	/**
	 * Create a player head with a GameProfile.
	 *
	 * @param profile the profile to use on the player head
	 * @return
	 */
	public ItemStack createPlayerHead(GameProfile profile) {
		NBTCompound item = newCompound();
		item.setByte("Count", (byte) 1);
		item.setShort("Damage", (short) 3);
		item.setShort("id", (short) 397);

		NBTCompound tag = newCompound();
		item.setCompound("tag", tag);

		NBTCompound skullOwner = newCompound();
		tag.setCompound("SkullOwner", skullOwner);

		UUID id = profile.getId();
		if (id != null) {
			skullOwner.setString("Id", id.toString().toLowerCase());
		}

		String name = profile.getName();
		if (name != null) {
			skullOwner.setString("Name", name);
		}

		NBTCompound properties = newCompound();
		skullOwner.setCompound("Properties", properties);

		AuthLibProperty textures = null;
		PropertyMap props = profile.getProperties();
		for (Map.Entry<String, Property> entry : props.entries()) {
			if (entry.getKey().equals("textures")) {
				textures = wrapProperty(entry.getValue());
				break;
			}
		}

		if (textures != null) {
			NBTList texturesList = newList();
			properties.setList("textures", texturesList);

			NBTCompound texturesCompound = newCompound();
			texturesList.addCompound(texturesCompound);

			String value = textures.value();
			if (value != null) {
				texturesCompound.setString("Value", value);
			}

			String signature = textures.signature();
			if (signature != null) {
				texturesCompound.setString("Signature", signature);
			}
		}

		return itemFromNBT(item);
	}

	/**
	 * Create a player head with a texture URL.
	 *
	 * @param textureUrl the texture URL
	 * @return
	 */
	public ItemStack createPlayerHeadFromTexture(String textureUrl) {
		GameProfile profile = new GameProfile(new UUID(0L, 0L), "Textured Head");
		String texturePayload = Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + textureUrl + "\"}}}").getBytes(StandardCharsets.UTF_8));
		profile.getProperties().put("textures", new Property("textures", texturePayload));
		return createPlayerHead(profile);
	}

	/**
	 * Set the GameProfile for a player head.
	 *
	 * @param skull the player head to set the GameProfile for.
	 * @param profile the profile to set it to.
	 */
	public void setGameProfile(Skull skull, GameProfile profile) {
		throw new UnsupportedOperationException();
	}

	public abstract void serialize(OutputStream out, NBTCompound compound) throws IOException;

	public abstract NBTCompound deserialize(InputStream in) throws IOException;

	public AuthLibProperty wrapProperty(Property property) {
		return new AuthLibProperty(property.name(), property.value(), property.signature());
	}
}
