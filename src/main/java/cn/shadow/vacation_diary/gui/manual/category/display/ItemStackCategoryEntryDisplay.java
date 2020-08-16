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

import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.gui.manual.design.display.ICategoryEntryDisplay;
import cn.shadow.vacation_diary.util.ManualHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class ItemStackCategoryEntryDisplay implements ICategoryEntryDisplay {

    private final ItemStack itemStack;
    private int sizeX;
    private int sizeY;
    private String textDisplay;

    public ItemStackCategoryEntryDisplay(ItemStack itemStack) {
        this(itemStack, "");
    }

    public ItemStackCategoryEntryDisplay(ItemStack itemStack, int sizeX, int sizeY) {
        this.itemStack = itemStack;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public ItemStackCategoryEntryDisplay(ItemStack itemStack, String textDisplay) {
        this.itemStack = itemStack;
        this.sizeX = 123;
        this.sizeY = 17;
        this.textDisplay = textDisplay;
    }

    public ItemStackCategoryEntryDisplay(ItemStack itemStack, int sizeX, int sizeY, String textDisplay) {
        this.itemStack = itemStack;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.textDisplay = textDisplay;
    }

    @Override
    public void render(Minecraft mc, int x, int y, boolean isHovered) {
        RenderHelper.enableStandardItemLighting();
        mc.getItemRenderer().renderItemIntoGUI(itemStack, x + 2, y + 2);
        ManualInfo info = ManualHelper.getCurrentManualInfoFromGUI();
        Color color = info == null ? Color.CYAN.darker() : new Color(info.getDesign().getTextColor());
        mc.fontRenderer.drawString(getTextDisplay(isHovered), x + 22, y + 7, isHovered ? color.darker().getRGB() : color.getRGB());
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    public String getTextDisplay(boolean isHovered) {
        return textDisplay.isEmpty() ? itemStack.getDisplayName().getFormattedText() : new TranslationTextComponent(textDisplay).getFormattedText();
    }
}
