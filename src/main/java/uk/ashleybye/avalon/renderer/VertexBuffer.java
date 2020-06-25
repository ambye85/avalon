package uk.ashleybye.avalon.renderer;

public interface VertexBuffer {

  BufferLayout getLayout();

  void setLayout(BufferLayout layout);

  void bind();

  void unbind();

  void dispose();
}
