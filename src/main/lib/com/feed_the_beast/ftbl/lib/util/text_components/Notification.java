package com.feed_the_beast.ftbl.lib.util.text_components;

import com.feed_the_beast.ftbl.api.INotification;
import com.feed_the_beast.ftbl.lib.util.StringJoiner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * @author LatvianModder
 */
public class Notification extends TextComponentString implements INotification
{
	public static Notification of(ResourceLocation id, String text, ITextComponent... lines)
	{
		Notification n = new Notification(id, text);

		for (ITextComponent line : lines)
		{
			n.addLine(line);
		}

		return n;
	}

	public static Notification of(ResourceLocation id, ITextComponent... lines)
	{
		return of(id, "", lines);
	}

	private final ResourceLocation id;
	private int timer;
	private boolean important;

	private Notification(ResourceLocation i, String text)
	{
		super(text);
		id = i;
		timer = 60;
		important = false;
	}

	public Notification(INotification n)
	{
		this(n.getId(), n.getUnformattedComponentText());

		setStyle(n.getStyle().createShallowCopy());

		for (ITextComponent line : n.getSiblings())
		{
			getSiblings().add(line.createCopy());
		}

		setTimer(n.getTimer());
	}

	public Notification addLine(ITextComponent line)
	{
		if (!getSiblings().isEmpty())
		{
			appendText("\n");
		}

		appendSibling(line);
		return this;
	}

	public Notification setError()
	{
		getStyle().setColor(TextFormatting.DARK_RED);
		important = true;
		return this;
	}

	@Override
	public Notification appendText(String text)
	{
		return appendSibling(new TextComponentString(text));
	}

	@Override
	public Notification appendSibling(ITextComponent component)
	{
		super.appendSibling(component);
		return this;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	public boolean equals(Object o)
	{
		return o == this || (o instanceof INotification && ((INotification) o).getId().equals(getId()));
	}

	public String toString()
	{
		return "Notification{" + StringJoiner.with(", ").joinObjects("id=" + id, "siblings=" + siblings, "style=" + getStyle(), "timer=" + timer, "important=" + important) + '}';
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public int getTimer()
	{
		return timer;
	}

	public Notification setTimer(int t)
	{
		timer = t;
		return this;
	}

	@Override
	public boolean isImportant()
	{
		return important;
	}

	public Notification setImportant(boolean v)
	{
		important = v;
		return this;
	}

	@Override
	public Notification createCopy()
	{
		return new Notification(this);
	}
}