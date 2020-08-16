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
package cn.shadow.vacation_diary.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.gui.button.CategoryListButton;
import cn.shadow.vacation_diary.gui.manual.IBookCategory;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.gui.manual.design.IBackgroundDesign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class GuiCategoryList extends GuiManualBase {

    public GuiCategoryList(Screen prevScreen, ManualInfo manualInfo) {
        super(prevScreen, manualInfo);
    }

    @Override
    public void init() {
        super.init();
        int spaceX = this.getGuiXSize() - this.getManualInfo().getDesign().getCategoryDesign().getRightPadding() - this.getManualInfo().getDesign().getCategoryDesign().getLeftPadding();
        int spaceY = this.getGuiYSize() - this.getManualInfo().getDesign().getCategoryDesign().getTopPadding() - this.getManualInfo().getDesign().getCategoryDesign().getBottomPadding();
        int currentY = 0;
        int pointer = 0;
        while (currentY < spaceY) {
            int biggerY = 0;
            int currentX = 0;
            List<CategoryListButton> buttons = new ArrayList<>();
            while (currentX < spaceX) {
                if (pointer >= this.getManualInfo().getCategories().size()) break;
                IBookCategory bookCategory = this.getManualInfo().getCategories().get(pointer);
                if (currentX + bookCategory.getDisplay().getSizeX() <= spaceX) {
                    buttons.add(new CategoryListButton(this.getGuiLeft() + this.getManualInfo().getDesign().getCategoryDesign().getRightPadding() + currentX, this.getGuiTop() + this.getManualInfo().getDesign().getCategoryDesign().getTopPadding() + currentY + 16, bookCategory));
                    if (bookCategory.getDisplay().getSizeY() > biggerY) biggerY = bookCategory.getDisplay().getSizeY();
                    ++pointer;
                }
                currentX += bookCategory.getDisplay().getSizeX() + 1;
            }
            currentY += biggerY + 1;
            if (currentY < spaceY) {
                this.buttons.addAll(buttons);
                this.children.addAll(buttons);
            }
        }
    }

    @Override
    public void drawScreenBack(int mouseX, int mouseY, float partialTicks) {
        super.drawScreenBack(mouseX, mouseY, partialTicks);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void drawScreenFront(int mouseX, int mouseY, float partialTicks) {
        for (Widget button : this.buttons) {
            if (button instanceof CategoryListButton) {
                if (button.isHovered()) {
                    GlStateManager.color4f(1, 1, 1, 1);
                    String name = ((CategoryListButton) button).getEntry().getName();
                    Minecraft mc = Minecraft.getInstance();
                    mc.fontRenderer.drawString(new TranslationTextComponent(name).getFormattedText(),
                            this.getManualInfo().getDesign().getCategoryDesign().getLeftPadding()
                                    + this.getGuiLeft() + (this.getGuiXSize() - this.getManualInfo().getDesign().getCategoryDesign().getLeftPadding()
                                    - this.getManualInfo().getDesign().getCategoryDesign().getRightPadding()) / 2 - mc.fontRenderer.getStringWidth(name) / 2,
                            this.getGuiTop() + this.getManualInfo().getDesign().getCategoryDesign().getTopPadding() + 4,
                            this.getManualInfo().getDesign().getTextColor());
                }
            }
        }
        super.drawScreenFront(mouseX, mouseY, partialTicks);
    }

    @Override
    public IBackgroundDesign getBackground() {
        return this.getManualInfo().getDesign().getCategoryDesign();
    }
}
