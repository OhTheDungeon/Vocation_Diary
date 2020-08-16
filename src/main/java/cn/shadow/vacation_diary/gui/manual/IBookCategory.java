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

import com.google.common.collect.LinkedListMultimap;

import cn.shadow.vacation_diary.gui.manual.design.display.IBookCategoryDisplay;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Represents a Book Category to be shown in the Category GUI
 * <p>
 * Example: {@link cn.shadow.vacation_diary.gui.manual.category.BasicCategory}
 */
public interface IBookCategory {

    /**
     * Gets the name of category
     * @return the name of the category
     */
    String getName();

    /**
     * Gets the tooltip strings to be rendered when hovered
     * @return
     */
    List<String> getTooltip();

    /**
     * Gets the display for the category
     * @return the display of the category
     */
    IBookCategoryDisplay getDisplay();

    /**
     * Gets all the categories entries with a resource location as an unique id
     * @return a map of resource locations an categories entries
     */
    LinkedListMultimap<ResourceLocation, CategoryEntry> getEntries();

    /**
     * Adds an entry to the map with the resource location as an unique id
     * @param location the unique id
     * @param category the category entry to add
     * @return itself
     */
    default IBookCategory addEntry(ResourceLocation location, CategoryEntry category) {
        getEntries().put(location, category);
        return this;
    }

}
