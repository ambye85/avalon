package uk.ashleybye.avalon.event;

public class MouseButtonReleasedEvent extends MouseButtonEvent {

  public MouseButtonReleasedEvent(int button) {
    super(EventType.MOUSE_BUTTON_RELEASED, button);
  }

  @Override
  public String toString() {
    return String.format("%s { button: %d }", super.getName(), super.getButton());
  }
}
