package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public abstract class MountainFlatLot extends ConstructLot {

	private final static Block retainingWallMaterial = Blocks.SMOOTH_STONE;

	MountainFlatLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.NATURE;
		trulyIsolated = true;
	}

	void generateRetainerLot(VocationCityWorldGenerator generator, InitialBlocks chunk, DataContext context) {

		// flatten things out a bit
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = getBlockY(x, z);

				// add the retaining walls
				if (x == 0 || x == chunk.width - 1 || z == 0 || z == chunk.width - 1) {
					if (y <= blockYs.getAverageHeight()) {
						chunk.setBlocks(x, y - 2, blockYs.getAverageHeight() + 1, z, retainingWallMaterial);
					} else if (y > blockYs.getAverageHeight()) {
						chunk.setBlocks(x, blockYs.getAverageHeight() - 2, y + 1, z, retainingWallMaterial);
					}

					// backfill
				} else {
					if (generator.getWorldSettings().includeDecayedNature) {
						chunk.setBlocks(x, y - 2, blockYs.getAverageHeight() + 1, z, Blocks.SAND);
					} else {
						chunk.setBlocks(x, y - 2, blockYs.getAverageHeight(), z, generator.oreProvider.subsurfaceMaterial);
						chunk.setBlock(x, blockYs.getAverageHeight(), z, generator.oreProvider.surfaceMaterial);
					}
					chunk.airoutBlocks(generator, x, blockYs.getAverageHeight() + 1, blockYs.getMaxHeight() + 1, z, true);
				}
			}
		}
	}

	private final static int bevelInset = 2;

	void generateSmoothedLot(VocationCityWorldGenerator generator, InitialBlocks chunk, DataContext context) {

		// blend the edges
		for (int i = 0; i < bevelInset; i++)
			generateSmoothedLotBevel(generator, chunk, context, i);

		// flatten things out the center bit
		for (int x = bevelInset; x < chunk.width - bevelInset; x++) {
			for (int z = bevelInset; z < chunk.width - bevelInset; z++) {
				int y = getBlockY(x, z);

				if (generator.getWorldSettings().includeDecayedNature) {
					chunk.setBlocks(x, y - 2, blockYs.getAverageHeight() + 1, z, Blocks.SAND);
				} else {
					chunk.setBlocks(x, y - 2, blockYs.getAverageHeight(), z, generator.oreProvider.subsurfaceMaterial);
					chunk.setBlock(x, blockYs.getAverageHeight(), z, generator.oreProvider.surfaceMaterial);
				}
				chunk.airoutBlocks(generator, x, blockYs.getAverageHeight() + 1, blockYs.getMaxHeight() + 1, z, true);
			}
		}
	}

	private void generateSmoothedLotBevel(VocationCityWorldGenerator generator, InitialBlocks chunk, DataContext context,
			int inset) {

		// Xwise
		for (int x = inset; x < chunk.width - inset; x++) {
			generateBevelBlock(generator, chunk, context, inset, x, inset);
			generateBevelBlock(generator, chunk, context, inset, x, chunk.width - inset - 1);
		}

		// Zwise
		for (int z = inset + 1; z < chunk.width - inset - 1; z++) {
			generateBevelBlock(generator, chunk, context, inset, inset, z);
			generateBevelBlock(generator, chunk, context, inset, chunk.width - inset - 1, z);
		}
	}

	private void generateBevelBlock(VocationCityWorldGenerator generator, InitialBlocks chunk, DataContext context, int inset,
			int x, int z) {
		int y = getBlockY(x, z);
		int y1 = y;
		if (y < blockYs.getAverageHeight()) {
			// build up
			y1 = (blockYs.getAverageHeight() - y) / 2 + y;
			chunk.setBlocks(x, y - 1, y1, z, generator.oreProvider.subsurfaceMaterial);
			chunk.setBlock(x, y1, z, generator.oreProvider.surfaceMaterial);
		} else if (y > blockYs.getAverageHeight()) {
			// trim down
			y1 = (y - blockYs.getAverageHeight()) / 2 + blockYs.getAverageHeight();
			chunk.setBlocks(x, blockYs.getAverageHeight() - 1, y1, z, generator.oreProvider.subsurfaceMaterial);
			chunk.setBlock(x, y1, z, generator.oreProvider.surfaceMaterial);
			chunk.airoutBlocks(generator, x, y1 + 1, y + 1, z, true);
		}
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return blockYs.getAverageHeight() + 1;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + DataContext.FloorHeight * 2 + 1;
	}

}
