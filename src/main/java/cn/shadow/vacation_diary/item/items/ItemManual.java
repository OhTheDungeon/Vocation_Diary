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
package cn.shadow.vacation_diary.item.items;

import java.util.Map;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.item.ModGroup;
import cn.shadow.vacation_diary.util.ManualHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemManual extends Item {

    public final static ResourceLocation MODEL_LOCATION = new ResourceLocation(VacationDiary.MOD_ID, "manual");

    private final String name;
    private ResourceLocation modelLocation;

    public ItemManual(String name) {
    	super(new Properties().group(ModGroup.itemGroup).maxStackSize(1));
        this.name = name;
        this.modelLocation = MODEL_LOCATION;
//        setRegistryName(HuaxiaWorld.MOD_ID, name);
    }
    
    private static boolean isInit = false;

    public void initManual() {
    	for(Map.Entry<String, ManualInfo> entry : ManualInfo.MANUALS.entrySet()) {
    		ManualInfo info = entry.getValue();
	        info.getCategories().clear();
	        info.setLastManual(null);
	        info.getManualObject().registerCategories(info);
	        int dimensionX = info.getDesign().getPageDesign().getTextureWidth() - info.getDesign().getPageDesign().getLeftPadding() - info.getDesign().getPageDesign().getRightPadding();
	        int dimensionY = info.getDesign().getPageDesign().getTextureHeight() - info.getDesign().getPageDesign().getTopPadding() - info.getDesign().getPageDesign().getBottomPadding();
	        info.getCategories().forEach(category -> category.getEntries().forEach((location, categoryEntry) -> categoryEntry.generatePages(dimensionX, dimensionY)));
	    }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
        	if(!isInit) {
        		isInit = true;
        		initManual();
        	}
            ManualInfo info = ManualHelper.getManualFromName(name);
            if (info != null) {
                info.openGui();
            }
        }
        return ActionResult.resultFail(itemstack);
    }
    
    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        ManualInfo info = ManualHelper.getManualFromName(name);
        if (info != null) {
            return new TranslationTextComponent(info.getDisplayName());
        }
        return super.getDisplayName(stack);
    }

    @Override
    public ITextComponent getName() {
        return new StringTextComponent(name);
    }

    public ResourceLocation getModelLocation() {
        return modelLocation;
    }

    public void setModelLocation(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }
}
