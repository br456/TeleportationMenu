package me.br456.Telepads;

import java.io.Serializable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("serial")
public class SerializableItemStack implements Serializable{
	private Material m;
	private Short damage;
	
	public SerializableItemStack(ItemStack is) {
		m = is.getType();
		damage = is.getDurability();
	}
	
	public ItemStack getItemStack() {
		return new ItemStack(m, 1, damage);
	}
}
