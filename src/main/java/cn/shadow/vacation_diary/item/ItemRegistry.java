package cn.shadow.vacation_diary.item;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.gui.manual.IManual;
import cn.shadow.vacation_diary.gui.manual.actual.ManualInfo;
import cn.shadow.vacation_diary.gui.manual.actual.TestManual;
import cn.shadow.vacation_diary.item.items.ItemBottle;
import cn.shadow.vacation_diary.item.items.ItemBowl;
import cn.shadow.vacation_diary.item.items.ItemCheese;
import cn.shadow.vacation_diary.item.items.ItemFoodStick;
import cn.shadow.vacation_diary.item.items.ItemFoodUF;
import cn.shadow.vacation_diary.item.items.ItemJuice;
import cn.shadow.vacation_diary.item.items.ItemMagicAppleJuice;
import cn.shadow.vacation_diary.item.items.ItemMagicCake;
import cn.shadow.vacation_diary.item.items.ItemMagicFruitSalad;
import cn.shadow.vacation_diary.item.items.ItemMagicIceCream;
import cn.shadow.vacation_diary.item.items.ItemReedUF;
import cn.shadow.vacation_diary.item.items.ItemSoupUF;
import cn.shadow.vacation_diary.item.items.ItemTea;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
	@SuppressWarnings("deprecation")
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, VacationDiary.MOD_ID);
	  public static RegistryObject<Item> guide = ITEMS.register("guide", () -> {
	     try {
	    	String id = "guide";
	        ManualInfo manualInfo = new ManualInfo(id, VacationDiary.MOD_ID, (Class<? extends IManual>) TestManual.class);
	        manualInfo.getManualObject().onManualItemCreation(manualInfo, manualInfo.getItemManual());
//	        item = manualInfo.getItemManual();
	        ManualInfo.MANUALS.put(id, manualInfo);
	        return manualInfo.getItemManual();
	     } catch(Exception ex) {
	    	 return null;
	     }
	  });
	public static RegistryObject<Item> dimension = ITEMS.register("dimension", () -> {
		return new Item(new Properties().group(ModGroup.itemGroup));
	});
	  
