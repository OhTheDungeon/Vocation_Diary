package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.RelativeBlocks;
import cn.shadow.vacation_diary.dimension.support.RememberedBlocks;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.dimension.support.TreeSpecies;
import cn.shadow.vacation_diary.dimension.support.Trees;
import cn.shadow.vacation_diary.util.noise.NoiseGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public abstract class TreeProvider {

	public enum TreeStyle {
		NORMAL, SPOOKY, CRYSTAL
	}

	public static TreeStyle toTreeStyle(String value, TreeStyle defaultValue) {
		try {
			return TreeStyle.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	TreeProvider(VocationCityWorldGenerator generator) {
		this.generator = generator;
	}

	public final VocationCityWorldGenerator generator;
	Odds odds;

	public static TreeProvider loadProvider(VocationCityWorldGenerator generator, Odds odds) {

		TreeProvider provider = null;

		// get the right defaults
		switch (generator.getWorldSettings().treeStyle) {
		case SPOOKY:
			provider = new TreeProvider_Spooky(generator);
			break;
		case CRYSTAL:
			provider = new TreeProvider_Crystal(generator);
			break;
		case NORMAL:
		default:
			provider = new TreeProvider_Normal(generator);
			break;
		}

		provider.odds = odds;

		return provider;
	}

	public static TreeSpecies getTreeSpecies(TreeType treeType) {
		switch (treeType) {
		case ACACIA:
			return TreeSpecies.ACACIA;
		case DARK_OAK:
			return TreeSpecies.DARK_OAK;
		case JUNGLE:
		case JUNGLE_BUSH:
		case SMALL_JUNGLE:
		case COCOA_TREE:
			return TreeSpecies.JUNGLE;
		case TALL_REDWOOD:
		case MEGA_REDWOOD:
		case REDWOOD:
			return TreeSpecies.REDWOOD;
		case BIRCH:
		case TALL_BIRCH:
			return TreeSpecies.BIRCH;
		case SWAMP:
		case BIG_TREE:
		case TREE:
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		default:
			return TreeSpecies.GENERIC;
		}
	}

	public static TreeType getTreeType(TreeSpecies treeSpecies) {
		switch (treeSpecies) {
		case ACACIA:
			return TreeType.ACACIA;
		case BIRCH:
			return TreeType.BIRCH;
		case DARK_OAK:
			return TreeType.DARK_OAK;
		case JUNGLE:
			return TreeType.JUNGLE;
		case REDWOOD:
			return TreeType.REDWOOD;
		case GENERIC:
		default:
			return TreeType.TREE;
		}
	}

	//NOTE when used as the default world generator (via bukkit.yml), placing leafs beyond the current chunk causes exceptions
	void generateLeafBlock(SupportBlocks chunk, int x, int y, int z, Block material, Colors colors) {
		// this variant does nothing with the special color
		if (chunk.insideXYZ(x, y, z))
//			try {
			if (chunk.isEmpty(x, y, z))
				chunk.setLeaf(x, y, z, material, false);
//			} catch (Exception e) {
//				generator.reportFormatted("LEAF EXCEPTION at %d, %d, %d", x, y, z);
//				generator.reportException("EXCEPTION", e);
//				// TODO: handle exception
//			}
	}

	void generateTrunkBlock(SupportBlocks chunk, int x, int y, int z, int w, int h, Block material) {
		chunk.setBlocks(x, x + w, y, y + h, z, z + w, material);
	}

	public final void generateMiniTrunk(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			TreeType treeType) {
		generateMiniTree(generator, chunk, x, y, z, treeType, false);
	}

	public final void generateMiniTree(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			TreeType treeType) {
		generateMiniTree(generator, chunk, x, y, z, treeType, true);
	}

	private void generateMiniTree(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			TreeType treeType, Boolean includeLeaves) {
		int trunkHeight = 2;

		// Figure out the height
		switch (treeType) {
		default:
		case TREE:
		case REDWOOD:
		case BIRCH:
		case JUNGLE_BUSH:
			trunkHeight = 2;
			break;

		case BIG_TREE:
		case TALL_REDWOOD:
		case TALL_BIRCH:
		case SMALL_JUNGLE:
			trunkHeight = 3;
			break;

		case JUNGLE:
		case ACACIA:
		case SWAMP:
			trunkHeight = 4;
			break;

		case DARK_OAK:
		case MEGA_REDWOOD:
			trunkHeight = 6;
			break;

		case BROWN_MUSHROOM: // TODO: We don't do these yet
		case RED_MUSHROOM:
			trunkHeight = 0;
			break;
		}

		// Figure out the material data
		Block trunkMaterial = BlockRegistry.tea_log.get();
		Block leavesMaterial = BlockRegistry.tea_leaves.get();

		// something to do?
		if (trunkHeight > 0) {

			// a place to work
			RelativeBlocks blocks = new RelativeBlocks(generator, chunk, chunk.getWorldGenRegion());

			// do the trunk
			generateTrunkBlock(blocks, x, y, z, 1, trunkHeight, trunkMaterial);

			// for that special case
			Colors leafColor = new Colors(odds);

			// and then do the leaves... maybe
			if (includeLeaves) {
				int leavesHeight = trunkHeight - 1;
				generateLeafBlock(blocks, x - 1, y + leavesHeight, z, leavesMaterial, leafColor);
				generateLeafBlock(blocks, x + 1, y + leavesHeight, z, leavesMaterial, leafColor);
				generateLeafBlock(blocks, x, y + leavesHeight, z - 1, leavesMaterial, leafColor);
				generateLeafBlock(blocks, x, y + leavesHeight, z + 1, leavesMaterial, leafColor);
				generateLeafBlock(blocks, x, y + trunkHeight, z, leavesMaterial, leafColor);
			}

		}
	}

	private static final int maxDepth = 5;

	private int generateRootBall(SupportBlocks chunk, RememberedBlocks originalBlocks, int x1, int x2, int y1, int z1,
			int z2, Block root) {

		// set things up
		int y = y1;
		boolean foundBase = false;
		while (!foundBase && y > y1 - maxDepth) {

			// assume success
			boolean partialLevel = false;
			for (int x = x1; x < x2; x++) {
				for (int z = z1; z < z2; z++) {
					if (chunk.isEmpty(x, y, z)) {
						partialLevel = true;
					}
					if (partialLevel)
						break;
				}
				if (partialLevel)
					break;
			}

			// failed? if so move down
			if (partialLevel) {

				// clear this level but remember what was there
				originalBlocks.clearBlocks(x1, x2, y, z1, z2);
				y--;
			} else
				foundBase = true;
		}

		// add the root base
		if (foundBase) {
			originalBlocks.setBlocks(x1, x2, y, z1, z2, root);

			// clear a bit of room out above it
			originalBlocks.clearBlocks(x1, x2, y + 1, y + 3, z1, z2);

			// return just above the root ball
			return y + 1;
		} else {
			return 0;
		}
	}

	public final void generateNormalTrunk(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z,
			TreeType treeType) {

		// how wide is the trunk?
		int trunkWidth = 1;
		int trunkHeight = odds.getRandomInt(3, 6);
		switch (treeType) {
		case DARK_OAK:
		case JUNGLE:
		case MEGA_REDWOOD:
			trunkWidth = 2;
			trunkHeight = trunkHeight * 2;

			// scoot the origin if needed
			if (x == 15)
				x = 14;
			if (z == 15)
				z = 14;
			break;
		default:
			break;
		}

		// what is under the trunk?
		Block root = Blocks.DIRT;
		switch (treeType) {
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
			root = Blocks.MYCELIUM;
			break;
		default:
			break;
		}

		// let try and plant something, or at least give it a whirl
		RememberedBlocks originalBlocks = new RememberedBlocks(chunk);
		int rootAt = generateRootBall(chunk, originalBlocks, x, x + trunkWidth, y, z, z + trunkWidth, root);
		if (rootAt < 1) {
		}

		// lets put a trunk on that then
		else {

			// create the trunk
			Trees trees = new Trees(odds);
			chunk.setBlocks(x, x + trunkWidth, rootAt, rootAt + trunkHeight, z, z + trunkWidth,
					trees.getRandomWoodLog(), Direction.UP);

			// roughen up the top bit
			if (trunkWidth > 1)
				chunk.clearBlocks(x, x + trunkWidth, rootAt + trunkHeight - 1, z, z + trunkWidth, odds);

			// all done then
		}
	}

	public final void generateNormalTree(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			TreeType treeType) {
		generateNormalTree(generator, chunk, x, y, z, treeType, true);
	}

	private void generateNormalTree(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			TreeType treeType, boolean includeLeaves) {
		Block trunkMaterial = BlockRegistry.tea_log.get();
		Block leavesMaterial = BlockRegistry.tea_leaves.get();
//		int trunkBlackMagicData = 0;
		int trunkHeight = 2;
		int trunkWidth = 1;

		boolean leaves1exist = false;
		int leaves1start = 1;
		int leaves1end = 3;
		double leaves1width = 2;
		double leaves1delta = 0;

		boolean leaves2exist = false;
		int leaves2start = 1;
		int leaves2end = 3;
		double leaves2width = 2;
		double leaves2delta = 0;

		// Figure out the tree
		switch (treeType) {
		default:
		case TREE:
			trunkHeight = 4;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case BIG_TREE:
			trunkHeight = 7;

			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;
		case DARK_OAK:
			trunkHeight = 10;
			trunkWidth = 2;

			leaves1exist = true;
			leaves1start = -4;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;

		case BIRCH:
			trunkHeight = 5;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case TALL_BIRCH:
			trunkHeight = 7;

			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;

		case REDWOOD:
			trunkHeight = 5;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0.5;
			break;
		case TALL_REDWOOD:
			trunkHeight = 10;

			leaves1exist = true;
			leaves1start = -4;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0.5;
			break;
		case MEGA_REDWOOD:
			trunkHeight = 15;

			leaves1exist = true;
			leaves1start = -8;
			leaves1end = -2;
			leaves1width = 3;
			leaves1delta = 0.5;

			leaves2exist = true;
			leaves2start = -2;
			leaves2end = 2;
			leaves2width = 2;
			leaves2delta = 0.5;
			break;

		case JUNGLE_BUSH:
			trunkHeight = 2;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case SMALL_JUNGLE:
			trunkHeight = 5;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case JUNGLE:
			trunkHeight = 9;

			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;

		case ACACIA:
			trunkHeight = 6;

			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 3;
			leaves1width = 3;
			leaves1delta = 0.25;
			break;

		case BROWN_MUSHROOM: // TODO: We don't do these yet
		case RED_MUSHROOM:
		case SWAMP:
			trunkHeight = 0;
			break;
		}

		// something to do?
		if (trunkHeight > 0) {

			// a place to work
			RelativeBlocks blocks = new RelativeBlocks(generator, chunk, chunk.getWorldGenRegion());
			
			int[] max_height = {0};
			// and then do the leaves... maybe
			if (includeLeaves) {
				if (leaves1exist) {
					addLeaves(blocks, x, y, z, leavesMaterial, trunkWidth, trunkHeight, leaves1start, leaves1end,
							leaves1width, leaves1delta, max_height);
					if (leaves2exist)
						addLeaves(blocks, x, y, z, leavesMaterial, trunkWidth, trunkHeight, leaves2start, leaves2end,
								leaves2width, leaves2delta, max_height);
				}
			}
			
			// do the trunk
			int actual_height;
	        if(max_height[0] != 0) 
	        	actual_height = max_height[0] - y;
	        else
	        	actual_height = trunkHeight;
				
			generateTrunkBlock(blocks, x, y, z, trunkWidth, actual_height, trunkMaterial);

		}
	}

	private final static double edgeOdds = 0.00; // Not chance of edge bits

	private void addLeaves(SupportBlocks chunk, int trunkX, int trunkY, int trunkZ, Block leavesMaterial,
			int trunkWidth, int trunkHeight, int start, int end, double width, double delta, int[] max_height) {

		// for that special case
		Colors leafColor = new Colors(odds);
		boolean randomColor = odds.playOdds(Odds.oddsPrettyUnlikely);
		if (!randomColor)
			leafColor.fixColor();

		// from the bottom up
		double widthAt = width;
		int minY = trunkY + trunkHeight + start;
		int maxY = trunkY + trunkHeight + end;
		for (int y = minY; y < maxY; y++) {

			// calculate the current extremes
			int widthInt = NoiseGenerator.floor(widthAt);
			int minX = trunkX - widthInt;
			int maxX = trunkX + widthInt + trunkWidth;
			int minZ = trunkZ - widthInt;
			int maxZ = trunkZ + widthInt + trunkWidth;

			for (int x = minX; x < maxX; x++) {
				for (int z = minZ; z < maxZ; z++) {

					// odds of leaves
					double leavesOdds = Odds.oddsExceedinglyLikely;

					// extremes
					if (x == minX || x == maxX - 1) {
						if (z == minZ || z == maxZ - 1)
							leavesOdds = edgeOdds;
						else if (y == minY || y == maxY - 1)
							leavesOdds = edgeOdds;
					} else if (z == minZ || z == maxZ - 1) {
						if (x == minX || x == maxX - 1)
							leavesOdds = edgeOdds;
						else if (y == minY || y == maxY - 1)
							leavesOdds = edgeOdds;
					} else if (y == minY || y == maxY - 1) {
						if (x == minX || x == maxX - 1)
							leavesOdds = edgeOdds;
						else if (z == minZ || z == maxZ - 1)
							leavesOdds = edgeOdds;
					}

					// worth doing?
					if (leavesOdds > 0.00 && odds.playOdds(leavesOdds)) {
						generateLeafBlock(chunk, x, y, z, leavesMaterial, leafColor);
						max_height[0] = max_height[0] > y ? max_height[0] : y;
					}
				}
			}

			// make it smaller as we go higher
			widthAt = widthAt - delta;
		}
	}

}
