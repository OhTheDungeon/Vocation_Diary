package cn.shadow.vacation_diary.gui.manual.content;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.button.DrawableButton;
import cn.shadow.vacation_diary.gui.manual.IAdvancedContent;
import cn.shadow.vacation_diary.gui.manual.IContent;
import cn.shadow.vacation_diary.gui.manual.design.DefaultBackgroundDesign;
import cn.shadow.vacation_diary.gui.manual.design.DefaultDrawableLocationTexture;
import cn.shadow.vacation_diary.gui.manual.design.IDrawableLocationTextureHovereable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class FurnaceContent implements IContent, IAdvancedContent {

    private DrawableButton leftArrow;
    private DrawableButton rightArrow;
    private int pointer;
    private List<ItemStack> inputs;
    private ItemStack output;

    public FurnaceContent(ItemStack output) {
        this.output = output;
        this.pointer = 0;
        this.inputs = null;
    }

    @Override
    public boolean canBeSplitted() {
        return false;
    }

    @Override
    public Pair<IContent, IContent> split(int y) {
        return null;
    }

    @Override
    public int getSizeY() {
        return 62;
    }

    @Override
    public int getSizeX() {
        return 90;
    }
    
    private static List<ItemStack> getRecipesByOutput(@Nonnull ItemStack targetOutput)
    {
    	Minecraft mc = Minecraft.getInstance();
    	List<IRecipe<?>> res = mc
        		.world
        		.getRecipeManager()
        		.getRecipes()
        		.stream()
                .filter(r -> !r.isDynamic()
                                && ItemStack.areItemsEqualIgnoreDurability(targetOutput, r.getRecipeOutput())
                ).collect(Collectors.toList());
    	List<ItemStack> res2 = new ArrayList<>();
    	for(IRecipe<?> recipe : res) {
    		try {
	    		if(recipe.getType() == IRecipeType.SMELTING) {
	    			res2.add(recipe.getIngredients().get(0).getMatchingStacks()[0]);
	    		}
    		} catch(Exception ex) {
    			
    		}
    	}
    	return res2;
    }

    
    public static List<String> getItemToolTip(ItemStack p_191927_1_)
    {
    	Minecraft mc = Minecraft.getInstance();
        List<ITextComponent> list = p_191927_1_.getTooltip(mc.player, 
        		mc.gameSettings.advancedItemTooltips ? 
        				ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

        List<String> res = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i)
        {
            if (i == 0)
            {
            	String text = p_191927_1_.getItem().getRarity(p_191927_1_).color + (String)list.get(i).getFormattedText();
            	if(text != null) res.add(text);
            }
            else
            {
            	String text = list.get(i).getFormattedText();
            	if(text != null) res.add(TextFormatting.GRAY + text);
            }
        }

        return res;
    }


    @SuppressWarnings("deprecation")
	@Override
    public void renderBack(Minecraft mc, int x, int y, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(DefaultBackgroundDesign.EXTRAS);
        GuiUtils.drawTexturedModalRect(x, y, 0, 64, 90, 62, mc.currentScreen.getBlitOffset());
        if(this.inputs == null) {
        	this.inputs = getRecipesByOutput(this.output);
        }
        if(this.inputs.isEmpty()) return;
        ItemStack input = this.inputs.get(pointer);
        RenderHelper.enableStandardItemLighting();
        mc.getItemRenderer().renderItemAndEffectIntoGUI(input, x + 5, y + 5);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(output, x + 64, y + 23);
        RenderHelper.disableStandardItemLighting();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void renderFront(Minecraft mc, int x, int y, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(DefaultBackgroundDesign.EXTRAS);
        int fireY = 14 - (int) (mc.world.getGameTime() / 15 % 14);
        GuiUtils.drawTexturedModalRect(x + 5, y + 6 + 18 + 14 - fireY, 91, 64 + 14 - fireY, 14, fireY, mc.currentScreen.getBlitOffset());
        int arrowX = (int) (mc.world.getGameTime() / 15 % 24);
        GuiUtils.drawTexturedModalRect(x + 28, y + 6 + 16, 91, 78, arrowX, 17, mc.currentScreen.getBlitOffset());
        if(this.inputs == null) {
        	this.inputs = getRecipesByOutput(this.output);
        }
        if(this.inputs.isEmpty()) return;
        ItemStack input = this.inputs.get(pointer);
        if (mouseX > x + 5 && mouseX < x + 5 + 18 && mouseY > y + 5 && mouseY < y + 5 + 18) {
            GuiUtils.drawHoveringText(getItemToolTip(input), mouseX, mouseY,
        			mc.currentScreen.width, mc.currentScreen.height, mc.currentScreen.width - 2,
        			mc.fontRenderer);
        }
        if (mouseX > x + 64 && mouseX < x + 64 + 18 && mouseY > y + 23 && mouseY < y + 23 + 18) {
            GuiUtils.drawHoveringText(getItemToolTip(output), mouseX, mouseY,
        			mc.currentScreen.width, mc.currentScreen.height, mc.currentScreen.width - 2,
        			mc.fontRenderer);
        }
    }

    private static class LeftArrow extends DrawableButton {

    	FurnaceContent content;
    	GuiManualBase base;
    	Minecraft mc;
    	int contentX, contentY;
		public LeftArrow(int posX, int posY, IDrawableLocationTextureHovereable texture, GuiManualBase base, FurnaceContent content, Minecraft mc,
				int contentX, int contentY) {
			super(posX, posY, texture, null);
			this.content = content;
			this.base = base;
			this.mc = mc;
			this.contentX = contentX;
			this.contentY = contentY;
		}
		
    	@Override
    	public void onPress() {
    		if(this.isHovered()) {
    			content.pointer--;
    			content.onRemoved(mc, base, contentX, contentY);
    			content.onAdded(mc, base, contentX, contentY);
    		}
    	}
    }
    
    @Override
    public void onAdded(Minecraft mc, GuiManualBase base, int contentX, int contentY) {
        int x = 60;
        int y = 46;
        if (pointer > 0) {
            leftArrow = new LeftArrow(contentX + x, contentY + y, new DefaultDrawableLocationTexture(0, 0, DefaultBackgroundDesign.EXTRAS, 170, 1, 12, 10, 183, 1), 
            		base, this, mc, contentX, contentY);
            base.addButtonToGUI(leftArrow);
        }
        if(this.inputs == null) {
        	this.inputs = getRecipesByOutput(this.output);
        }
        if (pointer < inputs.size() - 1) {
            rightArrow = new RightArrow(contentX + 14 + x, contentY + y, new DefaultDrawableLocationTexture(0, 0, DefaultBackgroundDesign.EXTRAS, 170, 12, 12, 10, 183, 12),
            		base, this, mc, contentX, contentY);
            base.addButtonToGUI(rightArrow);
        }
    }
    
    private static class RightArrow extends DrawableButton {

    	FurnaceContent content;
    	GuiManualBase base;
    	Minecraft mc;
    	int contentX, contentY;
		public RightArrow(int posX, int posY, IDrawableLocationTextureHovereable texture, GuiManualBase base, FurnaceContent content, Minecraft mc,
				int contentX, int contentY) {
			super(posX, posY, texture, null);
			this.content = content;
			this.base = base;
			this.mc = mc;
			this.contentX = contentX;
			this.contentY = contentY;
		}
		
    	@Override
    	public void onPress() {
    		if(this.isHovered()) {
    			content.pointer++;
    			content.onRemoved(mc, base, contentX, contentY);
    			content.onAdded(mc, base, contentX, contentY);
    		}
    	}
    }

    @Override
    public void onRemoved(Minecraft mc, GuiManualBase base, int contentX, int contentY) {
        if (leftArrow != null) {
            base.removeButtonToGUI(leftArrow);
            leftArrow = null;
        }
        if (rightArrow != null) {
            base.removeButtonToGUI(rightArrow);
            rightArrow = null;
        }
    }
}
