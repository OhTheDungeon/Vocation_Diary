package cn.shadow.vacation_diary.dimension.support;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.WorldGenRegion;

public final class RelativeBlocks extends SupportBlocks {

	private final int originX;
	private final int originZ;
	private final WorldGenRegion world;

	public RelativeBlocks(VocationCityWorldGenerator generator, SupportBlocks relative, WorldGenRegion region) {
		super(generator);

		this.originX = relative.getOriginX();
		this.originZ = relative.getOriginZ();
		this.world = region;
	}

	@Override
	public WorldBlock getActualBlock(int x, int y, int z) {
		return new WorldBlock(new BlockPos(originX + x, y, originZ + z), world);
	}

	@Override
	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return isEmpty(x - 1, y, z) && isEmpty(x + 1, y, z) && isEmpty(x, y, z - 1) && isEmpty(x, y, z + 1);
	}

	@Override
	public boolean isByWater(int x, int y, int z) {
		return isWater(x - 1, y, z) || isWater(x + 1, y, z) || isWater(x, y, z - 1) || isWater(x, y, z + 1);
	}

}
