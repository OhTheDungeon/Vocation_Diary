package cn.shadow.vacation_diary.dimension;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DimensionsEventHandler {
    public static final ResourceLocation CITY_DIMENSION_ID = new ResourceLocation("vacation_diary", "city");
    public static DimensionType CITY_DIMENSION_TYPE;
    
    @SubscribeEvent
    public static void onDimensionsRegistry(RegisterDimensionsEvent event) {
        if (DimensionType.byName(CITY_DIMENSION_ID) == null) {
            CITY_DIMENSION_TYPE = DimensionManager.registerDimension(CITY_DIMENSION_ID, ModDimensionRegistry.vacationModDimension.get(), null, true);
        } else {
        	CITY_DIMENSION_TYPE = DimensionType.byName(CITY_DIMENSION_ID);
        }
    }
}
