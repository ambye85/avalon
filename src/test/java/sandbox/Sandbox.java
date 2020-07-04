package sandbox;

import uk.ashleybye.avalon.Application;

public class Sandbox extends Application {

  public Sandbox() {
    super();
//    super.pushLayer(new ExampleLayer());
    super.pushLayer(new Example2DLayer());
  }

  public static void main(String[] args) {
    var sandbox = new Sandbox();
    sandbox.run();
  }
}

