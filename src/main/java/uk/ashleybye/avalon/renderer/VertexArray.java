package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLVertexArray;

public interface VertexArray {

  static VertexArray create() {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> new OpenGLVertexArray();
    };
  }

  void bind();

  void unbind();

  void dispose();

  void addVertexBuffer(VertexBuffer vertexBuffer);

  IndexBuffer getIndexBuffer();

  void setIndexBuffer(IndexBuffer indexBuffer);
}
