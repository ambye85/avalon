package uk.ashleybye.avalon.event;

public class WindowCloseEvent extends Event {

  public WindowCloseEvent() {
    super(EventType.WINDOW_CLOSE, EventCategory.APPLICATION);
  }

  @Override
  public String toString() {
    return super.getName();
  }
}
