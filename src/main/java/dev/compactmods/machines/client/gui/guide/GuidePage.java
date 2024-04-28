package dev.compactmods.machines.client.gui.guide;

import dev.compactmods.machines.api.core.Constants;
import dev.compactmods.machines.client.gui.widget.AbstractCMGuiWidget;
import dev.compactmods.machines.client.gui.widget.ScrollableWrappedTextWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;

import java.util.*;

public class GuidePage
		implements Renderable, GuiEventListener
{
	protected final List<AbstractCMGuiWidget> widgets;
	
	public GuidePage()
	{
		widgets = new ArrayList<>();
		
		ScrollableWrappedTextWidget sc = new ScrollableWrappedTextWidget(Constants.MOD_ID + ".psd.pages.machines", 2, 18, 222, 160);
		widgets.add(sc);
	}
	
	@Override
	public void render(GuiGraphics ms, int mouseX, int mouseY, float partialTicks)
	{
		Font fr = Minecraft.getInstance().font;
		
		ms.drawString(fr, Component.translatable(Constants.MOD_ID + ".psd.pages.machines.title")
						.withStyle(ChatFormatting.GOLD),
				2, 2, 0
		);
		
		for(var comp : widgets)
			comp.render(ms, mouseX, mouseY, partialTicks);
	}
	
	public Optional<AbstractCMGuiWidget> getWidgetByPosition(double mouseX, double mouseY)
	{
		for(AbstractCMGuiWidget wid : widgets)
		{
			if(wid.isMouseOver(mouseX, mouseY))
				return Optional.of(wid);
		}
		
		return Optional.empty();
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY)
	{
		getWidgetByPosition(mouseX, mouseY)
				.ifPresent(c -> c.mouseMoved(mouseX, mouseY));
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta)
	{
		return getWidgetByPosition(mouseX, mouseY)
				.map(c -> c.mouseScrolled(mouseX, mouseY, delta))
				.orElse(false);
	}
	
	protected boolean focused;
	
	@Override
	public void setFocused(boolean f)
	{
		focused = f;
	}
	
	@Override
	public boolean isFocused()
	{
		return focused;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		return getWidgetByPosition(mouseX, mouseY)
				.map(c -> c.mouseClicked(mouseX, mouseY, button))
				.orElse(false);
	}
}
