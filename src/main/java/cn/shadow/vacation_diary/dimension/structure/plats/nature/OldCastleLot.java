package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;

public class OldCastleLot extends ConstructLot {

	private final Block platformMaterial;
	private final Block supportMaterial;
	private final Block wallMaterial;

	public OldCastleLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		trulyIsolated = true;
		
		platformMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_Castles, chunkOdds, Blocks.COBBLESTONE);

		if (chunkOdds.playOdds(Odds.oddsPrettyLikely))
			wallMaterial = platformMaterial;
		else
			wallMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_Castles, chunkOdds, platformMaterial);

		if (chunkOdds.playOdds(Odds.oddsSomewhatLikely))
			supportMaterial = platformMaterial;
		else
			supportMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_Castles, chunkOdds, platformMaterial);
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OldCastleLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return blockYs.getMinHeight();
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return getBottomY(generator) + DataContext.FloorHeight * 4 + 1;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

		// main bits
		int floorHeight = DataContext.FloorHeight;
		int y1 = blockYs.getMinHeight() + ((blockYs.getMaxHeight() - blockYs.getMinHeight()) / 3 * 2);
		int y2 = y1 + floorHeight;
		int y3 = y2 + floorHeight;
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();

		// random bits
		int secondX1 = 2 + chunkOdds.getRandomInt(3);
		int secondZ1 = 2 + chunkOdds.getRandomInt(3);
		int thirdX1 = chunkOdds.flipCoin() ? secondX1 : secondX1 + 4;
		int thirdZ1 = chunkOdds.flipCoin() ? secondZ1 : secondZ1 + 4;

		// legs
