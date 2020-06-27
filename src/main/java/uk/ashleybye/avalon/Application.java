package uk.ashleybye.avalon;

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
import uk.ashleybye.avalon.renderer.OrthographicCamera;
import uk.ashleybye.avalon.renderer.RenderCommand;
import uk.ashleybye.avalon.renderer.Renderer;
import uk.ashleybye.avalon.renderer.Shader;
import uk.ashleybye.avalon.renderer.ShaderDataType;
import uk.ashleybye.avalon.renderer.VertexArray;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public abstract class Application {

  private static final Logger logger = Logger.builder("AVALON", GREEN).build();
  private static Application instance = null;
  private final LayerStack layers;
  private final ImGuiLayer imGuiLayer;
  private final Window window;
  private final VertexArray triangleVertexArray;
  private final VertexArray squareVertexArray;
  private final Shader colourShader;
  private final Shader blueShader;
  private boolean running = false;
  private OrthographicCamera camera;

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
        
        uniform mat4 u_ViewProjection;

        out vec4 v_Color;

        void main()
        {
          v_Color = a_Color;
          gl_Position = u_ViewProjection * vec4(a_Position, 1.0);
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
        
        uniform mat4 u_ViewProjection;

        void main()
        {
          gl_Position = u_ViewProjection * vec4(a_Position, 1.0);
        }""";

    String blueFragmentSource = """
        #version 330 core

        out vec4 color;

        void main()
        {
          color = vec4(0.2, 0.3, 0.8, 1.0);
        }""";

    blueShader = new Shader(blueVertexSource, blueFragmentSource);

    camera = new OrthographicCamera(-1.6F, 1.6F, -0.9F, 0.9F);
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
      RenderCommand.setClearColor(0.1F, 0.1F, 0.1F, 1.0F);
      RenderCommand.clear();

      camera.setPosition(0.5F, 0.5F, 0.0F);
      camera.setRotation(45);

      Renderer.beginScene(camera);
      Renderer.submit(blueShader, squareVertexArray);
      Renderer.submit(colourShader, triangleVertexArray);
      Renderer.endScene();

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
