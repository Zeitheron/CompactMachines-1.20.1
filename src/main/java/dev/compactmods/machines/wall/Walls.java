package dev.compactmods.machines.wall;

import dev.compactmods.machines.core.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Walls
{
	static final Supplier<Item.Properties> WALL_ITEM_PROPS = () -> new Item.Properties();
	
	public static final RegistryObject<Block> BLOCK_SOLID_WALL = Registries.BLOCKS.register("solid_wall", () ->
			new SolidWallBlock(BlockBehaviour.Properties.of()
					.strength(-1.0F, 3600000.8F)
					.sound(SoundType.METAL)
					.lightLevel((state) -> 15)));
	
	public static final RegistryObject<Item> ITEM_SOLID_WALL = Registries.ITEMS.register("solid_wall", () ->
			new ItemBlockWall(BLOCK_SOLID_WALL.get(), WALL_ITEM_PROPS.get()));
	
	public static final RegistryObject<Block> BLOCK_BREAKABLE_WALL = Registries.BLOCKS.register("wall", () ->
			new BreakableWallBlock(BlockBehaviour.Properties.of()
					.strength(3.0f, 128.0f)
					.requiresCorrectToolForDrops()));
	
	public static final RegistryObject<Item> ITEM_BREAKABLE_WALL = Registries.ITEMS.register("wall", () ->
			new ItemBlockWall(BLOCK_BREAKABLE_WALL.get(), WALL_ITEM_PROPS.get()));
	
	public static void prepare()
	{
	}
}