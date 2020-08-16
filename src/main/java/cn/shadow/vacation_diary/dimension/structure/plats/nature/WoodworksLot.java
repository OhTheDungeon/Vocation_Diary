package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.CoverProvider.CoverageType;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public class WoodworksLot extends ConstructLot {

	public WoodworksLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new WoodworksLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {

		int y = generator.streetLevel + 1;
		generateSomething(generator, chunk, 2, y, 3);
		generateSomething(generator, chunk, 8, y, 6);
		generateSomething(generator, chunk, 2, y, 9);
		generateSomething(generator, chunk, 8, y, 12);

		// place snow
		generateSurface(generator, chunk, false);
	}

	private void generateSomething(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {
		switch (chunkOdds.getRandomInt(11)) {
		default:
		case 0:
		case 1:
			// shimmy
			generator.coverProvider.generateCoverage(generator, chunk, chunk.clampXZ(x + chunkOdds.getRandomInt(1, 4)),
					y, z, CoverageType.SHORT_OAK_TREE);
			break;
		case 2:
		case 3:
			generator.coverProvider.generateCoverage(generator, chunk, chunk.clampXZ(x + chunkOdds.getRandomInt(1, 4)),
					y, z, CoverageType.OAK_TRUNK);
			break;
		case 4:
		case 5:
			int logL = chunkOdds.getRandomInt(3, 3);
			int logX = x + 6 - logL;
			if (chunkOdds.flipCoin())
				chunk.setBlocks(logX, chunk.clampXZ(logX + logL), y, z - 1, z, Blocks.OAK_LOG, Direction.EAST);
			chunk.setBlocks(logX, logX + logL, y, z, z + 1, Blocks.OAK_LOG, Direction.EAST);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(logX, chunk.clampXZ(logX + logL), y, z + 1, z + 2, Blocks.OAK_LOG, Direction.EAST);
			break;
		case 6:
		case 7:
			if (chunkOdds.flipCoin())
				chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z - 1, z, Blocks.OAK_PLANKS);
			chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z, z + 1, Blocks.OAK_PLANKS);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z + 1, z + 2, Blocks.OAK_PLANKS);
			break;
		case 8:
			if (chunkOdds.flipCoin())
				chunk.setChest(generator, x + 1, y, z, chunkOdds, generator.lootProvider,
						LootLocation.WOODWORKS);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x + 3, y, z, Blocks.CRAFTING_TABLE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x + 4, y, z, Blocks.FURNACE);
			break;
		case 9:
			Direction direction = Direction.NORTH;
			if (chunkOdds.flipCoin()) {
				if (chunkOdds.flipCoin())
					direction = Direction.SOUTH;
				chunk.setDoubleChest(generator, chunk.clampXZ(chunkOdds.calcRandomRange(x + 1, x + 4)), y, z, direction,
						chunkOdds, generator.lootProvider, LootLocation.WOODWORKS_OUTPUT);
			} else {
				direction = Direction.WEST;
				if (chunkOdds.flipCoin())
					direction = Direction.EAST;
				chunk.setDoubleChest(generator, x, y, chunk.clampXZ(chunkOdds.calcRandomRange(z + 1, z + 4)), direction,
						chunkOdds, generator.lootProvider, LootLocation.WOODWORKS_OUTPUT);
			}
			break;
		case 10:
			if (chunkOdds.flipCoin()) {
				x = x / 8 * 8;
				z = z / 8 * 8;
				generateSection(chunk, x, y + floorHeight, z);
				if (chunkOdds.flipCoin())
					generateStairs(chunk, x + 4, y + floorHeight, z + 1);
			}
			break;
		case 11:
			generator.structureOnGroundProvider.generateFirePit(generator, chunk, chunkOdds, x, y, z);
		}

		// TODO add decay
	}

	final static int sectionWidth = 5;
	final static int floorHeight = 4;

	void generateSection(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, x + sectionWidth + 1, y, y + 1, z, z + sectionWidth + 1, Blocks.BIRCH_SLAB);

		generateColumn(chunk, x, y, z);
		generateColumn(chunk, x + sectionWidth, y, z);
		generateColumn(chunk, x, y, z + sectionWidth);
		generateColumn(chunk, x + sectionWidth, y, z + sectionWidth);
	}

	private void generateColumn(RealBlocks chunk, int x, int y, int z) {
//		if (chunk.isEmpty(x, y - 1, z)) {
//		chunk.setBlock(x, y - floorHeight, z, Material.SPRUCE_PLANKS);
//		chunk.setBlocks(x, y - floorHeight + 1, y, z, Material.SPRUCE_FENCE);
		chunk.setBlocks(x, y - floorHeight, y, z, Blocks.SCAFFOLDING);
//		}
	}

	void generateStairs(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, x + 1, y, z, z + 3, Blocks.AIR);
		chunk.setBlock(x, y - 1, z, Blocks.BIRCH_STAIRS, Direction.NORTH);
		chunk.setBlock(x, y - 2, z + 1, Blocks.BIRCH_STAIRS, Direction.NORTH);
		chunk.setBlock(x, y - 3, z + 2, Blocks.BIRCH_STAIRS, Direction.NORTH);
		chunk.setBlock(x, y - 4, z + 3, Blocks.BIRCH_STAIRS, Direction.NORTH);
	}
}
