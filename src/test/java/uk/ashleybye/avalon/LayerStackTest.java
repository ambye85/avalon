package uk.ashleybye.avalon;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

class LayerStackTest {

  @Test
  void pushSingleLayerOntoStack() {
    var layer = new LayerStub("layer");
    var stack = new LayerStack();

    stack.pushLayer(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer.getDebugName());
  }

  @Test
  void pushMultipleLayersOntoStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushLayer(layer0);
    stack.pushLayer(layer1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer0.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer1.getDebugName());
  }

  @Test
  void popTopLayerFromStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushLayer(layer0);
    stack.pushLayer(layer1);
    stack.popLayer(layer1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer0.getDebugName());
  }

  @Test
  void popAnyLayerFromStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushLayer(layer0);
    stack.pushLayer(layer1);
    stack.popLayer(layer0);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer1.getDebugName());
  }

  @Test
  void pushLayerOntoStackAfterPopLayerFromStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var layer2 = new LayerStub("layer 2");
    var stack = new LayerStack();

    stack.pushLayer(layer0);
    stack.pushLayer(layer1);
    stack.popLayer(layer0);
    stack.pushLayer(layer2);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer1.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer2.getDebugName());
  }

  @Test
  void pushSingleOverlayOntoStack() {
    var layer = new LayerStub("layer");
    var stack = new LayerStack();

    stack.pushOverlay(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer.getDebugName());
  }

  @Test
  void pushMultipleOverlaysOntoStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushOverlay(layer0);
    stack.pushOverlay(layer1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer0.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer1.getDebugName());
  }

  @Test
  void popTopOverlayFromStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushOverlay(layer0);
    stack.pushOverlay(layer1);
    stack.popOverlay(layer1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer0.getDebugName());
  }

  @Test
  void popAnyOverlayFromStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushOverlay(layer0);
    stack.pushOverlay(layer1);
    stack.popOverlay(layer0);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer1.getDebugName());
  }

  @Test
  void pushOverlayOntoStackAfterPopOverlayFromStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var layer2 = new LayerStub("layer 2");
    var stack = new LayerStack();

    stack.pushOverlay(layer0);
    stack.pushOverlay(layer1);
    stack.popOverlay(layer0);
    stack.pushOverlay(layer2);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer1.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer2.getDebugName());
  }

  @Test
  void pushOverlayBeforePushLayerOverlayComesAfterLayer() {
    var layer = new LayerStub("layer");
    var overlay = new LayerStub("overlay");
    var stack = new LayerStack();

    stack.pushOverlay(overlay);
    stack.pushLayer(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(overlay.getDebugName());
  }

  @Test
  void pushAndPopLayersAndOverlaysArbitrarily() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var layer2 = new LayerStub("layer 2");
    var overlay0 = new LayerStub("overlay 0");
    var overlay1 = new LayerStub("overlay 1");
    var overlay2 = new LayerStub("overlay 2");
    var stack = new LayerStack();

    stack.pushOverlay(overlay0);
    stack.pushLayer(layer0);
    stack.pushLayer(layer1);
    stack.pushOverlay(overlay1);
    stack.pushOverlay(overlay2);
    stack.popOverlay(overlay1);
    stack.popLayer(layer1);
    stack.pushLayer(layer2);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.all()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(4);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer0.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer2.getDebugName());
    assertThat(layers.get(2).getDebugName()).isEqualTo(overlay0.getDebugName());
    assertThat(layers.get(3).getDebugName()).isEqualTo(overlay2.getDebugName());
  }

  @Test
  void iteratesInReverseOrder() {
    var layer = new LayerStub("layer");
    var overlay = new LayerStub("overlay");
    var stack = new LayerStack();

    stack.pushOverlay(overlay);
    stack.pushLayer(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.reversed()) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(overlay.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer.getDebugName());
  }

  @Test
  void iteratesLayersAndOverlaysSeparately() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var overlay0 = new LayerStub("overlay 0");
    var overlay1 = new LayerStub("overlay 1");
    var stack = new LayerStack();

    stack.pushLayer(layer0);
    stack.pushLayer(layer1);
    stack.pushOverlay(overlay0);
    stack.pushOverlay(overlay1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack.allLayers()) {
      layers.add(l);
    }

    List<Layer> overlays = new ArrayList<>();
    for (Layer o : stack.allOverlays()) {
      overlays.add(o);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo(layer0.getDebugName());
    assertThat(layers.get(1).getDebugName()).isEqualTo(layer1.getDebugName());

    assertThat(overlays.size()).isEqualTo(2);
    assertThat(overlays.get(0).getDebugName()).isEqualTo(overlay0.getDebugName());
    assertThat(overlays.get(1).getDebugName()).isEqualTo(overlay1.getDebugName());
  }
}

class LayerStub extends Layer {

  public LayerStub(String debugName) {
    super(debugName);
  }
}
