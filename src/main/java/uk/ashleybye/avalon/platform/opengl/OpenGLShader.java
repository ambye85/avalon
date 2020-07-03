package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL11C.GL_FALSE;
import static org.lwjgl.opengl.GL11C.GL_NONE;
import static org.lwjgl.opengl.GL20C.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20C.GL_FRAGMENT_SHADER;
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
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glLinkProgram;
import static org.lwjgl.opengl.GL20C.glShaderSource;
import static org.lwjgl.opengl.GL20C.glUniform1i;
import static org.lwjgl.opengl.GL20C.glUniform3f;
import static org.lwjgl.opengl.GL20C.glUniform4f;
import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20C.glUseProgram;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20C;
import uk.ashleybye.avalon.renderer.Shader;

public class OpenGLShader implements Shader {

  private static final String TYPE_TOKEN = "#type";
  private final int programId;
  private final float[] uniformMatrix4f = new float[16];
  private final String name;

  private OpenGLShader(String name, String vertexSource, String fragmentSource) {
    var shaderIds = compile(
        Map.of(GL_VERTEX_SHADER, vertexSource, GL_FRAGMENT_SHADER, fragmentSource));
    programId = link(shaderIds);
    this.name = name;
  }

  private OpenGLShader(String name, String path) {
    var shaderSource = load(path);
    var programs = parse(shaderSource);
    var shaderIds = compile(programs);
    programId = link(shaderIds);
    this.name = name;
  }

  private OpenGLShader(String path) {
    this(filename(path), path);
  }

  public static OpenGLShader create(String name, String vertexSource, String fragmentSource) {
    return new OpenGLShader(name, vertexSource, fragmentSource);
  }

  public static OpenGLShader create(String name, String path) {
    return new OpenGLShader(name, path);
  }

  public static OpenGLShader create(String path) {
    return new OpenGLShader(path);
  }

  private static String load(String path) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(path);
    try {
      return Files.readString(Paths.get(url.toURI()));
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException("Could not load shader file");
    }
  }

  static Map<Integer, String> parse(String source) {
    Map<Integer, String> programs = new HashMap<>();

    int current = source.indexOf(TYPE_TOKEN);
    while (current < source.length()) {
      var next = source.indexOf("\n", current);
      var rawType = source.substring(current + TYPE_TOKEN.length(), next).strip();
      var shaderType = lookupOpenGLShaderType(rawType);
      current = next;

      next = source.indexOf(TYPE_TOKEN, next + 1);
      next = next == -1 ? source.length() : next;
      var shaderSource = source.substring(current + 1, next);
      current = next;

      programs.put(shaderType, shaderSource);
    }

    return programs;
  }

  private static int lookupOpenGLShaderType(String type) {
    return switch (type) {
      case "vertex" -> GL_VERTEX_SHADER;
      case "fragment" -> GL_FRAGMENT_SHADER;
      default -> GL_NONE;
    };
  }

  private static CompiledShader compile(Map<Integer, String> programs) {
    List<Integer> shaderIds = new ArrayList<>(programs.size());

    int programId = glCreateProgram();

    programs.forEach((shaderType, shaderSource) -> {
      int shader = glCreateShader(shaderType);
      glShaderSource(shader, shaderSource);
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

      glAttachShader(programId, shader);
      shaderIds.add(shader);
    });

    return new CompiledShader(programId, shaderIds);
  }

  private static int link(CompiledShader compiledShader) {
    var programId = compiledShader.programId;
    var shaderIds = compiledShader.shaderIds;

    glLinkProgram(programId);

    boolean isLinked = glGetProgrami(programId, GL_LINK_STATUS) != GL_FALSE;
    if (!isLinked) {
      String programLog = glGetProgramInfoLog(programId);
      if (programLog.trim().length() > 0) {
        System.err.println(programLog);
      }

      glDeleteProgram(programId);
      shaderIds.forEach(GL20C::glDeleteShader);

      throw new IllegalStateException("Could not compile shader");
    }

    shaderIds.forEach(shader -> glDetachShader(programId, shader));

    return programId;
  }

  private static String filename(String path) {
    var start = path.lastIndexOf(File.separator);
    start = start == -1 ? 0 : start + 1;
    var end = path.lastIndexOf(".");
    end = end == -1 ? path.length() - 1 : end;
    return path.substring(start, end);
  }

  @Override
  public void bind() {
    glUseProgram(programId);
  }

  @Override
  public void unbind() {
    glUseProgram(0);
  }

  @Override
  public void dispose() {
    glDeleteProgram(programId);
  }

  @Override
  public String getName() {
    return name;
  }

  public void uploadUniform(String name, int value) {
    int location = glGetUniformLocation(programId, name);
    glUniform1i(location, value);
  }

  public void uploadUniform(String name, Matrix4f matrix) {
    int location = glGetUniformLocation(programId, name);
    glUniformMatrix4fv(location, false, matrix.get(uniformMatrix4f));
  }

  public void uploadUniform(String name, Vector3f vector) {
    int location = glGetUniformLocation(programId, name);
    glUniform3f(location, vector.x, vector.y, vector.z);
  }

  public void uploadUniform(String name, Vector4f vector) {
    int location = glGetUniformLocation(programId, name);
    glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
  }

  private static class CompiledShader {

    int programId;
    List<Integer> shaderIds;

    public CompiledShader(int programId, List<Integer> shaderIds) {
      this.programId = programId;
      this.shaderIds = shaderIds;
    }
  }
}
