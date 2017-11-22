package nl.naxanria.researchpower.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.researchpower.containers.ContainerPress;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
  public static final int PRESS = 0;
  
  @Nullable
  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    Log.warn("Server GUI Request: " + ID + " " + x + "," + y + "," + z);
    switch (ID)
    {
      case PRESS:
        return new ContainerPress(player.inventory, (TileEntityPress) world.getTileEntity(new BlockPos(x, y, z)));
      default:
        return null;
    }
  }
  
  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    Log.warn("Client GUI Request: " + ID + " " + x + "," + y + "," + z);
    
    switch (ID)
    {
      case PRESS:
        return new GuiPress(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
        
      default:
        return null;
    }
  }
}
