package cn.shadow.vacation_diary.block.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagicCake extends CakeBlock {
	int foodlevel;
	float saturation;
	public MagicCake(String name, int foodlevel, float saturation, Block.Properties properties) {
		super(properties);
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
			p_226911_4_.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 3));
			p_226911_4_.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 2000, 0));
			p_226911_4_.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 2000, 0));

			int i = p_226911_3_.get(BITES);
			if (i < 6) {
				p_226911_1_.setBlockState(p_226911_2_, p_226911_3_.with(BITES, Integer.valueOf(i + 1)), 3);
			} else {
				p_226911_1_.removeBlock(p_226911_2_, false);
			}

			return ActionResultType.SUCCESS;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		spawnParticles(worldIn, pos);
	}

	private static void spawnParticles(World p_180691_0_, BlockPos worldIn) {
		Random random = p_180691_0_.rand;

		for(Direction direction : Direction.values()) {
			BlockPos blockpos = worldIn.offset(direction);
			if (!p_180691_0_.getBlockState(blockpos).isOpaqueCube(p_180691_0_, blockpos)) {
				Direction.Axis direction$axis = direction.getAxis();
				double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getXOffset() : (double)random.nextFloat();
				double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getYOffset() : (double)random.nextFloat();
				double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getZOffset() : (double)random.nextFloat();
				p_180691_0_.addParticle(RedstoneParticleData.REDSTONE_DUST, (double)worldIn.getX() + d1, (double)worldIn.getY() + d2, (double)worldIn.getZ() + d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
