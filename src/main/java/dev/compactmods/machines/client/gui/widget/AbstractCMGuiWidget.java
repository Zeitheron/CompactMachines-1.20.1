package dev.compactmods.machines.client.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

public class AbstractCMGuiWidget
		implements Renderable, GuiEventListener
{
	protected boolean focused;
	protected final int x, y, width, height;
	
	protected AbstractCMGuiWidget(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks)
	{
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY)
	{
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
	
	@Override
	public void setFocused(boolean p_265728_)
	{
		focused = p_265728_;
	}
	
	@Override
	public boolean isFocused()
	{
		return focused;
	}
}