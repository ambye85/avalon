package uk.ashleybye.avalon.renderer;

public interface Shader {

  void bind();

  void unbind();

  void dispose();

  String getName();
}
