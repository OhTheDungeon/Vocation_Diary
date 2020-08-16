package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.BaseBlock;
import cn.shadow.vacation_diary.dimension.support.MaterialList;
import cn.shadow.vacation_diary.dimension.support.Odds;
import net.minecraft.item.ItemStack;

public abstract class LootProvider extends Provider {

	public enum LootLocation {
		EMPTY, RANDOM, SEWER, MINE, BUNKER, BUILDING, WAREHOUSE, FOOD, STORAGE_SHED, FARMWORKS, FARMWORKS_OUTPUT,
		WOODWORKS, WOODWORKS_OUTPUT, STONEWORKS, STONEWORKS_OUTPUT
	}

	public abstract void setLoot(VocationCityWorldGenerator generator, Odds odds, String worldPrefix,
			LootLocation chestLocation, BaseBlock block);

	public abstract void saveLoots();

	public static LootProvider loadProvider(VocationCityWorldGenerator generator) {
		// Based on work contributed by drew-bahrue
		// (https://github.com/echurchill/CityWorld/pull/2)

		LootProvider provider;

		// REMOVED PHATLOOTS, the plugin is currently either forking or being retired, hard to tell
		// try PhatLoots...
		//provider = LootProvider_Phat.loadPhatLoots(generator);

		if (generator.getWorldSettings().useMinecraftLootTables)
			provider = new LootProvider_LootTable();
		else
			provider = new LootProvider_Normal();

		return provider;
	}

	ItemStack[] pickFromTreasures(MaterialList materials, Odds odds, int maxCount, int maxStack) {
		int count = maxCount > 0 ? odds.getRandomInt(maxCount) + 1 : 0;

		// make room
		ItemStack[] items = new ItemStack[count];

		// populate
		for (int i = 0; i < count; i++) {
			ItemStack itemStack = new ItemStack(materials.getRandomMaterial(odds));
			itemStack.setCount(itemStack.getMaxStackSize() == 1 ? 1 : odds.getRandomInt(maxStack) + 1);
			items[i] = itemStack;
		}

		// all done
		return items;
	}

}
