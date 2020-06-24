package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class MouseMovedEventTest {

  @Test
  void eventHasNewXAndYCoordinates() {
    var event = new MouseMovedEvent(12.3, 45.6);

    assertThat(event.getXPos()).isWithin(1.0e-10).of(12.3);
    assertThat(event.getYPos()).isWithin(1.0e-10).of(45.6);
    assertThat(event.toString()).isEqualTo("MouseMovedEvent { x: 12.30, y: 45.60 }");
  }
}
