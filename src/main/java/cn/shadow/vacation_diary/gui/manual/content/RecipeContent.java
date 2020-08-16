/*
 * This file is part of Project 42.
 *
 * Copyright 2018, Buuz135
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.shadow.vacation_diary.gui.manual.content;

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.button.DrawableButton;
import cn.shadow.vacation_diary.gui.manual.IAdvancedContent;
import cn.shadow.vacation_diary.gui.manual.IContent;
import cn.shadow.vacation_diary.gui.manual.design.DefaultBackgroundDesign;
import cn.shadow.vacation_diary.gui.manual.design.DefaultDrawableLocationTexture;
import cn.shadow.vacation_diary.gui.manual.design.IDrawableLocationTextureHovereable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.gui.GuiUtils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class RecipeContent implements IContent, IAdvancedContent {

    private List<IRecipe<?>> recipe;
    private int pointer;
    private DrawableButton leftArrow;
    private DrawableButton rightArrow;
    private ItemStack recipeOutput;

    @Override
    public int getSizeY() {
        return 62;
    }

    @Override
    public int getSizeX() {
        return 124;
    }

    public RecipeContent(IRecipe<?> recipe) {
        this.recipe = new ArrayList<>();
        this.recipe.add(recipe);
        this.pointer = 0;
    }

    public RecipeContent(ItemStack recipeOutput) {
    	this.recipeOutput = recipeOutput;
        this.recipe = null;//getRecipesByOutput(recipeOutput);
        this.pointer = 0;
    }

    @Override
    public boolean canBeSplitted() {
        return false;
    }

    @Override
    public Pair<IContent, IContent> split(int y) {
        return null;
    }
    
    private static List<IRecipe<?>> getRecipesByOutput(@Nonnull ItemStack targetOutput)
    {
    	Minecraft mc = Minecraft.getInstance();
    	List<IRecipe<?>> res = mc
        		.world
        		.getRecipeManager()
        		.getRecipes()
        		.stream()
                .filter(r -> !r.isDynamic()
                                && ItemStack.areItemsEqualIgnoreDurability(targetOutput, r.getRecipeOutput())
                ).collect(Collectors.toList());
    	List<IRecipe<?>> res2 = new ArrayList<>();
    	for(IRecipe<?> recipe : res) {
    		if(recipe.getType() == IRecipeType.CRAFTING) {
    			res2.add(recipe);
    		}
    	}
    	return res2;
    }


    @SuppressWarnings("deprecation")
	@Override
    public void renderBack(Minecraft mc, int x, int y, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(DefaultBackgroundDesign.EXTRAS);
        GuiUtils.drawTexturedModalRect(x, y, 45, 1, 124, 62, mc.currentScreen.getBlitOffset());
        if(this.recipe == null) {
        	this.recipe = getRecipesByOutput(this.recipeOutput);
        }
        if(this.recipe.isEmpty()) return;
        IRecipe<?> recipe = this.recipe.get(pointer);
        int pos = 0;
        int recipeSize = getSize();
        RenderHelper.enableStandardItemLighting();
        NonNullList<Ingredient> list = recipe.getIngredients();
        for (Ingredient ingredient : list) {
            if (ingredient != null && ingredient.getMatchingStacks().length > 0) {
                int ing = (int) (mc.world.getGameTime() / 30 % ingredient.getMatchingStacks().length);
                int posX = x + 5 + (pos % recipeSize) * 18;
                int posY = y + 5 + (pos / recipeSize) * 18;
                ItemStack stack = ingredient.getMatchingStacks()[ing];
                mc.getItemRenderer().renderItemIntoGUI(stack, posX, posY);
            }
            ++pos;
        }
        mc.getItemRenderer().renderItemAndEffectIntoGUI(recipe.getRecipeOutput(), x + 99, y + 23);
        mc.getItemRenderer().renderItemOverlays(mc.fontRenderer, recipe.getRecipeOutput(), x + 99, y + 23);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public void renderFront(Minecraft mc, int x, int y, int mouseX, int mouseY) {
        if(this.recipe == null) {
        	this.recipe = getRecipesByOutput(this.recipeOutput);
        }
        if(this.recipe.isEmpty()) return;
        IRecipe<?> recipe = this.recipe.get(pointer);
        int pos = 0;
        int recipeSize = getSize();
        NonNullList<Ingredient> list = recipe.getIngredients();
        for (Ingredient ingredient : list) {
            if (ingredient != null && ingredient.getMatchingStacks().length > 0) {
                int ing = (int) (mc.world.getGameTime() / 30 % ingredient.getMatchingStacks().length);
                int posX = x + 5 + (pos % recipeSize) * 18;
                int posY = y + 5 + (pos / recipeSize) * 18;
                ItemStack stack = ingredient.getMatchingStacks()[ing];
                if (mouseX > posX && mouseX < posX + 18 && mouseY > posY && mouseY < posY + 18) {
                	GuiUtils.drawHoveringText(getItemToolTip(stack), mouseX, mouseY,
                			mc.currentScreen.width, mc.currentScreen.height, mc.currentScreen.width - 2,
                			mc.fontRenderer);
                }
            }
            ++pos;
        }
        if (mouseX > x + 99 && mouseX < x + 99 + 18 && mouseY > y + 23 && mouseY < y + 23 + 18) {
        	GuiUtils.drawHoveringText(getItemToolTip(recipe.getRecipeOutput()), mouseX, mouseY,
        			mc.currentScreen.width, mc.currentScreen.height, mc.currentScreen.width - 2,
        			mc.fontRenderer);
        }
    }
    
    public static List<String> getItemToolTip(ItemStack p_191927_1_)
    {
    	Minecraft mc = Minecraft.getInstance();
        List<ITextComponent> list = p_191927_1_.getTooltip(mc.player, 
        		mc.gameSettings.advancedItemTooltips ? 
        				ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

        List<String> res = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i)
        {
            if (i == 0)
            {
            	String text = p_191927_1_.getItem().getRarity(p_191927_1_).color + (String)list.get(i).getFormattedText();
            	if(text != null) res.add(text);
            }
            else
            {
            	String text = list.get(i).getFormattedText();
            	if(text != null) res.add(TextFormatting.GRAY + text);
            }
        }

        return res;
    }



    public int getSize() {
        if(this.recipe == null) {
        	this.recipe = getRecipesByOutput(this.recipeOutput);
        }
        return recipe.get(pointer).canFit(2, 2) ? 2 : 3;
    }
    
    private static class LeftArrow extends DrawableButton {

    	RecipeContent content;
    	GuiManualBase base;
    	Minecraft mc;
    	int contentX, contentY;
		public LeftArrow(int posX, int posY, IDrawableLocationTextureHovereable texture, GuiManualBase base, RecipeContent content, Minecraft mc,
				int contentX, int contentY) {
			super(posX, posY, texture, null);
			this.content = content;
			this.base = base;
			this.mc = mc;
			this.contentX = contentX;
			this.contentY = contentY;
		}
		
    	@Override
    	public void onPress() {
    		if(this.isHovered()) {
    			content.pointer--;
    			content.onRemoved(mc, base, contentX, contentY);
    			content.onAdded(mc, base, contentX, contentY);
    		}
    	}
    }
    
    private static class RightArrow extends DrawableButton {

    	RecipeContent content;
    	GuiManualBase base;
    	Minecraft mc;
    	int contentX, contentY;
		public RightArrow(int posX, int posY, IDrawableLocationTextureHovereable texture, GuiManualBase base, RecipeContent content, Minecraft mc,
				int contentX, int contentY) {
			super(posX, posY, texture, null);
			this.content = content;
			this.base = base;
			this.mc = mc;
			this.contentX = contentX;
			this.contentY = contentY;
		}
		
    	@Override
    	public void onPress() {
    		if(this.isHovered()) {
    			content.pointer++;
    			content.onRemoved(mc, base, contentX, contentY);
    			content.onAdded(mc, base, contentX, contentY);
    		}
    	}
    }

    @Override
    public void onAdded(Minecraft mc, GuiManualBase base, int contentX, int contentY) {
        int x = 94;
        int y = 46;
        if (pointer > 0) {
        	leftArrow = new LeftArrow(contentX + x, contentY + y,
        			new DefaultDrawableLocationTexture(0, 0, DefaultBackgroundDesign.EXTRAS, 170, 1, 12, 10, 183, 1),
        			base, this, mc, contentX, contentY);
            base.addButtonToGUI(leftArrow);
        }
        if(this.recipe == null) {
        	this.recipe = getRecipesByOutput(this.recipeOutput);
        }
        if (pointer < recipe.size() - 1) {
        	rightArrow = new RightArrow(contentX + 14 + x, contentY + y,
        			new DefaultDrawableLocationTexture(0, 0, DefaultBackgroundDesign.EXTRAS, 170, 12, 12, 10, 183, 12),
        			base, this, mc, contentX, contentY);
            base.addButtonToGUI(rightArrow);
        }
    }

    @Override
    public void onRemoved(Minecraft mc, GuiManualBase base, int contentX, int contentY) {
        if (leftArrow != null) {
            base.removeButtonToGUI(leftArrow);
            leftArrow = null;
        }
        if (rightArrow != null) {
            base.removeButtonToGUI(rightArrow);
            rightArrow = null;
        }
    }
}
