package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class MouseButtonReleasedEventTest {

  @Test
  void eventHasMouseButton() {
    var event = new MouseButtonReleasedEvent(1);

    assertThat(event.getButton()).isEqualTo(1);
    assertThat(event.toString()).isEqualTo("MouseButtonReleasedEvent { button: 1 }");
  }
}
