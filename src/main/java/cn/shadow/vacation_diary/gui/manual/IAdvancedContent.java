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

import cn.shadow.vacation_diary.gui.GuiManualBase;
import net.minecraft.client.Minecraft;

/**
 * Makes the content advanced so it can add extra stuff like more buttons to the content when created
 */
public interface IAdvancedContent {

    /**
     * Called when content is added to a GUI
     *
     * @param mc   The current Minecraft instance
     * @param base The current GUI
     * @param contentX The X position of the content in the GUI
     * @param contentY The Y position of the content in the GUI
     */
    void onAdded(Minecraft mc, GuiManualBase base, int contentX, int contentY);

    /**
     * Called when content is removed in a GUI
     *
     * @param mc   The current Minecraft instance
     * @param base The current GUI
     * @param contentX The X position of the content in the GUI
     * @param contentY The Y position of the content in the GUI
     */
    void onRemoved(Minecraft mc, GuiManualBase base, int contentX, int contentY);
}
