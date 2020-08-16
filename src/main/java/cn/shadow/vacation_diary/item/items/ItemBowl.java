package cn.shadow.vacation_diary.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemBowl extends ItemFoodUF {
	
	boolean removepoison = false;
	
	public ItemBowl(String name, int hunger, float saturation, boolean removepoison) {
		super(name, hunger, saturation, false, 1);
		this.removepoison = removepoison;
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		if(!worldIn.isRemote) {
			if(removepoison) {
				entityLiving.removePotionEffect(Effects.POISON);
			}
		}
		return entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).abilities.isCreativeMode ? stack : new ItemStack(Items.BOWL);
	}
}
