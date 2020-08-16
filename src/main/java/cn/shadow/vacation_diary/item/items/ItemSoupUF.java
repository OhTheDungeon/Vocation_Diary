package cn.shadow.vacation_diary.item.items;

import cn.shadow.vacation_diary.item.ModGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemSoupUF extends ItemUF {
	int foodlevel;
	float saturation;

	public ItemSoupUF(String name, int var2, float var3) {
		super(name, new Properties().group(ModGroup.itemGroup).maxStackSize(1).food(
				(new Food.Builder()).hunger(var2).saturation(var3).build()
			)
		);
		this.foodlevel = var2;
		this.saturation = var3;
	}

	public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entityLiving) {
		if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).isCreative()) {
			itemstack.shrink(1);;
		}

		if (entityLiving instanceof PlayerEntity && !world.isRemote) {
			((PlayerEntity)entityLiving).getFoodStats().addStats(foodlevel, 0);
		}

		return itemstack.isEmpty() ? new ItemStack(Items.BOWL) : itemstack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
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