//		chunk.setBlocks(2, 4, minHeight, maxHeight - 1, 2, 4, supportId);
//		chunk.setBlocks(2, 4, minHeight, maxHeight - 1, 7, 9, supportId);
//		chunk.setBlocks(2, 4, minHeight, maxHeight - 1, 12, 14, supportId);
//		chunk.setBlocks(7, 9, minHeight, maxHeight - 1, 2, 4, supportId);
//		//chunk.setBlocks(7, 9, minHeight, maxHeight - 1, 7, 9, supportId);
//		chunk.setBlocks(7, 9, minHeight, maxHeight - 1, 12, 14, supportId);
//		chunk.setBlocks(12, 14, minHeight, maxHeight - 1, 2, 4, supportId);
//		chunk.setBlocks(12, 14, minHeight, maxHeight - 1, 7, 9, supportId);
//		chunk.setBlocks(12, 14, minHeight, maxHeight - 1, 12, 14, supportId);

		// platform
		chunk.setWalls(2, 14, blockYs.getMinHeight(), y1 - 2, 2, 14, supportMaterial);
		for (int i = 4; i < 11; i += 3) {
			chunk.setBlocks(i, i + 2, blockYs.getMinHeight(), y1 - 2, 1, 2, supportMaterial);
			chunk.setBlocks(i, i + 2, blockYs.getMinHeight(), y1 - 2, 14, 15, supportMaterial);
			chunk.setBlocks(1, 2, blockYs.getMinHeight(), y1 - 2, i, i + 2, supportMaterial);
			chunk.setBlocks(14, 15, blockYs.getMinHeight(), y1 - 2, i, i + 2, supportMaterial);
		}
		chunk.setBlocks(1, 15, y1 - 2, 1, 15, supportMaterial);

		// clear things out a bit
		chunk.airoutBlocks(generator, 0, 16, y1, blockYs.getMaxHeight() + 2, 0, 16, true);

		// add the first layer
		chunk.setLayer(y1 - 1, platformMaterial);
		chunk.setWalls(0, 11, y1, y2, 0, 6, wallMaterial);
		chunk.setWalls(10, 16, y1, y2, 0, 11, wallMaterial);
		chunk.setWalls(5, 16, y1, y2, 10, 16, wallMaterial);
		chunk.setWalls(0, 6, y1, y2, 5, 16, wallMaterial);
		chunk.setWalls(0, 16, y2, y2 + 1, 0, 16, wallMaterial);
		chunk.setBlocks(1, 15, y2, 1, 15, platformMaterial);
		chunk.setWalls(0, 16, y2 + 1, y2 + 2, 0, 16, supportMaterial);

		// add trim
		for (int i = 0; i < 13; i += 3) {
			chunk.setBlock(i, y2 + 2, 0, supportMaterial);
			chunk.setBlock(15 - i, y2 + 2, 15, supportMaterial);
			chunk.setBlock(15, y2 + 2, i, supportMaterial);
			chunk.setBlock(0, y2 + 2, 15 - i, supportMaterial);
		}

		// add retaining walls if needed
		for (int i = 0; i < 15; i++) {
			buildWall(chunk, i, y2 + 2, generator.getFarBlockY(originX + i, originZ - 1), 0);
			buildWall(chunk, i, y2 + 2, generator.getFarBlockY(originX + i, originZ + 16), 15);
			buildWall(chunk, 0, y2 + 2, generator.getFarBlockY(originX - 1, originZ + i), i);
			buildWall(chunk, 15, y2 + 2, generator.getFarBlockY(originX + 16, originZ + i), i);
		}

		// punch out the doors
		punchOutNSDoor(generator, chunk, 10, y1, 2);
		punchOutNSDoor(generator, chunk, 5, y1, 7);
		punchOutNSDoor(generator, chunk, 10, y1, 7);
		punchOutNSDoor(generator, chunk, 5, y1, 12);
		punchOutWEDoor(generator, chunk, 2, y1, 5);
		punchOutWEDoor(generator, chunk, 7, y1, 5);
		punchOutWEDoor(generator, chunk, 7, y1, 10);
		punchOutWEDoor(generator, chunk, 12, y1, 10);

		// add second level
		buildTower(generator, chunk, secondX1, y2, secondZ1, 9);

		// add third level
		buildTower(generator, chunk, thirdX1, y3, thirdZ1, 5);
	}

	private void buildWall(InitialBlocks chunk, int x, int y1, int y2, int z) {
		if (y2 > y1)
			chunk.setBlocks(x, y1, y2 + 2, z, supportMaterial);
	}

	private void buildTower(VocationCityWorldGenerator generator, InitialBlocks chunk, int x, int y1, int z, int width) {
		int y2 = y1 + DataContext.FloorHeight;
		chunk.setWalls(x, x + width, y1 + 1, y2 + 1, z, z + width, wallMaterial);
		chunk.setBlocks(x + 1, x + width - 1, y2, z + 1, z + width - 1, platformMaterial);

		// add trim
		chunk.setWalls(x, x + width, y2 + 1, y2 + 2, z, z + width, supportMaterial);
		for (int i = 0; i < width - 1; i += 2) {
			chunk.setBlock(x + i, y2 + 2, z, supportMaterial);
			chunk.setBlock(x + width - 1 - i, y2 + 2, z + width - 1, supportMaterial);
			chunk.setBlock(x, y2 + 2, z + width - 1 - i, supportMaterial);
			chunk.setBlock(x + width - 1, y2 + 2, z + i, supportMaterial);

			// windows
			if (i > 0) {
				punchOutWindow(generator, chunk, x + i, y1 + 2, z);
				punchOutWindow(generator, chunk, x + width - 1 - i, y1 + 2, z + width - 1);
				punchOutWindow(generator, chunk, x, y1 + 2, z + width - 1 - i);
				punchOutWindow(generator, chunk, x + width - 1, y1 + 2, z + i);
			}
		}
	}

	private void punchOutWindow(VocationCityWorldGenerator generator, InitialBlocks chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.airoutBlocks(generator, x, y, y + 1 + chunkOdds.getRandomInt(2), z, true);
	}

	private void punchOutNSDoor(VocationCityWorldGenerator generator, InitialBlocks chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.airoutBlocks(generator, x, x + 1, y, y + 3, z, z + 2, true);
	}

	private void punchOutWEDoor(VocationCityWorldGenerator generator, InitialBlocks chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.airoutBlocks(generator, x, x + 2, y, y + 3, z, z + 1, true);
	}

	private static final int insetChaos = 3;

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		generator.reportLocation("Castle", chunk);

		// main bits
		int floorHeight = DataContext.FloorHeight;
		int y1 = blockYs.getMinHeight() + ((blockYs.getMaxHeight() - blockYs.getMinHeight()) / 3 * 2);
		int y2 = y1 + floorHeight;
		int y3 = y2 + floorHeight;
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();

		// random bits
//		int secondX1 = 2 + chunkRandom.nextInt(3);
//		int secondZ1 = 2 + chunkRandom.nextInt(3);
//		int thirdX1 = chunkRandom.nextBoolean() ? secondX1 : secondX1 + 4;
//		int thirdZ1 = chunkRandom.nextBoolean() ? secondZ1 : secondZ1 + 4;

		// always an ex-castle
		generator.destroyWithin(originX + insetChaos, originX + 16 - insetChaos, y1, y3,
				originZ + insetChaos, originZ + 16 - insetChaos, false);

		// who is the king of the hill
		int x = 7;
		int z = 7;
		int y = chunk.findFirstEmpty(x, y2, z, y1, y3);
		// TODO 不要生成怪物副本？
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, x, y, z, EntityType.IRON_GOLEM,
				EntityType.WITCH);
	}
}
