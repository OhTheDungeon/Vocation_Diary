package cn.shadow.vacation_diary.dimension.structure.plats;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public abstract class PlatLot {

	// extremes
	protected final int chunkX;
	protected final int chunkZ;
	protected final AbstractCachedYs blockYs;

	//	protected Odds platmapOdds;
	protected Odds chunkOdds;

	// styling!
	public enum LotStyle {
		NATURE, STRUCTURE, ROAD, ROUNDABOUT
	}

	public LotStyle style;
	public boolean trulyIsolated;
	protected final boolean inACity;

	final Block pavementSidewalk;
	final Block dirtroadSidewalk;

	PlatLot(PlatMap platmap, int chunkX, int chunkZ) {
		super();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.style = LotStyle.NATURE;
		this.trulyIsolated = false;
		this.inACity = platmap.generator.getWorldSettings().inCityRange(chunkX, chunkZ);

		// pavement is 0, read in RoadLot
		// lines is 1, read in RoadLot
		pavementSidewalk = MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_Roads, 2, Blocks.STONE_SLAB);
		// dirt is 3, read in RoadLot
		dirtroadSidewalk = MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_Roads, 4, Blocks.GRASS_PATH);

		initializeDice(platmap, chunkX, chunkZ);

		// precalc the Ys
		blockYs = platmap.generator.shapeProvider.getCachedYs(platmap.generator, chunkX, chunkZ);
	}

	protected abstract long getConnectedKey();

	public abstract boolean makeConnected(PlatLot relative);

	public abstract boolean isConnectable(PlatLot relative);

	public abstract boolean isConnected(PlatLot relative);

	public abstract PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ);

	protected abstract void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ);

	protected abstract void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ);

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public Biome getChunkBiome() {
		return Biomes.PLAINS;
	}

	public boolean isPlaceableAt(VocationCityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.getWorldSettings().inCityRange(chunkX, chunkZ);
	}

	public PlatLot validateLot(PlatMap platmap, int platX, int platZ) {
		return null; // assume that we don't do anything
	}

	public RoadLot repaveLot(VocationCityWorldGenerator generator, PlatMap platmap) {
		return null; // same here
	}

	private void initializeDice(PlatMap platmap, int chunkX, int chunkZ) {

		// reset and pick up the dice
//		platmapOdds = platmap.getOddsGenerator();
		chunkOdds = platmap.getChunkOddsGenerator(chunkX, chunkZ);
	}

	protected int getSidewalkLevel(VocationCityWorldGenerator generator) {
		int result = generator.streetLevel;
		if (inACity)
			return result + 1;
		else
			return result;
	}

	protected Block getSidewalkMaterial() {
		if (inACity)
			return pavementSidewalk;
		else
			return dirtroadSidewalk;
	}

	protected int getBlockY(int x, int z) {
		return blockYs == null ? 0 : blockYs.getBlockY(x, z);
	}

	//	public double getAverageY() {
//		return blockYs == null ? 0 : blockYs.averageHeight;
//	}
//	
//	protected double getPerciseY(int x, int z) {
//		return blockYs == null ? 0 : blockYs.getPerciseY(x, z);
//	}
//	
	protected int getSurfaceAtY(int x, int z) {
		return getSurfaceAtY(x, 15 - x, z, 15 - z);
	}

	private int getSurfaceAtY(int x1, int x2, int z1, int z2) {
		int surfaceY = Math.min(getBlockY(x1, z1), getBlockY(x2, z1));
		surfaceY = Math.min(surfaceY, getBlockY(x1, z2));
		surfaceY = Math.min(surfaceY, getBlockY(x2, z2));
		return surfaceY;
	}

	public abstract int getBottomY(VocationCityWorldGenerator generator);

	public abstract int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z);

	// TODO: It seems that Spigot is generating the real blocks twice
	// (generateBlocks) for each time the blocks are initialized (generateChunk)
