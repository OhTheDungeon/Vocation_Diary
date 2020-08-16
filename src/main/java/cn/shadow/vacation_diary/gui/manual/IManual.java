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

import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.item.items.ItemManual;

/**
 * Interface used with the annotation {@link com.buuz135.project42.api.annotation.ProjectManual} to register a manual
 *
 * {@link #registerCategories(ManualInfo)} gets executed on Minecraft Resource Reload
 *
 * {@link #onManualItemCreation(ManualInfo, ItemManual)} gets executed on {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}
 */
public interface IManual {

    /**
     * Registers the categories and the contents of the manual
     *
     * @param info All the manual information where the categories are added
     */
    void registerCategories(ManualInfo info);

    /**
     * Gives you the manual item so you can modify the item model, the resource location, etc.
     *
     * @param info   All the manual information
     * @param manual The manual item to be modified
     */
    void onManualItemCreation(ManualInfo info, ItemManual manual);
}
