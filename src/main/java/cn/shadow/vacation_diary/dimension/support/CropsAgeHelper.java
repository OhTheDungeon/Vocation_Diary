package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.state.properties.BlockStateProperties;

public class CropsAgeHelper {
	public static void setCropBlock(int x, int y, int z, AbstractBlocks blocks, Block crops, double value) {
		BlockState blockState = crops.getDefaultState();
		if(crops instanceof CropsBlock) {
			int max_age = ((CropsBlock) crops).getMaxAge();
			int age = (int) (value * max_age);
			if(blockState.has(BlockStateProperties.AGE_0_7))
				blockState = blockState.with(BlockStateProperties.AGE_0_7, Integer.valueOf(age));
			else if(blockState.has(BlockStateProperties.AGE_0_3))
				blockState = blockState.with(BlockStateProperties.AGE_0_3, Integer.valueOf(age));
		} else if(crops instanceof SweetBerryBushBlock) {
			int max_age = 3;
			int age = (int) (value * max_age);
			blockState = blockState.with(BlockStateProperties.AGE_0_3, Integer.valueOf(age));
		} else if(crops instanceof StemBlock) {
			int max_age = 7;
			int age = (int) (value * max_age);
			blockState = blockState.with(BlockStateProperties.AGE_0_7, Integer.valueOf(age));
		} else if(crops instanceof KelpTopBlock) {
			int max_age = 25;
			int age = (int) (value * max_age);
			blockState = blockState.with(BlockStateProperties.AGE_0_25, Integer.valueOf(age));
		}
		blocks.setBlockState(x, y, z, blockState);
	}
}
