package uk.ashleybye.avalon;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static uk.ashleybye.avalon.Logger.Color.GREEN;

import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public abstract class Application {

  static Logger logger = Logger.builder("AVALON", GREEN).build();

  private boolean running = false;
  private Window window;

  public final void run() {
    var properties = new WindowProperties("Avalon", 1280, 720, true, this::onEvent);
    window = Window.create(properties);

    running = true;
    while (running) {
      glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
      glClear(GL_COLOR_BUFFER_BIT);
      window.onUpdate();
    }
  }

  public final boolean onEvent(Event e) {
    logger.log(e.toString());
    var dispatcher = new EventDispatcher(e);
    dispatcher.dispatch(WindowCloseEvent.class, this::onWindowClose);
    return false;
  }

  private boolean onWindowClose(Event e) {
    running = false;
    e.setHandled(true);
    return true;
  }
}
