package uk.ashleybye.avalon.renderer;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Renderer2D {

  private static VertexArray vertexArray = null;
  private static Shader shader = null;

  public static void init() {
    float[] vertices = new float[]{
        -0.5F, -0.5F, 0.0F,
        +0.5F, -0.5F, 0.0F,
        +0.5F, +0.5F, 0.0F,
        -0.5F, +0.5F, 0.0F,
    };

    vertexArray = VertexArray.create();
    BufferLayout layout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .build();
    var vertexBuffer = VertexBuffer.create(vertices);
    vertexBuffer.setLayout(layout);
    vertexArray.addVertexBuffer(vertexBuffer);

    int[] indices = new int[]{0, 1, 2, 2, 3, 0};
    var indexBuffer = IndexBuffer.create(indices);
    vertexArray.setIndexBuffer(indexBuffer);

    shader = Shader.create("shaders/flatColour.glsl");
  }

  public static void dispose() {
    shader.dispose();
    vertexArray.dispose();
    shader = null;
    vertexArray = null;
  }

  public static void beginScene(OrthographicCamera camera) {
    shader.bind();
    shader.setData("u_ViewProjection", camera.getViewProjectionMatrix());
    shader.unbind();
  }

  public static void endScene() {

  }

  public static void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
    drawRotatedQuad(new Vector3f(position, 0.0F), 0.0F, size, color);
  }

  public static void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
    drawRotatedQuad(position, 0.0F, size, color);
  }

  public static void drawRotatedQuad(
      Vector2f position,
      float rotation,
      Vector2f size,
      Vector4f color
  ) {
    drawRotatedQuad(new Vector3f(position, 0.0F), rotation, size, color);
  }

  public static void drawRotatedQuad(
      Vector3f position,
      float rotation,
      Vector2f size,
      Vector4f color
  ) {
    var transform = new Matrix4f()
        .translate(position)
        .rotate(Math.toRadians(rotation), new Vector3f(0.0F, 0.0F, 1.0F))
        .scale(size.x, size.y, 1.0F);

    shader.bind();
    shader.setData("u_Transform", transform);
    shader.setData("u_Color", new Vector4f(color));
    vertexArray.bind();

    RenderCommand.drawIndexed(vertexArray);

    vertexArray.unbind();
    shader.unbind();
  }
}
