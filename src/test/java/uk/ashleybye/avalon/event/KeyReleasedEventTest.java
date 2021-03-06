package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class KeyReleasedEventTest {

  @Test
  void eventHasKeyCode() {
    var event = new KeyReleasedEvent(90);

    assertThat(event.getKeyCode()).isEqualTo(90);
    assertThat(event.toString()).isEqualTo("KeyReleasedEvent { keyCode: 90 }");
    assertThat(event.isInCategory(EventCategory.INPUT | EventCategory.KEYBOARD)).isTrue();
    assertThat(event.getEventType()).isEqualTo(EventType.KEY_RELEASED);
  }
}
