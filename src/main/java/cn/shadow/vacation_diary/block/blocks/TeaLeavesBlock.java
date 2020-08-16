package cn.shadow.vacation_diary.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;

public class TeaLeavesBlock extends LeavesBlock {
	public TeaLeavesBlock(Block.Properties properties) {
		super(properties);
	}
	
	public boolean ticksRandomly(BlockState state) {
		return false;
	}
}
