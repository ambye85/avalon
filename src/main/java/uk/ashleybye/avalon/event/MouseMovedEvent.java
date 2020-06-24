package uk.ashleybye.avalon.event;

public class MouseMovedEvent extends MouseEvent {

  private final double xPos;
  private final double yPos;

  public MouseMovedEvent(double xPos, double yPos) {
    super(EventType.MOUSE_MOVED);
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public double getXPos() {
    return xPos;
  }

  public double getYPos() {
    return yPos;
  }

  @Override
  public String toString() {
    return String.format("%s { x: %.2f, y: %.2f }", super.getName(), xPos, yPos);
  }
}
