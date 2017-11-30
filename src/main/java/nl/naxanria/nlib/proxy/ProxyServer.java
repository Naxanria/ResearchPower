package nl.naxanria.nlib.proxy;

import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class ProxyServer extends Proxy
{
    @Override
    public String getLocalization(String unlocalized, Object... args)
    {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }
}
