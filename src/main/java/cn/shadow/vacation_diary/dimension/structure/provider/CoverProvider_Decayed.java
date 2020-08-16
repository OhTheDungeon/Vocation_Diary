package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.dimension.support.Colors.ColorSet;

public class CoverProvider_Decayed extends CoverProvider {

	private final double oddsOfCrop = Odds.oddsLikely;

	public CoverProvider_Decayed(Odds odds) {
		super(odds);
	}

	@Override
	public ColorSet getColorSet() {
		return ColorSet.TAN;
	}

	@Override
	public void generateCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType) {
		if (likelyCover(generator))
			setCoverage(generator, chunk, x, y, z, coverageType);
	}

	@Override
	void setCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType) {
		switch (coverageType) {
		case GRASS:
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
		case WHEAT:
		case CARROTS:
		case POTATO:
		case MELON:
		case PUMPKIN:
		case FERN:
		case REED:
		case OAK_SAPLING:
		case DARK_OAK_SAPLING:
		case BIRCH_SAPLING:
		case JUNGLE_SAPLING:
		case ACACIA_SAPLING:
		case DEAD_BUSH:
			if (odds.playOdds(oddsOfCrop))
				super.setCoverage(generator, chunk, x, y, z, CoverageType.DEAD_BUSH);
			break;

		case MINI_OAK_TREE:
		case MINI_OAK_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_OAK_TRUNK);
			break;
		case MINI_PINE_TREE:
		case MINI_PINE_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_PINE_TRUNK);
			break;
		case MINI_BIRCH_TREE:
		case MINI_BIRCH_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_BIRCH_TRUNK);
			break;
		case MINI_JUNGLE_TREE:
		case MINI_JUNGLE_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_JUNGLE_TRUNK);
			break;
		case MINI_ACACIA_TRUNK:
		case MINI_ACACIA_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_JUNGLE_TRUNK);
			break;
		case MINI_SWAMP_TREE:
		case MINI_SWAMP_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_SWAMP_TRUNK);
			break;

		case OAK_TRUNK:
		case OAK_TREE:
		case SHORT_OAK_TREE:
		case DARK_OAK_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.OAK_TRUNK);
			break;

		case PINE_TRUNK:
		case PINE_TREE:
		case SHORT_PINE_TREE:
		case TALL_PINE_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.PINE_TRUNK);
			break;

		case BIRCH_TRUNK:
		case BIRCH_TREE:
		case SHORT_BIRCH_TREE:
		case TALL_BIRCH_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.BIRCH_TRUNK);
			break;

		case JUNGLE_TREE:
		case JUNGLE_TRUNK:
		case SHORT_JUNGLE_TREE:
		case TALL_JUNGLE_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.JUNGLE_TRUNK);
			break;

		case ACACIA_TRUNK:
		case ACACIA_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.ACACIA_TRUNK);
			break;

		case SWAMP_TREE:
		case SWAMP_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.SWAMP_TRUNK);
			break;

		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case NETHERWART:
		case FIRE:
		case CACTUS:
//		case DEAD_GRASS:
			super.setCoverage(generator, chunk, x, y, z, coverageType);
			break;
		default:
			break;
		}
	}

	@Override
	public void makePlantable(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
//		chunk.setBlock(x, y, z, Material.GRASS_BLOCK); // Honey Badger don't care!
		chunk.clearBlock(x, y + 1, z);
	}

	@Override
	public boolean isPlantable(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {

		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;

		// depends on the block's type and what the world is like
		return !chunk.isEmpty(x, y, z);
	}
}
