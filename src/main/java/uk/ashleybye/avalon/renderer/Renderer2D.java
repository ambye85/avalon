package uk.ashleybye.avalon.renderer;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Renderer2D {

  private static VertexArray vertexArray = null;
  private static Shader singleColourShader = null;
  private static Shader textureShader = null;

  public static void init() {
    float[] vertices = new float[]{
        -0.5F, -0.5F, 0.0F, 0.0F, 0.0F,
        +0.5F, -0.5F, 0.0F, 1.0F, 0.0F,
        +0.5F, +0.5F, 0.0F, 1.0F, 1.0F,
        -0.5F, +0.5F, 0.0F, 0.0F, 1.0F,
    };

    vertexArray = VertexArray.create();
    BufferLayout layout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .addElement(ShaderDataType.FLOAT_2, "a_TexCoord")
        .build();
    var vertexBuffer = VertexBuffer.create(vertices);
    vertexBuffer.setLayout(layout);
    vertexArray.addVertexBuffer(vertexBuffer);

    int[] indices = new int[]{0, 1, 2, 2, 3, 0};
    var indexBuffer = IndexBuffer.create(indices);
    vertexArray.setIndexBuffer(indexBuffer);

    singleColourShader = Shader.create("shaders/flatColour.glsl");
    textureShader = Shader.create("shaders/texture.glsl");

    textureShader.bind();
    textureShader.setData("u_Texture", 0);
    textureShader.unbind();
  }

  public static void dispose() {
    textureShader.dispose();
    singleColourShader.dispose();
    vertexArray.dispose();

    textureShader = null;
    singleColourShader = null;
    vertexArray = null;
  }

  public static void beginScene(OrthographicCamera camera) {
    singleColourShader.bind();
    singleColourShader.setData("u_ViewProjection", camera.getViewProjectionMatrix());
    singleColourShader.unbind();

    textureShader.bind();
    textureShader.setData("u_ViewProjection", camera.getViewProjectionMatrix());
    textureShader.unbind();
  }

  public static void endScene() {

  }

  public static void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
    drawQuad(new Vector3f(position, 0.0F), 0.0F, size, color);
  }

  public static void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
    drawQuad(position, 0.0F, size, color);
  }

  public static void drawQuad(
      Vector2f position,
      float rotation,
      Vector2f size,
      Vector4f color
  ) {
    drawQuad(new Vector3f(position, 0.0F), rotation, size, color);
  }

  public static void drawQuad(
      Vector3f position,
      float rotation,
      Vector2f size,
      Vector4f color
  ) {
    var transform = new Matrix4f()
        .translate(position)
        .rotate(Math.toRadians(rotation), new Vector3f(0.0F, 0.0F, 1.0F))
        .scale(size.x, size.y, 1.0F);

    singleColourShader.bind();
    singleColourShader.setData("u_Transform", transform);
    singleColourShader.setData("u_Color", new Vector4f(color));
    vertexArray.bind();

    RenderCommand.drawIndexed(vertexArray);

    vertexArray.unbind();
    singleColourShader.unbind();
  }

  public static void drawQuad(Vector2f position, Vector2f size, Texture texture) {
    drawQuad(new Vector3f(position, 0.0F), 0.0F, size, texture);
  }

  public static void drawQuad(Vector3f position, Vector2f size, Texture texture) {
    drawQuad(position, 0.0F, size, texture);
  }

  public static void drawQuad(
      Vector2f position,
      float rotation,
      Vector2f size,
      Texture texture
  ) {
    drawQuad(new Vector3f(position, 0.0F), rotation, size, texture);
  }

  public static void drawQuad(
      Vector3f position,
      float rotation,
      Vector2f size,
      Texture texture
  ) {
    var transform = new Matrix4f()
        .translate(position)
        .rotate(Math.toRadians(rotation), new Vector3f(0.0F, 0.0F, 1.0F))
        .scale(size.x, size.y, 1.0F);

    textureShader.bind();
    textureShader.setData("u_Transform", transform);
    texture.bind(0);
    vertexArray.bind();

    RenderCommand.drawIndexed(vertexArray);

    vertexArray.unbind();
    texture.unbind();
    textureShader.unbind();
  }
}
