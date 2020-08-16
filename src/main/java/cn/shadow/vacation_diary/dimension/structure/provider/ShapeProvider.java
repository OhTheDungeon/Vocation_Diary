package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.context.RoadContext;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.TraditionalCachedYs;
import cn.shadow.vacation_diary.util.noise.NoiseGenerator;
import cn.shadow.vacation_diary.util.noise.SimplexNoiseGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;

public abstract class ShapeProvider extends Provider {

	public abstract int getWorldHeight();

	public abstract int getStreetLevel();

	public abstract int getSeaLevel();

	public abstract int getLandRange();

	public abstract int getSeaRange();

	public abstract int getConstuctMin();

	public abstract int getConstuctRange();

	public abstract double findPerciseY(VocationCityWorldGenerator generator, int blockX, int blockZ);
	
	public abstract void generateBiome(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk,
			LinearBiomeContainer biomes, AbstractCachedYs blockYs);

	public abstract void preGenerateChunk(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk,
			LinearBiomeContainer biomes, AbstractCachedYs blockYs);

	public abstract void postGenerateChunk(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk,
			AbstractCachedYs blockYs);

	public abstract void preGenerateBlocks(VocationCityWorldGenerator generator, PlatLot lot, RealBlocks chunk,
			AbstractCachedYs blockYs);

	public abstract void postGenerateBlocks(VocationCityWorldGenerator generator, PlatLot lot, RealBlocks chunk,
			AbstractCachedYs blockYs);

	protected abstract Biome remapBiome(VocationCityWorldGenerator generator, PlatLot lot, Biome biome);

	protected abstract void allocateContexts(VocationCityWorldGenerator generator);

	public abstract String getCollectionName();

	protected abstract void validateLots(VocationCityWorldGenerator generator, PlatMap platmap);

	public abstract DataContext getContext(int originX, int originZ);

	protected abstract DataContext getContext(PlatMap platmap);

	public AbstractCachedYs getCachedYs(VocationCityWorldGenerator generator, int chunkX, int chunkZ) {
		return new TraditionalCachedYs(generator, chunkX, chunkZ);
	}

	public void populateLots(VocationCityWorldGenerator generator, PlatMap platmap) {
		try {
			allocateContexts(generator);

			// assume everything is natural for the moment
			platmap.context = natureContext;
			natureContext.populateMap(generator, platmap);
			natureContext.validateMap(generator, platmap);

			// place and validate the roads
			if (generator.getWorldSettings().includeRoads) {
				platmap.context = getContext(platmap);
				platmap.populateRoads(); // this will see the platmap's context as natural since it hasn't been re-set
				// yet, see below
				platmap.validateRoads();

				// place the buildings
				if (generator.getWorldSettings().includeBuildings) {

					// recalculate the context based on the "natural-ness" of the platmap
//					platmap.context = getContext(platmap);
					platmap.context.populateMap(generator, platmap);
					platmap.context.validateMap(generator, platmap);
				}

				// one last check
				validateLots(generator, platmap);
			}
		} catch (Exception e) {
			generator.reportException("ShapeProvider.populateLots FAILED", e);

		}
	}

	boolean contextInitialized = false;
	DataContext natureContext;
	RoadContext roadContext;

	private final SimplexNoiseGenerator macroShape;
	private final SimplexNoiseGenerator microShape;
	final Odds odds;

	public int getStructureLevel() {
		return getStreetLevel();
	}

	public int findBlockY(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseY(generator, blockX, blockZ));
	}

	public int findGroundY(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		return findBlockY(generator, blockX, blockZ);
	}

	public double findPerciseFloodY(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		return getSeaLevel();
	}

	public int findFloodY(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		return getSeaLevel();
	}

	public int findHighestFloodY(VocationCityWorldGenerator generator) {
		return getSeaLevel();
	}

	public int findLowestFloodY(VocationCityWorldGenerator generator) {
		return getSeaLevel();
	}

//	public byte findAtmosphereIdAt(WorldGenerator generator, int blockY) {
//		return BlackMagic.airId;
//	}

	public boolean clearAtmosphere(VocationCityWorldGenerator generator) {
		return true;
	}

	public Block findAtmosphereMaterialAt(VocationCityWorldGenerator generator, int blockY) {
		return Blocks.AIR;
	}

