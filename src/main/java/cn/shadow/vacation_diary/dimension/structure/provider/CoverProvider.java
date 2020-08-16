package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.CropsAgeHelper;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.dimension.support.TreeSpecies;
import cn.shadow.vacation_diary.dimension.support.Trees;
import cn.shadow.vacation_diary.dimension.support.Colors.ColorSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public abstract class CoverProvider extends Provider {

	public enum CoverageType {
		NOTHING, GRASS, FERN, /* DEAD_GRASS, */ DANDELION, DEAD_BUSH,

		POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, OXEYE_DAISY, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP,

		SUNFLOWER, LILAC, TALL_GRASS, TALL_FERN, ROSE_BUSH, PEONY,
		
		GREEN_TEA, BLACK_TEA, BLUE_SAGE, BUTTERFLY_WEED, FUCHSIA, GOLDEN_SHOWER,
		HORTENSIA, LARKSPUR, MOUNTAIN_LAUREL, PURPLE_HIBISCUS,

		CACTUS, REED, EMERALD_GREEN,

		OAK_SAPLING, DARK_OAK_SAPLING, BIRCH_SAPLING, JUNGLE_SAPLING, ACACIA_SAPLING,

		MINI_OAK_TREE, SHORT_OAK_TREE, OAK_TREE, DARK_OAK_TREE, MINI_PINE_TREE, SHORT_PINE_TREE, PINE_TREE,
		TALL_PINE_TREE, MINI_BIRCH_TREE, SHORT_BIRCH_TREE, BIRCH_TREE, TALL_BIRCH_TREE, MINI_JUNGLE_TREE,
		SHORT_JUNGLE_TREE, JUNGLE_TREE, TALL_JUNGLE_TREE, MINI_SWAMP_TREE, SWAMP_TREE, MINI_ACACIA_TREE, ACACIA_TREE,

		MINI_OAK_TRUNK, OAK_TRUNK, TALL_OAK_TRUNK, MINI_PINE_TRUNK, PINE_TRUNK, TALL_PINE_TRUNK, MINI_BIRCH_TRUNK,
		BIRCH_TRUNK, TALL_BIRCH_TRUNK, MINI_JUNGLE_TRUNK, JUNGLE_TRUNK, TALL_JUNGLE_TRUNK, MINI_SWAMP_TRUNK,
		SWAMP_TRUNK, TALL_SWAMP_TRUNK, MINI_ACACIA_TRUNK, ACACIA_TRUNK, TALL_ACACIA_TRUNK,

		WHEAT, CARROTS, POTATO, MELON, PUMPKIN, BEETROOT,

		//		TALL_BROWN_MUSHROOM, TALL_RED_MUSHROOM,
		BROWN_MUSHROOM, RED_MUSHROOM, NETHERWART,

		BRAIN_CORAL, BUBBLE_CORAL, FIRE_CORAL, HORN_CORAL, TUBE_CORAL, SEAGRASS, KELP,

		FIRE
	}

	/*
	 * BUBBLE_COLUMN(13758, BubbleColumn.class),
	 *
	 *
	 * SEAGRASS(23942), TALL_SEAGRASS(27189, Bisected.class),
	 *
	 * SEA_LANTERN(16984), SEA_PICKLE(19562, SeaPickle.class),
	 *
	 *
	 */

	public enum CoverageSets {
		SHORT_FLOWERS, TALL_FLOWERS, ALL_FERNS, ALL_FLOWERS, SHORT_PLANTS, TALL_PLANTS, ALL_PLANTS, GENERAL_SAPLINGS,
		ALL_SAPLINGS, OAK_TREES, PINE_TREES, BIRCH_TREES, JUNGLE_TREES, ACACIA_TREES, SWAMP_TREES, SHORT_TREES,
		MEDIUM_TREES, TALL_TREES, ALL_TREES, PRARIE_PLANTS, EDIBLE_PLANTS, SHORT_MUSHROOMS, NETHER_PLANTS, DECAY_PLANTS,
		SEA_PLANTS, SEA_CORALS
	}

	private final static CoverageType[] ShortFlowers = { CoverageType.DANDELION, CoverageType.POPPY,
			CoverageType.BLUE_ORCHID, CoverageType.ALLIUM, CoverageType.AZURE_BLUET, CoverageType.OXEYE_DAISY,
			CoverageType.RED_TULIP, CoverageType.ORANGE_TULIP, CoverageType.WHITE_TULIP, CoverageType.PINK_TULIP };

	private final static CoverageType[] TallFlowers = { CoverageType.SUNFLOWER, CoverageType.LILAC,
			CoverageType.ROSE_BUSH, CoverageType.PEONY,
			CoverageType.GREEN_TEA, CoverageType.BLACK_TEA,
			CoverageType.BUTTERFLY_WEED, CoverageType.FUCHSIA,
			CoverageType.LARKSPUR, CoverageType.GOLDEN_SHOWER,
			CoverageType.HORTENSIA, CoverageType.MOUNTAIN_LAUREL,
			CoverageType.PURPLE_HIBISCUS
			};

	private final static CoverageType[] AllFlowers = { CoverageType.DANDELION, CoverageType.POPPY,
			CoverageType.BLUE_ORCHID, CoverageType.ALLIUM, CoverageType.AZURE_BLUET, CoverageType.OXEYE_DAISY,
			CoverageType.RED_TULIP, CoverageType.ORANGE_TULIP, CoverageType.WHITE_TULIP, CoverageType.PINK_TULIP,
			CoverageType.SUNFLOWER, CoverageType.LILAC, CoverageType.ROSE_BUSH, CoverageType.PEONY,
			CoverageType.GREEN_TEA, CoverageType.BLACK_TEA,
			CoverageType.BUTTERFLY_WEED, CoverageType.FUCHSIA,
			CoverageType.LARKSPUR, CoverageType.GOLDEN_SHOWER,
			CoverageType.HORTENSIA, CoverageType.MOUNTAIN_LAUREL,
			CoverageType.PURPLE_HIBISCUS
			};

	private final static CoverageType[] ShortPlants = { CoverageType.GRASS, CoverageType.FERN };

	private final static CoverageType[] AllFerns = { CoverageType.FERN, CoverageType.FERN, CoverageType.FERN,
			CoverageType.FERN, CoverageType.TALL_FERN };

	private final static CoverageType[] TallPlants = { CoverageType.CACTUS, CoverageType.REED, CoverageType.TALL_GRASS,
			CoverageType.TALL_FERN, CoverageType.SUNFLOWER, CoverageType.LILAC, CoverageType.EMERALD_GREEN };

	private final static CoverageType[] AllPlants = { CoverageType.GRASS, CoverageType.FERN, CoverageType.CACTUS,
			CoverageType.REED, CoverageType.TALL_GRASS, CoverageType.TALL_FERN, CoverageType.ROSE_BUSH,
			CoverageType.PEONY, CoverageType.EMERALD_GREEN,
			CoverageType.GREEN_TEA, CoverageType.BLACK_TEA,
			CoverageType.BUTTERFLY_WEED, CoverageType.FUCHSIA,
			CoverageType.LARKSPUR, CoverageType.GOLDEN_SHOWER,
			CoverageType.HORTENSIA, CoverageType.MOUNTAIN_LAUREL,
			CoverageType.PURPLE_HIBISCUS
			};

	private final static CoverageType[] PrariePlants = { CoverageType.GRASS, CoverageType.TALL_GRASS,
			CoverageType.TALL_GRASS, CoverageType.TALL_GRASS, CoverageType.TALL_GRASS, CoverageType.TALL_FERN,
			CoverageType.TALL_FERN, CoverageType.DANDELION, CoverageType.POPPY, CoverageType.BLUE_ORCHID,
			CoverageType.ALLIUM, CoverageType.AZURE_BLUET, CoverageType.OXEYE_DAISY, CoverageType.RED_TULIP,
			CoverageType.ORANGE_TULIP, CoverageType.WHITE_TULIP, CoverageType.PINK_TULIP };

	private final static CoverageType[] EdiblePlants = { CoverageType.WHEAT, CoverageType.CARROTS, CoverageType.POTATO,
			CoverageType.BEETROOT, CoverageType.MELON, CoverageType.PUMPKIN };

	private final static CoverageType[] GeneralSaplings = { CoverageType.OAK_SAPLING, CoverageType.DARK_OAK_SAPLING,
			CoverageType.BIRCH_SAPLING };

	private final static CoverageType[] AllSaplings = { CoverageType.OAK_SAPLING, CoverageType.DARK_OAK_SAPLING,
			CoverageType.BIRCH_SAPLING, CoverageType.JUNGLE_SAPLING, CoverageType.ACACIA_SAPLING };

	private final static CoverageType[] OakTrees = { CoverageType.OAK_SAPLING, CoverageType.SHORT_OAK_TREE,
			CoverageType.OAK_TREE, CoverageType.DARK_OAK_TREE };

	private final static CoverageType[] PineTrees = { CoverageType.DARK_OAK_SAPLING, CoverageType.SHORT_PINE_TREE,
			CoverageType.PINE_TREE, CoverageType.TALL_PINE_TREE };

	private final static CoverageType[] BirchTrees = { CoverageType.BIRCH_SAPLING, CoverageType.SHORT_BIRCH_TREE,
			CoverageType.BIRCH_TREE, CoverageType.TALL_BIRCH_TREE };

	private final static CoverageType[] JungleTrees = { CoverageType.JUNGLE_SAPLING, CoverageType.SHORT_JUNGLE_TREE,
			CoverageType.JUNGLE_TREE, CoverageType.TALL_JUNGLE_TREE };

	private final static CoverageType[] AcaciaTrees = { CoverageType.ACACIA_SAPLING, CoverageType.ACACIA_TREE };

	private final static CoverageType[] SwampTrees = { CoverageType.SWAMP_TREE };

	private final static CoverageType[] ShortTrees = { CoverageType.SHORT_BIRCH_TREE, CoverageType.SHORT_JUNGLE_TREE,
			CoverageType.SHORT_OAK_TREE, CoverageType.SHORT_PINE_TREE };

	private final static CoverageType[] MediumTrees = { CoverageType.BIRCH_TREE, CoverageType.JUNGLE_TREE,
			CoverageType.OAK_TREE, CoverageType.PINE_TREE };

	private final static CoverageType[] TallTrees = { CoverageType.TALL_BIRCH_TREE, CoverageType.TALL_JUNGLE_TREE,
			CoverageType.DARK_OAK_TREE, CoverageType.TALL_PINE_TREE, CoverageType.ACACIA_TREE,
			CoverageType.SWAMP_TREE };

	private final static CoverageType[] AllTrees = { CoverageType.SHORT_BIRCH_TREE, CoverageType.SHORT_JUNGLE_TREE,
			CoverageType.SHORT_OAK_TREE, CoverageType.SHORT_PINE_TREE, CoverageType.BIRCH_TREE,
			CoverageType.JUNGLE_TREE, CoverageType.OAK_TREE, CoverageType.PINE_TREE, CoverageType.TALL_BIRCH_TREE,
			CoverageType.TALL_JUNGLE_TREE, CoverageType.DARK_OAK_TREE, CoverageType.TALL_PINE_TREE,
			CoverageType.ACACIA_TREE, CoverageType.SWAMP_TREE };

	private final static CoverageType[] ShortMushrooms = { CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM };

	private final static CoverageType[] NetherPlants = { CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM,
			CoverageType.NETHERWART, CoverageType.DEAD_BUSH, CoverageType.FIRE };

	private final static CoverageType[] DecayPlants = { CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM,
			CoverageType.DEAD_BUSH };

	private final static CoverageType[] SeaPlants = { CoverageType.SEAGRASS, CoverageType.KELP };

	private final static CoverageType[] SeaCoral = { CoverageType.BRAIN_CORAL, CoverageType.BUBBLE_CORAL,
			CoverageType.FIRE_CORAL, CoverageType.HORN_CORAL, CoverageType.TUBE_CORAL };

	private final static double oddsOfDarkCover = Odds.oddsLikely;
	final Odds odds;

	CoverProvider(Odds odds) {
		super();
		this.odds = odds;
	}

	public abstract void generateCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType);

	private CoverageType getRandomCoverage(CoverageType... types) {
		return types[odds.getRandomInt(types.length)];
	}

	public void generateRandomCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType... types) {
		setCoverage(generator, chunk, x, y, z, getRandomCoverage(types));
	}

	public void generateCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageSets coverageSet) {
		switch (coverageSet) {
		case ALL_FLOWERS:
			generateRandomCoverage(generator, chunk, x, y, z, AllFlowers);
			break;
		case ALL_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, AllPlants);
			break;
		case ALL_SAPLINGS:
			generateRandomCoverage(generator, chunk, x, y, z, AllSaplings);
			break;
		case ALL_FERNS:
			generateRandomCoverage(generator, chunk, x, y, z, AllFerns);
			break;
		case PRARIE_PLANTS:
			if (odds.playOdds(Odds.oddsMoreLikely))
				generateRandomCoverage(generator, chunk, x, y, z, CoverageType.GRASS);
			else
				generateRandomCoverage(generator, chunk, x, y, z, PrariePlants);
			break;
		case EDIBLE_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, EdiblePlants);
			break;
		case GENERAL_SAPLINGS:
			generateRandomCoverage(generator, chunk, x, y, z, GeneralSaplings);
			break;
		case OAK_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, OakTrees);
			break;
		case PINE_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, PineTrees);
			break;
		case BIRCH_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, BirchTrees);
			break;
		case JUNGLE_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, JungleTrees);
			break;
		case ACACIA_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, AcaciaTrees);
			break;
		case SWAMP_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, SwampTrees);
			break;
		case SHORT_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, ShortTrees);
			break;
		case MEDIUM_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, MediumTrees);
			break;
		case TALL_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, TallTrees);
			break;
		case ALL_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, AllTrees);
			break;

		case NETHER_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, NetherPlants);
			break;
		case DECAY_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, DecayPlants);
			break;
		case SHORT_FLOWERS:
			generateRandomCoverage(generator, chunk, x, y, z, ShortFlowers);
			break;
		case SHORT_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, ShortPlants);
			break;
		case SHORT_MUSHROOMS:
			generateRandomCoverage(generator, chunk, x, y, z, ShortMushrooms);
			break;
		case TALL_FLOWERS:
			generateRandomCoverage(generator, chunk, x, y, z, TallFlowers);
			break;
		case TALL_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, TallPlants);
			break;
		case SEA_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, SeaPlants);
			break;
		case SEA_CORALS:
			generateRandomCoverage(generator, chunk, x, y, z, SeaCoral);
			break;
		}
	}

	void setCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType) {
		Block topsoil = generator.oreProvider.surfaceMaterial;
		switch (coverageType) {
		case NOTHING:
			break;
		case GRASS:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.GRASS);
			break;
		case FERN:
			if (chunk.isOfTypes(x, y - 1, z, topsoil, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.FERN);
			break;
//		case DEAD_GRASS:
//			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.FARMLAND))
//				chunk.setBlock(x, y, z, Blocks.LONG_GRASS, new LongGrass(GrassSpecies.DEAD));
//			
//			// fine, try the other one that looks like this
//			else if (chunk.isOfTypes(x, y - 1, z, Blocks.SAND, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA))
//				chunk.setBlock(x, y, z, Blocks.DEAD_BUSH);
//			break;
		case DEAD_BUSH:
			// @@ technically this can also go on all colored terracotta but... whatever
			if (chunk.isOfTypes(x, y - 1, z, Blocks.SAND, Blocks.DIRT, Blocks.PODZOL,
					Blocks.TERRACOTTA /* , Blocks.WHITE_TERRACOTTA */))
				chunk.setBlock(x, y, z, Blocks.DEAD_BUSH);
//			
//			// fine, try the other one that looks like this
//			else if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.FARMLAND))
//				chunk.setBlock(x, y, z, Blocks.LONG_GRASS, new LongGrass(GrassSpecies.DEAD));
			break;
		case CACTUS:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.SAND);
			chunk.setBlocks(x, y, y + odds.getRandomInt(3) + 2, z, Blocks.CACTUS);
			break;
		case WHEAT:			

			chunk.setBlockIfNot(x, y - 1, z, Blocks.FARMLAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.WHEAT, odds.getRandomDouble());
			break;
		case CARROTS:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.FARMLAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.CARROTS, odds.getRandomDouble());
			break;
		case POTATO:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.FARMLAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.POTATOES, odds.getRandomDouble());
			break;
		case MELON:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.FARMLAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.MELON_STEM, odds.getRandomDouble());
			break;
		case PUMPKIN:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.FARMLAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.PUMPKIN_STEM, odds.getRandomDouble());
			break;
		case BEETROOT:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.FARMLAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.BEETROOTS, odds.getRandomDouble());
			break;
		case REED:
			if (chunk.isByWater(x, y - 1, z)) {
				chunk.setBlock(x, y - 1, z, Blocks.SAND);
				chunk.setBlocks(x, y, y + odds.getRandomInt(2) + 2, z, Blocks.SUGAR_CANE);
			}
			break;
		case DANDELION:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.DANDELION);
			break;
		case POPPY:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.POPPY);
			break;
		case BLUE_ORCHID:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.BLUE_ORCHID);
			break;
		case ALLIUM:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.ALLIUM);
			break;
		case AZURE_BLUET:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.AZURE_BLUET);
			break;
		case OXEYE_DAISY:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.OXEYE_DAISY);
			break;
		case RED_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.RED_TULIP);
			break;
		case ORANGE_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.ORANGE_TULIP);
			break;
		case WHITE_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.WHITE_TULIP);
			break;
		case PINK_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.PINK_TULIP);
			break;
		case SUNFLOWER:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, Blocks.SUNFLOWER);
			}
			break;
		case LILAC:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, Blocks.LILAC);
			}
			break;
		case TALL_GRASS:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, Blocks.TALL_GRASS);
			}
			break;
		case TALL_FERN:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, Blocks.LARGE_FERN);
			}
			break;
		case ROSE_BUSH:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, Blocks.ROSE_BUSH);
			}
			break;
		case PEONY:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, Blocks.PEONY);
			}
			break;
		case GREEN_TEA:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.green_tea_plant.get());
			}
			break;
		case BLACK_TEA:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.black_tea_plant.get());
			}
			break;
		case BLUE_SAGE:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.blue_sage_block.get());
			}
			break;
		case BUTTERFLY_WEED:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.butterfly_weed_block.get());
			}
			break;
		case FUCHSIA:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.fuchsia_block.get());
			}
			break;
		case GOLDEN_SHOWER:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.golden_shower_block.get());
			}
			break;
		case HORTENSIA:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.hortensia_block.get());
			}
			break;
		case LARKSPUR:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.larkspur_block.get());
			}
			break;
		case MOUNTAIN_LAUREL:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.mountain_laurel_block.get());
			}
			break;
		case PURPLE_HIBISCUS:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				chunk.setTallBlock(x, y, z, BlockRegistry.purple_hibiscus_block.get());
			}
			break;
		case EMERALD_GREEN:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND)) {
				TreeSpecies species = odds.getRandomWoodSpecies();
				chunk.setLeaves(x, y + 1, y + odds.getRandomInt(2, 4), z, Trees.getRandomWoodLeaves(species), true); // @@
				// why
				// are
				// these
				// decaying
				// when
				// set
				// to
				// false?
				chunk.setBlock(x, y, z, Trees.getRandomWoodLog(species));
			}
			break;
		case OAK_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.OAK_SAPLING);
			break;
		case BIRCH_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.BIRCH_SAPLING);
			break;
		case DARK_OAK_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.DARK_OAK_SAPLING);
			break;
		case JUNGLE_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.JUNGLE_SAPLING);
			break;
		case ACACIA_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
					Blocks.FARMLAND))
				chunk.setBlock(x, y, z, Blocks.ACACIA_SAPLING);
			break;

		case BROWN_MUSHROOM:
			if (chunk.isWater(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, Blocks.MYCELIUM);
			if (chunk.isOfTypes(x, y - 1, z, Blocks.MYCELIUM))
				chunk.setBlock(x, y, z, Blocks.BROWN_MUSHROOM);
			break;
		case RED_MUSHROOM:
			if (chunk.isWater(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, Blocks.MYCELIUM);
			if (chunk.isOfTypes(x, y - 1, z, Blocks.MYCELIUM))
				chunk.setBlock(x, y, z, Blocks.RED_MUSHROOM);
			break;
		case NETHERWART:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.SOUL_SAND);
			CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.NETHER_WART, odds.getRandomDouble());
			break;
		case FIRE:
			chunk.setBlockIfNot(x, y - 1, z, Blocks.NETHERRACK);
			chunk.setBlock(x, y, z, Blocks.FIRE);
			break;

		case BRAIN_CORAL:
			if (y < generator.seaLevel || !generator.getWorldSettings().includeAbovegroundFluids)
				generateCoral(generator, chunk, x, y, z, Blocks.BRAIN_CORAL, Blocks.BRAIN_CORAL_FAN,
						Blocks.BRAIN_CORAL_WALL_FAN, Blocks.BRAIN_CORAL_BLOCK);
			else
				generateCoral(generator, chunk, x, y, z, Blocks.DEAD_BRAIN_CORAL, Blocks.DEAD_BRAIN_CORAL_FAN,
						Blocks.DEAD_BRAIN_CORAL_WALL_FAN, Blocks.DEAD_BRAIN_CORAL_BLOCK);
			break;
		case BUBBLE_CORAL:
			if (y < generator.seaLevel || !generator.getWorldSettings().includeAbovegroundFluids)
				generateCoral(generator, chunk, x, y, z, Blocks.BUBBLE_CORAL, Blocks.BUBBLE_CORAL_FAN,
						Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.BUBBLE_CORAL_BLOCK);
			else
				generateCoral(generator, chunk, x, y, z, Blocks.DEAD_BUBBLE_CORAL, Blocks.DEAD_BUBBLE_CORAL_FAN,
						Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, Blocks.DEAD_BUBBLE_CORAL_BLOCK);
			break;
		case FIRE_CORAL:
			if (y < generator.seaLevel || !generator.getWorldSettings().includeAbovegroundFluids)
				generateCoral(generator, chunk, x, y, z, Blocks.FIRE_CORAL, Blocks.FIRE_CORAL_FAN,
						Blocks.FIRE_CORAL_WALL_FAN, Blocks.FIRE_CORAL_BLOCK);
			else
				generateCoral(generator, chunk, x, y, z, Blocks.DEAD_FIRE_CORAL, Blocks.DEAD_FIRE_CORAL_FAN,
						Blocks.DEAD_FIRE_CORAL_WALL_FAN, Blocks.DEAD_FIRE_CORAL_BLOCK);
			break;
		case HORN_CORAL:
			if (y < generator.seaLevel || !generator.getWorldSettings().includeAbovegroundFluids)
				generateCoral(generator, chunk, x, y, z, Blocks.HORN_CORAL, Blocks.HORN_CORAL_FAN,
						Blocks.HORN_CORAL_WALL_FAN, Blocks.HORN_CORAL_BLOCK);
			else
				generateCoral(generator, chunk, x, y, z, Blocks.DEAD_HORN_CORAL, Blocks.DEAD_HORN_CORAL_FAN,
						Blocks.DEAD_HORN_CORAL_WALL_FAN, Blocks.DEAD_HORN_CORAL_BLOCK);
			break;
		case TUBE_CORAL:
			if (y < generator.seaLevel || !generator.getWorldSettings().includeAbovegroundFluids)
				generateCoral(generator, chunk, x, y, z, Blocks.TUBE_CORAL, Blocks.TUBE_CORAL_FAN,
						Blocks.TUBE_CORAL_WALL_FAN, Blocks.TUBE_CORAL_BLOCK);
			else
				generateCoral(generator, chunk, x, y, z, Blocks.DEAD_TUBE_CORAL, Blocks.DEAD_TUBE_CORAL_FAN,
						Blocks.DEAD_TUBE_CORAL_WALL_FAN, Blocks.DEAD_TUBE_CORAL_BLOCK);
			break;
		case SEAGRASS:
			if (y < generator.seaLevel && odds.playOdds(Odds.oddsLikely)) {
				if (generator.getWorldSettings().includeAbovegroundFluids) {
					if (chunk.isWater(x, y, z) && !chunk.isWater(x, y - 1, z)) {
						if (odds.flipCoin())
							chunk.setBlock(x, y, z, Blocks.SEAGRASS);
						else if (chunk.isWater(x, y + 1, z))
							chunk.setTallBlock(x, y, z, Blocks.TALL_SEAGRASS);
					}
				} else
					chunk.setBlock(x, y, z, Blocks.DEAD_BUSH);
			}
			// if under water then
			// randomly
			// place seagrass or tall seagrass
			// Blocks.SEAGRASS;
			// Blocks.TALL_SEAGRASS;
			// Seagrass generates in either its tall or small form in ocean biomes near
			// kelp,
			break;

		case KELP:
			if (y < generator.seaLevel && odds.playOdds(Odds.oddsLikely)) {
				if (generator.getWorldSettings().includeAbovegroundFluids) {
					if (chunk.isWater(x, y, z) && !chunk.isWater(x, y - 1, z)) {
						if (odds.flipCoin())
							chunk.setBlock(x, y, z, Blocks.KELP);
						else {
							int h = (generator.seaLevel - y) / 3 * 2;
							if (h > 0) {
								h = odds.getRandomInt(h);
								chunk.setBlocks(x, y, y + h, z, Blocks.KELP_PLANT);
								CropsAgeHelper.setCropBlock(x, y + h, z, chunk, Blocks.KELP, 0.50);
							} else
								CropsAgeHelper.setCropBlock(x, y, z, chunk, Blocks.KELP, 0.50);
						}
					}
				} else
					chunk.setBlock(x, y, z, Blocks.DEAD_BUSH);
			}
			// Blocks.KELP;
			// Kelp naturally generates in any ocean biomes except warm ocean, near and
			// around seagrass.
			break;

		default:
			if (odds.playOdds(generator.getWorldSettings().spawnTrees) && !chunk.onEdgeXZ(x, z)) {
				switch (coverageType) {
				case MINI_OAK_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case OAK_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case TALL_OAK_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.BIG_TREE);
					break;
				case MINI_PINE_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case PINE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case TALL_PINE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.TALL_REDWOOD);
					break;
				case MINI_BIRCH_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case BIRCH_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case TALL_BIRCH_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.TALL_BIRCH);
					break;
				case MINI_JUNGLE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case JUNGLE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.SMALL_JUNGLE);
					break;
				case TALL_JUNGLE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case MINI_SWAMP_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case SWAMP_TRUNK:
				case TALL_SWAMP_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case MINI_ACACIA_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
				case ACACIA_TRUNK:
				case TALL_ACACIA_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.ACACIA);
					break;

				case MINI_OAK_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case SHORT_OAK_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case OAK_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.BIG_TREE);
					break;
				case DARK_OAK_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.DARK_OAK);
					break;
				case MINI_PINE_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case SHORT_PINE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case PINE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.TALL_REDWOOD);
					break;
				case TALL_PINE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.MEGA_REDWOOD);
					break;
				case MINI_BIRCH_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case SHORT_BIRCH_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case BIRCH_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case TALL_BIRCH_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.TALL_BIRCH);
					break;
				case MINI_JUNGLE_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case SHORT_JUNGLE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.JUNGLE_BUSH);
					break;
				case JUNGLE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.SMALL_JUNGLE);
					break;
				case TALL_JUNGLE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case MINI_SWAMP_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case SWAMP_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case MINI_ACACIA_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
				case ACACIA_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
				default:
					break;
				}
			}
		}
	}

	private void generateCoral(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, Block coral,
			Block coralFan, Block coralWall, Block coralBlock) {
		// if under water then living ones
		// if on sand then
		// maybe turn it into a coral block
		//
		// if beside coral block then
		// place wall fan
		// else randomly
		// place coral or fan coral
		// else above water
		// same as above but dead ones
//		on dirt, coarse dirt, sand, red sand or gravel underwater
//		Corals naturally generate in coral reef structures found in warm ocean biomes.
		if (chunk.isWater(x, y, z) && !chunk.isWater(x, y - 1, z)) {
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBlockRandomly(x, y, z, odds, coral, coralFan);
			else {
				double oddsOfBlocks = odds.calcRandomRange(Odds.oddsSomewhatUnlikely, Odds.oddsPrettyLikely);
				double oddsOfTrim = odds.calcRandomRange(Odds.oddsSomewhatLikely, Odds.oddsVeryLikely);

				int xW = odds.getRandomInt(1, 4);
				int xH = Math.max(1, xW / 2);
				int x1 = chunk.clampXZ(x - xH);
				int x2 = chunk.clampXZ(x1 + xW);

				int zW = odds.getRandomInt(1, 4);
				int zH = Math.max(1, zW / 2);
				int z1 = chunk.clampXZ(z - zH);
				int z2 = chunk.clampXZ(z1 + zW);

				int y1 = y - 1;
				int y2 = y + odds.getRandomInt(Math.max(2, (generator.seaLevel - y) / 3 * 2));

				// coral itself
				for (int xI = x1; xI < x2; xI++)
					for (int zI = z1; zI < z2; zI++) {
						for (int yI = y1; yI < y2; yI++)
							if (odds.playOdds(oddsOfBlocks))
								chunk.setBlock(xI, yI, zI, coralBlock);

						// top fans
						if (odds.playOdds(oddsOfTrim) && chunk.isWater(xI, y2, zI)
								&& chunk.isType(xI, y2 - 1, zI, coralBlock))
							chunk.setBlockRandomly(xI, y2, zI, odds, coral, coralFan);
					}

				// north/south wall fans
				for (int xI = x1; xI < x2; xI++)
					for (int yI = y1; yI < y2; yI++) {
						if (chunk.insideXZ(z1 - 1))
							if (odds.playOdds(oddsOfTrim) && chunk.isWater(xI, yI, z1 - 1)
									&& chunk.isType(xI, yI, z1, coralBlock))
								chunk.setBlock(xI, yI, z1 - 1, coralWall, Direction.NORTH);
						if (chunk.insideXZ(z2))
							if (odds.playOdds(oddsOfTrim) && chunk.isWater(xI, yI, z2)
									&& chunk.isType(xI, yI, z2 - 1, coralBlock))
								chunk.setBlock(xI, yI, z2, coralWall, Direction.SOUTH);
					}

				// west/east wall fans
				for (int zI = z1; zI < z2; zI++)
					for (int yI = y1; yI < y2; yI++) {
						if (chunk.insideXZ(x1 - 1))
							if (odds.playOdds(oddsOfTrim) && chunk.isWater(x1 - 1, yI, zI)
									&& chunk.isType(x1, yI, zI, coralBlock))
								chunk.setBlock(x1 - 1, yI, zI, coralWall, Direction.WEST);
						if (chunk.insideXZ(x2))
							if (odds.playOdds(oddsOfTrim) && chunk.isWater(x2, yI, zI)
									&& chunk.isType(x2 - 1, yI, zI, coralBlock))
								chunk.setBlock(x2, yI, zI, coralWall, Direction.EAST);
					}
			}
		}
	}

	boolean likelyCover(VocationCityWorldGenerator generator) {
		return !generator.getWorldSettings().darkEnvironment || odds.playOdds(oddsOfDarkCover);
	}

	public ColorSet getColorSet() {
		return ColorSet.GREEN;
	}

	// Based on work contributed by drew-bahrue
	public static CoverProvider loadProvider(VocationCityWorldGenerator generator, Odds odds) {

		CoverProvider provider = null;

//		// need something like PhatLoot but for coverage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		if (provider == null) {

			switch (generator.worldStyle) {
			default:
				switch (generator.worldType) {
				case NETHER:
					provider = new CoverProvider_Nether(odds);
					break;
				case THE_END:
					provider = new CoverProvider_TheEnd(odds);
					break;
				default:
					if (generator.getWorldSettings().includeDecayedNature)
						provider = new CoverProvider_Decayed(odds);
					else
						provider = new CoverProvider_Normal(odds);
					break;
				}
				break;
			}
		}

		return provider;
	}

	public void makePlantable(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		chunk.setBlock(x, y, z, Blocks.GRASS_BLOCK);
		chunk.clearBlock(x, y + 1, z);
	}

	public boolean isPlantable(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {

		// only if the spot is empty and the spot above is empty
		return chunk.isEmpty(x, y + 1, z) && !chunk.isEmpty(x, y, z);
//		if (!chunk.isEmpty(x, y + 1, z))
//			return false;
//		
//		// depends on the block's type and what the world is like
//		if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
//			return chunk.isType(x, y, z, Blocks.SAND);
//		else
//			return chunk.isPlantable(x, y, z);
	}

//	@Deprecated
//	protected boolean isATree(CoverageType coverageType) {
//		switch (coverageType) {
//		case MINI_OAK_TREE:
//		case SHORT_OAK_TREE:
//		case OAK_TREE:
//		case TALL_OAK_TREE:
//			
//		case MINI_PINE_TREE:
//		case SHORT_PINE_TREE:
//		case PINE_TREE:
//		case TALL_PINE_TREE:
//		
//		case MINI_BIRCH_TREE:
//		case SHORT_BIRCH_TREE:
//		case BIRCH_TREE:
//		case TALL_BIRCH_TREE:
//		
//		case MINI_JUNGLE_TREE:
//		case SHORT_JUNGLE_TREE:
//		case JUNGLE_TREE:
//		case TALL_JUNGLE_TREE:
//		
//		case MINI_SWAMP_TREE:
//		case SWAMP_TREE:
//		
//		case MINI_ACACIA_TREE:
//		case ACACIA_TREE:
//			return true;
//		
//		default:
//			return false;
//		}
//	}
//	
//	@Deprecated
//	protected CoverageType convertToTreeTrunk(CoverageType coverageType) {
//		switch (coverageType) {
//		case MINI_OAK_TREE:
//			return CoverageType.MINI_OAK_TRUNK;
//		case SHORT_OAK_TREE:
//		case OAK_TREE:
//		case TALL_OAK_TREE:
//			return CoverageType.OAK_TRUNK;
//			
//		case MINI_PINE_TREE:
//			return CoverageType.MINI_PINE_TRUNK;
//		case SHORT_PINE_TREE:
//		case PINE_TREE:
//		case TALL_PINE_TREE:
//			return CoverageType.PINE_TRUNK;
//		
//		case MINI_BIRCH_TREE:
//			return CoverageType.MINI_BIRCH_TRUNK;
//		case SHORT_BIRCH_TREE:
//		case BIRCH_TREE:
//		case TALL_BIRCH_TREE:
//			return CoverageType.BIRCH_TRUNK;
//		
//		case MINI_JUNGLE_TREE:
//			return CoverageType.MINI_JUNGLE_TRUNK;
//		case SHORT_JUNGLE_TREE:
//		case JUNGLE_TREE:
//		case TALL_JUNGLE_TREE:
//			return CoverageType.JUNGLE_TRUNK;
//		
//		case MINI_SWAMP_TREE:
//			return CoverageType.MINI_SWAMP_TRUNK;
//		case SWAMP_TREE:
//			return CoverageType.SWAMP_TRUNK;
//		
//		case MINI_ACACIA_TREE:
//			return CoverageType.MINI_ACACIA_TRUNK;
//		case ACACIA_TREE:
//			return CoverageType.ACACIA_TRUNK;
//		
//		default:
//			return CoverageType.NOTHING;
//		}
//	}
}
