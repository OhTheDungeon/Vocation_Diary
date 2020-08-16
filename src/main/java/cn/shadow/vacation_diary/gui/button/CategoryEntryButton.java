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

import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.GuiPageEntry;
import cn.shadow.vacation_diary.gui.manual.CategoryEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class CategoryEntryButton extends Button {

    private final CategoryEntry entry;

    public CategoryEntryButton(int x, int y, CategoryEntry entry) {
        super(x, y, entry.getDisplay().getSizeX(), entry.getDisplay().getSizeY(), "", null);
        this.entry = entry;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            this.isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
            entry.getDisplay().render(Minecraft.getInstance(), x, y, this.isHovered);
        }
    }

    @Override
    public void onPress() {
        if (this.isHovered() && !entry.getPages().isEmpty()) {
        	Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new GuiPageEntry(mc.currentScreen, mc.currentScreen instanceof GuiManualBase ? ((GuiManualBase) mc.currentScreen).getManualInfo() : null, entry));
        }
    }
}
