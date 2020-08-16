package cn.shadow.vacation_diary.dimension.support;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;

public final class HeightInfo extends AbstractYs {

	public static HeightInfo getHeightsFaster(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();

		int sumHeight = 0;

		sumHeight += heights.add(generator, blockX + 8, blockZ + 8); // center
		sumHeight += heights.add(generator, blockX, blockZ); // corners
		sumHeight += heights.add(generator, blockX + 15, blockZ);
		sumHeight += heights.add(generator, blockX, blockZ + 15);
		sumHeight += heights.add(generator, blockX + 15, blockZ + 15);

		heights.calcState(generator, sumHeight, 5);
		return heights;
	}

	public static HeightInfo getHeightsFast(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();

		int sumHeight = 0;

		sumHeight += heights.add(generator, blockX + 8, blockZ + 8); // center
		sumHeight += heights.add(generator, blockX, blockZ); // corners
		sumHeight += heights.add(generator, blockX + 15, blockZ);

		sumHeight += heights.add(generator, blockX, blockZ + 15);
		sumHeight += heights.add(generator, blockX + 15, blockZ + 15);
		sumHeight += heights.add(generator, blockX, blockZ + 8); // edges

		sumHeight += heights.add(generator, blockX + 15, blockZ + 8);
		sumHeight += heights.add(generator, blockX + 8, blockZ + 15);
		sumHeight += heights.add(generator, blockX + 8, blockZ + 15);

		heights.calcState(generator, sumHeight, 9);
		return heights;
	}

	public static boolean isBuildableAt(VocationCityWorldGenerator generator, int blockX, int blockZ) {
		return getHeightsFaster(generator, blockX, blockZ).getState() == HeightState.BUILDING;
	}

	public static boolean isBuildableToNorth(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX(), chunk.getOriginZ() - chunk.width);
	}

	public static boolean isBuildableToSouth(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX(), chunk.getOriginZ() + chunk.width);
	}

	public static boolean isBuildableToWest(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX() - chunk.width, chunk.getOriginZ());
	}

	public static boolean isBuildableToEast(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX() + chunk.width, chunk.getOriginZ());
	}

	public static boolean isBuildableToNorthWest(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX() - chunk.width, chunk.getOriginZ() - chunk.width);
	}

	public static boolean isBuildableToSouthWest(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX() - chunk.width, chunk.getOriginZ() + chunk.width);
	}

	public static boolean isBuildableToNorthEast(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX() + chunk.width, chunk.getOriginZ() - chunk.width);
	}

	public static boolean isBuildableToSouthEast(VocationCityWorldGenerator generator, AbstractBlocks chunk) {
		return isBuildableAt(generator, chunk.getOriginX() + chunk.width, chunk.getOriginZ() + chunk.width);
	}

	public boolean anyEmpties = false;

	private int add(VocationCityWorldGenerator generator, int x, int z) {
		// we will need to get the Y the hard way
		int value = generator.getFarBlockY(x, z);
		anyEmpties = anyEmpties || value == 0;
		calcMinMax(x, value, z);
		return value;
	}

	@Override
	public int getMinHeight() {
		return minHeight;
	}

	@Override
	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	public int getAverageHeight() {
		return averageHeight;
	}
}
