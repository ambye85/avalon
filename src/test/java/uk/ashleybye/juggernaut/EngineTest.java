package uk.ashleybye.juggernaut;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EngineTest {
  @Test
  void helloFromEngine() {
    var engine = new Engine();

    assertEquals("Welcome to Juggernaut Engine!", engine.initialise());
  }

  @Test
  void engineLoadsApplicationFromClassName()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    var engine = new Engine();
    engine.createApplication("uk.ashleybye.juggernaut.ApplicationSpy");

    var application = engine.getApplication();

    assertTrue(application instanceof ApplicationSpy);
  }
}
