package sandbox;

import imgui.ImGui;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import uk.ashleybye.avalon.Layer;
import uk.ashleybye.avalon.OrthographicCameraController;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.platform.opengl.OpenGLShader;
import uk.ashleybye.avalon.renderer.BufferLayout;
import uk.ashleybye.avalon.renderer.IndexBuffer;
import uk.ashleybye.avalon.renderer.RenderCommand;
import uk.ashleybye.avalon.renderer.Renderer;
import uk.ashleybye.avalon.renderer.Shader;
import uk.ashleybye.avalon.renderer.ShaderDataType;
import uk.ashleybye.avalon.renderer.ShaderLibrary;
import uk.ashleybye.avalon.renderer.Texture2D;
import uk.ashleybye.avalon.renderer.VertexArray;
import uk.ashleybye.avalon.renderer.VertexBuffer;

class ExampleLayer extends Layer {

  private final ShaderLibrary shaderLibrary;
  private final OrthographicCameraController cameraController;
  private final VertexArray triangleVertexArray;
  private final VertexArray squareVertexArray;
  private final Vector3f squarePosition;
  private final Matrix4f squareScale;
  private final float[] squareColor = new float[]{0.2F, 0.3F, 0.8F};
  private final Texture2D checkerboardTexture;
  private final Texture2D dukeWavingTexture;

  public ExampleLayer() {
    super("Example Layer");

    float[] triangleVertices = new float[]{
        -0.5F, -0.5F, 0.0F, 0.8F, 0.2F, 0.8F, 1.0F,
        +0.5F, -0.5F, 0.0F, 0.2F, 0.3F, 0.8F, 1.0F,
        +0.0F, +0.5F, 0.0F, 0.8F, 0.8F, 0.2F, 1.0F,
    };

    triangleVertexArray = VertexArray.create();
    BufferLayout triangleLayout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .addElement(ShaderDataType.FLOAT_4, "a_Color")
        .build();
    var triangleVertexBuffer = VertexBuffer.create(triangleVertices);
    triangleVertexBuffer.setLayout(triangleLayout);
    triangleVertexArray.addVertexBuffer(triangleVertexBuffer);

    int[] triangleIndices = new int[]{0, 1, 2};
    var triangleIndexBuffer = IndexBuffer.create(triangleIndices);
    triangleVertexArray.setIndexBuffer(triangleIndexBuffer);

    float[] squareVertices = new float[]{
        -0.5F, -0.5F, 0.0F, 0.0F, 0.0F,
        +0.5F, -0.5F, 0.0F, 1.0F, 0.0F,
        +0.5F, +0.5F, 0.0F, 1.0F, 1.0F,
        -0.5F, +0.5F, 0.0F, 0.0F, 1.0F,
    };

    squareVertexArray = VertexArray.create();
    BufferLayout squareLayout = BufferLayout.builder()
        .addElement(ShaderDataType.FLOAT_3, "a_Position")
        .addElement(ShaderDataType.FLOAT_2, "a_TexCoord")
        .build();
    var squareVertexBuffer = VertexBuffer.create(squareVertices);
    squareVertexBuffer.setLayout(squareLayout);
    squareVertexArray.addVertexBuffer(squareVertexBuffer);

    int[] squareIndices = new int[]{0, 1, 2, 2, 3, 0};
    var squareIndexBuffer = IndexBuffer.create(squareIndices);
    squareVertexArray.setIndexBuffer(squareIndexBuffer);

    String colourVertexSource = """
        #version 330 core

        layout(location = 0) in vec3 a_Position;
        layout(location = 1) in vec4 a_Color;
                
        uniform mat4 u_ViewProjection;
        uniform mat4 u_Transform;

        out vec4 v_Color;

        void main()
        {
          v_Color = a_Color;
          gl_Position = u_ViewProjection * u_Transform * vec4(a_Position, 1.0);
        }""";

    String colourFragmentSource = """
        #version 330 core

        in vec4 v_Color;

        out vec4 color;

        void main()
        {
          color = v_Color;
        }""";

    String flatColorVertexSource = """
        #version 330 core

        layout(location = 0) in vec3 a_Position;
                
        uniform mat4 u_ViewProjection;
        uniform mat4 u_Transform;

        void main()
        {
          gl_Position = u_ViewProjection * u_Transform * vec4(a_Position, 1.0);
        }""";

    String flatColorFragmentSource = """
        #version 330 core

        out vec4 color;
                
        uniform vec3 u_Color;

        void main()
        {
          color = vec4(u_Color, 1.0);
        }""";

    shaderLibrary = new ShaderLibrary();
    shaderLibrary
        .add(Shader.create("vertexPosColour", colourVertexSource, colourFragmentSource));
    shaderLibrary
        .add(Shader.create("flatColour", flatColorVertexSource, flatColorFragmentSource));
    shaderLibrary.load("shaders/texture.glsl");

    checkerboardTexture = Texture2D.create("textures/Checkerboard.png");
    dukeWavingTexture = Texture2D.create("textures/Duke_waving.png");

    var textureShader = shaderLibrary.get("texture").orElseThrow(() -> new RuntimeException("texture shader not found"));
    textureShader.bind();
    ((OpenGLShader) textureShader).uploadUniform("u_Texture", 0);

    squarePosition = new Vector3f();
    squareScale = new Matrix4f().scale(0.1F);

    cameraController = new OrthographicCameraController(
        1280.0 / 720.0); // This is also 16.0/9.0 or 16:9.
    cameraController.setRotationEnabled(true);
  }

  @Override
  public void onUpdate(double dt) {
    cameraController.onUpdate(dt);

    RenderCommand.setClearColor(0.1F, 0.1F, 0.1F, 1.0F);
    RenderCommand.clear();

    Renderer.beginScene(cameraController.getCamera());

    var flatColorShader = shaderLibrary.get("flatColour").orElseThrow(() -> new RuntimeException("flatColour shader not found"));
    flatColorShader.bind();
    flatColorShader.setData("u_Color", new Vector3f(squareColor));

    for (int y = 0; y < 20; y++) {
      for (int x = 0; x < 20; x++) {
        squarePosition.x = x * 0.11F;
        squarePosition.y = y * 0.11F;
        Matrix4f transform = new Matrix4f().translate(squarePosition).mul(squareScale);
        Renderer.submit(flatColorShader, squareVertexArray, transform);
      }
    }

    var textureShader = shaderLibrary.get("texture").orElseThrow(() -> new RuntimeException("texture shader not found"));
    checkerboardTexture.bind(0);
    Renderer.submit(textureShader, squareVertexArray, new Matrix4f().scale(1.5F));
    checkerboardTexture.unbind();
    dukeWavingTexture.bind(0);
    Renderer.submit(textureShader, squareVertexArray, new Matrix4f().scale(1.5F));
    dukeWavingTexture.unbind();

//    Triangle
//    Renderer.submit(colourShader, triangleVertexArray);

    Renderer.endScene();
  }

  @Override
  public void onEvent(Event event) {
    cameraController.onEvent(event);
  }

  @Override
  public void onImGuiRender() {
//    ImGui.begin("test");
//    ImGui.text("Hello, world!");
//    ImGui.end();
    ImGui.begin("Settings");
    ImGui.colorEdit3("Square Color", squareColor);
    ImGui.end();
  }

  @Override
  public void onDetach() {
    shaderLibrary.dispose();
    checkerboardTexture.dispose();
    dukeWavingTexture.dispose();
    squareVertexArray.dispose();
    triangleVertexArray.dispose();
  }
}
