package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;

public class OilPlatformLot extends ConstructLot {

	public OilPlatformLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OilPlatformLot(platmap, chunkX, chunkZ);
	}

	private final static Block headMaterial = Blocks.STONE_SLAB;
	private final static Block railingMaterial = Blocks.IRON_BARS;
	private final static Block drillSupportMaterial = Blocks.BIRCH_FENCE;
	private final static Block drillMaterial = Blocks.IRON_BARS;

	private final static int aboveSea = 6;

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.seaLevel + aboveSea;
	}

	//	@Override
//	public int getTopY(CityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
//		return getBottomY(generator) + DataContext.FloorHeight * 4 + 1;
//	}
//
	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return blockYs.getBlockY(x, z);// + generator.landRange;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		generator.reportLocation("Oil Platform", chunk);

		Block platformMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_OilPlatformFloor, chunkOdds, Blocks.STONE);
		Block supportMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_OilPlatformColumn, chunkOdds, Blocks.STONE);

		// working levels
		int y0 = generator.seaLevel;
		int y1 = y0 + aboveSea;
		int y2 = y1 + DataContext.FloorHeight;
		int y3 = y2 + DataContext.FloorHeight;
		int y4 = y3 + DataContext.FloorHeight;
//		Material emptyMaterial = getAirMaterial(generator, y1);

		generateSurface(generator, chunk, false);

		// access levels
		chunk.setBlocks(2, 6, y0, y0 + 1, 2, 6, platformMaterial);
		chunk.setBlocks(10, 14, y0, y0 + 1, 10, 14, platformMaterial);

		// lower level
		chunk.setLayer(y1, platformMaterial);
		chunk.setWalls(0, 16, y1 + 1, y1 + 2, 0, 16, railingMaterial);
		chunk.airoutBlocks(generator, 7, 9, y1, y1 + 1, 7, 9);
		chunk.setWalls(6, 10, y1 + 1, y1 + 2, 6, 10, railingMaterial);

		// upper level
		chunk.setLayer(y2, platformMaterial);
		chunk.setWalls(0, 16, y2 + 1, y2 + 2, 0, 16, railingMaterial);
		chunk.airoutBlocks(generator, 7, 9, y2, y2 + 1, 7, 9);
		chunk.setWalls(6, 10, y2 + 1, y2 + 2, 6, 10, railingMaterial);

		// put the balcony on top
		chunk.setBlocks(2, 14, y3, y3 + 1, 2, 14, platformMaterial);
		chunk.setWalls(2, 14, y3 + 1, y3 + 2, 2, 14, railingMaterial);
		chunk.airoutBlocks(generator, 7, 9, y3, y3 + 1, 7, 9);
		chunk.setWalls(6, 10, y3 + 1, y3 + 2, 6, 10, railingMaterial);

		// drill head level
		chunk.setBlocks(6, 14, y4, y4 + 1, 6, 14, platformMaterial);
		chunk.setWalls(6, 14, y4 + 1, y4 + 2, 6, 14, railingMaterial);
		chunk.airoutBlocks(generator, 6, 9, y4, y4 + 2, 6, 9);
		chunk.setBlocks(9, y3 + 1, y4 + 2, 6, supportMaterial);
		chunk.setBlocks(6, y3 + 1, y4 + 2, 9, supportMaterial);

		// drill head itself
		chunk.setBlock(9, y4 + 2, 6, platformMaterial);
		chunk.setBlock(6, y4 + 2, 9, platformMaterial);
		chunk.setBlock(7, y4, 7, headMaterial);
		chunk.setBlock(7, y4, 8, headMaterial);
		chunk.setBlock(8, y4, 7, headMaterial);
		chunk.setBlock(9, y4 + 3, 6, headMaterial);
		chunk.setBlock(9, y4 + 3, 7, headMaterial);
		chunk.setBlock(9, y4 + 3, 8, headMaterial);
		chunk.setBlock(6, y4 + 3, 9, headMaterial);
		chunk.setBlock(7, y4 + 3, 9, headMaterial);
		chunk.setBlock(8, y4 + 3, 9, headMaterial);
		chunk.setBlock(8, y4 + 3, 8, headMaterial, SlabType.DOUBLE);

		// two big legs to hold up the various levels (a little bit deeper than needed,
		// just to be safe)
		chunk.setBlocks(2, 4, blockYs.getMinHeight() - 10, y4, 2, 4, supportMaterial);
		chunk.setBlocks(12, 14, blockYs.getMinHeight() - 10, y4 + 3, 12, 14, supportMaterial);

		// two lesser legs to help the other two
		chunk.setBlocks(2, 4, blockYs.getMinHeight() - 10, y3, 12, 14, supportMaterial);
		chunk.setBlocks(12, 14, blockYs.getMinHeight() - 10, y3, 2, 4, supportMaterial);
		chunk.setBlocks(13, y3, y3 + 2, 2, supportMaterial);
		chunk.setBlocks(2, y3, y3 + 2, 13, supportMaterial);

		// drill down
		chunk.setWaterLoggedBlocks(8, blockYs.getBlockY(8, 8), generator.seaLevel + 1, 8, drillMaterial);
		chunk.setBlocks(8, generator.seaLevel + 1, y4 + 3, 8, drillMaterial);

		// extra drill bits
		for (int i = 5; i < 13; i++)
			drawExtraPipes(chunk, i, y3, 1);
