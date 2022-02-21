package hk.siggi.bukkit.nbt;

import com.mojang.authlib.GameProfile;
import io.siggi.nbt.NBTToolBukkit;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Deprecated
public class NBTUtil {
	public NBTCompound newCompound() {
		return new NBTCompound();
	}

	public NBTList newList() {
		return new NBTList();
	}

	public NBTCompound getTag(ItemStack stack) {
		io.siggi.nbt.NBTCompound tag = NBTToolBukkit.getTag(stack);
		if (tag == null) return null;
		return new NBTCompound(tag);
	}

	public ItemStack setTag(ItemStack stack, NBTCompound compound) {
		return NBTToolBukkit.setTag(stack, compound == null ? null : compound.unwrap());
	}

	public NBTCompound itemToNBT(ItemStack stack) {
		return new NBTCompound(NBTToolBukkit.itemToNBT(stack));
	}

	public ItemStack itemFromNBT(NBTCompound compound) {
		ItemStack itemStack = NBTToolBukkit.itemFromNBT(compound.unwrap());
		return itemStack;
	}

	public String getItemName(ItemStack stack) {
		return NBTToolBukkit.getItemName(stack);
	}

	public String getEnchantmentName(Enchantment enchantment, int level) {
		return NBTToolBukkit.getEnchantmentName(enchantment, level);
	}

	public ItemStack createPlayerHead(GameProfile profile) {
		return NBTToolBukkit.createPlayerHead(profile);
	}

	public void setGameProfile(Skull skull, GameProfile profile) {
		NBTToolBukkit.setGameProfile(skull, profile);
	}

	public void serialize(OutputStream out, NBTCompound compound) throws IOException {
		NBTToolBukkit.serialize(out, compound.unwrap());
	}

	public NBTCompound deserialize(InputStream in) throws IOException {
		return new NBTCompound(NBTToolBukkit.deserialize(in));
	}
}
