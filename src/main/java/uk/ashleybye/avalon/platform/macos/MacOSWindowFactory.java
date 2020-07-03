package uk.ashleybye.avalon.platform.macos;

import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowFactory;
import uk.ashleybye.avalon.window.WindowProperties;

public class MacOSWindowFactory implements WindowFactory {

  @Override
  public Window create(WindowProperties properties) {
    return new MacOSWindow(properties);
  }
}
