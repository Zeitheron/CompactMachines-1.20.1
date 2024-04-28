package dev.compactmods.machines.upgrade;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.api.core.Tooltips;
import dev.compactmods.machines.api.room.upgrade.RoomUpgrade;
import dev.compactmods.machines.api.upgrade.RoomUpgradeHelper;
import dev.compactmods.machines.i18n.TranslationUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.zeith.hammerlib.api.items.ITabItem;

import java.util.List;
import java.util.Set;

public abstract class RoomUpgradeItem
		extends Item
		implements ITabItem
{
	
	public RoomUpgradeItem(Properties props)
	{
		super(props);
		CompactMachines.COMPACT_MACHINES_ITEMS.add(this);
	}
	
	public abstract RoomUpgrade getUpgradeType();
	
	@Override
	public Component getName(ItemStack stack)
	{
		String key = RoomUpgradeHelper.getTypeFrom(stack)
				.map(rl -> MachineRoomUpgrades.REGISTRY.get().getValue(rl))
				.map(def -> def.getTranslationKey(stack))
				.orElse(RoomUpgrade.UNNAMED_TRANS_KEY);
		
		return Component.translatable(key);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> info, TooltipFlag flag)
	{
		if(Screen.hasShiftDown())
		{
			info.add(TranslationUtil.tooltip(Tooltips.TUTORIAL_APPLY_ROOM_UPGRADE).withStyle(ChatFormatting.ITALIC));
		} else
		{
			info.add(TranslationUtil.tooltip(Tooltips.HINT_HOLD_SHIFT).withStyle(ChatFormatting.DARK_GRAY));
		}
		
		// Show upgrade type while sneaking, or if advanced tooltips are on
		if(Screen.hasShiftDown() || flag.isAdvanced())
		{
			RoomUpgradeHelper.getTypeFrom(stack).ifPresent(upgType ->
			{
				info.add(TranslationUtil.tooltip(Tooltips.ROOM_UPGRADE_TYPE, upgType).withStyle(ChatFormatting.DARK_GRAY));
			});
		}
	}
	
	@Override
	public CreativeModeTab getItemCategory()
	{
		return CompactMachines.COMPACT_MACHINES_ITEMS.tab();
	}
}
