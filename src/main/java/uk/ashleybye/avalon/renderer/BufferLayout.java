package uk.ashleybye.avalon.renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BufferLayout implements Iterable<BufferElement> {

  private final int stride;
  private final List<BufferElement> elements;

  private BufferLayout(int stride, List<BufferElement> elements) {
    this.stride = stride;
    this.elements = elements;
  }

  public static BufferLayoutBuilder builder() {
    return new BufferLayoutBuilder();
  }

  public int getStride() {
    return stride;
  }

  @Override
  public Iterator<BufferElement> iterator() {
    return elements.iterator();
  }

  public static class BufferLayoutBuilder {

    int totalSize;
    List<BufferElement> elements;

    private BufferLayoutBuilder() {
      totalSize = 0;
      elements = new ArrayList<>();
    }

    public BufferLayout build() {
      if (elements.size() == 0) {
        throw new IllegalStateException();
      }

      return new BufferLayout(totalSize, elements);
    }

    public BufferLayoutBuilder addElement(ShaderDataType type, String name) {
      elements.add(new BufferElement(name, type, type.getSize(), totalSize, false));

      totalSize += type.getSize();

      return this;
    }
  }
}
