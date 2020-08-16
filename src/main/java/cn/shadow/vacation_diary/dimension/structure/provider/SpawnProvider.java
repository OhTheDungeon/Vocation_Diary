package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.AbstractEntityList;
import cn.shadow.vacation_diary.dimension.support.AnimalList;
import cn.shadow.vacation_diary.dimension.support.BaseBlock;
import cn.shadow.vacation_diary.dimension.support.BeingList;
import cn.shadow.vacation_diary.dimension.support.EntityHelper;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.SeaAnimalList;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.spawner.AbstractSpawner;

public class SpawnProvider extends Provider {

	private final static String tagEntities_Goodies = "Entities_For_Goodies";
	private final AbstractEntityList itemsEntities_Goodies = createBeingList(tagEntities_Goodies, EntityType.VILLAGER,
			EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER,
			EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER, EntityType.WITCH);

	private final static String tagEntities_Baddies = "Entities_For_Baddies";
	private final AbstractEntityList itemsEntities_Baddies = createBeingList(tagEntities_Baddies, EntityType.CREEPER,
			EntityType.CREEPER, EntityType.CREEPER, EntityType.CREEPER, EntityType.SKELETON, EntityType.SKELETON,
			EntityType.SKELETON, EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE,
			EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.SPIDER,
			EntityType.SPIDER, EntityType.SPIDER, EntityType.WITCH, EntityType.WITCH, EntityType.ZOMBIE_PIGMAN,
			EntityType.ENDERMAN, EntityType.PHANTOM, EntityType.BLAZE);

	private final static String tagEntities_Animals = "Entities_For_Animals";
	private final AbstractEntityList itemsEntities_Animals = createAnimalList(tagEntities_Animals, EntityType.HORSE,
			EntityType.HORSE, EntityType.DONKEY, EntityType.LLAMA, EntityType.COW, EntityType.COW, EntityType.SHEEP,
			EntityType.SHEEP, EntityType.PIG, EntityType.PIG, EntityType.PARROT, EntityType.PARROT, EntityType.CHICKEN,
			EntityType.CHICKEN, EntityType.CHICKEN, EntityType.CHICKEN, EntityType.CHICKEN, EntityType.CHICKEN,
			EntityType.RABBIT, EntityType.RABBIT, EntityType.RABBIT, EntityType.RABBIT, EntityType.WOLF,
			EntityType.OCELOT, EntityType.CAT, EntityType.FOX);

	private final static String tagEntities_SeaAnimals = "Entities_For_SeaAnimals";
	private final AbstractEntityList itemsEntities_SeaAnimals = createSeaAnimalList(tagEntities_SeaAnimals,
			EntityType.TROPICAL_FISH, EntityType.TROPICAL_FISH, EntityType.TROPICAL_FISH, EntityType.TROPICAL_FISH,
			EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.PUFFERFISH, EntityType.PUFFERFISH,
			EntityType.SALMON, EntityType.SALMON, EntityType.SALMON, EntityType.DOLPHIN, EntityType.DOLPHIN,
//			EntityType.GUARDIAN,
//			EntityType.ELDER_GUARDIAN,
			EntityType.SQUID, EntityType.SQUID, EntityType.COD, EntityType.COD, EntityType.COD);

	private final static String tagEntities_Vagrants = "Entities_For_Vagrants";
	private final AbstractEntityList itemsEntities_Vagrants = createBeingList(tagEntities_Vagrants, EntityType.CHICKEN,
			EntityType.CHICKEN, EntityType.RABBIT, EntityType.RABBIT, EntityType.WOLF, EntityType.WOLF, EntityType.FOX,
			EntityType.OCELOT, EntityType.CAT, EntityType.HORSE, EntityType.DONKEY, EntityType.LLAMA,
			EntityType.VILLAGER, EntityType.VILLAGER, EntityType.PARROT,
//			EntityType.POLAR_BEAR,
			EntityType.SPIDER, EntityType.CREEPER);