//	public byte findGroundCoverIdAt(WorldGenerator generator, int blockY) {
//		return BlackMagic.airId;
//	}

	Block findGroundCoverMaterialAt(VocationCityWorldGenerator generator, int blockY) {
		return Blocks.AIR;
	}

	public PlatLot createNaturalLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return natureContext.createNaturalLot(generator, platmap, x, z);
	}

	public PlatLot createRoadLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart,
			PlatLot oldLot) {
		return roadContext.createRoadLot(generator, platmap, x, z, roundaboutPart, oldLot);
	}

	public PlatLot createRoundaboutStatueLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return roadContext.createRoundaboutStatueLot(generator, platmap, x, z);
	}

	ShapeProvider(VocationCityWorldGenerator generator, Odds odds) {
		super();
		this.odds = odds;
		long seed = generator.getWorldSeed();

		macroShape = new SimplexNoiseGenerator(seed + 2);
		microShape = new SimplexNoiseGenerator(seed + 3);

	}

	// Based on work contributed by drew-bahrue
	// (https://github.com/echurchill/CityWorld/pull/2)
	public static ShapeProvider loadProvider(VocationCityWorldGenerator generator, Odds odds) {

		ShapeProvider provider = null;

		switch (generator.worldStyle) {
//		case FLOATING:
//			provider = new ShapeProvider_Floating(generator, odds);
//			break;
//		case FLOODED:
//			provider = new ShapeProvider_Flooded(generator, odds);
//			break;
//		case SANDDUNES:
//			provider = new ShapeProvider_SandDunes(generator, odds);
//			break;
//		case SNOWDUNES:
//			provider = new ShapeProvider_SnowDunes(generator, odds);
//			break;
//		case ASTRAL:
//			provider = new ShapeProvider_Astral(generator, odds);
//			break;
//		case MAZE:
//			provider = new ShapeProvider_Maze(generator, odds);
//			break;
//		case NATURE:
//			provider = new ShapeProvider_Nature(generator, odds);
//			break;
//		case METRO:
//			provider = new ShapeProvider_Metro(generator, odds);
//			break;
//		case DESTROYED:
//		case SPARSE:
		case NORMAL:
			provider = new ShapeProvider_Normal(generator, odds);
			break;
		}

		return provider;
	}

	private static final int bottomOfWorld = 0;

	void actualGenerateStratas(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z,
			Block substratumMaterial, Block stratumMaterial, int stratumY, Block subsurfaceMaterial,
			int subsurfaceY, Block surfaceMaterial, boolean surfaceCaves) {

		// make the base
		chunk.setBlock(x, bottomOfWorld, z, substratumMaterial);
		chunk.setBlock(x, bottomOfWorld + 1, z, stratumMaterial);

		// compute the world block coordinates
		int blockX = chunk.sectionX * chunk.width + x;
		int blockZ = chunk.sectionZ * chunk.width + z;

		// stony bits
		for (int y = bottomOfWorld + 2; y < stratumY; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ)
					&& generator.shapeProvider.notACave(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, stratumMaterial);
			else if (y <= OreProvider.lavaFieldLevel && generator.getWorldSettings().includeLavaFields)
				chunk.setBlock(x, y, z, Blocks.LAVA);

		// aggregate bits
		for (int y = stratumY; y < subsurfaceY - 1; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ)
					&& (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, y, blockZ)))
				chunk.setBlock(x, y, z, subsurfaceMaterial);

		// icing for the cake
		if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, subsurfaceY, blockZ)) {
			if (lot.isValidStrataY(generator, blockX, subsurfaceY - 1, blockZ))
				chunk.setBlock(x, subsurfaceY - 1, z, subsurfaceMaterial);
			if (lot.isValidStrataY(generator, blockX, subsurfaceY, blockZ))
				chunk.setBlock(x, subsurfaceY, z, surfaceMaterial);
		}
	}

	void generateStratas(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z,
			Block substratumMaterial, Block stratumMaterial, int stratumY, Block subsurfaceMaterial,
			int subsurfaceY, Block surfaceMaterial, boolean surfaceCaves) {

		// a little crust please?
		actualGenerateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY,
				subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);
	}

	void generateStratas(VocationCityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z,
			Block substratumMaterial, Block stratumMaterial, int stratumY, Block subsurfaceMaterial,
			int subsurfaceY, Block surfaceMaterial, int coverY, Block coverMaterial, boolean surfaceCaves) {

		// a little crust please?
		actualGenerateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY,
				subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);

		// cover it up
		for (int y = subsurfaceY + 1; y <= coverY; y++)
			chunk.setBlock(x, y, z, coverMaterial);
	}

	// TODO refactor these over to UndergroundProvider (which should include
	// PlatLot's mines generator code)
	// TODO rename these to ifSoAndSo
	public abstract boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ);

	public abstract boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ);

	public abstract boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ);

	// TODO refactor this so that it is a positive (maybe ifCave) instead of a
	// negative
	protected abstract boolean notACave(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ);

	// macro slots
	private final static int macroRandomGeneratorSlot = 0;
	private final static int macroNSBridgeSlot = 1;

	// micro slots
	private final static int microRandomGeneratorSlot = 0;
	private final static int microRoundaboutSlot = 1;
	private final static int microSurfaceCaveSlot = 2;
	private final static int microIsolatedLotSlot = 3;
	private final static int microIsolatedConstructSlot = 4;

	private final double macroScale = 1.0 / 384.0;
	private final double microScale = 2.0;

	private double getMicroNoiseAt(double x, double z, int a) {
		return microShape.noise(x * microScale, z * microScale, a);
	}

	private double getMacroNoiseAt(double x, double z, int a) {
		return macroShape.noise(x * macroScale, z * macroScale, a);
	}

