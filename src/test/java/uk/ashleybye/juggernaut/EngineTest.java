package uk.ashleybye.juggernaut;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EngineTest {
  @Test
  void helloFromEngine() {
    var engine = new Engine();

    assertEquals("Welcome to Juggernaut Engine!", engine.initialise());
  }
}
