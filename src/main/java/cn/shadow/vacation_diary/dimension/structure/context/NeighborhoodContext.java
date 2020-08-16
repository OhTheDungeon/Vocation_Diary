package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.rural.HouseLot;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;

public class NeighborhoodContext extends RuralContext {

	public NeighborhoodContext(VocationCityWorldGenerator generator) {
		super(generator);
	}

	@Override
	public void populateMap(VocationCityWorldGenerator generator, PlatMap platmap) {
		Odds platmapOdds = platmap.getOddsGenerator();

		// let the user add their stuff first, then plug any remaining holes with our
		// stuff

		/// do we check for roads?
		boolean checkForRoads = platmap.getNumberOfRoads() > 0;

		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {

					// make houses? but only if they are right beside a road
					if (generator.getWorldSettings().includeHouses) {

						// check for roads?
						if (checkForRoads) {
							if (platmap.isExistingRoad(x - 1, z) || platmap.isExistingRoad(x + 1, z)
									|| platmap.isExistingRoad(x, z - 1) || platmap.isExistingRoad(x, z + 1)) {
								platmap.setLot(x, z, getHouseLot(generator, platmap, platmapOdds, platmap.originX + x,
										platmap.originZ + z));
							}

							// just do it then
						} else
							platmap.setLot(x, z, getHouseLot(generator, platmap, platmapOdds, platmap.originX + x,
									platmap.originZ + z));
					}
				}
			}
		}
	}

	@Override
	protected PlatLot getBackfillLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return null; // this will eventually get filled in with nature
	}

	protected PlatLot getHouseLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new HouseLot(platmap, chunkX, chunkZ);
	}

	public void validateMap(VocationCityWorldGenerator generator, PlatMap platmap) {
//		for (int x = 0; x < PlatMap.Width; x++) {
//			for (int z = 0; z < PlatMap.Width; z++) {
//				PlatLot current = platmap.getLot(x, z);
//				if (current != null)
//					if (platmap.isNaturalLot(x, z))
//			}
//		}
	}
}
