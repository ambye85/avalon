package uk.ashleybye.avalon.event;

public class KeyTypedEvent extends KeyboardEvent {

  public KeyTypedEvent(int keyCode) {
    super(EventType.KEY_TYPED, keyCode);
  }

  @Override
  public String toString() {
    return String.format("%s { keyCode: %d }", super.getName(), super.getKeyCode());
  }
}
