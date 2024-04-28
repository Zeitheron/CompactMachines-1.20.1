package dev.compactmods.machines.room.menu;

import dev.compactmods.machines.api.core.Constants;
import dev.compactmods.machines.core.UIRegistration;
import dev.compactmods.machines.dimension.MissingDimensionException;
import dev.compactmods.machines.location.LevelBlockPosition;
import dev.compactmods.machines.room.Rooms;
import dev.compactmods.machines.room.exceptions.NonexistentRoomException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MachineRoomMenu
		extends AbstractContainerMenu
{
	private final ChunkPos room;
	private String roomName;
	private final LevelBlockPosition machine;
	private StructureTemplate roomBlocks;
	
	public final RegistryAccess registryAccess;
	
	public MachineRoomMenu(int win, Inventory playerInv, ChunkPos room, LevelBlockPosition machine, String roomName)
	{
		super(UIRegistration.MACHINE_MENU.get(), win);
		this.room = room;
		this.roomName = roomName;
		this.roomBlocks = new StructureTemplate();
		this.machine = machine;
		this.registryAccess = playerInv.player.level().registryAccess();
	}
	
	public ChunkPos getRoom()
	{
		return room;
	}
	
	public LevelBlockPosition getMachine()
	{
		return machine;
	}
	
	public static MenuProvider makeProvider(MinecraftServer server, ChunkPos roomId, LevelBlockPosition machinePos)
	{
		return new MenuProvider()
		{
			@Override
			public Component getDisplayName()
			{
				return Component.translatable(Constants.MOD_ID + ".ui.room");
			}
			
			@Nullable
			@Override
			public AbstractContainerMenu createMenu(int winId, Inventory inv, Player player2)
			{
				try
				{
					final var title = Rooms.getRoomName(server, roomId);
					
					var menu = new MachineRoomMenu(winId, inv, roomId, machinePos, title.orElse("Room Preview"));
					menu.roomBlocks = Rooms.getInternalBlocks(server, roomId);
					return menu;
					
				} catch(MissingDimensionException | NonexistentRoomException e)
				{
					return null;
				}
			}
		};
	}
	
	@Nonnull
	@Override
	public ItemStack quickMoveStack(@Nonnull Player player, int slotInd)
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean stillValid(Player player)
	{
		return true;
	}
	
	@Nullable
	public StructureTemplate getBlocks()
	{
		return roomBlocks;
	}
	
	public void setBlocks(StructureTemplate blocks)
	{
		this.roomBlocks = blocks;
	}
	
	public String getRoomName()
	{
		return roomName;
	}
}
