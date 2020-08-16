package cn.shadow.vacation_diary.item.items;

import net.minecraft.item.Item;

public class ItemUF extends Item {
	String name;
	
	public ItemUF(String name, Item.Properties properities) {
		super(properities);
		this.name = name;
	}
}
