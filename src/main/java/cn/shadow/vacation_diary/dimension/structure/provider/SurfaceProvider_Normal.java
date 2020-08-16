package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageSets;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageType;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.noise.NoiseGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;

public class SurfaceProvider_Normal extends SurfaceProvider {

	private final BlockState STARFISH[];
	public SurfaceProvider_Normal(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
		
		STARFISH = new BlockState[] {
			BlockRegistry.starfish_orange.get().getDefaultState().with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)),
			BlockRegistry.starfish_red.get().getDefaultState().with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)),
			BlockRegistry.starfish_yellow.get().getDefaultState().with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)),
		};
	}

	@Override
	protected void generateSurfacePoint(VocationCityWorldGenerator generator, PlatLot lot, RealBlocks chunk,
			CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		OreProvider ores = generator.oreProvider;
		int y = NoiseGenerator.floor(perciseY);

		// roll the dice
		double primary = odds.getRandomDouble();
		double secondary = odds.getRandomDouble();
		double thirdary = odds.getRandomDouble();

		// top of the world?
		if (y >= generator.snowLevel) {
			ores.dropSnow(generator, chunk, x, y, z,
					(byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));

			// are on a plantable spot?
		} else if (foliage.isPlantable(generator, chunk, x, y, z)) {

			// below sea level and plantable.. then cactus?
			if (y <= generator.seaLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees) {
					if (generator.getWorldSettings().includeAbovegroundFluids) {
						if (primary < reedOdds) {
							if (chunk.isType(x, y, z, Blocks.SAND)) {
								foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.REED);
							}
						}
					} else {
						if (primary < cactusOdds && x % 2 == 0 && z % 2 != 0) {
							if (chunk.isSurroundedByEmpty(x, y + 1, z))
								foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.CACTUS);
						}
					}
				}

				// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

				// trees? but only if we are not too close to the edge of the chunk
				if (includeTrees && primary < treeOdds && inTreeRange(x, z)) {
					if (secondary < treeAltTallOdds && inBigTreeRange(x, z))
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DARK_OAK_TREE);
					else if (secondary < treeAltOdds)
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.BIRCH_TREE);
					else
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.OAK_TREE);

					// foliage?
				} else if (primary < foliageOdds) {

					// what to pepper about
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageSets.PRARIE_PLANTS);
				}

				// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// trees?
				if (includeTrees && primary < treeOdds && inTreeRange(x, z)) {

					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.OAK_TREE);
					else
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.PINE_TREE);

					// foliage?
				} else if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageSets.SHORT_PLANTS);
					else
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageSets.ALL_FERNS);
				}

				// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {

				// trees?
				if (includeTrees && primary < treeOdds && x % 2 == 0 && z % 2 != 0) {
					if (secondary < treeTallOdds)
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.PINE_TREE);
					else
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.TALL_PINE_TREE);

					// foliage?
				} else if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (odds.playOdds(flowerFernOdds))
							foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.FERN);
					} else {
						generator.oreProvider.dropSnow(generator, chunk, x, y + 5, z);
					}
				}
			}

			// can't plant, maybe there is something else I can do
		} else {

			// below sea level?
			if (y < generator.seaLevel) {
				if (generator.getWorldSettings().includeAbovegroundFluids) {

					// trees? but only if we are not too close to the edge
					if (includeTrees && primary < treeOdds && x % 2 == 0 && z % 2 != 0) {
						if(thirdary < starfishOdds) chunk.setBlockState(x, y + 1, z, STARFISH[odds.getRandomInt(3)]);
						else foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageSets.SEA_CORALS);
					} else {
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageSets.SEA_PLANTS);
					}
				} else
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DEAD_BUSH);

//				// trees? but only if we are not too close to the edge
//				if (includeTrees) {
//					if (generator.settings.includeAbovegroundFluids) {
//						if (primary < reedOdds) {
//							if (chunk.isType(x, y, z, Material.SAND)) {
//								foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.REED);
//							}
//						}
//					} else {
//						
//					}
//				}

				// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

				// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {

				if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (odds.playOdds(0.10) && foliage.isPlantable(generator, chunk, x, y, z))
							foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.FERN);
					} else {
						ores.dropSnow(generator, chunk, x, y, z);
					}
				}
			}
		}
	}
}
