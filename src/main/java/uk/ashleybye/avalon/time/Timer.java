package uk.ashleybye.avalon.time;

import uk.ashleybye.avalon.platform.macos.MacOSTimer;

public interface Timer {

  static Timer create() {
    return new MacOSTimer();
  }

  void start();

  void tick();

  double getDeltaSeconds();

  double getDeltaMillis();
}
