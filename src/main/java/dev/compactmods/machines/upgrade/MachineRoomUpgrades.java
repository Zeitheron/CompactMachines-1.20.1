package dev.compactmods.machines.upgrade;

import dev.compactmods.machines.api.room.upgrade.RoomUpgrade;
import dev.compactmods.machines.core.Registries;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class MachineRoomUpgrades
{
	public static final Supplier<IForgeRegistry<RoomUpgrade>> REGISTRY = Registries.UPGRADES.makeRegistry(RegistryBuilder::new);
	
	// ================================================================================================================
	public static final RegistryObject<RoomUpgrade> CHUNKLOAD = Registries.UPGRADES.register(ChunkloadUpgrade.REG_ID.getPath(), ChunkloadUpgrade::new);
	
//	public static final RegistryObject<Item> CHUNKLOADER = Registries.ITEMS.register("chunkloader_upgrade", () -> new ChunkloadUpgradeItem(new Item.Properties()
//			.stacksTo(1)));
	
	public static void prepare()
	{
	
	}
}
