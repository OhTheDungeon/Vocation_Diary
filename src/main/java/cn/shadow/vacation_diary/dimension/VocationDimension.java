package cn.shadow.vacation_diary.dimension;


import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator.WorldStyle;
import cn.shadow.vacation_diary.dimension.support.WorldEnvironment;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;


public class VocationDimension extends OverworldDimension {
    public VocationDimension(World world, DimensionType dimensionType) {
        super(world, dimensionType);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
    	VocationCityGenerationSettings settings = new VocationCityGenerationSettings(WorldStyle.NORMAL, WorldEnvironment.NORMAL);
        BiomeProvider biomeProvider = new OverworldBiomeProvider(new OverworldBiomeProviderSettings(world.getWorldInfo()));
        return new VocationCityWorldGenerator(world, biomeProvider, settings);
    }
    
    @Override
    public void setWorldTime(long time) {
        if (time == 24000L && this.world.getWorldInfo() instanceof DerivedWorldInfo) {
        	DerivedWorldInfo world = ((DerivedWorldInfo) this.world.getWorldInfo());
        	WorldInfo worldinfo = (WorldInfo) ObfuscationReflectionHelper
        			.getPrivateValue(DerivedWorldInfo.class, world, "delegate");
        	worldinfo.setDayTime(time);
        }
        
        super.setWorldTime(time);
    }

}
