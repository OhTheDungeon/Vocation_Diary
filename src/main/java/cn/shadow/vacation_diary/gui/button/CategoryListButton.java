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

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.gui.GuiCategoryEntryList;
import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.manual.IBookCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryListButton extends Button implements IHasTooltip {

    private final IBookCategory entry;

    public CategoryListButton(int x, int y, IBookCategory entry) {
        super(x, y, entry.getDisplay().getSizeX(), entry.getDisplay().getSizeY(), "", null);
        this.entry = entry;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (visible) {
        	Minecraft mc = Minecraft.getInstance();
            this.isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            entry.getDisplay().render(mc, this.x, this.y, this.isHovered);
            GlStateManager.popMatrix();
        }
    }

    @Nullable
    @Override
    public List<String> getTooltip() {
        List<String> tooltips = new ArrayList<>();
        if (entry.getTooltip() != null)
        	tooltips.addAll(entry.getTooltip().stream().map(s -> new TranslationTextComponent(s).getFormattedText()).collect(Collectors.toList()));
        return tooltips;
    }

    public IBookCategory getEntry() {
        return entry;
    }
    
    @Override
    public void onPress() {
        if (this.isHovered() && !entry.getEntries().isEmpty()) {
        	Minecraft mc = Minecraft.getInstance();
        	mc.displayGuiScreen(new GuiCategoryEntryList(mc.currentScreen, mc.currentScreen instanceof GuiManualBase ? ((GuiManualBase) Minecraft.getInstance().currentScreen).getManualInfo() : null, entry));
        }
    }
}
