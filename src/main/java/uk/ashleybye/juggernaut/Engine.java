package uk.ashleybye.juggernaut;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Engine {
  private Application application;

  public String initialise() {
    return "Welcome to Juggernaut Engine!";
  }

  public void createApplication(String className)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class<?> applicationClass = Class.forName(className);
    Constructor<?> constructor = applicationClass.getConstructor();
    Application application = (Application) constructor.newInstance();

    this.application = application;
  }

  public Application getApplication() {
    return application;
  }
}
