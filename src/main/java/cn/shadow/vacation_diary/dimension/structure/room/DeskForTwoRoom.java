package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class DeskForTwoRoom extends DeskRoom {

	public DeskForTwoRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		Block tableLeg = getTableLeg(odds);
		Block tableTop = getTableTop(odds);

		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			chunk.setTable(x + 1, x + 2, y, z, z + 3, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z, Blocks.BIRCH_STAIRS, Direction.EAST);
			chunk.setBlock(x + 2, y, z + 2, Blocks.BIRCH_STAIRS, Direction.EAST);
			break;
		case SOUTH:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			chunk.setTable(x + 1, x + 2, y, z, z + 3, tableLeg, tableTop);
			chunk.setBlock(x, y, z, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setBlock(x, y, z + 2, Blocks.BIRCH_STAIRS, Direction.WEST);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setTable(x, x + 3, y, z + 1, z + 2, tableLeg, tableTop);
			chunk.setBlock(x, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setBlock(x + 2, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			chunk.setTable(x, x + 3, y, z + 1, z + 2, tableLeg, tableTop);
			chunk.setBlock(x, y, z + 2, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			chunk.setBlock(x + 2, y, z + 2, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			break;
		}
	}

}
