package cn.shadow.vacation_diary.dimension.feature.tree;

import java.util.Set;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public abstract class TreeFeatureBase extends AbstractTreeFeature<TreeFeatureConfig> {
    protected TreeFeatureBase() {
        super(TreeFeatureConfig::deserializeAcacia);
    }
    
    protected static class TreeNode {
		public int trunkHeight = 2;
		public int trunkWidth = 1;

		public boolean leaves1exist = false;
		public int leaves1start = 1;
		public int leaves1end = 3;
		public double leaves1width = 2;
		public double leaves1delta = 0;

		public boolean leaves2exist = false;
		public int leaves2start = 1;
		public int leaves2end = 3;
		public double leaves2width = 2;
		public double leaves2delta = 0;
    }
    
    /**
     * Returns the absolute greatest distance in the BlockPos object.
     */
    private int getGreatestDistance(BlockPos posIn)
    {
        int i = MathHelper.abs(posIn.getX());
        int j = MathHelper.abs(posIn.getY());
        int k = MathHelper.abs(posIn.getZ());
        return k > i && k > j ? k : (j > i ? j : i);
    }
    
    @SuppressWarnings("unused")
	private Direction.Axis getLogAxis(BlockPos startPos, BlockPos endPos)
    {
        Direction.Axis axis = Direction.Axis.Y;

        //Find the difference between the start and end pos
        int xDiff = Math.abs(endPos.getX() - startPos.getX());
        int zDiff = Math.abs(endPos.getZ() - startPos.getZ());
        int maxDiff = Math.max(xDiff, zDiff);

        //Check if the distance between the two positions is greater than 0 on either
        //the x or the z axis. axis is set to the axis with the greatest distance
        if (maxDiff > 0)
        {
            if (xDiff == maxDiff)
            {
                axis = Direction.Axis.X;
            }
            else if (zDiff == maxDiff)
            {
                axis = Direction.Axis.Z;
            }
        }

        return axis;
    }
    
    private int checkLineAndOptionallySet(Set<BlockPos> changedBlocks, IWorldGenerationReader world, BlockPos startPos, BlockPos endPos, MutableBoundingBox boundingBox)
    {
    	{
            //The distance between the two points, may be negative if the second pos is smaller
            BlockPos delta = endPos.add(-startPos.getX(), -startPos.getY(), -startPos.getZ());

            int steps = this.getGreatestDistance(delta);

            //How much should be incremented with each iteration relative
            //to the greatest distance which will have a value of 1.0F.
            float dx = (float)delta.getX() / (float)steps;
            float dy = (float)delta.getY() / (float)steps;
            float dz = (float)delta.getZ() / (float)steps;

            //Iterates over all values between the start pos and end pos
            for (int j = 0; j <= steps; ++j)
            {
                BlockPos deltaPos = startPos.add((double)(0.5F + (float)j * dx), (double)(0.5F + (float)j * dy), (double)(0.5F + (float)j * dz));
                if (!canBeReplacedByLogs(world, deltaPos))
                {
                    return j;
                }
            }

            return -1;
        }
    }

    
    protected int checkLocation(Set<BlockPos> changedBlocks, IWorldGenerationReader world, BlockPos pos, int height, MutableBoundingBox boundingBox)
    {
        {
            int step = this.checkLineAndOptionallySet(changedBlocks, world, pos, pos.up(height - 1), boundingBox);

            if (step == -1)
            {
                return height;
            }
            else
            {
                return step < 6 ? -1 : step;
            }
        }
    }

}
