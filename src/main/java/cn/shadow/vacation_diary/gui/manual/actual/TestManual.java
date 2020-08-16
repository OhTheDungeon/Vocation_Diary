package cn.shadow.vacation_diary.gui.manual.actual;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.gui.manual.IManual;
import cn.shadow.vacation_diary.gui.manual.category.BasicCategory;
import cn.shadow.vacation_diary.gui.manual.category.BasicCategoryEntry;
import cn.shadow.vacation_diary.gui.manual.category.display.ItemStackCategoryDisplay;
import cn.shadow.vacation_diary.gui.manual.content.FurnaceContent;
import cn.shadow.vacation_diary.gui.manual.content.RecipeContent;
import cn.shadow.vacation_diary.gui.manual.content.TextContent;
import cn.shadow.vacation_diary.item.ItemRegistry;
import cn.shadow.vacation_diary.item.items.ItemManual;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class TestManual implements IManual {

    @Override
    public void registerCategories(ManualInfo info) {
    	double scale = 1;
    	int width = 130;
        info.registerCategory(new BasicCategory("gui.dimension", new ItemStackCategoryDisplay(new ItemStack(Items.END_PORTAL_FRAME), scale), 
        		I18n.format("gui.dimension.lore1"))
                .addEntry(new ResourceLocation(VacationDiary.MOD_ID, "dimension"), 
                		new BasicCategoryEntry(new ItemStack(ItemRegistry.dimension.get()))
	                        .addContent(new TextContent(I18n.format("gui.dimension.content"),
	                        		width)))
        );
        
        info.registerCategory(new BasicCategory("gui.plant", 
        		new ItemStackCategoryDisplay(new ItemStack(ItemRegistry.tea_sapling.get()), scale), 
        		I18n.format("gui.plant.lore1"))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "blue_sage_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.blue_sage_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "butterfly_weed_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.butterfly_weed_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fuchsia_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.fuchsia_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "golden_shower_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.golden_shower_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hortensia_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.hortensia_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "larkspur_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.larkspur_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "mountain_laurel_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.mountain_laurel_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "purple_hibiscus_block"), new BasicCategoryEntry(new ItemStack(ItemRegistry.purple_hibiscus_block.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "green_tea_plant"), new BasicCategoryEntry(new ItemStack(ItemRegistry.green_tea_plant.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "black_tea_plant"), new BasicCategoryEntry(new ItemStack(ItemRegistry.black_tea_plant.get()))
        				.addContent(new TextContent(I18n.format("gui.plant.content"),
        						width)))
        );
        
        info.registerCategory(new BasicCategory("gui.decoration", 
        		new ItemStackCategoryDisplay(new ItemStack(ItemRegistry.tea_leaves.get()), scale), 
        		I18n.format("gui.decoration.lore1"))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "tea_log"), new BasicCategoryEntry(new ItemStack(ItemRegistry.tea_log.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "tea_leaves"), new BasicCategoryEntry(new ItemStack(ItemRegistry.tea_leaves.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "starfish_orange"), new BasicCategoryEntry(new ItemStack(ItemRegistry.starfish_orange.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "starfish_red"), new BasicCategoryEntry(new ItemStack(ItemRegistry.starfish_red.get())))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "starfish_yellow"), new BasicCategoryEntry(new ItemStack(ItemRegistry.starfish_yellow.get())))
        );
        
        info.registerCategory(new BasicCategory("gui.food", 
        		new ItemStackCategoryDisplay(new ItemStack(ItemRegistry.apple_cake.get()), scale), 
        		I18n.format("gui.food.lore1"))
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_cake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_ice_cream"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_jam.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam_biscuit"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_jam_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam_pancake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_jam_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jam_toast"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_jam_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jam_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_jelly"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_jelly.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_jelly.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "apple_juice"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.apple_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.apple_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "biscuit"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "bread_slice"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.bread_slice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.bread_slice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "butterfly_weed_block"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.butterfly_weed_block.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.butterfly_weed_block.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cactus_juice"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.cactus_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cactus_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.caramel.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_apple"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel_apple.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_apple.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_biscuit"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_cake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_ice_cream"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_pancake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "caramel_toast"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.caramel_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.caramel_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "carrot_juice"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.carrot_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.carrot_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "carrot_pie"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.carrot_pie.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.carrot_pie.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "carrot_soup"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.carrot_soup.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.carrot_soup.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cereal"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.cereal.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cereal.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cheese"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.cheese.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cheese.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cheese_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.cheese_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.cheese_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chicken_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chicken_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chicken_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_apple"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_apple.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_apple.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_bar"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_bar.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_bar.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_biscuit"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_cake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_cereal"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_cereal.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_cereal.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_ice_cream"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_milk_bottle"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_pancake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "chocolate_toast"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.chocolate_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.chocolate_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cooked_marshmallow"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.cooked_marshmallow.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.cooked_marshmallow.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "cooked_squid_tentacle"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.cooked_squid_tentacle.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.cooked_squid_tentacle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "donut"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.donut.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.donut.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "egg_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.egg_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.egg_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fish_n_chips"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.fish_n_chips.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fish_n_chips.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fish_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.fish_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fish_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fish_soup"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.fish_soup.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fish_soup.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "french_fries"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.french_fries.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.french_fries.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fried_egg"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.fried_egg.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.fried_egg.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "fruit_salad"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.fruit_salad.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.fruit_salad.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hot_chocolate_milk_bottle"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.hot_chocolate_milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.hot_chocolate_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hot_milk_bottle"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.hot_milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.hot_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "hot_milk_bottle2"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.hot_milk_bottle.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.hot_milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "jelly"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.jelly.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.jelly.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_apple_juice"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.magic_apple_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_apple_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_cake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.magic_cake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_cake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_fruit_salad"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.magic_fruit_salad.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_fruit_salad.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "magic_ice_cream"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.magic_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.magic_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_ice_cream"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_ice_cream.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_jam.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam_biscuit"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_jam_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam_pancake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_jam_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jam_toast"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_jam_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jam_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_jelly"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_jelly.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_jelly.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "melon_juice"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.melon_juice.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.melon_juice.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "milk_bottle"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.milk_bottle.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.milk_bottle.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "mutton_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.mutton_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.mutton_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "oatmeal"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.oatmeal.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.oatmeal.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "oreo"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.oreo.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.oreo.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pancake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.pancake.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pancake_dough"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.pancake_dough.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.pancake_dough.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "porkchop_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.porkchop_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.porkchop_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pumpkin_bread"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.pumpkin_bread.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.pumpkin_bread.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "pumpkin_soup"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.pumpkin_soup.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.pumpkin_soup.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "raw_marshmallow"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.raw_marshmallow.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.raw_marshmallow.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "roasted_seeds"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.roasted_seeds.get())).addContent(new FurnaceContent(new ItemStack(ItemRegistry.roasted_seeds.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "salad"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.salad.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.salad.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "spaghetti"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.spaghetti.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.spaghetti.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "squid_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.squid_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.squid_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "squid_sushi"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.squid_sushi.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.squid_sushi.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "steak_sandwich"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.steak_sandwich.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.steak_sandwich.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_biscuit"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.sugar_biscuit.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_biscuit.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_cube"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.sugar_cube.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_cube.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_pancake"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.sugar_pancake.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_pancake.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sugar_toast"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.sugar_toast.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sugar_toast.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "sushi"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.sushi.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.sushi.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "tea"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.tea.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.tea.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "trailmix"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.trailmix.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.trailmix.get())))
        				)
        		.addEntry(new ResourceLocation(VacationDiary.MOD_ID, "vanilla_ice_cream"), 
        				new BasicCategoryEntry(new ItemStack(ItemRegistry.vanilla_ice_cream.get())).addContent(new RecipeContent(new ItemStack(ItemRegistry.vanilla_ice_cream.get())))
        				)
        		);

        
        info.setDisplayName(I18n.format("title"));
    }

    @Override
    public void onManualItemCreation(ManualInfo info, ItemManual manual) {

    }

}
