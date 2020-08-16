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
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.entity.EntityType;

public class BeeHouseLot extends IsolatedLot {

	public BeeHouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub

	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BeeHouseLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		// if things are bad
		if (generator.getWorldSettings().includeDecayedBuildings) {
			destroyLot(generator, generator.streetLevel - 2, generator.streetLevel + 2);
		} else {
			generator.structureOnGroundProvider.drawBeeGreenHouse(generator, chunk, 4, generator.streetLevel + 1, 4,
					chunkOdds);
			generateSurface(generator, chunk, false);
			spawnBee(generator, chunk, generator.streetLevel + 2);
		}
	}
	
	private void spawnBee(VocationCityWorldGenerator generator, SupportBlocks chunk, int y) {
		EntityType<?> animal = EntityType.BEE;
		generator.spawnProvider.spawnAnimals(generator, chunk, chunkOdds, 5, y, 6, animal);
		generator.spawnProvider.spawnAnimals(generator, chunk, chunkOdds, 5, y, 7, animal);
//		generator.spawnProvider.spawnAnimal(generator, chunk, chunkOdds, 5, y, 8, animal);
//		generator.spawnProvider.spawnAnimal(generator, chunk, chunkOdds, 5, y, 9, animal);
	}


	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + 15;
	}
}
