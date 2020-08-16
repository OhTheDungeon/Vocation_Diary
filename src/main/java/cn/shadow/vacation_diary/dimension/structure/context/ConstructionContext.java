package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;

public class ConstructionContext extends UrbanContext {

	public ConstructionContext(VocationCityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsUnlikely;
		oddsOfIsolatedLots = Odds.oddsLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = Odds.oddsExtremelyLikely;
		// oddsOfMissingRoad = oddsLikely;
		// oddsOfRoundAbouts = Odds.oddsSomewhatLikely;

		oddsOfUnfinishedBuildings = Odds.oddsVeryLikely;
		oddsOfOnlyUnfinishedBasements = Odds.oddsLikely;
		oddsOfCranes = Odds.oddsExtremelyLikely;

		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsExtremelyLikely;
		oddsOfBuildingWallInset = Odds.oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = Odds.oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = Odds.oddsExtremelyLikely;
		rangeOfWallInset = 2;

		minSizeOfBuilding = 2;

		maximumFloorsAbove = absoluteMaximumFloorsAbove;
		maximumFloorsBelow = 3;
	}
}
