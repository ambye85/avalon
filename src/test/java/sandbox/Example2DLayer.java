package sandbox;

import imgui.ImGui;
import org.joml.Vector4f;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.OrthographicCameraController;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.instrumentation.InstrumentationTimer;
import uk.ashleybye.avalon.renderer.RenderCommand;
import uk.ashleybye.avalon.renderer.Renderer2D;
import uk.ashleybye.avalon.renderer.Texture2D;
import uk.ashleybye.avalon.renderer.Transform;

public class Example2DLayer extends Layer {

  private final OrthographicCameraController cameraController = new OrthographicCameraController(
      1280.0 / 720.0);
  private final float[] squareColor = new float[]{0.8F, 0.2F, 0.3F, 1.0F};
  private final Texture2D checkerboardTexture;
  private final Transform adjustableSquareTransform = new Transform();
  private final Transform rotatedSquareTransform = new Transform();
  private final Transform checkerboardTransform = new Transform();
  private final Transform rotatedCheckerboardTransform = new Transform();

  public Example2DLayer() {
    super("2D Example Layer");

    checkerboardTexture = Texture2D.create("textures/Checkerboard.png");
  }

  @Override
  public void onAttach() {
    adjustableSquareTransform.setPosition(-1.0F, 0.0F);
    adjustableSquareTransform.setScale(0.8F, 0.8F);

    rotatedSquareTransform.setPosition(0.5F, -0.5F);
    rotatedSquareTransform.setRotation(30.0F);
    rotatedSquareTransform.setScale(0.5F, 0.75F);

    checkerboardTransform.setPosition(0.0F, 0.0F, -0.1F);
    checkerboardTransform.setScale(10.0F, 10.0F);

    rotatedCheckerboardTransform.setPosition(0.0F, 0.0F, -0.2F);
    rotatedCheckerboardTransform.setRotation(-30.0F);
    rotatedCheckerboardTransform.setScale(20.0F, 20.0F);
  }

  @Override
  public void onUpdate(double dt) {
    try (var methodTimer = new InstrumentationTimer("Example2DLayer.onUpdate")) {

      try (var cameraTimer = new InstrumentationTimer("cameraController.onUpdate")) {
        cameraController.onUpdate(dt);
      }

      try (var preRenderTimer = new InstrumentationTimer("Pre-render")) {
        RenderCommand.setClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        RenderCommand.clear();
      }

      try (var renderTimer = new InstrumentationTimer("Render")) {
        Renderer2D.beginScene(cameraController.getCamera());
        Renderer2D.drawQuad(adjustableSquareTransform, new Vector4f(squareColor));
        Renderer2D.drawQuad(rotatedSquareTransform, new Vector4f(0.2F, 0.3F, 0.8F, 1.0F));
        Renderer2D.drawQuad(checkerboardTransform, checkerboardTexture, 10.0F);
        Renderer2D.drawQuad(
            rotatedCheckerboardTransform,
            new Vector4f(0.2F, 0.3F, 0.8F, 1.0F),
            checkerboardTexture
        );
        Renderer2D.endScene();
      }
    }
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

    ImGui.begin("Profiler");
//    instrumentationResults.forEach(pr -> ImGui.text(String.format("%.3fms: %s", pr.time(), pr.name())));
//    instrumentationResults.clear();
    ImGui.end();
  }

  @Override
  public void onDetach() {
  }
}
