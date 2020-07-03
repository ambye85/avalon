package uk.ashleybye.avalon.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShaderLibrary {

  private final Map<String, Shader> shaders;

  public ShaderLibrary() {
    this.shaders = new HashMap<>();
  }

  public void add(Shader shader) {
    add(shader.getName(), shader);
  }

  public void add(String name, Shader shader) {
    shaders.putIfAbsent(name, shader);
  }

  public Shader load(String path) {
    var shader = Shader.create(path);
    add(shader);
    return shader;
  }

  public Shader load(String name, String path) {
    var shader = Shader.create(name, path);
    add(name, shader);
    return shader;
  }

  public Optional<Shader> get(String name) {
    return Optional.ofNullable(shaders.get(name));
  }

  public void dispose() {
    shaders.forEach((__, shader) -> shader.dispose());
  }
}
