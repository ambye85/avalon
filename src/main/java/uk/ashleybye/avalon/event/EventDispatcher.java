package uk.ashleybye.avalon.event;

public class EventDispatcher {

  private final Event event;

  public EventDispatcher(Event event) {
    this.event = event;
  }

  public void dispatch(Class<? extends Event> eventType, EventCallback callback) {
    if (eventType.isInstance(event)) {
      event.setHandled(callback.call(event));
    }
  }
}
