package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.RoadLot;
import cn.shadow.vacation_diary.dimension.structure.plats.nature.BunkerLot.BunkerType;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;

public class RoadThroughBunkerLot extends RoadLot {

	private final int bottomOfBunker;
	private final int topOfBunker;

	public final static Block wallMaterial = tunnelWallMaterial;

	public RoadThroughBunkerLot(PlatMap platmap, int chunkX, int chunkZ, long globalconnectionkey,
			boolean roundaboutPart, BunkerLot originalLot) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);

		this.bottomOfBunker = originalLot.bottomOfBunker;
		this.topOfBunker = originalLot.topOfBunker;
	}

	@Override
	public boolean isValidStrataY(VocationCityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return BunkerLot.bunkerIsValidStrataY(generator, blockX, blockY, blockZ, bottomOfBunker, topOfBunker);
	}

	@Override
	protected boolean isShaftableLevel(VocationCityWorldGenerator generator, int blockY) {
		return BunkerLot.bunkerIsShaftableLevel(generator, blockY, bottomOfBunker, topOfBunker)
				&& super.isShaftableLevel(generator, blockY);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return bottomOfBunker;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return topOfBunker;
	}

	@Override
	protected boolean isValidWithBones() {
		return false;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		super.generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {

		// draw the road
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);

		// do it!
		BunkerLot.generateBunker(generator, platmap, chunk, chunkOdds, context, platX, platZ, blockYs, bottomOfBunker,
				topOfBunker, BunkerType.ROAD);
	}
}
