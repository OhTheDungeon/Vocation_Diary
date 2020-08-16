package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConnectedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageType;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.HeightInfo;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.MazeArray;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SurroundingLots;
import cn.shadow.vacation_diary.dimension.support.TreeSpecies;
import cn.shadow.vacation_diary.dimension.support.Trees;
import cn.shadow.vacation_diary.dimension.support.MazeArray.MazeBit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;

public class ParkLot extends ConnectedLot {

	private final static int cisternDepth = DataContext.FloorHeight * 4;
	private final static int groundDepth = 2;

	private final static Block cisternMaterial = Blocks.CLAY;
	private final static Block fenceMaterial = Blocks.SPRUCE_FENCE;
	private final static Block columnMaterial = Blocks.SMOOTH_STONE;
	private final static Block grassMaterial = Blocks.GRASS_BLOCK;
	private final static Block pathMaterial = Blocks.GRASS_PATH;
	private final static Block stepMaterial = Blocks.STONE_SLAB;
	private final static Block ledgeMaterial = Blocks.CLAY;

	// TODO NW/SE quarter partial circle sidewalks
	// TODO pond inside of circle sidewalks instead of tree
	// TODO park benches
	// TODO gazebos

	public enum CenterStyles {
		CROSS_PATH, CIRCLE_PATH, WATER_TOWER, HEDGE_MAZE, CIRCLE_MAZE, LABYRINTH_MAZE
	}

	private final CenterStyles centerStyle;

	protected int waterDepth;

	public ParkLot(PlatMap platmap, int chunkX, int chunkZ, long globalconnectionkey, int depth) {
		super(platmap, chunkX, chunkZ);

		// all parks are interconnected
		connectedkey = globalconnectionkey;
		style = LotStyle.STRUCTURE;

		// pick a style
		centerStyle = getRandomCenterStyle();
		waterDepth = depth;
	}

	private CenterStyles getRandomCenterStyle() {
		if (chunkOdds.playOdds(Odds.oddsSomewhatLikely)) {
			if (chunkOdds.playOdds(Odds.oddsExtremelyUnlikely))
				return CenterStyles.LABYRINTH_MAZE;
			else if (chunkOdds.playOdds(Odds.oddsExtremelyUnlikely))
				return CenterStyles.HEDGE_MAZE;
			else if (chunkOdds.playOdds(Odds.oddsExtremelyUnlikely))
				return CenterStyles.CIRCLE_MAZE;
			else if (chunkOdds.playOdds(Odds.oddsExtremelyUnlikely))
				return CenterStyles.WATER_TOWER;
			else
				return CenterStyles.CIRCLE_PATH;
		} else
			return CenterStyles.CROSS_PATH;
	}

	public static int getWaterDepth(Odds odds) {
		return 1 + odds.getRandomInt(DataContext.FloorHeight * 2);
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ParkLot(platmap, chunkX, chunkZ, connectedkey, waterDepth);
	}

