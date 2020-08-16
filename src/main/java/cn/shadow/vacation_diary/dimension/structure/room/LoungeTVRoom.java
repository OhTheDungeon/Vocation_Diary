package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LoungeTVRoom extends LoungeRoom {

	public LoungeTVRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlock(x, y, z + depth - 1, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			chunk.setBlock(x + 1, y, z + depth - 1, Blocks.BIRCH_STAIRS, Direction.SOUTH);
			chunk.setBlock(x + 2, y, z + depth - 1, Blocks.BIRCH_STAIRS, Direction.SOUTH);

			chunk.setBlocks(x, x + 3, y, y + height, z - 1, z, materialWall);

			// TODO add picture to wall at x, x + 3, y, y + height, z, z + 1
			break;
		case SOUTH:
			chunk.setBlock(x, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setBlock(x + 1, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
			chunk.setBlock(x + 2, y, z, Blocks.BIRCH_STAIRS, Direction.NORTH);

			chunk.setBlocks(x, x + 3, y, y + height, z + depth, z + depth + 1, materialWall);
			break;
		case WEST:
			chunk.setBlock(x + width - 1, y, z, Blocks.BIRCH_STAIRS, Direction.EAST);
			chunk.setBlock(x + width - 1, y, z + 1, Blocks.BIRCH_STAIRS, Direction.EAST);
			chunk.setBlock(x + width - 1, y, z + 2, Blocks.BIRCH_STAIRS, Direction.EAST);

			chunk.setBlocks(x - 1, x, y, y + height, z, z + 3, materialWall);
			break;
		case EAST:
			chunk.setBlock(x, y, z, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setBlock(x, y, z + 1, Blocks.BIRCH_STAIRS, Direction.WEST);
			chunk.setBlock(x, y, z + 2, Blocks.BIRCH_STAIRS, Direction.WEST);

			chunk.setBlocks(x + width, x + width + 1, y, y + height, z, z + 3, materialWall);
			break;
		}
	}

}
