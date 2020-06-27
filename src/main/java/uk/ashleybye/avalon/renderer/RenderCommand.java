package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLRendererAPI;

public class RenderCommand {

  private static RendererAPI rendererAPI = new OpenGLRendererAPI();

  public static void setClearColor(float r, float g, float b, float a) {
    rendererAPI.setClearColor(r, g, b, a);
  }

  public static void clear() {
    rendererAPI.clear();
  }

  public static void drawIndexed(VertexArray vertexArray) {
    rendererAPI.drawIndexed(vertexArray);
  }
}
