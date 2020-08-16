package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.*;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot.LotStyle;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.noise.SimplexNoiseGenerator;
import cn.shadow.vacation_diary.util.noise.SimplexOctaveGenerator;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class ShapeProvider_Normal extends ShapeProvider {

	DataContext parkContext;
	DataContext highriseContext;
	DataContext constructionContext;
	DataContext midriseContext;
	DataContext municipalContext;
	DataContext industrialContext;
	DataContext lowriseContext;
	DataContext neighborhoodContext;
	DataContext farmContext;
	DataContext outlandContext;

	private final SimplexOctaveGenerator landShape1;
	private final SimplexOctaveGenerator landShape2;
	private final SimplexOctaveGenerator seaShape;
	private final SimplexOctaveGenerator noiseShape;
	private final SimplexOctaveGenerator featureShape;
	private final SimplexNoiseGenerator caveShape;
	private final SimplexNoiseGenerator mineShape;

	final int height;
	final int seaLevel;
	private final int landRange;
	private final int seaRange;
	int constructMin;
	int constructRange;

	private final static int landFlattening = 32;
	private final static int seaFlattening = 4;
	private final static int landFactor1to2 = 3;
	final static int noiseVerticalScale = 3;
	private final static int featureVerticalScale = 10;
	private final static int fudgeVerticalScale = noiseVerticalScale * landFactor1to2
			+ featureVerticalScale * landFactor1to2;

	private final static double landFrequency1 = 1.50;
	private final static double landAmplitude1 = 20.0;
	private final static double landHorizontalScale1 = 1.0 / 2048.0;
	private final static double landFrequency2 = 1.0;
	private final static double landAmplitude2 = landAmplitude1 / landFactor1to2;
	private final static double landHorizontalScale2 = landHorizontalScale1 * landFactor1to2;

	private final static double seaFrequency = 1.00;
	private final static double seaAmplitude = 2.00;
	private final static double seaHorizontalScale = 1.0 / 384.0;

	private final static double noiseFrequency = 1.50;
	private final static double noiseAmplitude = 0.70;
	private final static double noiseHorizontalScale = 1.0 / 32.0;

	private final static double featureFrequency = 1.50;
	private final static double featureAmplitude = 0.75;
	private final static double featureHorizontalScale = 1.0 / 64.0;

	private final static double caveScale = 1.0 / 64.0;
	private final static double caveScaleY = caveScale * 2;
	private final static double caveThreshold = 0.75; // smaller the number the more larger the caves will be

	private final static double mineScale = 1.0 / 4.0;
	public final static double mineScaleY = mineScale;

	private final static double oddsOfCentralPark = Odds.oddsUnlikely;

	public ShapeProvider_Normal(VocationCityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		IWorld world = generator.getWorld();
		long seed = generator.getWorldSeed();

		landShape1 = new SimplexOctaveGenerator(seed, 4);
		landShape1.setScale(landHorizontalScale1);
		landShape2 = new SimplexOctaveGenerator(seed, 6);
		landShape2.setScale(landHorizontalScale2);
		seaShape = new SimplexOctaveGenerator(seed + 2, 8);
		seaShape.setScale(seaHorizontalScale);
		noiseShape = new SimplexOctaveGenerator(seed + 3, 16);
		noiseShape.setScale(noiseHorizontalScale);
		featureShape = new SimplexOctaveGenerator(seed + 4, 2);
		featureShape.setScale(featureHorizontalScale);

		caveShape = new SimplexNoiseGenerator(seed);
		mineShape = new SimplexNoiseGenerator(seed + 1);

		// get ranges
		height = world.getMaxHeight();
		seaLevel = world.getSeaLevel();
		landRange = height - seaLevel - fudgeVerticalScale + landFlattening;
		seaRange = seaLevel - fudgeVerticalScale + seaFlattening;
		constructMin = seaLevel;
		constructRange = height - constructMin;
	}

	@Override
	protected void validateLots(VocationCityWorldGenerator generator, PlatMap platmap) {
		// nothing to do in this one
	}

	@Override
	protected void allocateContexts(VocationCityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new NatureContext(generator);
			roadContext = new RoadContext(generator);

			parkContext = new ParkContext(generator);
			highriseContext = new HighriseContext(generator);
			constructionContext = new ConstructionContext(generator);
			midriseContext = new MidriseContext(generator);
			municipalContext = new MunicipalContext(generator);
			industrialContext = new IndustrialContext(generator);
			lowriseContext = new LowriseContext(generator);
			neighborhoodContext = new NeighborhoodContext(generator);
			farmContext = new FarmContext(generator);
			outlandContext = new OutlandContext(generator);

			contextInitialized = true;
		}
	}

	@Override
	public DataContext getContext(int originX, int originZ) {
		return null;
	}

	@Override
	public DataContext getContext(PlatMap platmap) {
		double nature = platmap.getNaturePercent();
		if (nature == 0.0) {
			if (platmap.getOddsGenerator().playOdds(oddsOfCentralPark))
				return parkContext;
			else
				return highriseContext;
		} else if (nature < 0.05) // 5
			return highriseContext;
		else if (nature < 0.10) // 5
			return constructionContext;
		else if (nature < 0.15 && platmap.generator.getWorldSettings().includeMunicipalities) // 5
			return municipalContext;
		else if (nature < 0.25) // 10
			return midriseContext;
		else if (nature < 0.30 && platmap.generator.getWorldSettings().includeIndustrialSectors) // 5
			return industrialContext;
		else if (nature < 0.40) // 10
			return lowriseContext;
		else if (nature < 0.55) // 15
			return neighborhoodContext;
		else if (nature < 0.70 && platmap.generator.getWorldSettings().includeFarms) // 10
			return farmContext;
		else if (nature < 0.75) // 5
			return outlandContext;

			// otherwise just keep what we have
		else
			return natureContext;
	}

	// second attempt
