package uk.ashleybye.juggernaut;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EngineTest {

  @Test
  void startEngineLoadsApplication() {
    ApplicationSpy application = new ApplicationSpy();
    var engine = new Engine(application);

    engine.start();

    assertTrue(application.wasInitialised());
  }
}
