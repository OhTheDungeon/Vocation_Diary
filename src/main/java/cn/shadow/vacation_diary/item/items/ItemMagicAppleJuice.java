package cn.shadow.vacation_diary.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemMagicAppleJuice extends ItemJuice {

	
	public ItemMagicAppleJuice(String name, int var2, float var3) {
		super(name, var2, var3);
	}

	public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entity) {
		if (entity instanceof PlayerEntity && !((PlayerEntity)entity).isCreative()) {
			itemstack.shrink(1);;
			if(!itemstack.isEmpty()) {
				((PlayerEntity)entity).inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		if (entity instanceof PlayerEntity && !world.isRemote) {
			((PlayerEntity)entity).getFoodStats().addStats(foodlevel, saturation);
			((PlayerEntity)entity).addPotionEffect(new EffectInstance(Effects.REGENERATION, 600, 3));
			((PlayerEntity)entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, 6000, 0));
			((PlayerEntity)entity).addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0));
			((PlayerEntity)entity).addPotionEffect(new EffectInstance(Effects.HASTE, 400, 0));
			((PlayerEntity)entity).addPotionEffect(new EffectInstance(Effects.SPEED, 600, 0));
		}

		return itemstack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : itemstack;
	}

	public Rarity getRarity(ItemStack stack) {
		return Rarity.EPIC;
	}

	public boolean hasEffect(ItemStack var1) {
		return true;
	}
}
