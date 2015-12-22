package ftb.lib.gui.widgets;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.*;
import ftb.lib.gui.GuiLM;

@SideOnly(Side.CLIENT)
public class ButtonPopupMenu extends ButtonLM
{
	public TextureCoords icon;
	public Object object = null;
	
	public ButtonPopupMenu(PanelPopupMenu p, TextureCoords i, String t)
	{
		super(p.gui, 0, p.menuButtons.size(), 0, p.buttonHeight);
		icon = i;
		title = t;
		width = 2 + (icon == null ? 0 : p.buttonHeight) + ((t == null || t.isEmpty()) ? 0 : (3 + p.gui.getFontRenderer().getStringWidth(t)));
	}
	
	public void onButtonPressed(int b)
	{ ((PanelPopupMenu)parentPanel).onClosed(this, b); }
	
	public TextureCoords getIcon()
	{ return icon; }
	
	public void renderWidget()
	{
		int ay = getAY();
		if(ay + height < 0 || ay > gui.height) return;
		int ax = getAX();
		
		TextureCoords icon = getIcon();
		int x = 3;
		if(icon != null) x += 18;
		
		double z = gui.getZLevel();
		GuiLM.drawBlankRect(ax, ay, z, width, height, mouseOver() ? 0xFF666666 : 0xFF444444);
		GuiLM.drawBlankRect(ax, ay - 1, z, width, 1, 0xFF222222);
		GuiLM.drawBlankRect(ax, ay + height, z, width, 1, 0xFF222222);
		GuiLM.drawBlankRect(ax, ay, z, 1, height, 0xFF222222);
		GuiLM.drawBlankRect(ax + width - 1, ay, z, 1, height, 0xFF222222);
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		gui.render(icon, ax + 2, ay + 1D, 16D, 16D);
		if(title != null && !title.isEmpty())
		{
			GlStateManager.translate(0F, 0F, gui.getZLevel());
			gui.getFontRenderer().drawString(title, ax + x, ay + (height - 8) / 2, 0xFFFFFFFF);
			GlStateManager.translate(0F, 0F, -gui.getZLevel());
		}
	}
	
	public void addMouseOverText(List<String> l)
	{
	}
}