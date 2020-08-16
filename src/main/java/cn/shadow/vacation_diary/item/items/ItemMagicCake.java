package cn.shadow.vacation_diary.item.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class ItemMagicCake extends ItemReedUF
{
	public ItemMagicCake(Block var2, String name) {
		super(var2, name);
	}
	
	public boolean hasEffect(ItemStack var1) {
		return true;
	}
	
	public Rarity getRarity(ItemStack stack) {
		return Rarity.EPIC;
	}
}
