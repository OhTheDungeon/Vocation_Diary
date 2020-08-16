package cn.shadow.vacation_diary.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemBottle extends ItemFoodUF {

	int foodlevel;
	float saturation;
	boolean removepoison = false;
	
	public ItemBottle(String name, int foodlevel, float saturation, boolean removepoison) {
		super(name, 0, 0, false, 16);
		this.foodlevel = foodlevel;
		this.saturation = saturation;
		this.removepoison = removepoison;
	}
	
	public ItemBottle(String name, int foodlevel, float saturation) {
		super(name, 0, 0, false, 16);
		this.foodlevel = foodlevel;
		this.saturation = saturation;
	}
	
	public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entity) {
		
		if (entity instanceof PlayerEntity && !((PlayerEntity)entity).isCreative()) {
			itemstack.shrink(1);;
			if(!itemstack.isEmpty()) ((PlayerEntity)entity).inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}

		if (entity instanceof PlayerEntity && !world.isRemote) {
			((PlayerEntity)entity).getFoodStats().addStats(foodlevel, saturation);
			if(this.removepoison) ((PlayerEntity)entity).removePotionEffect(Effects.POISON);
		}
		
		return itemstack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : itemstack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 16;
    }

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
    public UseAction getUseAction(ItemStack stack) {
    	return UseAction.DRINK;
    }

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    	playerIn.setActiveHand(handIn);
    	return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}
