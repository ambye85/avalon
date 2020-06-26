package uk.ashleybye.avalon.platform.opengl;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11C.GL_VENDOR;
import static org.lwjgl.opengl.GL11C.glGetString;

import uk.ashleybye.avalon.renderer.GraphicsContext;

public class OpenGLContext implements GraphicsContext {

  private final long windowId;

  public OpenGLContext(long windowId) {
    this.windowId = windowId;
  }

  @Override
  public void initialise() {
    glfwMakeContextCurrent(windowId);
    createCapabilities();

    System.out.println("OpenGL info:");
    System.out.println("  Vendor:   " + glGetString(GL_VENDOR));
    System.out.println("  Renderer: " + glGetString(GL_VENDOR));
    System.out.println("  Version:  " + glGetString(GL_VENDOR));
  }

  @Override
  public void swapBuffers() {
    glfwSwapBuffers(windowId);
  }
}