//		chunk.setBlocks(5, y2 + 2, y3 + 2, 1, drillMaterial);
//		chunk.setBlocks(7, y2 + 2, y3 + 2, 1, drillMaterial);
//		chunk.setBlocks(9, y2 + 2, y3 + 2, 1, drillMaterial);
//		chunk.setBlocks(11, y2 + 2, y3 + 2, 1, drillMaterial);

		// bit hanging from the crane
		chunk.setBlocks(13, y4 + 7, y4 + 8, 2, drillSupportMaterial);
		chunk.setBlocks(13, y4 + 3, y4 + 7, 2, drillMaterial);

		// ladder from access level to the balcony
		chunk.setLadder(3, y0 + 1, y4 - 2, 4, Direction.SOUTH); // fixed
		chunk.setLadder(12, y0 + 1, y4 + 2, 11, Direction.NORTH); // fixed

		// now draw the crane
		chunk.clearBlock(2, y4 - 1, 2);
		chunk.clearBlock(2, y4 - 1, 3);
		chunk.setBlocks(2, y4 - 2, y4, 2, Blocks.IRON_BARS, Direction.EAST);
		chunk.drawCrane(context, chunkOdds, 3, y4, 2);

		// bleed off
		chunk.setWalls(12, 14, y4 + 3, y4 + 8, 12, 14, Blocks.IRON_BARS);
		chunk.setBlocks(12, 14, y4 + 8, 12, 14, Blocks.NETHERRACK);
		chunk.setBlocks(12, 14, y4 + 9, 12, 14, Blocks.FIRE);

		// it looked so nice for a moment... but the moment has passed
		if (generator.getWorldSettings().includeDecayedBuildings) {

			// do we take out a bit of it?
			decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y1,
					chunk.getBlockZ(0) + chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y2,
					chunk.getBlockZ(0) + chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y1,
					chunk.getBlockZ(15) - chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y2,
					chunk.getBlockZ(15) - chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(0) + chunkOdds.getRandomInt(2), y1,
					chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(0) + chunkOdds.getRandomInt(2), y2,
					chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(15) - chunkOdds.getRandomInt(2), y1,
					chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(15) - chunkOdds.getRandomInt(2), y2,
					chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(7), y4, chunk.getBlockZ(12));
		}
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, y2 + 1, 5);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, y3 + 1, 5);
	}

	private void drawExtraPipes(RealBlocks chunk, int x, int y, int z) {
		if (chunkOdds.playOdds(Odds.oddsVeryLikely)) {
			chunk.setBlock(x, y + 1, z, drillMaterial);
			chunk.setBlock(x, y, z, drillSupportMaterial, Direction.SOUTH);
			chunk.setBlocks(x, y - 2, y, z, drillMaterial);
		}
	}

	private final static double decayedEdgeOdds = 0.25;

	private void decayEdge(VocationCityWorldGenerator generator, int x, int y, int z) {
		if (chunkOdds.playOdds(decayedEdgeOdds))

			// make it go away
			generator.destroyArea(x, y, z, chunkOdds.getRandomInt(2) + 2);
	}
}
