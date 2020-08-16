package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.IsolatedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.GravelLot;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageSets;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class RoundaboutCenterLot extends IsolatedLot {

	private enum BaseStyle {
		WATER, GRASS, PEDESTAL
	}

	private enum PitStyle {
		WATER, LAVA, UNFINISHED
	}

	private final static Block curbMaterial = Blocks.STONE;
	private final static Block brickMaterial = Blocks.SMOOTH_STONE;
	//	private final static Material fenceMaterial = Material.SPRUCE_FENCE;
	private final static Block baseMaterial = Blocks.QUARTZ_BLOCK;

	private final BaseStyle baseStyle;
	private final PitStyle pitStyle;

	public RoundaboutCenterLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.ROUNDABOUT;
		baseStyle = randomBase();
		pitStyle = randomPitStyle();
	}

	private BaseStyle randomBase() {
		BaseStyle[] values = BaseStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}

	private PitStyle randomPitStyle() {
		PitStyle[] values = PitStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new RoundaboutCenterLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean isPlaceableAt(VocationCityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.getWorldSettings().inRoadRange(chunkX, chunkZ);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel + 1;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

	@Override
	public boolean isValidStrataY(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < generator.streetLevel;
	}

	@Override
	protected boolean isShaftableLevel(VocationCityWorldGenerator generator, int blockY) {
		return blockY < generator.streetLevel - (generator.streetLevel / 2);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

		// where to start?
		int yPitTop = generator.streetLevel - 1;
		int ySurface = generator.streetLevel + 1;

		// clear out underneath
		chunk.setLayer(ySurface - 1, 2, curbMaterial);
		chunk.pepperBlocks(0, 16, yPitTop, 0, 16, chunkOdds, generator.oreProvider.stratumMaterial); // replace some
		// dirt with
		// stone
		chunk.clearBlocks(0, 16, yPitTop - 1, 0, 16, chunkOdds); // remove some dirt
		chunk.pepperBlocks(0, 16, yPitTop - 1, 0, 16, chunkOdds, generator.oreProvider.stratumMaterial); // replace some
		// dirt or
		// air with
		// stone
		chunk.clearBlocks(0, 16, yPitTop - 5, yPitTop - 1, 0, 16); // remove the rest of the stone

		// what to build?
		switch (baseStyle) {
		case WATER:

			// bottom of the fountain...
			chunk.setCircle(8, 8, 5, ySurface, brickMaterial, false);
			for (int x = 0; x < 10; x++)
				for (int z = 0; z < 10; z++)
					chunk.setBlock(x + 3, ySurface, z + 3, brickMaterial);

			// the plain bit... later we will take care of the fancy bit
			ySurface++;
			chunk.setCircle(8, 8, 6, ySurface, brickMaterial, false);

			// fill with water
			if (generator.getWorldSettings().includeAbovegroundFluids)
				chunk.setCircle(8, 8, 5, ySurface, generator.oreProvider.fluidFluidMaterial, true);
			break;
		case GRASS:

			// outer edge
			ySurface++;
			chunk.setCircle(8, 8, 6, ySurface, brickMaterial, false);

			// backfill with grass
			chunk.setCircle(8, 8, 5, ySurface - 1, generator.oreProvider.surfaceMaterial, false);
			chunk.setBlocks(3, 13, ySurface - 1, 4, 12, generator.oreProvider.surfaceMaterial);
			chunk.setBlocks(4, 12, ySurface - 1, 3, 13, generator.oreProvider.surfaceMaterial);
			break;
		case PEDESTAL:

			// pedestal, imagine that!
			ySurface++;
			chunk.setCircle(8, 8, 4, ySurface, brickMaterial, false);
			chunk.setCircle(8, 8, 3, ySurface, brickMaterial, false);
			chunk.setCircle(8, 8, 3, ySurface + 1, brickMaterial, false);
//			chunk.setCircle(8, 8, 3, y1 + 2, fenceMaterial, false);
			chunk.setBlocks(5, 11, ySurface, ySurface + 2, 5, 11, brickMaterial);
			break;
		}
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int ySurface = generator.streetLevel + 1;

		// something got stolen?
		boolean somethingInTheCenter = chunkOdds.playOdds(context.oddsOfArt);

		// where to start?
		if (generator.getWorldSettings().includeSewers) {
			int yPitTop = generator.streetLevel - 1;
			int yPitBottom = 29;
			int yWaterBottom = 8;

			// bricks around the edges... if we do anything at all
			chunk.setWalls(0, 16, yPitTop - 6, yPitTop - 5, 0, 16, Blocks.SMOOTH_STONE);

			// pit style
			switch (pitStyle) {
			case LAVA:
				// bottom of the world
				GravelLot.generateHole(generator, chunkOdds, chunk, yPitTop - 6, 14, yPitBottom, false);

				// fill with lava
				chunk.setBlocks(4, 12, yPitBottom - 2, 4, 12, Blocks.LAVA);
				chunk.clearBlocks(4, 12, yPitBottom - 1, 4, 12);
				chunk.pepperBlocks(4, 12, yPitBottom - 1, 4, 12, chunkOdds, Blocks.LAVA);

				// spawner?
				if (generator.getWorldSettings().spawnersInSewers) {
					chunk.setBlocks(8, yPitBottom - 2, yPitBottom + 4, 8, Blocks.OBSIDIAN);
					generator.spawnProvider.setSpawner(generator, chunk, chunkOdds, 8, yPitBottom + 4, 8,
							generator.spawnProvider.itemsEntities_LavaPit);
				}

				break;

			case UNFINISHED:
				// not quite made yet... are we sure?
				GravelLot.generateHole(generator, chunkOdds, chunk, yPitTop - 6, 14,
						yPitTop - chunkOdds.getRandomInt(8, 16), true);
				break;

			case WATER:

				// nearly the bottom of the world
				GravelLot.generateHole(generator, chunkOdds, chunk, yPitTop - 6, 14, yPitBottom, false);

				// half pipes leading across
				int yPitPipes = yPitTop - 7;
				chunk.setBlocks(6, 10, yPitPipes, 1, 15, Blocks.SMOOTH_STONE);
				chunk.setBlocks(1, 15, yPitPipes, 6, 10, Blocks.SMOOTH_STONE);
				chunk.setBlocks(7, 9, yPitPipes - 1, 1, 15, Blocks.SMOOTH_STONE);
				chunk.setBlocks(1, 15, yPitPipes - 1, 7, 9, Blocks.SMOOTH_STONE);

				// pipe leading down
				chunk.setBlocks(7, 9, yPitPipes - 5, yPitPipes - 1, 6, 7, Blocks.SMOOTH_STONE);
				chunk.setBlocks(7, 9, yPitPipes - 5, yPitPipes - 1, 9, 10, Blocks.SMOOTH_STONE);
				chunk.setBlocks(6, 7, yPitPipes - 5, yPitPipes - 1, 7, 9, Blocks.SMOOTH_STONE);
				chunk.setBlocks(9, 10, yPitPipes - 5, yPitPipes - 1, 7, 9, Blocks.SMOOTH_STONE);

				// round things out a bit on the edges
				chunk.setBlock(6, yPitPipes + 1, 1, Blocks.SMOOTH_STONE);
				chunk.setBlock(9, yPitPipes + 1, 1, Blocks.SMOOTH_STONE);
				chunk.setBlock(6, yPitPipes + 1, 14, Blocks.SMOOTH_STONE);
				chunk.setBlock(9, yPitPipes + 1, 14, Blocks.SMOOTH_STONE);
				chunk.setBlock(1, yPitPipes + 1, 6, Blocks.SMOOTH_STONE);
				chunk.setBlock(1, yPitPipes + 1, 9, Blocks.SMOOTH_STONE);
				chunk.setBlock(14, yPitPipes + 1, 6, Blocks.SMOOTH_STONE);
				chunk.setBlock(14, yPitPipes + 1, 9, Blocks.SMOOTH_STONE);

				// notch the sides a bit
				chunk.clearBlocks(7, 9, yPitPipes + 1, 0, 1);
				chunk.clearBlocks(7, 9, yPitPipes + 1, 15, 16);
				chunk.clearBlocks(0, 1, yPitPipes + 1, 7, 9);
				chunk.clearBlocks(15, 16, yPitPipes + 1, 7, 9);

				// clear out the half pipe
				chunk.clearBlocks(7, 9, yPitPipes, 1, 15);
				chunk.clearBlocks(1, 15, yPitPipes, 7, 9);
				chunk.clearBlocks(7, 9, yPitPipes - 1, 7, 9);

				// fill the pool
				chunk.setBlocks(4, 12, yWaterBottom, yPitBottom, 4, 12, generator.oreProvider.fluidMaterial);
				chunk.pepperBlocks(4, 12, yWaterBottom, 4, 12, chunkOdds, Blocks.PRISMARINE);
				chunk.pepperBlocks(4, 12, yWaterBottom, 4, 12, chunkOdds, Odds.oddsUnlikely, Blocks.SEA_LANTERN);
				chunk.pepperBlocks(4, 12, yWaterBottom + 1, 4, 12, chunkOdds, Blocks.PRISMARINE);

				// spawner?
				if (generator.getWorldSettings().spawnersInSewers) {
					chunk.setBlocks(8, yWaterBottom, yWaterBottom + 4, 8, Blocks.PRISMARINE);
					generator.spawnProvider.setSpawner(generator, chunk, chunkOdds, 8, yWaterBottom + 4, 8,
							generator.spawnProvider.itemsEntities_WaterPit);
				} else {
					generator.spawnProvider.spawnSeaAnimals(generator, chunk, chunkOdds, 7, yPitBottom - 2, 7);
				}

				break;
			}
		}

		// making a fountain?
		switch (baseStyle) {
		case WATER:

			// add some water to the mix
			if (generator.getWorldSettings().includeAbovegroundFluids) {
				Block liquid = Blocks.WATER;
				if (generator.getWorldSettings().includeDecayedNature)
					liquid = Blocks.LAVA;
				int fountianY = ySurface + 1;

				// four little fountains?
				if (chunkOdds.flipCoin()) {
					chunk.setBlocks(5, fountianY, fountianY + chunkOdds.getRandomInt(4), 5, liquid);
					chunk.setBlocks(5, fountianY, fountianY + chunkOdds.getRandomInt(4), 10, liquid);
					chunk.setBlocks(10, fountianY, fountianY + chunkOdds.getRandomInt(4), 5, liquid);
					chunk.setBlocks(10, fountianY, fountianY + chunkOdds.getRandomInt(4), 10, liquid);
				}

				// water can be art too, you know?
				if (somethingInTheCenter && chunkOdds.playOdds(context.oddsOfNaturalArt)) {
					chunk.setBlocks(7, 9, fountianY, fountianY + 4 + chunkOdds.getRandomInt(5), 7, 9, liquid);
					somethingInTheCenter = false;
				}
			}

			break;
		case GRASS:

			// tree can be art too, you know!
			if (somethingInTheCenter && chunkOdds.playOdds(context.oddsOfNaturalArt)) {
				generator.coverProvider.generateCoverage(generator, chunk, 7, ySurface + 1, 7, CoverageSets.TALL_TREES);
				somethingInTheCenter = false;
			}

			// backfill with grass
			for (int x = 2; x < 14; x++) {
				for (int z = 2; z < 14; z++) {
					if (generator.coverProvider.isPlantable(generator, chunk, x, ySurface, z))
						generator.coverProvider.generateCoverage(generator, chunk, x, ySurface + 1, z,
								CoverageSets.PRARIE_PLANTS);
				}
			}
			break;
		case PEDESTAL:

			ySurface = ySurface + 2;
			break;
		}

		// if we have not placed something in the center... place a "ART!" like thingy
		if (somethingInTheCenter && !generator.getWorldSettings().includeDecayedRoads) {

			generateArt(chunk, chunkOdds, 6, ySurface, 6, baseMaterial);
		}
	}

	public static void generateArt(SupportBlocks chunk, Odds odds, int atX, int atY, int atZ, Block base) {

		// simple glass or colored blocks?
		boolean woolArt = odds.playOdds(Odds.oddsUnlikely);
		boolean multiColorArt = odds.playOdds(Odds.oddsLikely);
		Colors colors = new Colors(odds);
		if (!multiColorArt)
			colors.fixColor();

		// base art
		if (odds.flipCoin() || woolArt) {

			// now the "art"
			for (int x = atX; x < atX + 4; x++)
				for (int y = atY + 4; y < atY + 8; y++)
					for (int z = atZ; z < atZ + 4; z++) {

						// place a block
						if (odds.flipCoin())
							chunk.setBlock(x, y, z, colors.getGlassPane());
						else if (woolArt)
							chunk.setBlock(x, y, z, colors.getWool());
						else
							chunk.setBlock(x, y, z, colors.getGlass());
					}

			// now put the base in
			chunk.setBlocks(atX + 1, atX + 3, atY - 1, atY + 5, atZ + 1, atZ + 3, base);

			// else spire art
		} else {
			// now put the base in
			chunk.setBlocks(atX, atX + 4, atY - 1, atY, atZ, atZ + 4, base);

			// now for the art
			for (int x = atX; x < atX + 4; x++)
				for (int z = atZ; z < atZ + 4; z++) {

					// height
					int y2 = atY + odds.getRandomInt(5) + 1;
					int y3 = y2 + odds.getRandomInt(5) + 1;

					// place a block
					for (int y = atY; y < y2; y++)
						chunk.setBlock(x, y, z, colors.getGlass());
					for (int y = y2; y < y3; y++)
						chunk.setBlock(x, y, z, colors.getGlassPane());
				}
		}
	}
}
