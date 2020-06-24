package uk.ashleybye.avalon.event;

public abstract class KeyboardEvent extends Event {

  private final int keyCode;

  protected KeyboardEvent(EventType eventType, int keyCode) {
    super(eventType, EventCategory.INPUT | EventCategory.KEYBOARD);
    this.keyCode = keyCode;
  }

  public int getKeyCode() {
    return keyCode;
  }
}
