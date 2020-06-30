package uk.ashleybye.avalon;

import static uk.ashleybye.avalon.Logger.Color.GREEN;

import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.imgui.ImGuiLayer;
import uk.ashleybye.avalon.platform.macos.MacOSTimer;
import uk.ashleybye.avalon.platform.macos.MacOSWindow;
import uk.ashleybye.avalon.time.Timer;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public abstract class Application {

  private static final Logger logger = Logger.builder("AVALON", GREEN).build();
  private static Application instance = null;
  private final LayerStack layers;
  private final ImGuiLayer imGuiLayer;
  private final Window window;
  private final Timer timer;
  private boolean running = false;

  public Application() {
    instance = this;

    layers = new LayerStack();

    var properties = new WindowProperties("Avalon", 1280, 720, true, this::onEvent);
    window = MacOSWindow.create(properties);

    imGuiLayer = new ImGuiLayer();
    pushOverlay(imGuiLayer);

    timer = new MacOSTimer();
  }

  public static Application getInstance() {
    return instance;
  }

  public final Window getWindow() {
    return window;
  }

  public final void run() {
    timer.start();
    running = true;
    while (running) {

      timer.tick();
      float dt = (float) timer.getDeltaSeconds();

      for (var layer : layers) {
        layer.onUpdate(dt);
      }

      imGuiLayer.begin();
      for (var layer : layers) {
        layer.onImGuiRender();
      }
      imGuiLayer.end();

      window.onUpdate();
    }

    popOverlay(imGuiLayer);
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

  public final void popLayer(Layer layer) {
    layers.popLayer(layer);
    layer.onDetach();
  }

  public final void popOverlay(Layer overlay) {
    layers.popOverlay(overlay);
    overlay.onDetach();
  }

  private boolean onWindowClose(Event e) {
    running = false;
    e.setHandled(true);
    return true;
  }
}
