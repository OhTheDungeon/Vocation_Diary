package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class MeetingForSixRoom extends MeetingForFourRoom {

	public MeetingForSixRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall, materialWall,
				materialGlass);

		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			chunk.setBlock(x, y, z + 1, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setBlock(x + 2, y, z + 1, Blocks.BIRCH_STAIRS, Direction.EAST);
			break;
		case WEST:
		case EAST:
			chunk.setBlock(x + 1, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setBlock(x + 1, y, z + 2, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			break;
		}
	}

}
