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

import com.mojang.blaze3d.platform.GlStateManager;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.gui.manual.CategoryEntry;
import cn.shadow.vacation_diary.gui.manual.IManual;
import cn.shadow.vacation_diary.gui.manual.category.BasicCategory;
import cn.shadow.vacation_diary.gui.manual.category.display.ItemStackCategoryDisplay;
import cn.shadow.vacation_diary.gui.manual.content.FurnaceContent;
import cn.shadow.vacation_diary.gui.manual.content.RecipeContent;
import cn.shadow.vacation_diary.gui.manual.content.TextContent;
import cn.shadow.vacation_diary.gui.manual.design.DefaultDrawableLocationTexture;
import cn.shadow.vacation_diary.gui.manual.design.IBackgroundDesign;
import cn.shadow.vacation_diary.gui.manual.design.IDrawableLocationTextureHovereable;
import cn.shadow.vacation_diary.gui.manual.design.IManualDesign;
import cn.shadow.vacation_diary.gui.manual.design.IManualItemDesign;
import cn.shadow.vacation_diary.gui.manual.design.display.ICategoryEntryDisplay;
import cn.shadow.vacation_diary.item.ItemRegistry;
import cn.shadow.vacation_diary.item.items.ItemManual;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import static cn.shadow.vacation_diary.gui.manual.design.DefaultBackgroundDesign.EXTRAS;

import java.awt.*;

public class TestManualCustomDesign implements IManual {

