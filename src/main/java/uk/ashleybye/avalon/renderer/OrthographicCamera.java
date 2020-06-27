package uk.ashleybye.avalon.renderer;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class OrthographicCamera {

  private Matrix4f projectionMatrix;
  private Matrix4f viewMatrix;
  private Matrix4f viewProjectionMatrix;
  private Vector3f position;
  private float rotation;

  public OrthographicCamera(float left, float right, float bottom, float top) {
    projectionMatrix = new Matrix4f().setOrtho(left, right, bottom, top, -1.0F, 1.0F);
    viewMatrix = new Matrix4f();
    viewProjectionMatrix = new Matrix4f();
    position = new Vector3f();
    rotation = 0.0F;
    recalculateViewMatrix();
  }

  public Matrix4f getProjectionMatrix() {
    return projectionMatrix;
  }

  public Matrix4f getViewMatrix() {
    return viewMatrix;
  }

  public Matrix4f getViewProjectionMatrix() {
    return viewProjectionMatrix;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(float x, float y, float z) {
    this.position.x = x;
    this.position.y = y;
    this.position.z = z;
    recalculateViewMatrix();
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
    recalculateViewMatrix();
  }

  private void recalculateViewMatrix() {
    Matrix4f transform = new Matrix4f()
        .translate(position)
        .rotate(Math.toRadians(rotation), new Vector3f(0.0F, 0.0F, 1.0F));
    viewMatrix = transform.invert();
    projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
  }
}
