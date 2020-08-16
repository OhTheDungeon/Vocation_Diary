package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.BlockFace;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.Trees;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class ClosetRoom extends FilledRoom {

	public ClosetRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		Trees trees = new Trees(odds);
		Block door = trees.getRandomWoodDoor();

		switch (sideWithWall) {
		default:
		case NORTH:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, Direction.SOUTH);
			chunk.setDoor(x + 1, y, z + depth - 1, door, BlockFace.SOUTH_SOUTH_EAST);
			break;
		case SOUTH:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, Direction.NORTH);
			chunk.setDoor(x + 1, y, z, door, BlockFace.NORTH_NORTH_WEST);
			break;
		case WEST:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, Direction.EAST);
			chunk.setDoor(x + width - 1, y, z + 1, door, BlockFace.EAST_NORTH_EAST);
			break;
		case EAST:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, Direction.WEST);
			chunk.setDoor(x, y, z + 1, door, BlockFace.WEST_NORTH_WEST);
			break;
		}
	}

	private void drawShelves(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z, int width,
			int height, int depth, Block materialWall, Direction facing) {

		// walls and room
		chunk.setBlocks(x, x + width, y, y + height, z, z + depth, materialWall);
		chunk.setBlocks(x + 1, y, y + height, z + 1, Blocks.AIR);

		// now the stuff
		if (generator.getWorldSettings().treasuresInBuildings
				&& odds.playOdds(generator.getWorldSettings().oddsOfTreasureInBuildings)) {
			chunk.setChest(generator, x + 1, y, z + 1, facing, odds, generator.lootProvider, LootLocation.BUILDING);
		} else {
			Block shelveMaterial = getShelveMaterial(odds, materialWall);
			drawShelve(chunk, odds, x + 1, y, z + 1, shelveMaterial);
			drawShelve(chunk, odds, x + 1, y + 1, z + 1, shelveMaterial);
		}
	}

	private void drawShelve(RealBlocks chunk, Odds odds, int x, int y, int z, Block shelveMaterial) {
		if (odds.flipCoin())
			chunk.setBlock(x, y, z, shelveMaterial);
		else
			chunk.setBlock(x, y, z, Blocks.BOOKSHELF);
	}

	private Block getShelveMaterial(Odds odds, Block wall) {
		if (wall == Blocks.QUARTZ_BLOCK) {
			return Blocks.STONE_SLAB;
		}
		Trees trees = new Trees(odds);
		return trees.getRandomWoodSlab();
	}

}
