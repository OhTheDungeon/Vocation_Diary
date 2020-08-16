package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class DeskAdminRoom extends DeskRoom {

	public DeskAdminRoom() {
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
			chunk.setTable(x, x + 1, y, z, z + 2, tableLeg, tableTop);
			chunk.setBlock(x, y, z + 2, Blocks.BOOKSHELF);
			chunk.setBlock(x, y + 1, z + 2, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Blocks.BIRCH_STAIRS, Direction.EAST);
			break;
		case SOUTH:
			chunk.setTable(x + 2, x + 3, y, z + 1, z + 3, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z, Blocks.BOOKSHELF);
			chunk.setBlock(x + 2, y + 1, z, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Blocks.BIRCH_STAIRS, Direction.WEST);
			break;
		case WEST:
			chunk.setTable(x, x + 2, y, z + 2, z + 3, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z + 2, Blocks.BOOKSHELF);
			chunk.setBlock(x + 2, y + 1, z + 2, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Blocks.BIRCH_STAIRS, Direction.NORTH);
			break;
		case EAST:
			chunk.setTable(x + 1, x + 3, y, z, z + 1, tableLeg, tableTop);
			chunk.setBlock(x, y, z, Blocks.BOOKSHELF);
			chunk.setBlock(x, y + 1, z, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			break;
		}
	}

}
