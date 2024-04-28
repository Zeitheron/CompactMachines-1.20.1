package dev.compactmods.machines.dimension;

import dev.compactmods.machines.api.room.IRoomHistory;
import dev.compactmods.machines.config.ServerConfig;
import dev.compactmods.machines.room.RoomCapabilities;
import dev.compactmods.machines.util.PlayerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class VoidAirBlock
		extends AirBlock
{
	public VoidAirBlock()
	{
		super(BlockBehaviour.Properties.of().noCollission().air().noLootTable());
	}
	
	
	@Override
	public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity)
	{
		if(ServerConfig.isAllowedOutsideOfMachine()) return;
		if(pLevel.isClientSide) return;
		
		if(pEntity instanceof ServerPlayer player)
		{
			if(player.isCreative()) return;
			
			player.addEffect(new MobEffectInstance(MobEffects.POISON, 5 * 20));
			player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5 * 20));
			player.hurt(player.damageSources().fellOutOfWorld(), 1);
			
			PlayerUtil.howDidYouGetThere(player);
			player.getCapability(RoomCapabilities.ROOM_HISTORY).ifPresent(IRoomHistory::clear);
			PlayerUtil.teleportPlayerToRespawnOrOverworld(player.server, player);
		}
	}
}
