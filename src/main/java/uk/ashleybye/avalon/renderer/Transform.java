package uk.ashleybye.avalon.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

  private final Vector3f position = new Vector3f();
  private float rotation = (float) Math.toRadians(0.0F);
  private final Vector3f scale = new Vector3f();
  private final Vector3f rotationAxis = new Vector3f(0.0F, 0.0F, 1.0F);
  private final Matrix4f trs = new Matrix4f();

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(float x, float y) {
    setPosition(x, y, 0.0F);
  }

  public void setPosition(float x, float y, float z) {
    position.x = x;
    position.y = y;
    position.z = z;
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float degrees) {
    rotation = (float) Math.toRadians(degrees);
  }

  public Vector3f getScale() {
    return scale;
  }

  public void setScale(float x, float y) {
    setScale(x, y, 1.0F);
  }

  public void setScale(float x, float y, float z) {
    scale.x = x;
    scale.y = y;
    scale.z = z;
  }

  public Matrix4f getTRS() {
    return trs
        .identity()
        .translate(position)
        .rotate(rotation, rotationAxis)
        .scale(scale);
  }
}
