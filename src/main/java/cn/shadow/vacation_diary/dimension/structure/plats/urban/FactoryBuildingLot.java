package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.BunkerLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.FactoryWithStuff;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.Surroundings;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class FactoryBuildingLot extends IndustrialBuildingLot {

	private static final RoomProvider contentsStuff = new FactoryWithStuff();

	private final static double oddsOfSimilarContent = Odds.oddsUnlikely;

	private enum ContentStyle {
		BUILDING_SMOKESTACK, BUILDING_OFFICE, SIMPLE_TANK, STACKED_STUFF, SIMPLE_PIT, BUNKER_RECALL, BUNKER_TANK,
		BUNKER_QUAD, BUNKER_BALLS, BUNKER_GROWING
	}// , BUNKER_FLOORED};

	private ContentStyle contentStyle;

	private enum WallStyle {
		BUILDING, METAL_FENCE, WOOD_FENCE
	}// , STONE_FENCE};

	private WallStyle wallStyle;

	public FactoryBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		firstFloorHeight = DataContext.FloorHeight * (chunkOdds.getRandomInt(3) + 3);

	}

	@Override
	protected void calculateOptions(DataContext context) {
		super.calculateOptions(context);

		// how do the walls inset?
		insetWallWE = 1;
		insetWallNS = 1;

		// what about the ceiling?
		insetCeilingWE = insetWallWE;
		insetCeilingNS = insetWallNS;

		// nudge in a bit more as we go up
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetStyle = InsetStyle.STRAIGHT;

		contentStyle = pickContentStyle(chunkOdds);
		wallStyle = pickWallStyle(chunkOdds);
	}

	private ContentStyle pickContentStyle(Odds odds) {
		ContentStyle[] values = ContentStyle.values();
		return values[odds.getRandomInt(values.length)];
	}

	private WallStyle pickWallStyle(Odds odds) {
		WallStyle[] values = WallStyle.values();
		return values[odds.getRandomInt(values.length)];
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return Math.max(super.getTopY(generator, blockYs, x, z),
				generator.structureLevel + DataContext.FloorHeight * 10);
	}

	@Override
	protected boolean isShaftableLevel(VocationCityWorldGenerator generator, int blockY) {
		return blockY < generator.structureLevel - 10;
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof FactoryBuildingLot) {
			FactoryBuildingLot relativebuilding = (FactoryBuildingLot) relative;

			// any other bits
			firstFloorHeight = relativebuilding.firstFloorHeight;
			wallStyle = relativebuilding.wallStyle;

			if (chunkOdds.playOdds(oddsOfSimilarContent))
				contentStyle = relativebuilding.contentStyle;
		}

		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FactoryBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		return InteriorStyle.EMPTY;
	}

	@Override
	protected void drawExteriorParts(VocationCityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, int floor, boolean onTopFloor, boolean inMiddleSection,
			CornerWallStyle cornerStyle, boolean allowRounded, boolean outsetEffect, Block wallMaterial,
			Block glassMaterial, Surroundings heights) {

		switch (wallStyle) {
		case BUILDING:
			super.drawExteriorParts(generator, byteChunk, context, y1, height, insetNS, insetWE, floor, onTopFloor,
					inMiddleSection, cornerStyle, allowRounded, outsetEffect, wallMaterial, glassMaterial, heights);
			break;
		case METAL_FENCE:
			drawFence(generator, byteChunk, context, 0, y1, 0, heights, Blocks.IRON_BARS, 3);
			break;
		case WOOD_FENCE:
			drawFence(generator, byteChunk, context, 0, y1, 0, heights, Blocks.SPRUCE_FENCE, 2);
			break;
		}
	}

	@Override
	protected void drawCeilings(VocationCityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, boolean allowRounded, boolean outsetEffect, boolean onRoof,
			Block ceilingMaterial, Surroundings heights) {

		switch (wallStyle) {
		case BUILDING:
			super.drawCeilings(generator, byteChunk, context, y1, height, insetNS, insetWE, allowRounded, outsetEffect,
					onRoof, ceilingMaterial, heights);
			break;
		default:
			// nop
			break;
		}
	}

	@Override
	protected void drawRoof(VocationCityWorldGenerator generator, InitialBlocks chunk, DataContext context, int y1, int insetNS,
			int insetWE, int floor, boolean allowRounded, Block material, Surroundings heights) {

		switch (wallStyle) {
		case BUILDING:
			super.drawRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, material, heights);
			break;
		default:
			// nop
			break;
		}
	}

	@Override
	protected void drawRoof(VocationCityWorldGenerator generator, RealBlocks chunk, DataContext context, int y1, int insetNS,
			int insetWE, int floor, boolean allowRounded, boolean outsetEffect, Block material, Surroundings heights,
			RoofStyle thisStyle, RoofFeature thisFeature) {
		switch (wallStyle) {
		case BUILDING:
			super.drawRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, outsetEffect, material,
					heights, thisStyle, thisFeature);
			break;
		default:
			// nop
			break;
		}
	}

	@Override
	protected void drawInteriorParts(VocationCityWorldGenerator generator, RealBlocks chunk, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Block materialWall, Block materialGlass, StairWell stairLocation, Block materialStair,
			Block materialStairWall, Block materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {

		if (singleFloor) {
			int groundY = generator.structureLevel + 2;
			int skywalkHeight = firstFloorHeight / 2;
			int skywalkAt = groundY + skywalkHeight;
			int roofAt = skywalkAt + skywalkHeight;
			int extendedAt = ((roofAt - groundY) / 2) * 3 + groundY;
			boolean skyWalks = heights.adjacentNeighbors();

			Block airMat = generator.shapeProvider.findAtmosphereMaterialAt(generator, groundY);
			Block wallMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryInsides, chunkOdds, 
					Blocks.SMOOTH_STONE);
			Block officeMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryInsides, chunkOdds, 
					Blocks.SMOOTH_STONE);
			Block supportMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryInsides, chunkOdds, 
					Blocks.CLAY);
			Block smokestackMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryInsides, chunkOdds, 
					Blocks.CLAY);
			Block fluidMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryTanks, chunkOdds, 
					Blocks.WATER);
			
			switch (contentStyle) {
			case BUILDING_SMOKESTACK:
				if (skyWalks)
					generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
				generateSmokeStackArea(generator, chunk, heights, groundY, skywalkAt, roofAt, officeMat, smokestackMat);
				break;
			case BUILDING_OFFICE:
				if (skyWalks)
					generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
				generateOfficeArea(generator, chunk, groundY, skywalkAt, officeMat);
				break;
			case SIMPLE_PIT:
				if (skyWalks)
					generateSkyWalkCross(generator, chunk, heights, skywalkAt, roofAt);
				generatePitArea(generator, chunk, heights, groundY, skywalkAt, roofAt, airMat, wallMat, fluidMat);
				break;
			case SIMPLE_TANK:
				if (skyWalks)
					generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
				generateTankArea(generator, chunk, heights, groundY, skywalkAt, roofAt, wallMat, supportMat, fluidMat);
				break;
			case STACKED_STUFF:
				if (skyWalks)
					generateSkyWalkCross(generator, chunk, heights, skywalkAt, roofAt);
				generateStuffArea(generator, chunk, heights, groundY, skywalkAt, roofAt);
				break;
			case BUNKER_RECALL:
				if (skyWalks)
					generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
				if (wallStyle == WallStyle.BUILDING)
					BunkerLot.generateRecallBunker(generator, context, chunk, chunkOdds, groundY, roofAt);
				else
					BunkerLot.generateRecallBunker(generator, context, chunk, chunkOdds, groundY, extendedAt);
				break;
			case BUNKER_TANK:
				if (skyWalks)
					generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
				if (wallStyle == WallStyle.BUILDING)
					BunkerLot.generateTankBunker(generator, context, chunk, chunkOdds, groundY, roofAt - 2);
				else
					BunkerLot.generateTankBunker(generator, context, chunk, chunkOdds, groundY, extendedAt);
				break;
			case BUNKER_BALLS:
				if (skyWalks)
					generateSkyWalkCross(generator, chunk, heights, skywalkAt, roofAt);
				if (wallStyle == WallStyle.BUILDING)
					BunkerLot.generateBallsyBunker(generator, context, chunk, chunkOdds, groundY, roofAt);
				else
					BunkerLot.generateBallsyBunker(generator, context, chunk, chunkOdds, groundY, extendedAt);
				break;
			case BUNKER_QUAD:
				if (skyWalks)
					generateSkyWalkCross(generator, chunk, heights, skywalkAt, roofAt);
				if (wallStyle == WallStyle.BUILDING)
					BunkerLot.generateQuadBunker(generator, context, chunk, chunkOdds, groundY - 2, roofAt);
				else
					BunkerLot.generateQuadBunker(generator, context, chunk, chunkOdds, groundY - 2, extendedAt);
				break;
//			case BUNKER_FLOORED:
//				if (skyWalks)
//  				generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
//				if (wallStyle == WallStyle.BUILDING)
//					BunkerLot.generateFlooredBunker(generator, context, chunk, chunkOdds, groundY, roofAt);
//				else
//					BunkerLot.generateFlooredBunker(generator, context, chunk, chunkOdds, groundY, extendedAt);
//				break;
			case BUNKER_GROWING:
				if (skyWalks)
					generateSkyWalkBits(generator, chunk, heights, skywalkAt, roofAt);
				if (wallStyle == WallStyle.BUILDING)
					BunkerLot.generateGrowingBunker(generator, context, chunk, chunkOdds, groundY, roofAt);
				else
					BunkerLot.generateGrowingBunker(generator, context, chunk, chunkOdds, groundY, extendedAt);
				break;
			}
		}
	}

	private void generateOfficeArea(VocationCityWorldGenerator generator, RealBlocks chunk, int groundY, int skywalkAt,
			Block officeMat) {
		// bottom floor
		chunk.setWalls(3, 13, groundY, groundY + 1, 3, 13, officeMat);
		chunk.setWalls(3, 13, groundY + 1, groundY + 2, 3, 13, Blocks.GLASS_PANE);
		chunk.setWalls(3, 13, groundY + 2, skywalkAt, 3, 13, officeMat);
		chunk.setBlocks(3, 13, skywalkAt, 3, 13, officeMat);
		generateOpenings(chunk, groundY);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 7, groundY, 7);

		// room for middle floor?
		if (groundY + aboveFloorHeight <= skywalkAt - aboveFloorHeight) {
			int secondY = groundY + aboveFloorHeight;
			chunk.setBlocks(3, 13, secondY, 3, 13, officeMat);
			chunk.setWalls(3, 13, secondY + 2, secondY + 3, 3, 13, Blocks.GLASS_PANE);
		}

		// top floor at skywalk level
		chunk.setWalls(3, 13, skywalkAt + 1, skywalkAt + 2, 3, 13, officeMat);
		chunk.setWalls(3, 13, skywalkAt + 2, skywalkAt + 3, 3, 13, Blocks.GLASS_PANE);
		chunk.setWalls(3, 13, skywalkAt + 3, skywalkAt + 4, 3, 13, officeMat);
		chunk.setBlocks(3, 13, skywalkAt + 4, 3, 13, officeMat);
		generateOpenings(chunk, skywalkAt + 1);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 7, skywalkAt + 1, 7);

		chunk.setBlocks(5, groundY, skywalkAt + 2, 5, officeMat);
		chunk.setLadder(5, groundY, skywalkAt + 2, 6, Direction.SOUTH); // fixed
	}

	private void generateSmokeStackArea(VocationCityWorldGenerator generator, RealBlocks chunk, Surroundings heights,
			int groundY, int skywalkAt, int roofAt, Block officeMat, Block smokestackMat) {
		chunk.setWalls(3, 13, groundY, skywalkAt - 1, 3, 13, officeMat);
		chunk.setWalls(4, 12, skywalkAt - 1, skywalkAt, 4, 12, officeMat);
		chunk.setBlocks(3, 13, skywalkAt, 3, 13, officeMat);
		generateOpenings(chunk, groundY);

		int smokestackY1 = skywalkAt + firstFloorHeight;
		int smokestackY2 = smokestackY1 + chunkOdds.calcRandomRange(4, firstFloorHeight - 3);
		int smokestackY3 = smokestackY2 + chunkOdds.calcRandomRange(4, firstFloorHeight - 3);

		chunk.setBlocks(6, 10, groundY - 3, 6, 10, smokestackMat);
		chunk.clearBlocks(6, 10, groundY - 2, smokestackY1, 6, 10);
		chunk.setWalls(5, 11, groundY - 2, groundY, 5, 11, smokestackMat);
		chunk.setBlocks(6, 10, groundY - 2, 6, 10, Blocks.NETHERRACK);
		chunk.setWalls(5, 11, groundY, groundY + 6, 5, 11, smokestackMat);

		chunk.setBlock(8, groundY + 1, 5, Blocks.RED_STAINED_GLASS_PANE, Direction.EAST, Direction.WEST);
		chunk.setBlock(7, groundY + 1, 10, Blocks.RED_STAINED_GLASS_PANE, Direction.EAST, Direction.WEST);
		chunk.setBlock(5, groundY + 1, 8, Blocks.RED_STAINED_GLASS_PANE, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(10, groundY + 1, 7, Blocks.RED_STAINED_GLASS_PANE, Direction.NORTH, Direction.SOUTH);

		// too bad I have to goof it up now
		if (generator.getWorldSettings().includeDecayedBuildings) {
			if (chunkOdds.playOdds(Odds.oddsLikely)) {
				chunk.setCircle(8, 8, 2, groundY + 6, smokestackY1, smokestackMat);
				chunk.pepperBlocks(5, 11, groundY, smokestackY1, 5, 11, chunkOdds, Odds.oddsPrettyUnlikely,
						Odds.oddsSomewhatUnlikely, Blocks.AIR);
				if (chunkOdds.playOdds(Odds.oddsSomewhatLikely)) {
					chunk.setWalls(6, 10, smokestackY1, smokestackY2, 6, 10, smokestackMat);
					chunk.pepperBlocks(6, 10, smokestackY1, smokestackY2, 6, 10, chunkOdds, Odds.oddsSomewhatUnlikely,
							Odds.oddsLikely, Blocks.AIR);
					if (chunkOdds.playOdds(Odds.oddsSomewhatUnlikely)) {
						chunk.setCircle(8, 8, 1, smokestackY2, smokestackY3, smokestackMat);
						chunk.pepperBlocks(7, 9, smokestackY2, smokestackY3, 7, 9, chunkOdds, Odds.oddsLikely,
								Odds.oddsExtremelyLikely, Blocks.AIR);
					}
				}
			}

		} else {
			boolean onFire = chunkOdds.playOdds(Odds.oddsPrettyLikely);
			if (onFire)
				chunk.setBlocks(6, 10, groundY - 1, 6, 10, Blocks.FIRE);

			chunk.setCircle(8, 8, 2, groundY + 6, smokestackY1, smokestackMat);
			chunk.setWalls(6, 10, smokestackY1, smokestackY2, 6, 10, smokestackMat);
			chunk.setCircle(8, 8, 1, smokestackY2, smokestackY3, smokestackMat);

			if (onFire) {
				chunk.setBlock(7, smokestackY3 - 2, 7, Blocks.CAMPFIRE);
				chunk.setBlock(8, smokestackY3 - 2, 8, Blocks.CAMPFIRE);
//				chunk.pepperBlocks(7, 9, smokestackY3 - 2, smokestackY3 + 6, 7, 9, chunkOdds, Material.COBWEB);
			}
		}
	}

	private void generateStuffArea(VocationCityWorldGenerator generator, RealBlocks chunk, Surroundings heights, int groundY,
			int skywalkAt, int roofAt) {
		generateStuff(generator, chunk, 2, groundY, 2, 3, 3);
		if (heights.toNorth())
			generateStuff(generator, chunk, 6, groundY, 2, 4, 3);
		generateStuff(generator, chunk, 11, groundY, 2, 3, 3);

		if (heights.toWest())
			generateStuff(generator, chunk, 2, groundY, 6, 3, 4);
		generateStuff(generator, chunk, 6, groundY, 6, 4, 4);
		if (heights.toEast())
			generateStuff(generator, chunk, 11, groundY, 6, 3, 4);

		generateStuff(generator, chunk, 2, groundY, 11, 3, 3);
		if (heights.toSouth())
			generateStuff(generator, chunk, 6, groundY, 11, 4, 3);
		generateStuff(generator, chunk, 11, groundY, 11, 3, 3);

//			chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
//			chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
//			chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
//			chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
//			
//			if (!neighborFloors.toNorth())
//				chunk.setWool(7, 9, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
//			if (!neighborFloors.toSouth())
//				chunk.setWool(7, 9, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
//			if (!neighborFloors.toWest())
//				chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 7, 9, chunkOdds.getRandomColor());
//			if (!neighborFloors.toEast())
//				chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 7, 9, chunkOdds.getRandomColor());
//			
	}

	private void generateTankArea(VocationCityWorldGenerator generator, RealBlocks chunk, Surroundings heights, int groundY,
			int skywalkAt, int roofAt, Block wallMat, Block supportMat, Block fluidMat) {
		int topOfTank = skywalkAt + 2;
		int bottomOfTank = groundY + 4;
		int tankLevel = topOfTank - chunkOdds.getRandomInt(3) - 1;

		chunk.setCircle(8, 8, 4, bottomOfTank - 1, wallMat, true);
		chunk.setCircle(8, 8, 4, bottomOfTank, tankLevel, fluidMat, true);
		chunk.setCircle(8, 8, 4, bottomOfTank, topOfTank, wallMat); // put the wall up quick!

		chunk.setBlocks(4, 6, groundY, bottomOfTank + 1, 4, 6, supportMat);
		chunk.setBlocks(10, 12, groundY, bottomOfTank + 1, 4, 6, supportMat);
		chunk.setBlocks(4, 6, groundY, bottomOfTank + 1, 10, 12, supportMat);
		chunk.setBlocks(10, 12, groundY, bottomOfTank + 1, 10, 12, supportMat);
	}

	private void generatePitArea(VocationCityWorldGenerator generator, RealBlocks chunk, Surroundings heights, int groundY,
			int skywalkAt, int roofAt, Block airMat, Block wallMat, Block fluidMat) {
		int topOfPit = groundY + 1;
		int bottomOfPit = topOfPit - 6;
		int pitLevel = topOfPit - chunkOdds.getRandomInt(3) - 1;

		chunk.setCircle(8, 8, 4, bottomOfPit - 1, wallMat, true);
		chunk.setCircle(8, 8, 4, bottomOfPit, topOfPit, airMat, true);
		chunk.setCircle(8, 8, 4, bottomOfPit, pitLevel, fluidMat, true);
		chunk.setCircle(8, 8, 4, bottomOfPit, topOfPit, wallMat); // put the wall up quick!
	}

	private void generateStuff(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z, int width,
			int depth) {
		contentsStuff.drawFixtures(generator, chunk, chunkOdds, 1, x, y, z, width, DataContext.FloorHeight, depth,
				Direction.NORTH, Blocks.STONE, Blocks.GLASS);
	}

	private void generateOpenings(RealBlocks chunk, int y) {
		chunk.clearBlocks(7 + chunkOdds.getRandomInt(2), y, y + 2, 3);
		chunk.clearBlocks(7 + chunkOdds.getRandomInt(2), y, y + 2, 12);
		chunk.clearBlocks(3, y, y + 2, 7 + chunkOdds.getRandomInt(2));
		chunk.clearBlocks(12, y, y + 2, 7 + chunkOdds.getRandomInt(2));
	}

	private void generateSkyWalkBits(VocationCityWorldGenerator generator, RealBlocks chunk, Surroundings neighbors,
			int skywalkAt, int roofAt) {
		boolean doNorthward = neighbors.toNorth();
		boolean doSouthward = neighbors.toSouth();
		boolean doWestward = neighbors.toWest();
		boolean doEastward = neighbors.toEast();

		if (doNorthward) {
			generateSkyWalkBitsN(chunk, 6, 0, skywalkAt);
			if (wallStyle != WallStyle.BUILDING) {
				chunk.setBlocks(7, 9, generator.structureLevel + 2, skywalkAt, 0, 1, wallMaterial);
				generateLadder(chunk, 6, generator.structureLevel, skywalkAt, 0, Direction.WEST); // fixed
			}
		}
		if (doSouthward) {
			generateSkyWalkBitsS(chunk, 6, 12, skywalkAt);
			if (wallStyle != WallStyle.BUILDING) {
				chunk.setBlocks(7, 9, generator.structureLevel + 2, skywalkAt, 15, 16, wallMaterial);
				generateLadder(chunk, 9, generator.structureLevel, skywalkAt, 15, Direction.EAST); // fixed
			}
		}
		if (doWestward) {
			generateSkyWalkBitsW(chunk, 0, 6, skywalkAt);
			if (wallStyle != WallStyle.BUILDING) {
				chunk.setBlocks(0, 1, generator.structureLevel + 2, skywalkAt, 7, 9, wallMaterial);
				generateLadder(chunk, 0, generator.structureLevel, skywalkAt, 6, Direction.NORTH); // fixed
			}
		}
		if (doEastward) {
			generateSkyWalkBitsE(chunk, 12, 6, skywalkAt);
			if (wallStyle != WallStyle.BUILDING) {
				chunk.setBlocks(15, 16, generator.structureLevel + 2, skywalkAt, 7, 9, wallMaterial);
				generateLadder(chunk, 15, generator.structureLevel, skywalkAt, 9, Direction.SOUTH); // fixed
			}
		}
	}

	private void generateSkyWalkBitsN(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 4, roofMaterial);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z, z + 3, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(x, skywalkAt + 1, z + 3, Blocks.IRON_BARS, Direction.NORTH);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z, z + 3, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(x + 3, skywalkAt + 1, z + 3, Blocks.IRON_BARS, Direction.NORTH);
		chunk.setBlocks(x + 1, x + 2, skywalkAt - 1, z, z + 4, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH,
				Direction.EAST);
		chunk.setBlocks(x + 2, x + 3, skywalkAt - 1, z, z + 4, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH,
				Direction.WEST);
	}

	private void generateSkyWalkBitsS(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 4, roofMaterial);
		chunk.setBlock(x, skywalkAt + 1, z, Blocks.IRON_BARS, Direction.SOUTH);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z + 1, z + 4, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		chunk.setBlock(x + 3, skywalkAt + 1, z, Blocks.IRON_BARS, Direction.SOUTH);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z + 1, z + 4, Blocks.IRON_BARS, Direction.NORTH,
				Direction.SOUTH);
		chunk.setBlocks(x + 1, x + 2, skywalkAt - 1, z, z + 4, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH,
				Direction.EAST);
		chunk.setBlocks(x + 2, x + 3, skywalkAt - 1, z, z + 4, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH,
				Direction.WEST);
	}

	private void generateSkyWalkBitsW(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 4, roofMaterial);
		chunk.setBlocks(x, x + 3, skywalkAt + 1, z, z + 1, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		chunk.setBlock(x + 3, skywalkAt + 1, z, Blocks.IRON_BARS, Direction.WEST);
		chunk.setBlocks(x, x + 3, skywalkAt + 1, z + 3, z + 4, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		chunk.setBlock(x + 3, skywalkAt + 1, z + 3, Blocks.IRON_BARS, Direction.WEST);
		chunk.setBlocks(x, x + 4, skywalkAt - 1, z + 1, z + 2, Blocks.IRON_BARS, Direction.EAST, Direction.WEST,
				Direction.SOUTH);
		chunk.setBlocks(x, x + 4, skywalkAt - 1, z + 2, z + 3, Blocks.IRON_BARS, Direction.EAST, Direction.WEST,
				Direction.NORTH);
	}

	private void generateSkyWalkBitsE(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 4, roofMaterial);
		chunk.setBlock(x, skywalkAt + 1, z, Blocks.IRON_BARS, Direction.EAST);
		chunk.setBlocks(x + 1, x + 4, skywalkAt + 1, z, z + 1, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		chunk.setBlock(x, skywalkAt + 1, z + 3, Blocks.IRON_BARS, Direction.EAST);
		chunk.setBlocks(x + 1, x + 4, skywalkAt + 1, z + 3, z + 4, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		chunk.setBlocks(x, x + 4, skywalkAt - 1, z + 1, z + 2, Blocks.IRON_BARS, Direction.EAST, Direction.WEST,
				Direction.SOUTH);
		chunk.setBlocks(x, x + 4, skywalkAt - 1, z + 2, z + 3, Blocks.IRON_BARS, Direction.EAST, Direction.WEST,
				Direction.NORTH);
	}

	private void generateSkyWalkCross(VocationCityWorldGenerator generator, RealBlocks chunk, Surroundings neighbors,
			int skywalkAt, int roofAt) {
		boolean doNorthward = neighbors.toNorth();
		boolean doSouthward = neighbors.toSouth();
		boolean doWestward = neighbors.toWest();
		boolean doEastward = neighbors.toEast();

		if (doNorthward)
			generateSkyWalkNS(chunk, 6, 0, skywalkAt, roofAt);
		if (doSouthward)
			generateSkyWalkNS(chunk, 6, 10, skywalkAt, roofAt);
		if (doWestward)
			generateSkyWalkWE(chunk, 0, 6, skywalkAt, roofAt);
		if (doEastward)
			generateSkyWalkWE(chunk, 10, 6, skywalkAt, roofAt);

		chunk.setBlocks(6, 10, skywalkAt, 6, 10, ceilingMaterial);
		chunk.setBlocks(7, 9, skywalkAt - 1, 7, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH, Direction.EAST,
				Direction.WEST);
		if (doNorthward) {
			chunk.setBlock(7, skywalkAt - 1, 6, Blocks.IRON_BARS, Direction.EAST, Direction.SOUTH, Direction.NORTH);
			chunk.setBlock(8, skywalkAt - 1, 6, Blocks.IRON_BARS, Direction.WEST, Direction.SOUTH, Direction.NORTH);
		} else {
			chunk.setBlock(7, skywalkAt - 1, 6, Blocks.IRON_BARS, Direction.EAST, Direction.SOUTH);
			chunk.setBlock(8, skywalkAt - 1, 6, Blocks.IRON_BARS, Direction.WEST, Direction.SOUTH);
		}
		if (doSouthward) {
			chunk.setBlock(7, skywalkAt - 1, 9, Blocks.IRON_BARS, Direction.EAST, Direction.NORTH, Direction.SOUTH);
			chunk.setBlock(8, skywalkAt - 1, 9, Blocks.IRON_BARS, Direction.WEST, Direction.NORTH, Direction.SOUTH);
		} else {
			chunk.setBlock(7, skywalkAt - 1, 9, Blocks.IRON_BARS, Direction.EAST, Direction.NORTH);
			chunk.setBlock(8, skywalkAt - 1, 9, Blocks.IRON_BARS, Direction.WEST, Direction.NORTH);
		}
		if (doWestward) {
			chunk.setBlock(6, skywalkAt - 1, 7, Blocks.IRON_BARS, Direction.SOUTH, Direction.EAST, Direction.WEST);
			chunk.setBlock(6, skywalkAt - 1, 8, Blocks.IRON_BARS, Direction.NORTH, Direction.EAST, Direction.WEST);
		} else {
			chunk.setBlock(6, skywalkAt - 1, 7, Blocks.IRON_BARS, Direction.SOUTH, Direction.EAST);
			chunk.setBlock(6, skywalkAt - 1, 8, Blocks.IRON_BARS, Direction.NORTH, Direction.EAST);
		}
		if (doEastward) {
			chunk.setBlock(9, skywalkAt - 1, 7, Blocks.IRON_BARS, Direction.SOUTH, Direction.WEST, Direction.EAST);
			chunk.setBlock(9, skywalkAt - 1, 8, Blocks.IRON_BARS, Direction.NORTH, Direction.WEST, Direction.EAST);
		} else {
			chunk.setBlock(9, skywalkAt - 1, 7, Blocks.IRON_BARS, Direction.SOUTH, Direction.WEST);
			chunk.setBlock(9, skywalkAt - 1, 8, Blocks.IRON_BARS, Direction.NORTH, Direction.WEST);
		}

		if (!doNorthward)
			chunk.setBlocks(7, 9, skywalkAt + 1, 6, 7, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		if (!doSouthward)
			chunk.setBlocks(7, 9, skywalkAt + 1, 9, 10, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		if (!doWestward)
			chunk.setBlocks(6, 7, skywalkAt + 1, 7, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		if (!doEastward)
			chunk.setBlocks(9, 10, skywalkAt + 1, 7, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);

		if (doNorthward && doWestward) {
			chunk.setBlock(6, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.NORTH, Direction.WEST);
		} else if (doNorthward) {
			chunk.setBlock(6, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		} else if (doWestward) {
			chunk.setBlock(6, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		} else {
			chunk.setBlock(6, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.SOUTH, Direction.EAST);
		}
		if (doSouthward && doWestward) {
			chunk.setBlock(6, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.SOUTH, Direction.WEST);
		} else if (doSouthward) {
			chunk.setBlock(6, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		} else if (doWestward) {
			chunk.setBlock(6, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		} else {
			chunk.setBlock(6, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.EAST);
		}
		if (doNorthward && doEastward) {
			chunk.setBlock(9, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.NORTH, Direction.EAST);
		} else if (doNorthward) {
			chunk.setBlock(9, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		} else if (doEastward) {
			chunk.setBlock(9, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		} else {
			chunk.setBlock(9, skywalkAt + 1, 6, Blocks.IRON_BARS, Direction.SOUTH, Direction.WEST);
		}
		if (doSouthward && doEastward) {
			chunk.setBlock(9, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.SOUTH, Direction.EAST);
		} else if (doSouthward) {
			chunk.setBlock(9, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		} else if (doEastward) {
			chunk.setBlock(9, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		} else {
			chunk.setBlock(9, skywalkAt + 1, 9, Blocks.IRON_BARS, Direction.NORTH, Direction.WEST);
		}

		if (wallStyle == WallStyle.BUILDING) {
			chunk.setBlocksUpward(6, skywalkAt + 2, 6, roofAt, Blocks.IRON_BARS);
			chunk.setBlocksUpward(6, skywalkAt + 2, 9, roofAt, Blocks.IRON_BARS);
			chunk.setBlocksUpward(9, skywalkAt + 2, 6, roofAt, Blocks.IRON_BARS);
			chunk.setBlocksUpward(9, skywalkAt + 2, 9, roofAt, Blocks.IRON_BARS);
		} else {
			if (doNorthward) {
				chunk.setBlocks(7, 9, generator.structureLevel + 2, skywalkAt, 0, 1, wallMaterial);
				generateLadder(chunk, 6, generator.structureLevel, skywalkAt, 0, Direction.WEST); // fixed
			}
			if (doSouthward) {
				chunk.setBlocks(7, 9, generator.structureLevel + 2, skywalkAt, 15, 16, wallMaterial);
				generateLadder(chunk, 9, generator.structureLevel, skywalkAt, 15, Direction.EAST); // fixed
			}
			if (doWestward) {
				chunk.setBlocks(0, 1, generator.structureLevel + 2, skywalkAt, 7, 9, wallMaterial);
				generateLadder(chunk, 0, generator.structureLevel, skywalkAt, 6, Direction.NORTH); // fixed
			}
			if (doEastward) {
				chunk.setBlocks(15, 16, generator.structureLevel + 2, skywalkAt, 7, 9, wallMaterial);
				generateLadder(chunk, 15, generator.structureLevel, skywalkAt, 9, Direction.SOUTH); // fixed
			}
		}
	}

	private void generateLadder(RealBlocks chunk, int x, int y1, int y2, int z, Direction facing) {
		boolean doLadder = false;
		boolean chunkXEven = getChunkX() % 2 == 0;
		boolean chunkZEven = getChunkZ() % 2 == 0;

		switch (facing) {
		default:
		case SOUTH:
			doLadder = chunkXEven && chunkZEven;
			break;
		case NORTH:
			doLadder = chunkXEven && !chunkZEven;
			break;
		case EAST:
			doLadder = !chunkXEven && chunkZEven;
			break;
		case WEST:
			doLadder = !chunkXEven && !chunkZEven;
			break;
		}

		if (doLadder) {
			chunk.setLadder(x, y1 + 2, y2 + 1, z, facing); // fixed
			chunk.clearBlock(x, y2 + 1, z);
		}

//		chunk.setBlock(x, y2 + 10, z, Material.STONE);
//		chunk.setSignPost(x, y2 + 11, z, facing, "X, Z = " + getChunkX() + ", " + getChunkZ(), "XEven = " + chunkXEven, "ZEven = " + chunkZEven, "Ladder = " + doLadder);
	}

	private void generateSkyWalkNS(RealBlocks chunk, int x, int z, int skywalkAt, int roofAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 6, roofMaterial);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z, z + 6, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z, z + 6, Blocks.IRON_BARS, Direction.NORTH, Direction.SOUTH);
		chunk.fillBlocks(x + 1, x + 3, skywalkAt - 1, z, z + 6, Blocks.IRON_BARS);
		if (wallStyle == WallStyle.BUILDING) {
			chunk.setBlocksUpward(x, skywalkAt + 2, z + 2, roofAt, Blocks.IRON_BARS);
			chunk.setBlocksUpward(x + 3, skywalkAt + 2, z + 2, roofAt, Blocks.IRON_BARS);
		}
	}

	private void generateSkyWalkWE(RealBlocks chunk, int x, int z, int skywalkAt, int roofAt) {
		chunk.setBlocks(x, x + 6, skywalkAt, z, z + 4, roofMaterial);
		chunk.setBlocks(x, x + 6, skywalkAt + 1, z, z + 1, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		chunk.setBlocks(x, x + 6, skywalkAt + 1, z + 3, z + 4, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		chunk.fillBlocks(x, x + 6, skywalkAt - 1, z + 1, z + 3, Blocks.IRON_BARS);
		if (wallStyle == WallStyle.BUILDING) {
			chunk.setBlocksUpward(x + 2, skywalkAt + 2, z, roofAt, Blocks.IRON_BARS);
			chunk.setBlocksUpward(x + 2, skywalkAt + 2, z + 3, roofAt, Blocks.IRON_BARS);
		}
	}
}
