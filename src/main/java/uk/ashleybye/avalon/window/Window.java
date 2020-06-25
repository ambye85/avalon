package uk.ashleybye.avalon.window;

public interface Window {

  long getWindowId();

  int getWidth();

  int getHeight();

  void onUpdate();

  boolean isVSync();

  void setVSync(boolean enabled);

  void dispose();
}
