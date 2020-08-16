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
package cn.shadow.vacation_diary.gui.manual.category;

import com.google.common.collect.LinkedListMultimap;

import cn.shadow.vacation_diary.gui.manual.CategoryEntry;
import cn.shadow.vacation_diary.gui.manual.IBookCategory;
import cn.shadow.vacation_diary.gui.manual.design.display.IBookCategoryDisplay;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class BasicCategory implements IBookCategory {

    private String name;
    private IBookCategoryDisplay display;
    private LinkedListMultimap<ResourceLocation, CategoryEntry> entryMap;
    private List<String> tooltip;

    public BasicCategory(String name, IBookCategoryDisplay display, String... tooltip) {
        this.name = name;
        this.display = display;
        this.entryMap = LinkedListMultimap.create();
        if (tooltip != null) {
            this.tooltip = Arrays.asList(tooltip);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getTooltip() {
        return tooltip;
    }

    @Override
    public IBookCategoryDisplay getDisplay() {
        return display;
    }

    @Override
    public LinkedListMultimap<ResourceLocation, CategoryEntry> getEntries() {
        return entryMap;
    }
}
