package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class StorageSingleShelvesRoom extends StorageRoom {

	public StorageSingleShelvesRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		int offset;
		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			drawNSEmptyShelve(chunk, x + offset, y, z, height - 1, depth);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			drawWEEmptyShelve(chunk, x, y, z + offset, height - 1, width);
			break;
		}
	}

}
