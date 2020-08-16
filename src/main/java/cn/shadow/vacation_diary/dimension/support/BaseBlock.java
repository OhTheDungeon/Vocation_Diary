package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class BaseBlock {
	public abstract BlockState getBlockState();
	public abstract void setBlockState(BlockState blockState, boolean phy);
	public abstract void setType(Block block, boolean phy);
	public abstract Block getType();
	public abstract int getX();
	public abstract int getZ();
	public abstract boolean isEmpty();
	public abstract BlockPos getLocation();
	public abstract boolean isSolid();
	public abstract TileEntity getTileEntity();
	public abstract boolean isLiquid();
}
