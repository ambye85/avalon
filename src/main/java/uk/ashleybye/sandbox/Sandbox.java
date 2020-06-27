package uk.ashleybye.sandbox;

import imgui.ImGui;
import org.joml.Vector3f;
import uk.ashleybye.avalon.Application;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.input.Input;
import uk.ashleybye.avalon.input.KeyCodes;
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

public class Sandbox extends Application {

  public Sandbox() {
    super();
    super.pushLayer(new ExampleLayer());
  }

  public static void main(String[] args) {
    var sandbox = new Sandbox();
    sandbox.run();
  }
}

class ExampleLayer extends Layer {

  private final VertexArray triangleVertexArray;
  private final VertexArray squareVertexArray;
  private final Shader colourShader;
  private final Shader blueShader;
  private final OrthographicCamera camera;
  private final Vector3f cameraPosition;
  private float cameraRotation;
  private final float cameraMovementSpeed = 0.1f;
  private final float cameraRotationSpeed = 2.0f;

  public ExampleLayer() {
    super("Example Layer");

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

    cameraPosition = new Vector3f(0.0F, 0.0F, 0.0F);
    cameraRotation = 0.0F;
    camera = new OrthographicCamera(-1.6F, 1.6F, -0.9F, 0.9F);
    camera.setPosition(cameraPosition);
    camera.setRotation(cameraRotation);
  }

  @Override
  public void onUpdate() {

    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_UP)) {
      cameraPosition.y -= cameraMovementSpeed;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_DOWN)) {
      cameraPosition.y += cameraMovementSpeed;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_LEFT)) {
      cameraPosition.x += cameraMovementSpeed;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_RIGHT)) {
      cameraPosition.x -= cameraMovementSpeed;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_A)) {
      cameraRotation += cameraRotationSpeed;
    }
    if (Input.isKeyPressed(KeyCodes.AVALON_KEY_D)) {
      cameraRotation -= cameraRotationSpeed;
    }
    camera.setPosition(cameraPosition);
    camera.setRotation(cameraRotation);

    RenderCommand.setClearColor(0.1F, 0.1F, 0.1F, 1.0F);
    RenderCommand.clear();

    Renderer.beginScene(camera);
    Renderer.submit(blueShader, squareVertexArray);
    Renderer.submit(colourShader, triangleVertexArray);
    Renderer.endScene();
  }

  @Override
  public void onImGuiRender() {
    ImGui.begin("test");
    ImGui.text("Hello, world!");
    ImGui.end();
  }

  @Override
  public void onDetach() {
    blueShader.dispose();
    colourShader.dispose();
    squareVertexArray.dispose();
    triangleVertexArray.dispose();
  }
}
