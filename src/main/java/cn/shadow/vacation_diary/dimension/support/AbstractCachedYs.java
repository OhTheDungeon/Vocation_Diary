package cn.shadow.vacation_diary.dimension.support;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.util.noise.NoiseGenerator;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractCachedYs extends AbstractYs {

	// extremes
	int segmentWidth;

	final double[][] blockYs = new double[width][width];

	AbstractCachedYs(VocationCityWorldGenerator generator, int chunkX, int chunkZ) {

		// compute offset to start of chunk
		int originX = chunkX * width;
		int originZ = chunkZ * width;
		double sumHeight = 0.0;

		// calculate the Ys for this chunk
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < width; z++) {

				// how high are we?
				blockYs[x][z] = generator.shapeProvider.findPerciseY(generator, originX + x, originZ + z);
				sumHeight = sumHeight + blockYs[x][z];

				// keep the tally going
				calcMinMax(x, NoiseGenerator.floor(blockYs[x][z]), z);
			}
		}

		//noinspection SuspiciousNameCombination
		calcState(generator, NoiseGenerator.floor(sumHeight), width * width);
	}

	public int getMaxYWithin(int x1, int x2, int z1, int z2) {
		assert (x1 >= 0 && x2 <= 15 && z1 >= 0 && z2 <= 15);
		int maxY = Integer.MIN_VALUE;
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++) {
				int y = getBlockY(x, z);
				if (y > maxY)
					maxY = y;
			}
		return maxY;
	}

	public int getBlockY(int x, int z) {
		return NoiseGenerator.floor(blockYs[x][z]);
	}

	public double getPerciseY(int x, int z) {
		return blockYs[x][z];
	}

	public BlockPos getHighPoint() {
		return new BlockPos(maxHeightX, maxHeight, maxHeightZ);
	}

	public BlockPos getLowPoint() {
		return new BlockPos(minHeightX, minHeight, minHeightZ);
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

	public int getSegmentWidth() {
		return segmentWidth;
	}
}
