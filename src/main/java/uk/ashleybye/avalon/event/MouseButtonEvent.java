package uk.ashleybye.avalon.event;

public abstract class MouseButtonEvent extends MouseEvent {

  private final int button;

  protected MouseButtonEvent(EventType eventType, int button) {
    super(eventType, EventCategory.MOUSE_BUTTON);
    this.button = button;
  }

  public int getButton() {
    return button;
  }
}
