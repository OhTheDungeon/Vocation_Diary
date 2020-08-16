package cn.shadow.vacation_diary.dimension.feature.tree;

import java.util.Random;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class NormalTree extends Tree {

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		Feature<TreeFeatureConfig> tree = new NormalTreeFeature();
		return tree.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG);
	}
}