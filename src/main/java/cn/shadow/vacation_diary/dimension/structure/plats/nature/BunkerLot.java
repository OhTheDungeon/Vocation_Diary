package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConnectedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.RoadLot;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Mapper;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;

public class BunkerLot extends ConnectedLot {

	private final static int FloorHeight = DataContext.FloorHeight;

	// these MUST be given in chunk segment units (currently 16)
	private final static int bunkerSegment = 16;
	private final static int bunkerBuffer = bunkerSegment;
	private final static int bunkerBelowStreet = bunkerSegment;
	private final static int bunkerMinHeight = bunkerSegment * 2;
	private final static int bunkerMaxHeight = bunkerSegment * 6;

	//	private boolean firstOne = false;
	final int bottomOfBunker;
	final int topOfBunker;

	public enum BunkerType {
		ENTRY, PYRAMID, TANK, QUAD, RECALL, BALLSY, FLOORED, GROWING, SAUCER, ROAD
	} // MISSILE, FARM, VENT

	private int bilgeType;
	private final BunkerType buildingType;

	public BunkerLot(PlatMap platmap, int chunkX, int chunkZ, boolean firstOne) {
		super(platmap, chunkX, chunkZ);

//		this.firstOne = firstOne;

		// been here?
		bottomOfBunker = calcSegmentOrigin(platmap.generator.streetLevel) - bunkerBelowStreet;
		topOfBunker = calcBunkerCeiling(platmap.generator);

//		platmap.generator.reportMessage("minHeight = " + minHeight + 
//			" calcSegmentOrigin(minHeight) = " + calcSegmentOrigin(minHeight) +
//			" calcSegmentOrigin(minHeight) - bunkerBuffer = " + (calcSegmentOrigin(minHeight) - bunkerBuffer) +
//			" -> topOfBunker = " + topOfBunker);

		// initial rolls, using two different odds engines
		buildingType = getRandomBunkerType(chunkOdds, firstOne);
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BunkerLot(platmap, chunkX, chunkZ, false);
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof BunkerLot) {
			BunkerLot bunker = (BunkerLot) relative;

			bilgeType = bunker.bilgeType;
		}
		return result;
	}

	@Override
	public long getConnectedKey() {
		return connectedkey = 135792468; // all bunkers share this key
	}

	@Override
	public boolean isValidStrataY(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return bunkerIsValidStrataY(generator, blockX, blockY, blockZ, bottomOfBunker, topOfBunker);
	}

	@Override
	protected boolean isShaftableLevel(VocationCityWorldGenerator generator, int blockY) {
		return isBunkerShaftable() && bunkerIsShaftableLevel(generator, blockY, bottomOfBunker, topOfBunker)
				&& super.isShaftableLevel(generator, blockY);
	}

	private boolean isBunkerShaftable() {
		return buildingType != BunkerType.ENTRY && buildingType != BunkerType.SAUCER;
	}

	public static boolean bunkerIsValidStrataY(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ,
			int bottomOfBunker, int topOfBunker) {
		return blockY < bottomOfBunker || blockY >= topOfBunker;
	}

	public static boolean bunkerIsShaftableLevel(VocationCityWorldGenerator generator, int blockY, int bottomOfBunker,
			int topOfBunker) {

		return (blockY < bottomOfBunker - bunkerBuffer || blockY > topOfBunker - bunkerSegment - bunkerBuffer);
	}

	private static int calcSegmentOrigin(int y) {
		return (y / bunkerSegment) * bunkerSegment;
	}

	public static int calcBunkerMinHeight(VocationCityWorldGenerator generator) {
		return calcSegmentOrigin(generator.streetLevel) + bunkerMinHeight - bunkerBelowStreet + bunkerBuffer;
	}

	private static int calcBunkerMaxHeight(VocationCityWorldGenerator generator) {
		return calcSegmentOrigin(generator.streetLevel) + bunkerMaxHeight - bunkerBelowStreet + bunkerBuffer;
	}

	private int calcBunkerCeiling(VocationCityWorldGenerator generator) {
		return Math.min(calcBunkerMaxHeight(generator), calcSegmentOrigin(blockYs.getMinHeight()) - bunkerBuffer);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return bottomOfBunker;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return topOfBunker;
	}

	@Override
	protected boolean isValidWithBones() {
		return false;
	}

	@Override
	public RoadLot repaveLot(VocationCityWorldGenerator generator, PlatMap platmap) {
		return new RoadThroughBunkerLot(platmap, chunkX, chunkZ, generator.connectedKeyForPavedRoads, false, this);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		if (buildingType == BunkerType.ENTRY)
			generator.reportLocation("Bunker", chunk);

		// where is the surface?
//		int surfaceY = getSurfaceAtY(6, 6);
//		generator.reportMessage("SurfaceY = " + surfaceY + " TopOfBunker = " + topOfBunker + " MinHeight = " + minHeight);
//		generator.reportMessage("TopOfBunker = " + topOfBunker + " MinHeight = " + minHeight);

		// do it!
		int addTo = generateBunker(generator, platmap, chunk, chunkOdds, context, platX, platZ, blockYs, bottomOfBunker,
				topOfBunker, buildingType);

		// add some surface plus any height required by whatever got created
		generateSurface(generator, chunk, addTo, true);
	}

	private static class BunkerMaterials {
		Block pillar = Blocks.QUARTZ_PILLAR;
		Block support = Blocks.QUARTZ_BLOCK;
		Block platform = Blocks.QUARTZ_BLOCK;
		Block crosswalk = Blocks.QUARTZ_BLOCK;
		Block building = Blocks.WHITE_TERRACOTTA;
		Block bilge = Blocks.AIR;

		final Block railing = Blocks.IRON_BARS;
		final Block window = Blocks.GLASS;

		BunkerMaterials(VocationCityWorldGenerator generator, Odds odds) {
			platform = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerPlatforms, odds, platform);
			crosswalk = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerPlatforms, odds, crosswalk);
			support = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerPlatforms, odds, support);
			pillar = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerPlatforms, odds, pillar);
			building = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerBuildings, odds, building);
			bilge = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerBilge, odds, bilge);
		}
	}

	private static BunkerMaterials materials;

	private static void loadMaterials(VocationCityWorldGenerator generator, Odds odds) {
		// make sure we know what we are making
		if (materials == null)
			materials = new BunkerMaterials(generator, odds);
	}

	static int generateBunker(VocationCityWorldGenerator generator, PlatMap platmap, SupportBlocks chunk, Odds odds,
			DataContext context, int platX, int platZ, AbstractCachedYs blockYs, int bottomOfBunker, int topOfBunker,
			BunkerType buildingType) {

		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		// precalculate
		int yBottom = bottomOfBunker;// calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet;
		int yTop4 = topOfBunker;// calcBunkerCeiling(generator);
		int yTop3 = yTop4 - 2;
		int yTop2 = yTop4 - bunkerSegment;
		int yTop1 = yTop2 - 2;
		int yPlatform = calcSegmentOrigin(yBottom) + 6;
//		int yRange = (yTop2 - yPlatform) / 3; //TODO: this sometimes returns 0 - level-seed=-34393919603997097
		int yRange = Math.max(1, (yTop2 - yPlatform) / 3);
		int yPlatformTop = Math.min(Math.max(yPlatform + bunkerSegment * 2, odds.getRandomInt(yRange) + yRange * 2),
				yTop1);

		// bottom
		chunk.setLayer(yBottom, materials.support);

		// clear out stuff?
		if (materials.bilge != Blocks.AIR)
			chunk.setLayer(yBottom + 1, materials.bilge);

		// hold up buildings
		generateSupport(chunk, odds, context, 3, yBottom + 1, 3);
		generateSupport(chunk, odds, context, 3, yBottom + 1, 10);
		generateSupport(chunk, odds, context, 10, yBottom + 1, 3);
		generateSupport(chunk, odds, context, 10, yBottom + 1, 10);

		// vertical beams
		chunk.setBlocks(0, 2, yBottom + 1, yTop3, 0, 1, materials.pillar);
		chunk.setBlocks(0, yBottom + 1, yTop3, 1, materials.pillar);
		chunk.setBlocks(0, 2, yBottom + 1, yTop3, 15, 16, materials.pillar);
		chunk.setBlocks(0, yBottom + 1, yTop3, 14, materials.pillar);
		chunk.setBlocks(14, 16, yBottom + 1, yTop3, 0, 1, materials.pillar);
		chunk.setBlocks(15, yBottom + 1, yTop3, 1, materials.pillar);
		chunk.setBlocks(14, 16, yBottom + 1, yTop3, 15, 16, materials.pillar);
		chunk.setBlocks(15, yBottom + 1, yTop3, 14, materials.pillar);

		// near top cross beams
		chunk.setBlocks(0, 16, yTop1, yTop2, 0, 2, materials.support);
		chunk.setBlocks(0, 16, yTop1, yTop2, 14, 16, materials.support);
		chunk.setBlocks(0, 2, yTop1, yTop2, 2, 14, materials.support);
		chunk.setBlocks(14, 16, yTop1, yTop2, 2, 14, materials.support);

		// top cross beams
		chunk.setBlocks(0, 16, yTop3, yTop4, 0, 2, materials.support);
		chunk.setBlocks(0, 16, yTop3, yTop4, 14, 16, materials.support);
		chunk.setBlocks(0, 2, yTop3, yTop4, 2, 14, materials.support);
		chunk.setBlocks(14, 16, yTop3, yTop4, 2, 14, materials.support);

//		// clear out space between the top cross beams
//		chunk.setBlocks(2, 14, yTop3, yTop4, 2, 14, airId);

		// draw platform
		chunk.setBlocks(2, 14, yPlatform, 2, 14, materials.platform);

		// draw crosswalks
		chunk.setBlocks(7, 9, yPlatform, 0, 2, materials.crosswalk);
		chunk.setBlocks(0, 2, yPlatform, 7, 9, materials.crosswalk);
		chunk.setBlocks(7, 9, yPlatform, 14, 16, materials.crosswalk);
		chunk.setBlocks(14, 16, yPlatform, 7, 9, materials.crosswalk);

		// draw railing
		chunk.setBlock(2, yPlatform + 1, 2, materials.railing, Direction.EAST, Direction.SOUTH);
		chunk.setBlocks(3, 6, yPlatform + 1, 2, 3, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlock(6, yPlatform + 1, 2, materials.railing, Direction.WEST, Direction.NORTH);
		chunk.setBlock(9, yPlatform + 1, 2, materials.railing, Direction.EAST, Direction.NORTH);
		chunk.setBlocks(10, 13, yPlatform + 1, 2, 3, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlock(13, yPlatform + 1, 2, materials.railing, Direction.WEST, Direction.SOUTH);
		chunk.setBlock(2, yPlatform + 1, 13, materials.railing, Direction.EAST, Direction.NORTH);
		chunk.setBlocks(3, 6, yPlatform + 1, 13, 14, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlock(6, yPlatform + 1, 13, materials.railing, Direction.WEST, Direction.SOUTH);
		chunk.setBlock(9, yPlatform + 1, 13, materials.railing, Direction.EAST, Direction.SOUTH);
		chunk.setBlocks(10, 13, yPlatform + 1, 13, 14, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlock(13, yPlatform + 1, 13, materials.railing, Direction.WEST, Direction.NORTH);

		chunk.setBlocks(2, 3, yPlatform + 1, 3, 6, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(2, yPlatform + 1, 6, materials.railing, Direction.NORTH, Direction.WEST);
		chunk.setBlocks(13, 14, yPlatform + 1, 3, 6, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(13, yPlatform + 1, 6, materials.railing, Direction.NORTH, Direction.EAST);
		chunk.setBlock(2, yPlatform + 1, 9, materials.railing, Direction.SOUTH, Direction.WEST);
		chunk.setBlocks(2, 3, yPlatform + 1, 10, 13, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(13, yPlatform + 1, 9, materials.railing, Direction.EAST, Direction.SOUTH);
		chunk.setBlocks(13, 14, yPlatform + 1, 10, 13, materials.railing, Direction.NORTH, Direction.SOUTH);

		chunk.setBlocks(6, 7, yPlatform, 0, 2, materials.railing, Direction.NORTH, Direction.SOUTH, Direction.EAST);
		chunk.setBlocks(6, 7, yPlatform + 1, 0, 2, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlocks(9, 10, yPlatform, 0, 2, materials.railing, Direction.NORTH, Direction.SOUTH, Direction.WEST);
		chunk.setBlocks(9, 10, yPlatform + 1, 0, 2, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlocks(6, 7, yPlatform, 14, 16, materials.railing, Direction.NORTH, Direction.SOUTH, Direction.EAST);
		chunk.setBlocks(6, 7, yPlatform + 1, 14, 16, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlocks(9, 10, yPlatform, 14, 16, materials.railing, Direction.NORTH, Direction.SOUTH, Direction.WEST);
		chunk.setBlocks(9, 10, yPlatform + 1, 14, 16, materials.railing, Direction.NORTH, Direction.SOUTH);

		chunk.setBlocks(0, 2, yPlatform, 6, 7, materials.railing, Direction.EAST, Direction.WEST, Direction.SOUTH);
		chunk.setBlocks(0, 2, yPlatform + 1, 6, 7, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlocks(0, 2, yPlatform, 9, 10, materials.railing, Direction.EAST, Direction.WEST, Direction.NORTH);
		chunk.setBlocks(0, 2, yPlatform + 1, 9, 10, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlocks(14, 16, yPlatform, 6, 7, materials.railing, Direction.EAST, Direction.WEST, Direction.SOUTH);
		chunk.setBlocks(14, 16, yPlatform + 1, 6, 7, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlocks(14, 16, yPlatform, 9, 10, materials.railing, Direction.EAST, Direction.WEST, Direction.NORTH);
		chunk.setBlocks(14, 16, yPlatform + 1, 9, 10, materials.railing, Direction.EAST, Direction.WEST);

		// build a bunker
		switch (buildingType) {
		case BALLSY:
			return generateBallsyBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case ENTRY:
			return generateEntryBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop, yTop2, blockYs);
		case FLOORED:
			return generateFlooredBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case GROWING:
			return generateGrowingBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case PYRAMID:
			return generatePyramidBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case QUAD:
			return generateQuadBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case RECALL:
			return generateRecallBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case TANK:
			return generateTankBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case SAUCER:
			return generateSaucerBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop, topOfBunker,
					blockYs);
		case ROAD:
			return generateRoadTunnel(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		}
		return 0;
	}

	private static int generateEntryBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2, int topOfBunker, AbstractCachedYs blockYs) {
		int surfaceY = blockYs.getMaxYWithin(6, 10, 6, 10);
		int topY = surfaceY + DataContext.FloorHeight + 1;

		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		// walls
		chunk.setBlocks(5, 11, y1, topY, 5, 11, Blocks.TERRACOTTA);

		// do it!
		generateStairWell(generator, chunk, odds, 6, 6, y1, surfaceY, surfaceY, topY,
				Mapper.getStairsFor(materials.platform), materials.platform, materials.pillar);

		// mock up the top bit
		chunk.setBlocks(5, 11, surfaceY, topY, 5, 11, Blocks.TERRACOTTA);
		chunk.setBlocks(6, 10, topY, 6, 10, Blocks.TERRACOTTA);
		chunk.setBlocks(7, 9, topY + 1, 7, 9, Blocks.TERRACOTTA);

		// make sure there is room in the middle
		chunk.clearBlocks(6, 10, surfaceY, topY, 6, 10);

		// colorize it
		Colors colors = new Colors(odds, generator.coverProvider.getColorSet());
		chunk.colorizeBlocks(5, 11, blockYs.getMinHeight(), topY + 2, 5, 11, Blocks.TERRACOTTA, colors);

		// bottom doors
		chunk.setBlocks(7, 9, y1, y1 + 2, 5, 6, Blocks.AIR);
		chunk.setBlocks(7, 9, y1, y1 + 2, 10, 11, Blocks.AIR);
		chunk.setBlocks(5, 6, y1, y1 + 2, 7, 9, Blocks.AIR);
		chunk.setBlocks(10, 11, y1, y1 + 2, 7, 9, Blocks.AIR);

		// top doors
		chunk.setBlocks(7, 9, surfaceY + 1, surfaceY + 3, 5, 6, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlocks(7, 9, surfaceY + 1, surfaceY + 3, 10, 11, materials.railing, Direction.EAST, Direction.WEST);
		chunk.setBlocks(5, 6, surfaceY + 1, surfaceY + 3, 7, 9, materials.railing, Direction.NORTH, Direction.SOUTH);
		chunk.setBlocks(10, 11, surfaceY + 1, surfaceY + 3, 7, 9, materials.railing, Direction.NORTH, Direction.SOUTH);

		// put in some windows
		for (int y = y1 + 3; y < topOfBunker - 3; y = y + 3) {
			if (odds.flipCoin())
				chunk.setBlocks(7, 8 + odds.getRandomInt(2), y, y + 2, 5, 6, materials.window);
			if (odds.flipCoin())
				chunk.setBlocks(8 - odds.getRandomInt(2), 9, y, y + 2, 10, 11, materials.window);
			if (odds.flipCoin())
				chunk.setBlocks(5, 6, y, y + 2, 7, 8 + odds.getRandomInt(2), materials.window);
			if (odds.flipCoin())
				chunk.setBlocks(10, 11, y, y + 2, 8 - odds.getRandomInt(2), 9, materials.window);
		}

		// lift the surface?
		return 7;
	}

	private static int generateSaucerBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2, int topOfBunker, AbstractCachedYs blockYs) {

		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		// walls
		chunk.setCircle(8, 8, 5, topOfBunker, blockYs.getMaxHeight() + 1, Blocks.AIR, true);
		chunk.setCircle(8, 8, 6, topOfBunker, blockYs.getMinHeight() + 1, materials.building, false);
		chunk.setCircle(8, 8, 6, blockYs.getMinHeight(), blockYs.getAverageHeight() + 1, Blocks.TERRACOTTA, false);

		// lid & crack
		int lidY = blockYs.getAverageHeight() - 1;
		chunk.setCircle(8, 8, 5, lidY, Blocks.TERRACOTTA, true);

		for (int x = 3; x < 14; x += 2)
			chunk.setBlock(x, lidY, 7, Blocks.DARK_OAK_TRAPDOOR, Direction.NORTH, Half.TOP);
		for (int x = 2; x < 15; x += 2)
			chunk.setBlock(x, lidY, 8, Blocks.DARK_OAK_TRAPDOOR, Direction.SOUTH, Half.TOP);

		chunk.setLadder(2, topOfBunker, lidY, 8, Direction.EAST);
		chunk.setWalls(2, 14, topOfBunker - 1, topOfBunker, 2, 14, materials.crosswalk);

		// camo the exit
		Colors colors = new Colors(odds, generator.coverProvider.getColorSet());
		chunk.colorizeBlocks(1, 15, blockYs.getMinHeight(), lidY + 2, 1, 15, Blocks.TERRACOTTA, colors);

		// place it then
		if (odds.flipCoin())
			generator.structureInAirProvider.generateSaucer(generator, chunk, 8, y1, 8, true);

		// lift the surface?
		return 7;
	}

	public static int generateGrowingBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int x1 = 4;
		int x2 = x1 + 8;
		int y = y1;
		int z1 = 4;
		int z2 = z1 + 8;
		int Height = FloorHeight;

		Block emptyMaterial = Blocks.AIR;
		boolean firstFloor = true;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();
		Block detailColor = colors.getTerracotta();

		while (y + Height < y2) {

			// walls please
			chunk.setWalls(x1, x2, y, y + Height - 1, z1, z2, coreColor);

			// doors
			if (firstFloor) {
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z1, z1 + 1, emptyMaterial);
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z2 - 1, z2, emptyMaterial);
				chunk.setBlocks(x1, x1 + 1, y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				chunk.setBlocks(x2 - 1, x2, y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				firstFloor = false;
			}

			// interspace
			chunk.setBlocks(x1 + 1, x2 - 1, y + Height - 1, y + Height, z1 + 1, z2 - 1, detailColor);

			// make things bigger
			y += Height;
			Height += FloorHeight;
		}

		// lift the surface? NOPE
		return 0;
	}

	public static int generateFlooredBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int y3 = y2 - 2;

		Block emptyMaterial = Blocks.AIR;
		boolean firstFloor = true;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();
		Block detailColor = colors.getTerracotta();

		for (int y = y1; y < y3; y += FloorHeight) {

			// walls please
			chunk.setWalls(x1, x2, y, y + FloorHeight - 1, z1, z2, coreColor);

			// windows in the wall
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z1, z1 + 1, materials.window);
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z2 - 1, z2, materials.window);
			chunk.setBlocks(x1, x1 + 1, y + 1, y + 2, z1 + 2, z2 - 2, materials.window);
			chunk.setBlocks(x2 - 1, x2, y + 1, y + 2, z1 + 2, z2 - 2, materials.window);

			// doors
			if (firstFloor) {
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z1, z1 + 1, emptyMaterial);
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z2 - 1, z2, emptyMaterial);
				chunk.setBlocks(x1, x1 + 1, y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				chunk.setBlocks(x2 - 1, x2, y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				firstFloor = false;
			}

			// interspace
			chunk.setBlocks(x1 + 1, x2 - 1, y + FloorHeight - 1, y + FloorHeight, z1 + 1, z2 - 1, detailColor);
		}

		// lift the surface? NOPE
		return 0;
	}

	public static int generateRecallBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int buildingWidth = 10;
		int x1 = (chunk.width - buildingWidth) / 2;
		int x2 = x1 + buildingWidth;
		int z1 = x1;
		int z2 = z1 + buildingWidth;

		Block emptyMaterial = Blocks.AIR;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();
		Block detailColor = colors.getTerracotta();

		// lower bit
		chunk.setWalls(x1 + 1, x2 - 1, y1, y1 + 1, z1 + 1, z2 - 1, coreColor);
		chunk.setWalls(x1 + 1, x2 - 1, y1 + 1, y1 + 2, z1 + 1, z2 - 1, coreColor);

		// make it so we can walk into the
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 1, z1 + 2, emptyMaterial);
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z2 - 2, z2 - 1, emptyMaterial);
		chunk.setBlocks(x1 + 1, x1 + 2, y1, y1 + 2, z1 + 4, z2 - 4, emptyMaterial);
		chunk.setBlocks(x2 - 2, x2 - 1, y1, y1 + 2, z1 + 4, z2 - 4, emptyMaterial);

		int y = y1 + 2;
		int Height = FloorHeight;
		while (y + Height < y2) {
			int yTop = y + Height - 1;

			// texture
			for (int i = 1; i < buildingWidth; i += 2) {
				chunk.setBlocks(x1 + i, y, yTop, z1, detailColor);
				chunk.setBlocks(x1 + i - 1, y, yTop, z2 - 1, detailColor);
				chunk.setBlocks(x1, y, yTop, z1 + i, detailColor);
				chunk.setBlocks(x2 - 1, y, yTop, z1 + i - 1, detailColor);
			}

			// inner wall
			chunk.setWalls(x1 + 1, x2 - 1, y, yTop, z1 + 1, z2 - 1, coreColor);

			// cap it off
			chunk.setBlocks(x1 + 1, x2 - 1, yTop, yTop + 1, z1 + 1, z2 - 1, detailColor);

			// make things bigger
			y += Height;
			Height += FloorHeight;
		}

		generateTreat(generator, chunk, odds, 5, y1, 5);
		generateTreat(generator, chunk, odds, 10, y1, 10);

		generateTrick(generator, chunk, odds, 10, y1, 5);
		generateTrick(generator, chunk, odds, 5, y1, 10);

		// lift the surface? NOPE
		return 0;
	}

	public static int generateBallsyBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int y3 = y2 - 5;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();

		// initial pylon
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, coreColor);

		// rest of the pylon and balls
		for (int y = y1 + 2; y < y3; y += 6) {

			// center pylon
			chunk.setBlocks(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, coreColor);

			// balls baby!
			generateBallsyBuildingBall(chunk, odds, x1, y, z1);
			generateBallsyBuildingBall(chunk, odds, x1, y, z2 - 5);
			generateBallsyBuildingBall(chunk, odds, x2 - 5, y, z1);
			generateBallsyBuildingBall(chunk, odds, x2 - 5, y, z2 - 5);
		}

		// lift the surface? NOPE
		return 0;
	}

	private static void generateBallsyBuildingBall(SupportBlocks chunk, Odds odds, int x, int y, int z) {
		if (odds.flipCoin()) {

			Colors colors = new Colors(odds);
			Block ballColor = colors.getTerracotta();

			// bottom
			chunk.setBlocks(x + 1, x + 4, y, y + 1, z + 1, z + 4, ballColor);

			// sides
			chunk.setWalls(x, x + 5, y + 1, y + 4, z, z + 5, ballColor);

			// top
			chunk.setBlocks(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, ballColor);
		}
	}

	public static int generateQuadBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int ySegment = Math.max(1, (y2 - y1) / 5);
		int yRange = ySegment * 3;
		int yBase = y1 + ySegment;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();
		Block detailColor = colors.getTerracotta();

		int colY1 = yBase + odds.getRandomInt(yRange);
		int colY2 = yBase + odds.getRandomInt(yRange);
		int colY3 = yBase + odds.getRandomInt(yRange);
		int colY4 = yBase + odds.getRandomInt(yRange);

		// four towers
		generateQuadTowers(chunk, odds, x1, x1 + 5, y1, colY1, z1, z1 + 5, coreColor, detailColor);
		generateQuadTowers(chunk, odds, x1, x1 + 5, y1, colY2, z2 - 5, z2, coreColor, detailColor);
		generateQuadTowers(chunk, odds, x2 - 5, x2, y1, colY3, z1, z1 + 5, coreColor, detailColor);
		generateQuadTowers(chunk, odds, x2 - 5, x2, y1, colY4, z2 - 5, z2, coreColor, detailColor);

		// now randomly place connectors
		generateQuadConnectors(chunk, odds, x1, x1 + 5, y1 + 3, Math.min(colY1, colY2) - 3, z1 + 5, z1 + 7, true);
		generateQuadConnectors(chunk, odds, x1 + 5, x1 + 7, y1 + 3, Math.min(colY1, colY3) - 3, z1, z1 + 5, false);
		generateQuadConnectors(chunk, odds, x1 + 7, x1 + 12, y1 + 3, Math.min(colY3, colY4) - 3, z1 + 5, z1 + 7, true);
		generateQuadConnectors(chunk, odds, x1 + 5, x1 + 7, y1 + 3, Math.min(colY2, colY4) - 3, z1 + 7, z1 + 12, false);

		// TODO make them hollow
		// TODO vertical windows
		// TODO horizontal connections from time to time, place treasures here
		// TODO spiral staircase up the middle

		// lift the surface? NOPE
		return 0;
	}

	private static void generateQuadTowers(SupportBlocks chunk, Odds odds, int x1, int x2, int y1, int y2, int z1,
			int z2, Block coreColor, Block detailColor) {
		chunk.setBlocks(x1 + 2, x2 - 2, y1, y1 + 1, z1 + 2, z2 - 2, detailColor);
		chunk.setBlocks(x1 + 1, x2 - 1, y1 + 1, y1 + 2, z1 + 1, z2 - 1, detailColor);

		chunk.setWalls(x1, x2, y1 + 2, y2 - 2, z1, z2, coreColor);

		chunk.setBlocks(x1 + 1, x2 - 1, y2 - 2, y2 - 1, z1 + 1, z2 - 1, detailColor);
		chunk.setBlocks(x1 + 2, x2 - 2, y2 - 1, y2, z1 + 2, z2 - 2, detailColor);
	}

	private static void generateQuadConnectors(SupportBlocks chunk, Odds odds, int x1, int x2, int y1, int y2, int z1,
			int z2, boolean doX) {
		int px1 = x1;
		int px2 = x2;
		int py1 = y1;
		int pz1 = z1;
		int pz2 = z2;

		Colors colors = new Colors(odds);

		while (py1 < y2) {
			if (doX) {
				int lx = px1;
				do {
					px1 = x1 + odds.getRandomInt(x2 - x1);
					px2 = px1 + 1;
				} while (px1 == lx);
			} else {
				int lz = pz1;
				do {
					pz1 = z1 + odds.getRandomInt(z2 - z1);
					pz2 = pz1 + 1;
				} while (pz1 == lz);
			}

			chunk.setBlocks(px1, px2, py1, py1 + 1, pz1, pz2, colors.getTerracotta());
			py1 = py1 + 1;
		}
	}

	public static int generateTankBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int yBottom = y1 + 4;
		int yTop = y2;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();
		Block detailColor = colors.getTerracotta();

		// supports
		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z1 + 1, z1 + 3, detailColor);
		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z2 - 3, z2 - 1, detailColor);
		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z1 + 1, z1 + 3, detailColor);
		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z2 - 3, z2 - 1, detailColor);

		// bottom bit
		chunk.setBlocks(x1, x2, yBottom, yBottom + 1, z1, z2, coreColor);

		// walls
		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z1 - 1, z1, coreColor);
		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z2, z2 + 1, coreColor);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1, z2, coreColor);
		chunk.setBlocks(x2, x2 + 1, yBottom + 1, yTop, z1, z2, coreColor);

		// make it so we can see in a bit
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z1 - 1, z1, materials.window);
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z2, z2 + 1, materials.window);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1 + 3, z2 - 3, materials.window);
		chunk.setBlocks(x2, x2 + 1, yBottom + 1, yTop, z1 + 3, z2 - 3, materials.window);

		// put a top on it
		chunk.setBlocks(x1, x2, yTop, yTop + 1, z1, z2, coreColor);

		// fill it in
		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2,
				MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BunkerTanks, odds));

		// lift the surface? NOPE
		return 0;
	}

	public static int generatePyramidBunker(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;

		Colors colors = new Colors(odds);
		Block coreColor = colors.getTerracotta();
		Block detailColor = colors.getTerracotta();

		Block emptyMaterial = Blocks.AIR;
		for (int i = 0; i < 7; i++) {
			int y = y1 + i * 2;
			chunk.setWalls(x1 + i, x2 - i, y, y + 2, z1 + i, z2 - i, coreColor);
		}

		// make it so we can walk through the pyramid
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z1, z1 + 1, emptyMaterial);
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z2 - 1, z2, emptyMaterial);
		chunk.setBlocks(x1, x1 + 1, y1, y1 + 2, z1 + 5, z2 - 5, emptyMaterial);
		chunk.setBlocks(x2 - 1, x2, y1, y1 + 2, z1 + 5, z2 - 5, emptyMaterial);

		// top off the entry ways
		chunk.setBlocks(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z1, z1 + 1, detailColor);
		chunk.setBlocks(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z2 - 1, z2, detailColor);
		chunk.setBlocks(x1, x1 + 1, y1 + 2, y1 + 3, z1 + 4, z2 - 4, detailColor);
		chunk.setBlocks(x2 - 1, x2, y1 + 2, y1 + 3, z1 + 4, z2 - 4, detailColor);

		generateTreat(generator, chunk, odds, 3, y1, 3);
		generateTreat(generator, chunk, odds, 12, y1, 12);

		generateTrick(generator, chunk, odds, 12, y1, 3);
		generateTrick(generator, chunk, odds, 3, y1, 12);

		if (odds.playOdds(Odds.oddsVeryLikely)) {
			int yB = y1 - 1;
			chunk.setWalls(6, 10, yB, 6, 10, Blocks.OBSIDIAN);
			chunk.setBlocks(6, yB + 1, yB + 4, 6, Blocks.OBSIDIAN);
			chunk.setBlocks(9, yB + 1, yB + 4, 6, Blocks.OBSIDIAN);
			chunk.setBlocks(6, yB + 1, yB + 4, 9, Blocks.OBSIDIAN);
			chunk.setBlocks(9, yB + 1, yB + 4, 9, Blocks.OBSIDIAN);
			chunk.setWalls(6, 10, yB + 4, 6, 10, Blocks.OBSIDIAN);
//			if (odds.flipCoin()) {
////				chunk.setBlocks(6, 9, yB, 5, 6, Blocks.ENDER_PORTAL_FRAME);
////				chunk.setBlocks(9, 10, yB, 6, 9, Blocks.ENDER_PORTAL_FRAME);
////				chunk.setBlocks(6, 9, yB, 9, 10, Blocks.ENDER_PORTAL_FRAME);
////				chunk.setBlocks(5, 6, yB, 6, 9, Blocks.ENDER_PORTAL_FRAME);
//				chunk.setBlocks(7, 8, yB, 6, 10, Blocks.OBSIDIAN);
//				chunk.setBlocks(7, 8, yB, y1 + 4, 6, 7, Blocks.OBSIDIAN);
//				chunk.setBlocks(7, 8, yB, y1 + 4, 9, 10, Blocks.OBSIDIAN);
//				chunk.setBlocks(7, 8, y1 + 4, 6, 10, Blocks.OBSIDIAN);
//			} else {
//				chunk.setBlocks(6, 10, yB, 7, 8, Blocks.OBSIDIAN);
//				chunk.setBlocks(6, 7, yB, y1 + 4, 7, 8, Blocks.OBSIDIAN);
//				chunk.setBlocks(9, 10, yB, y1 + 4, 7, 8, Blocks.OBSIDIAN);
//				chunk.setBlocks(6, 10, y1 + 4, 7, 8, Blocks.OBSIDIAN);
//			}
		}

		// lift the surface? NOPE
		return 0;
	}

	private static final double oddsOfWayDownFromTunnel = Odds.oddsVeryLikely;

	private static int generateRoadTunnel(VocationCityWorldGenerator generator, DataContext context, SupportBlocks chunk,
			Odds odds, int y1, int y2) {
		// make sure we know what we using to make things
		loadMaterials(generator, odds);

		int underStreetY = generator.streetLevel - 3;
		int streetY = generator.streetLevel;

		// cross supports
		chunk.setBlocks(0, 16, underStreetY + 1, underStreetY + 2, 0, 2, materials.support);
		chunk.setBlocks(0, 16, underStreetY + 1, underStreetY + 2, 14, 16, materials.support);
		chunk.setBlocks(0, 2, underStreetY + 1, underStreetY + 2, 2, 14, materials.support);
		chunk.setBlocks(14, 16, underStreetY + 1, underStreetY + 2, 2, 14, materials.support);
		chunk.setBlocks(0, 16, underStreetY, underStreetY + 1, 0, 1, materials.support);
		chunk.setBlocks(0, 16, underStreetY, underStreetY + 1, 15, 16, materials.support);
		chunk.setBlocks(0, 1, underStreetY, underStreetY + 1, 2, 14, materials.support);
		chunk.setBlocks(15, 16, underStreetY, underStreetY + 1, 2, 14, materials.support);

		// center support
		chunk.setBlocks(7, 9, y1, underStreetY + 2, 7, 9, materials.pillar);

		// fix up the tunnel walls
		chunk.setBlocks(0, 2, streetY - 1, streetY + 6, 0, 1, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(0, streetY - 1, streetY + 6, 1, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(0, 2, streetY - 1, streetY + 6, 15, 16, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(0, streetY - 1, streetY + 6, 14, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(14, 16, streetY - 1, streetY + 6, 0, 1, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(15, streetY - 1, streetY + 6, 1, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(14, 16, streetY - 1, streetY + 6, 15, 16, RoadThroughBunkerLot.wallMaterial);
		chunk.setBlocks(15, streetY - 1, streetY + 6, 14, RoadThroughBunkerLot.wallMaterial);

		// put in a way down?
		if (odds.playOdds(oddsOfWayDownFromTunnel)) {
			chunk.setBlock(6, streetY, 7, Blocks.BIRCH_TRAPDOOR, Direction.WEST, Half.TOP);
			chunk.setLadder(6, y1, streetY, 7, Direction.WEST); // fixed
		}

		// lift the surface? NOPE
		return 0;
	}

	private final static Block springMat = Blocks.QUARTZ_STAIRS;
	private final static Block springBaseMat = Blocks.QUARTZ_PILLAR;
	private final static Block springCoreMat = Blocks.GLOWSTONE;

	private static void generateSupport(SupportBlocks chunk, Odds odds, DataContext context, int x, int y, int z) {
		chunk.setBlocks(x, x + 3, y, z, z + 3, springBaseMat);

		generateSpringBit(chunk, odds, x, y + 1, z, Direction.EAST, Direction.SOUTH, Direction.EAST, false);
		generateSpringBit(chunk, odds, x + 1, y + 1, z, Direction.WEST, Direction.EAST, Direction.WEST, true);
		generateSpringBit(chunk, odds, x + 2, y + 1, z, Direction.SOUTH, Direction.WEST, Direction.SOUTH, false);
		generateSpringBit(chunk, odds, x + 2, y + 1, z + 1, Direction.NORTH, Direction.SOUTH, Direction.NORTH, true);
		generateSpringBit(chunk, odds, x + 2, y + 1, z + 2, Direction.WEST, Direction.NORTH, Direction.WEST, false);
		generateSpringBit(chunk, odds, x + 1, y + 1, z + 2, Direction.EAST, Direction.WEST, Direction.EAST, true);
		generateSpringBit(chunk, odds, x, y + 1, z + 2, Direction.NORTH, Direction.EAST, Direction.NORTH, false);
		generateSpringBit(chunk, odds, x, y + 1, z + 1, Direction.SOUTH, Direction.NORTH, Direction.SOUTH, true);

		chunk.setBlocks(x + 1, y + 1, y + 5, z + 1, springCoreMat);

		chunk.setBlocks(x, x + 3, y + 4, z, z + 3, springBaseMat);
	}

	private static void generateSpringBit(SupportBlocks chunk, Odds odds, int x, int y, int z, Direction data1,
			Direction data2, Direction data3, boolean flip13) {
		chunk.setBlock(x, y, z, springMat, data1, flip13 ? Half.TOP : Half.BOTTOM);
		chunk.setBlock(x, y + 1, z, springMat, data2, flip13 ? Half.BOTTOM : Half.TOP);
		chunk.setBlock(x, y + 2, z, springMat, data3, flip13 ? Half.TOP : Half.BOTTOM);
	}

	private static void generateTreat(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y,
			int z) {

		// cool stuff?
		if (generator.getWorldSettings().treasuresInBunkers && odds.playOdds(generator.getWorldSettings().oddsOfTreasureInBunkers)) {
			chunk.setChest(generator, x, y, z, odds, generator.lootProvider, LootLocation.BUNKER);
		}
	}

	private static void generateTrick(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y,
			int z) {

		// not so cool stuff?
		generator.spawnProvider.setSpawnOrSpawner(generator, chunk, odds, x, y, z, generator.getWorldSettings().spawnersInBunkers,
				generator.spawnProvider.itemsEntities_Bunker);
	}

	private static BunkerType getRandomBunkerType(Odds chunkOdds, boolean firstOne) {
		if (firstOne) {
			if (chunkOdds.playOdds(Odds.oddsSomewhatUnlikely))
				return BunkerType.SAUCER;
			else
				return BunkerType.ENTRY;
		} else
			switch (chunkOdds.getRandomInt(7)) {
			case 1:
				return BunkerType.BALLSY;
			case 2:
				return BunkerType.FLOORED;
			case 3:
				return BunkerType.GROWING;
			case 4:
				return BunkerType.PYRAMID;
			case 5:
				return BunkerType.QUAD;
			case 6:
				return BunkerType.RECALL;
			default:
				return BunkerType.TANK;
			}
	}

	private final static double oddsOfStairs = Odds.oddsVeryLikely;
	private final static double oddsOfLanding = Odds.oddsVeryLikely;

	public static void generateStairWell(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int offX,
			int offZ, int shaftY, int minHeight, int surfaceY, int clearToY, Block stairs, Block landing,
			Block center) {

		// drill down
		chunk.setBlocks(offX, offX + 4, shaftY, clearToY, offZ, offZ + 4, Blocks.AIR);
		chunk.setBlocks(offX + 1, offX + 3, shaftY, surfaceY, offZ + 1, offZ + 3, center);

		// make the surface bits
		chunk.setBlocks(offX, offX + 4, minHeight, surfaceY + 1, offZ, offZ + 4, landing);

		// now do the stair
		do {
			shaftY = generateStairs(generator, chunk, odds, offX + 3, shaftY, offZ + 2, Direction.NORTH,
					Direction.SOUTH, stairs);
			if (shaftY > surfaceY)
				break;

			shaftY = generateStairs(generator, chunk, odds, offX + 3, shaftY, offZ + 1, Direction.NORTH, Direction.EAST,
					stairs);
			if (shaftY > surfaceY)
				break;

			generateLanding(generator, chunk, odds, offX + 3, shaftY, offZ, Direction.EAST, stairs, landing);

			shaftY = generateStairs(generator, chunk, odds, offX + 2, shaftY, offZ, Direction.WEST, Direction.EAST,
					stairs);
			if (shaftY > surfaceY)
				break;

			shaftY = generateStairs(generator, chunk, odds, offX + 1, shaftY, offZ, Direction.WEST, Direction.NORTH,
					stairs);
			if (shaftY > surfaceY)
				break;

			generateLanding(generator, chunk, odds, offX, shaftY, offZ, Direction.NORTH, stairs, landing);

			shaftY = generateStairs(generator, chunk, odds, offX, shaftY, offZ + 1, Direction.SOUTH,
					Direction.NORTH, stairs);
			if (shaftY > surfaceY)
				break;

			shaftY = generateStairs(generator, chunk, odds, offX, shaftY, offZ + 2, Direction.SOUTH, Direction.WEST,
					stairs);
			if (shaftY > surfaceY)
				break;

			generateLanding(generator, chunk, odds, offX, shaftY, offZ + 3, Direction.WEST, stairs, landing);

			shaftY = generateStairs(generator, chunk, odds, offX + 1, shaftY, offZ + 3, Direction.EAST, Direction.WEST,
					stairs);
			if (shaftY > surfaceY)
				break;

			shaftY = generateStairs(generator, chunk, odds, offX + 2, shaftY, offZ + 3, Direction.EAST, Direction.SOUTH,
					stairs);
			if (shaftY > surfaceY)
				break;

			generateLanding(generator, chunk, odds, offX + 3, shaftY, offZ + 3, Direction.SOUTH, stairs, landing);
		} while (shaftY <= surfaceY);
	}

	private static int generateStairs(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y, int z,
			Direction direction, Direction underdirection, Block stairs) {
		chunk.setBlocks(x, y + 1, y + 4, z, Blocks.AIR);

		// make a step... or not...
		if (!generator.getWorldSettings().includeDecayedBuildings || odds.playOdds(oddsOfStairs)) {
			chunk.setBlock(x, y, z, stairs, direction);
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, stairs, underdirection, Half.TOP);
		}

		// moving on up
		y++;

		// far enough?
		return y;
	}

	private static void generateLanding(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y,
			int z, Direction underdirection, Block stairs, Block landing) {
		chunk.setBlocks(x, y, y + 3, z, Blocks.AIR);

		// make a landing... or not...
		if (!generator.getWorldSettings().includeDecayedBuildings || odds.playOdds(oddsOfLanding)) {
			chunk.setBlock(x, y - 1, z, landing);
			if (chunk.isEmpty(x, y - 2, z))
				chunk.setBlock(x, y - 2, z, stairs, underdirection, Half.TOP);
		}
	}
}
