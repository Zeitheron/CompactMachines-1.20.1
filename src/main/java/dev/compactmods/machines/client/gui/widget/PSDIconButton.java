package dev.compactmods.machines.client.gui.widget;

import dev.compactmods.machines.core.CompactMachinesNet;
import dev.compactmods.machines.room.client.MachineRoomScreen;
import dev.compactmods.machines.room.network.PlayerRequestedTeleportPacket;
import dev.compactmods.machines.shrinking.Shrinking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class PSDIconButton
		extends ExtendedButton
{
	private final MachineRoomScreen parent;
	
	public PSDIconButton(MachineRoomScreen parent, int xPos, int yPos)
	{
		super(xPos, yPos, 20, 22, Component.empty(), PSDIconButton::onClicked);
		this.active = false;
		this.parent = parent;
	}
	
	@Override
	public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks)
	{
		super.render(gfx, mouseX, mouseY, partialTicks);
		gfx.renderFakeItem(new ItemStack(Shrinking.PERSONAL_SHRINKING_DEVICE.get()), getX() + 2, getY() + 2);
	}
	
	private static void onClicked(Button button)
	{
		if(button instanceof PSDIconButton psd && button.active)
		{
			var menu = psd.parent.getMenu();
			var mach = psd.parent.getMachine();
			var room = menu.getRoom();
			CompactMachinesNet.CHANNEL.sendToServer(new PlayerRequestedTeleportPacket(mach, room));
		}
	}
	
	public void setEnabled(boolean has)
	{
		this.active = has;
	}
}