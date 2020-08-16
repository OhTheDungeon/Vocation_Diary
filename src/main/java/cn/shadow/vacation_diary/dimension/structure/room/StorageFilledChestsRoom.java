package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;

public class StorageFilledChestsRoom extends StorageRoom {

	public StorageFilledChestsRoom() {
		// TODO Auto-generated constructor stub
	}

	private final Block matPole = Blocks.STONE_SLAB;

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		if (generator.getWorldSettings().treasuresInBuildings) {
			switch (sideWithWall) {
			default:
			case NORTH:
				chunk.setBlocks(x, y, y + height - 1, z, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z, matPole, SlabType.DOUBLE);
				drawChests(generator, chunk, odds, Direction.EAST, x, y, z + 1, height);
				drawChests(generator, chunk, odds, Direction.EAST, x, y, z + 2, height);
				drawChests(generator, chunk, odds, Direction.WEST, x + 2, y, z + 1, height);
				drawChests(generator, chunk, odds, Direction.WEST, x + 2, y, z + 2, height);
				chunk.setBlocks(x, y, y + height - 1, z + 3, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 3, matPole, SlabType.DOUBLE);
				break;
			case SOUTH:
				chunk.setBlocks(x, y, y + height - 1, z + 2, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 2, matPole, SlabType.DOUBLE);
				drawChests(generator, chunk, odds, Direction.EAST, x, y, z, height);
				drawChests(generator, chunk, odds, Direction.EAST, x, y, z + 1, height);
				drawChests(generator, chunk, odds, Direction.WEST, x + 2, y, z, height);
				drawChests(generator, chunk, odds, Direction.WEST, x + 2, y, z + 1, height);
				chunk.setBlocks(x, y, y + height - 1, z - 1, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z - 1, matPole, SlabType.DOUBLE);
				break;
			case WEST:
				chunk.setBlocks(x, y, y + height - 1, z, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x, y, y + height - 1, z + 2, matPole, SlabType.DOUBLE);
				drawChests(generator, chunk, odds, Direction.SOUTH, x + 1, y, z, height);
				drawChests(generator, chunk, odds, Direction.SOUTH, x + 2, y, z, height);
				drawChests(generator, chunk, odds, Direction.NORTH, x + 1, y, z + 2, height);
				drawChests(generator, chunk, odds, Direction.NORTH, x + 2, y, z + 2, height);
				chunk.setBlocks(x + 3, y, y + height - 1, z, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x + 3, y, y + height - 1, z + 2, matPole, SlabType.DOUBLE);
				break;
			case EAST:
				chunk.setBlocks(x + 2, y, y + height - 1, z, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 2, matPole, SlabType.DOUBLE);
				drawChests(generator, chunk, odds, Direction.SOUTH, x, y, z, height);
				drawChests(generator, chunk, odds, Direction.SOUTH, x + 1, y, z, height);
				drawChests(generator, chunk, odds, Direction.NORTH, x, y, z + 2, height);
				drawChests(generator, chunk, odds, Direction.NORTH, x + 1, y, z + 2, height);
				chunk.setBlocks(x - 1, y, y + height - 1, z, matPole, SlabType.DOUBLE);
				chunk.setBlocks(x - 1, y, y + height - 1, z + 2, matPole, SlabType.DOUBLE);
				break;
			}
		}
	}

	private void drawChests(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, Direction direction, int x,
			int y, int z, int height) {
		if (odds.playOdds(generator.getWorldSettings().oddsOfTreasureInBuildings))
			drawChest(generator, chunk, odds, direction, x, y, z);
		if (height > 3) {
			chunk.setBlock(x, y + 1, z, matPole, SlabType.TOP);
			if (odds.playOdds(generator.getWorldSettings().oddsOfTreasureInBuildings))
				drawChest(generator, chunk, odds, direction, x, y + 2, z);
			if (height > 5) {
				chunk.setBlock(x, y + 3, z, matPole, SlabType.TOP);
				if (odds.playOdds(generator.getWorldSettings().oddsOfTreasureInBuildings))
					drawChest(generator, chunk, odds, direction, x, y + 4, z);
			}
		}
	}

	void drawChest(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, Direction direction, int x,
			int y, int z) {
		chunk.setChest(generator, x, y, z, direction, odds, generator.lootProvider, LootLocation.WAREHOUSE);
	}
}
