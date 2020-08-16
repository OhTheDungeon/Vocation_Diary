package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LoungeEllCouchRoom extends LoungeCouchRoom {

	public LoungeEllCouchRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall, materialWall,
				materialGlass);

		Block tableLeg = getTableLeg(odds);
		Block tableTop = getTableTop(odds);

		switch (sideWithWall) {
		default:
		case NORTH:
			for (int z1 = z + 1; z1 < z + depth; z1++)
				chunk.setBlock(x + width - 1, y, z1, Blocks.BIRCH_STAIRS, Direction.EAST);
			chunk.setTable(x, x + width - 2, y, z + depth - 1, z + depth, tableLeg, tableTop);
			break;
		case SOUTH:
			for (int z1 = z; z1 < z + depth - 1; z1++)
				chunk.setBlock(x, y, z1, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setTable(x + 2, x + width, y, z, z + 1, tableLeg, tableTop);
			break;
		case WEST:
			for (int x1 = x + 1; x1 < x + width; x1++)
				chunk.setBlock(x1, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setTable(x + 2, x + width, y, z + depth - 1, z + depth, tableLeg, tableTop);
			break;
		case EAST:
			for (int x1 = x; x1 < x + width - 1; x1++)
				chunk.setBlock(x1, y, z + depth - 1, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			chunk.setTable(x, x + width - 2, y, z, z + 1, tableLeg, tableTop);
			break;
		}
	}

}
