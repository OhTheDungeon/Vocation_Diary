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

import net.minecraft.util.ResourceLocation;

/**
 * Represents enough information to renderBack a texture inside of a GUI
 */
public interface IDrawableTexture {

    /**
     * The texture used to renderBack
     *
     * @return a resource location that points to the texture
     */
    ResourceLocation getTexture();

    /**
     * Where the texture is located in the Resource Location
     *
     * @return the X position of the texture
     */
    int getTextureX();

    /**
     * Where the texture is located in the Resource Location
     *
     * @return the Y position of the texture
     */
    int getTextureY();

    /**
     * The size of the texture inside of the Resource Location
     *
     * @return how wide the texture is
     */
    int getTextureWidth();

    /**
     * The size of the texture inside of the Resource Location
     *
     * @return how long the texture is
     */
    int getTextureHeight();
}
