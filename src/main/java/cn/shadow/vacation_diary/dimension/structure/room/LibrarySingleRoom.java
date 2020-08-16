package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LibrarySingleRoom extends LibraryRoom {

	public LibrarySingleRoom() {
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
			chunk.setBlocks(x + offset, x + 1 + offset, y, y + height, z, z + depth, Blocks.BOOKSHELF);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			chunk.setBlocks(x, x + width, y, y + height, z + offset, z + 1 + offset, Blocks.BOOKSHELF);
			break;
		}
	}

}
