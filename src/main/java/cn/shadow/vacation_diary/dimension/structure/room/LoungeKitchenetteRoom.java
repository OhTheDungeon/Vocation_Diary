package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class LoungeKitchenetteRoom extends LoungeRoom {

	public LoungeKitchenetteRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			placeStuff(generator, chunk, odds, x + 1, y, z, Direction.EAST);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlock(x + 1, y, z + 2, Blocks.PISTON, Direction.UP);
			if (odds.flipCoin())
				chunk.setBlock(x + 1, y + 1, z, Blocks.BREWING_STAND);
			break;
		case SOUTH:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			placeStuff(generator, chunk, odds, x + 1, y, z, Direction.WEST);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlock(x + 1, y, z + 2, Blocks.PISTON, Direction.UP);
			if (odds.flipCoin())
				chunk.setBlock(x + 1, y + 1, z + 2, Blocks.BREWING_STAND);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			placeStuff(generator, chunk, odds, x, y, z + 1, Direction.NORTH);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlock(x + 2, y, z + 1, Blocks.PISTON, Direction.UP);
			if (odds.flipCoin())
				chunk.setBlock(x, y + 1, z + 1, Blocks.BREWING_STAND);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			placeStuff(generator, chunk, odds, x, y, z + 1, Direction.SOUTH);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlock(x + 2, y, z + 1, Blocks.PISTON, Direction.UP);
			if (odds.flipCoin())
				chunk.setBlock(x + 2, y + 1, z + 1, Blocks.BREWING_STAND);
			break;
		}
	}

	private void placeStuff(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z,
			Direction facing) {
		if (generator.getWorldSettings().treasuresInBuildings
				&& odds.playOdds(generator.getWorldSettings().oddsOfTreasureInBuildings))
			chunk.setChest(generator, x, y, z, facing, odds, generator.lootProvider, LootLocation.FOOD);
		else
			chunk.setBlock(x, y, z, Blocks.PISTON, Direction.UP);
	}
}
