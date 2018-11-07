package nl.naxanria.nlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.util.NBTHelper;
import nl.naxanria.nlib.util.WorldUtil;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public abstract class BlockTileBaseInternal<T extends IProperty, TE extends TileEntityBase> extends BlockBase
{
  public BlockTileBaseInternal(Material blockMaterialIn, String name, T property)
  {
    super(blockMaterialIn, name, property);
  }

  public BlockTileBaseInternal(Material blockMaterialIn, String name)
  {
    super(blockMaterialIn, name);
  }
  
  @SuppressWarnings("unchecked")
  public TE getTileEntity(IBlockAccess world, BlockPos pos)
  {
    return (TE) world.getTileEntity(pos);
  }
  
  @Override
  public boolean hasTileEntity(IBlockState state)
  {
    return true;
  }
  
  @Nullable
  @Override
  public abstract TE createTileEntity(World world, IBlockState state);
  
  public abstract Class<TE> getTileEntityClass();
  
  @SuppressWarnings("ConstantConditions")
  public void registerTileEntity()
  {
    if (needTileEntityRegistration())
    {
      GameRegistry.registerTileEntity(getTileEntityClass(), getRegistryName().toString());
      if (getTileEntityRendererClass() != null)
      {
        Proxy.registerTileEntityRenderer(getTileEntityClass(), getTileEntityRendererClass());
      }
    }
  }

  public String getTileEntityRendererClass()
  {
    return null;
  }

  protected boolean needTileEntityRegistration()
  {
    return true;
  }
  
  @Override
  public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
  {
    neighbourUpdate(world, pos);
  }
  
  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
  {
    neighbourUpdate(world, pos);
  }
  
  private void neighbourUpdate(IBlockAccess world, BlockPos pos)
  {
    TE tile = getTileEntity(world, pos);
  
    if (tile.shouldSaveDataOnChangeOrWorldStart())
    {
      tile.saveDataOnChangeOrWorldStart();
    }
  }
  
  @Override
  public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
  {
    return getTileEntity(world, pos).getComparatorStrength();
  }
  
  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
  {
    if (!worldIn.isRemote)
    {
      TileEntityBase tile = getTileEntity(worldIn, pos);
      if (tile.hasFlags(TileFlags.DropInventory))
      {
        IItemHandler[] toDrop = tile.getAllInventoriesToDrop();
        if (toDrop != null)
        {
          for (IItemHandler handler :
            toDrop)
          {
            for (int i = 0; i < handler.getSlots(); i++)
            {
              ItemStack stack = handler.getStackInSlot(i);
              WorldUtil.dropItemInWorld(worldIn, pos, stack);
            }
          }
        }
      }
  
      super.breakBlock(worldIn, pos, state);
    }
  }
  
  protected boolean useItemOnTank(EntityPlayer player, EnumHand hand, IFluidHandler tank)
  {
    return  (FluidUtil.interactWithFluidHandler(player, hand, tank));
  }
  
  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
  {
    TileEntity tile = world.getTileEntity(pos);
    if(stack.hasTagCompound())
    {
      if(tile instanceof TileEntityBase)
      {
        TileEntityBase base = (TileEntityBase) tile;
        
        if (stack.getTagCompound() != null)
        {
          NBTTagCompound compound = stack.getTagCompound().getCompoundTag("Data");
  
          base.readSyncableNBT(compound, TileEntityBase.NBTType.SAVE_BLOCK);
        }
        
        if (entity instanceof EntityPlayer && base.hasFlags(TileFlags.HasOwner))
        {
          if (base.getOwner() == null)
          {
            base.setOwner((EntityPlayer) entity);
            base.markDirty();
          }
        }
      }
    }
    else if (tile instanceof TileEntityBase)
    {
      TileEntityBase base = (TileEntityBase) tile;
  
      if (entity instanceof EntityPlayer && base.hasFlags(TileFlags.HasOwner))
      {
        if (base.getOwner() == null)
        {
          base.setOwner((EntityPlayer) entity);
          base.markDirty();
        }
      }
    }
  }
  
  @SuppressWarnings("ConstantConditions")
  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
  {
    TileEntityBase tile = getTileEntity(world, pos);
    Random rand = tile == null ? ((World) world).rand : tile.getWorld().rand;
  
    ItemStack stack = new ItemStack(getItemDropped(state, rand, fortune),  quantityDropped(state, fortune, rand), damageDropped(state));
    
    if (tile == null)
    {
      Log.warn("Tile is null");
      drops.add(new ItemStack(getItemDropped(state, rand, fortune), quantityDropped(state, fortune, rand), damageDropped(state)));
      return;
    }
    
    if (tile.hasFlags(TileFlags.KeepNBTData))
    {
  
      NBTTagCompound data = new NBTTagCompound();
      tile.writeSyncableNBT(data, TileEntityBase.NBTType.SAVE_BLOCK);
  
      NonNullList<String> toRemove = NonNullList.create();
      removeNBTTags(toRemove, data);
  
      //keysToRemove.addAll(toRemove);
  
      for (String key : toRemove)
      {
        data.removeTag(key);
      }
  
      if (!data.hasNoTags())
      {
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag("Data", data);
      }
  
      Log.info(LogColor.CYAN, "=====================");
    }
    
    drops.add(stack);
  }
  
  public void removeNBTTags(NonNullList<String> toRemove, NBTTagCompound compound)
  {
    for (String key : compound.getKeySet())
    {
      NBTBase tag = compound.getTag(key);
    
      //Remove only ints because they are the most common ones
    
      if (tag instanceof NBTTagInt)
      {
        if (((NBTTagInt) tag).getInt() == 0)
        {
          toRemove.add(key);
        }
      }
    }
  
    if (compound.hasKey("tank"))
    {
      if (NBTHelper.getFluidAmount(compound, "tank") <= 0)
      {
        toRemove.add("tank");
      }
    }
  }
}
