package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class KeyPressedEventTest {

  @Test
  void eventHasKeyCodeAndRepeatCount() {
    var event = new KeyPressedEvent(90, 1);

    assertThat(event.getKeyCode()).isEqualTo(90);
    assertThat(event.getRepeatCount()).isEqualTo(1);
    assertThat(event.toString()).isEqualTo("KeyPressedEvent { keyCode: 90, repeatCount: 1 }");
  }
}
