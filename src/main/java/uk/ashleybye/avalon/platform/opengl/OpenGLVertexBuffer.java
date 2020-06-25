package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15C.glBindBuffer;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glDeleteBuffers;
import static org.lwjgl.opengl.GL15C.glGenBuffers;

import org.lwjgl.BufferUtils;
import uk.ashleybye.avalon.renderer.BufferLayout;
import uk.ashleybye.avalon.renderer.VertexBuffer;

public class OpenGLVertexBuffer implements VertexBuffer {

  private final int bufferId;
  private BufferLayout layout;

  public OpenGLVertexBuffer(float[] vertices) {
    bufferId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, bufferId);
    glBufferData(GL_ARRAY_BUFFER,
        BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip(), GL_STATIC_DRAW);
  }

  @Override
  public BufferLayout getLayout() {
    return layout;
  }

  @Override
  public void setLayout(BufferLayout layout) {
    this.layout = layout;
  }

  @Override
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, bufferId);
  }

  @Override
  public void unbind() {
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  @Override
  public void dispose() {
    glDeleteBuffers(bufferId);
  }
}
