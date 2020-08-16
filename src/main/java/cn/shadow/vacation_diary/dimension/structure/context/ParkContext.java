package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;

public class ParkContext extends UrbanContext {

	public ParkContext(VocationCityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsAlwaysGoingToHappen;
		oddsOfIsolatedLots = Odds.oddsAlwaysGoingToHappen;
		oddsOfIdenticalBuildingHeights = Odds.oddsNeverGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsNeverGoingToHappen;
		oddsOfSimilarBuildingRounding = Odds.oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = Odds.oddsNeverGoingToHappen;
		// oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsNeverGoingToHappen;

		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsNeverGoingToHappen;
		oddsOfBuildingWallInset = Odds.oddsNeverGoingToHappen;
		oddsOfFlatWalledBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfSimilarInsetBuildings = Odds.oddsNeverGoingToHappen;
		rangeOfWallInset = 1;
	}
}
