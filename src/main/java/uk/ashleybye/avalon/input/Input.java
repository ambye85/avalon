package uk.ashleybye.avalon.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;
import java.util.Map;
import org.lwjgl.system.MemoryStack;
import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.Pair;

public class Input {

  public static boolean isKeyPressed(int keyCode) {
    var windowId = Application.getInstance().getWindow().getWindowId();
    var state = glfwGetKey(windowId, keyCode);
    return state == GLFW_PRESS || state == GLFW_REPEAT;
  }

  public static boolean isMouseButtonPressed(int button) {
    var windowId = Application.getInstance().getWindow().getWindowId();
    var state = glfwGetMouseButton(windowId, button);
    return state == GLFW_PRESS;
  }

  public static Pair<Double, Double> getMousePosition() {
    var windowId = Application.getInstance().getWindow().getWindowId();

    try (MemoryStack stack = MemoryStack.stackPush()) {
      DoubleBuffer xPos = stack.mallocDouble(1);
      DoubleBuffer yPos = stack.mallocDouble(1);
      glfwGetCursorPos(windowId, xPos, yPos);
      return new Pair<>(xPos.get(), yPos.get());
    }
  }

  public static double getMouseX() {
    return getMousePosition().x();
  }

  public static double getMouseY() {
    return getMousePosition().y();
  }
}
