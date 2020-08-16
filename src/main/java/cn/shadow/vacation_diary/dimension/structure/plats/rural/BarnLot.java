package cn.shadow.vacation_diary.dimension.structure.plats.rural;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.IsolatedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;

public class BarnLot extends IsolatedLot {

	private final Block wallMat;
	private final Block roofMat;
	private final Block windowsMat;

	public BarnLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.STRUCTURE;

		wallMat = Blocks.ACACIA_PLANKS;
		roofMat = Blocks.BIRCH_PLANKS;
		windowsMat = Blocks.GLASS_PANE;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BarnLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + 15;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

//		generator.reportFormatted("@@@@@@@ chunk %d,%d [0][0] = %4d", chunk.sectionX, chunk.sectionZ, generator.shapeProvider.findBlockY(generator, chunk.getBlockX(0), chunk.getBlockZ(0)));
//		burp(generator, 100, true);
//		
//		if (blockYs.minHeight > generator.streetLevel) {
//			blockYs.report(generator, "#######");
//			blockYs.reportMatrix(generator, "#######");
//		}
//		
//		blockYs.draw(chunk);
//		
//		if (blockYs.minHeight > generator.streetLevel)
//			blockYs.report(generator, "*******");

		// ground please
		chunk.setWalls(0, 16, generator.streetLevel, generator.streetLevel + 1, 0, 16, Blocks.GRASS_BLOCK);
		chunk.setBlocks(1, 15, generator.streetLevel, 1, 15, Blocks.SANDSTONE);
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int y1 = generator.streetLevel + 1;
		int y2 = y1 + 4;
		int y3 = y2 + 4;

		// walls and windows first
		chunk.setWalls(1, 15, y1, y3, 1, 15, wallMat);
		punchWindows(chunk, y1 + 1);
		punchWindows(chunk, y2 + 1);

		// figure out the doors
		boolean firstDoor = chunkOdds.flipCoin();
		boolean secondDoor = chunkOdds.flipCoin();
		if (!firstDoor && !secondDoor)
			firstDoor = true;

		// paddocks
		boolean firstPaddock = chunkOdds.playOdds(Odds.oddsSomewhatUnlikely);
		boolean secondPaddock = chunkOdds.playOdds(Odds.oddsSomewhatUnlikely);

