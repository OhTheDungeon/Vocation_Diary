package cn.shadow.vacation_diary.dimension.feature;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.dimension.feature.tree.NormalTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry {
    @SuppressWarnings("deprecation")
	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(
    		ForgeRegistries.FEATURES, VacationDiary.MOD_ID);
    public static RegistryObject<Feature<?>> semiliquidambar_tree = FEATURES.register("semiliquidambar_tree", () -> {
    	return new NormalTreeFeature();
    });
}
