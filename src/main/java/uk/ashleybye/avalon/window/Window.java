package uk.ashleybye.avalon.window;

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
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import uk.ashleybye.avalon.event.EventCallback;
import uk.ashleybye.avalon.event.KeyPressedEvent;
import uk.ashleybye.avalon.event.KeyReleasedEvent;
import uk.ashleybye.avalon.event.MouseButtonPressedEvent;
import uk.ashleybye.avalon.event.MouseButtonReleasedEvent;
import uk.ashleybye.avalon.event.MouseMovedEvent;
import uk.ashleybye.avalon.event.MouseScrolledEvent;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.event.WindowResizeEvent;

public class Window {

  private static boolean GLFWInitialised = false;
  private final long window;
  private final String title;
  private final GLFWWindowSizeCallback windowSizeCallback;
  private final GLFWWindowCloseCallback windowCloseCallback;
  private final GLFWKeyCallback keyCallback;
  private final GLFWMouseButtonCallback mouseButtonCallback;
  private final GLFWScrollCallback scrollCallback;
  private final GLFWCursorPosCallback cursorPosCallback;
  private int width;
  private int height;
  private boolean vSync;
  private final EventCallback eventCallback;
  private GLFWErrorCallback errorCallback;

  private Window(WindowProperties properties) {
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

    window = glfwCreateWindow(width, height, title, NULL, NULL);
    glfwMakeContextCurrent(window);
    setVSync(true);

    glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
      @Override
      public void invoke(long wnd, int w, int h) {
        width = w;
        height = h;

        var event = new WindowResizeEvent(width, height);
        eventCallback.call(event);
      }
    });

    glfwSetWindowCloseCallback(window, windowCloseCallback = new GLFWWindowCloseCallback() {
      @Override
      public void invoke(long window) {
        var event = new WindowCloseEvent();
        eventCallback.call(event);
      }
    });

    glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
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

    glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback() {
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

    glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
      @Override
      public void invoke(long window, double xOffset, double yOffset) {
        var event = new MouseScrolledEvent(xOffset, yOffset);
        eventCallback.call(event);
      }
    });

    glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback() {
      @Override
      public void invoke(long window, double xPos, double yPos) {
        var event = new MouseMovedEvent(xPos, yPos);
        eventCallback.call(event);
      }
    });

    createCapabilities();
    glfwShowWindow(window);
  }

  public static Window create(WindowProperties properties) {
    return new Window(properties);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void onUpdate() {
    glfwPollEvents();
    glfwSwapBuffers(window);
  }

  public boolean isVSync() {
    return vSync;
  }

  public void setVSync(boolean enabled) {
    if (enabled) {
      glfwSwapInterval(1);
    } else {
      glfwSwapInterval(0);
    }

    vSync = enabled;
  }

  public void close() {
    glfwDestroyWindow(window);
    windowSizeCallback.free();
    windowCloseCallback.free();
    keyCallback.free();
    mouseButtonCallback.free();
    scrollCallback.free();
    cursorPosCallback.free();

    glfwTerminate();
    errorCallback.free();
  }
}
