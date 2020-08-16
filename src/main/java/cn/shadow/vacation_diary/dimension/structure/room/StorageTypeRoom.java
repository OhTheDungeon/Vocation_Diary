package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

abstract class StorageTypeRoom extends StorageRoom {

	private final Block materialType;

	StorageTypeRoom(Block type) {
		super();
		materialType = type;
	}

	void setStorageBlocks(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y1, int y2,
			int z) {
		if (materialType == Blocks.PISTON) {
			chunk.setBlocks(x, x + 1, y1, y2, z, z + 1, materialType, Direction.UP);
		} else {
			chunk.setBlocks(x, x + 1, y1, y2, z, z + 1, materialType);
		}
	}

}
