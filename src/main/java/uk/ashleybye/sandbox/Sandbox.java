package uk.ashleybye.sandbox;

import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.Logger;
import uk.ashleybye.avalon.Logger.Color;
import uk.ashleybye.avalon.event.Event;

public class Sandbox extends Application {

  public static void main(String[] args) {
    var sandbox = new Sandbox();
    sandbox.run();
  }

  public Sandbox() {
    super();
    pushLayer(new ExampleLayer());
  }
}

class ExampleLayer extends Layer {

  Logger logger = Logger.builder("APP", Color.CYAN).build();

  public ExampleLayer() {
    super("Example Layer");
  }

  @Override
  public void onUpdate() {
    logger.log(getDebugName() + "::Update");
  }

  @Override
  public void onEvent(Event event) {
    logger.log(event.toString());
  }
}