		// NORTH/SOUTH or EAST/WEST
		if (chunkOdds.flipCoin()) {

			// bottom stuff
			if (firstPaddock) {
				boolean includeHorses = chunkOdds.flipCoin();

				// generate fence & gate
				chunk.setBlocks(4, 5, y1, 2, 4, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);
				chunk.setBlock(4, y1, 5, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.EAST);
				chunk.setBlock(5, y1, 5, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.WEST);
				chunk.setBlock(5, y1, 6, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);

				chunk.setGate(5, y1, 7, Blocks.SPRUCE_FENCE_GATE, Direction.EAST, !includeHorses); // only open if no horses

				chunk.setBlock(5, y1, 8, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);
				chunk.setBlock(5, y1, 9, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);
				chunk.setBlock(5, y1, 10, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.WEST);
				chunk.setBlock(4, y1, 10, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.EAST);
				chunk.setBlocks(4, 5, y1, 12, 14, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);

				// hay & water please
				chunk.setBlock(2, y1, 2, Blocks.HAY_BLOCK);
				chunk.setCauldron(2, y1, 13, chunkOdds);

				// spawn horses
				if (includeHorses)
					spawnHorses(generator, chunk, 2, y1, 7);
			} else
				// or just a pile of hay
				hayPile(chunk, 2, 5, y1, 2, 14);
			if (secondPaddock) {
				boolean includeHorses = chunkOdds.flipCoin();

				// generate fence & gate
				chunk.setBlocks(11, 12, y1, 2, 4, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);
				chunk.setBlock(11, y1, 5, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.WEST);
				chunk.setBlock(10, y1, 5, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.EAST);
				chunk.setBlock(10, y1, 6, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);

				chunk.setGate(10, y1, 7, Blocks.SPRUCE_FENCE_GATE, Direction.WEST, !includeHorses); // only open if no horses 

				chunk.setBlock(10, y1, 8, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);
				chunk.setBlock(10, y1, 9, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);
				chunk.setBlock(10, y1, 10, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.EAST);
				chunk.setBlock(11, y1, 10, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.WEST);
				chunk.setBlocks(11, 12, y1, 12, 14, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.SOUTH);

				// hay & water please
				chunk.setBlock(13, y1, 2, Blocks.HAY_BLOCK);
				chunk.setCauldron(13, y1, 13, chunkOdds);

				// spawn horses
				if (includeHorses)
					spawnHorses(generator, chunk, 12, y1, 7);
			} else
				// or just a pile of hay
				hayPile(chunk, 11, 14, y1, 2, 14);

			// top stuff
			hayLoft(chunk, 2, 5, y2, 2, 14);
			hayLoft(chunk, 11, 14, y2, 2, 14);

			// columns
			chunk.setBlocks(4, y1, y2, 4, wallMat);
			chunk.setBlocks(11, y1, y2, 4, wallMat);
			chunk.setBlocks(4, y1, y2, 11, wallMat);
			chunk.setBlocks(11, y1, y2, 11, wallMat);

			// access and back fill
			if (firstDoor) {
				chunk.clearBlocks(5, 11, y1, y2, 1, 2);
				chunk.setLadder(5, y1, y2 + 1, 4, Direction.EAST); // fixed
				chunk.setLadder(10, y1, y2 + 1, 4, Direction.WEST); // fixed
				if (!secondDoor) {
					hayLoft(chunk, 5, 11, y2, 11, 14);
					hayPile(chunk, 5, 11, y1, 11, 14);

					// treasures
					placeChest(generator, chunk, 7, y1, 10, Direction.NORTH);
					placeChest(generator, chunk, 8, y1, 10, Direction.NORTH);
				}
			}
			if (secondDoor) {
				chunk.clearBlocks(5, 11, y1, y2, 14, 15);
				chunk.setLadder(5, y1, y2 + 1, 11, Direction.EAST); // fixed
				chunk.setLadder(10, y1, y2 + 1, 11, Direction.WEST); // fixed
				if (!firstDoor) {
					hayLoft(chunk, 5, 11, y2, 2, 5);
					hayPile(chunk, 5, 11, y1, 2, 5);

					// treasures
					placeChest(generator, chunk, 7, y1, 5, Direction.SOUTH);
					placeChest(generator, chunk, 8, y1, 5, Direction.SOUTH);
				}
			}

			// roof
			chunk.setBlocks(0, 1, y3 - 1, 0, 16, roofMat);
			chunk.setBlocks(1, 2, y3, 0, 16, roofMat);
			chunk.setBlocks(2, 3, y3 + 1, 0, 16, roofMat);
			chunk.setBlocks(3, 5, y3 + 2, 0, 16, roofMat);
			chunk.setBlocks(5, 7, y3 + 3, 0, 16, roofMat);
			chunk.setBlocks(7, 9, y3 + 4, 0, 16, roofMat);
			chunk.setBlocks(9, 11, y3 + 3, 0, 16, roofMat);
			chunk.setBlocks(11, 13, y3 + 2, 0, 16, roofMat);
			chunk.setBlocks(13, 14, y3 + 1, 0, 16, roofMat);
			chunk.setBlocks(14, 15, y3, 0, 16, roofMat);
			chunk.setBlocks(15, 16, y3 - 1, 0, 16, roofMat);

			// end cap
			chunk.setBlocks(2, 14, y3, 1, 2, wallMat);
			chunk.setBlocks(3, 13, y3 + 1, 1, 2, wallMat);
			chunk.setBlocks(5, 11, y3 + 2, 1, 2, wallMat);
			chunk.setBlocks(2, 14, y3, 14, 15, wallMat);
			chunk.setBlocks(3, 13, y3 + 1, 14, 15, wallMat);
			chunk.setBlocks(5, 11, y3 + 2, 14, 15, wallMat);

			// a few more windows
			punchWindows(chunk, 7, y3 + 2, 1, Direction.EAST, Direction.WEST);
			punchWindows(chunk, 8, y3 + 2, 1, Direction.EAST, Direction.WEST);
			punchWindows(chunk, 7, y3 + 2, 14, Direction.EAST, Direction.WEST);
			punchWindows(chunk, 8, y3 + 2, 14, Direction.EAST, Direction.WEST);
		} else {
			// bottom stuff
			if (firstPaddock) {

				// generate fence & gate
				chunk.setBlocks(2, 4, y1, 4, 5, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);
				chunk.setBlock(5, y1, 4, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.WEST);
				chunk.setBlock(5, y1, 5, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.EAST);
				chunk.setBlock(6, y1, 5, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);

				chunk.setGate(7, y1, 5, Blocks.SPRUCE_FENCE_GATE, Direction.SOUTH, true); // open south

				chunk.setBlock(8, y1, 5, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);
				chunk.setBlock(9, y1, 5, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);
				chunk.setBlock(10, y1, 5, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.WEST);
				chunk.setBlock(10, y1, 4, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.EAST);
				chunk.setBlocks(12, 14, y1, 4, 5, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);

				// hay & water please
				chunk.setBlock(2, y1, 2, Blocks.HAY_BLOCK);
				chunk.setCauldron(13, y1, 2, chunkOdds);

				// spawn horses
				spawnHorses(generator, chunk, 7, y1, 2);
			} else
				// or just a pile of hay
				hayPile(chunk, 2, 14, y1, 2, 5);
			if (secondPaddock) {

				// generate fence & gate
				chunk.setBlocks(2, 4, y1, 11, 12, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);
				chunk.setBlock(5, y1, 11, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.WEST);
				chunk.setBlock(5, y1, 10, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.EAST);
				chunk.setBlock(6, y1, 10, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);

				chunk.setGate(7, y1, 10, Blocks.SPRUCE_FENCE_GATE, Direction.NORTH, true); // open north

				chunk.setBlock(8, y1, 10, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);
				chunk.setBlock(9, y1, 10, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);
				chunk.setBlock(10, y1, 10, Blocks.SPRUCE_FENCE, Direction.SOUTH, Direction.WEST);
				chunk.setBlock(10, y1, 11, Blocks.SPRUCE_FENCE, Direction.NORTH, Direction.EAST);
				chunk.setBlocks(12, 14, y1, 11, 12, Blocks.SPRUCE_FENCE, Direction.EAST, Direction.WEST);

				// hay & water please
				chunk.setBlock(2, y1, 13, Blocks.HAY_BLOCK);
				chunk.setCauldron(13, y1, 13, chunkOdds);

				// spawn horses
				spawnHorses(generator, chunk, 7, y1, 13);
			} else
				// or just a pile of hay
				hayPile(chunk, 2, 14, y1, 11, 14);

			// top stuff
			hayLoft(chunk, 2, 14, y2, 2, 5);
			hayLoft(chunk, 2, 14, y2, 11, 14);

			// columns
			chunk.setBlocks(4, y1, y2, 4, wallMat);
			chunk.setBlocks(11, y1, y2, 4, wallMat);
			chunk.setBlocks(4, y1, y2, 11, wallMat);
			chunk.setBlocks(11, y1, y2, 11, wallMat);

			// access and back fill
			if (firstDoor) {
				chunk.clearBlocks(1, 2, y1, y2, 5, 11);
				chunk.setLadder(4, y1, y2 + 1, 5, Direction.SOUTH); // fixed
				chunk.setLadder(4, y1, y2 + 1, 10, Direction.NORTH); // fixed
				if (!secondDoor) {
					hayLoft(chunk, 11, 14, y2, 5, 11);
					hayPile(chunk, 11, 14, y1, 5, 11);

					// treasures
					placeChest(generator, chunk, 10, y1, 7, Direction.WEST);
					placeChest(generator, chunk, 10, y1, 8, Direction.WEST);
				}
			}
			if (secondDoor) {
				chunk.clearBlocks(14, 15, y1, y2, 5, 11);
				chunk.setLadder(11, y1, y2 + 1, 5, Direction.SOUTH); // fixed
				chunk.setLadder(11, y1, y2 + 1, 10, Direction.NORTH); // fixed
				if (!firstDoor) {
					hayLoft(chunk, 2, 5, y2, 5, 11);
					hayPile(chunk, 2, 5, y1, 5, 11);

					// treasures
					placeChest(generator, chunk, 5, y1, 7, Direction.EAST);
					placeChest(generator, chunk, 5, y1, 8, Direction.EAST);
				}
			}

			// roof
			chunk.setBlocks(0, 16, y3 - 1, 0, 1, roofMat);
			chunk.setBlocks(0, 16, y3, 1, 2, roofMat);
			chunk.setBlocks(0, 16, y3 + 1, 2, 3, roofMat);
			chunk.setBlocks(0, 16, y3 + 2, 3, 5, roofMat);
			chunk.setBlocks(0, 16, y3 + 3, 5, 7, roofMat);
			chunk.setBlocks(0, 16, y3 + 4, 7, 9, roofMat);
			chunk.setBlocks(0, 16, y3 + 3, 9, 11, roofMat);
			chunk.setBlocks(0, 16, y3 + 2, 11, 13, roofMat);
			chunk.setBlocks(0, 16, y3 + 1, 13, 14, roofMat);
			chunk.setBlocks(0, 16, y3, 14, 15, roofMat);
			chunk.setBlocks(0, 16, y3 - 1, 15, 16, roofMat);

			// endcaps
			chunk.setBlocks(1, 2, y3, 2, 14, wallMat);
			chunk.setBlocks(1, 2, y3 + 1, 3, 13, wallMat);
			chunk.setBlocks(1, 2, y3 + 2, 5, 11, wallMat);
			chunk.setBlocks(14, 15, y3, 2, 14, wallMat);
			chunk.setBlocks(14, 15, y3 + 1, 3, 13, wallMat);
			chunk.setBlocks(14, 15, y3 + 2, 5, 11, wallMat);

			// a few more windows
			punchWindows(chunk, 1, y3 + 2, 7, Direction.SOUTH, Direction.NORTH);
			punchWindows(chunk, 1, y3 + 2, 8, Direction.SOUTH, Direction.NORTH);
			punchWindows(chunk, 14, y3 + 2, 7, Direction.SOUTH, Direction.NORTH);
			punchWindows(chunk, 14, y3 + 2, 8, Direction.SOUTH, Direction.NORTH);
		}

