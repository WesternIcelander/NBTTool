package io.siggi.nbt;

import com.mojang.authlib.GameProfile;
import io.siggi.nbt.util.NBTUtil;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NBTToolBukkit {
	private NBTToolBukkit() {
	}

	private static NBTUtil getUtil() {
		if (NBTTool.nbtutil == null)
			throw new UnsupportedOperationException();
		return NBTTool.nbtutil;
	}

	/**
	 * Gets the NBT tag of the specified {@link ItemStack}, which may be null.
	 *
	 * @param stack the item to get the NBT tag of, or null if the item doesn't
	 *              have one.
	 * @return an {@link NBTCompound} containing the NBT tag of the item stack.
	 */
	public static NBTCompound getTag(ItemStack stack) {
		return getUtil().getTag(stack);
	}

	/**
	 * Creates a copy of the specified {@link ItemStack} and sets the NBT Tag on
	 * the copy to the specified tag and returns the copy.
	 *
	 * @param stack    the item to set a new NBT tag on.
	 * @param compound the NBT tag to set on the item.
	 * @return an {@link ItemStack} with the newly set NBT tag.
	 */
	public static ItemStack setTag(ItemStack stack, NBTCompound compound) {
		return getUtil().setTag(stack, compound);
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
	public static NBTCompound itemToNBT(ItemStack stack) {
		return getUtil().itemToNBT(stack);
	}

	/**
	 * Deserializes an {@link ItemStack} from an {@link NBTCompound}. Guaranteed
	 * to work flawlessly as long as the NBTCompound representation of this
	 * ItemStack was created in the same or older version of Minecraft.
	 *
	 * @param compound the ItemStack in it's NBT representation
	 * @return the {@link ItemStack}
	 */
	public static ItemStack itemFromNBT(NBTCompound compound) {
		return getUtil().itemFromNBT(compound);
	}

	/**
	 * Get the name of an item stack.
	 *
	 * @param itemStack the item stack you want to get the name of.
	 * @return name of the item stack
	 */
	public static String getItemName(ItemStack itemStack) {
		return getUtil().getItemName(itemStack);
	}

	/**
	 * Get an enchantment name.
	 *
	 * @param enchantment the enchantment you want the name of
	 * @param level       the level of the enchantment you want the name of
	 * @return the name of the enchantment
	 */
	public static String getEnchantmentName(Enchantment enchantment, int level) {
		return getUtil().getEnchantmentName(enchantment, level);
	}

	/**
	 * Create a player head with a GameProfile.
	 *
	 * @param profile the profile to use on the player head
	 * @return
	 */
	public static ItemStack createPlayerHead(GameProfile profile) {
		return getUtil().createPlayerHead(profile);
	}

	/**
	 * Set the GameProfile for a player head.
	 *
	 * @param skull   the player head to set the GameProfile for.
	 * @param profile the profile to set it to.
	 */
	public static void setGameProfile(Skull skull, GameProfile profile) {
		getUtil().setGameProfile(skull, profile);
	}

	public static void serialize(OutputStream out, NBTCompound compound) throws IOException {
		getUtil().serialize(out, compound);
	}

	public static NBTCompound deserialize(InputStream in) throws IOException {
		return getUtil().deserialize(in);
	}
}
