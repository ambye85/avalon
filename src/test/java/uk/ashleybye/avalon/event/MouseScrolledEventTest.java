package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class MouseScrolledEventTest {

  @Test
  void eventHasXAndYOffsets() {
    var event = new MouseScrolledEvent(12.3, 45.6);

    assertThat(event.getXOffset()).isWithin(1.0e-10).of(12.3);
    assertThat(event.getYOffset()).isWithin(1.0e-10).of(45.6);
    assertThat(event.toString()).isEqualTo("MouseScrolledEvent { xOffset: 12.30, yOffset: 45.60 }");
  }
}
