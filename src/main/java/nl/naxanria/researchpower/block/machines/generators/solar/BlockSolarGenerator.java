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
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.block.BlockTileBaseProperties;
import nl.naxanria.nlib.item.ItemMetaBlock;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.LogColor;
import nl.naxanria.researchpower.tile.TileEntitySolarGenerator;

import javax.annotation.Nullable;

public class BlockSolarGenerator extends BlockTileBaseProperties<PropertyInteger, TileEntitySolarGenerator>
{
  protected int base = 2;
  
  protected static boolean needRegistration = true;
  
  private BlockSolarGenerator(PropertyInteger prop)
  {
    super(Material.IRON, "solar_generator", prop);
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
    return new TileEntitySolarGenerator(state.getValue((PropertyInteger)PROPERTY) + 1);
  }
  
  @Override
  public Class<TileEntitySolarGenerator> getTileEntityClass()
  {
    return TileEntitySolarGenerator.class;
  }

  public static BlockSolarGenerator createStateVersion(PropertyInteger property)
  {
    tempProperty = property;
    BlockSolarGenerator block = new BlockSolarGenerator(property);
    tempProperty = null;
    return block;
  }
}
