package nl.naxanria.researchpower;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nl.naxanria.nlib.NMod;

@Mod
(
  modid = ResearchPower.MOD_ID,
  name = ResearchPower.MOD_NAME,
  version = ResearchPower.VERSION
)
public class ModInit
{
    private final NMod NModInstance;
    public ModInit()
    {
        NModInstance = new ResearchPower(this);
    }

    @Mod.EventHandler
    public final void preInit(FMLPreInitializationEvent event)
    {
        NModInstance.preInit(event);
    }

    @Mod.EventHandler
    public final void init(FMLInitializationEvent event)
    {
        NModInstance.init(event);
    }

    @Mod.EventHandler
    public final void postInit(FMLPostInitializationEvent event)
    {
        NModInstance.postInit(event);
    }
}
