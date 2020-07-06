package uk.ashleybye.avalon.profiler;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class ProfilerTimer {

  private final String name;
  private final ProfilerFunction profilerFunction;
  private final Instant start;
  private boolean stopped;

  public ProfilerTimer(String name, ProfilerFunction profilerFunction) {
    this.name = name;
    this.profilerFunction = profilerFunction;
    stopped = false;
    start = Instant.now(Clock.systemUTC());
  }

  public void stop() {
    Instant end = Instant.now(Clock.systemUTC());

    var duration = Duration.between(start, end).toNanos() * 0.000001;

    profilerFunction.processResult(new ProfilerResult(name, duration));
  }
}
