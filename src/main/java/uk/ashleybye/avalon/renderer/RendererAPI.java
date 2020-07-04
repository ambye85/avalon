package uk.ashleybye.avalon.renderer;

public interface RendererAPI {

  void init();

  void setClearColor(float r, float g, float b, float a);

  void clear();

  void drawIndexed(VertexArray vertexArray);

  void setViewport(int x, int y, int width, int height);
}
