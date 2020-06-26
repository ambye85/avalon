package uk.ashleybye.avalon;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL11C.glDrawElements;
import static uk.ashleybye.avalon.Logger.Color.GREEN;

import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.imgui.ImGuiLayer;
import uk.ashleybye.avalon.platform.macos.MacOSWindow;
import uk.ashleybye.avalon.platform.opengl.OpenGLIndexBuffer;
import uk.ashleybye.avalon.platform.opengl.OpenGLVertexArray;
import uk.ashleybye.avalon.platform.opengl.OpenGLVertexBuffer;
import uk.ashleybye.avalon.renderer.BufferLayout;
import uk.ashleybye.avalon.renderer.Shader;
import uk.ashleybye.avalon.renderer.ShaderDataType;
import uk.ashleybye.avalon.renderer.VertexArray;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public abstract class Application {

  private static Application instance = null;
  private static final Logger logger = Logger.builder("AVALON", GREEN).build();
  private final LayerStack layers;
  private final ImGuiLayer imGuiLayer;
  private boolean running = false;
  private final Window window;
  private final VertexArray triangleVertexArray;
  private final VertexArray squareVertexArray;
  private final Shader colourShader;
  private final Shader blueShader;

  public Application() {
    instance = this;

    var properties = new WindowProperties("Avalon", 1280, 720, true, this::onEvent);
    window = MacOSWindow.create(properties);
    layers = new LayerStack();

    imGuiLayer = new ImGuiLayer();
    pushOverlay(imGuiLayer);

    float[] triangleVertices = new float[]{
        -0.5F, -0.5F, 0.0F, 0.8F, 0.2F, 0.8F, 1.0F,
        +0.5F, -0.5F, 0.0F, 0.2F, 0.3F, 0.8F, 1.0F,
        +0.0F, +0.5F, 0.0F, 0.8F, 0.8F, 0.2F, 1.0F,
    };

    triangleVertexArray = new OpenGLVertexArray();
    BufferLayout triangleLayout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .addElement(ShaderDataType.FLOAT_4, "a_Color")
        .build();
    var triangleVertexBuffer = new OpenGLVertexBuffer(triangleVertices);
    triangleVertexBuffer.setLayout(triangleLayout);
    triangleVertexArray.addVertexBuffer(triangleVertexBuffer);

    int[] triangleIndices = new int[]{0, 1, 2};
    var triangleIndexBuffer = new OpenGLIndexBuffer(triangleIndices);
    triangleVertexArray.setIndexBuffer(triangleIndexBuffer);

    float[] squareVertices = new float[]{
        -0.75F, -0.75F, 0.0F,
        +0.75F, -0.75F, 0.0F,
        +0.75F, +0.75F, 0.0F,
        -0.75F, +0.75F, 0.0F,
    };

    squareVertexArray = new OpenGLVertexArray();
    BufferLayout squareLayout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .build();
    var squareVertexBuffer = new OpenGLVertexBuffer(squareVertices);
    squareVertexBuffer.setLayout(squareLayout);
    squareVertexArray.addVertexBuffer(squareVertexBuffer);

    int[] squareIndices = new int[]{0, 1, 2, 2, 3, 0};
    var squareIndexBuffer = new OpenGLIndexBuffer(squareIndices);
    squareVertexArray.setIndexBuffer(squareIndexBuffer);

    String colourVertexSource = """
        #version 330 core

        layout(location = 0) in vec3 a_Position;
        layout(location = 1) in vec4 a_Color;

        out vec4 v_Color;

        void main()
        {
          v_Color = a_Color;
          gl_Position = vec4(a_Position, 1.0);
        }""";

    String colourFragmentSource = """
        #version 330 core

        in vec4 v_Color;

        out vec4 color;

        void main()
        {
          color = v_Color;
        }""";

    colourShader = new Shader(colourVertexSource, colourFragmentSource);

    String blueVertexSource = """
        #version 330 core

        layout(location = 0) in vec3 a_Position;

        void main()
        {
          gl_Position = vec4(a_Position, 1.0);
        }""";

    String blueFragmentSource = """
        #version 330 core

        out vec4 color;

        void main()
        {
          color = vec4(0.2, 0.3, 0.8, 1.0);
        }""";

    blueShader = new Shader(blueVertexSource, blueFragmentSource);
  }

  public static Application getInstance() {
    return instance;
  }

  public final Window getWindow() {
    return window;
  }

  public final void run() {
    running = true;
    while (running) {
      glClearColor(0.1F, 0.1F, 0.1F, 1.0F);
      glClear(GL_COLOR_BUFFER_BIT);

      blueShader.bind();
      squareVertexArray.bind();
      glDrawElements(GL_TRIANGLES, squareVertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT,
          0L);
      squareVertexArray.unbind();
      blueShader.unbind();
      colourShader.bind();
      triangleVertexArray.bind();
      glDrawElements(GL_TRIANGLES, triangleVertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT,
          0L);
      triangleVertexArray.unbind();
      colourShader.unbind();

      for (var layer : layers) {
        layer.onUpdate();
      }

      imGuiLayer.begin();
      for (var layer : layers) {
        layer.onImGuiRender();
      }
      imGuiLayer.end();

      window.onUpdate();
    }

    colourShader.dispose();
    triangleVertexArray.dispose();
    popOverlay(imGuiLayer);
    window.dispose();
  }

  public final boolean onEvent(Event e) {
    var dispatcher = new EventDispatcher(e);
    dispatcher.dispatch(WindowCloseEvent.class, this::onWindowClose);

    for (var layer : layers.reversed()) {
      layer.onEvent(e);
      if (e.isHandled()) {
        break;
      }
    }

    return false;
  }

  public final void pushLayer(Layer layer) {
    layers.pushLayer(layer);
    layer.onAttach();
  }

  public final void pushOverlay(Layer overlay) {
    layers.pushOverlay(overlay);
    overlay.onAttach();
  }

  public final void popLayer(Layer layer) {
    layers.popLayer(layer);
    layer.onDetach();
  }

  public final void popOverlay(Layer overlay) {
    layers.popOverlay(overlay);
    overlay.onDetach();
  }

  private boolean onWindowClose(Event e) {
    running = false;
    e.setHandled(true);
    return true;
  }
}
