package uk.ashleybye.avalon.renderer;

public class Renderer {
  private static final RendererAPI rendererAPI = RendererAPI.OPEN_GL;

  public static RendererAPI getAPI() {
    return rendererAPI;
  }
}
