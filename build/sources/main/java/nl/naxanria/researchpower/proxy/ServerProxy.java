package nl.naxanria.researchpower.proxy;


import net.minecraft.util.text.translation.I18n;

public class ServerProxy extends Proxy
{
    @Override
    public String getLocalization(String unlocalized, Object... args)
    {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }
}
