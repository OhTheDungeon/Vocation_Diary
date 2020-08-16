package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot.LotStyle;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.CampgroundLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.GravelMineLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.GravelworksLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.MineEntranceLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.WoodframeLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.WoodworksLot;
import cn.shadow.vacation_diary.dimension.support.HeightInfo;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class OutlandContext extends RuralContext {

	public OutlandContext(VocationCityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedLots = Odds.oddsVeryLikely;
	}

	@Override
	public void populateMap(VocationCityWorldGenerator generator, PlatMap platmap) {

		// now add our stuff
		Odds platmapOdds = platmap.getOddsGenerator();
		boolean singletonOneUsed = false;
		boolean singletonTwoUsed = false;
		HeightInfo heights;

		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;

		// clean up the platmap of singletons and odd road structures
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);

				// something here?
				if (current == null) {

					// but there aren't neighbors
					if (!platmap.isEmptyLot(x - 1, z) && !platmap.isEmptyLot(x + 1, z) && !platmap.isEmptyLot(x, z - 1)
							&& !platmap.isEmptyLot(x, z + 1))
						platmap.recycleLot(x, z);
				}

				// look for singleton nature and roundabouts
				else {

					// if a single natural thing is here but surrounded by four "things"
					if (current.style == LotStyle.NATURE && platmap.isEmptyLot(x - 1, z) && platmap.isEmptyLot(x + 1, z)
							&& platmap.isEmptyLot(x, z - 1) && platmap.isEmptyLot(x, z + 1))
						platmap.emptyLot(x, z);

						// get rid of roundabouts
					else if (current.style == LotStyle.ROUNDABOUT) {
						platmap.paveLot(x, z, false);
						platmap.emptyLot(x - 1, z - 1);
						platmap.emptyLot(x - 1, z + 1);
						platmap.emptyLot(x + 1, z - 1);
						platmap.emptyLot(x + 1, z + 1);
					}
				}
			}
		}

		// fill with more stuff
		if (!generator.getWorldSettings().includeDecayedBuildings) {
			boolean stoneworks = platmapOdds.flipCoin() && generator.getWorldSettings().includeMines;
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
						if (heights.isBuildable()) {
							if (stoneworks) {
								if (!singletonOneUsed && platmapOdds.playOdds(Odds.oddsUnlikely)) {
									current = new GravelMineLot(platmap, chunkX, chunkZ);
									singletonOneUsed = true;
								} else if (!singletonTwoUsed && platmapOdds.playOdds(Odds.oddsUnlikely)) {
									current = new MineEntranceLot(platmap, chunkX, chunkZ);
									singletonTwoUsed = true;
								} else
									current = new GravelworksLot(platmap, chunkX, chunkZ);

							} else { // woodworks
								if (!singletonOneUsed && platmapOdds.playOdds(Odds.oddsVeryUnlikely)) {
									current = new WoodframeLot(platmap, chunkX, chunkZ);
									singletonOneUsed = true;
								} else if (!singletonTwoUsed && platmapOdds.playOdds(Odds.oddsSomewhatUnlikely)) {
									current = new CampgroundLot(platmap, chunkX, chunkZ);
									singletonTwoUsed = true;
								} else
									current = new WoodworksLot(platmap, chunkX, chunkZ);
							}

							// remember what we did
							platmap.setLot(x, z, current);
						}
					}
				}
			}
		}
	}

	@Override
	protected PlatLot getBackfillLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		// this will eventually be filled in with nature
		return null;
	}

}
