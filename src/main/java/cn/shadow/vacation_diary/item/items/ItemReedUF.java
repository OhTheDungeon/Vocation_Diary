package cn.shadow.vacation_diary.item.items;

import cn.shadow.vacation_diary.item.ModGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ItemReedUF extends BlockItem 
{
	String name;
	
	public ItemReedUF(Block var2, String name) {
		super(var2, (new Item.Properties()).maxStackSize(1).group(ModGroup.itemGroup));
		this.name = name;
	}
}
