package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.RoadLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.BunkerLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.FlyingSaucerLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.HotairBalloonLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.MineEntranceLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.MountainShackLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.MountainTentLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.OilPlatformLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.OldCastleLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.RadioTowerLot;
import cn.shadow.vacation_diary.dimension.support.HeightInfo;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.dimension.support.AbstractYs.HeightState;

public class NatureContext extends UncivilizedContext {

	public NatureContext(VocationCityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedConstructs = Odds.oddsSomewhatLikely;
	}

	private final static double oddsOfBunkers = Odds.oddsLikely;

	@Override
	public void populateMap(VocationCityWorldGenerator generator, PlatMap platmap) {

		// TODO, Nature doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our
		// stuff
		// mapsSchematics.populate(generator, platmap);

		// random stuff?
		Odds platmapOdds = platmap.getOddsGenerator();
		boolean doBunkers = generator.getWorldSettings().includeBunkers && platmapOdds.playOdds(oddsOfBunkers);
		boolean didBunkerEntrance = false;

		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;

		// highest special place
		int maxHeight = Integer.MIN_VALUE;
		int maxHeightX = -1;
		int maxHeightZ = -1;
		HeightState maxState = HeightState.BUILDING;

		// lowest special place
		int minHeight = Integer.MAX_VALUE;
		int minHeightX = -1;
		int minHeightZ = -1;
		HeightState minState = HeightState.BUILDING;

		// is this natural or buildable?
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {

					// what is the world location of the lot?
					int chunkX = originX + x;
					int chunkZ = originZ + z;
					int blockX = chunkX * SupportBlocks.sectionBlockWidth;
					int blockZ = chunkZ * SupportBlocks.sectionBlockWidth;

					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.isBuildable()) {

						// our inner chunks?
						if (x > 0 && x < PlatMap.Width - 1 && z > 0 && z < PlatMap.Width - 1) {

							// extreme changes?
							if (heights.getMinHeight() < minHeight) {
								minHeight = heights.getMinHeight();
								minHeightX = x;
								minHeightZ = z;
								minState = heights.getState();
							}
							if (heights.getMaxHeight() > maxHeight) {
								maxHeight = heights.getMaxHeight();
								maxHeightX = x;
								maxHeightZ = z;
								maxState = heights.getState();
							}

							// innermost chunks?
							boolean potentialRoads = (x == (RoadLot.PlatMapRoadInset - 1)
									|| x == (PlatMap.Width - RoadLot.PlatMapRoadInset))
									&& (z == (RoadLot.PlatMapRoadInset - 1)
									|| z == (PlatMap.Width - RoadLot.PlatMapRoadInset));
							boolean doBunkerEntrance = doBunkers && !didBunkerEntrance && !potentialRoads;

							// what type of height are we talking about?
							switch (heights.getState()) {
							case MIDLAND:
								if (doBunkers && minHeight > BunkerLot.calcBunkerMinHeight(generator)) {
									current = createBuriedBuildingLot(generator, platmap, chunkX, chunkZ,
											doBunkerEntrance);

								} else if (heights.isSortaFlat() && generator.shapeProvider
										.isIsolatedConstructAt(originX + x, originZ + z, oddsOfIsolatedConstructs)) {
									current = createSurfaceBuildingLot(generator, platmap, originX + x, originZ + z,
											heights);
								}

								break;
							case HIGHLAND:
							case PEAK:
								if (doBunkers) {
									current = createBuriedBuildingLot(generator, platmap, chunkX, chunkZ,
											doBunkerEntrance);
								}
								break;
							default:
								break;
							}

							// did we do it?
							if (doBunkerEntrance && current != null)
								didBunkerEntrance = true;
						}

						// did current get defined?
						if (current != null)
							platmap.setLot(x, z, current);
						else
							platmap.recycleLot(x, z);
					}
				}
			}
		}

		// any special things to do?
		populateSpecial(generator, platmap, maxHeightX, maxHeight, maxHeightZ, maxState);
		populateSpecial(generator, platmap, minHeightX, minHeight, minHeightZ, minState);
	}

	private PlatLot createBuriedBuildingLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z,
			boolean firstOne) {
		if (generator.getWorldSettings().includeBunkers)
			return new BunkerLot(platmap, x, z, firstOne);
		return null;
	}

	protected PlatLot createSurfaceBuildingLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z,
			HeightInfo heights) {
		if (generator.getWorldSettings().includeHouses)
			if (platmap.getOddsGenerator().flipCoin())
				return new MountainShackLot(platmap, x, z);
			else
				return new MountainTentLot(platmap, x, z);
		return null;
	}

	protected void populateSpecial(VocationCityWorldGenerator generator, PlatMap platmap, int x, int y, int z,
			HeightState state) {

		// what type of height are we talking about?
		if (state != HeightState.BUILDING && generator.shapeProvider.isIsolatedConstructAt(platmap.originX + x,
				platmap.originZ + z, oddsOfIsolatedConstructs)) {
//		if (state != HeightState.BUILDING) {
			PlatLot current = null;
			Odds platmapOdds = platmap.getOddsGenerator();

			// what to make?
			switch (state) {
			case DEEPSEA:
				// Oil rigs
				if (generator.getWorldSettings().includeBuildings)
					current = new OilPlatformLot(platmap, platmap.originX + x, platmap.originZ + z);
				break;
			case SEA:
				if (generator.getWorldSettings().includeAirborneStructures) {
					if (platmapOdds.playOdds(Odds.oddsEnormouslyUnlikely))
						current = new FlyingSaucerLot(platmap, platmap.originX + x, platmap.originZ + z);
					else if (platmapOdds.playOdds(Odds.oddsSomewhatLikely))
						current = new HotairBalloonLot(platmap, platmap.originX + x, platmap.originZ + z);

					// TODO boat!
				}
				break;
//			case BUILDING:
//				break;
			case LOWLAND:
				if (generator.getWorldSettings().includeAirborneStructures) {
					if (platmapOdds.playOdds(Odds.oddsEnormouslyUnlikely))
						current = new FlyingSaucerLot(platmap, platmap.originX + x, platmap.originZ + z);
					else if (platmapOdds.playOdds(Odds.oddsSomewhatLikely))
						current = new HotairBalloonLot(platmap, platmap.originX + x, platmap.originZ + z);

					// TODO statue overlooking the city?
				}
				break;
			case MIDLAND:
				// Mine entrance
				if (generator.getWorldSettings().includeMines)
					current = new MineEntranceLot(platmap, platmap.originX + x, platmap.originZ + z);
				break;
			case HIGHLAND:
				// Radio towers
				if (generator.getWorldSettings().includeBuildings)
					current = new RadioTowerLot(platmap, platmap.originX + x, platmap.originZ + z);
				break;
			case PEAK:
				// Old castle
				if (generator.getWorldSettings().includeBuildings)
					current = new OldCastleLot(platmap, platmap.originX + x, platmap.originZ + z);
				break;
			default:
				break;
			}

			if (current != null) {
				platmap.setLot(x, z, current);
			}
		}
	}

	public void validateMap(VocationCityWorldGenerator generator, PlatMap platmap) {
		// TODO Should this be calling the parent?
	}
}
