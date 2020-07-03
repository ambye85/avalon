package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLIndexBuffer;

public interface IndexBuffer {

  static IndexBuffer create(int[] indices) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> new OpenGLIndexBuffer(indices);
    };
  }

  void bind();

  void unbind();

  void dispose();

  int getCount();
}
