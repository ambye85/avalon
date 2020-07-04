package sandbox;

import imgui.ImGui;
import org.joml.Matrix4f;
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
import uk.ashleybye.avalon.renderer.Shader;
import uk.ashleybye.avalon.renderer.ShaderDataType;
import uk.ashleybye.avalon.renderer.VertexArray;
import uk.ashleybye.avalon.renderer.VertexBuffer;

public class Example2DLayer extends Layer {

  private final OrthographicCameraController cameraController = new OrthographicCameraController(
      1280.0 / 720.0);
  private final float[] squareColor = new float[]{0.2F, 0.3F, 0.8F, 1.0F};
  private final Shader shader = Shader.create("shaders/flatColour.glsl");
  private VertexArray vertexArray;

  public Example2DLayer() {
    super("2D Example Layer");
  }

  @Override
  public void onAttach() {
    float[] vertices = new float[]{
        -0.5F, -0.5F, 0.0F, 0.0F, 0.0F,
        +0.5F, -0.5F, 0.0F, 1.0F, 0.0F,
        +0.5F, +0.5F, 0.0F, 1.0F, 1.0F,
        -0.5F, +0.5F, 0.0F, 0.0F, 1.0F,
    };

    vertexArray = VertexArray.create();
    BufferLayout layout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .addElement(ShaderDataType.FLOAT_2, "a_TexCoord")
        .build();
    var vertexBuffer = VertexBuffer.create(vertices);
    vertexBuffer.setLayout(layout);
    vertexArray.addVertexBuffer(vertexBuffer);

    int[] indices = new int[]{0, 1, 2, 2, 3, 0};
    var indexBuffer = IndexBuffer.create(indices);
    vertexArray.setIndexBuffer(indexBuffer);
  }

  @Override
  public void onUpdate(double dt) {
    cameraController.onUpdate(dt);

    RenderCommand.setClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    RenderCommand.clear();

    Renderer.beginScene(cameraController.getCamera());

    shader.bind();
    ((OpenGLShader) shader).uploadUniform("u_Color", new Vector4f(squareColor));
    Matrix4f transform = new Matrix4f().translate(new Vector3f());
    Renderer.submit(shader, vertexArray, transform);

    Renderer.endScene();
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
    shader.dispose();
  }
}
