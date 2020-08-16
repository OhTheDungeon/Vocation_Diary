package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;

abstract class StorageRoom extends FilledRoom {

	StorageRoom() {
		// TODO Auto-generated constructor stub
	}

	void drawNSEmptyShelve(RealBlocks chunk, int x, int y, int z, int height, int run) {
		for (int y1 = 0; y1 < height; y1++) {
			chunk.setBlock(x, y + y1, z, Blocks.BIRCH_STAIRS, Direction.NORTH, DoubleBlockHalf.UPPER);
			chunk.setBlocks(x, x + 1, y + y1, z + 1, z + run - 1, Blocks.BIRCH_SLAB, SlabType.TOP);
			chunk.setBlock(x, y + y1, z + run - 1, Blocks.BIRCH_STAIRS, Direction.SOUTH, Half.TOP);
		}
	}

	void drawWEEmptyShelve(RealBlocks chunk, int x, int y, int z, int height, int run) {
		for (int y1 = 0; y1 < height; y1++) {
			chunk.setBlock(x, y + y1, z, Blocks.BIRCH_STAIRS, Direction.WEST, DoubleBlockHalf.UPPER);
			chunk.setBlocks(x + 1, x + run - 1, y + y1, z, z + 1, Blocks.BIRCH_SLAB, SlabType.TOP);
			chunk.setBlock(x + run - 1, y + y1, z, Blocks.BIRCH_STAIRS, Direction.EAST, Half.TOP);
		}
	}
}
