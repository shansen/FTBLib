package com.feed_the_beast.ftbl.client.teamsgui;

import com.feed_the_beast.ftbl.api.EnumTeamColor;
import com.feed_the_beast.ftbl.api.IForgeTeam;
import com.feed_the_beast.ftbl.lib.io.DataIn;
import com.feed_the_beast.ftbl.lib.io.DataOut;
import com.feed_the_beast.ftbl.lib.util.FinalIDObject;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class PublicTeamData extends FinalIDObject implements Comparable<PublicTeamData>
{
	public static final DataOut.Serializer<PublicTeamData> SERIALIZER = (data, d) ->
	{
		data.writeString(d.getName());
		data.writeString(d.displayName);
		data.writeString(d.description);
		data.write(EnumTeamColor.NAME_MAP, d.color);
		data.writeUUID(d.ownerId);
		data.writeString(d.ownerName);
		data.writeBoolean(d.isInvited);
	};

	public static final DataIn.Deserializer<PublicTeamData> DESERIALIZER = PublicTeamData::new;

	public final String displayName, description;
	public final EnumTeamColor color;
	public final UUID ownerId;
	public final String ownerName;
	public final boolean isInvited;

	public PublicTeamData(DataIn data)
	{
		super(data.readString());
		displayName = data.readString();
		description = data.readString();
		color = data.read(EnumTeamColor.NAME_MAP);
		ownerId = data.readUUID();
		ownerName = data.readString();
		isInvited = data.readBoolean();
	}

	public PublicTeamData(IForgeTeam team, boolean c)
	{
		super(team.getName());
		displayName = team.getTitle();
		description = team.getDesc();
		color = team.getColor();
		ownerId = team.getOwner().getId();
		ownerName = team.getOwner().getName();
		isInvited = c;
	}

	@Override
	public int compareTo(PublicTeamData o)
	{
		int i = Boolean.compare(o.isInvited, isInvited);

		if (i == 0)
		{
			i = displayName.compareToIgnoreCase(o.displayName);
		}

		return i;
	}
}