package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class WorldBlock extends BaseBlock {
	private BlockPos pos;
	private IWorld world;
	public WorldBlock(BlockPos pos, IWorld world) {
		this.pos = pos;
		this.world = world;
	}
	
	@Override
	public BlockState getBlockState() {
		return world.getBlockState(pos);
	}
	
	@Override
	public void setBlockState(BlockState blockState, boolean phy) {
		world.setBlockState(pos, blockState, (phy ? 1 : 0) + 2);
	}
	
	@Override
	public void setType(Block block, boolean phy) {
		world.setBlockState(pos, block.getDefaultState(), (phy ? 1 : 0) + 2);
	}
	
	@Override
	public Block getType() {
		return world.getBlockState(pos).getBlock();
	}
	
	public int getX() {
		return pos.getX();
	}
	
	public int getY() {
		return pos.getY();
	}
	
	public int getZ() {
		return pos.getZ();
	}
	
	public boolean isEmpty() {
		return this.getType() == Blocks.AIR;
	}
	
	public boolean isSolid() {
		return world.getBlockState(pos).isSolid();
	}
	
	public boolean isLiquid() {
		return world.getBlockState(pos).getMaterial().isLiquid();
	}
	
	public BlockPos getLocation() {
		return this.pos;
	}
	
	public TileEntity getTileEntity() {
		return world.getTileEntity(pos);
	}
}
