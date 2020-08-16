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
 * Represent the information to renderBack a manual background
 * <p>
 * Example: {@link cn.shadow.vacation_diary.gui.manual.design.DefaultBackgroundDesign}
 */
public interface IBackgroundDesign extends IDrawableTexture {

    /**
     * How much padding on the top the texture has
     *
     * @return the amount of pixels of top padding
     */
    int getTopPadding();

    /**
     * How much padding on the bottom the texture has
     *
     * @return the amount of pixels of bottom padding
     */
    int getBottomPadding();

    /**
     * How much padding on the left the texture has
     *
     * @return the amount of pixels of left padding
     */
    int getLeftPadding();

    /**
     * How much padding on the right the texture has
     *
     * @return the amount of pixels of right padding
     */
    int getRightPadding();

    /**
     * The texture used for the previous page button
     *
     * @return a drawable for the previous page button
     */
    IDrawableLocationTextureHovereable getPrevPageTexture();

    /**
     * The texture used for the next page button
     *
     * @return a drawable for the next page button
     */
    IDrawableLocationTextureHovereable getNextPageTexture();

    /**
     * The texture used for the back button
     *
     * @return a drawable for the back button
     */
    IDrawableLocationTextureHovereable getBackTexture();

}
