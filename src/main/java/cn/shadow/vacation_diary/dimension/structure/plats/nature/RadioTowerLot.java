package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.BlockFace;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class RadioTowerLot extends ConstructLot {

	public RadioTowerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new RadioTowerLot(platmap, chunkX, chunkZ);
	}

	private final static int platformWidth = 8;
	private final static int heightRange = 15;
	private final static int heightShortest = 10;
	private final static int heightTallest = heightShortest + heightRange;
	private final boolean antennaBuilt = false;
	private boolean tallestBuilt = false;

	private final static Block platformMaterial = Blocks.SMOOTH_STONE;
	private final static Block supportMaterial = Blocks.COBBLESTONE;
	private final static Block wallMaterial = Blocks.STONE;
	//	private final static Material roofMaterial = Material.STONE_SLAB;
	private final static Block baseMaterial = Blocks.CLAY;
	private final static Block antennaMaterial = Blocks.IRON_BARS;

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return blockYs.getMaxHeight() + 2;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return getBottomY(generator) + heightTallest;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {

		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		BlockPos highPoint = blockYs.getHighPoint();
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, highPoint.getX()));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, highPoint.getZ()));
		int platformY = getBottomY(generator);

		// base
		for (int x = originX + 1; x < originX + platformWidth - 1; x++) {
			for (int z = originZ + 1; z < originZ + platformWidth - 1; z++) {
				for (int y = platformY - 2; y > blockYs.getMinHeight(); y--) {
					if (!chunk.setEmptyBlock(x, y, z, supportMaterial)) {
						chunk.setBlocks(x, y - 3, y + 1, z, supportMaterial);
						break;
					}
				}
			}
		}

		// top it off
		chunk.setBlocks(originX, originX + platformWidth, platformY - 1, originZ, originZ + platformWidth,
				platformMaterial);

//		// base
//		if (minHeight > generator.evergreenLevel)
//			generator.oreProvider.sprinkleSnow(generator, chunk, chunkOdds, originX, originX + platformWidth, platformY, originZ, originZ + platformWidth);
//		
		// building
		chunk.setBlocks(originX + 2, originX + platformWidth - 2, platformY, platformY + 2, originZ + 2,
				originZ + platformWidth - 2, wallMaterial);
		chunk.airoutBlocks(generator, originX + 3, originX + platformWidth - 3, platformY, platformY + 2,
				originZ + 3, originZ + platformWidth - 3, true);
		chunk.setBlocks(originX + 2, originX + platformWidth - 2, platformY + 2, platformY + 3, originZ + 2,
				originZ + platformWidth - 2, platformMaterial);
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		generator.reportLocation("Radio Tower", chunk);

		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		BlockPos highPoint = blockYs.getHighPoint();
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, highPoint.getX()));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, highPoint.getZ()));
		int platformY = getBottomY(generator);

		// place snow
		generateSurface(generator, chunk, false);

		// blow it all up?
		if (generator.getWorldSettings().includeDecayedBuildings) {
			int x1 = chunk.getBlockX(originX);
			int z1 = chunk.getBlockZ(originZ);
			generator.destroyWithin(x1, x1 + platformWidth, blockYs.getAverageHeight(), platformY + 3, z1,
					z1 + platformWidth);

			// place a door but only if everything is "normal"
		} else {
			chunk.setDoor(originX + 2, platformY, originZ + 3, Blocks.BIRCH_DOOR, BlockFace.WEST_NORTH_WEST);

			// place the ladder
			int ladderBase = platformY - 2;
			while (chunk.isEmpty(originX, ladderBase, originZ + 4)) {
				ladderBase--;
			}
			chunk.setLadder(originX, ladderBase, platformY, originZ + 4, Direction.WEST); // fixed
			chunk.airoutBlock(generator, originX, platformY, originZ + 4);

			// place antennas
			generateAntenna(generator, chunk, context, originX + 1, platformY, originZ + 1, false);
			generateAntenna(generator, chunk, context, originX + 1, platformY, originZ + platformWidth - 3, false);
			generateAntenna(generator, chunk, context, originX + platformWidth - 3, platformY, originZ + 1, false);
			generateAntenna(generator, chunk, context, originX + platformWidth - 3, platformY,
					originZ + platformWidth - 3, true);
		}
	}

	private void generateAntenna(VocationCityWorldGenerator generator, RealBlocks chunk, DataContext context, int x, int y,
			int z, boolean lastChance) {

		// build an antenna?
		if ((lastChance && !antennaBuilt) || chunkOdds.flipCoin()) {
			if (!chunk.isEmpty(x, y - 1, z)) {
//				chunk.setBlocks(x, y, y + 2, z, baseMaterial);

				// how tall?
				int antennaHeight = heightShortest;
				if (!tallestBuilt && (lastChance || chunkOdds.flipCoin())) {
					antennaHeight = heightTallest;
					tallestBuilt = true;
				} else
					antennaHeight += chunkOdds.getRandomInt(heightRange);

				// stuff going out?
				boolean middleStuff = chunkOdds.flipCoin();
				boolean topStuff = chunkOdds.flipCoin();

				// actually build the antenna
				for (int y1 = y; y1 < y + antennaHeight; y1++) {

					// horizontal stuff?
					if ((middleStuff && y1 == y + antennaHeight - 5) || (topStuff && y1 == y + antennaHeight - 1)) {

						if (chunkOdds.playOdds(Odds.oddsExtremelyLikely))
							chunk.setBlocks(x - 2, x, y1, z, z + 1, antennaMaterial, Direction.EAST, Direction.WEST);
						if (chunkOdds.playOdds(Odds.oddsExtremelyLikely))
							chunk.setBlocks(x + 2, x + 4, y1, z + 1, z + 2, antennaMaterial, Direction.EAST,
									Direction.WEST);
						if (chunkOdds.playOdds(Odds.oddsExtremelyLikely))
							chunk.setBlocks(x + 1, x + 2, y1, z - 2, z, antennaMaterial, Direction.NORTH,
									Direction.SOUTH);
						if (chunkOdds.playOdds(Odds.oddsExtremelyLikely))
							chunk.setBlocks(x, x + 1, y1, z + 2, z + 4, antennaMaterial, Direction.NORTH,
									Direction.SOUTH);

						chunk.setBlocks(x, x + 2, y1, z, z + 2, baseMaterial);

						// vertical stuff?
					} else {
						if (chunk.isEmpty(x, y1, z))
							chunk.setBlock(x, y1, z, Blocks.IRON_BARS, Direction.EAST, Direction.SOUTH);
						if (chunk.isEmpty(x + 1, y1, z))
							chunk.setBlock(x + 1, y1, z, Blocks.IRON_BARS, Direction.WEST, Direction.SOUTH);
						if (chunk.isEmpty(x, y1, z + 1))
							chunk.setBlock(x, y1, z + 1, Blocks.IRON_BARS, Direction.EAST, Direction.NORTH);
						if (chunk.isEmpty(x + 1, y1, z + 1))
							chunk.setBlock(x + 1, y1, z + 1, Blocks.IRON_BARS, Direction.WEST, Direction.NORTH);
					}
				}

				// top of the tallest one?
				if (antennaHeight == heightTallest) {
					chunk.setBlocks(x, x + 2, y + antennaHeight, z, z + 2, Blocks.END_ROD); // @@ really should be a
					// lantern or something
					// like that
				}
			}
		}
	}
}
