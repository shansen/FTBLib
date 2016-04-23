package ftb.lib.mod.client.gui.info;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.info.lines.InfoExtendedTextLine;
import net.minecraft.util.IChatComponent;

import java.util.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
@SideOnly(Side.CLIENT)
public class ButtonInfoExtendedTextLine extends ButtonInfoTextLine
{
	public final InfoExtendedTextLine line;
	public List<String> hover;
	
	public ButtonInfoExtendedTextLine(GuiInfo g, InfoExtendedTextLine l)
	{
		super(g, l);
		line = l;
		
		if(l != null)
		{
			List<IChatComponent> h = l.getHover();
			
			if(h != null)
			{
				hover = new ArrayList<>();
				
				for(IChatComponent c1 : h)
				{
					hover.add(c1.getFormattedText());
				}
				
				if(hover.isEmpty()) hover = null;
			}
			else hover = null;
		}
	}
	
	@Override
	public void addMouseOverText(List<String> l)
	{
		if(hover != null) l.addAll(hover);
	}
	
	@Override
	public void onButtonPressed(int b)
	{
		if(line != null) line.onClicked();
	}
	
	@Override
	public void renderWidget()
	{
		int ay = getAY();
		int ax = getAX();
		
		if(text != null && !text.isEmpty())
		{
			for(int i = 0; i < text.size(); i++)
			{
				gui.getFontRenderer().drawString(text.get(i), ax, ay + i * 10 + 1, guiInfo.colorText);
			}
		}
	}
}
