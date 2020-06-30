package uk.ashleybye.avalon.renderer;

public interface Texture {

  int getWidth();

  int getHeight();

  void bind(int slot);

  void dispose();
}
