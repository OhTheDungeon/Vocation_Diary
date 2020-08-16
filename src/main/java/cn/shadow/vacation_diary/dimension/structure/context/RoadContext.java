package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.plats.RoadLot;
import cn.shadow.vacation_diary.dimension.structure.plats.urban.RoundaboutCenterLot;
import cn.shadow.vacation_diary.dimension.support.PlatMap;

public class RoadContext extends UrbanContext {

	public RoadContext(VocationCityWorldGenerator generator) {
		super(generator);
	}

	@Override
	public void populateMap(VocationCityWorldGenerator generator, PlatMap platmap) {
		super.populateMap(generator, platmap);

	}

	@Override
	public void validateMap(VocationCityWorldGenerator generator, PlatMap platmap) {

	}

	public PlatLot createRoadLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart,
			PlatLot oldLot) {
		PlatLot result = null;

		// see if the old lot has a suggestion?
		if (oldLot != null)
			result = oldLot.repaveLot(generator, platmap);

		// if not then lets do return the standard one
		if (result == null)
			result = new RoadLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForPavedRoads,
					roundaboutPart);

		// ok... we are done
		return result;
	}

	public PlatLot createRoundaboutStatueLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return new RoundaboutCenterLot(platmap, platmap.originX + x, platmap.originZ + z);
	}

}
