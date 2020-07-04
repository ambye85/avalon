# Avalon

## Testing

From the project root directory, run:

```shell script
./gradlew cleanTest test
```

## ImGui HiDPI Scaling

https://github.com/ocornut/imgui/blob/master/docs/FAQ.md#q-how-should-i-handle-dpi-in-my-application

ImGui does not adjuct scale automatically for HiDPI displays. This means that we get a really
small UI on on HiDPI / retina displays. We can fix that simply by multiplying by the
scale factor:

```java
Application app = Application.getInstance();
long windowId = app.getWindow().getWindowId();
float[] windowX = new float[1];
float[] windowY = new float[1];
glfwGetWindowContentScale(windowId, windowX, windowY);
var scaleX = windowX[0];
var scaleY = windowY[0];

ImGui.setNextWindowPos(20, 20);
ImGui.setNextWindowSize(300 * scaleX, 200 * scaleY);
ImGui.getIO().setDisplaySize(1280 * scaleX, 720 * scaleY);
```

How do we set this for fonts? Reload each time. What about the default font?
```java
ImGui.getIO().setFontGlobalScale(scaleX); // Works, but font is a bit blurry. Should reload the font.

```

This must come after `ImGui.newFrame()` and before the relevant `ImGui.begin()` call. This
approach will not work for multi-viewport, see [FAQ](https://github.com/ocornut/imgui/blob/master/docs/FAQ.md#q-how-should-i-handle-dpi-in-my-application).

## Useful reading

- [https://blog.lwjgl.org/memory-management-in-lwjgl-3/](https://blog.lwjgl.org/memory-management-in-lwjgl-3/)

## Acknowledgements

- [The Cherno](https://www.youtube.com/channel/UCQ-W1KE9EYfdxhL6S4twUNw)
- [Image of Duke waving](https://wiki.openjdk.java.net/display/duke/Gallery)
