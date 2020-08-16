package cn.shadow.vacation_diary;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.dimension.VocationWorldType;
import cn.shadow.vacation_diary.dimension.ModDimensionRegistry;
import cn.shadow.vacation_diary.dimension.feature.FeatureRegistry;
import cn.shadow.vacation_diary.item.ItemRegistry;

@Mod(value = VacationDiary.MOD_ID)
public class VacationDiary
{
    public static final String MOD_ID = "vacation_diary";
    public static final VocationWorldType obsidianWorldType = new VocationWorldType();

    public static VacationDiary instance;
    @SuppressWarnings("deprecation")
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static Logger logger = LogManager.getLogger(MOD_ID);
    
    public VacationDiary() {
    	instance = this;
    	ModDimensionRegistry.MOD_DIMENSION.register(FMLJavaModLoadingContext.get().getModEventBus());
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
    	ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());  
    	BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    	FeatureRegistry.FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    
    public void reportMessage(String msg) {
    	VacationDiary.logger.info(msg);
    }
    
    public void reportException(String message, Exception e) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	
    	reportMessage(message);
    	reportMessage(sw.toString());
    }
    
    private void loadComplete(final FMLLoadCompleteEvent event) // PostRegistrationEven
    {
    	proxy.init();
    }
        
}