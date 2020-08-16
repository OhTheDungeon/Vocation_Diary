package cn.shadow.vacation_diary.block.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.dimension.DimensionsEventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ITeleporter;
import java.util.function.Function;


public class VacationPortal extends NetherPortalBlock {

	public VacationPortal(Block.Properties properties) {
		super(properties);
	}
	
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		
	}
	
    private static boolean canEntityTeleport(Entity entity)
    {
        // use vanilla portal logic from BlockPortal#onEntityCollision
        return !entity.isPassenger() && !entity.isBeingRidden() && entity.isNonBoss();
    }

	
	private void changeDimension(ServerPlayerEntity entity, DimensionType dimension) {
		ServerWorld world = entity.getServer().getWorld(dimension);
		BlockPos blockpos = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, world.getSpawnPoint());
		blockpos = new BlockPos(blockpos.getX(), 255, blockpos.getZ());
		while(blockpos.getY() > 0 && world.getBlockState(blockpos).getBlock() == Blocks.AIR) blockpos = blockpos.add(0, -1, 0);
//		VacationDiary.instance.reportMessage(blockpos.toString());
		
		final BlockPos pos = new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
		
		List<Entity> entities = new ArrayList<>();
		entities.add(entity);
        entities.removeIf(e -> !canEntityTeleport(e));
        if (entities.isEmpty()) return;

        entities.stream().filter(e -> e.dimension != dimension).forEach(e ->  e.changeDimension(dimension, new ITeleporter()
        {
            @Override
            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
            {
                Entity repositionedEntity = repositionEntity.apply(false);
                repositionedEntity.setPositionAndUpdate(pos.getX(), pos.getY() + 2, pos.getZ());
                return repositionedEntity;
            }
        }));
	}
	
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if(worldIn.isRemote) return;
		if(!(entityIn instanceof ServerPlayerEntity)) return;
