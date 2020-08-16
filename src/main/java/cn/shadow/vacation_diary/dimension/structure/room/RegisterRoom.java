package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class RegisterRoom extends FilledRoom {

	public RegisterRoom() {
		super();
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + 2, z, z + 3, materialWall);
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 3, Blocks.GLASS_PANE, Direction.NORTH, Direction.SOUTH);
			chunk.setBlocks(x + 1, x + 2, y, y + 1, z, z + 1, materialWall);
			chunk.setBlock(x + 1, y + 1, z, Blocks.QUARTZ_STAIRS, Direction.NORTH);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z, z + 3, Blocks.PISTON, Direction.UP);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + 1, y, y + 2, z, z + 3, materialWall);
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 3, Blocks.GLASS_PANE, Direction.NORTH, Direction.SOUTH);
			chunk.setBlocks(x + 1, x + 2, y, y + 1, z + 2, z + 3, materialWall);
			chunk.setBlock(x + 1, y + 1, z + 2, Blocks.QUARTZ_STAIRS, Direction.SOUTH);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z, z + 3, Blocks.PISTON, Direction.UP);
			break;
		case WEST:
			chunk.setBlocks(x, x + 3, y, y + 2, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 3, y + 2, y + 3, z, z + 1, Blocks.GLASS_PANE, Direction.EAST, Direction.WEST);
			chunk.setBlocks(x, x + 1, y, y + 1, z + 1, z + 2, materialWall);
			chunk.setBlock(x, y + 1, z + 1, Blocks.QUARTZ_STAIRS, Direction.WEST);
			chunk.setBlocks(x, x + 3, y, y + 1, z + 2, z + 3, Blocks.PISTON, Direction.UP);
			break;
		case EAST:
			chunk.setBlocks(x, x + 3, y, y + 2, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 3, y + 2, y + 3, z, z + 1, Blocks.GLASS_PANE, Direction.EAST, Direction.WEST);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z + 1, z + 2, materialWall);
			chunk.setBlock(x + 2, y + 1, z + 1, Blocks.QUARTZ_STAIRS, Direction.EAST);
			chunk.setBlocks(x, x + 3, y, y + 1, z + 2, z + 3, Blocks.PISTON, Direction.UP);
			break;
		}
	}

}
