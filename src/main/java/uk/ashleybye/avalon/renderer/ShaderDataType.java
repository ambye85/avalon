package uk.ashleybye.avalon.renderer;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL11C.GL_INT;
import static org.lwjgl.opengl.GL11C.GL_NONE;
import static org.lwjgl.opengl.GL20C.GL_BOOL;

public enum ShaderDataType {
  NONE,
  FLOAT,
  FLOAT_2,
  FLOAT_3,
  FLOAT_4,
  MATRIX_3,
  MATRIX_4,
  INTEGER,
  INTEGER_2,
  INTEGER_3,
  INTEGER_4,
  BOOLEAN,
  ;

  public int getComponentCount() {
    return switch (this) {
      case NONE -> 0;
      case FLOAT, INTEGER, BOOLEAN -> 1;
      case FLOAT_2, INTEGER_2 -> 2;
      case FLOAT_3, INTEGER_3 -> 3;
      case FLOAT_4, INTEGER_4 -> 4;
      case MATRIX_3 -> 3 * 3;
      case MATRIX_4 -> 4 * 4;
    };
  }

  public int getSize() {
    return switch (this) {
      case NONE, BOOLEAN -> this.getComponentCount();
      case FLOAT, FLOAT_2, FLOAT_3, FLOAT_4, MATRIX_3, MATRIX_4 -> Float.BYTES * this
          .getComponentCount();
      case INTEGER, INTEGER_2, INTEGER_3, INTEGER_4 -> Integer.BYTES * this.getComponentCount();
    };
  }

  public int toOpenGLBaseType() {
    return switch (this) {
      case NONE -> GL_NONE;
      case FLOAT, FLOAT_2, FLOAT_3, FLOAT_4, MATRIX_3, MATRIX_4 -> GL_FLOAT;
      case INTEGER, INTEGER_2, INTEGER_3, INTEGER_4 -> GL_INT;
      case BOOLEAN -> GL_BOOL;
    };
  }
}