//	else if (nature < 0.05)
//		return constructionContext;
//	else if (nature < 0.10)
//		return midriseContext;
//	else if (nature < 0.20)
//		return municipalContext;
//	else if (nature < 0.30)
//		return industrialContext;
//	else if (nature < 0.40)
//		return lowriseContext;
//	else if (nature < 0.50)
//		return neighborhoodContext;
//	else if (nature < 0.65 && platmap.generator.settings.includeFarms)
//		return farmContext;
//	else if (nature < 0.80)
//		return outlandContext;

	//original dist
//	else if (nature < 0.15)
//		return constructionContext;
//	else if (nature < 0.25)
//		return midriseContext;
//	else if (nature < 0.37)
//		return municipalContext;
//	else if (nature < 0.50)
//		return industrialContext;
//	else if (nature < 0.65)
//		return lowriseContext;
//	else if (nature < 0.75)
//		return neighborhoodContext;
//	else if (nature < 0.90 && platmap.generator.settings.includeFarms)
//		return farmContext;
//	else if (nature < 1.0)
//		return outlandContext;
	@Override
	public String getCollectionName() {
		return "Normal";
	}

	@Override
	protected Biome remapBiome(VocationCityWorldGenerator generator, PlatLot lot, Biome biome) {
		return generator.oreProvider.remapBiome(biome);
	}

	@Override
	public void generateBiome(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, LinearBiomeContainer biomes,
			AbstractCachedYs blockYs) {
		Biome biome = lot.getChunkBiome();
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = blockYs.getBlockY(x, z);

				// buildable?
				if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
				} else if (y == generator.streetLevel) {
				} else {

					// on the beach
					if (y == generator.seaLevel) {
						biome = Biomes.BEACH;
					} else if (y < generator.seaLevel) {
						biome = Biomes.DESERT;
						if (generator.getWorldSettings().includeDecayedNature) {
							
						} else if (generator.getWorldSettings().includeAbovegroundFluids) {
							biome = Biomes.OCEAN;
						}
					} else {
						if (y < generator.treeLevel) {
							biome = Biomes.FOREST;
						} else if (y < generator.evergreenLevel) {
							biome = Biomes.BIRCH_FOREST_HILLS;
						} else if (y < generator.snowLevel) {
							biome = Biomes.TAIGA_HILLS;
						} else {
							biome = Biomes.SNOWY_MOUNTAINS;
						}
					}
				}

				// set biome for block
				if (generator.getWorldSettings().includeDecayedNature)
					biome = Biomes.DESERT;
				biomes.setBiome(x, z, remapBiome(generator, lot, biome));
			}
		}
	}

	@Override
	public void preGenerateChunk(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, LinearBiomeContainer biomes,
			AbstractCachedYs blockYs) {
		OreProvider ores = generator.oreProvider;
		boolean surfaceCaves = isSurfaceCaveAt(chunk.sectionX, chunk.sectionZ);

		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = blockYs.getBlockY(x, z);

				// buildable?
				if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
							generator.streetLevel - 2, ores.subsurfaceMaterial, generator.streetLevel,
							ores.subsurfaceMaterial, false);

					// possibly buildable?
				} else if (y == generator.streetLevel) {
					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 3,
							ores.subsurfaceMaterial, y, ores.surfaceMaterial, generator.getWorldSettings().includeDecayedNature);

					// won't likely have a building
				} else {

					// on the beach
					if (y == generator.seaLevel) {
						generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
								y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial,
								generator.getWorldSettings().includeDecayedNature);
						// we are in the water! ...or are we?
					} else if (y < generator.seaLevel) {
						if (generator.getWorldSettings().includeDecayedNature)
							if (generator.getWorldSettings().includeAbovegroundFluids && y < generator.deepseaLevel)
								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial,
										ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y,
										ores.fluidSurfaceMaterial, generator.deepseaLevel, ores.fluidMaterial, false);
							else
								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial,
										ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y,
										ores.fluidSurfaceMaterial, true);
						else if (generator.getWorldSettings().includeAbovegroundFluids) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
									y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial,
									generator.seaLevel, ores.fluidMaterial, false);
						} else
							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
									y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial, false);

						// we are in the mountains
					} else {

						// regular trees only
						if (y < generator.treeLevel) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
									y - 3, ores.subsurfaceMaterial, y, ores.surfaceMaterial,
									generator.getWorldSettings().includeDecayedNature);

							// regular trees and some evergreen trees
						} else if (y < generator.evergreenLevel) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
									y - 2, ores.subsurfaceMaterial, y, ores.surfaceMaterial, surfaceCaves);

							// evergreen and some of fallen snow
						} else if (y < generator.snowLevel) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial,
									y - 1, ores.subsurfaceMaterial, y, ores.surfaceMaterial, surfaceCaves);

							// only snow up here!
						} else {
							if (generator.getWorldSettings().includeAbovegroundFluids && y > generator.snowLevel + 2)
								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial,
										ores.stratumMaterial, y - 1, ores.stratumMaterial, y, ores.fluidFrozenMaterial,
										surfaceCaves);
							else
								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial,
										ores.stratumMaterial, y - 1, ores.stratumMaterial, y, ores.stratumMaterial,
										surfaceCaves);
						}
					}
				}
			}
		}
	}

	@Override
	public void postGenerateChunk(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk,
			AbstractCachedYs blockYs) {

		// mines please
		lot.generateMines(generator, chunk);
	}

	@Override
	public void preGenerateBlocks(VocationCityWorldGenerator generator, PlatLot lot, RealBlocks chunk,
			AbstractCachedYs blockYs) {

		// put bones in?
		lot.generateBones(generator, chunk);
	}

	@Override
	public void postGenerateBlocks(VocationCityWorldGenerator generator, PlatLot lot, RealBlocks chunk,
			AbstractCachedYs blockYs) {

		// put ores in?
		lot.generateOres(generator, chunk);

		// do we do it or not?
		lot.generateMines(generator, chunk);
	}

	@Override
	public int getWorldHeight() {
		return height;
	}

	@Override
	public int getStreetLevel() {
		return seaLevel + 1;
	}

	@Override
	public int getSeaLevel() {
		return seaLevel;
	}

	@Override
	public int getLandRange() {
		return landRange;
	}

	@Override
	public int getSeaRange() {
		return seaRange;
	}

	@Override
	public int getConstuctMin() {
		return constructMin;
	}

	@Override
	public int getConstuctRange() {
		return constructRange;
	}

	@Override
	public double findPerciseY(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		double y = 0;

		// shape the noise
		double noise = noiseShape.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
		double feature = featureShape.noise(blockX, blockZ, featureFrequency, featureAmplitude, true);

		double land1 = seaLevel + (landShape1.noise(blockX, blockZ, landFrequency1, landAmplitude1, true) * landRange)
				+ (noise * noiseVerticalScale * landFactor1to2 + feature * featureVerticalScale * landFactor1to2)
				- landFlattening;
		double land2 = seaLevel
				+ (landShape2.noise(blockX, blockZ, landFrequency2, landAmplitude2, true)
				* (landRange / (double) landFactor1to2))
				+ (noise * noiseVerticalScale + feature * featureVerticalScale) - landFlattening;

		double landY = Math.max(land1, land2);
		double sea = seaShape.noise(blockX, blockZ, seaFrequency, seaAmplitude, true);

		// calculate the Ys
		double seaY = seaLevel + (sea * seaRange) + (noise * noiseVerticalScale) + seaFlattening;

		// land is below the sea
		if (landY <= seaLevel) {

			// if seabed is too high... then we might be buildable
			if (seaY >= seaLevel) {
				y = seaLevel + 1;

				// if we are too near the sea then we must be on the beach
				if (seaY <= seaLevel + 1) {
					y = seaLevel;
				}

				// if land is higher than the seabed use land to smooth
				// out under water base of the mountains
			} else if (landY >= seaY) {
				y = Math.min(seaLevel, landY + 1);

				// otherwise just take the sea bed as is
			} else {
				y = Math.min(seaLevel, seaY);
			}

			// must be a mountain then
		} else {
			y = Math.max(seaLevel, landY + 1);
		}

		// for real?
		if (!generator.getWorldSettings().includeMountains)
			y = Math.min(seaLevel + 1, y);
		if (!generator.getWorldSettings().includeSeas)
			y = Math.max(seaLevel + 1, y);

		// range validation
		return Math.min(height - 3, Math.max(y, 3));
	}

	@Override
	public boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale, chunkY * mineScale, chunkZ * mineScale + 0.5) > 0.0;
	}

	@Override
	public boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale + 0.5, chunkY * mineScale, chunkZ * mineScale) > 0.0;
	}

	@Override
	public boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale, chunkY * mineScale + 0.5, chunkZ * mineScale) > 0.0;
	}

	@Override
	public boolean notACave(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		if (generator.getWorldSettings().includeCaves) {
			double cave = caveShape.noise(blockX * caveScale, blockY * caveScaleY, blockZ * caveScale);
			return !(cave > caveThreshold || cave < -caveThreshold);
		} else
			return true;
	}

}
