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
package cn.shadow.vacation_diary.gui.button;

import com.mojang.blaze3d.systems.RenderSystem;

import cn.shadow.vacation_diary.gui.manual.design.IDrawableLocationTextureHovereable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class DrawableButton extends Button {

    private final IDrawableLocationTextureHovereable texture;

    public DrawableButton(int posX, int posY, IDrawableLocationTextureHovereable texture, IPressable onPress) {
        super(texture.getPosX() + posX, texture.getPosY() + posY, texture.getTextureWidth(), texture.getTextureHeight(), "", onPress);
        this.texture = texture;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            Minecraft.getInstance().getTextureManager().bindTexture(texture.getTexture());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiUtils.drawTexturedModalRect(x, y, this.isHovered ? texture.getHoveredX() : texture.getTextureX(), this.isHovered ? texture.getHoveredY() : texture.getTextureY(), texture.getTextureWidth(), texture.getTextureHeight(), this.getBlitOffset());
        }
    }
}
