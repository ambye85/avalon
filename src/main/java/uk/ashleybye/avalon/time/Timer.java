package uk.ashleybye.avalon.time;

public interface Timer {

  void start();

  void tick();

  double getDeltaSeconds();

  double getDeltaMillis();
}
