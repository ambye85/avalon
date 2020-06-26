package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;
import uk.ashleybye.avalon.renderer.BufferLayout;
import uk.ashleybye.avalon.renderer.IndexBuffer;
import uk.ashleybye.avalon.renderer.VertexArray;
import uk.ashleybye.avalon.renderer.VertexBuffer;

public class OpenGLVertexArray implements VertexArray {

  private final int vertexArray;
  private final List<VertexBuffer> vertexBuffers;
  private IndexBuffer indexBuffer;

  public OpenGLVertexArray() {
    vertexArray = glGenVertexArrays();
    vertexBuffers = new ArrayList<>();
  }

  @Override
  public void bind() {
    glBindVertexArray(vertexArray);
  }

  @Override
  public void unbind() {
    glBindVertexArray(0);
  }

  @Override
  public void dispose() {
    vertexBuffers.forEach(buffer -> buffer.bind());
    indexBuffer.dispose();
    glDeleteVertexArrays(vertexArray);
  }

  @Override
  public void addVertexBuffer(VertexBuffer vertexBuffer) {
    if (vertexBuffer.getLayout() == null) {
      throw new IllegalStateException("Cannot add vertex buffer with no layout");
    }

    vertexBuffers.add(vertexBuffer);
    glBindVertexArray(vertexArray);
    vertexBuffer.bind();
    applyLayout(vertexBuffer.getLayout());
    glBindVertexArray(0);
  }

  private void applyLayout(BufferLayout layout) {
    int index = 0;
//    for (int i = 0; layout.iterator().hasNext(); i++) ?
    for (var element : layout) {
      glEnableVertexAttribArray(index);
      glVertexAttribPointer(
          index,
          element.getComponentCount(),
          element.toOpenGLBaseType(),
          element.normalised(),
          layout.getStride(),
          element.offset()
      );
      index++;
    }
  }

  @Override
  public void setIndexBuffer(IndexBuffer indexBuffer) {
    this.indexBuffer = indexBuffer;
    glBindVertexArray(vertexArray);
    indexBuffer.bind();
    glBindVertexArray(0);
  }

  @Override
  public IndexBuffer getIndexBuffer() {
    return indexBuffer;
  }
}
