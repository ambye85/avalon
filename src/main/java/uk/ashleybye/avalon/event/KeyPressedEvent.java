package uk.ashleybye.avalon.event;

public class KeyPressedEvent extends KeyboardEvent {

  private final int repeatCount;

  public KeyPressedEvent(int keyCode, int repeatCount) {
    super(EventType.KEY_PRESSED, keyCode);
    this.repeatCount = repeatCount;
  }

  public int getRepeatCount() {
    return repeatCount;
  }

  @Override
  public String toString() {
    return String.format("%s { keyCode: %d, repeatCount: %d }", super.getName(), super.getKeyCode(),
        repeatCount);
  }
}
