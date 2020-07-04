package uk.ashleybye.avalon;

import org.joml.Vector3f;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.MouseScrolledEvent;
import uk.ashleybye.avalon.event.WindowResizeEvent;
import uk.ashleybye.avalon.input.Input;
import uk.ashleybye.avalon.input.KeyCodes;
import uk.ashleybye.avalon.renderer.OrthographicCamera;

public class OrthographicCameraController {

  private OrthographicCamera camera;
  private double aspectRatio;
  private boolean rotationEnabled = false;
  private Vector3f position = new Vector3f(0.0F, 0.0F, 0.0F);
  private double translationSpeed = 5.0;
  private double rotation = 0.0;
  private float rotationSpeed = 180.0F;
  private double zoomLevel = 1.0;
  private float zoomSpeed = 0.25F;

  public OrthographicCameraController(double aspectRatio) {
    /*
     * Assuming an aspect ration of 16:9, the following are equivalent using NDC.
     * camera = new OrthographicCamera(-1.6F, 1.6F, -0.9F, 0.9F);
     * camera = new OrthographicCamera(
     *   (float) -(aspectRatio * zoomLevel),
     *   (float) (aspectRatio * zoomLevel),
     *   (float) -zoomLevel,
     *   (float) zoomLevel
     * );
     */
    this.aspectRatio = aspectRatio;
    camera = new OrthographicCamera(
        (float) -(this.aspectRatio * zoomLevel),
        (float) (this.aspectRatio * zoomLevel),
        (float) -zoomLevel,
        (float) zoomLevel
    );
  }

  public void setRotationEnabled(boolean enabled) {
    rotationEnabled = enabled;
  }

  public void onUpdate(double dt) {
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_W)) {
      position.y += translationSpeed * dt;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_S)) {
      position.y -= translationSpeed * dt;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_A)) {
      position.x -= translationSpeed * dt;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_D)) {
      position.x += translationSpeed * dt;
    }

    camera.setPosition(position);

    if (rotationEnabled) {
      if (Input.isKeyPressed(KeyCodes.AVALON_KEY_Q)) {
        rotation += rotationSpeed * dt;
      }
      if (Input.isKeyPressed(KeyCodes.AVALON_KEY_E)) {
        rotation -= rotationSpeed * dt;
      }

      camera.setRotation((float) rotation);
    }

    translationSpeed = zoomLevel;
  }

  public void onEvent(Event event) {
    var dispatcher = new EventDispatcher(event);
    dispatcher.dispatch(MouseScrolledEvent.class, this::onMouseScrolled);
    dispatcher.dispatch(WindowResizeEvent.class, this::onWindowResized);
  }

  private boolean onMouseScrolled(Event event) {
    var e = (MouseScrolledEvent) event;
    zoomLevel -= e.getYOffset() * zoomSpeed;
    zoomLevel = clamp(zoomLevel, 0.25);
    camera.setProjection(
        (float) -(this.aspectRatio * zoomLevel),
        (float) (this.aspectRatio * zoomLevel),
        (float) -zoomLevel,
        (float) zoomLevel
    );
    return false;
  }

  private double clamp(double value, double minimum) {
    return Math.max(value, minimum);
  }

  private boolean onWindowResized(Event event) {
    var e = (WindowResizeEvent) event;
    aspectRatio = (float) e.getWidth() / (float) e.getHeight();
    camera.setProjection(
        (float) -(this.aspectRatio * zoomLevel),
        (float) (this.aspectRatio * zoomLevel),
        (float) -zoomLevel,
        (float) zoomLevel
    );
    return false;
  }

  public OrthographicCamera getCamera() {
    return camera;
  }
}
