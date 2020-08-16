package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LibraryStudyRoom extends LibraryRoom {

	public LibraryStudyRoom() {
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
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, Blocks.BOOKSHELF);
			chunk.setBlock(x, y, z + 2, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setTable(x + 1, y, z + 2, tableLeg, tableTop);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, Blocks.BOOKSHELF);
			chunk.setBlock(x, y, z, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setTable(x + 1, y, z, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z, Blocks.BIRCH_STAIRS, Direction.EAST);
			break;
		case WEST:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, Blocks.BOOKSHELF);
			chunk.setBlock(x + 2, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setTable(x + 2, y, z + 1, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z + 2, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			break;
		case EAST:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, Blocks.BOOKSHELF);
			chunk.setBlock(x, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setTable(x, y, z + 1, tableLeg, tableTop);
			chunk.setBlock(x, y, z + 2, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			break;
		}
	}

}
