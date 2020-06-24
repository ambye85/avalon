package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;
import static uk.ashleybye.avalon.event.EventCategory.INPUT;
import static uk.ashleybye.avalon.event.EventCategory.MOUSE;
import static uk.ashleybye.avalon.event.EventCategory.MOUSE_BUTTON;

import org.junit.jupiter.api.Test;

class MouseButtonReleasedEventTest {

  @Test
  void eventHasMouseButton() {
    var event = new MouseButtonReleasedEvent(1);

    assertThat(event.getButton()).isEqualTo(1);
    assertThat(event.toString()).isEqualTo("MouseButtonReleasedEvent { button: 1 }");
    assertThat(event.isInCategory(INPUT | MOUSE | MOUSE_BUTTON)).isTrue();
    assertThat(event.getEventType()).isEqualTo(EventType.MOUSE_BUTTON_RELEASED);
  }
}
