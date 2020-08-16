package cn.shadow.vacation_diary.dimension.structure.plats.rural;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConnectedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageSets;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageType;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.dimension.support.SurroundingLots;
import cn.shadow.vacation_diary.dimension.support.WorldEnvironment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class FarmLot extends ConnectedLot {

	// TODO Apple farm?
	// TODO Cocoa farm?
	// TODO PPPwPPPPPPwPPP based
	// wheat/flower/grass/mushroom/netherwart/dead/none/fallow
	// TODO SPSwSPSSPSwSPS based pumpkin/melon/dead/none/fallow
	// TODO wPPwPPwwPPwPPw based cane/dead/none/fallow

	public enum CropType {
		FALLOW, TRELLIS, VINES, GRASS, FERN, /* DEAD_GRASS, */ CACTUS, REED, DANDELION, DEAD_BUSH, POPPY, BLUE_ORCHID,
		ALLIUM, AZURE_BLUET, OXEYE_DAISY, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, SUNFLOWER, LILAC,
		TALL_GRASS, TALL_FERN, ROSE_BUSH, PEONY, EMERALD_GREEN, OAK_SAPLING, BIRCH_SAPLING, JUNGLE_SAPLING,
		ACACIA_SAPLING, DARK_OAK_SAPLING, OAK_TREE, PINE_TREE, BIRCH_TREE, JUNGLE_TREE, SWAMP_TREE, ACACIA_TREE, WHEAT,
		CARROT, POTATO, MELON, PUMPKIN, BEETROOT, BROWN_MUSHROOM, RED_MUSHROOM, NETHERWART, SHORT_FLOWERS, TALL_FLOWERS,
		ALL_FLOWERS, SHORT_PLANTS, TALL_PLANTS, ALL_PLANTS, PRARIE_PLANTS, EDIBLE_PLANTS, NETHER_PLANTS, DECAY_PLANTS,
		PADDOCK, HOTAIR_BALLOON
	}

	private CropType cropType;

	private boolean directionNorthSouth;
	private double oddsOfCrop = Odds.oddsExtremelyLikely;

	public FarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.STRUCTURE;
		cropType = CropType.FALLOW;
		directionNorthSouth = chunkOdds.flipCoin();

		// crop type please
		if (platmap.generator.worldType == WorldEnvironment.NETHER)
			if (platmap.generator.getWorldSettings().includeDecayedNature)
				cropType = setDecayedNetherCrop();
			else
				cropType = setNetherCrop();
		else if (platmap.generator.getWorldSettings().includeDecayedNature)
			cropType = setDecayedNormalCrop();
		else
			cropType = setNormalCrop();

		if (!platmap.generator.getWorldSettings().includeAirborneStructures && cropType == CropType.HOTAIR_BALLOON)
			cropType = CropType.PADDOCK;
		if (cropType == CropType.HOTAIR_BALLOON && chunkOdds.playOdds(Odds.oddsPrettyLikely))
			cropType = CropType.PADDOCK;

		// decayed world?
		if (platmap.generator.getWorldSettings().includeDecayedNature)
			oddsOfCrop = Odds.oddsSomewhatUnlikely;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FarmLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof FarmLot) {
			FarmLot relativeFarm = (FarmLot) relative;

			directionNorthSouth = relativeFarm.directionNorthSouth;
			cropType = relativeFarm.cropType;
		}
		return result;
	}

	protected final static Block cropNone = Blocks.DIRT;
	protected Block waterMaterial = Blocks.WATER;

	private final static Block poleMaterial = Blocks.SPRUCE_FENCE;
	private final static Block trellisMaterial = Blocks.SPRUCE_PLANKS;

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
//		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
		return generator.streetLevel;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

		// look around
		SurroundingLots farms = new SurroundingLots(platmap, platX, platZ);

		// what type of ground do we have
		chunk.setLayer(generator.streetLevel - 1, 2, generator.oreProvider.surfaceMaterial);

		// in-between bits bits
		Block dividerMaterial = Blocks.GRASS_PATH;
		if (generator.worldType == WorldEnvironment.NETHER) {
			dividerMaterial = Blocks.SOUL_SAND;
		}

		// draw the isolation blocks
