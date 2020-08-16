package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

public class ChunkBlock extends BaseBlock {
	
	private BlockPos pos;
	private IChunk chunk;
	
	public ChunkBlock(BlockPos pos, IChunk chunk) {
		this.pos = pos;
		this.chunk = chunk;
	}

	@Override
	public BlockState getBlockState() {
		return chunk.getBlockState(pos);
	}

	@Override
	public void setBlockState(BlockState blockState, boolean phy) {
		chunk.setBlockState(pos, blockState, phy);
	}

	@Override
	public void setType(Block block, boolean phy) {
		chunk.setBlockState(pos, block.getDefaultState(), phy);
	}

	@Override
	public Block getType() {
		return getBlockState().getBlock();
	}

	@Override
	public int getX() {
		return pos.getX() + chunk.getPos().x * 16;
	}

	@Override
	public int getZ() {
		return pos.getZ() + chunk.getPos().z * 16;
	}

	@Override
	public boolean isEmpty() {
		return this.getType() == Blocks.AIR;
	}

	@Override
	public BlockPos getLocation() {
		return new BlockPos(getX(), pos.getY(), getZ());
	}

	@Override
	public boolean isSolid() {
		return this.getBlockState().isSolid();
	}

	@Override
	public TileEntity getTileEntity() {
		return chunk.getTileEntity(pos);
	}

	@Override
	public boolean isLiquid() {
		return this.getBlockState().getMaterial().isLiquid();
	}

}
