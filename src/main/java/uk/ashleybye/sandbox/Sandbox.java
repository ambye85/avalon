package uk.ashleybye.sandbox;

import static uk.ashleybye.avalon.input.KeyCodes.AVALON_KEY_TAB;

import imgui.ImGui;
import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.input.Input;

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
  public void onImGuiRender() {
    ImGui.begin("test");
    ImGui.text("Hello, world!");
    ImGui.end();
  }
}
