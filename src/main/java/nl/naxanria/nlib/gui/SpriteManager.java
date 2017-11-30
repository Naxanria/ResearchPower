package nl.naxanria.nlib.gui;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class SpriteManager
{
  //private static HashMap<String, ITextureInfo> sprites = new HashMap<>();

  public static ITextureInfo registerSprite(Rect rect, ITextureInfo backer)
  {
    return registerSprite(rect.x, rect.y, rect.width, rect.height, backer);
  }
  
  public static ITextureInfo registerSprite(/*String name,*/ int x, int y, int width, int height, ITextureInfo backer)
  {
//    if (sprites.containsKey(name))
//    {
//      return sprites.get(name);
//    }

    if (x + width > backer.getWidth() || y + height > backer.getHeight())
    {
      throw new UnsupportedOperationException("Texture is out of bounds!");
    }

    ITextureInfo texture = new Sprite(backer, x, y, width, height);

    //sprites.put(name, texture);

    return texture;
  }

  private static class Sprite implements ITextureInfo
  {
    private final ITextureInfo backer;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Sprite(ITextureInfo backer, int x, int y, int width, int height)
    {
      this.backer = backer;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
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
}
