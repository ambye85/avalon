package uk.ashleybye.avalon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventCategory;
import uk.ashleybye.avalon.event.EventType;

public class EventTest {

  @Test
  void eventNotInCategory() {
    var event = new AnEvent(EventType.WINDOW_RESIZE, EventCategory.APPLICATION);

    assertThat(event.isInCategory(EventCategory.KEYBOARD)).isFalse();
  }

  @Test
  void eventInCategory() {
    var event = new AnEvent(EventType.WINDOW_RESIZE, EventCategory.APPLICATION);

    assertThat(event.isInCategory(EventCategory.APPLICATION)).isTrue();
  }

  @Test
  void eventInMultipleCategories() {
    var event = new AnEvent(EventType.WINDOW_RESIZE, EventCategory.INPUT | EventCategory.KEYBOARD);

    assertThat(event.isInCategory(EventCategory.INPUT)).isTrue();
    assertThat(event.isInCategory(EventCategory.KEYBOARD)).isTrue();
  }

  @Test
  void eventNameMatchesClassName() {
    var event = new AnEvent(EventType.NONE, EventCategory.NONE);

    assertThat(event.toString()).matches("AnEvent");
  }

  @Test
  void eventCategoryFlagsCorrect() {
    var event = new AnEvent(EventType.NONE, EventCategory.INPUT | EventCategory.MOUSE);

    assertThat(event.getCategoryFlags()).isEqualTo(EventCategory.INPUT | EventCategory.MOUSE);
  }
}

class AnEvent extends Event {

  protected AnEvent(EventType eventType, int categoryFlags) {
    super(eventType, categoryFlags);
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
