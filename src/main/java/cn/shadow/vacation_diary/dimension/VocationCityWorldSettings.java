package cn.shadow.vacation_diary.dimension;

import java.io.File;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator.WorldStyle;
import cn.shadow.vacation_diary.dimension.structure.provider.TreeProvider.TreeStyle;
import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.WorldEnvironment;
import cn.shadow.vacation_diary.util.JsonConfiguration;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;

public class VocationCityWorldSettings {

	public boolean darkEnvironment = false;

	public boolean includeRoads = true;
	public boolean includeRoundabouts = true;
	public boolean includeSewers = true;
	public boolean includeCisterns = true;
	public boolean includeBasements = true;
	public boolean includeMines = true;
	public boolean includeBunkers = true;
	public boolean includeBuildings = true;
	public boolean includeHouses = true;
	public boolean includeFarms = true;
	public boolean includeMunicipalities = true;
	public boolean includeIndustrialSectors = true;
	public boolean includeAirborneStructures = true;

	public boolean includeCaves = true;
	public boolean includeLavaFields = true;
	public boolean includeSeas = true;
	public boolean includeMountains = true;
	public boolean includeOres = true;
	public boolean includeBones = true;
	public boolean includeFires = true;

	public double spawnBeings = Odds.oddsLikely;
	public double spawnBaddies = Odds.oddsPrettyUnlikely;
	public double spawnAnimals = Odds.oddsVeryLikely;
	public double spawnVagrants = Odds.oddsSomewhatUnlikely;
	public boolean nameVillagers = true;
	public boolean showVillagersNames = true;

	public boolean spawnersInBunkers = true;
	public boolean spawnersInMines = true;
	public boolean spawnersInSewers = true;

	public boolean treasuresInBunkers = true;
	public boolean treasuresInMines = true;
	public boolean treasuresInSewers = true;
	public boolean treasuresInBuildings = true;

	public boolean includeUndergroundFluids = true;
	public boolean includeAbovegroundFluids = true;
	public boolean includeWorkingLights = true;
	public boolean includeNamedRoads = true;
	public boolean includeDecayedRoads = false;
	public boolean includeDecayedBuildings = false;
	public boolean includeDecayedNature = false;
	public boolean includeBuildingInteriors = true;
//	public boolean includeFloatingSubsurface = false; // not needed anymore
//	public boolean includeFloatingSubclouds = true;
//	public double spawnCities = Odds.oddsAlwaysGoingToHappen;

	public boolean useMinecraftLootTables = true;
	public boolean broadcastSpecialPlaces = false;

	public TreeStyle treeStyle = TreeStyle.NORMAL;
	public double spawnTrees = Odds.oddsLikely;
	//	public double oddsOfFoliage = Odds.oddsAlwaysGoingToHappen;

	private final static int maxRadius = 30000000 / AbstractBlocks.sectionBlockWidth; // 1875000 is the actual maximum
	// chunk limit for today's
	// minecraft world format
	private int centerPointOfChunkRadiusX = 0;
	private int centerPointOfChunkRadiusZ = 0;
	private int constructChunkRadius = maxRadius;
	private boolean checkConstructRange = false;
	private int roadChunkRadius = maxRadius;
	private boolean checkRoadRange = false;
	private int cityChunkRadius = maxRadius;
	private boolean checkCityRange = false;
	private boolean buildOutsideRadius = false;

	private int minInbetweenChunkDistanceOfCities = 0;
	private boolean checkMinInbetweenChunkDistanceOfCities = false;
	public double ruralnessLevel = 0.0;

	public double oddsOfTreasureInSewers = Odds.oddsLikely;
	public double oddsOfTreasureInBunkers = Odds.oddsLikely;
	public double oddsOfTreasureInMines = Odds.oddsLikely;
	public double oddsOfTreasureInBuildings = Odds.oddsLikely;
	public double oddsOfAlcoveInMines = Odds.oddsVeryLikely;

	public VocationCityWorldSettings() {
		super();
	}
	
