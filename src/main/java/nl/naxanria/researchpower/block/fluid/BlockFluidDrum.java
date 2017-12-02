package nl.naxanria.researchpower.block.fluid;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import nl.naxanria.nlib.block.BlockTileBaseProperties;
import nl.naxanria.researchpower.tile.TileEntityDrum;

import javax.annotation.Nullable;

public class BlockFluidDrum extends BlockTileBaseProperties<PropertyInteger, TileEntityDrum>
{
  private boolean needRegistration = true;
  
  public BlockFluidDrum(PropertyInteger property)
  {
    super(Material.IRON, "fluid_drum", property);
    setHarvestLevel("pickaxe", 0);
    setHardness(1.5f);
    setResistance(10.5f);
    setSoundType(SoundType.STONE);
    
    
  }
  
  @Nullable
  @Override
  public TileEntityDrum createTileEntity(World world, IBlockState state)
  {
    return new TileEntityDrum(state.getValue((PropertyInteger) PROPERTY));
  }
  
  @Override
  public Class<TileEntityDrum> getTileEntityClass()
  {
    return TileEntityDrum.class;
  }
  
  @Override
  protected boolean needTileEntityRegistration()
  {
    if (needRegistration)
    {
      needRegistration = false;
      return true;
    }
    
    return false;
  }
  
  @Override
  public boolean isFullBlock(IBlockState state)
  {
    return false;
  }
  
  public static BlockFluidDrum createStateVersion(PropertyInteger property)
  {
    tempProperty = property;
    BlockFluidDrum block = new BlockFluidDrum(property);
    tempProperty = null;
    return block;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote)
    {
      TileEntityDrum drum = getTileEntity(world, pos);
      FluidTank tank = drum.tank;
      
      if (!player.isSneaking())
      {
        useItemOnTank(player, hand, tank);
        
        // show info
        FluidStack fluid = tank.getFluid();
        String fluidName = fluid == null ? "EMPTY" : fluid.getLocalizedName();
        int amount = tank.getFluidAmount();
        int cap = tank.getCapacity();
        float perc = (float) amount / (float) cap;
        
        player.sendStatusMessage(new TextComponentString(fluidName + " " + amount + "/" + cap + " " + perc * 100 + "%"), true);
      }
    }
    return true;
  }
}
