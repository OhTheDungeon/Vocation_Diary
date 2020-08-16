package cn.shadow.vacation_diary.dimension.support;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.WorldGenRegion;

public final class RealBlocks extends SupportBlocks {
	private final IChunk chunk;
	private final WorldGenRegion world;

	public RealBlocks(VocationCityWorldGenerator generator, IChunk aChunk, WorldGenRegion world) {
		super(generator);

		this.chunk = aChunk;
		this.world = world;
		sectionX = chunk.getPos().x;
		sectionZ = chunk.getPos().z;
		super.setDoPhysics(true);
	}
	
	public WorldGenRegion getWorldGenRegion() {
		return world;
	}
	
	public ChunkPos getChunkPos() {
		return this.chunk.getPos();
	}
	
	@Override
	public void setBlock(int x, int y, int z, Block material) {
		//chunk.setBlockState(new BlockPos(x, y, z), material.getDefaultState(), false);
		x = x + sectionX * 16;
		z = z + sectionZ * 16;
		BlockPos pos = new BlockPos(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		world.setBlockState(pos, material.getDefaultState(), 3);
		if(blockState.getMaterial().isLiquid()) {
			IFluidState ifluidstate = world.getFluidState(pos);
			world.getPendingFluidTicks().scheduleTick(pos, ifluidstate.getFluid(), 0);
		}
	}
	
	@Override
	public final boolean setDoPhysics(boolean dophysics) {
		return true;
	}

	@Override
	public ChunkBlock getActualBlock(int x, int y, int z) {
		return new ChunkBlock(new BlockPos(x, y, z), chunk);
	}

	@Override
	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15)
				&& (isEmpty(x - 1, y, z) && isEmpty(x + 1, y, z) && isEmpty(x, y, z - 1) && isEmpty(x, y, z + 1));
	}

	@Override
	public boolean isByWater(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15)
				&& (isWater(x - 1, y, z) || isWater(x + 1, y, z) || isWater(x, y, z - 1) || isWater(x, y, z + 1));
	}
}
