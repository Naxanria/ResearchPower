package nl.naxanria.nlib.tile;

import net.minecraft.entity.player.EntityPlayer;

public interface IButtonResponder
{
  void onButtonPressed(int id, EntityPlayer player);
  
  boolean isButtonEnabled(int id, EntityPlayer player);
}
