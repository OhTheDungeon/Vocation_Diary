package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LibraryDoubleRoom extends LibraryRoom {

	public LibraryDoubleRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {

		switch (sideWithWall) {
		default:
		case NORTH:
			drawNSBookshelves(chunk, x, y, z, width, height, depth, 0);
			break;
		case SOUTH:
			drawNSBookshelves(chunk, x, y, z, width, height, depth, depth - 1);
			break;
		case EAST:
			drawWEBookshelves(chunk, x, y, z, width, height, depth, width - 1);
			break;
		case WEST:
			drawWEBookshelves(chunk, x, y, z, width, height, depth, 0);
			break;
		}
	}

	private void drawNSBookshelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < width; offset += 2) {
			chunk.setBlocks(x + offset, x + 1 + offset, y, y + height, z, z + depth, Blocks.BOOKSHELF);
			if (offset < width - 1)
				chunk.setBlock(x + offset + 1, y, z + i, Blocks.BOOKSHELF);
//				chunk.setBlock(x + offset + 1, y, z + i, Material.ENCHANTMENT_TABLE);
		}
	}

	private void drawWEBookshelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < depth; offset += 2) {
			chunk.setBlocks(x, x + width, y, y + height, z + offset, z + 1 + offset, Blocks.BOOKSHELF);
			if (offset < depth - 1)
				chunk.setBlock(x + i, y, z + offset + 1, Blocks.BOOKSHELF);
//				chunk.setBlock(x + i, y, z + offset + 1, Material.ENCHANTMENT_TABLE);
		}
	}
}
