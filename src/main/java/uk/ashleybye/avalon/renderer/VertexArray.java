package uk.ashleybye.avalon.renderer;

public interface VertexArray {

  void bind();

  void unbind();

  void dispose();

  void addVertexBuffer(VertexBuffer vertexBuffer);

  IndexBuffer getIndexBuffer();

  void setIndexBuffer(IndexBuffer indexBuffer);
}