    @Override
    public void registerCategories(ManualInfo info) {
        double scale = 2.5;
        
        info.registerCategory(new BasicCategory("gui.dimension", new ItemStackCategoryDisplay(new ItemStack(Items.END_PORTAL_FRAME), scale), 
        		I18n.format("gui.dimension.lore1"))
                .addEntry(new ResourceLocation(VacationDiary.MOD_ID, "dimension"), 
                		new CustomCategoryEntry(new ItemStack(ItemRegistry.dimension.get()))
	                        .addContent(new TextContent(I18n.format("gui.dimension.content"),
	                                222)))
        );
        
        info.registerCategory(new BasicCategory("gui.plant", 
        		new ItemStackCategoryDisplay(new ItemStack(ItemRegistry.tea_sapling.get()), scale), 
        		I18n.format("gui.plant.lore1"))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "blue_sage_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.blue_sage_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "butterfly_weed_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.butterfly_weed_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fuchsia_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.fuchsia_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "golden_shower_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.golden_shower_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hortensia_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.hortensia_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "larkspur_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.larkspur_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "mountain_laurel_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.mountain_laurel_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "purple_hibiscus_block"), new CustomCategoryEntry(new ItemStack(ItemRegistry.purple_hibiscus_block.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "green_tea_plant"), new CustomCategoryEntry(new ItemStack(ItemRegistry.green_tea_plant.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "black_tea_plant"), new CustomCategoryEntry(new ItemStack(ItemRegistry.black_tea_plant.get())))
        );
        
        info.registerCategory(new BasicCategory("gui.decoration", 
        		new ItemStackCategoryDisplay(new ItemStack(ItemRegistry.starfish_orange.get()), scale), 
        		I18n.format("gui.decoration.lore1"))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "tea_log"), new CustomCategoryEntry(new ItemStack(ItemRegistry.tea_log.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "tea_leaves"), new CustomCategoryEntry(new ItemStack(ItemRegistry.tea_leaves.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "starfish_orange"), new CustomCategoryEntry(new ItemStack(ItemRegistry.starfish_orange.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "starfish_red"), new CustomCategoryEntry(new ItemStack(ItemRegistry.starfish_red.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "starfish_yellow"), new CustomCategoryEntry(new ItemStack(ItemRegistry.starfish_yellow.get())))
        );
        
        info.registerCategory(new BasicCategory("gui.food", 
        		new ItemStackCategoryDisplay(new ItemStack(ItemRegistry.apple_cake.get()), scale), 
        		I18n.format("gui.food.lore1"))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_cake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_ice_cream"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_jam.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam_biscuit"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_jam_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam_pancake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_jam_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam_toast"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_jam_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jelly"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_jelly.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jelly.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_juice"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.apple_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "biscuit"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "bread_slice"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.bread_slice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.bread_slice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "butterfly_weed_block"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.butterfly_weed_block.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.butterfly_weed_block.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cactus_juice"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.cactus_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cactus_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.caramel.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_apple"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel_apple.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_apple.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_biscuit"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_cake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_ice_cream"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_pancake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_toast"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.caramel_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "carrot_juice"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.carrot_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.carrot_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "carrot_pie"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.carrot_pie.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.carrot_pie.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "carrot_soup"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.carrot_soup.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.carrot_soup.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cereal"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.cereal.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cereal.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cheese"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.cheese.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cheese.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cheese_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.cheese_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cheese_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chicken_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chicken_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chicken_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_apple"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_apple.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_apple.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_bar"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_bar.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_bar.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_biscuit"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_cake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_cereal"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_cereal.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_cereal.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_ice_cream"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_milk_bottle"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_pancake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_toast"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.chocolate_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cooked_marshmallow"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.cooked_marshmallow.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.cooked_marshmallow.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cooked_squid_tentacle"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.cooked_squid_tentacle.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.cooked_squid_tentacle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "donut"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.donut.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.donut.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "egg_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.egg_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.egg_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fish_n_chips"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.fish_n_chips.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fish_n_chips.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fish_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.fish_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fish_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fish_soup"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.fish_soup.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fish_soup.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "french_fries"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.french_fries.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.french_fries.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fried_egg"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.fried_egg.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.fried_egg.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fruit_salad"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.fruit_salad.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fruit_salad.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hot_chocolate_milk_bottle"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.hot_chocolate_milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.hot_chocolate_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hot_milk_bottle"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.hot_milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.hot_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hot_milk_bottle2"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.hot_milk_bottle.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.hot_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "jelly"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.jelly.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.jelly.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_apple_juice"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.magic_apple_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_apple_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_cake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.magic_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_fruit_salad"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.magic_fruit_salad.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_fruit_salad.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_ice_cream"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.magic_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_ice_cream"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_jam.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam_biscuit"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_jam_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam_pancake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_jam_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam_toast"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_jam_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jelly"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_jelly.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jelly.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_juice"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.melon_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "milk_bottle"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "mutton_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.mutton_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.mutton_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "oatmeal"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.oatmeal.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.oatmeal.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "oreo"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.oreo.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.oreo.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pancake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.pancake.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pancake_dough"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.pancake_dough.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.pancake_dough.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "porkchop_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.porkchop_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.porkchop_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pumpkin_bread"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.pumpkin_bread.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.pumpkin_bread.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pumpkin_soup"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.pumpkin_soup.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.pumpkin_soup.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "raw_marshmallow"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.raw_marshmallow.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.raw_marshmallow.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "roasted_seeds"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.roasted_seeds.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.roasted_seeds.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "salad"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.salad.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.salad.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "spaghetti"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.spaghetti.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.spaghetti.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "squid_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.squid_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.squid_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "squid_sushi"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.squid_sushi.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.squid_sushi.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "steak_sandwich"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.steak_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.steak_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_biscuit"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.sugar_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_cube"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.sugar_cube.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_cube.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_pancake"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.sugar_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_toast"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.sugar_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sushi"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.sushi.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sushi.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "tea"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.tea.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.tea.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "trailmix"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.trailmix.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.trailmix.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "vanilla_ice_cream"), 
        				new CustomCategoryEntry(new ItemStack(ItemRegistry.vanilla_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.vanilla_ice_cream.get())))
        				)
        		);

