package com.feed_the_beast.ftbl.api.security;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.FTBLibCapabilities;
import com.feed_the_beast.ftbl.api.IForgePlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 12.07.2016.
 */
public class Security implements ISecureModifiable, INBTSerializable<NBTBase>
{
    private final int flags;
    private UUID owner;
    private EnumPrivacyLevel level = EnumPrivacyLevel.PUBLIC;

    public Security(int f)
    {
        flags = f;
    }

    public static UUID getUUID(@Nonnull Object o)
    {
        if(o instanceof UUID)
        {
            return (UUID) o;
        }
        else if(o instanceof EntityPlayer)
        {
            return ((EntityPlayer) o).getGameProfile().getId();
        }
        else if(o instanceof IForgePlayer)
        {
            return ((IForgePlayer) o).getProfile().getId();
        }

        return FTBLibAPI.INSTANCE.getWorld().getPlayer(o).getProfile().getId();
    }

    @Override
    public int getFlags()
    {
        return flags;
    }

    @Nullable
    @Override
    public UUID getOwner()
    {
        return owner;
    }

    @Override
    public void setOwner(@Nullable UUID id)
    {
        owner = id;
    }

    public final boolean hasOwner()
    {
        return getOwner() != null;
    }

    @Nonnull
    @Override
    public EnumPrivacyLevel getPrivacyLevel()
    {
        return level;
    }

    @Override
    public void setPrivacyLevel(@Nonnull EnumPrivacyLevel l)
    {
        level = l;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return ISecureStorage.INSTANCE.writeNBT(FTBLibCapabilities.SECURE, this, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        ISecureStorage.INSTANCE.readNBT(FTBLibCapabilities.SECURE, this, null, nbt);
    }

    public boolean isOwner(@Nullable Object o)
    {
        return !hasOwner() || (o != null && owner.equals(getUUID(o)));
    }
}