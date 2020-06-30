package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.opengl.GL11C.GL_LINEAR;
import static org.lwjgl.opengl.GL11C.GL_RGB;
import static org.lwjgl.opengl.GL11C.GL_RGB8;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11C.glDeleteTextures;
import static org.lwjgl.opengl.GL45C.glBindTextureUnit;
import static org.lwjgl.opengl.GL45C.glCreateTextures;
import static org.lwjgl.opengl.GL45C.glTextureParameteri;
import static org.lwjgl.opengl.GL45C.glTextureStorage2D;
import static org.lwjgl.opengl.GL45C.glTextureSubImage2D;

import java.nio.ByteBuffer;
import org.lwjgl.stb.STBImage;
import uk.ashleybye.avalon.renderer.Texture2D;

public class OpenGLTexture2D implements Texture2D {

  private String path;
  private int width;
  private int height;
  private int textureId;

  public static OpenGLTexture2D create(String path) {
    return new OpenGLTexture2D(path);
  }

  private OpenGLTexture2D(String path) {
    this.path = path;

    int[] width = new int[1];
    int[] height = new int[1];
    int[] channels = new int[1];

    ByteBuffer texture = STBImage.stbi_load(path, width, height, channels, 0);
    this.width = width[0];
    this.height = height[0];

    textureId = glCreateTextures(GL_TEXTURE_2D);
    glTextureStorage2D(textureId, 1, GL_RGB8, this.width, this.height);

    glTextureParameteri(textureId, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTextureParameteri(textureId, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    glTextureSubImage2D(textureId, 0, 0, 0, this.width, this.height, GL_RGB, GL_UNSIGNED_BYTE, texture);

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
    glBindTextureUnit(slot, textureId);
  }

  @Override
  public void dispose() {
    glDeleteTextures(textureId);
  }
}
