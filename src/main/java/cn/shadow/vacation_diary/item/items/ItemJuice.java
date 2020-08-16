package cn.shadow.vacation_diary.item.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class ItemJuice extends ItemBottle {

	public ItemJuice(String name, int var2, float var3) {
		super(name, var2, var3);
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
		if (playerentity instanceof ServerPlayerEntity) {
	    	  CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
		}

		if (playerentity != null) {
			playerentity.addStat(Stats.ITEM_USED.get(this));
			if (!playerentity.abilities.isCreativeMode) {
				stack.shrink(1);
			}
		}
		
		if (playerentity == null || !playerentity.abilities.isCreativeMode) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (playerentity != null) {
				playerentity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
	      
		if (!worldIn.isRemote) {
			entityLiving.addPotionEffect(new EffectInstance(Effects.SPEED, 30*20, 0));
		}

		return stack;
	}
}
