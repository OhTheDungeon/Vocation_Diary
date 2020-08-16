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

import cn.shadow.vacation_diary.gui.manual.IContent;
import cn.shadow.vacation_diary.util.ManualHelper;
import net.minecraft.client.Minecraft;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class TextContent implements IContent {

    private List<String> splitContent;
    private int textWidth;

    public TextContent(String content, int textWidth) {
    	Minecraft mc = Minecraft.getInstance();
    	this.splitContent = mc.fontRenderer.listFormattedStringToWidth(content, textWidth);
    	this.textWidth = textWidth;
    }

    public TextContent(List<String> strings, int textWidth) {
        this.splitContent = strings;
        this.textWidth = textWidth;
    }

    @Override
    public boolean canBeSplitted() {
        return true;
    }

    @Override
    public Pair<IContent, IContent> split(int y) {
    	Minecraft mc = Minecraft.getInstance();
        int line = y / mc.fontRenderer.FONT_HEIGHT;
        return Pair.of(new TextContent(splitContent.subList(0, line), textWidth), new TextContent(splitContent.subList(line, splitContent.size()), textWidth));
    }

    @Override
    public int getSizeY() {
    	Minecraft mc = Minecraft.getInstance();
        return this.splitContent.size() * mc.fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int getSizeX() {
        return textWidth;
    }

    @Override
    public void renderBack(Minecraft mc, int x, int y, int mouseX, int mouseY) {
        int amount = 0;
        int color = ManualHelper.getCurrentManualInfoFromGUI() != null ? ManualHelper.getCurrentManualInfoFromGUI().getDesign().getTextColor() : 0;
        for (String line : splitContent) {
            mc.fontRenderer.drawString(line, x, y + amount * mc.fontRenderer.FONT_HEIGHT, color);
            ++amount;
        }
    }

    @Override
    public void renderFront(Minecraft mc, int x, int y, int mouseX, int mouseY) {

    }

}
