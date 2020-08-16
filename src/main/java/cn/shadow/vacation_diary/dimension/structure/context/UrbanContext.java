package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.EmptyBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.LibraryBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.OfficeBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.ParkLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.StoreBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.UnfinishedBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.provider.ShapeProvider;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;

public abstract class UrbanContext extends CivilizedContext {

	double oddsOfFloodFill = Odds.oddsVeryLikely;
	double oddsOfFloodDecay = Odds.oddsLikely;
	int minSizeOfBuilding = 1;

	UrbanContext(VocationCityWorldGenerator generator) {
		super(generator);

		maximumFloorsAbove = 2;
		maximumFloorsBelow = 2;
	}

	@Override
	public void populateMap(VocationCityWorldGenerator generator, PlatMap platmap) {

		// let the user add their stuff first, then plug any remaining holes with our
		// stuff
		// random fluff!
		Odds platmapOdds = platmap.getOddsGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		int waterDepth = ParkLot.getWaterDepth(platmapOdds);

		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {

					// TODO I need to come up with a more elegant way of doing this!
					if (generator.getWorldSettings().includeBuildings) {

						// what to build?
						boolean buildPark = platmapOdds.playOdds(oddsOfParks);
						if (buildPark)
							current = getPark(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z,
									waterDepth);
						else
							current = getBackfillLot(generator, platmap, platmapOdds, platmap.originX + x,
									platmap.originZ + z);

						// see if the previous chunk is the same type
						PlatLot previous = null;
						if (x > 0 && current.isConnectable(platmap.getLot(x - 1, z))) {
							previous = platmap.getLot(x - 1, z);
						} else if (z > 0 && current.isConnectable(platmap.getLot(x, z - 1))) {
							previous = platmap.getLot(x, z - 1);
						}

						// if there was a similar previous one then copy it... maybe
						if (previous != null && !shapeProvider.isIsolatedLotAt(platmap.originX + x, platmap.originZ + z,
								oddsOfIsolatedLots)) {
							current.makeConnected(previous);

							// 2 by 2 at a minimum if at all possible
						} else if (!buildPark && x < PlatMap.Width - 1 && z < PlatMap.Width - 1) {
							if (minSizeOfBuilding == 1) {
								fillOutBuilding(generator, platmap, platmapOdds, oddsOfFloodFill, current, x, z + 1);
								fillOutBuilding(generator, platmap, platmapOdds, oddsOfFloodFill, current, x + 1, z);
							} else if (platmap.isEmptyLots(x, z, minSizeOfBuilding, minSizeOfBuilding)) {
								boolean madeOne = false;
								int newZ = z;
								while (platmap.inBounds(x, newZ) && platmap.isEmptyLot(x, newZ)) {
									if (fillOutBuilding(generator, platmap, platmapOdds, oddsOfFloodFill, current, x,
											newZ))
										madeOne = true;
									newZ++;
								}

								// did it, so lets not do it again
								if (madeOne)
									current = null;
							}
						}
					}

					// remember what we did
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}

		// validate each lot
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current != null) {
					PlatLot replacement = current.validateLot(platmap, x, z);
					if (replacement != null)
						platmap.setLot(x, z, replacement);
				}
			}
		}
	}

	private void addToBigBuilding(VocationCityWorldGenerator generator, PlatMap platmap, PlatLot source, int x, int z) {
		PlatLot destination = source.newLike(platmap, platmap.originX + x, platmap.originZ + z);
		destination.makeConnected(source);
		platmap.setLot(x, z, destination);
	}

	private boolean fillOutBuilding(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, double theOdds,
			PlatLot source, int x, int z) {
		if (odds.playOdds(oddsOfFloodFill) && platmap.inBounds(x, z) && platmap.isEmptyLot(x, z)) {
			addToBigBuilding(generator, platmap, source, x, z);
			return fillOutBuilding(generator, platmap, odds, theOdds * oddsOfFloodDecay, source, x + 1, z)
					|| fillOutBuilding(generator, platmap, odds, theOdds * oddsOfFloodDecay, source, x, z + 1);
		} else
			return false;
	}

	@Override
	protected PlatLot getBackfillLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.playOdds(oddsOfUnfinishedBuildings))
			return getUnfinishedBuilding(generator, platmap, odds, chunkX, chunkZ);
		else
			return getBuilding(generator, platmap, odds, chunkX, chunkZ);
	}

	protected PlatLot getPark(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ,
			int waterDepth) {
		return new ParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks, waterDepth);
	}

	protected PlatLot getUnfinishedBuilding(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX,
			int chunkZ) {
		return new UnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}

	protected PlatLot getBuilding(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		switch (odds.getRandomInt(6)) {
		case 1:
			return new EmptyBuildingLot(platmap, chunkX, chunkZ);
		case 2:
			return new StoreBuildingLot(platmap, chunkX, chunkZ);
		case 3:
			return new LibraryBuildingLot(platmap, chunkX, chunkZ);
//		case 4:
//			return new ApartmentBuildingLot(platmap, chunkX, chunkZ);
//		case 5:
//			return new BankBuildingLot(platmap, chunkX, chunkZ);
//		case 6:
//			return new FactoryBuildingLot(platmap, chunkX, chunkZ);
//		case 7:
//			return new BlaBlaBuildingLot(platmap, chunkX, chunkZ);
		default:
			return new OfficeBuildingLot(platmap, chunkX, chunkZ);
		}
	}
}
