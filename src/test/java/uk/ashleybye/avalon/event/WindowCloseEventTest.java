package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;
import static uk.ashleybye.avalon.event.EventCategory.APPLICATION;

import org.junit.jupiter.api.Test;

class WindowCloseEventTest {

  @Test
  void eventHasCorrectInformation() {
    var event = new WindowCloseEvent();

    assertThat(event.toString()).isEqualTo("WindowCloseEvent");
    assertThat(event.isInCategory(APPLICATION)).isTrue();
    assertThat(event.getEventType()).isEqualTo(EventType.WINDOW_CLOSE);
  }
}
