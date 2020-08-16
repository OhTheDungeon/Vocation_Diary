package cn.shadow.vacation_diary.dimension;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator.WorldStyle;
import cn.shadow.vacation_diary.dimension.support.WorldEnvironment;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.ChunkGenerator;

public class VocationWorldType extends WorldType {
    public VocationWorldType() {
        super("vocation");
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator(World world) {
    	VocationCityGenerationSettings settings = new VocationCityGenerationSettings(WorldStyle.NORMAL, WorldEnvironment.NORMAL);
        BiomeProvider biomeProvider = new OverworldBiomeProvider(new OverworldBiomeProviderSettings(world.getWorldInfo()));
        return new VocationCityWorldGenerator(world, biomeProvider, settings);
    }
}
