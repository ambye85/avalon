package uk.ashleybye.avalon;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL11C.glDrawElements;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static uk.ashleybye.avalon.Logger.Color.GREEN;

import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.WindowCloseEvent;
import uk.ashleybye.avalon.imgui.ImGuiLayer;
import uk.ashleybye.avalon.platform.macos.MacOSWindow;
import uk.ashleybye.avalon.platform.opengl.OpenGLIndexBuffer;
import uk.ashleybye.avalon.platform.opengl.OpenGLVertexBuffer;
import uk.ashleybye.avalon.renderer.BufferLayout;
import uk.ashleybye.avalon.renderer.IndexBuffer;
import uk.ashleybye.avalon.renderer.Shader;
import uk.ashleybye.avalon.renderer.ShaderDataType;
import uk.ashleybye.avalon.renderer.VertexBuffer;
import uk.ashleybye.avalon.window.Window;
import uk.ashleybye.avalon.window.WindowProperties;

public abstract class Application {

  private static Application instance = null;
  private static final Logger logger = Logger.builder("AVALON", GREEN).build();
  private final LayerStack layers;
  private final ImGuiLayer imGuiLayer;
  private boolean running = false;
  private final Window window;
  private final int vertexArray;
  private final VertexBuffer vertexBuffer;
  private final IndexBuffer indexBuffer;
  private Shader shader;

  public Application() {
    instance = this;

    var properties = new WindowProperties("Avalon", 1280, 720, true, this::onEvent);
    window = MacOSWindow.create(properties);
    layers = new LayerStack();

    imGuiLayer = new ImGuiLayer();
    pushOverlay(imGuiLayer);

    vertexArray = glGenVertexArrays();
    glBindVertexArray(vertexArray);

    float[] vertices = new float[]{
        -0.5f,
        -0.5f,
        0.0f,
        0.5f,
        -0.5f,
        0.0f,
        0.0f,
        0.5f,
        0.0f,
    };

    BufferLayout layout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .build();
    vertexBuffer = new OpenGLVertexBuffer(vertices);
    vertexBuffer.setLayout(layout);

    int index = 0;
    for (var element : vertexBuffer.getLayout()) {
      glEnableVertexAttribArray(index);
      glVertexAttribPointer(index, element.getComponentCount(), element.toOpenGLBaseType(), element.normalised(), vertexBuffer.getLayout().getStride(), element.offset());
      index++;
//    glEnableVertexAttribArray(1);
//    glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * Float.BYTES,
//        Float.BYTES * 3);
    }


    int[] indices = new int[]{0, 1, 2};
    indexBuffer = new OpenGLIndexBuffer(indices);

    String vertexSource = """
        #version 330 core
              
        layout(location = 0) in vec3 a_Position;
              
        out vec3 v_Position;
              
        void main()
        {
          v_Position = a_Position;
          gl_Position = vec4(a_Position, 1.0);
        }""";

    String fragmentSource = """
        #version 330 core
              
        in vec3 v_Position;
              
        out vec4 color;
              
        void main()
        {
        color = vec4(v_Position * 0.5 + 0.5, 1.0);
        }""";

    shader = new Shader(vertexSource, fragmentSource);
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

      shader.bind();
      glBindVertexArray(vertexArray);
      glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), GL_UNSIGNED_INT, 0L);
      shader.unbind();

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

    shader.dispose();
    indexBuffer.dispose();
    vertexBuffer.dispose();
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
