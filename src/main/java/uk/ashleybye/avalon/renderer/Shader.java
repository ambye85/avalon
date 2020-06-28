package uk.ashleybye.avalon.renderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public interface Shader {

  void bind();

  void unbind();

  void dispose();
}
