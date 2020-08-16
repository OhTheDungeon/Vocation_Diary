package cn.shadow.vacation_diary.item.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class ItemMagicIceCream extends ItemBowl{

	public ItemMagicIceCream(String name, int var2, float var3, boolean var4) {
		super(name, var2, var3, var4);
	}

	public boolean hasEffect(ItemStack var1) {
		return true;
	}
	
	public Rarity getRarity(ItemStack stack) {
		return Rarity.EPIC;
	}
}
