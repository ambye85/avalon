package uk.ashleybye.avalon.instrumentation;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class InstrumentationTimer implements AutoCloseable {

  private final String name;
  private final Instant start;
  private boolean stopped;

  public InstrumentationTimer(String name) {
    this.name = name;
    stopped = false;
    start = Instant.now(Clock.systemUTC());
  }

  public void stop() {
    Instant end = Instant.now(Clock.systemUTC());

//    var duration = Duration.between(start, end).toNanos() * 0.000001;

    var threadId = Thread.currentThread().getId();
    Instrumentor.get().writeProfile(new InstrumentationResult(name, start, end, threadId));
    stopped = true;
  }

  @Override
  public void close() {
    if (!stopped) {
      stop();
    }
  }
}
