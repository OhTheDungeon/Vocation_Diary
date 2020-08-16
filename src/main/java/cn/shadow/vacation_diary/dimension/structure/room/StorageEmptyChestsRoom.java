package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.util.Direction;

public class StorageEmptyChestsRoom extends StorageFilledChestsRoom {

	public StorageEmptyChestsRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawChest(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, Direction direction, int x,
			int y, int z) {
		chunk.setChest(generator, x, y, z, direction, odds, generator.lootProvider, LootLocation.EMPTY);
	}

}
