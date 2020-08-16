package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.Colors.ColorSet;

public class CoverProvider_Nether extends CoverProvider_Decayed {

	private final double oddsOfCrop = Odds.oddsLikely;
	private final double oddsOfFire = Odds.oddsSomewhatUnlikely;

	public CoverProvider_Nether(Odds odds) {
		super(odds);
	}

	@Override
	public ColorSet getColorSet() {
		return ColorSet.NETHER;
	}

	@Override
	protected void setCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType) {
		switch (coverageType) {
		case NOTHING:
			break;

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
		case BEETROOT:
		case FERN:
		case CACTUS:
		case REED:
		case BRAIN_CORAL:
		case BUBBLE_CORAL:
		case FIRE_CORAL:
		case HORN_CORAL:
		case KELP:
		case SEAGRASS:
		case TUBE_CORAL:

		case OAK_SAPLING:
		case DARK_OAK_SAPLING:
		case BIRCH_SAPLING:
		case JUNGLE_SAPLING:
		case ACACIA_SAPLING:
		case OAK_TREE:
		case PINE_TREE:
		case BIRCH_TREE:
		case JUNGLE_TREE:
		case SWAMP_TREE:
		case ACACIA_TREE:
		case SHORT_OAK_TREE:
		case SHORT_PINE_TREE:
		case SHORT_BIRCH_TREE:
		case SHORT_JUNGLE_TREE:
		case DARK_OAK_TREE:
		case TALL_PINE_TREE:
		case TALL_BIRCH_TREE:
		case TALL_JUNGLE_TREE:
		case MINI_ACACIA_TREE:
		case MINI_BIRCH_TREE:
		case MINI_JUNGLE_TREE:
		case MINI_OAK_TREE:
		case MINI_PINE_TREE:
		case MINI_SWAMP_TREE:
			if (odds.playOdds(oddsOfFire))
				super.setCoverage(generator, chunk, x, y, z, CoverageType.FIRE);
			else if (odds.playOdds(oddsOfCrop))
				super.setCoverage(generator, chunk, x, y, z, CoverageType.NETHERWART);
			break;

		case OAK_TRUNK:
		case PINE_TRUNK:
		case BIRCH_TRUNK:
		case JUNGLE_TRUNK:
		case ACACIA_TRUNK:
		case SWAMP_TRUNK:
		case TALL_OAK_TRUNK:
		case TALL_PINE_TRUNK:
		case TALL_BIRCH_TRUNK:
		case TALL_JUNGLE_TRUNK:
		case TALL_ACACIA_TRUNK:
		case TALL_SWAMP_TRUNK:
		case MINI_ACACIA_TRUNK:
		case MINI_BIRCH_TRUNK:
		case MINI_JUNGLE_TRUNK:
		case MINI_OAK_TRUNK:
		case MINI_PINE_TRUNK:
		case MINI_SWAMP_TRUNK:
		case DEAD_BUSH:
//		case DEAD_GRASS:
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case NETHERWART:
		case FIRE:
			super.setCoverage(generator, chunk, x, y, z, coverageType);
			break;
		}
	}
}