//	private static int totalNumberOfLotsOverGenerated = 0;
//	private static int totalNumberOfGeneratedChunks = 0;
	private int generateBlocksCallCountForThisLot = 0;

	protected void flattenLot(VocationCityWorldGenerator generator, AbstractBlocks chunk, int maxLayersToDo) {
		if (blockYs.getMaxHeight() > generator.streetLevel && blockYs.getMaxHeight() <= generator.streetLevel + maxLayersToDo) {
			chunk.airoutLayer(generator, generator.streetLevel + 1,
					Math.min(blockYs.getMaxHeight() - generator.streetLevel + 1, maxLayersToDo));
		}
	}

	public final void generateBiome(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, LinearBiomeContainer biomes,
			DataContext context, int platX, int platZ) {
		initializeDice(platmap, chunk.sectionX, chunk.sectionZ);
		generator.shapeProvider.generateBiome(generator, this, chunk, biomes, blockYs);
	}
	
	public final void decorate(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, LinearBiomeContainer biomes,
			DataContext context, int platX, int platZ) {
		initializeDice(platmap, chunk.sectionX, chunk.sectionZ);
		generator.shapeProvider.preGenerateChunk(generator, this, chunk, biomes, blockYs);
				
		generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
		generateBlocksCallCountForThisLot = 0;
		generator.shapeProvider.postGenerateChunk(generator, this, chunk, blockYs);
	}
	

	public void generateBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context,
			int platX, int platZ) {

		// TODO: This code makes sure that there is a single generateBlocks for each
		// generateChunk... and occasionally reports how often the problem occurred.
		generateBlocksCallCountForThisLot++;
		if (generateBlocksCallCountForThisLot > 1) {
//			totalNumberOfLotsOverGenerated++;
//			if (totalNumberOfLotsOverGenerated % 100 == 0)
//				generator.reportMessage(String.format("OVERGEN: At least %3.1f percentage of the lots have been over generated", 
//						((double)totalNumberOfLotsOverGenerated / (double)totalNumberOfGeneratedChunks) * 100));
			return;
		}
		initializeDice(platmap, chunk.sectionX, chunk.sectionZ);
		// what do we need to first?
	//!	generator.shapeProvider.preGenerateBlocks(generator, this, chunk, blockYs);
		// let the specialized platlot do it's thing
		generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		// polish things off
	//!	generator.shapeProvider.postGenerateBlocks(generator, this, chunk, blockYs);
	}

	protected void destroyLot(VocationCityWorldGenerator generator, int y1, int y2) {
		int x1 = chunkX * SupportBlocks.sectionBlockWidth;
		int z1 = chunkZ * SupportBlocks.sectionBlockWidth;
		generator.destroyWithin(x1, x1 + SupportBlocks.sectionBlockWidth, y1, y2, z1,
				z1 + SupportBlocks.sectionBlockWidth);
	}

	protected void destroyBuilding(VocationCityWorldGenerator generator, int y, int floors) {
		destroyLot(generator, y, y + DataContext.FloorHeight * (floors + 1));
	}

	private final static int lowestMineSegment = 16;

	public void generateMines(VocationCityWorldGenerator generator, InitialBlocks chunk) {

		// get shafted! (this builds down to keep the support poles happy)
		if (generator.getWorldSettings().includeMines)
			for (int y = (blockYs.getMinHeight() / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
				if (isShaftableLevel(generator, y))
					generateHorizontalMineLevel(generator, chunk, y);
			}
	}

	protected int findHighestShaftableLevel(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk) {

		// keep going down until we find what we are looking for
		for (int y = (blockYs.getMinHeight() / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
			if (isShaftableLevel(generator, y)
					&& generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ))
				return y + 7;
		}

		// nothing found
		return 0;
	}

	protected boolean isShaftableLevel(VocationCityWorldGenerator generator, int blockY) {
		return blockY >= lowestMineSegment && blockY < blockYs.getMinHeight() && blockYs.getMinHeight() > generator.seaLevel;
	}

	private void generateHorizontalMineLevel(VocationCityWorldGenerator generator, InitialBlocks chunk, int y) {
		int y1 = y + 6;
		int y2 = y1 + 1;

		// draw the shafts/walkways
		boolean pathFound = false;
		if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineShaftSpace(generator, chunk, 6, 10, y1, y1 + 4, 0, 6);
			generateMineNSSupport(chunk, 6, y2, 1);
			generateMineNSSupport(chunk, 6, y2, 4);
			generateMineShaftSpace(generator, chunk, 6, 10, y1, y1 + 4, 10, 16);
			generateMineNSSupport(chunk, 6, y2, 11);
			generateMineNSSupport(chunk, 6, y2, 14);
			pathFound = true;
		}
		if (generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineShaftSpace(generator, chunk, 0, 6, y1, y1 + 4, 6, 10);
			generateMineWESupport(chunk, 1, y2, 6);
			generateMineWESupport(chunk, 4, y2, 6);
			generateMineShaftSpace(generator, chunk, 10, 16, y1, y1 + 4, 6, 10);
			generateMineWESupport(chunk, 11, y2, 6);
			generateMineWESupport(chunk, 14, y2, 6);
			pathFound = true;
		}

		// draw the center bit
		if (pathFound)
			generateMineShaftSpace(generator, chunk, 6, 10, y1, y1 + 4, 6, 10);
	}

	private final static Block shaftBridge = Blocks.OAK_PLANKS;
	private final static Block shaftSupport = Blocks.OAK_FENCE;
	private final static Block shaftBeam = Blocks.OAK_PLANKS;

	private void generateMineShaftSpace(VocationCityWorldGenerator generator, InitialBlocks chunk, int x1, int x2, int y1,
			int y2, int z1, int z2) {
		chunk.setEmptyBlocks(x1, x2, y1, z1, z2, shaftBridge);
		chunk.airoutBlocks(generator, x1, x2, y1 + 1, y2, z1, z2);
	}

	private void generateMineNSSupport(InitialBlocks chunk, int x, int y, int z) {

		// on a bridge
		if (chunk.isType(x, y - 1, z, shaftBridge) && chunk.isType(x + 3, y - 1, z, shaftBridge)) {

			// place supports
			generateMineSupport(chunk, x, y - 1, z);
			generateMineSupport(chunk, x + 3, y - 1, z);

			// in a tunnel
		} else {
			chunk.setBlock(x, y, z, shaftSupport);
			chunk.setBlock(x, y + 1, z, shaftSupport);
			chunk.setBlock(x + 3, y, z, shaftSupport);
			chunk.setBlock(x + 3, y + 1, z, shaftSupport);
			chunk.setBlocks(x, x + 4, y + 2, z, z + 1, shaftBeam);
		}
	}

	private void generateMineWESupport(InitialBlocks chunk, int x, int y, int z) {
		// on a bridge
		if (chunk.isType(x, y - 1, z, shaftBridge) && chunk.isType(x, y - 1, z + 3, shaftBridge)) {

			// place supports
			generateMineSupport(chunk, x, y - 1, z);
			generateMineSupport(chunk, x, y - 1, z + 3);

			// in a tunnel
		} else {
			chunk.setBlock(x, y, z, shaftSupport);
			chunk.setBlock(x, y + 1, z, shaftSupport);
			chunk.setBlock(x, y, z + 3, shaftSupport);
			chunk.setBlock(x, y + 1, z + 3, shaftSupport);
			chunk.setBlocks(x, x + 1, y + 2, z, z + 4, shaftBeam);
		}
	}

	private void generateMineSupport(InitialBlocks chunk, int x, int y, int z) {
		int aboveSupport = chunk.findLastEmptyAbove(x, y, z, blockYs.getMaxHeight());
		if (aboveSupport < blockYs.getMaxHeight())
			chunk.setBlocks(x, y + 1, aboveSupport + 1, z, shaftSupport);
	}

	public void generateMines(VocationCityWorldGenerator generator, SupportBlocks chunk) {

		// get shafted!
		if (generator.getWorldSettings().includeMines)
			for (int y = lowestMineSegment; y + 16 < blockYs.getMinHeight(); y += 16) {
				if (isShaftableLevel(generator, y))
					generateVerticalMineLevel(generator, chunk, y);
			}
	}

	private void generateVerticalMineLevel(VocationCityWorldGenerator generator, SupportBlocks chunk, int y) {
		int y1 = y + 6;
		boolean stairsFound = false;

		// going down?
		if (isShaftableLevel(generator, y - 16)) {
			if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ)
					&& generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y - 16, chunk.sectionZ)) {

				// draw the going down bit
				placeMineStairBase(chunk, 10, y1, 15);
				placeMineStairStep(chunk, 10, y1, 14, Direction.SOUTH, Direction.NORTH);
				placeMineStairStep(chunk, 10, y1 - 1, 13, Direction.SOUTH, Direction.NORTH);
				placeMineStairStep(chunk, 10, y1 - 2, 12, Direction.SOUTH, Direction.NORTH);
				placeMineStairStep(chunk, 10, y1 - 3, 11, Direction.SOUTH, Direction.NORTH);
				placeMineStairStep(chunk, 10, y1 - 4, 10, Direction.SOUTH, Direction.NORTH);
				placeMineStairStep(chunk, 10, y1 - 5, 9, Direction.SOUTH, Direction.NORTH);
				placeMineStairStep(chunk, 10, y1 - 6, 8, Direction.SOUTH, Direction.NORTH);
				stairsFound = true;
			}

			if (!stairsFound && generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ)
					&& generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y - 16, chunk.sectionZ)) {

				// draw the going down bit
				placeMineStairBase(chunk, 15, y1, 10);
				placeMineStairStep(chunk, 14, y1, 10, Direction.EAST, Direction.WEST);
				placeMineStairStep(chunk, 13, y1 - 1, 10, Direction.EAST, Direction.WEST);
				placeMineStairStep(chunk, 12, y1 - 2, 10, Direction.EAST, Direction.WEST);
				placeMineStairStep(chunk, 11, y1 - 3, 10, Direction.EAST, Direction.WEST);
				placeMineStairStep(chunk, 10, y1 - 4, 10, Direction.EAST, Direction.WEST);
				placeMineStairStep(chunk, 9, y1 - 5, 10, Direction.EAST, Direction.WEST);
				placeMineStairStep(chunk, 8, y1 - 6, 10, Direction.EAST, Direction.WEST);
			}
		}

		// reset the stairs flag
		stairsFound = false;

		// going up?
		if (isShaftableLevel(generator, y + 32)) {
			if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ)
					&& generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y + 16, chunk.sectionZ)) {

				// draw the going up bit
				placeMineStairBase(chunk, 5, y1, 15);
				placeMineStairStep(chunk, 5, y1 + 1, 14, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 2, 13, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 3, 12, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 4, 11, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 5, 10, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 6, 9, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 7, 8, Direction.NORTH, Direction.SOUTH);
				placeMineStairStep(chunk, 5, y1 + 8, 7, Direction.NORTH, Direction.SOUTH);
				placeMineStairBase(chunk, 5, y1 + 8, 6);
				placeMineStairBase(chunk, 6, y1 + 8, 6);
				placeMineStairBase(chunk, 7, y1 + 8, 6);
				placeMineStairBase(chunk, 8, y1 + 8, 6);
				placeMineStairBase(chunk, 9, y1 + 8, 6);
				placeMineStairBase(chunk, 10, y1 + 8, 6);
				placeMineStairStep(chunk, 10, y1 + 9, 7, Direction.SOUTH, Direction.NORTH);

				generateMineSupport(chunk, 6, y1 + 7, 7);
				generateMineSupport(chunk, 9, y1 + 7, 7);

				stairsFound = true;
			}

			if (!stairsFound && generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ)
					&& generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y + 16, chunk.sectionZ)) {

				// draw the going up bit
				placeMineStairBase(chunk, 15, y1, 5);
				placeMineStairStep(chunk, 14, y1 + 1, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 13, y1 + 2, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 12, y1 + 3, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 11, y1 + 4, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 10, y1 + 5, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 9, y1 + 6, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 8, y1 + 7, 5, Direction.WEST, Direction.EAST);
				placeMineStairStep(chunk, 7, y1 + 8, 5, Direction.WEST, Direction.EAST);
				placeMineStairBase(chunk, 6, y1 + 8, 5);
				placeMineStairBase(chunk, 6, y1 + 8, 6);
				placeMineStairBase(chunk, 6, y1 + 8, 7);
				placeMineStairBase(chunk, 6, y1 + 8, 8);
				placeMineStairBase(chunk, 6, y1 + 8, 9);
				placeMineStairBase(chunk, 6, y1 + 8, 10);
				placeMineStairStep(chunk, 7, y1 + 9, 10, Direction.EAST, Direction.WEST);

				generateMineSupport(chunk, 7, y1 + 7, 6);
				generateMineSupport(chunk, 7, y1 + 7, 9);
			}
		}

		// make the ceiling pretty
		boolean pathFound = false;
		if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineCeiling(chunk, 6, 10, y1 + 3, 0, 6);
			generateMineCeiling(chunk, 6, 10, y1 + 3, 10, 16);

			generateMineAlcove(generator, chunk, 4, y1, 2, 4, 2);
			generateMineAlcove(generator, chunk, 10, y1, 2, 11, 3);

			pathFound = true;
		}
		if (generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineCeiling(chunk, 0, 6, y1 + 3, 6, 10);
			generateMineCeiling(chunk, 10, 16, y1 + 3, 6, 10);

			generateMineAlcove(generator, chunk, 2, y1, 4, 2, 4);
			generateMineAlcove(generator, chunk, 2, y1, 10, 3, 11);

			pathFound = true;
		}

		// draw the center bit
		if (pathFound)
			generateMineCeiling(chunk, 6, 10, y1 + 3, 6, 10);
	}

	private void generateMineAlcove(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, int prizeX,
			int prizeZ) {
		if (chunkOdds.playOdds(generator.getWorldSettings().oddsOfAlcoveInMines)) {
			if (!chunk.isEmpty(x, y, z) && !chunk.isEmpty(x + 1, y, z) && !chunk.isEmpty(x, y, z + 1)
					&& !chunk.isEmpty(x + 1, y, z + 1)) {
				chunk.setBlocks(x, x + 2, y + 1, y + 4, z, z + 2, Blocks.AIR);
				generateMineCeiling(chunk, x, x + 2, y + 3, z, z + 2);
				if (chunkOdds.flipCoin())
					generateMineTrick(generator, chunk, prizeX, y + 1, prizeZ);
				else
					generateMineTreat(generator, chunk, prizeX, y + 1, prizeZ);
			}
		}
	}

	protected void generateMineCeiling(SupportBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (!chunk.isEmpty(x, y + 1, z) && chunk.isEmpty(x, y, z))
					if (chunkOdds.flipCoin())
						chunk.setBlock(x, y, z, Blocks.COBBLESTONE_SLAB, SlabType.TOP);
					else
						chunk.setBlock(x, y, z, Blocks.COBBLESTONE);
			}
		}
	}

	protected void generateMineFloor(SupportBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (!chunk.isEmpty(x, y, z))
					if (chunkOdds.flipCoin())
						chunk.setBlock(x, y, z, Blocks.COBBLESTONE_SLAB, SlabType.BOTTOM);
					else
						chunk.setBlock(x, y, z, Blocks.COBBLESTONE);
			}
		}
	}

	private void generateMineSupport(SupportBlocks chunk, int x, int y, int z) {
		int aboveSupport = chunk.findLastEmptyAbove(x, y, z, blockYs.getMaxHeight());
		if (aboveSupport < blockYs.getMaxHeight())
			chunk.setBlocks(x, y + 1, aboveSupport + 1, z, Blocks.OAK_FENCE);
	}

	private void placeMineStairBase(SupportBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, y + 1, y + 4, z, Blocks.AIR);
		chunk.setEmptyBlock(x, y, z, Blocks.OAK_PLANKS);
	}

	private void placeMineStairStep(SupportBlocks chunk, int x, int y, int z, Direction direction,
			Direction flipDirection) {
		chunk.setBlocks(x, y + 1, y + 4, z, Blocks.AIR);
		chunk.setBlock(x, y, z, Blocks.OAK_STAIRS, direction);
		if (chunk.isEmpty(x, y - 1, z))
			chunk.setBlock(x, y - 1, z, Blocks.OAK_STAIRS, flipDirection, Half.TOP);
	}

	private void generateMineTreat(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {

		// cool stuff?
		if (generator.getWorldSettings().treasuresInMines && chunkOdds.playOdds(generator.getWorldSettings().oddsOfTreasureInMines)) {
			chunk.setChest(generator, x, y, z, chunkOdds, generator.lootProvider, LootLocation.MINE);
		}
	}

	private void generateMineTrick(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		// not so cool stuff?
		generator.spawnProvider.setSpawnOrSpawner(generator, chunk, chunkOdds, x, y, z,
				generator.getWorldSettings().spawnersInMines, generator.spawnProvider.itemsEntities_Mine);
	}

	public boolean isValidStrataY(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return true;
	}

	protected boolean isValidWithBones() {
		return true;
	}

	public void generateBones(VocationCityWorldGenerator generator, SupportBlocks chunk) {

		// fossils?
		if (isValidWithBones() && generator.getWorldSettings().includeBones && chunkOdds.playOdds(Odds.oddsTremendouslyUnlikely))
			generator.thingProvider.generateBones(generator, this, chunk, blockYs, chunkOdds);
	}

	public void generateOres(VocationCityWorldGenerator generator, SupportBlocks chunk) {

		// shape the world
		if (generator.getWorldSettings().includeOres || generator.getWorldSettings().includeUndergroundFluids)
			generator.oreProvider.sprinkleOres(generator, this, chunk, blockYs, chunkOdds);
	}

	// TODO move this logic to SurroundingLots, add to it the ability to produce
	// SurroundingHeights and SurroundingDepths
	public PlatLot[][] getNeighborPlatLots(PlatMap platmap, int platX, int platZ, boolean onlyConnectedNeighbors) {
		PlatLot[][] miniPlatMap = new PlatLot[3][3];

		// populate the results
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {

				// which platchunk are we looking at?
				int atX = platX + x - 1;
				int atZ = platZ + z - 1;

				// is it in bounds?
				if (!(atX < 0 || atX > PlatMap.Width - 1 || atZ < 0 || atZ > PlatMap.Width - 1)) {
					PlatLot relative = platmap.getLot(atX, atZ);

					if (!onlyConnectedNeighbors || isConnected(relative)) {
						miniPlatMap[x][z] = relative;
					}
				}
			}
		}

		return miniPlatMap;
	}

	public void generateSurface(VocationCityWorldGenerator generator, RealBlocks chunk, boolean includeTrees) {
		generateSurface(generator, chunk, 0, includeTrees);
	}

	protected void generateSurface(VocationCityWorldGenerator generator, RealBlocks chunk, int addTo, boolean includeTrees) {

		// plant grass or snow... sometimes we want the sprinker to start a little
		// higher
		generator.surfaceProvider.generateSurface(generator, this, chunk, blockYs, addTo, includeTrees);
	}

	protected boolean clearAir(VocationCityWorldGenerator generator) {
		return generator.shapeProvider.clearAtmosphere(generator);
	}

//	protected Material getAirMaterial(CityWorldGenerator generator, int y) {
//		if (getTopY(generator) <= y)
//			return Material.AIR;
//		else
//			return generator.shapeProvider.findAtmosphereMaterialAt(generator, y);
//	}
}
