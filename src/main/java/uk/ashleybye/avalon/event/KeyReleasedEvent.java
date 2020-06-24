package uk.ashleybye.avalon.event;

public class KeyReleasedEvent extends KeyboardEvent {

  public KeyReleasedEvent(int keyCode) {
    super(EventType.KEY_RELEASED, keyCode);
  }

  @Override
  public String toString() {
    return String.format("%s { keyCode: %d }", super.getName(), super.getKeyCode());
  }
}
