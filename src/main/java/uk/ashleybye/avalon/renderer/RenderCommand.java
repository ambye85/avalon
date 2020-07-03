package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLRendererAPI;

public class RenderCommand {

  private static final RendererAPI rendererAPI = switch (Renderer.getAPIVersion()) {
    case NONE -> throw new RuntimeException(
        Renderer.getAPIVersion().toString() + " is not supported");
    case OPEN_GL -> new OpenGLRendererAPI();
  };

  static void init() {
    rendererAPI.init();
  }

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
