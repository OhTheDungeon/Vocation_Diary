package cn.shadow.vacation_diary.dimension;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator.WorldStyle;
import cn.shadow.vacation_diary.dimension.support.WorldEnvironment;
import net.minecraft.world.gen.GenerationSettings;

public class VocationCityGenerationSettings extends GenerationSettings {
	private WorldStyle worldStyle;
	private WorldEnvironment worldType;
	
	public VocationCityGenerationSettings(WorldStyle worldStyle, WorldEnvironment worldType) {
		super();
		this.worldStyle = worldStyle;
		this.worldType = worldType;
	}
	
	public WorldStyle getWorldStyle() {
		return this.worldStyle;
	}
	
	public WorldEnvironment getWorldType() {
		return this.worldType;
	}
}
