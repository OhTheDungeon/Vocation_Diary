package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;

public class GravelworksLot extends GravelLot {

	public GravelworksLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {

		flattenLot(generator, chunk, 4);

		switch (chunkOdds.getRandomInt(10)) {
		case 0:

			// hole please
			generateBase(generator, chunk);
			generateHole(generator, chunkOdds, chunk, generator.streetLevel, chunkOdds.getRandomInt(6, 7),
					generator.streetLevel - chunkOdds.getRandomInt(5, 48));
			break;

		case 1:
		case 2:

			// storage
			generateBase(generator, chunk);
			generator.structureOnGroundProvider.generateShed(generator, chunk, context, chunkOdds, 7,
					generator.streetLevel + 1, 7, chunkOdds.getRandomInt(2, 2), LootLocation.STONEWORKS,
					LootLocation.STONEWORKS_OUTPUT);
			generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 7, generator.streetLevel + 1, 7);
			break;

		case 3:
		case 4:
		case 5:

			// clear off some space
			generateBase(generator, chunk);
			int x = chunkOdds.getRandomInt(3);
			int z = chunkOdds.getRandomInt(3);
			generateTailings(generator, chunkOdds, chunk, x, 15 - x, z, 15 - z);
			break;

		default:

			// create some piles of stuff
			generateBase(generator, chunk);
			generatePile(generator, chunkOdds, chunk, 3 + chunkOdds.getShimmy(), 3 + chunkOdds.getShimmy(), 4);
			generatePile(generator, chunkOdds, chunk, 3 + chunkOdds.getShimmy(), 9 + chunkOdds.getShimmy(), 4);
			generatePile(generator, chunkOdds, chunk, 9 + chunkOdds.getShimmy(), 3 + chunkOdds.getShimmy(), 4);
			generatePile(generator, chunkOdds, chunk, 9 + chunkOdds.getShimmy(), 9 + chunkOdds.getShimmy(), 4);

			break;
		}

		// place snow
		generateSurface(generator, chunk, false);

	}

}
