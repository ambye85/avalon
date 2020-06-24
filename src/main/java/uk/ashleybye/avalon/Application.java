package uk.ashleybye.avalon;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static uk.ashleybye.avalon.Logger.Color.GREEN;

import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.imgui.ImGuiLayer;
import uk.ashleybye.avalon.input.Input;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public abstract class Application {

  private static Application instance = null;
  private static Logger logger = Logger.builder("AVALON", GREEN).build();
  private boolean running = false;
  private Window window;
  private final LayerStack layers;
  private final ImGuiLayer imGuiLayer;

  public Application() {
    instance = this;

    var properties = new WindowProperties("Avalon", 1280, 720, true, this::onEvent);
    window = Window.create(properties);
    layers = new LayerStack();

    imGuiLayer = new ImGuiLayer();
    pushOverlay(imGuiLayer);
  }

  public static final Application getInstance() {
    return instance;
  }

  public final Window getWindow() {
    return window;
  }

  public final void run() {
    running = true;
    while (running) {
      glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
      glClear(GL_COLOR_BUFFER_BIT);

      for (var layer : layers) {
        layer.onUpdate();
      }
//
//      imGuiLayer.begin();
//      for (var layer : layers) {
//        layer.onImGuiRender();
//      }
//      imGuiLayer.end();

      window.onUpdate();
    }

    imGuiLayer.dispose();
    window.dispose();
  }

  public final boolean onEvent(Event e) {
    var dispatcher = new EventDispatcher(e);
    dispatcher.dispatch(WindowCloseEvent.class, this::onWindowClose);

    for (var layer : layers.reversed()) {
      layer.onEvent(e);
      if (e.isHandled()) {
        break;
      }
    }

    return false;
  }

  public final void pushLayer(Layer layer) {
    layers.pushLayer(layer);
    layer.onAttach();
  }

  public final void pushOverlay(Layer overlay) {
    layers.pushOverlay(overlay);
    overlay.onAttach();
  }

  private boolean onWindowClose(Event e) {
    running = false;
    e.setHandled(true);
    return true;
  }
}
