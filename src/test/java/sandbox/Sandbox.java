package sandbox;

import uk.ashleybye.avalon.Application;

public class Sandbox extends Application {

  public Sandbox() {
    super();
    super.pushLayer(new ExampleLayer());
  }

  public static void main(String[] args) {
    var sandbox = new Sandbox();
    sandbox.run();
  }
}