	private final static String tagEntities_Sewers = "Entities_For_Sewers";
	public final AbstractEntityList itemsEntities_Sewers = createBeingList(tagEntities_Sewers, EntityType.ZOMBIE,
			EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SPIDER, EntityType.BAT, EntityType.BAT, EntityType.BAT,
			EntityType.BAT);

	private final static String tagEntities_Mine = "Entities_For_Mine";
	public final AbstractEntityList itemsEntities_Mine = createBeingList(tagEntities_Mine, EntityType.ZOMBIE,
			EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SKELETON, EntityType.CREEPER, EntityType.CREEPER,
			EntityType.CAVE_SPIDER, EntityType.CAVE_SPIDER, EntityType.BAT, EntityType.BAT, EntityType.BAT,
			EntityType.BAT, EntityType.ENDERMITE, EntityType.CAT);

	private final static String tagEntities_Bunker = "Entities_For_Bunker";
	public final AbstractEntityList itemsEntities_Bunker = createBeingList(tagEntities_Bunker, EntityType.ZOMBIE_PIGMAN,
			EntityType.ENDERMAN, EntityType.EVOKER, EntityType.ILLUSIONER, EntityType.BAT, EntityType.BAT,
			EntityType.BLAZE);

	private final static String tagEntities_WaterPit = "Entities_For_WaterPit";
	public final AbstractEntityList itemsEntities_WaterPit = createBeingList(tagEntities_WaterPit, EntityType.SQUID,
			EntityType.SQUID, EntityType.SQUID, EntityType.SQUID, EntityType.SQUID, EntityType.SQUID, EntityType.SQUID,
			EntityType.SQUID, EntityType.GUARDIAN);

	private final static String tagEntities_LavaPit = "Entities_For_LavaPit";
	public final AbstractEntityList itemsEntities_LavaPit = createBeingList(tagEntities_LavaPit, EntityType.BLAZE,
			EntityType.WITHER, EntityType.MAGMA_CUBE, EntityType.SHULKER);
	
	private List<AbstractEntityList> listOfLists;
	
	public static class SpawnListNode {
		public final BlockPos blockPos;
		public final EntityType<?> entity;
		
		public SpawnListNode(BlockPos blockPos, EntityType<?> entity) {
			this.blockPos = blockPos;
			this.entity = entity;
		}
	}
	
	private Hashtable<VocationCityWorldGenerator, Hashtable<ChunkPos, List<SpawnListNode>>> spawnList;

	public SpawnProvider(VocationCityWorldGenerator generator) {
		spawnList = new Hashtable<>();
	}
	
	public List<SpawnListNode> getSpawnListInChunk(VocationCityWorldGenerator generator, ChunkPos chunkPos) {
		if(!spawnList.containsKey(generator)) return new ArrayList<>();
		Map<ChunkPos, List<SpawnListNode>> map = spawnList.get(generator);
		
		if(!map.containsKey(chunkPos)) return new ArrayList<>();
		return map.get(chunkPos);
	}
	
	public boolean removeChunkFromSpawnList(VocationCityWorldGenerator generator, ChunkPos chunkPos) {
		if(!spawnList.containsKey(generator)) return false;
		Map<ChunkPos, List<SpawnListNode>> map = spawnList.get(generator);
		if(!map.containsKey(chunkPos)) return false;
		map.remove(chunkPos);
		return true;
	}
	
	private void addToSpawnList(VocationCityWorldGenerator generator, BlockPos blockPos, EntityType<?> entity) {
		if(!spawnList.containsKey(generator)) {
			spawnList.put(generator, new Hashtable<>());
		}
		Map<ChunkPos, List<SpawnListNode>> map = spawnList.get(generator);
		
		int chunkX = blockPos.getX() >> 4;
		int chunkZ = blockPos.getZ() >> 4;
		
		ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
		
		if(!map.containsKey(chunkPos)) {
			map.put(chunkPos, new ArrayList<>());
		}
		
		List<SpawnListNode> list = map.get(chunkPos);
		list.add(new SpawnListNode(blockPos, entity));
	}

	public final void spawnAnimals(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z) {
		spawnAnimals(generator, blocks, odds, x, y, z, itemsEntities_Animals.getRandomEntity(odds));
	}

