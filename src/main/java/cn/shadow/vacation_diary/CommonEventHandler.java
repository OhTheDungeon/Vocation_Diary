package cn.shadow.vacation_diary;

import cn.shadow.vacation_diary.block.BlockRegistry;
import cn.shadow.vacation_diary.block.blocks.VacationPortal;
import cn.shadow.vacation_diary.item.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber()
public class CommonEventHandler {
	
	private static final String HAS_BOOK = VacationDiary.MOD_ID + ".spawned_book";

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (true) {
			CompoundNBT playerData = event.getPlayer().getPersistentData();
			CompoundNBT data = playerData.contains(PlayerEntity.PERSISTED_NBT_TAG) ? playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG) : new CompoundNBT();

			if (!data.getBoolean(HAS_BOOK)) {
				ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), new ItemStack(ItemRegistry.guide.get()));
				data.putBoolean(HAS_BOOK, true);
				playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
			}
		}
	}

    
	@SubscribeEvent
	public static void onLivingDropsEvent(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		World world = event.getEntity().getEntityWorld();
		if(world.isRemote) return;
		BlockPos pos = event.getEntity().getPosition();
	
		if(entity instanceof SquidEntity) {
	
			ItemEntity itemDropX = new ItemEntity(world, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, new ItemStack(ItemRegistry.squid_tentacle.get(), 1));
	
	
			event.getDrops().add(itemDropX);
	
		}
	
	}
	
	@SubscribeEvent
	public static void onPlayerInteract(RightClickBlock e){
		World world = e.getWorld();
		if(world.isRemote || e.getPlayer() == null) return;
//		VacationDiary.instance.reportMessage("创建传送门");
		
		if(e.getPlayer().getHeldItem(e.getHand()) != null) {
			Item item = e.getPlayer().getHeldItem(e.getHand()).getItem();
			if(item == ItemRegistry.guide.get()) {
				BlockPos pos = e.getPos();
	            for (int x = -1; x < 2; x++) {
	                for (int y = -1; y < 2; y++) {
	                    for (int z = -1; z < 2; z++) {
	                    	if(((VacationPortal) BlockRegistry.vacation_portal.get()).trySpawnPortal(world, 
	                    			new BlockPos(x + pos.getX(), y + pos.getY(), z + pos.getZ()))) {
	                    		e.setCanceled(true);
	                    		break;
	                    	}
	                    }
	                }
	            }
			}
		}
	}
}
