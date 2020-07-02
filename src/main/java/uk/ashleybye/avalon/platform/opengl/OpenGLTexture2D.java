package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL11C.GL_LINEAR;
import static org.lwjgl.opengl.GL11C.GL_NEAREST;
import static org.lwjgl.opengl.GL11C.GL_RGB;
import static org.lwjgl.opengl.GL11C.GL_RGB8;
import static org.lwjgl.opengl.GL11C.GL_RGBA;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL11C.glDeleteTextures;
import static org.lwjgl.opengl.GL11C.glGenTextures;
import static org.lwjgl.opengl.GL11C.glTexImage2D;
import static org.lwjgl.opengl.GL11C.glTexParameteri;
import static org.lwjgl.opengl.GL11C.glTexSubImage2D;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static uk.ashleybye.avalon.util.IOUtils.load;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.stb.STBImage;
import uk.ashleybye.avalon.renderer.Texture2D;

public class OpenGLTexture2D implements Texture2D {

  private final String path;
  private final int width;
  private final int height;
  private final int textureId;

  public static OpenGLTexture2D create(String path) {
    try {
      return new OpenGLTexture2D(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private OpenGLTexture2D(String path) throws IOException {
    this.path = path;

    int[] width = new int[1];
    int[] height = new int[1];
    int[] channels = new int[1];

    STBImage.stbi_set_flip_vertically_on_load(true);
    ByteBuffer texture = STBImage.stbi_load_from_memory(load(path), width, height, channels, 4);
    this.width = width[0];
    this.height = height[0];

    textureId = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE,
        texture);
    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, this.width, this.height, GL_RGBA, GL_UNSIGNED_BYTE,
        texture);
    glBindTexture(GL_TEXTURE_2D, 0);

    STBImage.stbi_image_free(texture);
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
}