	public final void spawnAnimals(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType<?> entity) {
		if (!generator.getWorldSettings().includeDecayedBuildings) {
			int herdSize = itemsEntities_Animals.getHerdSize(odds, entity);
			if (herdSize > 0)
				spawnAnimal(generator, blocks, odds, x, y, z, entity);
			if (herdSize > 1)
				spawnAnimal(generator, blocks, odds, x + 1, y, z, entity);
			if (herdSize > 2)
				spawnAnimal(generator, blocks, odds, x, y, z + 1, entity);
			if (herdSize > 3)
				spawnAnimal(generator, blocks, odds, x + 1, y, z + 1, entity);
			if (herdSize > 4)
				spawnAnimal(generator, blocks, odds, x - 1, y, z, entity);
			if (herdSize > 5)
				spawnAnimal(generator, blocks, odds, x, y, z - 1, entity);
		}
	}

	public void spawnAnimal(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType<?> entity) {
		if (odds.playOdds(generator.getWorldSettings().spawnAnimals) && blocks.insideXYZ(x, y, z))
			spawnEntity(generator, blocks, odds, x, y, z, entity, false, true);
	}

	public final void spawnSeaAnimals(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z) {
		spawnSeaAnimals(generator, blocks, odds, x, y, z, itemsEntities_SeaAnimals.getRandomEntity(odds));
	}

	private void spawnSeaAnimals(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, EntityType<?> entity) {
		if (!generator.getWorldSettings().includeDecayedBuildings) {
			int herdSize = itemsEntities_SeaAnimals.getHerdSize(odds, entity);
			if (herdSize > 0)
				spawnSeaAnimal(generator, blocks, odds, x, y, z, entity);
			if (herdSize > 1)
				spawnSeaAnimal(generator, blocks, odds, x + 1, y, z, entity);
			if (herdSize > 2)
				spawnSeaAnimal(generator, blocks, odds, x, y, z + 1, entity);
			if (herdSize > 3)
				spawnSeaAnimal(generator, blocks, odds, x + 1, y, z + 1, entity);
			if (herdSize > 4)
				spawnSeaAnimal(generator, blocks, odds, x - 1, y, z, entity);
			if (herdSize > 5)
				spawnSeaAnimal(generator, blocks, odds, x, y, z - 1, entity);
		}
	}

	private void spawnSeaAnimal(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, EntityType<?> entity) {
		if (odds.playOdds(generator.getWorldSettings().spawnAnimals) && blocks.insideXYZ(x, y, z)) {
			if (blocks.isWater(x, y - 1, z) && blocks.isWater(x, y, z)) {
				spawnEntity(generator, blocks, odds, x, y, z, entity, true, false);
			}
		}
	}

	public final void spawnBeing(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z) {
		spawnBeing(generator, blocks, odds, x, y, z, itemsEntities_Goodies.getRandomEntity(odds),
				itemsEntities_Baddies.getRandomEntity(odds));
	}

	public final void spawnBeings(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z) {
		EntityType<?> goodies = itemsEntities_Goodies.getRandomEntity(odds);
		EntityType<?> baddies = itemsEntities_Baddies.getRandomEntity(odds);
		int count = odds.getRandomInt(1, 3); // shimmy
		for (int i = 0; i < count; i++)
			spawnBeing(generator, blocks, odds, blocks.clampXZ(x + i), y, blocks.clampXZ(z + i), goodies, baddies);
	}

	public final void spawnBeing(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType<?> goody, EntityType<?> baddy) {
		if (odds.playOdds(generator.getWorldSettings().spawnBeings))
			spawnGoodOrBad(generator, blocks, odds, x, y, z, goody, baddy);
	}

	public final void spawnVagrants(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z) {
		EntityType<?> goodies = itemsEntities_Vagrants.getRandomEntity(odds);
		EntityType<?> baddies = itemsEntities_Baddies.getRandomEntity(odds);
		int count = odds.getRandomInt(1, 3); // shimmy
		for (int i = 0; i < count; i++)
			spawnVagrant(generator, blocks, odds, blocks.clampXZ(x + i), y, blocks.clampXZ(z + i), goodies, baddies);
	}

