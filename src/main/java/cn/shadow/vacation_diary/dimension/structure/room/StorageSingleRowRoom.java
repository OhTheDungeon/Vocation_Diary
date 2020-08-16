package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class StorageSingleRowRoom extends StorageTypeRoom {

	public StorageSingleRowRoom(Block type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		int offset;
		int minheight = odds.getRandomInt(height - 1);
		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			drawNSEmptyShelve(chunk, x + offset, y, z, 1, depth);
			for (int run = 0; run < depth; run++)
				setStorageBlocks(generator, chunk, odds, x + offset, y + 1,
						y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + run);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			drawWEEmptyShelve(chunk, x, y, z + offset, 1, width);
			for (int run = 0; run < depth; run++)
				setStorageBlocks(generator, chunk, odds, x + run, y + 1,
						y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + offset);
			break;
		}
	}

}