//	private int macroValueAt(double chunkX, double chunkZ, int slot, int scale) {
//		return NoiseGenerator.floor(macroScaleAt(chunkX, chunkZ, slot) * scale);
//	}
//
//	private int microValueAt(double chunkX, double chunkZ, int slot, int scale) {
//		return NoiseGenerator.floor(microScaleAt(chunkX, chunkZ, slot) * scale);
//	}
//
//	private double macroScaleAt(double chunkX, double chunkZ, int slot) {
//		return (getMacroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
//	}

	private double microScaleAt(double chunkX, double chunkZ, int slot) {
		return (getMicroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
	}

	private boolean macroBooleanAt(double chunkX, double chunkZ, int slot) {
		return getMacroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}

	private boolean microBooleanAt(double chunkX, double chunkZ, int slot) {
		return getMicroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}

	public Odds getMicroOddsGeneratorAt(int x, int z) {
		return new Odds((long) (getMicroNoiseAt(x, z, microRandomGeneratorSlot) * Long.MAX_VALUE));
	}

	public Odds getMacroOddsGeneratorAt(int x, int z) {
		return new Odds((long) (getMacroNoiseAt(x, z, macroRandomGeneratorSlot) * Long.MAX_VALUE));
	}

	public boolean getBridgePolarityAt(double chunkX, double chunkZ) {
		return macroBooleanAt(chunkX, chunkZ, macroNSBridgeSlot);
	}

	boolean isSurfaceCaveAt(double chunkX, double chunkZ) {
		return microBooleanAt(chunkX, chunkZ, microSurfaceCaveSlot);
	}

	public boolean isRoundaboutAt(double chunkX, double chunkZ, double oddsOfRoundabouts) {
		return microScaleAt(chunkX, chunkZ, microRoundaboutSlot) < oddsOfRoundabouts;
	}

	public boolean isIsolatedConstructAt(double chunkX, double chunkZ, double oddsOfIsolatedConstruct) {
		return microScaleAt(chunkX, chunkZ, microIsolatedConstructSlot) < oddsOfIsolatedConstruct;
	}

	public boolean isIsolatedLotAt(double chunkX, double chunkZ, double oddsOfIsolatedLots) {
		return microScaleAt(chunkX, chunkZ, microIsolatedLotSlot) < oddsOfIsolatedLots;
	}

}
