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

import cn.shadow.vacation_diary.gui.button.CategoryEntryButton;
import cn.shadow.vacation_diary.gui.manual.CategoryEntry;
import cn.shadow.vacation_diary.gui.manual.IBookCategory;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.gui.manual.design.IBackgroundDesign;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;

import java.util.ArrayList;
import java.util.List;

public class GuiCategoryEntryList extends GuiManualBase {

    private final IBookCategory category;
    private List<Button> addedButtons;
    private int pointer = 0;
    private List<Integer> pageAmount;

    public GuiCategoryEntryList(Screen prevScreen, ManualInfo manualInfo, IBookCategory category) {
        super(prevScreen, manualInfo);
        this.category = category;
        this.addedButtons = new ArrayList<>();
        this.pageAmount = new ArrayList<>();
    }

    @Override
    public void drawScreenBack(int mouseX, int mouseY, float partialTicks) {
        super.drawScreenBack(mouseX, mouseY, partialTicks);
    }

    @Override
    public IBackgroundDesign getBackground() {
        return this.getManualInfo().getDesign().getCategoryEntryDesign();
    }

    @Override
    public void init() {
        super.init();
        rebuild();
    }

    private void rebuild() {
        this.buttons.removeIf(guiButton -> addedButtons.contains(guiButton));
        this.children.removeIf(guiButton -> addedButtons.contains(guiButton));
        this.addedButtons.clear();
        int spaceX = this.getGuiXSize() - this.getManualInfo().getDesign().getCategoryEntryDesign().getRightPadding() - this.getManualInfo().getDesign().getCategoryEntryDesign().getLeftPadding();
        int spaceY = this.getGuiYSize() - this.getManualInfo().getDesign().getCategoryEntryDesign().getTopPadding() - this.getManualInfo().getDesign().getCategoryEntryDesign().getBottomPadding();
        int currentY = 0;
        int pointer = this.pointer;
        
        while (currentY < spaceY) {
            int biggerY = 0;
            int currentX = 0;
            List<CategoryEntryButton> buttons = new ArrayList<>();
            while (currentX < spaceX) {
                if (pointer >= category.getEntries().values().size()) {
                    currentY = spaceY;
                    break;
                }
                CategoryEntry entry = category.getEntries().values().get(pointer);
                if (currentX + entry.getDisplay().getSizeX() <= spaceX) {
                	CategoryEntryButton b = new CategoryEntryButton(
                			this.getGuiLeft() + this.getManualInfo().getDesign().getCategoryEntryDesign().getRightPadding() + currentX, 
                			this.getGuiTop() + this.getManualInfo().getDesign().getCategoryEntryDesign().getTopPadding() + currentY, 
                			entry);
                    buttons.add(b);
                    if (entry.getDisplay().getSizeY() > biggerY) biggerY = entry.getDisplay().getSizeY();
                    ++pointer;
                }
                currentX += entry.getDisplay().getSizeX();
            }
            currentY += biggerY;
            if (currentY < spaceY || pointer >= category.getEntries().values().size()) {
                this.addedButtons.addAll(buttons);
                this.buttons.addAll(buttons);
                this.children.addAll(buttons);
            }
        }
    }

    @Override
    public boolean hasNextButton() {
        return this.pointer + addedButtons.size() < this.category.getEntries().size() - 1;
    }

    @Override
    public boolean hasPrevButton() {
        return pageAmount.size() > 0;
    }

    @Override
    public void onNextButton() {
        this.pointer += addedButtons.size();
        pageAmount.add(addedButtons.size());
        rebuild();
    }

    @Override
    public void onPrevButton() {
        this.pointer -= (pageAmount.get(pageAmount.size() - 1));
        pageAmount.remove(pageAmount.size() - 1);
        rebuild();
    }
}
