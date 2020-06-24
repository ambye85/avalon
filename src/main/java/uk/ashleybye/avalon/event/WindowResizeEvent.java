package uk.ashleybye.avalon.event;

public class WindowResizeEvent extends Event {

  private final int width;
  private final int height;

  public WindowResizeEvent(int width, int height) {
    super(EventType.WINDOW_RESIZE, EventCategory.APPLICATION);
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return String.format("%s { width: %d, height: %d }", super.getName(), width, height);
  }
}
