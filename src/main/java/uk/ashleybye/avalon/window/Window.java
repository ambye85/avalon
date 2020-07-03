package uk.ashleybye.avalon.window;

import uk.ashleybye.avalon.platform.macos.MacOSWindow;

public interface Window {

  static Window create(WindowProperties properties) {
    return new MacOSWindow(properties);
  }

  public abstract long getWindowId();

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract void onUpdate();

  public abstract boolean isVSync();

  public abstract void setVSync(boolean enabled);

  public abstract void dispose();
}
