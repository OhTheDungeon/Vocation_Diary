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

public class DefaultDrawableLocationTexture implements IDrawableLocationTextureHovereable {

    private int posX;
    private int posY;
    private ResourceLocation location;
    private int textureX;
    private int textureY;
    private int width;
    private int height;
    private int hoveredX;
    private int hoveredY;

    public DefaultDrawableLocationTexture(int posX, int posY, ResourceLocation location, int textureX, int textureY, int width, int height, int hoveredX, int hoveredY) {
        this.posX = posX;
        this.posY = posY;
        this.location = location;
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
        this.hoveredX = hoveredX;
        this.hoveredY = hoveredY;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public ResourceLocation getTexture() {
        return location;
    }

    @Override
    public int getTextureX() {
        return textureX;
    }

    @Override
    public int getTextureY() {
        return textureY;
    }

    @Override
    public int getTextureWidth() {
        return width;
    }

    @Override
    public int getTextureHeight() {
        return height;
    }

    @Override
    public int getHoveredX() {
        return hoveredX;
    }

    @Override
    public int getHoveredY() {
        return hoveredY;
    }
}
