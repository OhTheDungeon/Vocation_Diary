package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;

public abstract class GravelLot extends ConstructLot {

	GravelLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	static void generateTailings(VocationCityWorldGenerator generator, Odds odds, RealBlocks chunk, int x1, int x2,
			int z1, int z2) {
		generateTailings(generator, odds, chunk, x1, x2, generator.streetLevel, z1, z2);
	}

	private static void generateTailings(VocationCityWorldGenerator generator, Odds odds, RealBlocks chunk, int x1, int x2,
			int y, int z1, int z2) {

		// clear out some room above the tailings
		if (x1 + 1 < x2 - 1 && z1 + 1 < z2 - 1)
			chunk.setBlocks(x1 + 1, x2 - 1, y, z1 + 1, z2 - 1, Blocks.AIR);
		if (x1 + 2 < x2 - 2 && z1 + 2 < z2 - 2)
			chunk.setBlocks(x1 + 2, x2 - 2, y - 1, z1 + 2, z2 - 2, Blocks.AIR);

		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int yOffset;
				if (x == x1 || z == z1 || x == x2 - 1 || z == z2 - 1)
					yOffset = 0;
				else if (x == x1 + 1 || z == z1 + 1 || x == x2 - 2 || z == z2 - 2)
					yOffset = 1;
				else
					yOffset = 2;

				switch (odds.getRandomInt(3)) {
				case 0:
					chunk.setBlock(x, y - yOffset, z, Blocks.COBBLESTONE_SLAB);
					chunk.setBlock(x, y - 1 - yOffset, z, Blocks.COBBLESTONE);
					break;
				case 1:
					chunk.setBlock(x, y + 1 - yOffset, z, Blocks.COBBLESTONE_SLAB);
					chunk.setBlock(x, y - yOffset, z, Blocks.COBBLESTONE);
					chunk.setBlock(x, y - 1 - yOffset, z, Blocks.COBBLESTONE);
					break;
				default:
					chunk.setBlock(x, y - yOffset, z, Blocks.COBBLESTONE);
					break;
				}
			}
		}
	}

	void generateBase(VocationCityWorldGenerator generator, RealBlocks chunk) {
		chunk.setBlocks(1, 15, generator.streetLevel, 1, 15, Blocks.COBBLESTONE);
		for (int i = 0; i < 10; i++) {
			if (chunkOdds.flipCoin())
				chunk.setBlock(i + 2, generator.streetLevel, 0, Blocks.COBBLESTONE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(15, generator.streetLevel, i + 3, Blocks.COBBLESTONE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(13 - i, generator.streetLevel, 15, Blocks.COBBLESTONE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(0, generator.streetLevel, 13 - i, Blocks.COBBLESTONE);
		}
	}

	static void generatePile(VocationCityWorldGenerator generator, Odds odds, RealBlocks chunk, int x, int z,
			int width) {
		Block specialMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_QuaryPiles, odds, Blocks.COBBLESTONE);
		int y = generator.streetLevel + 1;
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			for (int a = 0; a < width; a++) {
				for (int b = 0; b < width; b++) {
					int base = 2;
					if (a == 0 || a == width - 1)
						base--;
					if (b == 0 || b == width - 1)
						base--;
					int height = odds.getRandomInt(base, 3);
					for (int c = 0; c < height; c++)
						chunk.setBlock(x + a, y + c, z + b,
								odds.playOdds(Odds.oddsVeryUnlikely) ? specialMaterial : Blocks.COBBLESTONE);
				}
			}
		}
	}

	static void generateHole(VocationCityWorldGenerator generator, Odds odds, RealBlocks chunk, int highestY, int width,
			int lowestY) {
		generateHole(generator, odds, chunk, highestY, width, lowestY, true);
	}

	public static void generateHole(VocationCityWorldGenerator generator, Odds odds, RealBlocks chunk, int highestY, int width,
			int lowestY, boolean doTailings) {
		width = (width / 2) * 2; // make sure width is even

		// get ready to dig
		int xz = (chunk.width - width) / 2;
		int depth = chunk.width - (xz * 2) - 1;
		int sectionTop = highestY;
		int y = sectionTop;

		// while the hole is wide enough
		int origin = xz;
		while (depth > 3) {

			// ***** steps
			for (int i = 0; i < depth - 1; i++) {

				// north/south sides
				generateStep(chunk, origin + i, y, sectionTop, origin, Blocks.COBBLESTONE_STAIRS, Direction.WEST,
						Direction.EAST);
				generateStep(chunk, origin + depth - i, y, sectionTop, origin + depth, Blocks.COBBLESTONE_STAIRS,
						Direction.EAST, Direction.WEST);

				// east/west sides
				generateStep(chunk, origin + depth, y, sectionTop, origin + i, Blocks.COBBLESTONE_STAIRS,
						Direction.NORTH, Direction.SOUTH);
				generateStep(chunk, origin, y, sectionTop, origin + depth - i, Blocks.COBBLESTONE_STAIRS,
						Direction.SOUTH, Direction.NORTH);

				// next level down
				y = y - 1;
			}

			// ***** landings
			// north/south sides
			generateLanding(chunk, origin + depth - 1, y, sectionTop, origin, Blocks.COBBLESTONE);
			generateLanding(chunk, origin + 1, y, sectionTop, origin + depth, Blocks.COBBLESTONE);

			// east/west sides
			generateLanding(chunk, origin + depth, y, sectionTop, origin + depth - 1, Blocks.COBBLESTONE);
			generateLanding(chunk, origin, y, sectionTop, origin + 1, Blocks.COBBLESTONE);

			// clear out the in between space
			chunk.setBlocks(origin + 1, origin + depth, y, sectionTop + 1, origin + 1, origin + depth, Blocks.AIR);

			// too far?
			if (y <= lowestY) {

				// rough up the bottom
				if (doTailings)
					generateTailings(generator, odds, chunk, origin + 1, origin + depth, y, origin + 1, origin + depth);

				// all done
				break;

				// more to do?
			} else {

				// increment/decrement to do the next level
				origin = origin + 1;
				depth = depth - 2;
				sectionTop = y;

				// last one?
				if (depth <= 3) {

					// north/south sides
					chunk.setBlock(origin, y, origin, Blocks.COBBLESTONE_STAIRS, Direction.WEST);
					chunk.setBlock(origin + depth, y, origin + depth, Blocks.COBBLESTONE_STAIRS, Direction.EAST);

					// east/west sides
					chunk.setBlock(origin + depth, y, origin, Blocks.COBBLESTONE_STAIRS, Direction.NORTH);
					chunk.setBlock(origin, y, origin + depth, Blocks.COBBLESTONE_STAIRS, Direction.SOUTH);

					// all done
					break;
				}
			}
		}

		// top it off
//		y = generator.streetLevel + 1;
//		generateTailings(generator, chunk, 0, 16, 0, 1);
//		generateTailings(generator, chunk, 0, 16, 15, 16);
//		generateTailings(generator, chunk, 0, 1, 1, 15);
//		generateTailings(generator, chunk, 15, 16, 1, 15);
	}

	private static void generateStep(RealBlocks chunk, int x, int y1, int y2, int z, Block step,
			Direction directionTop, Direction directionBottom) {
		chunk.setBlock(x, y1, z, step, directionTop);
		if (chunk.isEmpty(x, y1 - 1, z))
			chunk.setBlock(x, y1 - 1, z, step, directionBottom, Half.TOP);
		chunk.setBlocks(x, y1 + 1, y2 + 1, z, Blocks.AIR);
	}

	private static void generateLanding(RealBlocks chunk, int x, int y1, int y2, int z, Block landing) {
		chunk.setBlock(x, y1, z, landing);
		chunk.setBlocks(x, y1 + 1, y2 + 1, z, Blocks.AIR);
	}

}
