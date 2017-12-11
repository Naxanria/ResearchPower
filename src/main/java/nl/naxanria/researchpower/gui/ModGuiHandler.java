package nl.naxanria.researchpower.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import nl.naxanria.researchpower.containers.*;
import nl.naxanria.researchpower.tile.machines.furnace.TileEntityElectricFurnace;
import nl.naxanria.researchpower.tile.machines.generators.TileEntityCoalGenerator;
import nl.naxanria.researchpower.tile.machines.TileEntityEmpowerer;
import nl.naxanria.researchpower.tile.machines.TileEntityMachineSanding;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
  public static final int PRESS = 0;
  public static final int GENERATOR_COAL = 1;
  public static final int EMPOWERER = 2;
  public static final int SANDING = 3;
  public static final int FURNACE = 4;
  
  @Nullable
  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
    
    switch (ID)
    {
      case PRESS:
        return new ContainerPress((TileEntityPress) tile, player);
      case GENERATOR_COAL:
        return new ContainerCoalGenerator((TileEntityCoalGenerator) tile, player);
      case EMPOWERER:
        return new ContainerEmpowerer((TileEntityEmpowerer) tile, player);
      case SANDING:
        return new ContainerSanding((TileEntityMachineSanding) tile, player);
      case FURNACE:
        return new ContainerElectricFurnace((TileEntityElectricFurnace) tile, player);
      default:
        return null;
    }
  }
  
  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    
    switch (ID)
    {
      case PRESS:
        return new GuiPress((ContainerPress) container, player);
      case GENERATOR_COAL:
        return new GuiCoalGenerator((ContainerCoalGenerator) container, player);
      case EMPOWERER:
        return new GuiEmpowerer((ContainerEmpowerer) container, player);
      case SANDING:
        return new GuiSanding((ContainerSanding) container, player);
      case FURNACE:
        return new GuiElectricFurnace((ContainerElectricFurnace) container, player);
      default:
        return null;
    }
  }
}
