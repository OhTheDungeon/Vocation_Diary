package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.BuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;

public class ConcreteLot extends BuildingLot {

	//	more CenterStyles {...WATER_MAZE, WATER_CHECKER, WATER_CIRCLE, WATER_LABYRINTH};
	public enum CenterStyle {
		EMPTY, QUIET_ZONE, ART_ZONE, CHECKER_ART, SHALLOW_POND, ROUND_POND, PYRAMID_POND, CHECKER_POND, UPWARD_POND,
		DOWNWARD_POND
	}

	private final CenterStyle centerStyle;

	public ConcreteLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		trulyIsolated = true;
		centerStyle = getRandomCenterStyle();
	}

	private CenterStyle getRandomCenterStyle() {
		if (chunkOdds.playOdds(Odds.oddsUnlikely))
			return CenterStyle.EMPTY;
		else {
			CenterStyle[] values = CenterStyle.values();
			CenterStyle result = values[chunkOdds.getRandomInt(values.length)];
			if (result == CenterStyle.CHECKER_ART) // reduce the chances of checker art
				result = values[chunkOdds.getRandomInt(values.length)];
			return result;
		}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ConcreteLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
//		int sidewalkLevel = getSidewalkLevel(generator);
//		Material sidewalkMaterial = getSidewalkMaterial();

		// top it off
		flattenLot(generator, chunk, 4);
//		chunk.setLayer(sidewalkLevel - 3, 3, generator.oreProvider.surfaceMaterial);
//		chunk.setLayer(sidewalkLevel, sidewalkMaterial);
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int sidewalkLevel = getSidewalkLevel(generator);
		Block sidewalkMaterial = getSidewalkMaterial();
		Block fluid = generator.oreProvider.fluidMaterial;
		Block atmosphere = generator.shapeProvider.findAtmosphereMaterialAt(generator, sidewalkLevel);
		Block underneath = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_BuildingCeilings, chunkOdds);

		chunk.setLayer(sidewalkLevel - 3, 3, underneath);
		chunk.setLayer(sidewalkLevel, sidewalkMaterial);

