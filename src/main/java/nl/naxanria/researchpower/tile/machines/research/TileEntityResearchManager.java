package nl.naxanria.researchpower.tile.machines.research;

import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.tile.IButtonResponder;
import nl.naxanria.nlib.tile.power.TileEntityEnergyAcceptor;
import nl.naxanria.nlib.util.collections.ReadonlyList;
import nl.naxanria.researchpower.research.Research;

public class TileEntityResearchManager extends TileEntityEnergyAcceptor implements IButtonResponder
{
  public TileEntityResearchManager()
  {
    super(100, 2);
  }
  
  @Override
  public void onButtonPressed(int id, EntityPlayer player)
  {
    ReadonlyList<Research> researches = Research.getAsList();
    Research research = researches.get(id);
    research.start(player);
  }
  
  @Override
  public boolean isButtonEnabled(int id, EntityPlayer player)
  {
    return false;
  }
}
