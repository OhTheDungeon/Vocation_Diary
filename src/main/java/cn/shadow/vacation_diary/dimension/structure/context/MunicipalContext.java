package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.GovernmentBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.GovernmentMonumentLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.MuseumBuildingLot;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;

public class MunicipalContext extends UrbanContext {

	public MunicipalContext(VocationCityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsVeryUnlikely;
		oddsOfIsolatedLots = Odds.oddsVeryLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingRounding = Odds.oddsAlwaysGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsExtremelyUnlikely;
		oddsOfOnlyUnfinishedBasements = Odds.oddsUnlikely;
		// oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsVeryLikely;

		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsAlwaysGoingToHappen;
		oddsOfFlatWalledBuildings = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarInsetBuildings = Odds.oddsAlwaysGoingToHappen;
		oddsOfBuildingWallInset = Odds.oddsAlwaysGoingToHappen;
		rangeOfWallInset = 1;

		maximumFloorsAbove = 5;
		maximumFloorsBelow = 2;
		minSizeOfBuilding = 3;

		oddsOfFloodFill = Odds.oddsAlwaysGoingToHappen;
		oddsOfFloodDecay = Odds.oddsAlwaysGoingToHappen;
	}

	@Override
	protected PlatLot getBuilding(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		double roll = odds.getRandomDouble();
		if (roll < Odds.oddsVeryUnlikely)
			return new GovernmentMonumentLot(platmap, chunkX, chunkZ);
		else if (roll < Odds.oddsSomewhatUnlikely)
			return new MuseumBuildingLot(platmap, chunkX, chunkZ);
		else
			return new GovernmentBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected PlatLot getPark(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ,
			int waterDepth) {
		return getBuilding(generator, platmap, odds, chunkX, chunkZ);
	}

}
