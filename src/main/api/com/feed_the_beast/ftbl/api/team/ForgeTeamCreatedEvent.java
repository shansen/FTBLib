package com.feed_the_beast.ftbl.api.team;

import com.feed_the_beast.ftbl.api.IForgeTeam;

/**
 * @author LatvianModder
 */
public class ForgeTeamCreatedEvent extends ForgeTeamEvent
{
	public ForgeTeamCreatedEvent(IForgeTeam team)
	{
		super(team);
	}
}