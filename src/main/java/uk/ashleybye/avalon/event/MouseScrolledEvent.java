package uk.ashleybye.avalon.event;

public class MouseScrolledEvent extends MouseEvent {

  private final double xOffset;
  private final double yOffset;

  public MouseScrolledEvent(double xOffset, double yOffset) {
    super(EventType.MOUSE_SCROLLED);
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public double getXOffset() {
    return xOffset;
  }

  public double getYOffset() {
    return yOffset;
  }

  @Override
  public String toString() {
    return String.format("%s { xOffset: %.2f, yOffset: %.2f }", super.getName(), xOffset, yOffset);
  }
}
