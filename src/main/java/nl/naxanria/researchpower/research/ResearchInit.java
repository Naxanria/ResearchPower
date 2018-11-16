package nl.naxanria.researchpower.research;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nl.naxanria.nlib.util.ItemUtil;
import nl.naxanria.nlib.util.StackUtil;

public class ResearchInit
{
  public static void init()
  {
    new Research("test1", "Just a test...", 150, null, null);
    new Research("testing2", "MORE TESTS", 100, "test1", new ItemStack(Items.IRON_INGOT));
    new Research("testing3", "MORE TESTS", 800, "test1", new ItemStack(Items.IRON_INGOT));
    new Research("testing4", "MORE TESTS", 750, "test1", new ItemStack(Items.IRON_INGOT));
    new Research("testing5", "MORE TESTS", 1600, "test1", new ItemStack(Items.IRON_INGOT));
  }
}