        info.setDesign(new CustomManualDesign());
        info.setManualItemDesign(new CustomManualItem());
        info.setDisplayName("Test Manual Custom Design");
    }

    @Override
    public void onManualItemCreation(ManualInfo info, ItemManual manual) {
    }

    public static class CustomCategoryEntry extends CategoryEntry {

        private final ItemStack itemStack;

        public CustomCategoryEntry(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @Override
        public ICategoryEntryDisplay getDisplay() {
            return new CategoryEntryDisplay(itemStack);
        }
    }

    public static class CustomManualItem implements IManualItemDesign {

        @Override
        public int getCoverColor() {
            return 0xcdfc8d;
        }

        @Override
        public int getBorderColor() {
            return 0xaf9b00;
        }

        @Override
        public int getLetterColor() {
            return 0xaf9b00;
        }
    }

    public static class CategoryEntryDisplay implements ICategoryEntryDisplay {

        private final ItemStack itemStack;
        public CategoryEntryDisplay(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @SuppressWarnings("deprecation")
		@Override
        public void render(Minecraft mc, int x, int y, boolean isHovered) {
            GlStateManager.pushMatrix();
            String name = itemStack.getDisplayName().getFormattedText();
            Color color = Color.CYAN.darker();
            if (isHovered) color = color.darker();
            mc.fontRenderer.drawString(name, x + getSizeX() / 2 - mc.fontRenderer.getStringWidth(name) / 2, y + 3, color.getRGB());
//            GuiUtils.drawGradientRect(zLevel, left, top, right, bottom, startColor, endColor);
//            Gui.drawRect(x + 1, y + 3 + mc.fontRenderer.FONT_HEIGHT + 1, x + getSizeX() - 1, y + 3 + mc.fontRenderer.FONT_HEIGHT + 2, color.darker().getRGB());
            double scale = 1.8;
            GlStateManager.scaled(scale, scale, scale);
            RenderHelper.enableStandardItemLighting();
            mc.getItemRenderer().renderItemIntoGUI(itemStack, (int) ((x + getSizeX() / 2D - 15) / scale), (int) ((y + 16) / scale));
            GlStateManager.popMatrix();
        }

        @Override
        public int getSizeX() {
            return 94;
        }

        @Override
        public int getSizeY() {
            return 44;
        }
    }

    public static class CustomManualDesign implements IManualDesign {

        @Override
        public IBackgroundDesign getCategoryDesign() {
            return new CustomBackgroundDesign();
        }

        @Override
        public int getDisplayColor() {
            return 0x021868;
        }

        @Override
        public int getTextColor() {
            return 0xc2d7f9;
        }

        @Override
        public IBackgroundDesign getCategoryEntryDesign() {
            return new CustomBackgroundDesign();
        }

        @Override
        public IBackgroundDesign getPageDesign() {
            return new CustomBackgroundDesign();
        }


        public static class CustomBackgroundDesign implements IBackgroundDesign {

            public static final ResourceLocation BOOK_BACK = new ResourceLocation(VacationDiary.MOD_ID, "textures/gui/book_back_other.png");

            @Override
            public ResourceLocation getTexture() {
                return BOOK_BACK;
            }

            @Override
            public int getTopPadding() {
                return 17;
            }

            @Override
            public int getBottomPadding() {
                return 17;
            }

            @Override
            public int getLeftPadding() {
                return 22;
            }

            @Override
            public int getRightPadding() {
                return 22;
            }


            @Override
            public IDrawableLocationTextureHovereable getPrevPageTexture() {
                return new DefaultDrawableLocationTexture(6, 162, EXTRAS, 1, 14, 18, 10, 24, 14);
            }

            @Override
            public IDrawableLocationTextureHovereable getNextPageTexture() {
                return new DefaultDrawableLocationTexture(232, 162, EXTRAS, 1, 1, 18, 10, 24, 1);
            }

            @Override
            public IDrawableLocationTextureHovereable getBackTexture() {
                return new DefaultDrawableLocationTexture(6, 6, EXTRAS, 1, 27, 18, 10, 24, 27);
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
                return 256;
            }

            @Override
            public int getTextureHeight() {
                return 176;
            }
        }

    }
}