		// not a happy place?
		if (generator.getWorldSettings().includeDecayedBuildings)
			destroyBuilding(generator, y1, 3);
	}

	// TODO 骡子？但是不育
	private void spawnHorses(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		EntityType<?> animal = EntityType.HORSE;
		if (chunkOdds.playOdds(Odds.oddsUnlikely))
			animal = EntityType.DONKEY;
		generator.spawnProvider.spawnAnimals(generator, chunk, chunkOdds, x, y, z, animal);
	}

	private void punchWindows(RealBlocks chunk, int y) {
		punchWindows(chunk, 3, y, 1, Direction.EAST, Direction.WEST);
		punchWindows(chunk, 1, y, 3, Direction.SOUTH, Direction.NORTH);
		punchWindows(chunk, 12, y, 1, Direction.EAST, Direction.WEST);
		punchWindows(chunk, 1, y, 12, Direction.SOUTH, Direction.NORTH);
		punchWindows(chunk, 14, y, 3, Direction.SOUTH, Direction.NORTH);
		punchWindows(chunk, 3, y, 14, Direction.EAST, Direction.WEST);
		punchWindows(chunk, 12, y, 14, Direction.EAST, Direction.WEST);
		punchWindows(chunk, 14, y, 12, Direction.SOUTH, Direction.NORTH);
	}

	private void punchWindows(RealBlocks chunk, int x, int y, int z, Direction... facing) {
		chunk.setBlocks(x, y, y + 2, z, windowsMat, facing);
	}

	private void hayLoft(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		chunk.setBlocks(x1, x2, y, y + 1, z1, z2, wallMat);
		hayPile(chunk, x1, x2, y + 1, z1, z2);
	}

	private void hayPile(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		if (chunkOdds.flipCoin())
			for (int x = x1; x < x2; x++)
				for (int z = z1; z < z2; z++)
					chunk.setBlocks(x, y, y + chunkOdds.getRandomInt(3), z, Blocks.HAY_BLOCK);
	}

	private void placeChest(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z, Direction towards) {
		if (chunkOdds.flipCoin()) {
			chunk.setChest(generator, x, y, z, towards, chunkOdds, generator.lootProvider,
					LootLocation.FARMWORKS_OUTPUT);
		} else if (chunkOdds.flipCoin()) {
			chunk.setChest(generator, x, y, z, towards, chunkOdds, generator.lootProvider, LootLocation.FARMWORKS);
		}
	}
}
