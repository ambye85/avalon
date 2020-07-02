package uk.ashleybye.avalon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOUtils {

  public static ByteBuffer load(String path) throws IOException {
    return loadData(fromURL(path));
  }

  private static ByteBuffer loadData(URL url) throws IOException {
    File file = new File(url.getFile());
    try (FileInputStream fis = new FileInputStream(file); FileChannel fc = fis.getChannel()) {
      return fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    }
  }

  private static URL fromURL(String path) throws IOException {
    URL url = Thread.currentThread().getContextClassLoader().getResource(path);
    if (url == null) {
      throw new IOException("Classpath resource not found: " + path);
    }
    return url;
  }
}
