package uk.ashleybye.avalon.imgui;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11C.glViewport;
import static uk.ashleybye.avalon.input.KeyCodes.*;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseCursor;
import imgui.gl3.ImGuiImplGl3;
import imgui.type.ImBoolean;
import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.KeyPressedEvent;
import uk.ashleybye.avalon.event.KeyReleasedEvent;
import uk.ashleybye.avalon.event.KeyTypedEvent;
import uk.ashleybye.avalon.event.MouseButtonPressedEvent;
import uk.ashleybye.avalon.event.MouseButtonReleasedEvent;
import uk.ashleybye.avalon.event.MouseMovedEvent;
import uk.ashleybye.avalon.event.MouseScrolledEvent;
import uk.ashleybye.avalon.event.WindowResizeEvent;

public class ImGuiLayer extends Layer {

  // Mouse cursors provided by GLFW
  private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
  private float elapsedTime = 0.0f;
  private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

  public ImGuiLayer() {
    super("ImGuiLayer");
  }

  public void dispose() {
    imGuiGl3.dispose();
    ImGui.destroyContext();
  }

  @Override
  public void onAttach() {
    ImGui.createContext();
    ImGui.styleColorsDark();

    final ImGuiIO io = ImGui.getIO();

    io.setIniFilename(null); // We don't want to save .ini file
    io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard
        | ImGuiConfigFlags.DockingEnable); // Navigation with keyboard and enabled docking
    io.setBackendFlags(
        ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
    io.setBackendPlatformName("imgui_java_impl_glfw");

    // ------------------------------------------------------------
    // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
    final int[] keyMap = new int[ImGuiKey.COUNT];
    keyMap[ImGuiKey.Tab] = AVALON_KEY_TAB;
    keyMap[ImGuiKey.LeftArrow] = AVALON_KEY_LEFT;
    keyMap[ImGuiKey.RightArrow] = AVALON_KEY_RIGHT;
    keyMap[ImGuiKey.UpArrow] = AVALON_KEY_UP;
    keyMap[ImGuiKey.DownArrow] = AVALON_KEY_DOWN;
    keyMap[ImGuiKey.PageUp] = AVALON_KEY_PAGE_UP;
    keyMap[ImGuiKey.PageDown] = AVALON_KEY_PAGE_DOWN;
    keyMap[ImGuiKey.Home] = AVALON_KEY_HOME;
    keyMap[ImGuiKey.End] = AVALON_KEY_END;
    keyMap[ImGuiKey.Insert] = AVALON_KEY_INSERT;
    keyMap[ImGuiKey.Delete] = AVALON_KEY_DELETE;
    keyMap[ImGuiKey.Backspace] = AVALON_KEY_BACKSPACE;
    keyMap[ImGuiKey.Space] = AVALON_KEY_SPACE;
    keyMap[ImGuiKey.Enter] = AVALON_KEY_ENTER;
    keyMap[ImGuiKey.Escape] = AVALON_KEY_ESCAPE;
    keyMap[ImGuiKey.KeyPadEnter] = AVALON_KEY_KP_ENTER;
    keyMap[ImGuiKey.A] = AVALON_KEY_A;
    keyMap[ImGuiKey.C] = AVALON_KEY_C;
    keyMap[ImGuiKey.V] = AVALON_KEY_V;
    keyMap[ImGuiKey.X] = AVALON_KEY_X;
    keyMap[ImGuiKey.Y] = AVALON_KEY_Y;
    keyMap[ImGuiKey.Z] = AVALON_KEY_Z;
    io.setKeyMap(keyMap);

    // ------------------------------------------------------------
    // Mouse cursors mapping
    mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
    mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
    mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
    mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
    mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
    mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
    mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
    mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
    mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);

    imGuiGl3.init("#version 410");
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onUpdate() {
    final ImGuiIO io = ImGui.getIO();
    Application application = Application.getInstance();
    io.setDisplaySize(application.getWindow().getWidth(), application.getWindow().getHeight());

    float time = (float) glfwGetTime();
    io.setDeltaTime(elapsedTime > 0.0f ? time - elapsedTime : 1.0f / 60.0f);
    elapsedTime = time;

    ImGui.newFrame();

    ImBoolean show = new ImBoolean();
    show.set(true);
    ImGui.showDemoWindow(show);

    ImGui.render();
    imGuiGl3.render(ImGui.getDrawData());
  }

  @Override
  public void onEvent(Event event) {
    var dispatcher = new EventDispatcher(event);
    dispatcher.dispatch(MouseButtonPressedEvent.class, this::onMouseButtonPressedEvent);
    dispatcher.dispatch(MouseButtonReleasedEvent.class, this::onMouseButtonReleasedEvent);
    dispatcher.dispatch(MouseMovedEvent.class, this::onMouseMovedEvent);
    dispatcher.dispatch(MouseScrolledEvent.class, this::onMouseScrolledEvent);
    dispatcher.dispatch(KeyPressedEvent.class, this::onKeyPressedEvent);
    dispatcher.dispatch(KeyReleasedEvent.class, this::onKeyReleasedEvent);
    dispatcher.dispatch(KeyTypedEvent.class, this::onKeyTypedEvent);
    dispatcher.dispatch(WindowResizeEvent.class, this::onWindowResizedEvent);
  }

  private boolean onMouseButtonPressedEvent(Event event) {
    MouseButtonPressedEvent e = (MouseButtonPressedEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setMouseDown(e.getButton(), true);

    return false;
  }

  private boolean onMouseButtonReleasedEvent(Event event) {
    MouseButtonReleasedEvent e = (MouseButtonReleasedEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setMouseDown(e.getButton(), false);

    return false;
  }

  private boolean onMouseMovedEvent(Event event) {
    MouseMovedEvent e = (MouseMovedEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setMousePos((float) e.getXPos(), (float) e.getYPos());

    return false;
  }

  private boolean onMouseScrolledEvent(Event event) {
    MouseScrolledEvent e = (MouseScrolledEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setMouseWheel((float) e.getYOffset());
    io.setMouseWheelH((float) e.getXOffset());

    return false;
  }

  private boolean onKeyPressedEvent(Event event) {
    KeyPressedEvent e = (KeyPressedEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setKeysDown(e.getKeyCode(), true);

    io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
    io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
    io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
    io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));

    return false;
  }

  private boolean onKeyReleasedEvent(Event event) {
    KeyReleasedEvent e = (KeyReleasedEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setKeysDown(e.getKeyCode(), false);

    io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
    io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
    io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
    io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));

    return false;
  }

  private boolean onKeyTypedEvent(Event event) {
    KeyTypedEvent e = (KeyTypedEvent) event;
    ImGuiIO io = ImGui.getIO();
    int keyCode = e.getKeyCode();
    if (keyCode > 0 && keyCode < 0x10_000) {
      io.addInputCharacter((char) keyCode);
    }

    return false;
  }

  private boolean onWindowResizedEvent(Event event) {
    WindowResizeEvent e = (WindowResizeEvent) event;
    ImGuiIO io = ImGui.getIO();
    io.setDisplaySize(e.getWidth(), e.getHeight());
    io.setDisplayFramebufferScale(1.0f, 1.0f);
    glViewport(0, 0, e.getWidth(), e.getHeight());

    return false;
  }
}
