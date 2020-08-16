package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class StorageDoubleShelvesRoom extends StorageRoom {

	public StorageDoubleShelvesRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			drawNSEmptyShelves(chunk, x, y, z, width, height - 1, depth, 0);
			break;
		case SOUTH:
			drawNSEmptyShelves(chunk, x, y, z, width, height - 1, depth, depth - 1);
			break;
		case EAST:
			drawWEEmptyShelves(chunk, x, y, z, width, height - 1, depth, width - 1);
			break;
		case WEST:
			drawWEEmptyShelves(chunk, x, y, z, width, height - 1, depth, 0);
			break;
		}
	}

	private void drawNSEmptyShelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < width; offset += 2) {
			drawNSEmptyShelve(chunk, x + offset, y, z, height, depth);
		}
	}

	private void drawWEEmptyShelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < depth; offset += 2) {
			drawWEEmptyShelve(chunk, x, y, z + offset, height, width);
		}
	}
}
