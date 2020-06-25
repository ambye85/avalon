package uk.ashleybye.avalon.renderer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.lwjgl.opengl.GL11C.GL_FLOAT;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BufferLayoutTest {

  @Test
  void layoutWithNoDataTypesIsError() {
    assertThrows(IllegalStateException.class, () -> BufferLayout.builder().build());
  }

  @Test
  void layoutWithSingleDataType() {
    BufferLayout layout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .build();

    List<BufferElement> items = new ArrayList<>();
    for (var i : layout) {
      items.add(i);
    }

    assertThat(layout.getStride()).isEqualTo(12);
    assertThat(items.size()).isEqualTo(1);
    assertThat(items.get(0).name()).isEqualTo("a_Position");
    assertThat(items.get(0).type()).isEqualTo(ShaderDataType.FLOAT_3);
    assertThat(items.get(0).toOpenGLBaseType()).isEqualTo(GL_FLOAT);
    assertThat(items.get(0).getComponentCount()).isEqualTo(3);
    assertThat(items.get(0).size()).isEqualTo(12);
    assertThat(items.get(0).offset()).isEqualTo(0L);
    assertThat(items.get(0).normalised()).isFalse();
  }

  @Test
  void layoutWithMultipleDataType() {
    BufferLayout layout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .addElement(ShaderDataType.FLOAT_4, "a_Color")
        .build();

    List<BufferElement> items = new ArrayList<>();
    for (var i : layout) {
      items.add(i);
    }

    assertThat(layout.getStride()).isEqualTo(28);
    assertThat(items.size()).isEqualTo(2);
    assertThat(items.get(0).type()).isEqualTo(ShaderDataType.FLOAT_3);
    assertThat(items.get(0).toOpenGLBaseType()).isEqualTo(GL_FLOAT);
    assertThat(items.get(0).getComponentCount()).isEqualTo(3);
    assertThat(items.get(0).name()).isEqualTo("a_Position");
    assertThat(items.get(0).size()).isEqualTo(12);
    assertThat(items.get(0).offset()).isEqualTo(0L);
    assertThat(items.get(0).normalised()).isFalse();
    assertThat(items.get(1).type()).isEqualTo(ShaderDataType.FLOAT_4);
    assertThat(items.get(1).toOpenGLBaseType()).isEqualTo(GL_FLOAT);
    assertThat(items.get(1).getComponentCount()).isEqualTo(4);
    assertThat(items.get(1).name()).isEqualTo("a_Color");
    assertThat(items.get(1).size()).isEqualTo(16);
    assertThat(items.get(1).offset()).isEqualTo(12L);
    assertThat(items.get(1).normalised()).isFalse();
  }
}
