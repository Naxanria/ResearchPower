package nl.naxanria.nlib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.util.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TextureInfo implements ITextureInfo
{
  private final ResourceLocation location;
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
    
//    BufferedImage img = ImageIO.read(location.getResourcePath() + location.getResourceDomain())
  }

  @Override
  public ResourceLocation getResource()
  {
    return location;
  }

  @Override
  public int getX()
  {
    return 0;
  }

  @Override
  public int getY()
  {
    return 0;
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
