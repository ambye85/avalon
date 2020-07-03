package uk.ashleybye.avalon.platform.opengl;

import static com.google.common.truth.Truth.assertThat;
import static org.lwjgl.opengl.GL20C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;
import static uk.ashleybye.avalon.platform.opengl.OpenGLShader.parse;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpenGLShaderTest {

  @Test
  void parsesSingleVertexShader() {
    var shaderSource = """
        #type vertex
        VERTEX SHADER DEFINITION
        """;

    Map<Integer, String> programs = parse(shaderSource);

    assertThat(programs.size()).isEqualTo(1);
    assertThat(programs.get(GL_VERTEX_SHADER)).isEqualTo("VERTEX SHADER DEFINITION\n");
  }

  @Test
  void parsesCombinedVertexAndFragmentShader() {
    var shaderSource = """
        #type vertex
        VERTEX SHADER DEFINITION
        #type fragment
        FRAGMENT SHADER DEFINITION
        """;

    Map<Integer, String> programs = parse(shaderSource);

    assertThat(programs.size()).isEqualTo(2);
    assertThat(programs.get(GL_VERTEX_SHADER)).isEqualTo("VERTEX SHADER DEFINITION\n");
    assertThat(programs.get(GL_FRAGMENT_SHADER)).isEqualTo("FRAGMENT SHADER DEFINITION\n");
  }
}
