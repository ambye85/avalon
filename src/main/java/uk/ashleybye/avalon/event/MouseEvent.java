package uk.ashleybye.avalon.event;

public abstract class MouseEvent extends Event {

  protected MouseEvent(EventType eventType) {
    this(eventType, EventCategory.NONE);
  }

  protected MouseEvent(EventType eventType, int eventCategory) {
    super(eventType, EventCategory.INPUT | EventCategory.MOUSE | eventCategory);
  }
}
