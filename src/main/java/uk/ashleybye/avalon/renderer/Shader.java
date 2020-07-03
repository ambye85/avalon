package uk.ashleybye.avalon.renderer;

import uk.ashleybye.avalon.platform.opengl.OpenGLShader;

public interface Shader {

  static Shader create(String name, String vertexSource, String fragmentSource) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> OpenGLShader.create(name, vertexSource, fragmentSource);
    };
  }

  static Shader create(String name, String path) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> OpenGLShader.create(name, path);
    };
  }

  static Shader create(String path) {
    return switch (Renderer.getAPIVersion()) {
      case NONE -> throw new RuntimeException(
          Renderer.getAPIVersion().toString() + " is not supported");
      case OPEN_GL -> OpenGLShader.create(path);
    };
  }

  void bind();

  void unbind();

  void dispose();

  String getName();
}
