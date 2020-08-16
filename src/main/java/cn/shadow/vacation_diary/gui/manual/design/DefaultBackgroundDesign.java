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

import cn.shadow.vacation_diary.VacationDiary;
import net.minecraft.util.ResourceLocation;

public class DefaultBackgroundDesign implements IBackgroundDesign {

    public static final ResourceLocation BOOK_BACK = new ResourceLocation(VacationDiary.MOD_ID, "textures/gui/book_back.png");
    public static final ResourceLocation EXTRAS = new ResourceLocation(VacationDiary.MOD_ID, "textures/gui/extras.png");

    @Override
    public ResourceLocation getTexture() {
        return BOOK_BACK;
    }

    @Override
    public int getTopPadding() {
        return 10;
    }

    @Override
    public int getBottomPadding() {
        return 14;
    }

    @Override
    public int getLeftPadding() {
        return 16;
    }

    @Override
    public int getRightPadding() {
        return 16;
    }

    @Override
    public IDrawableLocationTextureHovereable getPrevPageTexture() {
        return new DefaultDrawableLocationTexture(-4, 196, EXTRAS, 1, 14, 18, 10, 24, 14);
    }

    @Override
    public IDrawableLocationTextureHovereable getNextPageTexture() {
        return new DefaultDrawableLocationTexture(140, 196, EXTRAS, 1, 1, 18, 10, 24, 1);
    }

    @Override
    public IDrawableLocationTextureHovereable getBackTexture() {
        return new DefaultDrawableLocationTexture(-4, 2, EXTRAS, 1, 27, 18, 10, 24, 27);
    }

    @Override
    public int getTextureX() {
        return 0;
    }

    @Override
    public int getTextureY() {
        return 0;
    }

    @Override
    public int getTextureWidth() {
        return 155;
    }

    @Override
    public int getTextureHeight() {
        return 204;
    }

}
