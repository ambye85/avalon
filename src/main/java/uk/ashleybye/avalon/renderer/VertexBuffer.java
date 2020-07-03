package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLVertexBuffer;

public interface VertexBuffer {

  static VertexBuffer create(float[] vertices) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> new OpenGLVertexBuffer(vertices);
    };
  }

  BufferLayout getLayout();

  void setLayout(BufferLayout layout);

  void bind();

  void unbind();

  void dispose();
}
