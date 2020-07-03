package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLContext;

public interface GraphicsContext {

  static GraphicsContext create(long windowId) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> new OpenGLContext(windowId);
    };
  }

  void initialise();

  void swapBuffers();
}