//	public static RegistryObject<Item> green_tea_leaf = ITEMS.register("green_tea_leaf", () -> {
//		return new ItemGreenTeaLeaf();
//	});
//	public static RegistryObject<Item> black_tea_leaf = ITEMS.register("black_tea_leaf", () -> {
//		return new ItemBlackTeaLeaf();
//	});	
	
	public static RegistryObject<Item> tea_sapling = ITEMS.register("tea_sapling", () -> {
		return new BlockItem(BlockRegistry.tea_sapling.get(), 
				new Item.Properties().group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> tea_leaves = ITEMS.register("tea_leaves", () -> {
		return new BlockItem(BlockRegistry.tea_leaves.get(), 
				new Item.Properties().group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> tea_log = ITEMS.register("tea_log", () -> {
		return new BlockItem(BlockRegistry.tea_log.get(), 
				new Item.Properties().group(ModGroup.itemGroup));
	});
	
	public static RegistryObject<Item> green_tea_plant = ITEMS.register("green_tea_plant", () -> {
		return new TallBlockItem(BlockRegistry.green_tea_plant.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	
	public static RegistryObject<Item> black_tea_plant = ITEMS.register("black_tea_plant", () -> {
		return new TallBlockItem(BlockRegistry.black_tea_plant.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	
//	public static RegistryObject<Item> cooked_mutton = ITEMS.register("cooked_mutton", () -> {
//		return new ItemFoodUF("CookedMutton", 6, 0.8F, true);
//	});
//	public static RegistryObject<Item> raw_mutton = ITEMS.register("raw_mutton", () -> {
//		return new ItemFoodUF("RawMutton", 3, 0.3F, true);
//	});
	public static RegistryObject<Item> mutton_sandwich = ITEMS.register("mutton_sandwich", () -> {
		return new ItemFoodUF("MuttonSandwich", 9, 1.0F, true);
	});
	

	public static RegistryObject<Item> milk_bottle = ITEMS.register("milk_bottle", () -> {
		return new ItemBottle("MilkBottle", 0, 0.0F, true);
	});
	public static RegistryObject<Item> chocolate_milk_bottle = ITEMS.register("chocolate_milk_bottle", () -> {
		return new ItemBottle("ChocolateMilkBottle", 3, 0.8F, true);
	});
	public static RegistryObject<Item> cheese = ITEMS.register("cheese", () -> {
		return new ItemCheese("Cheese", 2, 0.6F, true);
	});
	public static RegistryObject<Item> chocolate_bar = ITEMS.register("chocolate_bar", () -> {
		return new ItemFoodUF("ChocolateBar", 6, 1.0F, false);
	});
	public static RegistryObject<Item> fruit_salad = ITEMS.register("fruit_salad", () -> {
		return new ItemBowl("FruitSalad", 7, 0.6F, false);
	});
	public static RegistryObject<Item> magic_fruit_salad = ITEMS.register("magic_fruit_salad", () -> {
		return new ItemMagicFruitSalad("MagicFruitSalad", 6, 0.6F, false)
				.setPotionEffect(Effects.REGENERATION, 5, 0).setPotionEffect(Effects.RESISTANCE, 10, 0);
	});
	public static RegistryObject<Item> sugar_cube = ITEMS.register("sugar_cube", () -> {
		return new ItemFoodUF("SugarCube", 4, 0.1F, false).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> caramel = ITEMS.register("caramel", () -> {
		return new ItemFoodUF("Caramel", 5, 0.2F, false).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> caramel_apple = ITEMS.register("caramel_apple", () -> {
		return new ItemFoodStick("CaramelApple", 10, 0.5F, false).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> roasted_seeds = ITEMS.register("roasted_seeds", () -> {
		return new ItemFoodUF("RoastedSeeds", 1, 0.5F, false);
	});
	public static RegistryObject<Item> fried_egg = ITEMS.register("fried_egg", () -> {
		return new ItemFoodUF("FriedEgg", 3, 0.4F, true);
	});
	public static RegistryObject<Item> pumpkin_soup = ITEMS.register("pumpkin_soup", () -> {
		return new ItemSoupUF("PumpkinSoup", 5, 0.8F);
	});
	public static RegistryObject<Item> salad = ITEMS.register("salad", () -> {
		return new ItemBowl("Salad", 3, 0.6F, false);
	});
	public static RegistryObject<Item> oatmeal = ITEMS.register("oatmeal", () -> {
		return new ItemBowl("Oatmeal", 4, 0.6F, false);
	});
	public static RegistryObject<Item> jelly = ITEMS.register("jelly", () -> {
		return new ItemBowl("Jelly", 5, 0.3F, false).setPotionEffect(Effects.JUMP_BOOST, 30, 0);
	});
	public static RegistryObject<Item> raw_marshmallow = ITEMS.register("raw_marshmallow", () -> {
		return new ItemFoodStick("RawMarshmallow", 3, 0.3F, false);
	});
	public static RegistryObject<Item> cooked_marshmallow = ITEMS.register("cooked_marshmallow", () -> {
		return new ItemFoodStick("CookedMarshmallow", 4, 0.3F, false);
	});
	public static RegistryObject<Item> vanilla_ice_cream = ITEMS.register("vanilla_ice_cream", () -> {
		return new ItemBowl("VanillaIceCream", 2, 0.3F, true);
	});
	public static RegistryObject<Item> bread_slice = ITEMS.register("bread_slice", () -> {
		return new ItemFoodUF("BreadSlice", 1, 0.3F, false);
	});
	public static RegistryObject<Item> porkchop_sandwich = ITEMS.register("porkchop_sandwich", () -> {
		return new ItemFoodUF("PorkChopSandwich", 11, 1.0F, true);
	});
	public static RegistryObject<Item> steak_sandwich = ITEMS.register("steak_sandwich", () -> {
		return new ItemFoodUF("SteakSandwich", 11, 1.0F, true);
	});
	public static RegistryObject<Item> fish_sandwich = ITEMS.register("fish_sandwich", () -> {
		return new ItemFoodUF("FishSandwich", 8, 1.0F, true);
	});
	public static RegistryObject<Item> chicken_sandwich = ITEMS.register("chicken_sandwich", () -> {
		return new ItemFoodUF("ChickenSandwich", 9, 1.0F, true);
	});
	public static RegistryObject<Item> egg_sandwich = ITEMS.register("egg_sandwich", () -> {
		return new ItemFoodUF("EggSandwich", 6, 1.0F, true);
	});
	public static RegistryObject<Item> biscuit = ITEMS.register("biscuit", () -> {
		return new ItemFoodUF("Biscuit", 1, 0.3F, true);
	});
	public static RegistryObject<Item> trailmix = ITEMS.register("trailmix", () -> {
		return new ItemBowl("Trailmix", 4, 0.5F, false);
	});
	
	public static RegistryObject<Item> apple_cake = ITEMS.register("apple_cake", () -> {
		return new ItemReedUF(BlockRegistry.apple_cake.get(), "AppleCake");
	});
	public static RegistryObject<Item> chocolate_cake = ITEMS.register("chocolate_cake", () -> {
		return new ItemReedUF(BlockRegistry.chocolate_cake.get(), "ChocolateCake");
	});
	public static RegistryObject<Item> magic_cake = ITEMS.register("magic_cake", () -> {
		return new ItemMagicCake(BlockRegistry.magic_cake.get(), "MagicCake");
	});
	
	public static RegistryObject<Item> sushi = ITEMS.register("sushi", () -> {
		return new ItemFoodUF("Sushi", 5, 0.3F, true);
	});
	public static RegistryObject<Item> squid_tentacle = ITEMS.register("squid_tentacle", () -> {
		return new ItemFoodUF("SquidTentacle", 2, 0.3F, true);
	});
	public static RegistryObject<Item> cooked_squid_tentacle = ITEMS.register("cooked_squid_tentacle", () -> {
		return new ItemFoodUF("CookedSquidTentacle", 5, 0.8F, true);
	});
	public static RegistryObject<Item> squid_sandwich = ITEMS.register("squid_sandwich", () -> {
		return new ItemFoodUF("SquidSandwich", 8, 0.8F, true);
	});
	public static RegistryObject<Item> magic_apple_juice = ITEMS.register("magic_apple_juice", () -> {
		return new ItemMagicAppleJuice("MagicAppleJuice", 13, 1.2F);
	});
	public static RegistryObject<Item> melon_juice = ITEMS.register("melon_juice", () -> {
		return new ItemJuice("MelonJuice", 6, 0.9F);
	});
	public static RegistryObject<Item> apple_juice = ITEMS.register("apple_juice", () -> {
		return new ItemJuice("AppleJuice", 12, 0.9F);
	});
	public static RegistryObject<Item> carrot_juice = ITEMS.register("carrot_juice", () -> {
		return new ItemSoupUF("CarrotSoup", 9, 0.8F);
	});
	public static RegistryObject<Item> carrot_soup = ITEMS.register("carrot_soup", () -> {
		return new ItemSoupUF("CarrotSoup", 9, 0.8F);
	});
	public static RegistryObject<Item> pumpkin_bread = ITEMS.register("pumpkin_bread", () -> {
		return new ItemFoodUF("PumpkinBread", 10, 0.6F, true);
	});
	public static RegistryObject<Item> fish_n_chips = ITEMS.register("fish_n_chips", () -> {
		return new ItemFoodUF("FishnChips", 12, 1.2F, true);
	});
	public static RegistryObject<Item> sugar_biscuit = ITEMS.register("sugar_biscuit", () -> {
		return new ItemFoodUF("SugarBiscuit", 3, 0.3F, false);
	});
	public static RegistryObject<Item> apple_jam_biscuit = ITEMS.register("apple_jam_biscuit", () -> {
		return new ItemFoodUF("AppleJamBiscuit", 10, 0.3F, false);
	});
	public static RegistryObject<Item> chocolate_biscuit = ITEMS.register("chocolate_biscuit", () -> {
		return new ItemFoodUF("ChocolateBiscuit", 3, 1.0F, false);
	});
	public static RegistryObject<Item> carrot_pie = ITEMS.register("carrot_pie", () -> {
		return new ItemFoodUF("CarrotPie", 9, 0.8F, true);
	});
	public static RegistryObject<Item> hot_chocolate_milk_bottle = ITEMS.register("hot_chocolate_milk_bottle", () -> {
		return new ItemBottle("HotChocolateMilkBottle", 4, 1.0F, true);
	});
	public static RegistryObject<Item> chocolate_ice_cream = ITEMS.register("chocolate_ice_cream", () -> {
		return new ItemBowl("ChocolateIceCream", 9, 0.8F, true);
	});
	
	
	public static RegistryObject<Item> magic_ice_cream = ITEMS.register("magic_ice_cream", () -> {
		return new ItemMagicIceCream("MagicIceCream", 9, 0.6F, true).setPotionEffect(Effects.REGENERATION, 5, 0);
	});
	public static RegistryObject<Item> squid_sushi = ITEMS.register("squid_sushi", () -> {
		return new ItemFoodUF("SquidSushi", 5, 0.3F, true);
	});
	public static RegistryObject<Item> cactus_juice = ITEMS.register("cactus_juice", () -> {
		return new ItemJuice("CactusJuice", 5, 0.6F);
	});
	public static RegistryObject<Item> spaghetti = ITEMS.register("spaghetti", () -> {
		return new ItemBowl("Spaghetti", 5, 0.6F, false);
	});
	public static RegistryObject<Item> apple_ice_cream = ITEMS.register("apple_ice_cream", () -> {
		return new ItemBowl("AppleIceCream", 5, 0.6F, true);
	});
	public static RegistryObject<Item> melon_ice_cream = ITEMS.register("melon_ice_cream", () -> {
		return new ItemBowl("MelonIceCream", 7, 0.6F, true);
	});
	public static RegistryObject<Item> chocolate_apple = ITEMS.register("chocolate_apple", () -> {
		return new ItemFoodStick("ChocolateApple", 11, 0.6F, false);
	});
	public static RegistryObject<Item> caramel_biscuit = ITEMS.register("caramel_biscuit", () -> {
		return new ItemFoodUF("CaramelBiscuit", 7, 0.6F, false).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> fish_soup = ITEMS.register("fish_soup", () -> {
		return new ItemSoupUF("FishSoup", 12, 0.6F);
	});
	public static RegistryObject<Item> tea = ITEMS.register("tea", () -> {
		return new ItemTea("Tea", 2);
	});
	public static RegistryObject<Item> hot_milk_bottle = ITEMS.register("hot_milk_bottle", () -> {
		return new ItemBottle("HotMilkBottle", 1, 0.3F, true);
	});
	public static RegistryObject<Item> caramel_cake = ITEMS.register("caramel_cake", () -> {
		return new ItemReedUF(BlockRegistry.caramel_cake.get(), "CaramelCake");
	});
	public static RegistryObject<Item> cheese_sandwich = ITEMS.register("cheese_sandwich", () -> {
		return new ItemFoodUF("CheeseSandwich", 5, 1.0F, false);
	});
	public static RegistryObject<Item> caramel_ice_cream = ITEMS.register("caramel_ice_cream", () -> {
		return new ItemBowl("CaramelIceCream", 8, 0.6F, true).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> cereal = ITEMS.register("cereal", () -> {
		return new ItemBowl("Cereal", 4, 0.5F, true);
	});
	public static RegistryObject<Item> chocolate_cereal = ITEMS.register("chocolate_cereal", () -> {
		return new ItemBowl("ChocolateCereal", 6, 0.5F, true);
	});
	public static RegistryObject<Item> french_fries = ITEMS.register("french_fries", () -> {
		return new ItemFoodUF("FrenchFries", 7, 0.6F, false); 
	});
	public static RegistryObject<Item> apple_jelly = ITEMS.register("apple_jelly", () -> {
		return new ItemBowl("AppleJelly", 9, 0.4F, false).setPotionEffect(Effects.JUMP_BOOST, 30, 0);
	});
	public static RegistryObject<Item> melon_jelly = ITEMS.register("melon_jelly", () -> {
		return new ItemBowl("MelonJelly", 7, 0.4F, false).setPotionEffect(Effects.JUMP_BOOST, 30, 0);
	});
	public static RegistryObject<Item> donut = ITEMS.register("donut", () -> {
		return new ItemFoodUF("Donut", 7, 0.6F, false);
	});
	public static RegistryObject<Item> oreo = ITEMS.register("oreo", () -> {
		return new ItemFoodUF("Oreo", 9, 1.0F, false);
	});
	public static RegistryObject<Item> caramel_toast = ITEMS.register("caramel_toast", () -> {
		return new ItemFoodUF("CaramelToast", 7, 0.6F, false).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> chocolate_toast = ITEMS.register("chocolate_toast", () -> {
		return new ItemFoodUF("ChocolateToast", 8, 0.6F, false);
	});
	public static RegistryObject<Item> sugar_toast = ITEMS.register("sugar_toast", () -> {
		return new ItemFoodUF("SugarToast", 3, 0.2F, false).setPotionEffect(Effects.SPEED, 5, 0);
	});
	public static RegistryObject<Item> sugar_pancake = ITEMS.register("sugar_pancake", () -> {
		return new ItemFoodUF("SugarPancake", 8, 0.6F, false).setPotionEffect(Effects.SPEED, 5, 0);
	});
	public static RegistryObject<Item> apple_jam_pancake = ITEMS.register("apple_jam_pancake", () -> {
		return new ItemFoodUF("AppleJamPancake", 15, 0.7F, false);
	});
	public static RegistryObject<Item> apple_jam_toast = ITEMS.register("apple_jam_toast", () -> {
		return new ItemFoodUF("AppleJamToast", 10, 0.7F, false);
	});
	public static RegistryObject<Item> apple_jam = ITEMS.register("apple_jam", () -> {
		return new ItemBowl("AppleJam", 8, 0.4F, false);
	});
	public static RegistryObject<Item> caramel_pancake = ITEMS.register("caramel_pancake", () -> {
		return new ItemFoodUF("CaramelPancake", 12, 0.6F, false).setPotionEffect(Effects.SPEED, 10, 0);
	});
	public static RegistryObject<Item> chocolate_pancake = ITEMS.register("chocolate_pancake", () -> {
		return new ItemFoodUF("ChocolatePancake", 13, 0.6F, false);
	});
	public static RegistryObject<Item> melon_jam_pancake = ITEMS.register("melon_jam_pancake", () -> {
		return new ItemFoodUF("MelonJamPancake", 13, 0.4F, false);
	});
	public static RegistryObject<Item> melon_jam_toast = ITEMS.register("melon_jam_toast", () -> {
		return new ItemFoodUF("MelonJamToast", 8, 0.4F, false);
	});
	public static RegistryObject<Item> melon_jam_biscuit = ITEMS.register("melon_jam_biscuit", () -> {
		return new ItemFoodUF("MelonJamBiscuit", 8, 0.4F, false);
	});
	public static RegistryObject<Item> melon_jam = ITEMS.register("melon_jam", () -> {
		return new ItemBowl("MelonJam", 6, 0.4F, false);
	});
	public static RegistryObject<Item> pancake_dough = ITEMS.register("pancake_dough", () -> {
		return new ItemFoodUF("PancakeDough", 5, 0.3F, false);
	});
	public static RegistryObject<Item> pancake = ITEMS.register("pancake", () -> {
		return new ItemFoodUF("Pancake", 6, 0.6F, false);
	});
	
	
	
	public static RegistryObject<Item> blue_sage_block = ITEMS.register("blue_sage_block", () -> {
		return new TallBlockItem(BlockRegistry.blue_sage_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> butterfly_weed_block = ITEMS.register("butterfly_weed_block", () -> {
		return new TallBlockItem(BlockRegistry.butterfly_weed_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> fuchsia_block = ITEMS.register("fuchsia_block", () -> {
		return new TallBlockItem(BlockRegistry.fuchsia_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> golden_shower_block = ITEMS.register("golden_shower_block", () -> {
		return new TallBlockItem(BlockRegistry.golden_shower_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> hortensia_block = ITEMS.register("hortensia_block", () -> {
		return new TallBlockItem(BlockRegistry.hortensia_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> larkspur_block = ITEMS.register("larkspur_block", () -> {
		return new TallBlockItem(BlockRegistry.larkspur_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> mountain_laurel_block = ITEMS.register("mountain_laurel_block", () -> {
		return new TallBlockItem(BlockRegistry.mountain_laurel_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> purple_hibiscus_block = ITEMS.register("purple_hibiscus_block", () -> {
		return new TallBlockItem(BlockRegistry.purple_hibiscus_block.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	
	public static RegistryObject<Item> starfish_orange = ITEMS.register("starfish_orange", () -> {
		return new TallBlockItem(BlockRegistry.starfish_orange.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> starfish_red = ITEMS.register("starfish_red", () -> {
		return new TallBlockItem(BlockRegistry.starfish_red.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
	public static RegistryObject<Item> starfish_yellow = ITEMS.register("starfish_yellow", () -> {
		return new TallBlockItem(BlockRegistry.starfish_yellow.get(), 
				(new Item.Properties()).group(ModGroup.itemGroup));
	});
}
