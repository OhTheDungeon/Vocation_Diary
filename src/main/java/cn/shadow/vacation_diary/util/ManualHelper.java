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
package cn.shadow.vacation_diary.util;

import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import javax.annotation.Nullable;

public class ManualHelper {

    @Nullable
    public static ManualInfo getCurrentManualInfoFromGUI() {
    	Minecraft mc = Minecraft.getInstance();
    	Screen screen = mc.currentScreen;
        if (screen != null && screen instanceof GuiManualBase) {
        	GuiManualBase gui = (GuiManualBase) mc.currentScreen;
            return gui.getManualInfo();
        }
        return null;
    }

    @Nullable
    public static ManualInfo getManualFromName(String name) {
        if (ManualInfo.MANUALS.keySet().contains(name)) {
            return ManualInfo.MANUALS.get(name);
        }
        return null;
    }

}
