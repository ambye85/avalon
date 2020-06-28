package uk.ashleybye.avalon.renderer;

import static com.google.common.truth.Truth.assertThat;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.junit.jupiter.api.Test;

class OrthographicCameraTest {

  @Test
  void correctInitialMatricesComputedUsingNDC() {
    // JOML matrices are column major => each vector represents a column in a matrix.
    OrthographicCamera camera = new OrthographicCamera(-1.0F, 1.0F, -1.0F, 1.0F);

    assertThat(camera.getPosition()).isEqualTo(new Vector3f());
    assertThat(camera.getRotation()).isEqualTo(0.0f);
    assertThat(camera.getProjectionMatrix()).isEqualTo(new Matrix4f(
        new Vector4f(1.0F, 0.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 1.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 0.0F, -1.0F, 0.0F),
        new Vector4f(-0.0F, -0.0F, -0.0F, 1.0F)
    ));
    assertThat(camera.getViewMatrix()).isEqualTo(new Matrix4f(
        new Vector4f(1.0F, -0.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 1.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 0.0F, 1.0F, 0.0F),
        new Vector4f(-0.0F, -0.0F, -0.0F, 1.0F)
    ));
    assertThat(camera.getViewProjectionMatrix())
        .isEqualTo(camera.getProjectionMatrix().mul(camera.getViewMatrix()));
  }

  @Test
  void changingPositionUpdatesViewMatrix() {
    OrthographicCamera camera = new OrthographicCamera(-1.0F, 1.0F, -1.0F, 1.0F);
    camera.setPosition(new Vector3f(0.5F, 0.5F, 0.5F));

    assertThat(camera.getViewMatrix()).isEqualTo(new Matrix4f(
        new Vector4f(1.0F, -0.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 1.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 0.0F, 1.0F, 0.0F),
        new Vector4f(-0.5F, -0.5F, -0.5F, 1.0F)
    ));
    assertThat(camera.getViewProjectionMatrix())
        .isEqualTo(camera.getProjectionMatrix().mul(camera.getViewMatrix()));
  }

  @Test
  void changingRotationUpdatesViewMatrix() {
    OrthographicCamera camera = new OrthographicCamera(-1.0F, 1.0F, -1.0F, 1.0F);
    camera.setRotation(90.0F);

    assertThat(camera.getViewMatrix()).isEqualTo(new Matrix4f(
        new Vector4f(-0.0F, -1.0F, 0.0F, 0.0F),
        new Vector4f(1.0F, -0.0F, 0.0F, 0.0F),
        new Vector4f(0.0F, 0.0F, 1.0F, 0.0F),
        new Vector4f(-0.0F, -0.0F, -0.0F, 1.0F)
    ));
    assertThat(camera.getViewProjectionMatrix())
        .isEqualTo(camera.getProjectionMatrix().mul(camera.getViewMatrix()));
  }
}
