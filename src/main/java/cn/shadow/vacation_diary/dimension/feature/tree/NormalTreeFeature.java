package cn.shadow.vacation_diary.dimension.feature.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.util.noise.NoiseGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class NormalTreeFeature extends TreeFeatureBase {
	
	protected static List<TreeNode> trees;
	
	private static List<TreeNode> small;
	private static List<TreeNode> normal;
	private static List<TreeNode> tall;
	private static int minHeight = 2;
	private static int maxHeight = 15;
	
	public NormalTreeFeature() {
		
	}
	
    static {
    	trees = new ArrayList<>();
    	small = new ArrayList<>(); //4, [2,5]
    	normal = new ArrayList<>();//4, 10)
    	tall = new ArrayList<>();
    	
    	{
    		TreeNode tree = new TreeNode();
    		
			tree.trunkHeight = 4;

			tree.leaves1exist = true;
			tree.leaves1start = -2;
			tree.leaves1end = 2;
			tree.leaves1width = 2;
			tree.leaves1delta = 0;

			trees.add(tree);
			small.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 7;

    		tree.leaves1exist = true;
    		tree.leaves1start = -3;
    		tree.leaves1end = 2;
			tree.leaves1width = 3;
			tree.leaves1delta = 0;

			trees.add(tree);
			normal.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 10;
    		tree.trunkWidth = 2;

    		tree.leaves1exist = true;
    		tree.leaves1start = -4;
    		tree.leaves1end = 2;
    		tree.leaves1width = 3;
    		tree.leaves1delta = 0;

			trees.add(tree);
			tall.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 5;

    		tree.leaves1exist = true;
    		tree.leaves1start = -2;
    		tree.leaves1end = 2;
    		tree.leaves1width = 2;
    		tree.leaves1delta = 0;
    		
    		trees.add(tree);
    		small.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 7;

    		tree.leaves1exist = true;
    		tree.leaves1start = -3;
    		tree.leaves1end = 2;
			tree.leaves1width = 3;
			tree.leaves1delta = 0;
			
			trees.add(tree);
			normal.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 5;

    		tree.leaves1exist = true;
    		tree.leaves1start = -2;
    		tree.leaves1end = 2;
    		tree.leaves1width = 2;
    		tree.leaves1delta = 0.5;
    		
    		trees.add(tree);
    		small.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 10;

    		tree.leaves1exist = true;
    		tree.leaves1start = -4;
    		tree.leaves1end = 2;
    		tree.leaves1width = 3;
    		tree.leaves1delta = 0.5;
    		
    		trees.add(tree);
    		tall.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 15;

    		tree.leaves1exist = true;
			tree.leaves1start = -8;
			tree.leaves1end = -2;
			tree.leaves1width = 3;
			tree.leaves1delta = 0.5;

			tree.leaves2exist = true;
			tree.leaves2start = -2;
			tree.leaves2end = 2;
			tree.leaves2width = 2;
			tree.leaves2delta = 0.5;
			
			trees.add(tree);
			tall.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 2;

    		tree.leaves1exist = true;
    		tree.leaves1start = -2;
    		tree.leaves1end = 2;
    		tree.leaves1width = 2;
    		tree.leaves1delta = 0;

    		trees.add(tree);
    		small.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();

    		tree.trunkHeight = 9;

			tree.leaves1exist = true;
			tree.leaves1start = -3;
			tree.leaves1end = 2;
			tree.leaves1width = 3;
			tree.leaves1delta = 0;
			
			trees.add(tree);
			normal.add(tree);
    	}
    	{
    		TreeNode tree = new TreeNode();
    		
    		tree.trunkHeight = 6;

    		tree.leaves1exist = true;
    		tree.leaves1start = -3;
    		tree.leaves1end = 3;
    		tree.leaves1width = 3;
    		tree.leaves1delta = 0.25;
    		
    		trees.add(tree);
    		normal.add(tree);
    	}
    }
    
    private void generateTrunkBlock(IWorldGenerationReader world, int x, int y, int z, int w, int h, Block material, 
    		Set<BlockPos> changedLogs, MutableBoundingBox boundingBoxIn) {
		for(int i = x; i < x + w; i++) {
			for(int j = y; j < y + h; j++) {
				for(int k = z; k < z + w; k++) {
					BlockPos pos = new BlockPos(i, j, k);
					world.setBlockState(pos, material.getDefaultState(), 19);
					changedLogs.add(pos);
					boundingBoxIn.expandTo(new MutableBoundingBox(pos, pos));
				}
			}
		}
	}
    
	@Override
	protected boolean place(IWorldGenerationReader world, Random rand, BlockPos pos,
			Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBoxIn,
			TreeFeatureConfig configIn) {
        int height = this.checkLocation(changedLogs, world, pos, NormalTreeFeature.minHeight + rand.nextInt(NormalTreeFeature.maxHeight), boundingBoxIn);
        if (height == -1) {
            return false;
        } else {
            this.setDirtAt(world, pos.down(), pos);
        }
        
        TreeNode tree = trees.get(rand.nextInt(trees.size()));
        
        int[] max_height = {0};
        
        if (tree.leaves1exist) {
			addLeaves(world, rand, pos.getX(), pos.getY(), pos.getZ(), BlockRegistry.tea_leaves.get(), 
					tree.trunkWidth, tree.trunkHeight, tree.leaves1start, tree.leaves1end,
					tree.leaves1width, tree.leaves1delta, changedLeaves, boundingBoxIn, max_height);
			if (tree.leaves2exist)
				addLeaves(world, rand, pos.getX(), pos.getY(), pos.getZ(), BlockRegistry.tea_leaves.get(), 
						tree.trunkWidth, tree.trunkHeight, tree.leaves2start, tree.leaves2end,
						tree.leaves2width, tree.leaves2delta, changedLeaves, boundingBoxIn, max_height);
		}
        
        int actual_height;
        if(max_height[0] != 0) 
        	actual_height = max_height[0] - pos.getY();
        else
        	actual_height = tree.trunkHeight;
        
        generateTrunkBlock(world, pos.getX(), pos.getY(), pos.getZ(), 
        		tree.trunkWidth, actual_height, BlockRegistry.tea_log.get(), 
        		changedLogs, boundingBoxIn);

        return true;
	}

	private void addLeaves(IWorldGenerationReader chunk, Random rand, 
			int trunkX, int trunkY, int trunkZ, Block leavesMaterial,
			int trunkWidth, int trunkHeight, int start, int end, double width, double delta, 
			Set<BlockPos> changedLeaves, MutableBoundingBox boundingBoxIn, int[] max_height) {

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
					double leavesOdds = 20.0 / 21.00;

					// extremes
					if (x == minX || x == maxX - 1) {
						if (z == minZ || z == maxZ - 1)
							leavesOdds = 0;
						else if (y == minY || y == maxY - 1)
							leavesOdds = 0;
					} else if (z == minZ || z == maxZ - 1) {
						if (x == minX || x == maxX - 1)
							leavesOdds = 0;
						else if (y == minY || y == maxY - 1)
							leavesOdds = 0;
					} else if (y == minY || y == maxY - 1) {
						if (x == minX || x == maxX - 1)
							leavesOdds = 0;
						else if (z == minZ || z == maxZ - 1)
							leavesOdds = 0;
					}

					// worth doing?
					if (leavesOdds > 0.00 && rand.nextDouble() < leavesOdds)
						generateLeafBlock(chunk, x, y, z, leavesMaterial, changedLeaves, boundingBoxIn, max_height);
				}
			}

			// make it smaller as we go higher
			widthAt = widthAt - delta;
		}
	}
	
	void generateLeafBlock(IWorldGenerationReader world, int x, int y, int z, Block material, 
			Set<BlockPos> changedLeaves, MutableBoundingBox boundingBoxIn, int[] max_height) {
		max_height[0] = max_height[0] > y ? max_height[0] : y;
		BlockPos pos = new BlockPos(x, y, z);
		world.setBlockState(pos, material.getDefaultState(), 19);
		changedLeaves.add(pos);
		boundingBoxIn.expandTo(new MutableBoundingBox(pos, pos));
	}
}
