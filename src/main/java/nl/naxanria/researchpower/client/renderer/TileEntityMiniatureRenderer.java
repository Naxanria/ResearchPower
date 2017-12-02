package nl.naxanria.researchpower.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.researchpower.tile.machines.TileEntityMiniatureController;
import org.lwjgl.opengl.GL11;

public class TileEntityMiniatureRenderer extends TileEntitySpecialRenderer<TileEntityMiniatureController>
{

  BlockRendererDispatcher renderDispatcher;
  @Override
  public void render(TileEntityMiniatureController controller, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
  {
    EntityPlayer player = Minecraft.getMinecraft().player;
    if (!controller.structureGood || !controller.isInProgress())
    {
      return;
    }
    if (renderDispatcher == null)
    {
      renderDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    }
    
    BlockPos blockPos = controller.getPos();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    
    this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    
    RenderHelper.disableStandardItemLighting();
    
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.enableBlend();
    GlStateManager.disableCull();

    if (Minecraft.isAmbientOcclusionEnabled())
    {
      GlStateManager.shadeModel(7425); // 1D01
    }
    else
    {
      GlStateManager.shadeModel(7424); // 1D00
    }




    BlockPos[] positions = controller.getBlockPositions();

    double xTranslate = positions[4 + 9].getX();
    double yTranslate = positions[4 + 9].getY();
    double zTranslate = positions[4 + 9].getZ();
 
    float scaleAmount = 1.0F - (((float) controller.getProgress() + partialTicks) / (float) controller.getTotalTime());

    Log.info(controller.dir.toString());

    bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);



    for (int i = 0; i < 27; i++)
    {
      bufferbuilder.setTranslation(-positions[4 + 9].getX() + -0.5, -positions[4 + 9].getY() + -0.5, -positions[4 + 9].getZ() + -0.5);

      renderDispatcher.getBlockModelRenderer()
        .renderModel(controller.getWorld(), this.renderDispatcher.getModelForState(controller.processingRecipe[i]), controller.processingRecipe[i], positions[i], bufferbuilder, false);

      bufferbuilder.setTranslation(0, 0, 0);
    }

    //scaleAmount = 1;



    BlockPos ourPos = positions[4];

    switch (controller.dir)
    {
      case SOUTH:
        z += 2;
        break;
      case NORTH:
        z -= 2;
        break;
      case EAST:
        x += 2;
        break;
      case WEST:
        x -= 2;
    }

    x += 0.5;
    y += 0.5;
    y += 2.0;
    z += 0.5;

    GlStateManager.translate( + x, + y, + z);
    GlStateManager.scale(scaleAmount, scaleAmount, scaleAmount);
    GlStateManager.rotate(360F * scaleAmount, 0, 1, 0);

    tessellator.draw();

    RenderHelper.enableStandardItemLighting();

  }
}
