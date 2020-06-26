package uk.ashleybye.avalon.renderer;

public interface VertexArray {

  void bind();

  void unbind();

  void dispose();

  void addVertexBuffer(VertexBuffer vertexBuffer);

  void setIndexBuffer(IndexBuffer indexBuffer);

  IndexBuffer getIndexBuffer();
}
