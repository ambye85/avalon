package uk.ashleybye.sandbox;

import static uk.ashleybye.avalon.input.KeyCodes.AVALON_KEY_TAB;

import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.input.Input;

public class Sandbox extends Application {

  public static void main(String[] args) {
    var sandbox = new Sandbox();
    sandbox.run();
  }

  public Sandbox() {
    super();
    super.pushLayer(new ExampleLayer());
  }
}

class ExampleLayer extends Layer {
  public ExampleLayer() {
    super("Example Layer");
  }

  @Override
  public void onUpdate() {
    if (Input.isKeyPressed(AVALON_KEY_TAB)) {
      System.out.println("TAB key is pressed!");
    }
  }

  @Override
  public void onEvent(Event event) {

  }
}
