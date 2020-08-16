package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot.LotStyle;
import cn.shadow.vacation_diary.dimension.structure.plats.rural.BarnLot;
import cn.shadow.vacation_diary.dimension.structure.plats.rural.BeeHouseLot;
import cn.shadow.vacation_diary.dimension.structure.plats.rural.FarmLot;
import cn.shadow.vacation_diary.dimension.structure.plats.rural.HouseLot;
import cn.shadow.vacation_diary.dimension.structure.plats.rural.WaterTowerLot;
import cn.shadow.vacation_diary.dimension.structure.provider.ShapeProvider;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;

public class FarmContext extends RuralContext {

	private final static double oddsOfFarmHouse = Odds.oddsSomewhatUnlikely;
	private final static double oddsOfBarn = Odds.oddsSomewhatUnlikely;
	private final static double oddsOfWaterTower = Odds.oddsSomewhatUnlikely;
	private final static double oddsOfBeeHouse = Odds.oddsSomewhatUnlikely;

	public FarmContext(VocationCityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedLots = Odds.oddsLikely;
	}

	@Override
	public void populateMap(VocationCityWorldGenerator generator, PlatMap platmap) {

		// now add our stuff
		Odds platmapOdds = platmap.getOddsGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		boolean housePlaced = false;
		boolean barnPlaced = false;
		boolean waterTowerPlaced = false;
		boolean beeHousePlaced = false;

		int lastX = 0, lastZ = 0;

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

		// backfill with farms and a single house
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {

					// TODO Barns and Wells
					
					
					if (!housePlaced && platmapOdds.playOdds(oddsOfFarmHouse) && generator.getWorldSettings().includeHouses) {
						housePlaced = platmap.setLot(x, z,
								getHouseLot(generator, platmap, platmapOdds, originX + x, originZ + z));

						// barn here?
					} else if (!barnPlaced && platmapOdds.playOdds(oddsOfBarn) && generator.getWorldSettings().includeBuildings) {
						barnPlaced = platmap.setLot(x, z,
								getBarnLot(generator, platmap, platmapOdds, originX + x, originZ + z));

						// barn here?
					} else if (!waterTowerPlaced && platmapOdds.playOdds(oddsOfWaterTower)
							&& generator.getWorldSettings().includeBuildings) {
						waterTowerPlaced = platmap.setLot(x, z,
								getWaterTowerLot(generator, platmap, platmapOdds, originX + x, originZ + z));

						// place the farm
					} else if (!beeHousePlaced && platmapOdds.playOdds(oddsOfBeeHouse) && generator.getWorldSettings().includeHouses) {
						beeHousePlaced = platmap.setLot(x, z,
								getBeeHouseLot(generator, platmap, platmapOdds, originX + x, originZ + z));
						// barn here?
					} else {

						// place the farm
						current = getFarmLot(generator, platmap, platmapOdds, originX + x, originZ + z);

						// see if the previous chunk is the same type
						PlatLot previous = null;
						if (x > 0 && current.isConnectable(platmap.getLot(x - 1, z))) {
							previous = platmap.getLot(x - 1, z);
						} else if (z > 0 && current.isConnectable(platmap.getLot(x, z - 1))) {
							previous = platmap.getLot(x, z - 1);
						}

						// if there was a similar previous one then copy it... maybe
						if (previous != null
								&& !shapeProvider.isIsolatedLotAt(originX + x, originZ + z, oddsOfIsolatedLots)) {
							current.makeConnected(previous);
						}

						// remember what we did
						platmap.setLot(x, z, current);

						// remember the last place
						lastX = x;
						lastZ = z;
					}
				}
			}
		}

		// did we miss out placing the farm house?
		if (!housePlaced && platmap.isEmptyLot(lastX, lastZ) && generator.getWorldSettings().includeHouses) {
			platmap.setLot(lastX, lastZ,
					getHouseLot(generator, platmap, platmapOdds, originX + lastX, originZ + lastZ));
		}
	}

	@Override
	protected PlatLot getBackfillLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return getFarmLot(generator, platmap, odds, chunkX, chunkZ);
	}

	protected PlatLot getFarmLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FarmLot(platmap, chunkX, chunkZ);
	}

	protected PlatLot getHouseLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new HouseLot(platmap, chunkX, chunkZ);
	}
	
	protected PlatLot getBeeHouseLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new BeeHouseLot(platmap, chunkX, chunkZ);
	}

	private PlatLot getBarnLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new BarnLot(platmap, chunkX, chunkZ);
	}

	private PlatLot getWaterTowerLot(VocationCityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX,
			int chunkZ) {
		return new WaterTowerLot(platmap, chunkX, chunkZ);
	}

	public void validateMap(VocationCityWorldGenerator generator, PlatMap platmap) {
	}
}
