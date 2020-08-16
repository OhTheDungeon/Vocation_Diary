package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.FinishedBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Mapper;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.dimension.support.Surroundings;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class GovernmentBuildingLot extends FinishedBuildingLot {

	public GovernmentBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		depth = 0;
		height = chunkOdds.calcRandomRange(2, 4);
	}

	@Override
	protected void loadMaterials(PlatMap platmap) {

		// what is it made of?
		wallMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentWalls, chunkOdds, 
				Blocks.QUARTZ_BLOCK);
		foundationMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentFoundations, chunkOdds, 
				Blocks.QUARTZ_BLOCK);
		ceilingMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentCeilings, chunkOdds, 
				Blocks.WHITE_WOOL);
		roofMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentCeilings, chunkOdds, 
				Blocks.WHITE_WOOL);
		columnMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentWalls, chunkOdds, 
				Mapper.getColumnFor(wallMaterial));
		foundationSteps = Mapper.getStairsFor(foundationMaterial);
	}

	private Block foundationSteps;

	private static final int higher = 2;
	private static final int deeper = 3;

	@Override
	protected void calculateOptions(DataContext context) {
		super.calculateOptions(context);

		neighborsHaveIdenticalHeights = true;
		insetCeilingNS = deeper;
		insetCeilingWE = deeper;
		insetWallNS = deeper;
		insetWallWE = deeper;
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetStyle = InsetStyle.STRAIGHT;
		interiorStyle = InteriorStyle.COLUMNS_ONLY;
		rounded = false;
//		roofStyle = RoofStyle.PEAKS;
		roofFeature = RoofFeature.PLAIN;
		outsetEffects = false;
		cornerWallStyle = CornerWallStyle.FILLED;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GovernmentBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof GovernmentBuildingLot) {
			GovernmentBuildingLot relativebuilding = (GovernmentBuildingLot) relative;

			foundationMaterial = relativebuilding.foundationMaterial;
			foundationSteps = relativebuilding.foundationSteps;
		}
		return result;
	}

	private boolean doBuilding(Surroundings heights) {
//		return false;
		return heights.adjacentNeighbors();
	}

	@Override
	protected void drawExteriorParts(VocationCityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, int floor, boolean onTopFloor, boolean inMiddleSection,
			CornerWallStyle cornerStyle, boolean allowRounded, boolean outsetEffect, Block wallMaterial,
			Block glassMaterial, Surroundings heights) {

		if (doBuilding(heights)) {
			super.drawExteriorParts(generator, byteChunk, context, y1 + higher, height, insetNS, insetWE, floor,
					onTopFloor, inMiddleSection, cornerStyle, allowRounded, outsetEffect, wallMaterial, glassMaterial,
					heights);
		}
	}

	@Override
	protected void drawCeilings(VocationCityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, boolean allowRounded, boolean outsetEffect, boolean onRoof,
			Block ceilingMaterial, Surroundings heights) {

		if (doBuilding(heights)) {
			if (onRoof)
				super.drawCeilings(generator, byteChunk, context, y1 + higher, height, insetNS - deeper,
						insetWE - deeper, allowRounded, outsetEffect, onRoof, ceilingMaterial, heights);
			else
				super.drawCeilings(generator, byteChunk, context, y1 + higher, height, insetNS, insetWE, allowRounded,
						outsetEffect, onRoof, ceilingMaterial, heights);
		}
	}

	@Override
	protected void drawRoof(VocationCityWorldGenerator generator, InitialBlocks chunk, DataContext context, int y1, int insetNS,
			int insetWE, int floor, boolean allowRounded, Block roofMaterial, Surroundings heights) {

		if (doBuilding(heights)) {
			super.drawRoof(generator, chunk, context, y1 + higher, insetNS - deeper, insetWE - deeper, floor,
					allowRounded, roofMaterial, heights);
		}
	}

	@Override
	protected void drawInteriorParts(VocationCityWorldGenerator generator, RealBlocks blocks, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Block materialWall, Block materialGlass, StairWell stairLocation, Block materialStair,
			Block materialStairWall, Block materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {

		if (doBuilding(heights)) {
			// TODO Auto-generated method stub
			super.drawInteriorParts(generator, blocks, context, rooms, floor, floorAt + higher, floorHeight, insetNS,
					insetWE, allowRounded, materialWall, materialGlass, stairLocation, materialStair, materialStairWall,
					materialPlatform, drawStairWall, drawStairs, topFloor, singleFloor, heights);

			if (floor == 0) {
				drawFoundationSteps(blocks, floorAt, floorAt + 2, heights);
				drawFoundationColumns(blocks, floorAt, 1, heights);
			}
			drawFoundationColumns(blocks, floorAt + higher - 1, DataContext.FloorHeight, heights);
		}
	}

	private void drawFoundationSteps(SupportBlocks blocks, int y1, int y2, Surroundings heights) {
		// NorthWest
		if (heights.toNorth()) {
			if (heights.toWest()) {
				if (heights.toNorthWest()) {
					// 33
					blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
				} else {
					// 11
					// should be steps
					blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
				}
			} else {
				// 24
				drawFoundationHeadingEastBit(blocks, 0, y1, 0, 3);
			}
		} else {
			if (heights.toWest()) {
				// 14
				drawFoundationHeadingSouthBit(blocks, 0, y1, 0, 3);
			} else {
				// 1
				// should be steps
				blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
			}
		}

		// North
		if (heights.toNorth())
			// 12
			blocks.setBlocks(3, 13, y1, y2, 0, 3, foundationMaterial);
		else
			// 2
			drawFoundationHeadingSouthBit(blocks, 3, y1, 0, 10);

		// NorthEast
		if (heights.toNorth()) {
			if (heights.toEast()) {
				if (heights.toNorthEast()) {
					// 32
					blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
				} else {
					// 13
					// should be steps
					blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
				}
			} else {
				// 26
				drawFoundationHeadingWestBit(blocks, 13, y1, 0, 3);
			}
		} else {
			if (heights.toEast()) {
				// 10
				drawFoundationHeadingSouthBit(blocks, 13, y1, 0, 3);
			} else {
				// 3
				// should be steps
				blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
			}
		}

		// West
		if (heights.toWest()) {
			// 16
			blocks.setBlocks(0, 3, y1, y2, 3, 13, foundationMaterial);
		} else {
			// 4
			drawFoundationHeadingEastBit(blocks, 0, y1, 3, 10);
		}

		// Center
		// 5
		blocks.setBlocks(3, 13, y1, y2, 3, 13, foundationMaterial);

		// East
		if (heights.toEast()) {
			// 15
			blocks.setBlocks(13, 16, y1, y2, 3, 13, foundationMaterial);
		} else {
			// 6
			drawFoundationHeadingWestBit(blocks, 13, y1, 3, 10);
		}

		// SouthWest
		if (heights.toSouth()) {
			if (heights.toWest()) {
				if (heights.toSouthWest()) {
					// 31
					blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
				} else {
					// 20
					// should be steps
					blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
				}
			} else {
				// 7
				drawFoundationHeadingEastBit(blocks, 0, y1, 13, 3);
			}
		} else {
			if (heights.toWest()) {
				// 22
				drawFoundationHeadingNorthBit(blocks, 0, y1, 13, 3);
			} else {
				// 17
				// should be steps
				blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
			}
		}

		// South
		if (heights.toSouth()) {
			// 8
			blocks.setBlocks(3, 13, y1, y2, 13, 16, foundationMaterial);
		} else {
			// 18
			drawFoundationHeadingNorthBit(blocks, 3, y1, 13, 10);
		}

		// SouthEast
		if (heights.toSouth()) {
			if (heights.toEast()) {
				if (heights.toSouthEast()) {
					// 30
					blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
				} else {
					// 21
					// should be steps
					blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
				}
			} else {
				// 9
				drawFoundationHeadingWestBit(blocks, 13, y1, 13, 3);
			}
		} else {
			if (heights.toEast()) {
				// 19
				drawFoundationHeadingNorthBit(blocks, 13, y1, 13, 3);
			} else {
				// 23
				// should be steps
				blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
			}
		}
	}

	// 18 & 19
	private void drawFoundationHeadingNorthBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setBlocks(x, x + l, y, z + 1, z + 2, foundationSteps, Direction.NORTH);
		blocks.setBlocks(x, x + l, y, z, z + 1, foundationMaterial);
		blocks.setBlocks(x, x + l, y + 1, z, z + 1, foundationSteps, Direction.NORTH);
	}

	// 2 & 10
	private void drawFoundationHeadingSouthBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setBlocks(x, x + l, y, z + 1, z + 2, foundationSteps, Direction.SOUTH);
		blocks.setBlocks(x, x + l, y, z + 2, z + 3, foundationMaterial);
		blocks.setBlocks(x, x + l, y + 1, z + 2, z + 3, foundationSteps, Direction.SOUTH);
	}

	// 6 & 9
	private void drawFoundationHeadingWestBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setBlocks(x + 1, x + 2, y, z, z + l, foundationSteps, Direction.WEST);
		blocks.setBlocks(x, x + 1, y, z, z + l, foundationMaterial);
		blocks.setBlocks(x, x + 1, y + 1, z, z + l, foundationSteps, Direction.WEST);
	}

	// 4 & 7
	private void drawFoundationHeadingEastBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setBlocks(x + 1, x + 2, y, z, z + l, foundationSteps, Direction.EAST);
		blocks.setBlocks(x + 2, x + 3, y, z, z + l, foundationMaterial);
		blocks.setBlocks(x + 2, x + 3, y + 1, z, z + l, foundationSteps, Direction.EAST);
	}

	private void drawFoundationColumns(SupportBlocks blocks, int y1, int height, Surroundings heights) {
		int y2 = y1 + height;

		if (!heights.toNorthWest())
			drawColumn(blocks, 1, y1, y2, 1);
		if (!heights.toNorthEast())
			drawColumn(blocks, 14, y1, y2, 1);
		if (!heights.toSouthWest())
			drawColumn(blocks, 1, y1, y2, 14);
		if (!heights.toSouthEast())
			drawColumn(blocks, 14, y1, y2, 14);

		if (!heights.toNorth()) {
			drawColumn(blocks, 4, y1, y2, 1);
			drawColumn(blocks, 6, y1, y2, 1);
			drawColumn(blocks, 9, y1, y2, 1);
			drawColumn(blocks, 11, y1, y2, 1);
		}

		if (!heights.toSouth()) {
			drawColumn(blocks, 4, y1, y2, 14);
			drawColumn(blocks, 6, y1, y2, 14);
			drawColumn(blocks, 9, y1, y2, 14);
			drawColumn(blocks, 11, y1, y2, 14);
		}

		if (!heights.toWest()) {
			drawColumn(blocks, 1, y1, y2, 4);
			drawColumn(blocks, 1, y1, y2, 6);
			drawColumn(blocks, 1, y1, y2, 9);
			drawColumn(blocks, 1, y1, y2, 11);
		}

		if (!heights.toEast()) {
			drawColumn(blocks, 14, y1, y2, 4);
			drawColumn(blocks, 14, y1, y2, 6);
			drawColumn(blocks, 14, y1, y2, 9);
			drawColumn(blocks, 14, y1, y2, 11);
		}
	}

	private void drawColumn(SupportBlocks blocks, int x, int y1, int y2, int z) {
		blocks.setBlocks(x, y1, y2, z, columnMaterial);
	}

	@Override
	protected void drawRoof(VocationCityWorldGenerator generator, RealBlocks chunk, DataContext context, int y1, int insetNS,
			int insetWE, int floor, boolean allowRounded, boolean outsetEffect, Block material, Surroundings heights,
			RoofStyle thisStyle, RoofFeature thisFeature) {
		if (doBuilding(heights)) {
			super.drawRoof(generator, chunk, context, y1 + higher, insetNS - 2, insetWE - 2, floor, allowRounded,
					outsetEffect, Blocks.LAPIS_BLOCK, heights, thisStyle, thisFeature);
		}
	}
}
