package cn.shadow.vacation_diary.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemTea extends ItemBottle {

	int hearts;
	
	public ItemTea(String name, int hearts) {
		super(name, 0, 0.0F, true);
		this.hearts = hearts;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entityLiving) {
		if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).isCreative()) {
			itemstack.shrink(1);;
			if(!itemstack.isEmpty()) {
				((PlayerEntity)entityLiving).inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		if (entityLiving instanceof PlayerEntity && !world.isRemote) {
			((PlayerEntity)entityLiving).heal(hearts);
			if(removepoison) {
				((PlayerEntity)entityLiving).removePotionEffect(Effects.POISON);
			}
		}
		
		return itemstack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : itemstack;
	}
}
