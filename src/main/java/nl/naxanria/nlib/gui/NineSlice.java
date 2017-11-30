package nl.naxanria.nlib.gui;

import net.minecraft.util.ResourceLocation;

public class NineSlice implements ITextureInfo
{
  private final ITextureInfo[] slices = new ITextureInfo[9];
  
  private final ITextureInfo backer;
  private final int x = 0;
  private final int y = 0;
  private final int width;
  private final int height;
  
  public NineSlice(ResourceLocation backer, Rect... slices)
  {
    this(new TextureInfo(backer), slices);
  }
  
  public NineSlice(TextureInfo backer, Rect... slices)
  {
    this.backer = backer;
    width = backer.getWidth();
    height = backer.getHeight();
    
    if (slices.length != 9)
    {
      throw new IllegalArgumentException("Need 9 Slices!");
    }
    
    for (int i = 0; i < 9; i++)
    {
      Rect rect = slices[i];
      ITextureInfo t = SpriteManager.registerSprite(rect, backer);
      this.slices[i] = t;
    }
  }
  
  public NineSlice(ResourceLocation backer, int width, int height)
  {
    this(new TextureInfo(backer), width, height);
  }
  
  public NineSlice(TextureInfo backer, int width, int height)
  {
    this.backer = backer;
    this.width = width * 3;
    this.height = height * 3;
    
    
    for (int x = 0; x < 3; x++)
    {
      int px = x * width;
      for (int y = 0; y < 3; y++)
      {
        int py = y * height;
        
        slices[x + y * 3] = SpriteManager.registerSprite(px, py, width, height, backer);
      }
    }
  }
  
  public NineSlice(TextureInfo backer)
  {
    this(backer, backer.getWidth() / 3, backer.getHeight() / 3);
  }
  
  public ITextureInfo getSlice(EnumSlice slice)
  {
    return slices[slice.ordinal()];
  }
  
  @Override
  public ResourceLocation getResource()
  {
    return backer.getResource();
  }
  
  @Override
  public int getX()
  {
    return x;
  }
  
  @Override
  public int getY()
  {
    return y;
  }
  
  @Override
  public int getWidth()
  {
    return width;
  }
  
  @Override
  public int getHeight()
  {
    return height;
  }
}
