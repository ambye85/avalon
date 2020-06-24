package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class MouseButtonPressedEventTest {

  @Test
  void eventHasMouseButton() {
    var event = new MouseButtonPressedEvent(1);

    assertThat(event.getButton()).isEqualTo(1);
    assertThat(event.toString()).isEqualTo("MouseButtonPressedEvent { button: 1 }");
  }
}
