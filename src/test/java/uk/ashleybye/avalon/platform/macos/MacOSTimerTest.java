package uk.ashleybye.avalon.platform.macos;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ashleybye.avalon.time.Timer;

class TimerFunctionStub {

  private static final double timestep = 0.016;
  private static int offset = 1;

  public static double getTime() {
    return timestep * offset++;
  }
}

public class MacOSTimerTest {

  private Timer timer;

  @BeforeEach
  void setUp() {
    timer = new MacOSTimer(TimerFunctionStub::getTime);
  }

  @Test
  void deltaTimeIsZeroIfTimerNotStarted() {
    assertThat(timer.getDeltaSeconds()).isEqualTo(0.0);
  }

  @Test
  void deltaTimeIsZeroIfTimerNotTicked() {
    timer.start();

    assertThat(timer.getDeltaSeconds()).isEqualTo(0.0);
  }

  @Test
  void deltaTimeAfterOneTick() {
    timer.start();
    timer.tick();

    assertThat(timer.getDeltaSeconds()).isEqualTo(0.016);
    assertThat(timer.getDeltaMillis()).isEqualTo(16.0);
  }

  @Test
  void deltaTimeAfterTwoTicks() {
    timer.start();
    timer.tick();
    timer.tick();

    assertThat(timer.getDeltaSeconds()).isEqualTo(0.016);
    assertThat(timer.getDeltaMillis()).isEqualTo(16.0);
  }
}
