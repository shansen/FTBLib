package com.feed_the_beast.ftbl.api;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author LatvianModder
 */
public class ServerReloadEvent extends FTBLibEvent
{
	public static final ResourceLocation ALL = new ResourceLocation("*:*");

	private final ICommandSender sender;
	private final EnumReloadType type;
	private final ResourceLocation reloadId;
	private final Collection<ResourceLocation> failed;
	private boolean clientReloadRequired;

	public ServerReloadEvent(ICommandSender c, EnumReloadType t, ResourceLocation id, Collection<ResourceLocation> f)
	{
		sender = c;
		type = t;
		reloadId = id;
		failed = f;
		clientReloadRequired = false;
	}

	public ICommandSender getSender()
	{
		return sender;
	}

	public EnumReloadType getType()
	{
		return type;
	}

	public void setClientReloadRequired()
	{
		clientReloadRequired = true;
	}

	public boolean isClientReloadRequired()
	{
		return clientReloadRequired;
	}

	public boolean reload(ResourceLocation id)
	{
		String ridd = reloadId.getResourceDomain();
		String ridp = reloadId.getResourcePath();
		return ridd.equals("*") || ridd.equals(reloadId.getResourceDomain()) && (ridp.equals("*") || ridp.equals(id.getResourcePath()));
	}

	public void failedToReload(ResourceLocation id)
	{
		failed.add(id);
	}

	public static class RegisterIds extends FTBLibEvent
	{
		private final Consumer<ResourceLocation> callback;

		public RegisterIds(Consumer<ResourceLocation> c)
		{
			callback = c;
		}

		public void register(ResourceLocation id)
		{
			callback.accept(id);
		}
	}
}