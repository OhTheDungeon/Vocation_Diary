package cn.shadow.vacation_diary.dimension;

import cn.shadow.vacation_diary.VacationDiary;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModDimensionRegistry {
    @SuppressWarnings("deprecation")
	public static final DeferredRegister<ModDimension> MOD_DIMENSION = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, 
    		VacationDiary.MOD_ID);
    public static RegistryObject<VacationDimensions> vacationModDimension = MOD_DIMENSION.register("city", () -> {
        return new VacationDimensions();
    });
}
