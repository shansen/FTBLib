package com.feed_the_beast.ftbl.client.teamsgui;

import com.feed_the_beast.ftbl.api.EnumTeamStatus;
import com.feed_the_beast.ftbl.api_impl.FTBLibTeamGuiActions;
import com.feed_the_beast.ftbl.lib.gui.GuiBase;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.icon.Color4I;
import com.feed_the_beast.ftbl.lib.internal.FTBLibLang;
import com.feed_the_beast.ftbl.lib.util.ColorUtils;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;
import com.feed_the_beast.ftbl.net.MessageMyTeamAction;
import com.feed_the_beast.ftbl.net.MessageMyTeamPlayerList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;
import java.util.List;

/**
 * @author LatvianModder
 */
public class GuiManageMembers extends GuiManagePlayersBase
{
	private static class ButtonPlayer extends ButtonPlayerBase
	{
		private ButtonPlayer(GuiBase gui, MessageMyTeamPlayerList.Entry m)
		{
			super(gui, m);
		}

		@Override
		Color4I getPlayerColor()
		{
			if (entry.requestingInvite)
			{
				return ColorUtils.getChatFormattingColor(TextFormatting.GOLD.ordinal());
			}

			switch (entry.status)
			{
				case NONE:
					return getDefaultPlayerColor();
				case MEMBER:
				case MOD:
					return ColorUtils.getChatFormattingColor(TextFormatting.DARK_GREEN.ordinal());
				case INVITED:
					return ColorUtils.getChatFormattingColor(TextFormatting.BLUE.ordinal());
				case ALLY:
					return ColorUtils.getChatFormattingColor(TextFormatting.DARK_AQUA.ordinal());
			}

			return getDefaultPlayerColor();
		}

		@Override
		public void addMouseOverText(List<String> list)
		{
			if (!entry.status.isNone())
			{
				list.add(entry.status.getLangKey().translate());
			}
			else if (entry.requestingInvite)
			{
				list.add(FTBLibLang.TEAM_GUI_REQUESTING_INVITE.translate());
			}

			if (entry.requestingInvite)
			{
				list.add(FTBLibLang.TEAM_GUI_MEMBERS_REQUESTING_INVITE.translate());
			}
			else if (entry.status.isEqualOrGreaterThan(EnumTeamStatus.MEMBER))
			{
				list.add(FTBLibLang.TEAM_GUI_MEMBERS_KICK.translate());
			}
			else if (entry.status == EnumTeamStatus.INVITED)
			{
				list.add(FTBLibLang.TEAM_GUI_MEMBERS_CANCEL_INVITE.translate());
			}

			if (entry.status == EnumTeamStatus.NONE || entry.requestingInvite)
			{
				list.add(FTBLibLang.TEAM_GUI_MEMBERS_INVITE.translate());
			}

			if (entry.requestingInvite)
			{
				list.add(FTBLibLang.TEAM_GUI_MEMBERS_DENY_REQUEST.translate());
			}
		}

		@Override
		public void onClicked(MouseButton button)
		{
			GuiHelper.playClickSound();
			NBTTagCompound data = new NBTTagCompound();
			data.setString("player", entry.name);

			if (entry.requestingInvite)
			{
				if (button.isLeft())
				{
					data.setString("action", "invite");
					entry.status = EnumTeamStatus.MEMBER;
				}
				else
				{
					data.setString("action", "deny_request");
					entry.status = EnumTeamStatus.NONE;
				}

				entry.requestingInvite = false;
			}
			else if (entry.status == EnumTeamStatus.NONE)
			{
				data.setString("action", "invite");
				entry.status = EnumTeamStatus.INVITED;
			}
			else if (entry.status.isEqualOrGreaterThan(EnumTeamStatus.MEMBER))
			{
				if (!button.isLeft())
				{
					data.setString("action", "kick");
					entry.requestingInvite = true;
					entry.status = EnumTeamStatus.NONE;
				}
			}
			else if (entry.status == EnumTeamStatus.INVITED)
			{
				if (!button.isLeft())
				{
					data.setString("action", "cancel_invite");
					entry.status = EnumTeamStatus.NONE;
				}
			}

			if (data.hasKey("action"))
			{
				new MessageMyTeamAction(FTBLibTeamGuiActions.MEMBERS.getId(), data).sendToServer();
			}

			updateIcon();
		}
	}

	public GuiManageMembers(Collection<MessageMyTeamPlayerList.Entry> m)
	{
		super(FTBLibLang.TEAM_GUI_MEMBERS.translate(), m, ButtonPlayer::new);
	}
}