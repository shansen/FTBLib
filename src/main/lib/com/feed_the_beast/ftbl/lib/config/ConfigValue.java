package com.feed_the_beast.ftbl.lib.config;

import com.feed_the_beast.ftbl.api.ICustomColor;
import com.feed_the_beast.ftbl.api.ICustomName;
import com.feed_the_beast.ftbl.lib.gui.misc.GuiSelectors;
import com.feed_the_beast.ftbl.lib.icon.Color4I;
import com.feed_the_beast.ftbl.lib.icon.Icon;
import com.feed_the_beast.ftbl.lib.io.DataIn;
import com.feed_the_beast.ftbl.lib.io.DataOut;
import com.feed_the_beast.ftbl.lib.util.JsonUtils;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;
import com.google.gson.JsonElement;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author LatvianModder
 */
public abstract class ConfigValue implements IStringSerializable, IJsonSerializable, ICustomName, ICustomColor
{
	public abstract Object getValue();

	public abstract String getString();

	public abstract boolean getBoolean();

	public abstract int getInt();

	public double getDouble()
	{
		return getInt();
	}

	public abstract ConfigValue copy();

	public boolean equalsValue(ConfigValue value)
	{
		return Objects.equals(getValue(), value.getValue());
	}

	public Color4I getColor()
	{
		return Color4I.GRAY;
	}

	public void addInfo(ConfigValueInfo info, List<String> list)
	{
		list.add(TextFormatting.AQUA + "Def: " + info.defaultValue.getString());
	}

	public List<String> getVariants()
	{
		return Collections.emptyList();
	}

	public boolean isNull()
	{
		return false;
	}

	public void onClicked(IGuiEditConfig gui, ConfigValueInfo info, MouseButton button)
	{
		GuiSelectors.selectJson(this, (value, set) ->
		{
			if (set)
			{
				fromJson(value.getSerializableElement());
				gui.onChanged(info.id, getSerializableElement());
			}

			gui.openGui();
		});
	}

	public boolean setValueFromString(String text, boolean simulate)
	{
		try
		{
			JsonElement json = JsonUtils.fromJson(text);

			if (!json.isJsonNull())
			{
				if (!simulate)
				{
					fromJson(json);
				}

				return true;
			}
		}
		catch (Exception ex)
		{
		}

		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof ConfigValue && equalsValue((ConfigValue) o);
	}

	@Override
	public String toString()
	{
		return getString();
	}

	public abstract void writeData(DataOut data);

	public abstract void readData(DataIn data);

	@Override
	public boolean hasCustomName()
	{
		return getValue() instanceof ICustomName && ((ICustomName) getValue()).hasCustomName();
	}

	@Override
	public ITextComponent getCustomDisplayName()
	{
		return ((ICustomName) getValue()).getCustomDisplayName();
	}

	@Override
	public Color4I getCustomColor()
	{
		return getValue() instanceof ICustomColor ? ((ICustomColor) getValue()).getCustomColor() : Icon.EMPTY;
	}
}