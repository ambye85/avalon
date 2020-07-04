package uk.ashleybye.avalon;

import uk.ashleybye.avalon.event.Event;

public abstract class Layer {

  private final String debugName;

  public Layer(String debugName) {
    this.debugName = debugName;
  }

  public void onAttach() {
    // Intentionally not implemented.
  }

  public void onDetach() {
    // Intentionally not implemented.
  }

  public void onUpdate(double dt) {
    // Intentionally not implemented.
  }

  public void onEvent(Event event) {
    // Intentionally not implemented.
  }

  public void onImGuiRender() {
    // Intentionally not implemented.
  }

  public final String getDebugName() {
    return debugName;
  }
}
