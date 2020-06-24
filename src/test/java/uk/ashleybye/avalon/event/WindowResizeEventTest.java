package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class WindowResizeEventTest {

  @Test
  void eventHasWindowWidthAndHeight() {
    var event = new WindowResizeEvent(100, 400);

    assertThat(event.getWidth()).isEqualTo(100);
    assertThat(event.getHeight()).isEqualTo(400);
    assertThat(event.toString()).isEqualTo("WindowResizeEvent { width: 100, height: 400 }");
  }
}
