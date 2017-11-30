package nl.naxanria.researchpower.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

    bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);

    BlockPos[] positions = controller.getBlockPositions();
 
    float scaleAmount = 1.0F - (((float) controller.getProgress() + partialTicks) / (float) controller.getTotalTime());

    Log.info(controller.dir.toString());

    BlockPos ourPos = positions[0];

    for (int i = 0; i < 27; i++)
    {
      bufferbuilder.setTranslation((double)blockPos.getX() - ourPos.getX(), (double)blockPos.getY() - ourPos.getY(), (double)blockPos.getZ() - ourPos.getZ());

      renderDispatcher.getBlockModelRenderer()
        .renderModel(controller.getWorld(), this.renderDispatcher.getModelForState(controller.processingRecipe[i]), controller.processingRecipe[i], positions[i], bufferbuilder, false);

      bufferbuilder.setTranslation(0, 0, 0);
    }

    double xTranslate = -blockPos.getX();
    double yTranslate = -blockPos.getY();
    double zTranslate = -blockPos.getZ();

    xTranslate += x / scaleAmount;
    yTranslate += y / scaleAmount;
    zTranslate += z / scaleAmount;

    double scaleTranslateX, scaleTranslateZ, scaleTranslateY;
    scaleTranslateX = scaleTranslateY = scaleTranslateZ = 0;

    double translateOffset = 1.50 - (1.50 * scaleAmount);

    BlockPos tempPos = blockPos.up().offset(controller.dir, 1).offset(controller.dir.rotateAround(EnumFacing.Axis.Y).getOpposite(), 1);

    GlStateManager.translate(scaleTranslateX, scaleTranslateY, scaleTranslateZ);
    GlStateManager.pushMatrix();
    {
      GL11.glScalef(scaleAmount, scaleAmount, scaleAmount);
      GlStateManager.translate(xTranslate, yTranslate, zTranslate);
  
      tessellator.draw();
    }
    GlStateManager.popMatrix();

    RenderHelper.enableStandardItemLighting();

  }
}