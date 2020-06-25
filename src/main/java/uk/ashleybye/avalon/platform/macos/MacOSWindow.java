package uk.ashleybye.avalon.platform.macos;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import uk.ashleybye.avalon.renderer.GraphicsContext;
import uk.ashleybye.avalon.event.EventCallback;
import uk.ashleybye.avalon.event.KeyPressedEvent;
import uk.ashleybye.avalon.event.KeyReleasedEvent;
import uk.ashleybye.avalon.event.KeyTypedEvent;
import uk.ashleybye.avalon.event.MouseButtonPressedEvent;
import uk.ashleybye.avalon.event.MouseButtonReleasedEvent;
import uk.ashleybye.avalon.event.MouseMovedEvent;
import uk.ashleybye.avalon.event.MouseScrolledEvent;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.event.WindowResizeEvent;
import uk.ashleybye.avalon.platform.opengl.OpenGLContext;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public class MacOSWindow implements Window {

  private static boolean GLFWInitialised = false;
  private final long windowId;
  private final GraphicsContext context;
  private final String title;
  private final GLFWWindowSizeCallback windowSizeCallback;
  private final GLFWWindowCloseCallback windowCloseCallback;
  private final GLFWKeyCallback keyCallback;
  private final GLFWCharCallback charCallback;
  private final GLFWMouseButtonCallback mouseButtonCallback;
  private final GLFWScrollCallback scrollCallback;
  private final GLFWCursorPosCallback cursorPosCallback;
  private final EventCallback eventCallback;
  private int width;
  private int height;
  private boolean vSync;
  private GLFWErrorCallback errorCallback;

  public static Window create(WindowProperties properties) {
    return new MacOSWindow(properties);
  }

  private MacOSWindow(WindowProperties properties) {
    title = properties.title();
    width = properties.width();
    height = properties.height();
    vSync = properties.vSync();
    eventCallback = properties.eventCallback();

    if (!GLFWInitialised) {
      glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

      if (!glfwInit()) {
        throw new IllegalStateException("Unable to initialize GLFW");
      }

      glfwDefaultWindowHints(); // optional, the current window hints are already the default
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
      glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
      glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

      GLFWInitialised = true;
    }

    windowId = glfwCreateWindow(width, height, title, NULL, NULL);
    context = new OpenGLContext(windowId);
    context.initialise();
    setVSync(true);

    glfwSetWindowSizeCallback(windowId, windowSizeCallback = new GLFWWindowSizeCallback() {
      @Override
      public void invoke(long wnd, int w, int h) {
        width = w;
        height = h;

        var event = new WindowResizeEvent(width, height);
        eventCallback.call(event);
      }
    });

    glfwSetWindowCloseCallback(windowId, windowCloseCallback = new GLFWWindowCloseCallback() {
      @Override
      public void invoke(long window) {
        var event = new WindowCloseEvent();
        eventCallback.call(event);
      }
    });

    glfwSetKeyCallback(windowId, keyCallback = new GLFWKeyCallback() {
      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        var event = switch (action) {
          case GLFW_PRESS:
            yield new KeyPressedEvent(key, 0);
          case GLFW_RELEASE:
            yield new KeyReleasedEvent(key);
          case GLFW_REPEAT:
            yield new KeyPressedEvent(key, 1); // TODO: implement repeat count functionality.
          default:
            throw new IllegalStateException("Unexpected value: " + action);
        };
        eventCallback.call(event);
      }
    });

    glfwSetCharCallback(windowId, charCallback = new GLFWCharCallback() {
      @Override
      public void invoke(long window, int codepoint) {
        var event = new KeyTypedEvent(codepoint);
        eventCallback.call(event);
      }
    });

    glfwSetMouseButtonCallback(windowId, mouseButtonCallback = new GLFWMouseButtonCallback() {
      @Override
      public void invoke(long window, int button, int action, int mods) {
        var event = switch (action) {
          case GLFW_PRESS:
            yield new MouseButtonPressedEvent(button);
          case GLFW_RELEASE:
            yield new MouseButtonReleasedEvent(button);
          default:
            throw new IllegalStateException("Unexpected value: " + action);
        };
        eventCallback.call(event);
      }
    });

    glfwSetScrollCallback(windowId, scrollCallback = new GLFWScrollCallback() {
      @Override
      public void invoke(long window, double xOffset, double yOffset) {
        var event = new MouseScrolledEvent(xOffset, yOffset);
        eventCallback.call(event);
      }
    });

    glfwSetCursorPosCallback(windowId, cursorPosCallback = new GLFWCursorPosCallback() {
      @Override
      public void invoke(long window, double xPos, double yPos) {
        var event = new MouseMovedEvent(xPos, yPos);
        eventCallback.call(event);
      }
    });

    glfwShowWindow(windowId);
  }

  @Override public long getWindowId() {
    return windowId;
  }

  @Override public int getWidth() {
    return width;
  }

  @Override public int getHeight() {
    return height;
  }

  @Override public void onUpdate() {
    glfwPollEvents();
    context.swapBuffers();
  }

  @Override public boolean isVSync() {
    return vSync;
  }

  @Override public void setVSync(boolean enabled) {
    if (enabled) {
      glfwSwapInterval(1);
    } else {
      glfwSwapInterval(0);
    }

    vSync = enabled;
  }

  @Override public void dispose() {
    glfwDestroyWindow(windowId);
    windowSizeCallback.free();
    windowCloseCallback.free();
    keyCallback.free();
    charCallback.free();
    mouseButtonCallback.free();
    scrollCallback.free();
    cursorPosCallback.free();

    glfwTerminate();
    errorCallback.free();
  }
}
