package com.feed_the_beast.ftbl.api.events;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.server.command.CommandTreeBase;

/**
 * @author LatvianModder
 */
public class RegisterFTBClientCommandsEvent extends Event
{
    private final CommandTreeBase command;

    public RegisterFTBClientCommandsEvent(CommandTreeBase c)
    {
        command = c;
    }

    public void add(ICommand cmd)
    {
        command.addSubcommand(cmd);
    }
}