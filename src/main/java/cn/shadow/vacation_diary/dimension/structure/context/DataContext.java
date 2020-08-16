package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.NatureLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public abstract class DataContext {

	// While these are initialized here, the real defaults live in CivilizedContext
	// and UncivilizedContext

	protected double oddsOfIsolatedLots = Odds.oddsNeverGoingToHappen;
	protected double oddsOfIsolatedConstructs = Odds.oddsNeverGoingToHappen;
	protected double oddsOfParks = Odds.oddsNeverGoingToHappen; // parks show up 1/n of the time

	public double oddsOfIdenticalBuildingHeights = Odds.oddsNeverGoingToHappen; // similar height 1/n of the time
	public double oddsOfSimilarBuildingHeights = Odds.oddsNeverGoingToHappen; // identical height 1/n of the time
	public final double oddsOfRoundedBuilding = Odds.oddsEnormouslyLikely;// Odds.oddsLikely; // how naturally rounded are
	// buildings that can be rounded
	public double oddsOfSimilarBuildingRounding = Odds.oddsNeverGoingToHappen; // like rounding 1/n of the time
	public double oddsOfStairWallMaterialIsWallMaterial = Odds.oddsNeverGoingToHappen; // stair walls are the same as
	// walls 1/n of the time
//	public int buildingWallInsettedMinLowPoint; // minimum building height before insetting is allowed
//	public int buildingWallInsettedMinMidPoint; // lowest point of inset
//	public int buildingWallInsettedMinHighPoint; // lowest highest point of inset
	public int rangeOfWallInset = 2; // 1 or 2 in... but not zero
	public final double oddsOfForcedNarrowInteriorMode = Odds.oddsLikely;
	public final double oddsOfDifferentInteriorModes = Odds.oddsUnlikely;

	public double oddsOfOnlyUnfinishedBasements = Odds.oddsNeverGoingToHappen; // unfinished buildings only have
	// basements 1/n of the time
	public double oddsOfCranes = Odds.oddsVeryLikely; // plop a crane on top of the last horizontal girder 1/n of the
	// time

	public double oddsOfBuildingWallInset = Odds.oddsNeverGoingToHappen; // building walls inset as they go up 1/n of
	// the time
	public double oddsOfSimilarInsetBuildings = Odds.oddsNeverGoingToHappen; // the east/west inset is used for
	// north/south inset 1/n of the time
	public double oddsOfFlatWalledBuildings = Odds.oddsNeverGoingToHappen; // the ceilings are inset like the walls 1/n
	// of the time

	// TODO oddsOfMissingRoad is current not used... I need to fix this
	// public double oddsOfMissingRoad = oddsNeverGoingToHappen; // roads are
	// missing 1/n of the time
	public double oddsOfRoundAbouts = Odds.oddsNeverGoingToHappen; // roundabouts are created 1/n of the time

	public double oddsOfArt = Odds.oddsNeverGoingToHappen; // art is missing 1/n of the time
	public double oddsOfNaturalArt = Odds.oddsNeverGoingToHappen; // sometimes nature is art 1/n of the time

	public final Block lightMat;
	public final Block torchMat;

	public static final int FloorHeight = 4;
	private static final int FudgeFloorsBelow = 2;
	private static final int FudgeFloorsAbove = 0;// 3;
	private static final int absoluteMinimumFloorsAbove = 5; // shortest tallest building
	private static final int absoluteAbsoluteMaximumFloorsBelow = 3; // that is as many basements as I personally can
	// tolerate
	private static final int absoluteAbsoluteMaximumFloorsAbove = 20; // that is tall enough folks
	public final int buildingMaximumY;
	public int maximumFloorsAbove = 2;
	public int maximumFloorsBelow = 2;
	private final int absoluteMaximumFloorsBelow;
	protected final int absoluteMaximumFloorsAbove;

	protected DataContext(VocationCityWorldGenerator generator) {

		// lights?
		if (generator.getWorldSettings().includeWorkingLights) {
			lightMat = Blocks.GLOWSTONE;
			torchMat = Blocks.TORCH;
		} else {
			lightMat = Blocks.REDSTONE_LAMP;
			torchMat = Blocks.REDSTONE_TORCH;
		}

		// where is the ground
		buildingMaximumY = Math.min(192 + FudgeFloorsAbove * FloorHeight, generator.height);
		absoluteMaximumFloorsBelow = Math.max(
				Math.min(generator.streetLevel / FloorHeight - FudgeFloorsBelow, absoluteAbsoluteMaximumFloorsBelow),
				0);
		absoluteMaximumFloorsAbove = Math
				.max(Math.min((buildingMaximumY - generator.streetLevel) / FloorHeight - FudgeFloorsAbove,
						absoluteAbsoluteMaximumFloorsAbove), absoluteMinimumFloorsAbove);

		// calculate the extremes for this plat
		maximumFloorsAbove = Math.min(maximumFloorsAbove, absoluteMaximumFloorsAbove);
		maximumFloorsBelow = Math.min(maximumFloorsBelow, absoluteMaximumFloorsBelow);

//		int floorsFourth = Math.max((maximumFloorsAbove) / 4, 1);
//		buildingWallInsettedMinLowPoint = floorsFourth;
//		buildingWallInsettedMinMidPoint = floorsFourth * 2;
//		buildingWallInsettedMinHighPoint = floorsFourth * 3;

	}

	public abstract void populateMap(VocationCityWorldGenerator generator, PlatMap platmap);

	public abstract void validateMap(VocationCityWorldGenerator generator, PlatMap platmap);

	protected double oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;

	public PlatLot createNaturalLot(VocationCityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return new NatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}

	public Block getMapRepresentation() {
		return Blocks.AIR;
	}

}
