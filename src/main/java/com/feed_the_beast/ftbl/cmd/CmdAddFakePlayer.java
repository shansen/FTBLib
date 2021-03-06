package com.feed_the_beast.ftbl.cmd;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api_impl.ForgePlayer;
import com.feed_the_beast.ftbl.api_impl.Universe;
import com.feed_the_beast.ftbl.lib.cmd.CmdBase;
import com.feed_the_beast.ftbl.lib.internal.FTBLibLang;
import com.feed_the_beast.ftbl.lib.util.StringUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class CmdAddFakePlayer extends CmdBase
{
	public CmdAddFakePlayer()
	{
		super("add_fake_player", Level.OP);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i)
	{
		return i == 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		checkArgs(sender, args, 2);

		UUID id = StringUtils.fromString(args[0]);

		if (id == null)
		{
			throw FTBLibLang.CONFIG_ADD_FAKE_PLAYER_INVALID_UUID.commandError();
		}

		if (FTBLibAPI.API.getUniverse().getPlayer(id) != null || FTBLibAPI.API.getUniverse().getPlayer(args[1]) != null)
		{
			throw FTBLibLang.CONFIG_ADD_FAKE_PLAYER_PLAYER_EXISTS.commandError();
		}

		ForgePlayer p = new ForgePlayer(id, args[1]);
		Universe.INSTANCE.players.put(p.getId(), p);

		FTBLibLang.CONFIG_ADD_FAKE_PLAYER_ADDED.sendMessage(sender, args[1]);
	}
}
