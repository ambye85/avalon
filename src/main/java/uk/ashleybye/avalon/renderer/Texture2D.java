package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLTexture2D;

public interface Texture2D extends Texture {

  static Texture2D create(int width, int height) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> OpenGLTexture2D.create(width, height);
    };
  }

  static Texture2D create(String path) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> OpenGLTexture2D.create(path);
    };
  }
}