//		if (!farms.toNorth()) {
//			chunk.setBlocks(1, 15, generator.streetLevel, 0, 1, dividerMaterial);
//			if (farms.toWest())
//				chunk.setBlock(0, generator.streetLevel, 0, dividerMaterial);
//			if (farms.toEast())
//				chunk.setBlock(15, generator.streetLevel, 0, dividerMaterial);
//		}
//		if (!farms.toSouth()) {
//			chunk.setBlocks(1, 15, generator.streetLevel, 15, 16, dividerMaterial);
//			if (farms.toWest())
//				chunk.setBlock(0, generator.streetLevel, 15, dividerMaterial);
//			if (farms.toEast())
//				chunk.setBlock(15, generator.streetLevel, 15, dividerMaterial);
//		}
//		if (!farms.toWest()) {
//			chunk.setBlocks(0, 1, generator.streetLevel, 1, 15, dividerMaterial);
//			if (farms.toNorth())
//				chunk.setBlock(0, generator.streetLevel, 0, dividerMaterial);
//			if (farms.toSouth())
//				chunk.setBlock(0, generator.streetLevel, 15, dividerMaterial);
//		}
//		if (!farms.toEast()) {
//			chunk.setBlocks(15, 16, generator.streetLevel, 1, 15, dividerMaterial);
//			if (farms.toNorth())
//				chunk.setBlock(15, generator.streetLevel, 0, dividerMaterial);
//			if (farms.toSouth())
//				chunk.setBlock(15, generator.streetLevel, 15, dividerMaterial);
//		}

		if (!farms.toNorth())
			chunk.setBlocks(1, 15, generator.streetLevel, 0, 1, dividerMaterial);
		if (!farms.toSouth())
			chunk.setBlocks(1, 15, generator.streetLevel, 15, 16, dividerMaterial);
		if (!farms.toWest())
			chunk.setBlocks(0, 1, generator.streetLevel, 1, 15, dividerMaterial);
		if (!farms.toEast())
			chunk.setBlocks(15, 16, generator.streetLevel, 1, 15, dividerMaterial);
		if (!farms.toNorthWest())
			chunk.setBlock(0, generator.streetLevel, 0, dividerMaterial);
		if (!farms.toNorthEast())
			chunk.setBlock(15, generator.streetLevel, 0, dividerMaterial);
		if (!farms.toSouthWest())
			chunk.setBlock(0, generator.streetLevel, 15, dividerMaterial);
		if (!farms.toSouthEast())
			chunk.setBlock(15, generator.streetLevel, 15, dividerMaterial);
	}

	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int cropY = generator.streetLevel + 1;

		Block fallowMaterial = generator.shapeProvider.findAtmosphereMaterialAt(generator, cropY - 1);
		boolean fallowField = fallowMaterial != Blocks.AIR;

		if (!fallowField)
			switch (cropType) {
			case PADDOCK:
				chunk.setWalls(1, 15, cropY, cropY + 1, 1, 15, Blocks.SPRUCE_FENCE);

				if (chunkOdds.flipCoin())
					chunk.setGate(7, cropY, 1, Blocks.SPRUCE_FENCE_GATE, Direction.NORTH, chunkOdds.playOdds(Odds.oddsUnlikely));
				if (chunkOdds.flipCoin())
					chunk.setGate(7, cropY, 14, Blocks.SPRUCE_FENCE_GATE, Direction.SOUTH, chunkOdds.playOdds(Odds.oddsUnlikely));
				if (chunkOdds.flipCoin())
					chunk.setGate(1, cropY, 7, Blocks.SPRUCE_FENCE_GATE, Direction.WEST, chunkOdds.playOdds(Odds.oddsUnlikely));
				if (chunkOdds.flipCoin())
					chunk.setGate(14, cropY, 7, Blocks.SPRUCE_FENCE_GATE, Direction.EAST, chunkOdds.playOdds(Odds.oddsUnlikely));
				break;
			case TRELLIS:
			case VINES:
				buildVineyard(chunk, cropY);
				break;
			case GRASS:
			case FERN:
//			case DEAD_GRASS:
			case DANDELION:
			case POPPY:
			case BLUE_ORCHID:
			case ALLIUM:
			case AZURE_BLUET:
			case OXEYE_DAISY:
			case RED_TULIP:
			case ORANGE_TULIP:
			case WHITE_TULIP:
			case PINK_TULIP:
			case SUNFLOWER:
			case LILAC:
			case TALL_GRASS:
			case TALL_FERN:
			case ROSE_BUSH:
			case PEONY:
			case EMERALD_GREEN:
			case SHORT_FLOWERS:
			case TALL_FLOWERS:
			case ALL_FLOWERS:
			case SHORT_PLANTS:
			case TALL_PLANTS:
			case ALL_PLANTS:
			case DECAY_PLANTS:
				if (generator.getWorldSettings().includeAbovegroundFluids)
					plowField(chunk, cropY, Blocks.COARSE_DIRT, waterMaterial, 2);
				else
					fallowField = true;
				break;
			case PRARIE_PLANTS:
			case OAK_SAPLING:
			case BIRCH_SAPLING:
			case ACACIA_SAPLING:
			case JUNGLE_SAPLING:
			case DARK_OAK_SAPLING:
			case OAK_TREE:
			case PINE_TREE:
			case BIRCH_TREE:
			case JUNGLE_TREE:
			case ACACIA_TREE:
			case SWAMP_TREE:
				// leave the grass alone
				break;
			case CACTUS:
				plowField(chunk, cropY, Blocks.SAND, Blocks.SAND, 2);
				break;
			case REED:
				if (generator.getWorldSettings().includeAbovegroundFluids)
					plowField(chunk, cropY, Blocks.SAND, waterMaterial, 2);
				else
					fallowField = true;
				break;
			case DEAD_BUSH:
				plowField(chunk, cropY, Blocks.COARSE_DIRT, fallowMaterial, 2);
				break;
			case WHEAT:
			case CARROT:
			case POTATO:
			case BEETROOT:
				if (generator.getWorldSettings().includeAbovegroundFluids)
					plowField(chunk, cropY, Blocks.FARMLAND, waterMaterial, 2);
				else
					fallowField = true;
				break;
			case MELON:
			case PUMPKIN:
			case EDIBLE_PLANTS:
				if (generator.getWorldSettings().includeAbovegroundFluids)
					plowField(chunk, cropY, Blocks.FARMLAND, waterMaterial, 3);
				else
					fallowField = true;
				break;
			case BROWN_MUSHROOM:
			case RED_MUSHROOM:
				plowField(chunk, cropY, Blocks.MYCELIUM, fallowMaterial, 2);
				break;
			case NETHERWART:
				plowField(chunk, cropY, Blocks.SOUL_SAND, fallowMaterial, 2);
				break;
			case NETHER_PLANTS:
				plowField(chunk, cropY, Blocks.SOUL_SAND, fallowMaterial, 2);
				break;
			case FALLOW:
				fallowField = true;
				break;
			case HOTAIR_BALLOON:
				break;
			}

		if (fallowField)
			plowField(chunk, cropY, Blocks.COARSE_DIRT, fallowMaterial, 2);
		else {
			switch (cropType) {
			case FALLOW:
			case TRELLIS:
				break;
			case PADDOCK:
				generateSurface(generator, chunk, false);
				generator.spawnProvider.spawnAnimals(generator, chunk, chunkOdds, 7, cropY, 7);
				break;
			case VINES:
				plantVineyard(chunk, cropY, Blocks.VINE);
				break;
			case GRASS:
				plantField(generator, chunk, cropY, CoverageType.GRASS, 1, 2);
				break;
			case FERN:
				plantField(generator, chunk, cropY, CoverageType.FERN, 1, 2);
				break;
//			case DEAD_GRASS:
//				plantField(generator, chunk, croplevel, CoverageType.DEAD_GRASS, 1, 2);
//				break;
			case CACTUS:
				plantField(generator, chunk, cropY, CoverageType.CACTUS, 2, 2);
				break;
			case REED:
				plantField(generator, chunk, cropY, CoverageType.REED, 1, 2);
				break;
			case DANDELION:
				plantField(generator, chunk, cropY, CoverageType.DANDELION, 1, 2);
				break;
			case DEAD_BUSH:
				plantField(generator, chunk, cropY, CoverageType.DEAD_BUSH, 1, 2);
				break;
			case POPPY:
				plantField(generator, chunk, cropY, CoverageType.POPPY, 1, 2);
				break;
			case BLUE_ORCHID:
				plantField(generator, chunk, cropY, CoverageType.BLUE_ORCHID, 1, 2);
				break;
			case ALLIUM:
				plantField(generator, chunk, cropY, CoverageType.ALLIUM, 1, 2);
				break;
			case AZURE_BLUET:
				plantField(generator, chunk, cropY, CoverageType.AZURE_BLUET, 1, 2);
				break;
			case OXEYE_DAISY:
				plantField(generator, chunk, cropY, CoverageType.OXEYE_DAISY, 1, 2);
				break;
			case RED_TULIP:
				plantField(generator, chunk, cropY, CoverageType.RED_TULIP, 1, 2);
				break;
			case ORANGE_TULIP:
				plantField(generator, chunk, cropY, CoverageType.ORANGE_TULIP, 1, 2);
				break;
			case WHITE_TULIP:
				plantField(generator, chunk, cropY, CoverageType.WHITE_TULIP, 1, 2);
				break;
			case PINK_TULIP:
				plantField(generator, chunk, cropY, CoverageType.PINK_TULIP, 1, 2);
				break;
			case SUNFLOWER:
				plantField(generator, chunk, cropY, CoverageType.SUNFLOWER, 1, 2);
				break;
			case LILAC:
				plantField(generator, chunk, cropY, CoverageType.LILAC, 1, 2);
				break;
			case TALL_GRASS:
				plantField(generator, chunk, cropY, CoverageType.TALL_FERN, 1, 2);
				break;
			case TALL_FERN:
				plantField(generator, chunk, cropY, CoverageType.TALL_FERN, 1, 2);
				break;
			case ROSE_BUSH:
				plantField(generator, chunk, cropY, CoverageType.ROSE_BUSH, 1, 2);
				break;
			case PEONY:
				plantField(generator, chunk, cropY, CoverageType.PEONY, 1, 2);
				break;
			case EMERALD_GREEN:
				plantField(generator, chunk, cropY, CoverageType.EMERALD_GREEN, 2, 2);
				break;
			case OAK_SAPLING:
				plantSaplings(generator, chunk, cropY, CoverageType.OAK_SAPLING);
				generateSurface(generator, chunk, false);
				break;
			case BIRCH_SAPLING:
				plantSaplings(generator, chunk, cropY, CoverageType.BIRCH_SAPLING);
				generateSurface(generator, chunk, false);
				break;
			case JUNGLE_SAPLING:
				plantSaplings(generator, chunk, cropY, CoverageType.JUNGLE_SAPLING);
				break;
			case ACACIA_SAPLING:
				plantSaplings(generator, chunk, cropY, CoverageType.ACACIA_SAPLING);
				break;
			case DARK_OAK_SAPLING:
				plantSaplings(generator, chunk, cropY, CoverageType.DARK_OAK_SAPLING);
				break;
			case OAK_TREE:
				plantTrees(generator, chunk, cropY, CoverageSets.OAK_TREES);
				generateSurface(generator, chunk, false);
				break;
			case PINE_TREE:
				plantTrees(generator, chunk, cropY, CoverageSets.PINE_TREES);
				break;
			case BIRCH_TREE:
				plantTrees(generator, chunk, cropY, CoverageSets.BIRCH_TREES);
				generateSurface(generator, chunk, false);
				break;
			case JUNGLE_TREE:
				plantTrees(generator, chunk, cropY, CoverageSets.JUNGLE_TREES);
				break;
			case ACACIA_TREE:
				plantTrees(generator, chunk, cropY, CoverageSets.ACACIA_TREES);
				break;
			case SWAMP_TREE:
				plantTrees(generator, chunk, cropY, CoverageSets.SWAMP_TREES);
				break;
			case WHEAT:
				plantField(generator, chunk, cropY, CoverageType.WHEAT, 1, 2);
				break;
			case CARROT:
				plantField(generator, chunk, cropY, CoverageType.CARROTS, 1, 2);
				break;
			case POTATO:
				plantField(generator, chunk, cropY, CoverageType.POTATO, 1, 2);
				break;
			case BEETROOT:
				plantField(generator, chunk, cropY, CoverageType.BEETROOT, 1, 2);
				break;
			case MELON:
				plantField(generator, chunk, cropY, CoverageType.MELON, 1, 3);
				break;
			case PUMPKIN:
				plantField(generator, chunk, cropY, CoverageType.PUMPKIN, 1, 3);
				break;
			case BROWN_MUSHROOM:
				plantField(generator, chunk, cropY, CoverageType.BROWN_MUSHROOM, 1, 2);
				break;
			case RED_MUSHROOM:
				plantField(generator, chunk, cropY, CoverageType.RED_MUSHROOM, 1, 2);
				break;
			case NETHERWART:
				plantField(generator, chunk, cropY, CoverageType.NETHERWART, 1, 2);
				break;
			case SHORT_FLOWERS:
				plantField(generator, chunk, cropY, CoverageSets.SHORT_FLOWERS, 1, 2);
				break;
			case TALL_FLOWERS:
				plantField(generator, chunk, cropY, CoverageSets.TALL_FLOWERS, 1, 2);
				break;
			case ALL_FLOWERS:
				plantField(generator, chunk, cropY, CoverageSets.ALL_FLOWERS, 1, 2);
				break;
			case SHORT_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.SHORT_PLANTS, 1, 2);
				break;
			case TALL_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.TALL_PLANTS, 2, 2);
				break;
			case ALL_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.ALL_PLANTS, 2, 2);
				break;
			case PRARIE_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.PRARIE_PLANTS, 1, 3);
				break;
			case EDIBLE_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.EDIBLE_PLANTS, 1, 3);
				break;
			case NETHER_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.NETHER_PLANTS, 1, 2);
				break;
			case DECAY_PLANTS:
				plantField(generator, chunk, cropY, CoverageSets.DECAY_PLANTS, 1, 2);
				break;
			case HOTAIR_BALLOON:
				if (!generator.getWorldSettings().includeDecayedNature) {

					// place stuff
					generateSurface(generator, chunk, false);

					// hot air balloon
					generator.reportLocation("Hot Air Balloon, Landed", chunk);
					generator.structureInAirProvider.generateHotairBalloon(generator, chunk, context, cropY + 1,
							chunkOdds);

					// tie a string on it
					chunk.setBlocks(5, cropY, cropY + 2, 5, Blocks.IRON_BARS);
					chunk.setBlocks(10, cropY, cropY + 2, 10, Blocks.IRON_BARS);
				}
				break;
			}
		}

		if (generator.getWorldSettings().includeDecayedNature)
			destroyLot(generator, cropY - 3, cropY + 3);
	}

	private void plowField(SupportBlocks chunk, int croplevel, Block matRidge, Block matFurrow, int stepCol) {

		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x++) {
				if (x % stepCol == 0)
					chunk.setBlocks(x, x + 1, croplevel - 1, croplevel, 1, 15, matFurrow);
				else {
					chunk.setBlocks(x, x + 1, croplevel - 1, croplevel, 1, 15, matRidge);
				}
			}
		} else {
			for (int z = 1; z < 15; z++) {
				if (z % stepCol == 0)
					chunk.setBlocks(1, 15, croplevel - 1, croplevel, z, z + 1, matFurrow);
				else {
					chunk.setBlocks(1, 15, croplevel - 1, croplevel, z, z + 1, matRidge);
				}
			}
		}
	}

	private void plantField(VocationCityWorldGenerator generator, RealBlocks chunk, int croplevel, CoverageType coverageType,
			int stepRow, int stepCol) {

		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				for (int z = 1; z < 15; z += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop))
						generator.coverProvider.generateCoverage(generator, chunk, x, croplevel, z, coverageType);
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				for (int x = 1; x < 15; x += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop))
						generator.coverProvider.generateCoverage(generator, chunk, x, croplevel, z, coverageType);
			}
		}
	}

	private void plantField(VocationCityWorldGenerator generator, RealBlocks chunk, int croplevel, CoverageSets coverageSet,
			int stepRow, int stepCol) {

		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				for (int z = 1; z < 15; z += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop)) {
//						if (!chunk.isEmpty(x, croplevel, z))
//							chunk.setBlock(x, croplevel + 5, z, Material.DIAMOND_BLOCK);
						generator.coverProvider.generateCoverage(generator, chunk, x, croplevel, z, coverageSet);
					}
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				for (int x = 1; x < 15; x += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop)) {
//						if (!chunk.isEmpty(x, croplevel, z))
//							chunk.setBlock(x, croplevel + 5, z, Material.DIAMOND_BLOCK);
						generator.coverProvider.generateCoverage(generator, chunk, x, croplevel, z, coverageSet);
					}
			}
		}
	}

	private void plantSaplings(VocationCityWorldGenerator generator, RealBlocks chunk, int croplevel,
			CoverageType coverageType) {
		plantSaplingsRow(generator, chunk, 3, croplevel, 2, coverageType);
		plantSaplingsRow(generator, chunk, 6, croplevel, 4, coverageType);
		plantSaplingsRow(generator, chunk, 9, croplevel, 2, coverageType);
		plantSaplingsRow(generator, chunk, 12, croplevel, 4, coverageType);
	}

	private void plantSaplingsRow(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType) {
		for (int i = 0; i < 4; i++)
			generator.coverProvider.generateCoverage(generator, chunk, x, y, z + i * 3, coverageType);
	}

	private void plantTrees(VocationCityWorldGenerator generator, RealBlocks chunk, int croplevel,
			CoverageSets coverageSet) {
		plantTreesRow(generator, chunk, 2, croplevel, 2, coverageSet);
		plantTreesRow(generator, chunk, 7, croplevel, 3, coverageSet);
		plantTreesRow(generator, chunk, 12, croplevel, 2, coverageSet);
	}

	private void plantTreesRow(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageSets coverageSet) {
		for (int i = 0; i < 3; i++)
			generator.coverProvider.generateCoverage(generator, chunk, x, y, z + i * 5, coverageSet);
	}

	private static final int stepVineRowDelta = 2;

	private void buildVineyard(SupportBlocks chunk, int cropLevel) {
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepVineRowDelta) {
				chunk.setBlocks(x, cropLevel, cropLevel + 3, 1, poleMaterial);
				chunk.setBlock(x, cropLevel + 3, 1, poleMaterial, Direction.SOUTH);
				chunk.setBlocks(x, cropLevel, cropLevel + 3, 14, poleMaterial);
				chunk.setBlock(x, cropLevel + 3, 14, poleMaterial, Direction.NORTH);
				chunk.setBlocks(x, x + 1, cropLevel + 3, cropLevel + 4, 2, 14, trellisMaterial);
//				chunk.setBlocks(x, cropLevel, cropLevel + 3, 1, poleMaterial);
//				chunk.setBlock(x, cropLevel + 3, 1, trellisMaterial);
//				chunk.setBlocks(x, cropLevel, cropLevel + 3, 14, poleMaterial);
//				chunk.setBlock(x, cropLevel + 3, 14, trellisMaterial);
//				chunk.setSlabs(x, x + 1, cropLevel + 3, cropLevel + 4, 2, 14, trellisSpecies, true);
			}
		} else {
			for (int z = 1; z < 15; z += stepVineRowDelta) {
				chunk.setBlocks(1, cropLevel, cropLevel + 3, z, poleMaterial);
				chunk.setBlock(1, cropLevel + 3, z, poleMaterial, Direction.EAST);
				chunk.setBlocks(14, cropLevel, cropLevel + 3, z, poleMaterial);
				chunk.setBlock(14, cropLevel + 3, z, poleMaterial, Direction.WEST);
				chunk.setBlocks(2, 14, cropLevel + 3, cropLevel + 4, z, z + 1, trellisMaterial);
//				chunk.setBlocks(1, cropLevel, cropLevel + 3, z, poleMaterial);
//				chunk.setBlock(1, cropLevel + 3, z, trellisMaterial);
//				chunk.setBlocks(14, cropLevel, cropLevel + 3, z, poleMaterial);
//				chunk.setBlock(14, cropLevel + 3, z, trellisMaterial);
//				chunk.setSlabs(2, 14, cropLevel + 3, cropLevel + 4, z, z + 1, trellisSpecies, true);
			}
		}
	}

	private void plantVineyard(SupportBlocks chunk, int cropLevel, Block matCrop) {
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepVineRowDelta) {
				if (chunkOdds.playOdds(oddsOfCrop)) {
					for (int z = 2; z < 14; z++) {
						chunk.setVines(x - 1, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z,
								Direction.EAST);
						chunk.setVines(x + 1, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z,
								Direction.WEST);
//						chunk.setWool(x - 1, x, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z, z + 1, DyeColor.BLACK);
//						chunk.setWool(x + 1, x + 2, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z, z + 1, DyeColor.BLUE);
					}
				}
			}
		} else {
			for (int z = 1; z < 15; z += stepVineRowDelta) {
				if (chunkOdds.playOdds(oddsOfCrop)) {
					for (int x = 2; x < 14; x++) {
						chunk.setVines(x, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z - 1,
								Direction.SOUTH);
						chunk.setVines(x, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z + 1,
								Direction.NORTH);
//						chunk.setWool(x, x + 1, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z - 1, z, DyeColor.RED);
//						chunk.setWool(x, x + 1, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z + 1, z + 2, DyeColor.GREEN);
					}
				}
			}
		}
	}

	private final static CropType[] normalCrops = { CropType.FALLOW, CropType.TRELLIS, CropType.VINES, CropType.GRASS,
			CropType.FERN, CropType.CACTUS, CropType.REED, CropType.DANDELION, CropType.DEAD_BUSH, CropType.POPPY,
			CropType.BLUE_ORCHID, CropType.ALLIUM, CropType.AZURE_BLUET, CropType.OXEYE_DAISY, CropType.RED_TULIP,
			CropType.ORANGE_TULIP, CropType.WHITE_TULIP, CropType.PINK_TULIP, CropType.SUNFLOWER, CropType.LILAC,
			CropType.TALL_GRASS, CropType.TALL_FERN, CropType.ROSE_BUSH, CropType.PEONY, CropType.EMERALD_GREEN,
			CropType.OAK_SAPLING, CropType.DARK_OAK_SAPLING, CropType.BIRCH_SAPLING, CropType.ACACIA_SAPLING,
			CropType.JUNGLE_SAPLING, CropType.OAK_TREE, CropType.PINE_TREE, CropType.BIRCH_TREE, CropType.JUNGLE_TREE,
			CropType.SWAMP_TREE, CropType.ACACIA_TREE, CropType.WHEAT, CropType.CARROT, CropType.POTATO,
			CropType.BEETROOT, CropType.MELON, CropType.PUMPKIN, CropType.SHORT_FLOWERS, CropType.TALL_FLOWERS,
			CropType.ALL_FLOWERS, CropType.SHORT_PLANTS, CropType.TALL_PLANTS, CropType.EDIBLE_PLANTS, CropType.PADDOCK,
			CropType.PADDOCK, CropType.PADDOCK, CropType.PADDOCK, CropType.PADDOCK, CropType.PADDOCK, CropType.PADDOCK,
			CropType.PADDOCK, CropType.HOTAIR_BALLOON };

	protected CropType setNormalCrop() {
		CropType result = pickACrop(normalCrops);
		if (result == CropType.CACTUS) // make cactus less frequent
			result = pickACrop(normalCrops);
		return result;
	}

	private final static CropType[] decayCrops = { CropType.FALLOW, CropType.TRELLIS, CropType.DEAD_BUSH,
			CropType.BROWN_MUSHROOM, CropType.RED_MUSHROOM, CropType.DECAY_PLANTS, CropType.PADDOCK };

	protected CropType setDecayedNormalCrop() {
		return pickACrop(decayCrops);
	}

	private final static CropType[] netherCrops = { CropType.FALLOW, CropType.TRELLIS, CropType.DEAD_BUSH,
			CropType.BROWN_MUSHROOM, CropType.RED_MUSHROOM, CropType.NETHERWART, CropType.NETHER_PLANTS,
			CropType.PADDOCK };

	protected CropType setNetherCrop() {
		return pickACrop(netherCrops);
	}

	protected CropType setDecayedNetherCrop() {
		if (chunkOdds.flipCoin())
			return CropType.FALLOW;
		else
			return setNetherCrop();
	}

	private CropType pickACrop(CropType... types) {
		return types[chunkOdds.getRandomInt(types.length)];
	}
}
