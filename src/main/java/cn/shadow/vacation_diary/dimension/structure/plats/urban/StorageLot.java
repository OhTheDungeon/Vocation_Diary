package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.BuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.SurroundingLots;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class StorageLot extends BuildingLot {

	public StorageLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		trulyIsolated = true;
		contentType = pickContentType();
	}

	private enum ContentType {
		EMPTY, SHED, TANK
	}

	private final ContentType contentType;

	private ContentType pickContentType() {
		ContentType[] values = ContentType.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new StorageLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		int groundY = getBottomY(generator);

		// look around
		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);

		// different things
		switch (contentType) {
		case EMPTY:
		case SHED:
			drawFence(generator, chunk, context, 1, groundY + 2, 0, neighbors, Blocks.IRON_BARS, 3);
			break;
		case TANK:
			break;
		}

		// top it off
		Block floorMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryInsides, chunkOdds, 
				Blocks.SMOOTH_STONE);
		chunk.setLayer(groundY, 2, floorMat);
//		chunk.setLayer(groundY + 1, RoadLot.pavementId);

	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int groundY = getBottomY(generator) + 2;
		int topY = getTopY(generator);

//		// look around
//		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);

		// different things
		switch (contentType) {
		case EMPTY:
			break;
		case SHED:
			generator.structureOnGroundProvider.generateShed(generator, chunk, context, chunkOdds, 7, groundY, 7,
					2 + chunkOdds.getRandomInt(2), LootLocation.STORAGE_SHED);
			break;
		case TANK:
			Block wallMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryInsides, chunkOdds, 
					Blocks.SMOOTH_STONE);
			Block fluidMat = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_FactoryTanks, chunkOdds, 
					Blocks.WATER);
			
			chunk.setCircle(8, 8, 6, groundY, groundY + 2 + chunkOdds.getRandomInt(6), fluidMat, true);
			chunk.setCircle(8, 8, 6, groundY, topY - 3, wallMat);
			chunk.setCircle(8, 8, 6, topY - 3, wallMat, true);
			chunk.setCircle(8, 8, 4, topY - 3, Blocks.AIR, true);
			chunk.setCircle(8, 8, 5, topY - 2, wallMat, true);
			break;
		}

		// it looked so nice for a moment... but the moment has passed
		if (generator.getWorldSettings().includeDecayedBuildings)
			destroyLot(generator, groundY, groundY + 4);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 7, groundY, 7);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return getTopY(generator);
	}

	private int getTopY(VocationCityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

}
