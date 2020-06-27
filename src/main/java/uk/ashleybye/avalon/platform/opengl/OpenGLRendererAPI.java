package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL11C.glDrawElements;

import uk.ashleybye.avalon.renderer.RendererAPI;
import uk.ashleybye.avalon.renderer.VertexArray;

public class OpenGLRendererAPI implements RendererAPI {

  @Override
  public void setClearColor(float r, float g, float b, float a) {
    glClearColor(r, g, b, a);
  }

  @Override
  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  @Override
  public void drawIndexed(VertexArray vertexArray) {
    glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0L);
  }
}