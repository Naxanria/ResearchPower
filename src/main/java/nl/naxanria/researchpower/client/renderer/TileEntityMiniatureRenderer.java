package nl.naxanria.researchpower.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.tile.machines.TileEntityMiniatureController;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.*;

import java.nio.charset.Charset;

public class TileEntityMiniatureRenderer extends TileEntitySpecialRenderer<TileEntityMiniatureController>
{

  private boolean shaderSetup = false;
  private boolean useShader = false;

  public static long start = System.currentTimeMillis();

  BlockRendererDispatcher renderDispatcher;
  @Override
  public void render(TileEntityMiniatureController controller, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
  {
    if (!shaderSetup) {
      System.out.println(useShader = setUpShader());
      shaderSetup = true;
    }

    if (!controller.structureGood || !controller.isInProgress())
    {
      return;
    }
    if (renderDispatcher == null)
    {
      renderDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    }
    
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
 
    float scaleAmount = 1.0F - (((float) controller.getProgress() + partialTicks) / (float) controller.getTotalTime());

    bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);

    for (int i = 0; i < 27; i++)
    {
      bufferbuilder.setTranslation(-positions[4 + 9].getX() + -0.5, -positions[4 + 9].getY() + -0.5, -positions[4 + 9].getZ() + -0.5);

      renderDispatcher.getBlockModelRenderer()
        .renderModel(controller.getWorld(), this.renderDispatcher.getModelForState(controller.processingRecipe[i]), controller.processingRecipe[i], positions[i], bufferbuilder, false);

      bufferbuilder.setTranslation(0, 0, 0);
    }

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

    GlStateManager.pushMatrix();
    {
      GlStateManager.translate(+x, +y, +z);
      GlStateManager.scale(scaleAmount, scaleAmount, scaleAmount);
      GlStateManager.rotate(360F * scaleAmount, 0, 1, 0);

      tessellator.draw();
    }
    GlStateManager.popMatrix();

    doRenderShader();

    RenderHelper.enableStandardItemLighting();

  }

  private int createShader(ResourceLocation location, int shaderType) throws Exception {
    int shader = 0;
    try {
      shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

      if(shader == 0)
        return 0;

      ARBShaderObjects.glShaderSourceARB(shader, IOUtils.toString(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream(), Charset.defaultCharset()));
      ARBShaderObjects.glCompileShaderARB(shader);

      if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

      return shader;
    }
    catch(Exception exc) {
      ARBShaderObjects.glDeleteObjectARB(shader);
      throw exc;
    }
  }

  private static String getLogInfo(int obj) {
    return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
  }

  private int program=0;

  int fragShader = 0;

  private boolean setUpShader()
  {

    try {
      fragShader = createShader(new ResourceLocation(ResearchPower.MOD_ID, "electric.glsl"), ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    }
    catch(Exception exc) {
      exc.printStackTrace();
      return false;
    }
    finally {
      if(fragShader == 0)
        return false;
    }

    program = ARBShaderObjects.glCreateProgramObjectARB();

    if(program == 0)
      return false;

    ARBShaderObjects.glAttachObjectARB(program, fragShader);

    ARBShaderObjects.glLinkProgramARB(program);
    if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
      System.err.println(getLogInfo(program));
      return false;
    }

    ARBShaderObjects.glValidateProgramARB(program);
    if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
      System.err.println(getLogInfo(program));
      return false;
    }

    return true;
  }

  private void doRenderShader()
  {

    if(useShader)
    {
      ARBShaderObjects.glUseProgramObjectARB(program);
      int time = GL20.glGetUniformLocation(program, "time");
      int resolution = GL20.glGetUniformLocation(program, "resolution");
      GL20.glUniform1f(time, (System.currentTimeMillis() - start) / 20);
      //GL20.glUniform2i(resolution, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }


    GL11.glPushMatrix();

    GL11.glTranslatef(0.0f, 0.0f, -10.0f);
    GL11.glColor3f(1.0f, 1.0f, 1.0f);//white

    GL11.glBegin(GL11.GL_QUADS);
    GL11.glVertex3f(-100.0f, 100.0f, 0.0f);
    GL11.glVertex3f(100.0f, 100.0f, 0.0f);
    GL11.glVertex3f(100.0f, -10.0f, 0.0f);
    GL11.glVertex3f(-100.0f, -100.0f, 0.0f);
    GL11.glEnd();

    GL11.glPopMatrix();

    //release the shader
    if(useShader)
      ARBShaderObjects.glUseProgramObjectARB(0);
  }
}