//		VacationDiary.instance.reportMessage("玩家接触传送门");
		World vacation = DimensionManager.getWorld(entityIn.getServer(), 
				DimensionsEventHandler.CITY_DIMENSION_TYPE, 
				true, true);
		if(worldIn.getDimension().getType().getId() == vacation.getDimension().getType().getId()) {
			changeDimension((ServerPlayerEntity)entityIn, DimensionType.OVERWORLD);
		} else {
			changeDimension((ServerPlayerEntity)entityIn, DimensionsEventHandler.CITY_DIMENSION_TYPE);
		}
	}
	
	   @Nullable
	   public Size isVocationPortal(IWorld worldIn, BlockPos pos) {
		   Size netherportalblock$size = new Size(worldIn, pos, Direction.Axis.X);
	      if (netherportalblock$size.isValid() && netherportalblock$size.portalBlockCount == 0) {
	         return netherportalblock$size;
	      } else {
	    	  Size netherportalblock$size1 = new Size(worldIn, pos, Direction.Axis.Z);
	         return netherportalblock$size1.isValid() && netherportalblock$size1.portalBlockCount == 0 ? netherportalblock$size1 : null;
	      }
	   }
	
	public boolean trySpawnPortal(IWorld worldIn, BlockPos pos) {
		Size netherportalblock$size = this.isVocationPortal(worldIn, pos);
		if (netherportalblock$size != null) {
			netherportalblock$size.placePortalBlocks();
			return true;
		} else {
			return false;
		}
	}
	
	private final static boolean isVacationPortalFrame(BlockState state)
    {
        return state.getBlock() == Blocks.CHISELED_STONE_BRICKS;
    }
	
	
	   public static class Size {
		      private final IWorld world;
		      private final Direction.Axis axis;
		      private final Direction rightDir;
		      private final Direction leftDir;
		      private int portalBlockCount;
		      @Nullable
		      private BlockPos bottomLeft;
		      private int height;
		      private int width;

		      public Size(IWorld worldIn, BlockPos pos, Direction.Axis axisIn) {
		         this.world = worldIn;
		         this.axis = axisIn;
		         if (axisIn == Direction.Axis.X) {
		            this.leftDir = Direction.EAST;
		            this.rightDir = Direction.WEST;
		         } else {
		            this.leftDir = Direction.NORTH;
		            this.rightDir = Direction.SOUTH;
		         }

		         for(BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && this.func_196900_a(worldIn.getBlockState(pos.down())); pos = pos.down()) {
		            ;
		         }

		         int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
		         if (i >= 0) {
		            this.bottomLeft = pos.offset(this.leftDir, i);
		            this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
		            if (this.width < 2 || this.width > 21) {
		               this.bottomLeft = null;
		               this.width = 0;
		            }
		         }

		         if (this.bottomLeft != null) {
		            this.height = this.calculatePortalHeight();
		         }

		      }

		      protected int getDistanceUntilEdge(BlockPos pos, Direction directionIn) {
		         int i;
		         for(i = 0; i < 22; ++i) {
		            BlockPos blockpos = pos.offset(directionIn, i);
		            if (!this.func_196900_a(this.world.getBlockState(blockpos)) || !isVacationPortalFrame(this.world.getBlockState(blockpos.down()))) {
		               break;
		            }
		         }

		         BlockPos framePos = pos.offset(directionIn, i);
		         return isVacationPortalFrame(this.world.getBlockState(framePos)) ? i : 0;
		      }

		      public int getHeight() {
		         return this.height;
		      }

		      public int getWidth() {
		         return this.width;
		      }

		      protected int calculatePortalHeight() {
		         label56:
		         for(this.height = 0; this.height < 21; ++this.height) {
		            for(int i = 0; i < this.width; ++i) {
		               BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
		               BlockState blockstate = this.world.getBlockState(blockpos);
		               if (!this.func_196900_a(blockstate)) {
		                  break label56;
		               }

		               Block block = blockstate.getBlock();
		               if (block == BlockRegistry.vacation_portal.get()) {
		                  ++this.portalBlockCount;
		               }

		               if (i == 0) {
		                  BlockPos framePos = blockpos.offset(this.leftDir);
		                  if (!isVacationPortalFrame(this.world.getBlockState(framePos))) {
		                     break label56;
		                  }
		               } else if (i == this.width - 1) {
		                  BlockPos framePos = blockpos.offset(this.rightDir);
		                  if (!isVacationPortalFrame(this.world.getBlockState(framePos))) {
		                     break label56;
		                  }
		               }
		            }
		         }

		         for(int j = 0; j < this.width; ++j) {
		            BlockPos framePos = this.bottomLeft.offset(this.rightDir, j).up(this.height);
		            if (!isVacationPortalFrame(this.world.getBlockState(framePos))) {
		               this.height = 0;
		               break;
		            }
		         }

		         if (this.height <= 21 && this.height >= 3) {
		            return this.height;
		         } else {
		            this.bottomLeft = null;
		            this.width = 0;
		            this.height = 0;
		            return 0;
		         }
		      }

		      @SuppressWarnings("deprecation")
			protected boolean func_196900_a(BlockState pos) {
		         Block block = pos.getBlock();
		         return pos.isAir() || block == Blocks.FIRE || block == BlockRegistry.vacation_portal.get();
		      }

		      public boolean isValid() {
		         return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		      }

		      public void placePortalBlocks() {
		         for(int i = 0; i < this.width; ++i) {
		            BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

		            for(int j = 0; j < this.height; ++j) {
		               this.world.setBlockState(blockpos.up(j), BlockRegistry.vacation_portal.get().getDefaultState().with(NetherPortalBlock.AXIS, this.axis), 18);
		            }
		         }

		      }

		      private boolean func_196899_f() {
		         return this.portalBlockCount >= this.width * this.height;
		      }

		      public boolean func_208508_f() {
		         return this.isValid() && this.func_196899_f();
		      }
		   }
}
