package uk.ashleybye.avalon.event;

public abstract class Event {

  private final int categoryFlags;
  private final EventType eventType;
  private final String name;

  protected boolean handled = false;

  protected Event(EventType eventType, int categoryFlags) {
    this.eventType = eventType;
    this.categoryFlags = categoryFlags;
    this.name = this.getClass().getSimpleName();
  }

  public EventType getEventType() {
    return eventType;
  }

  public String getName() {
    return name;
  }

  public int getCategoryFlags() {
    return categoryFlags;
  }

  public boolean isInCategory(int category) {
    return (categoryFlags & category) == category;
  }

  public boolean isHandled() {
    return handled;
  }

  public void setHandled(boolean handled) {
    this.handled = handled;
  }

  @Override
  public abstract String toString();
}
