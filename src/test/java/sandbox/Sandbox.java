package sandbox;

import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.instrumentation.Instrumentor;

public class Sandbox extends Application {

  public Sandbox() {
    super();
//    super.pushLayer(new ExampleLayer());
    super.pushLayer(new Example2DLayer());
  }

  public static void main(String[] args) {
    Instrumentor.setEnabled(true);

    var sandbox = new Sandbox();
    sandbox.run();
  }
}