	private void fixWorldSettings(WorldEnvironment environment) {
		// get the right defaults
		switch (environment) {
		case NORMAL:
			darkEnvironment = false;
			treeStyle = TreeStyle.NORMAL;
			break;
		case NETHER:
			darkEnvironment = true;
			includeWorkingLights = false;
			includeDecayedRoads = true;
			includeDecayedBuildings = true;
			includeDecayedNature = true;
			treeStyle = TreeStyle.SPOOKY;
			break;
		case THE_END:
			darkEnvironment = true;
			treeStyle = TreeStyle.CRYSTAL;
			break;
		}
	}

	public static VocationCityWorldSettings loadSettings(VocationCityWorldGenerator generator, IWorld aWorld) {
		String worldName = generator.worldName;
		generator.reportMessage("Loading settings for '" + worldName + "'");
		VocationCityWorldSettings settings;
		WorldEnvironment worldEnv = generator.worldType;
		WorldStyle worldStyle = generator.worldStyle;
		
		VocationCityWorldSettings res;
		
		File pluginFolder = JsonConfiguration.getDataFolder();
		if (pluginFolder.isDirectory()) {
			File worldConfig = new File(pluginFolder, worldName + ".json");
			if(!worldConfig.exists()) {
				settings = new VocationCityWorldSettings();
				JsonConfiguration jc = new JsonConfiguration(worldName + ".json");
				jc.setSettings(settings);
				jc.saveConfig();
			}
			JsonConfiguration jc = new JsonConfiguration(worldName + ".json");
			jc.loadConfig();
			res = jc.getSettings();
		} else {
			res = new VocationCityWorldSettings();
		}
		res.fixWorldSettings(worldEnv);
		res.validateSettingsAgainstWorldStyle(worldStyle);
		
		return res;
	}

	public void copySettings(VocationCityWorldSettings otherSettings) {
		darkEnvironment = otherSettings.darkEnvironment;

		includeRoads = otherSettings.includeRoads;
		includeRoundabouts = otherSettings.includeRoundabouts;
		includeSewers = otherSettings.includeSewers;
		includeCisterns = otherSettings.includeCisterns;
		includeBasements = otherSettings.includeBasements;
		includeMines = otherSettings.includeMines;
		includeBunkers = otherSettings.includeBunkers;
		includeBuildings = otherSettings.includeBuildings;
		includeHouses = otherSettings.includeHouses;
		includeFarms = otherSettings.includeFarms;
		includeMunicipalities = otherSettings.includeMunicipalities;
		includeIndustrialSectors = otherSettings.includeIndustrialSectors;
		includeAirborneStructures = otherSettings.includeAirborneStructures;

		includeCaves = otherSettings.includeCaves;
		includeLavaFields = otherSettings.includeLavaFields;
		includeSeas = otherSettings.includeSeas;
		includeMountains = otherSettings.includeMountains;
		includeOres = otherSettings.includeOres;
		includeBones = otherSettings.includeBones;
		includeFires = otherSettings.includeFires;

		spawnBeings = otherSettings.spawnBeings;
		spawnBaddies = otherSettings.spawnBaddies;
		spawnAnimals = otherSettings.spawnAnimals;
		spawnVagrants = otherSettings.spawnVagrants;
		nameVillagers = otherSettings.nameVillagers;
		showVillagersNames = otherSettings.showVillagersNames;

		spawnersInBunkers = otherSettings.spawnersInBunkers;
		spawnersInMines = otherSettings.spawnersInMines;
		spawnersInSewers = otherSettings.spawnersInSewers;

		treasuresInBunkers = otherSettings.treasuresInBunkers;
		treasuresInMines = otherSettings.treasuresInMines;
		treasuresInSewers = otherSettings.treasuresInSewers;
		treasuresInBuildings = otherSettings.treasuresInBuildings;

		includeUndergroundFluids = otherSettings.includeUndergroundFluids;
		includeAbovegroundFluids = otherSettings.includeAbovegroundFluids;
		includeWorkingLights = otherSettings.includeWorkingLights;
		includeNamedRoads = otherSettings.includeNamedRoads;
		includeDecayedRoads = otherSettings.includeDecayedRoads;
		includeDecayedBuildings = otherSettings.includeDecayedBuildings;
		includeDecayedNature = otherSettings.includeDecayedNature;
		includeBuildingInteriors = otherSettings.includeBuildingInteriors;

		useMinecraftLootTables = otherSettings.useMinecraftLootTables;
		broadcastSpecialPlaces = otherSettings.broadcastSpecialPlaces;

		treeStyle = otherSettings.treeStyle;
		spawnTrees = otherSettings.spawnTrees;

		centerPointOfChunkRadiusX = otherSettings.centerPointOfChunkRadiusX;
		centerPointOfChunkRadiusZ = otherSettings.centerPointOfChunkRadiusZ;
		constructChunkRadius = otherSettings.constructChunkRadius;
		checkConstructRange = otherSettings.checkConstructRange;
		roadChunkRadius = otherSettings.roadChunkRadius;
		checkRoadRange = otherSettings.checkRoadRange;
		cityChunkRadius = otherSettings.cityChunkRadius;
		checkCityRange = otherSettings.checkCityRange;
		buildOutsideRadius = otherSettings.buildOutsideRadius;

		minInbetweenChunkDistanceOfCities = otherSettings.minInbetweenChunkDistanceOfCities;
		checkMinInbetweenChunkDistanceOfCities = otherSettings.checkMinInbetweenChunkDistanceOfCities;
		ruralnessLevel = otherSettings.ruralnessLevel;

		oddsOfTreasureInSewers = otherSettings.oddsOfTreasureInSewers;
		oddsOfTreasureInBunkers = otherSettings.oddsOfTreasureInBunkers;
		oddsOfTreasureInMines = otherSettings.oddsOfTreasureInMines;
		oddsOfTreasureInBuildings = otherSettings.oddsOfTreasureInBuildings;
		oddsOfAlcoveInMines = otherSettings.oddsOfAlcoveInMines;


	}

