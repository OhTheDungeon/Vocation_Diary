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
package cn.shadow.vacation_diary.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.gui.button.DrawableButton;
import cn.shadow.vacation_diary.gui.button.IHasTooltip;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.gui.manual.design.IBackgroundDesign;
import cn.shadow.vacation_diary.gui.manual.design.IDrawableLocationTextureHovereable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public abstract class GuiManualBase extends Screen {

    private final ManualInfo manualInfo;
    private Screen prevScreen;
    private int guiLeft;
    private int guiTop;
    private int guiXSize;
    private int guiYSize;

    private Button prevPageButton;
    private Button nextPageButton;
    @SuppressWarnings("unused")
	private Button backButton;

    public GuiManualBase(Screen prevScreen, ManualInfo manualInfo) {
    	super(new StringTextComponent(manualInfo.getModName()));
        this.prevScreen = prevScreen;
        this.manualInfo = manualInfo;
        this.guiXSize = getBackground().getTextureWidth();
        this.guiYSize = getBackground().getTextureHeight();
    }
    
    private static class ManualDrawableButton extends DrawableButton {

    	Screen screen;
		public ManualDrawableButton(int posX, int posY, IDrawableLocationTextureHovereable texture, Screen screen) {
			super(posX, posY, texture, null);
			this.screen = screen;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onPress() {
			if(this.isHovered()) {
				Minecraft.getInstance().displayGuiScreen(screen);
			}
		}
    	
    }

    @Override
    public void init() {
        super.init();
        this.guiLeft = (this.width - this.guiXSize) / 2;
        this.guiTop = (this.height - this.guiYSize) / 2;
        if (prevScreen != null) {
            this.addButton(backButton = new ManualDrawableButton(this.getGuiLeft(), this.getGuiTop(), this.getBackground().getBackTexture(), prevScreen));
        }
        if (this.nextPageButton != null && !this.buttons.contains(nextPageButton)) this.addButton(nextPageButton);
        if (this.prevPageButton != null && !this.buttons.contains(prevPageButton)) this.addButton(prevPageButton);
        this.getManualInfo().setLastManual(this);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawScreenBack(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
        this.drawScreenFront(mouseX, mouseY, partialTicks);
    }
    
    private static class NextDrawableButton extends DrawableButton {

    	GuiManualBase base;
		public NextDrawableButton(int posX, int posY, IDrawableLocationTextureHovereable texture, GuiManualBase base) {
			super(posX, posY, texture, null);
			this.base = base;
		}
    	
		@Override
		public void onPress() {
			if(this.isHovered())
				base.onNextButton();
		}
    }
    
    private static class PrevDrawableButton extends DrawableButton {

    	GuiManualBase base;
		public PrevDrawableButton(int posX, int posY, IDrawableLocationTextureHovereable texture, GuiManualBase base) {
			super(posX, posY, texture, null);
			this.base = base;
		}
    	
		@Override
		public void onPress() {
			if(this.isHovered())
				base.onPrevButton();
		}
    }

    @SuppressWarnings("deprecation")
	public void drawScreenBack(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        Minecraft mc = Minecraft.getInstance();
        drawCenteredString(mc.fontRenderer, new TranslationTextComponent(manualInfo.getDisplayName()).getFormattedText(), this.guiLeft + this.guiXSize / 2, this.guiTop - mc.fontRenderer.FONT_HEIGHT - 4, manualInfo.getDesign().getDisplayColor());
        GlStateManager.color4f(1, 1, 1, 1);
        IBackgroundDesign design = getBackground();
        Minecraft.getInstance().getTextureManager().bindTexture(design.getTexture());
        
        GuiUtils.drawTexturedModalRect(this.getGuiLeft(), this.getGuiTop(), design.getTextureX(), design.getTextureY(), this.getGuiXSize(), this.getGuiYSize(), this.getBlitOffset());

        if (hasNextButton() && nextPageButton == null) {
        	this.addButton(nextPageButton = new NextDrawableButton(this.getGuiLeft(), this.getGuiTop(), this.getBackground().getNextPageTexture(), this));
        }
        if (!hasNextButton() && nextPageButton != null) {
            this.buttons.remove(nextPageButton);
            this.children.remove(nextPageButton);
            nextPageButton = null;
        }
        if (hasPrevButton() && prevPageButton == null) {
        	this.addButton(prevPageButton = new PrevDrawableButton(this.getGuiLeft(), this.getGuiTop(), this.getBackground().getPrevPageTexture(), this));
        }
        if (!hasPrevButton() && prevPageButton != null) {
            this.buttons.remove(prevPageButton);
            this.children.remove(prevPageButton);
            prevPageButton = null;
        }
    }

    public void drawScreenFront(int mouseX, int mouseY, float partialTicks) {
        for (Widget button : this.buttons) {
            if (button instanceof IHasTooltip && button.isHovered() && ((IHasTooltip) button).getTooltip() != null && !((IHasTooltip) button).getTooltip().isEmpty()) {
            	GuiUtils.drawHoveringText(((IHasTooltip) button).getTooltip(), mouseX, mouseY, guiXSize, guiYSize, guiXSize - 3, this.font);
            }
        }
    }

    public ManualInfo getManualInfo() {
        return manualInfo;
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    public int getGuiXSize() {
        return guiXSize;
    }

    public int getGuiYSize() {
        return guiYSize;
    }

    public boolean hasNextButton() {
        return false;
    }

    public boolean hasPrevButton() {
        return false;
    }

    public void onNextButton() {

    }

    public void onPrevButton() {

    }

    public abstract IBackgroundDesign getBackground();


    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    public void addButtonToGUI(Button button) {
    	this.buttons.add(button);
    	this.children.add(button);
    }

    public void removeButtonToGUI(Button button) {
    	this.buttons.remove(button);
    	this.children.remove(button);
    }
}
