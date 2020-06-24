package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class KeyTypedEventTest {

  @Test
  void eventHasKeyCode() {
    var event = new KeyTypedEvent(90);

    assertThat(event.getKeyCode()).isEqualTo(90);
    assertThat(event.toString()).isEqualTo("KeyTypedEvent { keyCode: 90 }");
    assertThat(event.isInCategory(EventCategory.INPUT | EventCategory.KEYBOARD)).isTrue();
    assertThat(event.getEventType()).isEqualTo(EventType.KEY_TYPED);
  }
}
