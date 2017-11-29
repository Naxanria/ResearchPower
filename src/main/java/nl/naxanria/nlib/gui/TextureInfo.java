package nl.naxanria.nlib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.util.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TextureInfo
{
  public final ResourceLocation location;
  private int width, height;
  
  public TextureInfo(ResourceLocation location)
  {
    this.location = location;
    
    try
    {
      InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
      BufferedImage img = ImageIO.read(in);
      
      width = img.getWidth();
      height = img.getHeight();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  
    Log.info(location.toString());
    Log.info(location.getResourceDomain());
    Log.info(location.getResourcePath());
    
//    BufferedImage img = ImageIO.read(location.getResourcePath() + location.getResourceDomain())
  
    Log.info(toString());
    
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  @Override
  public String toString()
  {
    return "TextureInfo{" +
      "location=" + location +
      ", width=" + width +
      ", height=" + height +
      '}';
  }
}
