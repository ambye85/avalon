package uk.ashleybye.avalon.renderer;

import static org.lwjgl.opengl.GL11C.GL_FALSE;
import static org.lwjgl.opengl.GL20C.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20C.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20C.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20C.glAttachShader;
import static org.lwjgl.opengl.GL20C.glCompileShader;
import static org.lwjgl.opengl.GL20C.glCreateProgram;
import static org.lwjgl.opengl.GL20C.glCreateShader;
import static org.lwjgl.opengl.GL20C.glDeleteProgram;
import static org.lwjgl.opengl.GL20C.glDeleteShader;
import static org.lwjgl.opengl.GL20C.glDetachShader;
import static org.lwjgl.opengl.GL20C.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20C.glGetProgrami;
import static org.lwjgl.opengl.GL20C.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20C.glGetShaderi;
import static org.lwjgl.opengl.GL20C.glLinkProgram;
import static org.lwjgl.opengl.GL20C.glShaderSource;
import static org.lwjgl.opengl.GL20C.glUseProgram;

public class Shader {

  private final int programId;

  public Shader(String vertexSource, String fragmentSource) {
    int vertexShader = compileShader(GL_VERTEX_SHADER, vertexSource);
    int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentSource);
    programId = createProgram(vertexShader, fragmentShader);

    glDetachShader(programId, vertexShader);
    glDetachShader(programId, fragmentShader);
  }

  private int compileShader(int type, String source) {
    int shader = glCreateShader(type);
    glShaderSource(shader, source);
    glCompileShader(shader);

    boolean isCompiled = glGetShaderi(shader, GL_COMPILE_STATUS) != GL_FALSE;
    if (!isCompiled) {
      String shaderLog = glGetShaderInfoLog(shader);
      if (shaderLog.trim().length() > 0) {
        System.err.println(shaderLog);
      }

      glDeleteShader(shader);

      throw new IllegalStateException("Could not compile shader");
    }

    return shader;
  }

  private int createProgram(int vertexShader, int fragmentShader) {
    int programId = glCreateProgram();

    glAttachShader(programId, vertexShader);
    glAttachShader(programId, fragmentShader);

    glLinkProgram(programId);

    boolean isLinked = glGetProgrami(programId, GL_LINK_STATUS) != GL_FALSE;
    if (!isLinked) {
      String programLog = glGetProgramInfoLog(programId);
      if (programLog.trim().length() > 0) {
        System.err.println(programLog);
      }

      glDeleteProgram(programId);
      glDeleteShader(vertexShader);
      glDeleteShader(fragmentShader);

      throw new IllegalStateException("Could not compile shader");
    }

    return programId;
  }

  public void bind() {
    glUseProgram(programId);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void dispose() {
    glDeleteProgram(programId);
  }
}
