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
package cn.shadow.vacation_diary.gui.manual.category.display;

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.gui.manual.design.display.IBookCategoryDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ItemStackCategoryDisplay implements IBookCategoryDisplay {

    private final ItemStack display;
    private double scale;

    public ItemStackCategoryDisplay(ItemStack display) {
        this(display, 1);
    }

    public ItemStackCategoryDisplay(ItemStack display, double scale) {
        this.display = display;
        this.scale = scale;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void render(Minecraft mc, int x, int y, boolean isHovered) {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scaled(scale, scale, scale);
        mc.getItemRenderer().renderItemIntoGUI(display, (int) (x / scale), (int) (y / scale));
        GlStateManager.popMatrix();
    }

    @Override
    public int getSizeX() {
        return (int) (16 * scale);
    }

    @Override
    public int getSizeY() {
        return (int) (16 * scale);
    }
}
