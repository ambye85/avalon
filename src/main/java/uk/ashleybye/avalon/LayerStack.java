package uk.ashleybye.avalon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LayerStack {

  private final List<Layer> layers;
  private int currentLayer;

  public LayerStack() {
    layers = new ArrayList<>();
    currentLayer = 0;
  }

  public void popOverlay(Layer overlay) {
    layers.remove(overlay);
  }

  public void pushOverlay(Layer overlay) {
    layers.add(overlay);
  }

  public void popLayer(Layer layer) {
    layers.remove(layer);
    currentLayer--;
  }

  public void pushLayer(Layer layer) {
    layers.add(currentLayer++, layer);
  }

  public LayerStackIterator all() {
    return new LayerStackIterator(0, layers.size());
  }

  public LayerStackIterator allLayers() {
    return new LayerStackIterator(0, currentLayer);
  }

  public LayerStackIterator allOverlays() {
    return new LayerStackIterator(currentLayer, layers.size());
  }

  public LayerStackReverseIterator reversed() {
    return new LayerStackReverseIterator();
  }

  class LayerStackIterator implements Iterable<Layer>, Iterator<Layer> {

    private int current;
    private final int limit;

    LayerStackIterator(int start, int end) {
      current = start;
      limit = end;
    }

    @Override
    public LayerStackIterator iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      return current < limit;
    }

    @Override
    public Layer next() {
      return layers.get(current++);
    }
  }

  class LayerStackReverseIterator implements Iterable<Layer>, Iterator<Layer> {

    int current = layers.size() - 1;

    @Override
    public Iterator<Layer> iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      return current >= 0;
    }

    @Override
    public Layer next() {
      return layers.get(current--);
    }
  }
}
