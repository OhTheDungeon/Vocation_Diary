package cn.shadow.vacation_diary.block;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.block.blocks.CitySaplingBlock;
import cn.shadow.vacation_diary.block.blocks.MagicCake;
import cn.shadow.vacation_diary.block.blocks.SpecialCake;
import cn.shadow.vacation_diary.block.blocks.StarFish_Orange;
import cn.shadow.vacation_diary.block.blocks.StarFish_Red;
import cn.shadow.vacation_diary.block.blocks.StarFish_Yellow;
import cn.shadow.vacation_diary.block.blocks.TeaLeavesBlock;
import cn.shadow.vacation_diary.block.blocks.VacationPortal;
import cn.shadow.vacation_diary.dimension.feature.tree.NormalTree;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    @SuppressWarnings("deprecation")
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(
    		ForgeRegistries.BLOCKS, VacationDiary.MOD_ID);
    public static RegistryObject<Block> tea_sapling = BLOCKS.register("tea_sapling", () -> {
    	Block block = new CitySaplingBlock(
				new NormalTree(), 
				Block.Properties.create(Material.PLANTS)
				.doesNotBlockMovement().tickRandomly()
				.hardnessAndResistance(0.0F)
				.sound(SoundType.PLANT)
		);
    	return block;
    });
    
    public static RegistryObject<Block> tea_leaves = BLOCKS.register("tea_leaves", () -> {
    	return new TeaLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid());
    });
    
    public static RegistryObject<Block> green_tea_plant = BLOCKS.register("green_tea_plant", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    
    public static RegistryObject<Block> black_tea_plant = BLOCKS.register("black_tea_plant", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    
    public static RegistryObject<Block> vacation_portal = BLOCKS.register("vacation_portal", () -> {
    	return new VacationPortal(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).lightValue(11).noDrops());
    });
    
    
    
    
    public static RegistryObject<Block> apple_cake = BLOCKS.register("apple_cake", () -> {
    	return new SpecialCake("applecake", 18, 0.6F, Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH));
    });
    public static RegistryObject<Block> chocolate_cake = BLOCKS.register("chocolate_cake", () -> {
    	return new SpecialCake("chocolatecake", 12, 0.5F, Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH));
    });
    public static RegistryObject<Block> magic_cake = BLOCKS.register("magic_cake", () -> {
    	return new MagicCake("magiccake", 48, 0.5F, Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH));
    });
    public static RegistryObject<Block> caramel_cake = BLOCKS.register("caramel_cake", () -> {
    	return new SpecialCake("caramelcake", 19, 0.8F, Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH));
    });
    
    
    public static RegistryObject<Block> blue_sage_block = BLOCKS.register("blue_sage_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> butterfly_weed_block = BLOCKS.register("butterfly_weed_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> fuchsia_block = BLOCKS.register("fuchsia_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> golden_shower_block = BLOCKS.register("golden_shower_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> hortensia_block = BLOCKS.register("hortensia_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> larkspur_block = BLOCKS.register("larkspur_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> mountain_laurel_block = BLOCKS.register("mountain_laurel_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    public static RegistryObject<Block> purple_hibiscus_block = BLOCKS.register("purple_hibiscus_block", () -> {
    	return new TallFlowerBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    });
    
    public static RegistryObject<Block> starfish_orange = BLOCKS.register("starfish_orange", () -> {
    	return new StarFish_Orange();
    });
    public static RegistryObject<Block> starfish_red = BLOCKS.register("starfish_red", () -> {
    	return new StarFish_Red();
    });
    public static RegistryObject<Block> starfish_yellow = BLOCKS.register("starfish_yellow", () -> {
    	return new StarFish_Yellow();
    });
    
    public static RegistryObject<Block> tea_log = BLOCKS.register("tea_log", () -> {
    	return new LogBlock(
    			MaterialColor.WOOD, 
    			Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN)
    			.hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    });
}
