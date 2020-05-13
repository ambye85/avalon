package uk.ashleybye.juggernaut;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Engine {

  private Application application;

  public Engine(Application application) {
    this.application = application;
  }

  public void start() {
    application.initialise();
  }
}
