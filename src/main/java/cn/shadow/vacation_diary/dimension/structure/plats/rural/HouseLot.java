package cn.shadow.vacation_diary.dimension.structure.plats.rural;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.IsolatedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Blocks;

public class HouseLot extends IsolatedLot {

	public HouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.STRUCTURE;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new HouseLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		// make room
		chunk.airoutLayer(generator, generator.streetLevel + 1, DataContext.FloorHeight * 2, 2, true);

		// ground please
		if (generator.getWorldSettings().includeDecayedNature)
			chunk.setLayer(generator.streetLevel, Blocks.SAND);
		else
			chunk.setLayer(generator.streetLevel, generator.oreProvider.surfaceMaterial);
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		// now make a house
		int atY = generator.streetLevel + 1;
		int floors = generator.structureOnGroundProvider.generateHouse(generator, chunk, context, chunkOdds, atY, 2);

		// not a happy place?
		if (generator.getWorldSettings().includeDecayedBuildings)
			destroyBuilding(generator, atY, floors);
		else
			generateSurface(generator, chunk, false);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, atY, 5);
	}

}
