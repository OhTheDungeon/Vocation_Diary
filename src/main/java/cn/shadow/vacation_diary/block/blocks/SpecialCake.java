package cn.shadow.vacation_diary.block.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SpecialCake extends BlockCakeUF {

	int foodlevel;
	float saturation;
	String name;
	
	public SpecialCake(String name, int foodlevel, float saturation, Block.Properties properties) {
		super(properties);
		this.foodlevel = foodlevel / 6;
		this.saturation = saturation;
		this.name = name;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			ItemStack itemstack = player.getHeldItem(handIn);
			if (this.func_226911_a_(worldIn, pos, state, player) == ActionResultType.SUCCESS) {
			return ActionResultType.SUCCESS;
			}

			if (itemstack.isEmpty()) {
			return ActionResultType.CONSUME;
			}
		}

		return this.func_226911_a_(worldIn, pos, state, player);
	}


	private ActionResultType func_226911_a_(IWorld p_226911_1_, BlockPos p_226911_2_, BlockState p_226911_3_, PlayerEntity p_226911_4_) {
		if (!p_226911_4_.canEat(false)) {
			return ActionResultType.PASS;
		} else {
			p_226911_4_.addStat(Stats.EAT_CAKE_SLICE);
			p_226911_4_.getFoodStats().addStats(foodlevel, saturation);

			int i = p_226911_3_.get(BITES);
			if (i < 6) {
				p_226911_1_.setBlockState(p_226911_2_, p_226911_3_.with(BITES, Integer.valueOf(i + 1)), 3);
			} else {
				p_226911_1_.removeBlock(p_226911_2_, false);
			}

			return ActionResultType.SUCCESS;
		}
	}
}
