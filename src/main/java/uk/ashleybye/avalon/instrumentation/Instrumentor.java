package uk.ashleybye.avalon.instrumentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class Instrumentor {

  private static boolean enabled = false;
  private static Instrumentor instance;
  private InstrumentationSession currentSession;
  private int profileCount;
  private FileWriter results;
  private final Instant start;

  private Instrumentor() {
    profileCount = 0;
    start = Instant.now(Clock.systemUTC());
  }

  public static void setEnabled(boolean enabled) {
    Instrumentor.enabled = enabled;
  }

  public static Instrumentor get() {
    if (instance == null) {
      if (enabled) {
        instance = new Instrumentor();
      } else {
        instance = new NullInstrumentor();
      }
    }
    return instance;
  }

  public void beginSession(String name, String path) {
    currentSession = new InstrumentationSession(name);
    try {
      var parent = Path.of(path).getParent();
      if (parent != null) {
        new File(parent.toString()).mkdirs();
      }
      results = new FileWriter(path);
      writeHeader();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void writeHeader() throws IOException {
    results.write("{\"otherData\":{},\"traceEvents\":[");
    results.flush();
  }

  void writeProfile(InstrumentationResult result) {
    try {
      if (profileCount++ > 0) {
        results.write(",");
      }

      var startTimeMs = Duration.between(start, result.start()).toNanos() * 0.00001;
      var durationMs = Duration.between(result.start(), result.end()).toNanos() * 0.00001;
      results.write("{");
      results.write("\"cat\":\"function\",");
      results.write(String.format("\"dur\":%.3f,", durationMs));
      results.write(String.format("\"name\":\"%s\",", result.name()));
      results.write("\"ph\":\"X\",");
      results.write("\"pid\":0,");
      results.write(String.format("\"tid\":%d,", result.threadId()));
      results.write(String.format("\"ts\":%.3f", startTimeMs));
      results.write("}");

      results.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void endSession() {
    try {
      writeFooter();
      results.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    currentSession = null;
    profileCount = 0;
  }

  private void writeFooter() throws IOException {
    results.write("]}");
    results.flush();
  }

  static class NullInstrumentor extends Instrumentor {

    @Override
    public void beginSession(String name, String path) {
      // Do nothing.
    }

    @Override
    void writeProfile(InstrumentationResult result) {
      // Do nothing.
    }

    @Override
    public void endSession() {
      // Do nothing.
    }
  }
}

record InstrumentationSession(String name) {

}
