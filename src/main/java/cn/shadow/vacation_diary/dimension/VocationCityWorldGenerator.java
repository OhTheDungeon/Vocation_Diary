package cn.shadow.vacation_diary.dimension;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.dimension.structure.provider.*;
import cn.shadow.vacation_diary.dimension.structure.provider.SpawnProvider.SpawnListNode;
import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.dimension.support.WorldBlocks;
import cn.shadow.vacation_diary.dimension.support.WorldEnvironment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.CatSpawner;

public class VocationCityWorldGenerator extends ChunkGenerator<GenerationSettings> {

	private final VacationDiary huaxiaWorld;
	private IWorld world;
	private long worldSeed;
	private Odds connectionKeyGen;

	public final String worldName;
	public WorldStyle worldStyle;
	public DimensionType dimType;
	public WorldEnvironment worldType;

	public ShapeProvider shapeProvider;
	public LootProvider lootProvider;
	public OreProvider oreProvider;
	public ThingProvider thingProvider;
	public SurfaceProvider surfaceProvider;
	public CoverProvider coverProvider;
	public OdonymProvider odonymProvider;
	public StructureInAirProvider structureInAirProvider;
	public StructureOnGroundProvider structureOnGroundProvider;
	public TreeProvider treeProvider;
	public SpawnProvider spawnProvider;

	private WorldBlocks decayBlocks;

	private VocationCityWorldSettings settings;

	public int streetLevel;

	public int deepseaLevel;
	public int seaLevel;
	public int structureLevel;
	public int treeLevel;
	public int evergreenLevel;
	public int deciduousRange;
	public int evergreenRange;
	public int height;
	public int snowLevel;
	public int landRange;
	private int seaRange;

	public long connectedKeyForPavedRoads;
	public long connectedKeyForParks;
	
	private CityWorldDecorate populator;

	public enum WorldStyle {
//		FLOATING, // very low terrain with floating houses and cities
//		FLOODED, // traditional terrain and cities but with raised sea level
//		SNOWDUNES, // traditional terrain and cities but covered with snow dunes
//		SANDDUNES, // traditional terrain and cities but covered with sand dunes
//		ASTRAL, // alien landscape
//		MAZE, // mazes with smaller cities
//		NATURE, // just nature, no constructs anywhere
//		METRO, // just buildings, no nature
//		SPARSE, // a world of cities but away from each other
		// PILLARS // floating with pillars holding everything up
		// LAVADUNES // volcanos everywhere
		// GLACERS // glacers everywhere
		// MOON, // lunar landscape with lunar bases
		// UNDERWATER, // traditional terrain with raised sea level with under water
		// cities
		// WESTERN, // desert landscape with sparse western styled towns and ranches
		// UNDERGROUND, // elevated terrain with underground cities
		// MINING, // elevated terrain with very shallow mines and very small towns
//		DESTROYED, // normal landscape with destroyed cities
		NORMAL
	} // traditional terrain and cities

	//目前版本都有效
	public static WorldStyle validateStyle(WorldStyle style) {
		return style;
	}

	private void checkVersion() {
		//TODO:世界版本检查
	}
	
	@SuppressWarnings("unused")
	private static class ChunkGenNode {
		public final ChunkPos chunkPos;
		public final PlatMap platMap;
		public final InitialBlocks initialBlocks;
		public LinearBiomeContainer biomes;
		
		public ChunkGenNode(ChunkPos chunkPos, PlatMap platMap, InitialBlocks initialBlocks) {
			this.chunkPos = chunkPos;
			this.platMap = platMap;
			this.initialBlocks = initialBlocks;
		}
	}
	
	private Hashtable<Long, ChunkGenNode> platmaps;
	
	public void removeChunkGenNode(int chunkX, int chunkZ) {
		int platX = calcOrigin(chunkX);
		int platZ = calcOrigin(chunkZ);

		// calculate the plat's key
		Long platkey = ((long) platX * (long) Integer.MAX_VALUE + (long) platZ);
		platmaps.remove(platkey);
	}

