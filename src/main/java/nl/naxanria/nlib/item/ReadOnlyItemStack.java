//package nl.naxanria.nlib.item;
//
//import net.minecraft.block.Block;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//
//public class ReadOnlyItemStack extends ItemStack
//{
//  private ItemStack stack;
//
//  public ReadOnlyItemStack(ItemStack stack)
//  {
//    this.stack = stack;
//  }
//
//  public ReadOnlyItemStack(Block blockIn)
//  {
//    this(blockIn, 1);
//  }
//
//  public ReadOnlyItemStack(Block blockIn, int amount)
//  {
//    this(blockIn, amount, 0);
//  }
//
//  public ReadOnlyItemStack(Block blockIn, int amount, int meta)
//  {
//    this(Item.getItemFromBlock(blockIn), amount, meta);
//  }
//
//  public ReadOnlyItemStack(Item itemIn)
//  {
//    this(itemIn, 1);
//  }
//
//  public ReadOnlyItemStack(Item itemIn, int amount)
//  {
//    this(itemIn, amount, 0);
//  }
//
//  public ReadOnlyItemStack(Item item, int amount, int meta)
//  {
//    stack = new ItemStack(item, amount, meta);
//
//
//  }
//
//  @Override
//  public boolean isEmpty()
//  {
//    return super.isEmpty();
//  }
//
//  @Override
//  public Item getItem()
//  {
//    return super.getItem();
//  }
//
//  @Override
//  public int getMaxStackSize()
//  {
//    return super.getMaxStackSize();
//  }
//
//  @Override
//  public boolean isStackable()
//  {
//    return super.isStackable();
//  }
//
//  @Override
//  public boolean isItemStackDamageable()
//  {
//    return super.isItemStackDamageable();
//  }
//
//  @Override
//  public boolean getHasSubtypes()
//  {
//    return super.getHasSubtypes();
//  }
//
//  @Override
//  public boolean isItemDamaged()
//  {
//    return super.isItemDamaged();
//  }
//
//  @Override
//  public int getItemDamage()
//  {
//    return super.getItemDamage();
//  }
//
//  @Override
//  public int getMetadata()
//  {
//    return super.getMetadata();
//  }
//
//  @Override
//  public int getMaxDamage()
//  {
//    return super.getMaxDamage();
//  }
//
//  @Override
//  public ItemStack copy()
//  {
//    return super.copy();
//  }
//
//  @Override
//  public boolean isItemEqual(ItemStack other)
//  {
//    return super.isItemEqual(other);
//  }
//
//  @Override
//  public boolean isItemEqualIgnoreDurability(ItemStack stack)
//  {
//    return super.isItemEqualIgnoreDurability(stack);
//  }
//
//  @Override
//  public String getUnlocalizedName()
//  {
//    return super.getUnlocalizedName();
//  }
//
//  @Override
//  public String toString()
//  {
//    return super.toString();
//  }
//
//  @Override
//  public int getMaxItemUseDuration()
//  {
//    return super.getMaxItemUseDuration();
//  }
//
//  @Override
//  public EnumAction getItemUseAction()
//  {
//    return super.getItemUseAction();
//  }
//
//  @Override
//  public String getDisplayName()
//  {
//    return super.getDisplayName();
//  }
//
//  @Override
//  public boolean hasDisplayName()
//  {
//    return super.hasDisplayName();
//  }
//
//  @Override
//  public boolean hasEffect()
//  {
//    return super.hasEffect();
//  }
//
//  @Override
//  public EnumRarity getRarity()
//  {
//    return super.getRarity();
//  }
//
//  @Override
//  public boolean isItemEnchantable()
//  {
//    return super.isItemEnchantable();
//  }
//
//  @Override
//  public boolean isItemEnchanted()
//  {
//    return super.isItemEnchanted();
//  }
//
//  @Override
//  public int getRepairCost()
//  {
//    return super.getRepairCost();
//  }
//
//  @Override
//  public int getAnimationsToGo()
//  {
//    return super.getAnimationsToGo();
//  }
//
//  @Override
//  public int getCount()
//  {
//    return super.getCount();
//  }
//}
