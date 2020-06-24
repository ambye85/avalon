package uk.ashleybye.avalon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class BitTest {

  @Test
  void setsBitForZero() {
    int b = Bit.bit(0);

    assertThat(b).isEqualTo(1);
  }

  @Test
  void setsBitForOne() {
    int b = Bit.bit(1);

    assertThat(b).isEqualTo(2);
  }
}
