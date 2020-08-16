package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class MineEntranceLot extends ConstructLot {

	public MineEntranceLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new MineEntranceLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return 0;
	}

//	@Override
//	public int getTopY(CityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
//		return blockYs.getBlockY(x, z);// + generator.landRange;
////		return generator.streetLevel + DataContext.FloorHeight;
//	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		generator.reportLocation("Mine Entrance", chunk);

		// find the bottom of the world
		int shaftY = findHighestShaftableLevel(generator, context, chunk);
		shaftY = Math.max(2, shaftY); // make sure we don't go down too far
		int y = shaftY;
		int topY = blockYs.getMaxYWithin(0, 7, 0, 7) - 2;

		// spiral up
		while (y <= topY) {
			y = makeSpace(generator, chunk, chunkOdds, 4, y, 3);
			if (y > topY)
				break;
			y = makeSpace(generator, chunk, chunkOdds, 1, y, 4);
			if (y > topY)
				break;
			y = makeSpace(generator, chunk, chunkOdds, 0, y, 1);
			if (y > topY)
				break;
			y = makeSpace(generator, chunk, chunkOdds, 3, y, 0);
		}

		// place snow
		generateSurface(generator, chunk, false);
	}

	private int makeSpace(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y, int z) {
		chunk.airoutBlocks(generator, x, x + 3, y, y + 3, z, z + 3);
		generateMineFloor(chunk, x, x + 3, y - 1, z, z + 3);
		generateMineCeiling(chunk, x, x + 3, y + 2, z, z + 3);
		return y + 1;
	}
}
