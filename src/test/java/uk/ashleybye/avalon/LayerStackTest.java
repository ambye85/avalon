package uk.ashleybye.avalon;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class LayerStackTest {

  @Test
  void pushSingleLayerOntoStack() {
    var layer = new LayerStub("layer 0");
    var stack = new LayerStack();

    stack.pushLayer(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
  }

  @Test
  void pushMultipleLayersOntoStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushLayer(layer0);
    stack.pushLayer(layer1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
    assertThat(layers.get(1).getDebugName()).isEqualTo("layer 1");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 1");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 1");
    assertThat(layers.get(1).getDebugName()).isEqualTo("layer 2");
  }

  @Test
  void pushSingleOverlayOntoStack() {
    var layer = new LayerStub("layer 0");
    var stack = new LayerStack();

    stack.pushOverlay(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
  }

  @Test
  void pushMultipleOverlaysOntoStack() {
    var layer0 = new LayerStub("layer 0");
    var layer1 = new LayerStub("layer 1");
    var stack = new LayerStack();

    stack.pushOverlay(layer0);
    stack.pushOverlay(layer1);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
    assertThat(layers.get(1).getDebugName()).isEqualTo("layer 1");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(1);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 1");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 1");
    assertThat(layers.get(1).getDebugName()).isEqualTo("layer 2");
  }

  @Test
  void pushOverlayBeforePushLayerOverlayComesAfterLayer() {
    var layer = new LayerStub("layer");
    var overlay = new LayerStub("overlay");
    var stack = new LayerStack();

    stack.pushOverlay(overlay);
    stack.pushLayer(layer);

    List<Layer> layers = new ArrayList<>();
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(2);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer");
    assertThat(layers.get(1).getDebugName()).isEqualTo("overlay");
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
    for (Layer l : stack) {
      layers.add(l);
    }

    assertThat(layers.size()).isEqualTo(4);
    assertThat(layers.get(0).getDebugName()).isEqualTo("layer 0");
    assertThat(layers.get(1).getDebugName()).isEqualTo("layer 2");
    assertThat(layers.get(2).getDebugName()).isEqualTo("overlay 0");
    assertThat(layers.get(3).getDebugName()).isEqualTo("overlay 2");
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
    assertThat(layers.get(0).getDebugName()).isEqualTo("overlay");
    assertThat(layers.get(1).getDebugName()).isEqualTo("layer");
  }
}

class LayerStub extends Layer {

  public LayerStub(String debugName) {
    super(debugName);
  }
}