	private void validateSettingsAgainstWorldStyle(WorldStyle style) {
	}

	private Vec3i centerPointOfChunkRadius;

	private Vec3i getCenterPoint(int x, int z) {
		if (checkMinInbetweenChunkDistanceOfCities)
			return new Vec3i(calcOrigin(x, centerPointOfChunkRadiusX), 0, calcOrigin(z, centerPointOfChunkRadiusZ));
		else
			return centerPointOfChunkRadius;
	}

	// Supporting code used by getPlatMap
	private int calcOrigin(int i, int offset) {
		i = i - offset;
		if (i >= 0) {
			i = i / minInbetweenChunkDistanceOfCities * minInbetweenChunkDistanceOfCities;
		} else {
			i = -((Math.abs(i + 1) / minInbetweenChunkDistanceOfCities * minInbetweenChunkDistanceOfCities)
					+ minInbetweenChunkDistanceOfCities);
		}
		return i + offset;
	}

	public boolean inConstructRange(int x, int z) {
		if (checkConstructRange) {
			Vec3i centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distanceSq(new Vec3i(x, 0, z)) > constructChunkRadius;
			else
				return centerPoint.distanceSq(new Vec3i(x, 0, z)) <= constructChunkRadius;
		}
		return true;
	}

	public boolean inRoadRange(int x, int z) {
		if (checkRoadRange) {
			Vec3i centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distanceSq(new Vec3i(x, 0, z)) > roadChunkRadius;
			else
				return centerPoint.distanceSq(new Vec3i(x, 0, z)) <= roadChunkRadius;
		}
		return true;
	}

	public boolean inCityRange(int x, int z) {
		if (checkCityRange) {
			Vec3i centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distanceSq(new Vec3i(x, 0, z)) > cityChunkRadius;
			else
				return centerPoint.distanceSq(new Vec3i(x, 0, z)) <= cityChunkRadius;
		}
		return true;
	}
}
