package nl.naxanria.nlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.item.ItemMetaBlock;
import nl.naxanria.nlib.proxy.Proxy;

import java.util.Collection;

public class BlockBase<T extends IProperty> extends Block implements IBlockBase
{
  protected String name;
  public static IProperty tempProperty;
  public final T PROPERTY;

  public BlockBase(Material blockMaterialIn, String name, T property)
  {
    super(blockMaterialIn);

    this.name = name;
    PROPERTY = property;

    setUnlocalizedName(name);
    setRegistryName(name);

    setCreativeTab(NMod.getInstance().defaultTab());
  }

  public BlockBase(Material blockMaterialIn, String name)
  {
    this(blockMaterialIn, name, null);
  }
  
  public Item getAsItem()
  {
    return Item.getItemFromBlock(this);
  }
  
  public Ingredient getAsIngredient()
  {
    return Ingredient.fromItem(getAsItem());
  }
  
  public BlockBase registerOreDict(String ore)
  {
    OreDictionary.registerOre(ore, this);
    
    return this;
  }
  
  @Override
  public BlockBase setCreativeTab(CreativeTabs tab)
  {
    super.setCreativeTab(tab);
    return this;
  }
  
  public void registerItemModel(Item itemBlock)
  {
    if (PROPERTY == null && tempProperty == null)
    {
      Proxy.registerItemRender(itemBlock, 0, name);
      return;
    }

    Collection<? extends Comparable<?>> allowedValues = PROPERTY.getAllowedValues();
    int count = 0;
    StateMapperBase b = new DefaultStateMapper();
    for (Comparable obj : allowedValues)
    {
      String str = b.getPropertyString(getDefaultState().withProperty(PROPERTY, obj).getProperties());
      System.out.println(str);
      Proxy.registerItemRenderWithVariant(Item.getItemFromBlock(this), count, name, str);
      count++;
    }
  }
  
  public void registerItemModel()
  {
   registerItemModel(Item.getItemFromBlock(this));
  }
  
  public Item createItemBlock()
  {
    if (PROPERTY == null && tempProperty == null)
      return new ItemBlock(this).setRegistryName(getRegistryName());
    else
      return new ItemMetaBlock(this).setRegistryName(getRegistryName());
  }
  
  @Override
  public BlockBase getBlock()
  {
    return this;
  }

  // state stuff
  @Override
  protected BlockStateContainer createBlockState()
  {
    if (PROPERTY == null && tempProperty == null)
      return super.createBlockState();
    IProperty property = PROPERTY;
    if (PROPERTY == null)
      property = tempProperty;
    return new BlockStateContainer(this, property);
  }

  @SuppressWarnings("deprecation,unchecked")
  @Override
  public IBlockState getStateFromMeta(int meta)
  {
    if (PROPERTY == null && tempProperty == null)
      return super.getStateFromMeta(meta);

    Collection<? extends Comparable<?>> allowedValues = PROPERTY.getAllowedValues();
    int count = 0;
    for (Comparable obj : allowedValues)
    {
      if (count++ == meta)
        return getDefaultState().withProperty(PROPERTY, obj);
    }
    return getDefaultState();
  }

  @SuppressWarnings("unchecked")
  @Override
  public int getMetaFromState(IBlockState state)
  {
    if (PROPERTY == null && tempProperty == null)
      return super.getMetaFromState(state);
    Collection<? extends Comparable> allowedValues = PROPERTY.getAllowedValues();
    int count = 0;
    for (Comparable obj : allowedValues)
    {
      IBlockState ourState = getDefaultState().withProperty(PROPERTY, obj);
      if (ourState == state)
        return count;
      count++;
    }
    return 0;
  }

  @Override
  public void getSubBlocks(CreativeTabs itemIn, NonNullList items)
  {
    if (PROPERTY == null && tempProperty == null)
    {
      super.getSubBlocks(itemIn, items);
      return;
    }

    int size = PROPERTY.getAllowedValues().size();
    for (int i = 0; i <= size - 1; i++)
    {
      items.add(new ItemStack(this, 1, i));
    }
  }

  // This is a hack as Block.java expects the property to be available on creation - and it's constructor runs before ours - so we need to make sure we can get it before our constructor finishes running

  public static BlockBase createBlock(Material blockMaterialIn, String name, IProperty property)
  {
    return new BlockBase(blockMaterialIn, name, property);
  }

  @SuppressWarnings("unchecked")
  public static BlockBase createStateVersion(Material blockMaterialIn, String name, IProperty property)
  {
    tempProperty = property;
    BlockBase block = createBlock(blockMaterialIn, name, property);
    tempProperty = null;
    return block;
  }
}
