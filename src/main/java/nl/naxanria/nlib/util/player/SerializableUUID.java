package nl.naxanria.nlib.util.player;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class SerializableUUID
{
  protected String tag;
  protected UUID uuid;
  
  public SerializableUUID(String tag)
  {
    this.tag = tag;
  }
  
  public String getTag()
  {
    return tag;
  }
  
  public UUID getUuid()
  {
    return uuid;
  }
  
  public SerializableUUID setTag(String tag)
  {
    this.tag = tag;
    return this;
  }
  
  public SerializableUUID setUuid(UUID uuid)
  {
    this.uuid = uuid;
    return this;
  }
  
  public void writeNBT(NBTTagCompound compound)
  {
    compound.setLong(tag + ".least", uuid.getLeastSignificantBits());
    compound.setLong(tag + ".most", uuid.getMostSignificantBits());
  }
  
  public void readNBT(NBTTagCompound compound)
  {
    uuid = new UUID(compound.getLong(tag + ".least"), compound.getLong(tag + ".most"));
  }
}
