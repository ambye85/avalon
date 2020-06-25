package uk.ashleybye.avalon.renderer;

public interface IndexBuffer {

  void bind();

  void unbind();

  void dispose();

  int getCount();
}