	public ChunkGenNode getChunkGenNode(int chunkX, int chunkZ, IChunk chunk) {

		// get the plat map collection
		if (platmaps == null)
			platmaps = new Hashtable<>();

		// find the origin for the plat
		int platX = calcOrigin(chunkX);
		int platZ = calcOrigin(chunkZ);

		// calculate the plat's key
		Long platkey = ((long) platX * (long) Integer.MAX_VALUE + (long) platZ);

		// get the right plat
		ChunkGenNode chunkGenNode = platmaps.get(platkey);

		// doesn't exist? then make it!
		if (chunkGenNode == null) {
			
//			if(chunk == null) {
//				throw new UnsupportedOperationException("Wrong generation stage");
//			}

			// what is the context for this one?
			ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
			PlatMap platmap = new PlatMap(this, shapeProvider, platX, platZ);
			InitialBlocks initialBlocks = new InitialBlocks(this, chunk, chunkX, chunkZ);
			chunkGenNode = new ChunkGenNode(chunkPos, platmap, initialBlocks);

			// remember it for quicker look up
			platmaps.put(platkey, chunkGenNode);
		}

		// finally return the plat
		return chunkGenNode;
	}

	
	@Override
	public final void generateStructures(BiomeManager biomes, IChunk chunk, ChunkGenerator<?> generator, TemplateManager templates) {
		// 不在这里生成, 建筑太多了,为方便考虑不算入结构
	}
	 
	@Override
	public final void generateStructureStarts(IWorld world, IChunk chunk) {
	}
	
    @Override
    public final void generateBiomes(IChunk chunk) {
    	ChunkPos chunkPos = chunk.getPos();
    	ChunkGenNode chunkGenNode = getChunkGenNode(chunkPos.x, chunkPos.z, chunk);
    	PlatMap platmap = chunkGenNode.platMap;
    	LinearBiomeContainer summer = new LinearBiomeContainer(chunkPos, new Biome[BiomeContainer.BIOMES_SIZE]);
    	chunkGenNode.biomes = summer;
    	platmap.generateBiome(chunkGenNode.initialBlocks, summer);
    	((ChunkPrimer)chunk).func_225548_a_(summer);
    }
    
    @Override
    public final void makeBase(IWorld world, IChunk chunk) {
    }

    @Override
    public final void decorate(WorldGenRegion region) {
    	int x = region.getMainChunkX();
    	int z = region.getMainChunkZ();
    	this.populator.decorate(this.random, region.getChunk(x, z), region);
    }

    @Override
    public final void generateSurface(WorldGenRegion region, IChunk chunk) {
        int chunkX = region.getMainChunkX();
        int chunkZ = region.getMainChunkZ();
        
        ChunkGenNode chunkGenNode = getChunkGenNode(chunkX, chunkZ, chunk);
        PlatMap platmap = chunkGenNode.platMap;
        if(platmap != null) {
        	InitialBlocks initialBlocks = new InitialBlocks(this, chunk, chunkX, chunkZ);
        	platmap.decorate(initialBlocks, chunkGenNode.biomes);
        }
        //removeChunkGenNode(chunkX, chunkZ);
    }
    
    @Override
    public final void func_225550_a_(BiomeManager biomes, IChunk chunk, GenerationStage.Carving type) {
    }

    @SuppressWarnings("deprecation")
	@Override
    public final void spawnMobs(WorldGenRegion region) {
    	ChunkPos chunkPos = new ChunkPos(region.getMainChunkX(), region.getMainChunkZ());
    	List<SpawnListNode> list = this.spawnProvider.getSpawnListInChunk(this, chunkPos);
    	
    	for(SpawnListNode node : list) {
    		try {
    			Entity entity = node.entity.create(region.getWorld());
    			
    			if(entity instanceof MobEntity) {
    				MobEntity mobentity = (MobEntity) entity;
    				mobentity.setLocationAndAngles(node.blockPos.getX(), node.blockPos.getY(), node.blockPos.getZ(), 0F, 0F);
    				ILivingEntityData ilivingentitydata = null;
    				ilivingentitydata = mobentity.onInitialSpawn(region, region.getDifficultyForLocation(new BlockPos(mobentity)), SpawnReason.CHUNK_GENERATION, ilivingentitydata, (CompoundNBT)null);
                    region.addEntity(mobentity);
    			}
    		} catch (Exception ex) {
    		}
    	}
    	
    	this.spawnProvider.removeChunkFromSpawnList(this, chunkPos);
    }

    private final CatSpawner catSpawner = new CatSpawner();
    @Override
    public final void spawnMobs(ServerWorld worldIn, boolean hostile, boolean peaceful) {
    	catSpawner.tick(worldIn, hostile, peaceful);
    }
    
    private final Random random;

