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
package cn.shadow.vacation_diary.gui.manual;

import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Represents any type of content that later can be formatted into pages
 * <p>
 * Examples: {@link cn.shadow.vacation_diary.gui.manual.content.TextContent} and {@link cn.shadow.vacation_diary.gui.manual.content.RecipeContent}
 */
public interface IContent {

    /**
     * Gets if IContent can bit splitted into 2 different contents
     * @return true if it can be splitted or false if it can't be
     */
    boolean canBeSplitted();

    /**
     * If it can splitted it splits the current content into two new ones based on a length value
     * @param y the length value from where to split the two contents
     * @return a pair of contents where the left value is the top part of the content and the right value is the bottom part of the content
     */
    Pair<IContent, IContent> split(int y);

    /**
     * Gets the length of the content
     * @return how long is the content (in pixels)
     */
    int getSizeY();

    /**
     * Gets the width of the content
     * @return how wide is the content (in pixels)
     */
    int getSizeX();

    /**
     * Renders the back of the content, method run before "renderFront"
     *
     * @param mc     Current minecraft instance
     * @param x      The X position of the content
     * @param y      The Y position of the content
     * @param mouseX The X position of the mouse
     * @param mouseY The Y position of the mouse
     */
    void renderBack(Minecraft mc, int x, int y, int mouseX, int mouseY);

    /**
     * Renders the front of the content, method run after "renderBack"
     * @param mc Current minecraft instance
     * @param x The X position of the content
     * @param y The Y position of the content
     * @param mouseX The X position of the mouse
     * @param mouseY The Y position of the mouse
     */
    void renderFront(Minecraft mc, int x, int y, int mouseX, int mouseY);
}
