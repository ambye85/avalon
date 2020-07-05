package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL11C.GL_LINEAR;
import static org.lwjgl.opengl.GL11C.GL_NEAREST;
import static org.lwjgl.opengl.GL11C.GL_REPEAT;
import static org.lwjgl.opengl.GL11C.GL_RGB;
import static org.lwjgl.opengl.GL11C.GL_RGB8;
import static org.lwjgl.opengl.GL11C.GL_RGBA;
import static org.lwjgl.opengl.GL11C.GL_RGBA8;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL11C.glDeleteTextures;
import static org.lwjgl.opengl.GL11C.glGenTextures;
import static org.lwjgl.opengl.GL11C.glTexImage2D;
import static org.lwjgl.opengl.GL11C.glTexParameteri;
import static org.lwjgl.opengl.GL11C.glTexSubImage2D;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import uk.ashleybye.avalon.renderer.Texture2D;

public class OpenGLTexture2D implements Texture2D {

  private String path;
  private final int width;
  private final int height;
  private final int textureId;
  private int internalFormat = 0;
  private int dataFormat = 0;

  private OpenGLTexture2D(int width, int height) {
    this.width = width;
    this.height = height;
    internalFormat = GL_RGBA8;
    dataFormat = GL_RGBA;

    textureId = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glBindTexture(GL_TEXTURE_2D, textureId);
    glBindTexture(GL_TEXTURE_2D, 0);
  }

  private OpenGLTexture2D(String path) throws URISyntaxException {
    URL res = getClass().getClassLoader().getResource(path);
    File file = Paths.get(res.toURI()).toFile();
    String absolutePath = file.getAbsolutePath();

    this.path = path;

    int[] width = new int[1];
    int[] height = new int[1];
    int[] channels = new int[1];

    STBImage.stbi_set_flip_vertically_on_load(true);
    ByteBuffer texture = STBImage.stbi_load(absolutePath, width, height, channels, 0);
    this.width = width[0];
    this.height = height[0];

    if (channels[0] == 4) {
      internalFormat = GL_RGBA8;
      dataFormat = GL_RGBA;
    } else if (channels[0] == 3) {
      internalFormat = GL_RGB8;
      dataFormat = GL_RGB;
    }

    textureId = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, this.width, this.height, 0, dataFormat,
        GL_UNSIGNED_BYTE,
        texture);
    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, this.width, this.height, dataFormat, GL_UNSIGNED_BYTE,
        texture);
    glBindTexture(GL_TEXTURE_2D, 0);

    STBImage.stbi_image_free(texture);
  }

  public static Texture2D create(int width, int height) {
    return new OpenGLTexture2D(width, height);
  }

  public static OpenGLTexture2D create(String path) {
    try {
      return new OpenGLTexture2D(path);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void bind(int slot) {
    glActiveTexture(GL_TEXTURE0 + slot);
    glBindTexture(GL_TEXTURE_2D, textureId);
  }

  @Override
  public void dispose() {
    glDeleteTextures(textureId);
  }

  @Override
  public void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }

  @Override
  public void setData(int data) {
    glBindTexture(GL_TEXTURE_2D, textureId);
    try (MemoryStack stack = MemoryStack.stackPush()) {
      IntBuffer buffer = stack.mallocInt(1).put(data).flip();
      glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, this.width, this.height, 0, dataFormat,
          GL_UNSIGNED_BYTE, buffer);
      glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, this.width, this.height, dataFormat, GL_UNSIGNED_BYTE,
          buffer);
    }
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
