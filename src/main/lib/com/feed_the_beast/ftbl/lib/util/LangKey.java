package com.feed_the_beast.ftbl.lib.util;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.server.command.TextComponentHelper;

import javax.annotation.Nullable;

/**
 * @author LatvianModder
 */
public final class LangKey implements IStringSerializable
{
	private final String key;
	private final Class[] arguments;
	private Style defaultStyle;

	public static LangKey of(String key, Class... args)
	{
		return new LangKey(key, args);
	}

	private LangKey(String s, Class[] a)
	{
		key = s;
		arguments = a;
		defaultStyle = null;

		for (int i = 0; i < a.length; i++)
		{
			if (a[i] == null)
			{
				a[i] = Object.class;
			}
			else if (a[i] == Byte.class || a[i] == Short.class)
			{
				a[i] = Integer.class;
			}
			else if (ITextComponent.class.isAssignableFrom(a[i]))
			{
				a[i] = String.class;
			}
		}
	}

	private static boolean canAssign(@Nullable Object o, @Nullable Class c)
	{
		if (o == null && c == null)
		{
			return true;
		}
		else if (o == null || c == null)
		{
			return false;
		}

		Class c1 = o.getClass();

		if (c1 == c)
		{
			return true;
		}

		if (c == String.class)
		{
			return o instanceof ITextComponent;
		}
		else if (c == Integer.class)
		{
			return c1 == Short.class || c1 == Byte.class;
		}

		return c.isAssignableFrom(c1);
	}

	private void checkArgument(boolean b, Object[] with)
	{
		if (!b)
		{
			Object[] args = new Object[arguments.length];

			for (int i = 0; i < arguments.length; i++)
			{
				args[i] = arguments[i].getSimpleName();
			}

			Object[] got = new Object[with.length];

			for (int i = 0; i < with.length; i++)
			{
				Class c = Object.class;

				if (with[i] instanceof ITextComponent)
				{
					c = String.class;
				}
				else if (with[i] != null)
				{
					c = with.getClass();
				}
				if (c == Byte.class || c == Short.class)
				{
					c = Integer.class;
				}

				got[i] = c.getSimpleName();
			}

			throw new IllegalArgumentException("Expected [" + StringJoiner.with(", ").joinObjects(args) + "] for " + key + ", got [" + StringJoiner.with(", ").joinObjects(got) + "]");
		}
	}

	private void checkArguments(Object[] with)
	{
		checkArgument(arguments.length == with.length, with);

		for (int i = 0; i < arguments.length; i++)
		{
			checkArgument(canAssign(with[i], arguments[i]), with);
		}
	}

	@Override
	public String getName()
	{
		return key;
	}

	public LangKey setDefaultStyle(@Nullable Style style)
	{
		defaultStyle = style;
		return this;
	}

	public String translate()
	{
		checkArguments(CommonUtils.NO_OBJECTS);
		return StringUtils.translate(key);
	}

	public String translate(Object... o)
	{
		checkArguments(o);
		return StringUtils.translate(key, o);
	}

	public ITextComponent textComponent(@Nullable ICommandSender sender)
	{
		checkArguments(CommonUtils.NO_OBJECTS);
		ITextComponent component = TextComponentHelper.createComponentTranslation(sender, key, CommonUtils.NO_OBJECTS);

		if (defaultStyle != null)
		{
			component.setStyle(defaultStyle.createShallowCopy());
		}

		return component;
	}

	public ITextComponent textComponent(@Nullable ICommandSender sender, Object... with)
	{
		checkArguments(with);
		ITextComponent component = TextComponentHelper.createComponentTranslation(sender, key, with);

		if (defaultStyle != null)
		{
			component.setStyle(defaultStyle.createShallowCopy());
		}

		return component;
	}

	public void sendMessage(ICommandSender sender, Object... with)
	{
		sender.sendMessage(textComponent(sender, with));
	}

	public CommandException commandError(Object... with)
	{
		checkArguments(with);
		return new CommandException(key, with);
	}
}