	@Override
	protected boolean isShaftableLevel(VocationCityWorldGenerator generator, int blockY) {
		return blockY >= 0 && blockY < generator.streetLevel - cisternDepth - 2 - 16;
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof ParkLot) {
			ParkLot relativebuilding = (ParkLot) relative;

			// we don't card about circleSidewalk, that is supposed to be different
			waterDepth = relativebuilding.waterDepth;
		}
		return result;
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel - cisternDepth + 1;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

	}

	private final static CoverageType[] smallTrees = { CoverageType.SHORT_BIRCH_TREE, CoverageType.SHORT_OAK_TREE,
			CoverageType.SHORT_PINE_TREE, CoverageType.BIRCH_TREE, CoverageType.OAK_TREE,
			CoverageType.TALL_BIRCH_TREE };

	private final static CoverageType[] tallTrees = { CoverageType.TALL_BIRCH_TREE, CoverageType.DARK_OAK_TREE };

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		// look around
		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);

		// starting with the bottom
		int lowestY = getBottomY(generator);
		int highestY = generator.streetLevel - groundDepth - 1;

		// cistern?
		if (generator.getWorldSettings().includeCisterns) {
			chunk.setLayer(lowestY, cisternMaterial);

			// fill with water
			lowestY++;
			if (generator.getWorldSettings().includeAbovegroundFluids)
				chunk.setBlocks(0, chunk.width, lowestY, lowestY + waterDepth, 0, chunk.width,
						generator.oreProvider.fluidMaterial);

			// clear out the rest
			chunk.airoutBlocks(generator, 0, chunk.width, lowestY + waterDepth, highestY + 1, 0, chunk.width, true);

			// outer columns and walls as needed
			if (neighbors.toNorth()) {
				chunk.setBlocks(3, 5, lowestY, highestY, 0, 1, cisternMaterial);
				chunk.setBlocks(11, 13, lowestY, highestY, 0, 1, cisternMaterial);
			} else
				chunk.setBlocks(0, 16, lowestY, highestY + 1, 0, 1, cisternMaterial);
			if (neighbors.toSouth()) {
				chunk.setBlocks(3, 5, lowestY, highestY, 15, 16, cisternMaterial);
				chunk.setBlocks(11, 13, lowestY, highestY, 15, 16, cisternMaterial);
			} else
				chunk.setBlocks(0, 16, lowestY, highestY + 1, 15, 16, cisternMaterial);
			if (neighbors.toWest()) {
				chunk.setBlocks(0, 1, lowestY, highestY, 3, 5, cisternMaterial);
				chunk.setBlocks(0, 1, lowestY, highestY, 11, 13, cisternMaterial);
			} else
				chunk.setBlocks(0, 1, lowestY, highestY + 1, 0, 16, cisternMaterial);
			if (neighbors.toEast()) {
				chunk.setBlocks(15, 16, lowestY, highestY, 3, 5, cisternMaterial);
				chunk.setBlocks(15, 16, lowestY, highestY, 11, 13, cisternMaterial);
			} else
				chunk.setBlocks(15, 16, lowestY, highestY + 1, 0, 16, cisternMaterial);

			// center columns
			chunk.setBlocks(7, 9, lowestY, highestY, 3, 5, cisternMaterial);
			chunk.setBlocks(7, 9, lowestY, highestY, 11, 13, cisternMaterial);
			chunk.setBlocks(3, 5, lowestY, highestY, 7, 9, cisternMaterial);
			chunk.setBlocks(11, 13, lowestY, highestY, 7, 9, cisternMaterial);

			// ceiling supports
			chunk.setBlocks(3, 5, highestY, highestY + 1, 0, 16, cisternMaterial);
			chunk.setBlocks(11, 13, highestY, highestY + 1, 0, 16, cisternMaterial);
			chunk.setBlocks(0, 16, highestY, highestY + 1, 3, 5, cisternMaterial);
			chunk.setBlocks(0, 16, highestY, highestY + 1, 11, 13, cisternMaterial);

			// top it off
			chunk.setLayer(highestY + 1, cisternMaterial);
		} else {

			// backfill with dirt
			chunk.setLayer(lowestY, highestY + 2 - lowestY, generator.oreProvider.subsurfaceMaterial);
		}
		// top it off
		chunk.setLayer(highestY + 2, generator.oreProvider.subsurfaceMaterial);
		chunk.setLayer(highestY + 3, generator.oreProvider.surfaceMaterial);

		TreeSpecies species = chunkOdds.getRandomWoodSpecies();
		Block logMaterial = Trees.getRandomWoodLog(species);
		Block leafMaterial = Trees.getRandomWoodLeaves(species);

		// surface features
		int surfaceY = generator.streetLevel + 1;
		switch (centerStyle) {
		case LABYRINTH_MAZE:
		case HEDGE_MAZE:
		case CIRCLE_MAZE:
			//TODO @@ putting leaves within range of a log should prevent decay but it doesn't WHY?
			chunk.setLeafWalls(0, 16, surfaceY, surfaceY + 3, 0, 16, leafMaterial, true);
			chunk.setWalls(0, 16, surfaceY - 1, surfaceY, 0, 16, logMaterial);

			if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
				chunk.clearBlocks(6, 10, surfaceY, surfaceY + 3, 0, 1);
				chunk.setBlocks(6, surfaceY, surfaceY + 2, 0, columnMaterial);
				chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 0, 1, stepMaterial);
				chunk.setBlocks(9, surfaceY, surfaceY + 2, 0, columnMaterial);
			} else if (neighbors.toNorth()) {
				chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 1, grassMaterial);
				chunk.clearBlocks(7, 9, surfaceY, surfaceY + 3, 0, 1);
			}
			if (!neighbors.toSouth() && HeightInfo.isBuildableToSouth(generator, chunk)) {
				chunk.clearBlocks(6, 10, surfaceY, surfaceY + 3, 15, 16);
				chunk.setBlocks(6, surfaceY, surfaceY + 2, 15, columnMaterial);
				chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 15, 16, stepMaterial);
				chunk.setBlocks(9, surfaceY, surfaceY + 2, 15, columnMaterial);
			} else if (neighbors.toSouth()) {
				chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 15, 16, grassMaterial);
				chunk.clearBlocks(7, 9, surfaceY, surfaceY + 3, 15, 16);
			}
			if (!neighbors.toWest() && HeightInfo.isBuildableToWest(generator, chunk)) {
				chunk.clearBlocks(0, 1, surfaceY, surfaceY + 3, 6, 10);
				chunk.setBlocks(0, surfaceY, surfaceY + 2, 6, columnMaterial);
				chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 7, 9, stepMaterial);
				chunk.setBlocks(0, surfaceY, surfaceY + 2, 9, columnMaterial);
			} else if (neighbors.toWest()) {
				chunk.setBlocks(0, 1, surfaceY - 1, surfaceY, 7, 9, grassMaterial);
				chunk.clearBlocks(0, 1, surfaceY, surfaceY + 3, 7, 9);
			}
			if (!neighbors.toEast() && HeightInfo.isBuildableToEast(generator, chunk)) {
				chunk.clearBlocks(15, 16, surfaceY, surfaceY + 3, 6, 10);
				chunk.setBlocks(15, surfaceY, surfaceY + 2, 6, columnMaterial);
				chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 7, 9, stepMaterial);
				chunk.setBlocks(15, surfaceY, surfaceY + 2, 9, columnMaterial);
			} else if (neighbors.toEast()) {
				chunk.setBlocks(0, 1, surfaceY - 1, surfaceY, 7, 9, grassMaterial);
				chunk.clearBlocks(0, 1, surfaceY, surfaceY + 3, 7, 9);
			}
			break;
		case CIRCLE_PATH:
		case CROSS_PATH:
		case WATER_TOWER:
		default:
			boolean fenceNorth = false;
			boolean fenceSouth = false;
			boolean fenceWest = false;
			boolean fenceEast = false;

			// [ ][X][ ]
			// [ ][ ][ ]
			// [ ][ ][ ]
			if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
				chunk.setBlocks(1, 6, surfaceY, surfaceY + 1, 0, 1, columnMaterial);
				chunk.setBlocks(10, 15, surfaceY, surfaceY + 1, 0, 1, columnMaterial);

				chunk.setBlocks(6, surfaceY, surfaceY + 2, 0, columnMaterial);
				chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 0, 1, stepMaterial);
				chunk.setBlocks(9, surfaceY, surfaceY + 2, 0, columnMaterial);
				chunk.setBlock(6, surfaceY, 1, columnMaterial);
				chunk.setBlock(9, surfaceY, 1, columnMaterial);

				fenceNorth = true;
				chunk.setBlocks(1, 6, surfaceY + 1, surfaceY + 2, 0, 1, fenceMaterial, Direction.EAST, Direction.WEST);
				chunk.setBlocks(10, 15, surfaceY + 1, surfaceY + 2, 0, 1, fenceMaterial, Direction.EAST,
						Direction.WEST);
			}

			// [ ][ ][ ]
			// [ ][ ][ ]
			// [ ][X][ ]
			if (!neighbors.toSouth() && HeightInfo.isBuildableToSouth(generator, chunk)) {
				chunk.setBlocks(1, 6, surfaceY, surfaceY + 1, 15, 16, columnMaterial);
				chunk.setBlocks(10, 15, surfaceY, surfaceY + 1, 15, 16, columnMaterial);

				chunk.setBlocks(6, surfaceY, surfaceY + 2, 15, columnMaterial);
				chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 15, 16, stepMaterial);
				chunk.setBlocks(9, surfaceY, surfaceY + 2, 15, columnMaterial);
				chunk.setBlock(6, surfaceY, 14, columnMaterial);
				chunk.setBlock(9, surfaceY, 14, columnMaterial);

				fenceSouth = true;
				chunk.setBlocks(1, 6, surfaceY + 1, surfaceY + 2, 15, 16, fenceMaterial, Direction.EAST,
						Direction.WEST);
				chunk.setBlocks(10, 15, surfaceY + 1, surfaceY + 2, 15, 16, fenceMaterial, Direction.EAST,
						Direction.WEST);
			}

			// [ ][ ][ ]
			// [X][ ][ ]
			// [ ][ ][ ]
			if (!neighbors.toWest() && HeightInfo.isBuildableToWest(generator, chunk)) {
				chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 1, 6, columnMaterial);
				chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 10, 15, columnMaterial);

				chunk.setBlocks(0, surfaceY, surfaceY + 2, 6, columnMaterial);
				chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 7, 9, stepMaterial);
				chunk.setBlocks(0, surfaceY, surfaceY + 2, 9, columnMaterial);
				chunk.setBlock(1, surfaceY, 6, columnMaterial);
				chunk.setBlock(1, surfaceY, 9, columnMaterial);

				fenceWest = true;
				chunk.setBlocks(0, 1, surfaceY + 1, surfaceY + 2, 1, 6, fenceMaterial, Direction.NORTH,
						Direction.SOUTH);
				chunk.setBlocks(0, 1, surfaceY + 1, surfaceY + 2, 10, 15, fenceMaterial, Direction.NORTH,
						Direction.SOUTH);
			}

			// [ ][ ][ ]
			// [ ][ ][X]
			// [ ][ ][ ]
			if (!neighbors.toEast() && HeightInfo.isBuildableToEast(generator, chunk)) {
				chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 1, 6, columnMaterial);
				chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 10, 15, columnMaterial);

				chunk.setBlocks(15, surfaceY, surfaceY + 2, 6, columnMaterial);
				chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 7, 9, stepMaterial);
				chunk.setBlocks(15, surfaceY, surfaceY + 2, 9, columnMaterial);
				chunk.setBlock(14, surfaceY, 6, columnMaterial);
				chunk.setBlock(14, surfaceY, 9, columnMaterial);

				fenceEast = true;
				chunk.setBlocks(15, 16, surfaceY + 1, surfaceY + 2, 1, 6, fenceMaterial, Direction.NORTH,
						Direction.SOUTH);
				chunk.setBlocks(15, 16, surfaceY + 1, surfaceY + 2, 10, 15, fenceMaterial, Direction.NORTH,
						Direction.SOUTH);
			}

			// [X][ ][ ]
			// [ ][ ][ ]
			// [ ][ ][ ]
			if (fenceNorth || fenceWest) {
				chunk.setBlock(0, surfaceY, 0, columnMaterial);
				if (fenceNorth && fenceWest) {
					chunk.setBlock(0, surfaceY + 1, 0, fenceMaterial, Direction.EAST, Direction.SOUTH);
				} else if (fenceNorth) {
					if (neighbors.toWest()) {
						chunk.setBlock(0, surfaceY + 1, 0, fenceMaterial, Direction.EAST, Direction.WEST);
					} else {
						chunk.setBlock(0, surfaceY + 1, 0, fenceMaterial, Direction.EAST);
					}
				} else { // fenceWest
					if (neighbors.toNorth()) {
						chunk.setBlock(0, surfaceY + 1, 0, fenceMaterial, Direction.NORTH, Direction.SOUTH);
					} else {
						chunk.setBlock(0, surfaceY + 1, 0, fenceMaterial, Direction.SOUTH);
					}
				}
			} else if (!neighbors.toNorthWest() && neighbors.toNorth() && neighbors.toWest()) {
				// concave angle
				chunk.setBlock(0, surfaceY, 0, columnMaterial);
				chunk.setBlock(0, surfaceY + 1, 0, fenceMaterial, Direction.NORTH, Direction.WEST);
			}

			// [ ][ ][ ]
			// [ ][ ][ ]
			// [X][ ][ ]
			if (fenceSouth || fenceWest) {
				chunk.setBlock(0, surfaceY, 15, columnMaterial);
				if (fenceSouth && fenceWest) {
					chunk.setBlock(0, surfaceY + 1, 15, fenceMaterial, Direction.EAST, Direction.NORTH);
				} else if (fenceSouth) {
					if (neighbors.toWest()) {
						chunk.setBlock(0, surfaceY + 1, 15, fenceMaterial, Direction.EAST, Direction.WEST);
					} else {
						chunk.setBlock(0, surfaceY + 1, 15, fenceMaterial, Direction.EAST);
					}
				} else { // fenceWest
					if (neighbors.toSouth()) {
						chunk.setBlock(0, surfaceY + 1, 15, fenceMaterial, Direction.NORTH, Direction.SOUTH);
					} else {
						chunk.setBlock(0, surfaceY + 1, 15, fenceMaterial, Direction.NORTH);
					}
				}
			} else if (!neighbors.toSouthWest() && neighbors.toSouth() && neighbors.toWest()) {
				// concave angle
				chunk.setBlock(0, surfaceY, 15, columnMaterial);
				chunk.setBlock(0, surfaceY + 1, 15, fenceMaterial, Direction.SOUTH, Direction.WEST);
			}

			// [ ][ ][X]
			// [ ][ ][ ]
			// [ ][ ][ ]
			if (fenceNorth || fenceEast) {
				chunk.setBlock(15, surfaceY, 0, columnMaterial);
				if (fenceNorth && fenceEast) {
					chunk.setBlock(15, surfaceY + 1, 0, fenceMaterial, Direction.WEST, Direction.SOUTH);
				} else if (fenceNorth) {
					if (neighbors.toEast()) {
						chunk.setBlock(15, surfaceY + 1, 0, fenceMaterial, Direction.EAST, Direction.WEST);
					} else {
						chunk.setBlock(15, surfaceY + 1, 0, fenceMaterial, Direction.WEST);
					}
				} else { // fenceEast
					if (neighbors.toNorth()) {
						chunk.setBlock(15, surfaceY + 1, 0, fenceMaterial, Direction.NORTH, Direction.SOUTH);
					} else {
						chunk.setBlock(15, surfaceY + 1, 0, fenceMaterial, Direction.SOUTH);
					}
				}
			} else if (!neighbors.toNorthEast() && neighbors.toNorth() && neighbors.toEast()) {
				// concave angle
				chunk.setBlock(15, surfaceY, 0, columnMaterial);
				chunk.setBlock(15, surfaceY + 1, 0, fenceMaterial, Direction.NORTH, Direction.EAST);
			}

			// [ ][ ][ ]
			// [ ][ ][ ]
			// [ ][ ][X]
			if (fenceSouth || fenceEast) {
				chunk.setBlock(15, surfaceY, 15, columnMaterial);
				if (fenceSouth && fenceEast) {
					chunk.setBlock(15, surfaceY + 1, 15, fenceMaterial, Direction.WEST, Direction.NORTH);
				} else if (fenceSouth) {
					if (neighbors.toEast()) {
						chunk.setBlock(15, surfaceY + 1, 15, fenceMaterial, Direction.EAST, Direction.WEST);
					} else {
						chunk.setBlock(15, surfaceY + 1, 15, fenceMaterial, Direction.WEST);
					}
				} else { // fenceEast
					if (neighbors.toSouth()) {
						chunk.setBlock(15, surfaceY + 1, 15, fenceMaterial, Direction.NORTH, Direction.SOUTH);
					} else {
						chunk.setBlock(15, surfaceY + 1, 15, fenceMaterial, Direction.NORTH);
					}
				}
			} else if (!neighbors.toSouthEast() && neighbors.toSouth() && neighbors.toEast()) {
				// concave angle
				chunk.setBlock(15, surfaceY, 15, columnMaterial);
				chunk.setBlock(15, surfaceY + 1, 15, fenceMaterial, Direction.SOUTH, Direction.EAST);
			}

			break;
		}

		// draw center bits
		switch (centerStyle) {
		case CIRCLE_PATH:
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 3, pathMaterial);
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 13, 16, pathMaterial);
			chunk.setBlocks(0, 3, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			chunk.setBlocks(13, 16, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			chunk.setCircle(8, 8, 4, surfaceY - 1, pathMaterial, false);
			chunk.setCircle(8, 8, 3, surfaceY - 1, pathMaterial, false);
			break;
		case LABYRINTH_MAZE:
		case CIRCLE_MAZE:
		case HEDGE_MAZE:
			// nothing for this one
			break;
		case CROSS_PATH:
		case WATER_TOWER:
		default:
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 8, pathMaterial);
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 8, 16, pathMaterial);
			chunk.setBlocks(0, 8, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			chunk.setBlocks(8, 16, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			break;
		}
//		int surfaceY = generator.streetLevel + 1;

		// if things are bad
		if (generator.getWorldSettings().includeDecayedBuildings) {
			destroyLot(generator, surfaceY - 2, surfaceY + 2);
		} else {
			// draw center bits
			switch (centerStyle) {
			case WATER_TOWER:
				generator.structureOnGroundProvider.drawWaterTower(generator, chunk, 4, surfaceY, 4, chunkOdds);
				generateSurface(generator, chunk, false);
				break;
			case LABYRINTH_MAZE:
				chunk.setBlocks(1, 15, surfaceY - 1, 1, 15, Blocks.BLUE_TERRACOTTA);
				startLabyrinth(chunk, 6, surfaceY - 1, 14, Blocks.CYAN_TERRACOTTA); // a
				contLabyrinth(chunk, 1, 14); // b
				contLabyrinth(chunk, 1, 1); // c
				contLabyrinth(chunk, 14, 1); // d
				contLabyrinth(chunk, 14, 14); // e
				contLabyrinth(chunk, 8, 14); // f
				contLabyrinth(chunk, 8, 12); // g
				contLabyrinth(chunk, 3, 12); // h
				contLabyrinth(chunk, 3, 3); // i
				contLabyrinth(chunk, 12, 3); // j
				contLabyrinth(chunk, 12, 12); // k
				contLabyrinth(chunk, 10, 12); // l
				contLabyrinth(chunk, 10, 10); // m
				contLabyrinth(chunk, 5, 10); // n
				contLabyrinth(chunk, 5, 5); // o
				contLabyrinth(chunk, 10, 5); // p
				contLabyrinth(chunk, 10, 8); // q
				contLabyrinth(chunk, 7, 8); // r
				contLabyrinth(chunk, 7, 7); // s
				changeColor(Blocks.LIGHT_BLUE_TERRACOTTA);
				contLabyrinth(chunk, 8, 7); // t
				break;
			case CIRCLE_MAZE:
				//TODO @@ putting leaves within range of a log should prevent decay but it doesn't WHY?
				chunk.setLeafWalls(2, 14, surfaceY, surfaceY + 2, 2, 14, leafMaterial, true);
				chunk.setWalls(2, 14, surfaceY - 1, surfaceY, 2, 14, logMaterial);
				chunk.setLeafWalls(4, 12, surfaceY, surfaceY + 3, 4, 12, leafMaterial, true);
				chunk.setWalls(4, 12, surfaceY - 1, surfaceY, 4, 12, logMaterial);
				chunk.setLeafWalls(6, 10, surfaceY, surfaceY + 4, 6, 10, leafMaterial, true);
				chunk.setWalls(6, 10, surfaceY - 1, surfaceY, 6, 10, logMaterial);

				pokeHoleSomewhere(chunk, 3, 13, surfaceY, 2, 3);
				pokeHoleSomewhere(chunk, 5, 11, surfaceY, 4, 5);
				pokeHoleSomewhere(chunk, 7, 9, surfaceY, 6, 7);
				pokeHoleSomewhere(chunk, 7, 9, surfaceY, 9, 10);
				pokeHoleSomewhere(chunk, 5, 11, surfaceY, 11, 12);
				pokeHoleSomewhere(chunk, 3, 13, surfaceY, 13, 14);

				pokeHoleSomewhere(chunk, 2, 3, surfaceY, 3, 13);
				pokeHoleSomewhere(chunk, 4, 5, surfaceY, 5, 11);
				pokeHoleSomewhere(chunk, 6, 7, surfaceY, 7, 9);
				pokeHoleSomewhere(chunk, 9, 10, surfaceY, 7, 9);
				pokeHoleSomewhere(chunk, 11, 12, surfaceY, 5, 11);
				pokeHoleSomewhere(chunk, 13, 14, surfaceY, 3, 13);

				break;
			case HEDGE_MAZE:
				MazeArray maze = new MazeArray(chunkOdds, 11, 11);
				for (int x = 1; x < 6; x++)
					for (int z = 1; z < 6; z++) {
						int x1 = (x - 1) * 3 + 1;
						int x2 = x * 3;
						int z1 = (z - 1) * 3 + 1;
						int z2 = z * 3;
						int xWall = x * 2;
						int zWall = z * 2;
						//TODO @@putting leaves within range of a log should prevent decay but it doesn't WHY?
						if ((x < 5) && (maze.getBit(xWall, zWall - 1) == MazeBit.WALL)) {
							chunk.setLeaves(x2, x2 + 1, surfaceY, surfaceY + 3, z1, z2, leafMaterial, true);
							chunk.setBlocks(x2, x2 + 1, surfaceY - 1, surfaceY, z1, z2, logMaterial);
						}
						if ((z < 5) && (maze.getBit(xWall - 1, zWall) == MazeBit.WALL)) {
							chunk.setLeaves(x1, x2, surfaceY, surfaceY + 3, z2, z2 + 1, leafMaterial, true);
							chunk.setBlocks(x1, x2, surfaceY - 1, surfaceY, z2, z2 + 1, logMaterial);
						}
						if ((x < 5) && (z < 5)) {
							chunk.setLeaves(x2, surfaceY, surfaceY + 3, z2, leafMaterial, true);
							chunk.setBlocks(x2, surfaceY - 1, surfaceY, z2, logMaterial);
						}
					}
				break;
			case CIRCLE_PATH:
				generateTreePark(generator, chunk, surfaceY, 6, true);
				break;
			case CROSS_PATH:
				generateTreePark(generator, chunk, surfaceY, 7, false);
				break;
			default:
				for (int x = 4; x < 12; x += 7)
					for (int z = 4; z < 12; z += 7)
						generator.coverProvider.generateRandomCoverage(generator, chunk, x, surfaceY, z, smallTrees);
				generateSurface(generator, chunk, false);
				break;
			}

			// way down?
			if (generator.getWorldSettings().includeCisterns) {
//				SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);
				if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
//					int lowestY = generator.streetLevel - cisternDepth + 1 + waterDepth;
					lowestY = generator.streetLevel - cisternDepth + 1 + waterDepth;
					chunk.setBlocks(4, 7, lowestY, lowestY + 1, 1, 2, ledgeMaterial);
					chunk.setLadder(5, lowestY + 1, surfaceY, 1, Direction.SOUTH); // fixed
					chunk.setBlock(5, surfaceY - 1, 1, Blocks.ACACIA_TRAPDOOR, Direction.WEST, Half.TOP);
				}
			}
		}
	}

	private void generateTreePark(VocationCityWorldGenerator generator, RealBlocks chunk, int surfaceY, int benchEnd,
			boolean singleTree) {
		boolean NW = chunkOdds.flipCoin();
		boolean NE = chunkOdds.flipCoin();
		boolean SW = chunkOdds.flipCoin();
		boolean SE = chunkOdds.flipCoin();

		Trees trees = new Trees(chunkOdds);
		Block stairs = trees.getRandomWoodStairs();

		int benchStart = chunkOdds.getRandomInt(3, 3);
		if (chunkOdds.flipCoin())
			benchEnd--;

		if (benchStart == 3) {
			// cornet bit
			if (NW) {
				chunk.setStair(3, surfaceY, 3, stairs, Direction.NORTH, StairsShape.INNER_LEFT);
			}
			if (NE) {
				chunk.setStair(12, surfaceY, 3, stairs, Direction.NORTH, StairsShape.INNER_RIGHT);
			}
			if (SW) {
				chunk.setStair(3, surfaceY, 12, stairs, Direction.SOUTH, StairsShape.INNER_RIGHT);
			}
			if (SE) {
				chunk.setStair(12, surfaceY, 12, stairs, Direction.SOUTH, StairsShape.INNER_LEFT);
			}
		}

		for (int i = benchStart; i < benchEnd; i++) {
			// corner bit
			if (i == 3) {
				continue;
			}

			if (NW) {
				chunk.setBlock(i, surfaceY, 3, stairs, Direction.NORTH);
				chunk.setBlock(3, surfaceY, i, stairs, Direction.WEST);
			}
			if (NE) {
				chunk.setBlock(15 - i, surfaceY, 3, stairs, Direction.NORTH);
				chunk.setBlock(3, surfaceY, 15 - i, stairs, Direction.WEST);
			}
			if (SW) {
				chunk.setBlock(i, surfaceY, 12, stairs, Direction.SOUTH);
				chunk.setBlock(12, surfaceY, i, stairs, Direction.EAST);
			}
			if (SE) {
				chunk.setBlock(15 - i, surfaceY, 12, stairs, Direction.SOUTH);
				chunk.setBlock(12, surfaceY, 15 - i, stairs, Direction.EAST);
			}
		}

		if (singleTree) {
			generator.coverProvider.generateRandomCoverage(generator, chunk, 7, surfaceY, 7, tallTrees);
		} else {
			if (!NW)
				generator.coverProvider.generateRandomCoverage(generator, chunk, 4, surfaceY, 4, smallTrees);
			if (!NE)
				generator.coverProvider.generateRandomCoverage(generator, chunk, 11, surfaceY, 4, smallTrees);
			if (!SW)
				generator.coverProvider.generateRandomCoverage(generator, chunk, 4, surfaceY, 11, smallTrees);
			if (!SE)
				generator.coverProvider.generateRandomCoverage(generator, chunk, 11, surfaceY, 11, smallTrees);
		}

		generateSurface(generator, chunk, false);

	}

	private void pokeHoleSomewhere(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		int x = chunkOdds.getRandomInt(x1, x2 - x1);
		int z = chunkOdds.getRandomInt(z1, z2 - z1);
		chunk.setBlock(x, y - 1, z, grassMaterial);
		chunk.clearBlocks(x, y, y + 4, z);
	}

	private int lastX;
	private int lastY;
	private int lastZ;
	private Block lastColor;

	private void startLabyrinth(RealBlocks chunk, int x, int y, int z, Block color) {
		lastX = x;
		lastY = y;
		lastZ = z;
		lastColor = color;
		chunk.setBlock(lastX, lastY, lastZ, lastColor);
	}

	private void changeColor(Block color) {
		lastColor = color;
	}

	private void contLabyrinth(RealBlocks chunk, int newX, int newZ) {
		if (lastX == newX) {
			if (lastZ < newZ) {
				for (int z = lastZ + 1; z < newZ; z++)
					chunk.setBlock(newX, lastY, z, lastColor);
			} else if (lastZ > newZ) {
				for (int z = newZ + 1; z < lastZ; z++)
					chunk.setBlock(newX, lastY, z, lastColor);
			}
		}
		if (lastZ == newZ) {
			if (lastX < newX) {
				for (int x = lastX + 1; x < newX; x++)
					chunk.setBlock(x, lastY, newZ, lastColor);
			} else if (lastX > newX) {
				for (int x = newX + 1; x < lastX; x++)
					chunk.setBlock(x, lastY, newZ, lastColor);
			}
		}
		lastX = newX;
		lastZ = newZ;
		chunk.setBlock(lastX, lastY, lastZ, lastColor);
	}
}
