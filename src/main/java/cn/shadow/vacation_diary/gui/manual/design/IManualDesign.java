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
package cn.shadow.vacation_diary.gui.manual.design;

/**
 * Stores all the design elements of a manual
 * <p>
 * Example {@link cn.shadow.vacation_diary.gui.manual.design.DefaultManualDesign}
 */
public interface IManualDesign {

    /**
     * @return the design for the category GUI
     */
    IBackgroundDesign getCategoryDesign();

    /**
     *
     * @return the design for the category entry GUI
     */
    IBackgroundDesign getCategoryEntryDesign();

    /**
     *
     * @return the design for the pages GUI
     */
    IBackgroundDesign getPageDesign();

    /**
     *
     * @return the text color used for to display the name of the manual
     */
    int getDisplayColor();

    /**
     *
     * @return the text color used to display text in the manual
     */
    int getTextColor();

}
