package uk.ashleybye.avalon.event;

public class MouseButtonPressedEvent extends MouseButtonEvent {

  public MouseButtonPressedEvent(int button) {
    super(EventType.MOUSE_BUTTON_PRESSED, button);
  }

  @Override
  public String toString() {
    return String.format("%s { button: %d }", super.getName(), super.getButton());
  }
}
