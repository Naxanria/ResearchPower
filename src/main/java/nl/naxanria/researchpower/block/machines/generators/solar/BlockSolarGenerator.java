package nl.naxanria.researchpower.block.machines.generators.solar;

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.item.ItemMetaBlock;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.LogColor;
import nl.naxanria.researchpower.tile.TileEntitySolarGenerator;

import javax.annotation.Nullable;

public class BlockSolarGenerator extends BlockTileBase<TileEntitySolarGenerator>
{
  protected PropertyInteger TIER;
  protected int base = 2;
  
  protected static boolean needRegistration = true;
  
  public BlockSolarGenerator()
  {
    super(Material.IRON, "solar_generator");

  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    TileEntitySolarGenerator tile = getTileEntity(world, pos);
    player.sendStatusMessage
      (
        new TextComponentString("Producing: " + tile.produce +
        " Active: " + tile.canGenerate() +
        " Storage: " + tile.storage.getEnergyStored() + "/" + tile.storage.getMaxEnergyStored() + " " + tile.storage.getStoredPercentage() * 100 + "%"),
        true
      );
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
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
  
  @Nullable
  @Override
  public TileEntitySolarGenerator createTileEntity(World world, IBlockState state)
  {
    return new TileEntitySolarGenerator(state.getValue(TIER) + 1);
  }
  
  @Override
  public Class<TileEntitySolarGenerator> getTileEntityClass()
  {
    return TileEntitySolarGenerator.class;
  }

  @SuppressWarnings("deprecation")
  @Override
  public IBlockState getStateFromMeta(int meta)
  {
    return getDefaultState().withProperty(TIER, meta + 1);
  }

  /**
   * Convert the BlockState into the correct metadata value
   */
  @Override
  public int getMetaFromState(IBlockState state)
  {
    return state.getValue(TIER) - 1;
  }

  @Override
  protected BlockStateContainer createBlockState()
  {
    TIER = PropertyInteger.create("tier", 1, 7);
    return new BlockStateContainer(this, TIER);
  }

  public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
  {
    for (int i = 0; i <= 6; i++)
      items.add(new ItemStack(this, 1, i));
  }

  @Override
  public void registerItemModel()
  {
    for (int i = 0; i <= 6; i++)
    {
      StateMapperBase b = new DefaultStateMapper();
      BlockStateContainer bsc = getBlockState();
      ImmutableList<IBlockState> values = bsc.getValidStates();
      System.out.println("penis");
      for(IBlockState state : values) {
        String str = b.getPropertyString(state.getProperties());
        System.out.println(str);
        Proxy.registerItemRenderWithVariant(Item.getItemFromBlock(this), getMetaFromState(state), name, str); //TODO: Fix inventory display, this isn't the way to do it
      }
    }
  }

  public Item createItemBlock()
  {
    return new ItemMetaBlock(this).setRegistryName(getRegistryName()); // no harm in using it for everything
  }
}
