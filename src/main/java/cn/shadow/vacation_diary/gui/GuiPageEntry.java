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

import cn.shadow.vacation_diary.gui.manual.CategoryEntry;
import cn.shadow.vacation_diary.gui.manual.IAdvancedContent;
import cn.shadow.vacation_diary.gui.manual.IClickable;
import cn.shadow.vacation_diary.gui.manual.Page;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.gui.manual.design.IBackgroundDesign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiPageEntry extends GuiManualBase {

    private final CategoryEntry entry;
    private int page;

    public GuiPageEntry(Screen prevScreen, ManualInfo manualInfo, CategoryEntry entry) {
        super(prevScreen, manualInfo);
        this.entry = entry;
        this.page = 0;
    }

    @Override
    public void init() {
        super.init();
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IAdvancedContent)
                    ((IAdvancedContent) formattedContent.getContent()).onAdded(Minecraft.getInstance(), this, formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1);
            }
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public void drawScreenBack(int mouseX, int mouseY, float partialTicks) {
        super.drawScreenBack(mouseX, mouseY, partialTicks);
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                formattedContent.getContent().renderBack(Minecraft.getInstance(), formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1, mouseX, mouseY);
            }
        }
        GlStateManager.color4f(1, 1, 1, 1);
    }

    @Override
    public void drawScreenFront(int mouseX, int mouseY, float partialTicks) {
        super.drawScreenFront(mouseX, mouseY, partialTicks);
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                formattedContent.getContent().renderFront(Minecraft.getInstance(), formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1, mouseX, mouseY);
            }
        }
    }

    @Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IClickable && isInside(formattedContent, mouseX, mouseY)) {
                    ((IClickable) formattedContent.getContent()).onClick(Minecraft.getInstance(), mouseX, mouseY, mouseButton);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onClose() {
        super.onClose();
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IAdvancedContent)
                    ((IAdvancedContent) formattedContent.getContent()).onRemoved(Minecraft.getInstance(), this, formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1);
            }
        }
    }

    private boolean isInside(Page.FormattedContent formattedContent, double mouseX, double mouseY) {
        return mouseX > formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding() && mouseX < formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding() + formattedContent.getContent().getSizeX() &&
                mouseY > formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() && mouseY < formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + formattedContent.getContent().getSizeY();
    }

    @Override
    public IBackgroundDesign getBackground() {
        return this.getManualInfo().getDesign().getPageDesign();
    }

    @Override
    public boolean hasNextButton() {
        return page < entry.getPages().size() - 1;
    }

    @Override
    public boolean hasPrevButton() {
        return page > 0;
    }

    @Override
    public void onNextButton() {
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IAdvancedContent)
                    ((IAdvancedContent) formattedContent.getContent()).onRemoved(Minecraft.getInstance(), this, formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1);
            }
        }
        ++page;
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IAdvancedContent)
                    ((IAdvancedContent) formattedContent.getContent()).onAdded(Minecraft.getInstance(), this, formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1);
            }
        }
    }

    @Override
    public void onPrevButton() {
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IAdvancedContent)
                    ((IAdvancedContent) formattedContent.getContent()).onRemoved(Minecraft.getInstance(), this, formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1);
            }
        }
        --page;
        if (entry.getPages().size() > page) {
            for (Page.FormattedContent formattedContent : entry.getPages().get(page).getFormattedContent()) {
                if (formattedContent.getContent() instanceof IAdvancedContent)
                    ((IAdvancedContent) formattedContent.getContent()).onAdded(Minecraft.getInstance(), this, formattedContent.getX() + this.getGuiLeft() + this.getBackground().getLeftPadding(), formattedContent.getY() + this.getGuiTop() + this.getBackground().getTopPadding() + 1);
            }
        }
    }
}
