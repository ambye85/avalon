package uk.ashleybye.avalon.renderer;

public class Renderer {

  private static final RendererAPIVersion RENDERER_API_VERSION = RendererAPIVersion.OPEN_GL;

  public static RendererAPIVersion getAPI() {
    return RENDERER_API_VERSION;
  }

  public static void beginScene() {
    // Setup uniforms for the in-use shader.
  }

  public static void endScene() {

  }

  public static void submit(VertexArray vertexArray) {
    vertexArray.bind();
    RenderCommand.drawIndexed(vertexArray);
    vertexArray.unbind();
  }
}
