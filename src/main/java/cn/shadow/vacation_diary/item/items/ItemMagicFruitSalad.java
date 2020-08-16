package cn.shadow.vacation_diary.item.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class ItemMagicFruitSalad extends ItemBowl {

	public ItemMagicFruitSalad(String name, int var2, float var3, boolean var4) {
		super(name, var2, var3, var4);
	}
	
	public boolean hasEffect(ItemStack var1) {
		return true;
	}

	public Rarity getRarity(ItemStack stack) {
		return Rarity.EPIC;
	}
	
	private static class EffectNode {
		public Effect effect;
		public int dur;
		public int amp;
		
		public EffectNode(Effect effect, int dur, int amp) {
			this.effect = effect;
			this.dur = dur;
			this.amp = amp;
		}
	}
	
	private List<EffectNode> effects = new ArrayList<>();
	
	public ItemMagicFruitSalad setPotionEffect(Effect effectIn, int effectDuration, int amp) {
		effects.add(new EffectNode(effectIn, effectDuration * 20, amp));
		return this;
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
	 * the Item before the action is complete.
	 */
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
		
		for(EffectNode node : effects) {
			entityLiving.addPotionEffect(new EffectInstance(node.effect, node.dur, node.amp));
		}

		return entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).abilities.isCreativeMode ? itemstack : new ItemStack(Items.BOWL);
	}

}