	public final void spawnVagrant(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType<?> goody, EntityType<?> baddy) {
		if (odds.playOdds(generator.getWorldSettings().spawnVagrants))
			spawnGoodOrBad(generator, blocks, odds, x, y, z, goody, baddy);
	}

	private void spawnGoodOrBad(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, EntityType<?> goody, EntityType<?> baddy) {
		if (!blocks.isEmpty(x, y, z) && !blocks.isEmpty(x, y - 1, z))
			if (generator.getWorldSettings().includeDecayedBuildings || odds.playOdds(generator.getWorldSettings().spawnBaddies))
				spawnEntity(generator, blocks, odds, x, y, z, baddy, false, true);
			else
				spawnEntity(generator, blocks, odds, x, y, z, goody, false, true);
	}

	private void spawnEntity(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType<?> entity, boolean ignoreFlood, boolean ensureSpace) {
		if (blocks.insideXYZ(x, y, z) && entity != null && EntityHelper.isAlive(entity)) {

			// make sure we have room
			if (ensureSpace) {
				boolean foundSpot = false;
				int countDown = 10;
				while (countDown > 0) {
					int emptyY = blocks.findFirstEmptyAbove(x, y, z, y + 3);
					if (blocks.isEmpty(x, y, z) && blocks.isEmpty(x, y + 1, z)) {
						y = emptyY;
						foundSpot = true;
						break;
					} else
						countDown--;

					// shimmy
					x = blocks.clampXZ(x + odds.calcRandomRange(-2, 2));
					z = blocks.clampXZ(z + odds.calcRandomRange(-2, 2));
				}
				if (!foundSpot)
					return;
//				y = blocks.findFirstEmptyAbove(x, y, z, y + 3);
			}
			BlockPos at = blocks.getBlockLocation(x, y, z);

			// ignore flood level or make sure that we are above it
//			blocks.setBlock(x, y + 10, z, Material.STONE);
			if (ignoreFlood || y > generator.shapeProvider.findFloodY(generator, at.getX(), at.getZ())) {
				// make sure there is a clear space
				if (ensureSpace)
					blocks.clearBlocks(x, y, y + 2, z);

				// 添加到生成列表
				this.addToSpawnList(generator, at, entity);
			}
		}
	}

	public final void setSpawnOrSpawner(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, boolean doSpawner, AbstractEntityList entities) {
		EntityType<?> entity = entities.getRandomEntity(odds);
		if (doSpawner)
			setSpawner(generator, blocks, odds, x, y, z, entity);
		else
			spawnEntity(generator, blocks, odds, x, y, z, entity, false, true);
	}

	public final void setSpawner(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			AbstractEntityList list) {
		setSpawner(generator, blocks, odds, x, y, z, list.getRandomEntity(odds));
	}

	private void setSpawner(VocationCityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType<?> entity) {
		if(EntityHelper.isAlive(entity) && odds.playOdds(generator.getWorldSettings().spawnBaddies)) {
			BaseBlock block = blocks.getActualBlock(x, y, z);
			block.setType(Blocks.SPAWNER, true);
			
			try {
				TileEntity tileEntity = block.getTileEntity();
	            AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileEntity).getSpawnerBaseLogic();
	            abstractspawner.setEntityType(entity);
			} catch (Exception ex) {
				
			}
 		}
	}

	private AbstractEntityList createList(AbstractEntityList list) {

		// add it to the big list so we can generically remember it
		if (listOfLists == null)
			listOfLists = new ArrayList<>();
		listOfLists.add(list);

		// return it so we can specifically remember it
		return list;
	}

	private AbstractEntityList createBeingList(String name, EntityType<?>... entities) {
		return createList(new BeingList(name, entities));
	}

	private AbstractEntityList createAnimalList(String name, EntityType<?>... entities) {
		return createList(new AnimalList(name, entities));
	}

	private AbstractEntityList createSeaAnimalList(String name, EntityType<?>... entities) {
		return createList(new SeaAnimalList(name, entities));
	}
}
