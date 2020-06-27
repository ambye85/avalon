package uk.ashleybye.avalon.renderer;

import org.joml.Matrix4f;

public class Renderer {

  private static final RendererAPIVersion RENDERER_API_VERSION = RendererAPIVersion.OPEN_GL;
  private static Matrix4f viewProjectionMatrix;

  public static RendererAPIVersion getAPI() {
    return RENDERER_API_VERSION;
  }

  public static void beginScene(OrthographicCamera camera) {
    viewProjectionMatrix = camera.getViewProjectionMatrix();
  }

  public static void endScene() {

  }

  public static void submit(Shader shader, VertexArray vertexArray) {
    shader.bind();
    shader.uploadUniform("u_ViewProjection", viewProjectionMatrix);

    vertexArray.bind();

    RenderCommand.drawIndexed(vertexArray);

    vertexArray.unbind();

    shader.unbind();
  }
}
