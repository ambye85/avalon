package uk.ashleybye.avalon.event;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class WindowCloseEventTest {

  @Test
  void eventHasCorrectInformation() {
    var event = new WindowCloseEvent();

    assertThat(event.toString()).isEqualTo("WindowCloseEvent");
  }
}
