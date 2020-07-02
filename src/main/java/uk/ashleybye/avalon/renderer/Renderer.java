package uk.ashleybye.avalon.renderer;

import org.joml.Matrix4f;
import uk.ashleybye.avalon.platform.opengl.OpenGLShader;

public class Renderer {

  private static final RendererAPIVersion RENDERER_API_VERSION = RendererAPIVersion.OPEN_GL;
  private static Matrix4f viewProjectionMatrix;

  public static RendererAPIVersion getAPI() {
    return RENDERER_API_VERSION;
  }

  public static void init() {
    RenderCommand.init();
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
    // It can only be this for now because nothing else has been implemented, so this is fine.
    ((OpenGLShader) shader).uploadUniform("u_ViewProjection", viewProjectionMatrix);
    ((OpenGLShader) shader).uploadUniform("u_Transform", transform);

    vertexArray.bind();

    RenderCommand.drawIndexed(vertexArray);

    vertexArray.unbind();

    shader.unbind();
  }
}