	public VocationCityWorldGenerator(IWorld world, BiomeProvider provider, VocationCityGenerationSettings settings) {
		super(world, provider, settings);
		this.random = world.getRandom();
		this.huaxiaWorld = VacationDiary.instance;
		this.worldName = "DIM" + world.getDimension().getType().getId();
		VacationDiary.logger.info("HuaxiaWorld creating world '" + worldName + "'");
		this.worldStyle = WorldStyle.NORMAL;
		this.worldType = settings.getWorldType();

		checkVersion();

		// parse the style string
		if (worldStyle != null) {
			try {
				this.worldStyle = validateStyle(settings.getWorldStyle());
			} catch (IllegalArgumentException e) {
				reportMessage("[Generator] Unknown world style " + worldStyle + ", switching to NORMAL");
				this.worldStyle = WorldStyle.NORMAL;
			}
		}
		initializeWorldInfo(world);
	}

	public VacationDiary getModMain() {
		return huaxiaWorld;
	}

	public IWorld getWorld() {
		return world;
	}

	public Long getWorldSeed() {
		return worldSeed;
	}

	private int deltaSeed = 0;

	private Long getRelatedSeed() {
		deltaSeed++;
		return worldSeed + deltaSeed;
	}

	public void initializeWorldInfo(IWorld aWorld) {

		// initialize the shaping logic
		if (world == null) {
			world = aWorld;

			spawnProvider = new SpawnProvider(this);
			odonymProvider = OdonymProvider.loadProvider(this, new Odds(getRelatedSeed()));

			settings = VocationCityWorldSettings.loadSettings(this, aWorld);
			
			dimType = world.getDimension().getType();
			worldSeed = world.getSeed();
			connectionKeyGen = new Odds(getRelatedSeed());

			shapeProvider = ShapeProvider.loadProvider(this, new Odds(getRelatedSeed()));
			lootProvider = LootProvider.loadProvider(this);
			oreProvider = OreProvider.loadProvider(this);
			thingProvider = ThingProvider.loadProvider(this);
			coverProvider = CoverProvider.loadProvider(this, new Odds(getRelatedSeed()));
			surfaceProvider = SurfaceProvider.loadProvider(this, new Odds(getRelatedSeed()));
			structureOnGroundProvider = StructureOnGroundProvider.loadProvider(this);
			structureInAirProvider = StructureInAirProvider.loadProvider(this);
			treeProvider = TreeProvider.loadProvider(this, new Odds(getRelatedSeed()));

			decayBlocks = new WorldBlocks(this, new Odds(getRelatedSeed()));

			// get ranges and contexts
			height = shapeProvider.getWorldHeight();
			seaLevel = shapeProvider.getSeaLevel();
			landRange = shapeProvider.getLandRange();
			seaRange = shapeProvider.getSeaRange();
			structureLevel = shapeProvider.getStructureLevel();
			streetLevel = shapeProvider.getStreetLevel();

			// now the other vertical points
			deepseaLevel = seaLevel - seaRange / 3;
			snowLevel = seaLevel + (landRange / 4 * 3);
			evergreenLevel = seaLevel + (landRange / 4 * 2);
			treeLevel = seaLevel + (landRange / 4);
			deciduousRange = evergreenLevel - treeLevel;
			evergreenRange = snowLevel - evergreenLevel;
			
			populator = new CityWorldDecorate(this);

//				// seabed = 35 deepsea = 50 sea = 64 sidewalk = 65 tree = 110 evergreen = 156 snow = 202 top = 249
//				CityWorld.reportMessage("seabed = " + (seaLevel - seaRange) + 
//								        " deepsea = " + deepseaLevel + 
//								        " sea = " + seaLevel + 
//								        " sidewalk = " + sidewalkLevel + 
//								        " tree = " + treeLevel + 
//								        " evergreen = " + evergreenLevel + 
//								        " snow = " + snowLevel + 
//								        " top = " + (seaLevel + landRange));

			// get the connectionKeys
			connectedKeyForPavedRoads = connectionKeyGen.getRandomLong();
			connectedKeyForParks = connectionKeyGen.getRandomLong();

//			reportMessage("Plugins...");
//			PluginManager pm = Bukkit.getServer().getPluginManager();
//			Plugin[] plugins = pm.getPlugins();
//			for (Plugin plugin: plugins) {
//				reportMessage("Plugin = " + plugin.getName());
//			}
		}
	}


