package cn.shadow.vacation_diary.dimension.support;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class MaterialList {

	public final String listName;
	private List<Item> items;

	public MaterialList(String name) {
		super();
		listName = name;
	}

	public MaterialList(String name, Item... items) {
		super();
		listName = name;
		add(items);
	}

	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<>();
		else if (clear)
			items.clear();
	}
	
	private void add(Item... citems) {
		init(false);
		for (Item item : citems) {
			items.add(item);
		}
	}

	public void remove(Item item) {
		if (items != null)
			for (int i = items.size() - 1; i >= 0; i--)
				if (items.get(i) == item)
					items.remove(i);
	}

	public int count() {
		return items == null ? 0 : items.size();
	}

	public Item getRandomMaterial(Odds odds) {
		return getRandomMaterial(odds, Items.AIR);
	}

	public Item getRandomMaterial(Odds odds, Item defaultMaterial) {
		if (items == null || count() == 0)
			return defaultMaterial;
		else
			return items.get(odds.getRandomInt(count()));
	}
}
