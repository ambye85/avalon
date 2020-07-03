package uk.ashleybye.avalon.window;

public abstract class Window {

  private static WindowFactory factory;

  public static final void setFactory(WindowFactory factory) {
    Window.factory = factory;
  }

  public static final Window create(WindowProperties properties) {
    return factory.create(properties);
  }

  public abstract long getWindowId();

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract void onUpdate();

  public abstract boolean isVSync();

  public abstract void setVSync(boolean enabled);

  public abstract void dispose();
}