	public long getConnectionKey() {
		return connectionKeyGen.getRandomLong();
	}

	public int getFarBlockY(int blockX, int blockZ) {
		return shapeProvider.findBlockY(this, blockX, blockZ);
	}

//	@Override
//	public Location getFixedSpawnLocation(World world, Random random) {
//		
////		// guess a location
////		int spawnX = random.nextInt(spawnRadius * 2) - spawnRadius;
////		int spawnZ = random.nextInt(spawnRadius * 2) - spawnRadius;
////		
////		// find a general height
////		int spawnY = getFarBlockY(spawnX, spawnZ);
////		int maxY = world.getMaxHeight();
////		
////		// find the first empty block
////		while (spawnY < maxY) {
////			if (world.getBlockAt(spawnX, spawnY, spawnZ).isEmpty() && 
////				world.getBlockAt(spawnX, spawnY + 1, spawnZ).isEmpty())
////				return new Location(world, spawnX, spawnY, spawnZ);
////			
////			// little higher up then
////			spawnY++;
////		}
////		
////		// still nothing?
////		return new Location(world, spawnX, spawnY, spawnZ);
//		CityWorld.log.info("******* getFixedSpawnLocation = 0, 250, 0");
//		return new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
//	}

	// manager for handling the city plat maps collection

	// Supporting code used by getPlatMap
	private int calcOrigin(int i) {
		if (i >= 0) {
			return i / PlatMap.Width * PlatMap.Width;
		} else {
			return -((Math.abs(i + 1) / PlatMap.Width * PlatMap.Width) + PlatMap.Width);
		}
	}

	public void reportMessage(String message) {
		huaxiaWorld.reportMessage(message);
	}

//	public void reportFormatted(String format, Object... objects) {
//		huaxiaWorld.reportFormatted(format, objects);
//	}

	public void reportException(String message, Exception e) {
		huaxiaWorld.reportException(message, e);
	}

	public void reportLocation(String title, AbstractBlocks chunk) {
		reportLocation(title, chunk.getOriginX(), chunk.getOriginZ());
	}

	public void reportLocation(String title, int originX, int originZ) {
		if (getWorldSettings().broadcastSpecialPlaces)
			reportMessage(">> " + title + " placed near " + originX + ", " + originZ + ", in the world '" + worldName + "'");
	}

	public VocationCityWorldSettings getWorldSettings() {
		return settings;
	}

	public void destroyWithin(int x1, int x2, int y1, int y2, int z1, int z2, boolean withFire) {
		decayBlocks.destroyWithin(x1, x2, y1, y2, z1, z2, withFire && getWorldSettings().includeFires);
	}

	public void destroyWithin(int x1, int x2, int y1, int y2, int z1, int z2) {
		decayBlocks.destroyWithin(x1, x2, y1, y2, z1, z2, getWorldSettings().includeFires);
	}

	public void destroyArea(int x, int y, int z, int radius) {
		decayBlocks.destroyArea(x, y, z, radius, getWorldSettings().includeFires);
	}

	@Override
	public int getGroundHeight() {
	      return this.world.getSeaLevel() + 1;
	}

	@Override
	public int func_222529_a(int p_222529_1_, int p_222529_2_, Type heightmapType) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class CityWorldDecorate {

		private final VocationCityWorldGenerator chunkGen;

		CityWorldDecorate(VocationCityWorldGenerator chunkGen) {
			this.chunkGen = chunkGen;
		}

		public void decorate(Random random, IChunk chunk, WorldGenRegion region) {
			try {

				// where are we?
				int chunkX = chunk.getPos().x;
				int chunkZ = chunk.getPos().z;

				// place to work
				RealBlocks realChunk = new RealBlocks(chunkGen, chunk, region);
				realChunk.setDoPhysics(true);
				// figure out what everything looks like
				PlatMap platmap = chunkGen.getChunkGenNode(chunkX, chunkZ, chunk).platMap;
				if (platmap != null) {
					platmap.generateBlocks(realChunk);
					// finalize things
					chunkGen.lootProvider.saveLoots();

					// Originally by Sablednah
					// Moved and modified a bit by DaddyChurchill
					
					if(platmap.isFinished()) {
						removeChunkGenNode(chunkX, chunkZ);
					}
				}
			} catch (Exception e) {
				reportException("BlockPopulator FAILED", e);
			}
		}
	}


}
