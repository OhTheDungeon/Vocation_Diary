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
package cn.shadow.vacation_diary.gui.manual.actual;

import cn.shadow.vacation_diary.gui.GuiCategoryList;
import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.manual.IBookCategory;
import cn.shadow.vacation_diary.gui.manual.IManual;
import cn.shadow.vacation_diary.gui.manual.design.DefaultManualDesign;
import cn.shadow.vacation_diary.gui.manual.design.DefaultManualItemDesign;
import cn.shadow.vacation_diary.gui.manual.design.IManualDesign;
import cn.shadow.vacation_diary.gui.manual.design.IManualItemDesign;
import cn.shadow.vacation_diary.item.items.ItemManual;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ManualInfo {

    public static LinkedHashMap<String, ManualInfo> MANUALS = new LinkedHashMap<>();

    private final String id;
    @SuppressWarnings("unused")
	private final Class<? extends IManual> manualClass;
    private final List<IBookCategory> categories;
    private IManual manualObject;
    private String displayName;
    private IManualDesign design;
    private IManualItemDesign manualItemDesign;
    private String modName;
    private ItemManual itemManual;

    @OnlyIn(Dist.CLIENT)
    private GuiManualBase lastManual;

    public ManualInfo(String id, String modName, Class<? extends IManual> manualClass) throws IllegalAccessException, InstantiationException {
        this.id = id;
        this.manualClass = manualClass;
        this.categories = new ArrayList<>();
        this.manualObject = manualClass.newInstance();
        this.displayName = "";
        this.design = new DefaultManualDesign();
        this.manualItemDesign = new DefaultManualItemDesign();
        this.modName = modName;
        this.itemManual = new ItemManual(id);
    }

    /**
     * Registers a category into the itemManual
     *
     * @param category A category to register
     */
    public void registerCategory(IBookCategory category) {
        categories.add(category);
    }

    /**
     * Gets all the registered categories
     * @return a list of categories
     */
    public List<IBookCategory> getCategories() {
        return categories;
    }

    /**
     * Gets the IManual object
     * @return the itemManual object
     */
    public IManual getManualObject() {
        return manualObject;
    }

    /**
     * Gets the id of the itemManual
     * @return the id of the itemManual
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the display name of the itemManual
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of the itemManual (It can be a localization string)
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the current itemManual design
     * @return the itemManual design
     */
    public IManualDesign getDesign() {
        return design;
    }

    /**
     * Sets the itemManual design
     * @param design the itemManual design
     */
    public void setDesign(IManualDesign design) {
        this.design = design;
    }

    /**
     * Gets the design for the item itemManual
     * @return the itemManual item design
     */
    public IManualItemDesign getManualItemDesign() {
        return manualItemDesign;
    }

    /**
     * Sets the design of the item itemManual
     * @param manualItemDesign the itemManual item design
     */
    public void setManualItemDesign(IManualItemDesign manualItemDesign) {
        this.manualItemDesign = manualItemDesign;
    }

    /**
     * Gets the mod name that adds the itemManual
     * @return the mod name
     */
    public String getModName() {
        return modName;
    }

    /**
     * Opens the GUI for the itemManual, it will try to open the last instance of the itemManual or a new instance of the category GUI
     */
    @OnlyIn(Dist.CLIENT)
    public void openGui() {
        Minecraft.getInstance().displayGuiScreen(lastManual == null ? new GuiCategoryList(null, this) : lastManual);
    }

    /**
     * Sets the current GUI instance of the itemManual for later use
     *
     * @param lastManual The current GUI instance
     */
    @OnlyIn(Dist.CLIENT)
    public void setLastManual(GuiManualBase lastManual) {
        this.lastManual = lastManual;
    }

    /**
     * Gets the manual item for the info
     *
     * @return the Manual Item
     */
    public ItemManual getItemManual() {
        return itemManual;
    }
}
