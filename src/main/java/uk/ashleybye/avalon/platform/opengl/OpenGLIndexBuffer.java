package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15C.glBindBuffer;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glDeleteBuffers;
import static org.lwjgl.opengl.GL15C.glGenBuffers;

import org.lwjgl.BufferUtils;
import uk.ashleybye.avalon.renderer.IndexBuffer;

public class OpenGLIndexBuffer implements IndexBuffer {

  private final int bufferId;
  private final int count;

  public OpenGLIndexBuffer(int[] indices) {
    count = indices.length;
    bufferId = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferId);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,
        BufferUtils.createIntBuffer(count).put(indices).flip(), GL_STATIC_DRAW);
  }

  @Override
  public void bind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferId);
  }

  @Override
  public void unbind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  }

  @Override
  public void dispose() {
    glDeleteBuffers(bufferId);
  }

  @Override
  public int getCount() {
    return count;
  }
}