//		chunk.setBlock(8, sidewalkLevel + 20, 8, Blocks.COBBLESTONE);
//		chunk.setSignPost(8, sidewalkLevel + 21, 8, BlockFace.NORTH, "Style", centerStyle.toString());

		switch (centerStyle) {
		default:
		case EMPTY:
			// nothing needed here
			break;
		case QUIET_ZONE:
			generateSittingArea(chunk, sidewalkLevel, atmosphere, underneath);
			chunk.setBlocks(5, 11, sidewalkLevel - 1, 5, 11, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			break;
		case ART_ZONE:
			generateSittingArea(chunk, sidewalkLevel, atmosphere, underneath);
			chunk.setBlocks(6, 10, sidewalkLevel, 6, 10, Blocks.QUARTZ_BLOCK);
			RoundaboutCenterLot.generateArt(chunk, chunkOdds, 6, sidewalkLevel, 6, Blocks.QUARTZ_BLOCK);
			break;
		case UPWARD_POND:
			chunk.setBlocks(3, 13, sidewalkLevel, 3, 13, Blocks.STONE_SLAB, SlabType.DOUBLE);
			chunk.setBlocks(4, 12, sidewalkLevel + 1, 4, 12, Blocks.STONE_SLAB);
			chunk.setBlocks(5, 11, sidewalkLevel + 1, 5, 11, Blocks.STONE_SLAB, SlabType.DOUBLE);
			chunk.setBlocks(6, 10, sidewalkLevel + 2, 6, 10, Blocks.STONE_SLAB);

			chunk.clearBlocks(7, 9, sidewalkLevel + 2, 7, 9);
			chunk.setBlocks(7, 9, sidewalkLevel + 1, 7, 9, fluid);
			break;
		case DOWNWARD_POND:
			chunk.setBlocks(3, 13, sidewalkLevel - 1, 3, 13, Blocks.STONE_SLAB, SlabType.DOUBLE);
			chunk.setBlocks(4, 12, sidewalkLevel - 1, 4, 12, Blocks.STONE_SLAB);
			chunk.setBlocks(5, 11, sidewalkLevel - 2, 5, 11, Blocks.STONE_SLAB, SlabType.DOUBLE);
			chunk.setBlocks(6, 10, sidewalkLevel - 2, 6, 10, Blocks.STONE_SLAB);

			chunk.clearBlocks(3, 13, sidewalkLevel, 3, 13);
			chunk.clearBlocks(5, 11, sidewalkLevel - 1, 5, 11);
			chunk.clearBlocks(7, 9, sidewalkLevel - 2, 7, 9);

			chunk.setBlocks(6, 10, sidewalkLevel - 4, sidewalkLevel - 2, 6, 10, underneath);
			chunk.setBlocks(7, 9, sidewalkLevel - 3, 7, 9, fluid);
			break;
		case SHALLOW_POND:
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.setWalls(3, 13, sidewalkLevel, 3, 13, underneath);
			chunk.setBlocks(4, 12, sidewalkLevel, 4, 12, fluid);
			if (chunkOdds.playOdds(Odds.oddsPrettyLikely)) {
				randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			} else {
				RoundaboutCenterLot.generateArt(chunk, chunkOdds, 6, sidewalkLevel, 6, Blocks.QUARTZ_BLOCK);
			}
			break;
		case PYRAMID_POND:
			int y = sidewalkLevel + 1;
			for (int i = 0; i < 6; i++) {
				if (i == 0) {
					chunk.setBlocks(2 + i, 14 - i, y, 2 + i, 14 - i, atmosphere);
				} else {
					chunk.setWalls(1 + i, 15 - i, y, 1 + i, 15 - i, underneath);
					chunk.setBlocks(2 + i, 14 - i, y, 2 + i, 14 - i, fluid);
					if (i == 5)
						chunk.setBlocks(3 + i, 13 - i, y - 1, 3 + i, 13 - i, underneath);
				}
				y--;
			}
			randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			break;
		case ROUND_POND:
			chunk.setLayer(sidewalkLevel - 1, 1, underneath);
			chunk.setCircle(8, 8, 5, sidewalkLevel, underneath, true);
			chunk.setCircle(8, 8, 4, sidewalkLevel, fluid, true);
			if (chunkOdds.playOdds(Odds.oddsPrettyLikely)) {
				randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			} else {
				RoundaboutCenterLot.generateArt(chunk, chunkOdds, 6, sidewalkLevel, 6, Blocks.QUARTZ_BLOCK);
			}
			break;
		case CHECKER_POND:
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.clearBlocks(2, 14, sidewalkLevel, 2, 14);
			boolean skip = false;
			for (int x = 2; x < 14; x += 3) {
				skip = !skip;
				for (int z = 2; z < 14; z += 3) {
					skip = !skip;
					if (!skip) {
						chunk.setBlocks(x, x + 3, sidewalkLevel - 1, z, z + 3, fluid);
						randomFountain(chunk, x + 1, sidewalkLevel - 1, z + 1, fluid);
					}
				}
			}
			break;
		case CHECKER_ART:
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.clearBlocks(2, 14, sidewalkLevel, 2, 14);
			boolean randomColor = chunkOdds.playOdds(Odds.oddsSomewhatUnlikely);
			Colors colors = new Colors(chunkOdds);
			Block thing = colors.getGlass();
			int inset = 0;
			for (int z = 3; z < 13; z++) {
				inset = inset == 1 ? 0 : 1;
				for (int x = 3; x < 13; x += 2) {
					chunk.setBlocks(x + inset, x + inset + 1, sidewalkLevel - 1,
							sidewalkLevel + chunkOdds.calcRandomRange(3, 5), z, z + 1, thing);
					if (randomColor)
						thing = colors.getGlass();
				}
			}
		}

		// it looked so nice for a moment... but the moment has passed
		if (generator.getWorldSettings().includeDecayedBuildings)
			destroyLot(generator, sidewalkLevel, sidewalkLevel + 4);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + DataContext.FloorHeight * 2 + 1;
	}

	private void randomFountain(RealBlocks chunk, int x, int y, int z, Block fluid) {
		if (chunkOdds.playOdds(Odds.oddsNearlyAlwaysGoingToHappen))
			chunk.setBlocks(x, y, y + chunkOdds.calcRandomRange(2, 4), z, fluid);
	}

	private void generateSittingArea(RealBlocks chunk, int y, Block atmosphere, Block underneath) {
		chunk.setLayer(y - 2, 2, underneath);
		chunk.setBlocks(3, 13, y, y + 1, 3, 13, atmosphere);
		if (chunkOdds.flipCoin()) {
			for (int i = 5; i < 11; i++) {
				chunk.setBlock(i, y, 3, Blocks.QUARTZ_STAIRS, Direction.NORTH);
				chunk.setBlock(i, y, 12, Blocks.QUARTZ_STAIRS, Direction.SOUTH);
				chunk.setBlock(3, y, i, Blocks.QUARTZ_STAIRS, Direction.WEST);
				chunk.setBlock(12, y, i, Blocks.QUARTZ_STAIRS, Direction.EAST);
			}
		} else {
			chunk.setStair(3, y, 3, Blocks.QUARTZ_STAIRS, Direction.NORTH, StairsShape.INNER_LEFT);
			chunk.setStair(12, y, 3, Blocks.QUARTZ_STAIRS, Direction.NORTH, StairsShape.INNER_RIGHT);
			chunk.setStair(3, y, 12, Blocks.QUARTZ_STAIRS, Direction.SOUTH, StairsShape.INNER_RIGHT);
			chunk.setStair(12, y, 12, Blocks.QUARTZ_STAIRS, Direction.SOUTH, StairsShape.INNER_LEFT);
			for (int i = 4; i < 7; i++) {
				chunk.setBlock(i, y, 3, Blocks.QUARTZ_STAIRS, Direction.NORTH);
				chunk.setBlock(15 - i, y, 3, Blocks.QUARTZ_STAIRS, Direction.NORTH);
				chunk.setBlock(i, y, 12, Blocks.QUARTZ_STAIRS, Direction.SOUTH);
				chunk.setBlock(15 - i, y, 12, Blocks.QUARTZ_STAIRS, Direction.SOUTH);

				chunk.setBlock(3, y, i, Blocks.QUARTZ_STAIRS, Direction.WEST);
				chunk.setBlock(3, y, 15 - i, Blocks.QUARTZ_STAIRS, Direction.WEST);
				chunk.setBlock(12, y, i, Blocks.QUARTZ_STAIRS, Direction.EAST);
				chunk.setBlock(12, y, 15 - i, Blocks.QUARTZ_STAIRS, Direction.EAST);
			}
		}
	}
}
