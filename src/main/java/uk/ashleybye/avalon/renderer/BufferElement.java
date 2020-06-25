package uk.ashleybye.avalon.renderer;

public record BufferElement(
    String name,
    ShaderDataType type,
    int size,
    int offset,
    boolean normalised
) {

  public int getComponentCount() {
    return type.getComponentCount();
  }

  public int toOpenGLBaseType() {
    return type.toOpenGLBaseType();
  }
}
