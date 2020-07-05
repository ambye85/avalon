package sandbox;

import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.OrthographicCameraController;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.renderer.RenderCommand;
import uk.ashleybye.avalon.renderer.Renderer2D;
import uk.ashleybye.avalon.renderer.Texture;
import uk.ashleybye.avalon.renderer.Texture2D;
import uk.ashleybye.avalon.renderer.VertexArray;

public class Example2DLayer extends Layer {

  private final OrthographicCameraController cameraController = new OrthographicCameraController(
      1280.0 / 720.0);
  private final float[] squareColor = new float[]{0.8F, 0.2F, 0.3F, 1.0F};
  private final Texture checkerboardTexture;
  private VertexArray vertexArray;

  public Example2DLayer() {
    super("2D Example Layer");

    checkerboardTexture = Texture2D.create("textures/Checkerboard.png");
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
    Renderer2D.drawQuad(
        new Vector2f(-1.0F, 0.0F),
        new Vector2f(0.8F, 0.8F),
        new Vector4f(squareColor)
    );
    Renderer2D.drawQuad(
        new Vector2f(0.5F, -0.5F),
        30.0F,
        new Vector2f(0.5F, 0.75F),
        new Vector4f(0.2F, 0.3F, 0.8F, 1.0F)
    );
    Renderer2D.drawQuad(
        new Vector3f(0.0F, 0.0F, -0.1F),
        new Vector2f(10.0F, 10.0F),
        checkerboardTexture
    );

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
