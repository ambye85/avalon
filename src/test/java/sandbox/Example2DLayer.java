package sandbox;

import imgui.ImGui;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.OrthographicCameraController;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.platform.opengl.OpenGLShader;
import uk.ashleybye.avalon.renderer.BufferLayout;
import uk.ashleybye.avalon.renderer.IndexBuffer;
import uk.ashleybye.avalon.renderer.RenderCommand;
import uk.ashleybye.avalon.renderer.Renderer;
import uk.ashleybye.avalon.renderer.Renderer2D;
import uk.ashleybye.avalon.renderer.Shader;
import uk.ashleybye.avalon.renderer.ShaderDataType;
import uk.ashleybye.avalon.renderer.VertexArray;
import uk.ashleybye.avalon.renderer.VertexBuffer;

public class Example2DLayer extends Layer {

  private final OrthographicCameraController cameraController = new OrthographicCameraController(
      1280.0 / 720.0);
  private final float[] squareColor = new float[]{0.8F, 0.2F, 0.3F, 1.0F};
  private VertexArray vertexArray;

  public Example2DLayer() {
    super("2D Example Layer");
  }

  @Override
  public void onAttach() {
  }

  @Override
  public void onUpdate(double dt) {
    cameraController.onUpdate(dt);

    RenderCommand.setClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    RenderCommand.clear();

    Renderer2D.beginScene(cameraController.getCamera());

//    shader.bind();
//    ((OpenGLShader) shader).uploadUniform("u_Color", new Vector4f(squareColor));
//    Matrix4f transform = new Matrix4f().translate(new Vector3f());
//    Renderer2D.submit(shader, vertexArray, transform);
    Renderer2D.drawQuad(new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector4f(squareColor));

    Renderer2D.endScene();
  }

  @Override
  public void onEvent(Event event) {
    cameraController.onEvent(event);
  }

  @Override
  public void onImGuiRender() {
    ImGui.begin("Settings");
    ImGui.colorEdit4("Square Color", squareColor);
    ImGui.end();
  }

  @Override
  public void onDetach() {
  }
}
