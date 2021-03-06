package com.feed_the_beast.ftbl.lib.gui;

import com.feed_the_beast.ftbl.lib.icon.Icon;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;

public abstract class Button extends Widget
{
	private String title = "";
	protected Icon icon = Icon.EMPTY;

	public Button(GuiBase gui, int x, int y, int w, int h)
	{
		super(gui, x, y, w, h);
	}

	public Button(GuiBase gui, int x, int y, int w, int h, String title, Icon icon)
	{
		this(gui, x, y, w, h);
		setIcon(icon);
		setTitle(title);
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	public Button setTitle(String s)
	{
		title = s;
		return this;
	}

	public Button setIcon(Icon i)
	{
		icon = i;
		return this;
	}

	public Icon getButtonBackground()
	{
		return gui.getTheme().getButton(gui.isMouseOver(this));
	}

	@Override
	public Icon getIcon()
	{
		if (icon.isEmpty())
		{
			return getButtonBackground();
		}

		return icon;
	}

	@Override
	public boolean mousePressed(MouseButton button)
	{
		if (gui.isMouseOver(this))
		{
			onClicked(button);
			return true;
		}

		return false;
	}

	public abstract void onClicked(MouseButton button);
}