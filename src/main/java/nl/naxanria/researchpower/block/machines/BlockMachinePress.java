package nl.naxanria.researchpower.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.WorldUtil;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

import javax.annotation.Nullable;

public class BlockMachinePress extends BlockTileBase<TileEntityPress>
{
  public BlockMachinePress()
  {
    super(Material.IRON, "machine_press");
  }
  
  @Nullable
  @Override
  public TileEntityPress createTileEntity(World world, IBlockState state)
  {
    return new TileEntityPress();
  }
  
  @Override
  public Class<TileEntityPress> getTileEntityClass()
  {
    return TileEntityPress.class;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote)
    {
      if (!player.isSneaking())
      {
        // open crafting gui
        
        player.openGui(ResearchPower.getInstance(), ModGuiHandler.PRESS, world, pos.getX(), pos.getY(), pos.getZ());
        
        player.sendMessage(new TextComponentString("Stuff and things"));
  
        Log.info("--");
        Log.warn("Gui stuff?");
    
        return true;
      }
    }
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
  
  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state)
  {
    TileEntityPress tile = getTileEntity(world, pos);
    IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
    if (handler == null)
    {
      return;
    }
    
    ItemStack slot0 = handler.getStackInSlot(0);
    ItemStack slot1 = handler.getStackInSlot(1);
  
    if (!slot0.isEmpty())
    {
      WorldUtil.dropItemInWorld(world, pos, slot0);
    }
  
    if (!slot1.isEmpty())
    {
      WorldUtil.dropItemInWorld(world, pos, slot0);
    }
    
    super.breakBlock(world, pos, state);
  }
}
