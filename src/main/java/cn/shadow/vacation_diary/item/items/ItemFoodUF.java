package cn.shadow.vacation_diary.item.items;

import java.util.ArrayList;
import java.util.List;

import cn.shadow.vacation_diary.item.ModGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class ItemFoodUF extends Item {
	
	String name;
	
	public ItemFoodUF(String name, int hunger, float saturation, boolean meat) {
		this(name, hunger, saturation, meat, 64);
	}
	public ItemFoodUF(String name, int hunger, float saturation, boolean meat, int maxStackSize) {
		super(new Properties().group(ModGroup.itemGroup).maxStackSize(maxStackSize)
				.food(
						meat ?
							(new Food.Builder()).hunger(hunger).saturation(saturation).meat().build() :
							(new Food.Builder()).hunger(hunger).saturation(saturation).build()
				)
		);

		this.name = name;
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
	
	public ItemFoodUF setPotionEffect(Effect effectIn, int effectDuration, int amp) {
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
		
		return itemstack;
	}
}
