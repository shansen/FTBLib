package com.feed_the_beast.ftbl.cmd.team;

import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.api.IForgeTeam;
import com.feed_the_beast.ftbl.lib.cmd.CmdBase;
import com.feed_the_beast.ftbl.lib.internal.FTBLibLang;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * @author LatvianModder
 */
public class CmdLeave extends CmdBase
{
	public CmdLeave()
	{
		super("leave", Level.ALL);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		IForgePlayer p = getForgePlayer(getCommandSenderAsPlayer(sender));
		IForgeTeam team = p.getTeam();

		if (team == null)
		{
			throw FTBLibLang.TEAM_NO_TEAM.commandError();
		}
		else if (!team.removeMember(p))
		{
			throw FTBLibLang.TEAM_MUST_TRANSFER_OWNERSHIP.commandError();
		}
	}
}
