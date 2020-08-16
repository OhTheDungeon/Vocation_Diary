package cn.shadow.vacation_diary;


import cn.shadow.vacation_diary.block.BlockRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockRegistry.tea_sapling.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.tea_leaves.get(), RenderType.getCutoutMipped());
        
        RenderTypeLookup.setRenderLayer(BlockRegistry.green_tea_plant.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.black_tea_plant.get(), RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(BlockRegistry.blue_sage_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.butterfly_weed_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.fuchsia_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.golden_shower_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.hortensia_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.larkspur_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.mountain_laurel_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.purple_hibiscus_block.get(), RenderType.getCutout());
    }
}
