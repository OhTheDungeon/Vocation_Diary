package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LoungeCouchRoom extends LoungeRoom {

	public LoungeCouchRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			for (int x1 = x; x1 < x + width; x1++)
				chunk.setBlock(x1, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			break;
		case SOUTH:
			for (int x1 = x; x1 < x + width; x1++)
				chunk.setBlock(x1, y, z + depth - 1, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			break;
		case WEST:
			for (int z1 = z; z1 < z + depth; z1++)
				chunk.setBlock(x, y, z1, Blocks.BIRCH_STAIRS, Direction.WEST);
			break;
		case EAST:
			for (int z1 = z; z1 < z + depth; z1++)
				chunk.setBlock(x + width - 1, y, z1, Blocks.BIRCH_STAIRS, Direction.EAST);
			break;
		}
	}

}
