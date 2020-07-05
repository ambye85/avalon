package uk.ashleybye.avalon.renderer;

import org.joml.Matrix4f;

public class Renderer {

  private static final RendererAPIVersion RENDERER_API_VERSION = RendererAPIVersion.OPEN_GL;
  private static Matrix4f viewProjectionMatrix;

  public static void init() {
    RenderCommand.init();
    Renderer2D.init();
  }

  public static void dispose() {
    Renderer2D.dispose();
  }

  public static void onWindowResize(int width, int height) {
    RenderCommand.setViewport(0, 0, width, height);
  }

  public static RendererAPIVersion getAPIVersion() {
    return RENDERER_API_VERSION;
  }

  public static void beginScene(OrthographicCamera camera) {
    viewProjectionMatrix = camera.getViewProjectionMatrix();
  }

  public static void endScene() {

  }

  public static void submit(Shader shader, VertexArray vertexArray) {
    submit(shader, vertexArray, new Matrix4f());
  }

  public static void submit(Shader shader, VertexArray vertexArray, Matrix4f transform) {
    shader.bind();
    shader.setData("u_ViewProjection", viewProjectionMatrix);
    shader.setData("u_Transform", transform);

    vertexArray.bind();

    RenderCommand.drawIndexed(vertexArray);

    vertexArray.unbind();

    shader.unbind();
  }
}
