package uk.ashleybye.avalon.platform.macos;

import org.lwjgl.glfw.GLFW;
import uk.ashleybye.avalon.time.Timer;
import uk.ashleybye.avalon.time.TimerFunction;

public class MacOSTimer implements Timer {

  private final TimerFunction timerFunction;
  private double lastFrameTime;
  private double deltaTimeInSeconds;

  public MacOSTimer() {
    this(GLFW::glfwGetTime);
  }

  public MacOSTimer(TimerFunction timerFunction) {
    this.timerFunction = timerFunction;
  }

  @Override
  public void start() {
    lastFrameTime = 0.0;
    deltaTimeInSeconds = 0.0;
  }

  @Override
  public void tick() {
    double time = timerFunction.getSeconds();
    deltaTimeInSeconds = time - lastFrameTime;
    lastFrameTime = time;
  }

  @Override
  public double getDeltaSeconds() {
    return deltaTimeInSeconds;
  }

  @Override
  public double getDeltaMillis() {
    return deltaTimeInSeconds * 1000.0F;
  }
}
