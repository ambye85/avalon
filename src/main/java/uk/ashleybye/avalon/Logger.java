package uk.ashleybye.avalon;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.PrintStream;
import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class Logger {

  private final String name;
  private final Clock clock;
  private final PrintStream out;
  private final Ansi.Color color;

  private Logger(String name, Color color, Clock clock, PrintStream out) {
    this.name = name;
    this.color = switch (color) {
      case GREEN:
        yield Ansi.Color.GREEN;
      case CYAN:
        yield Ansi.Color.CYAN;
    };
    this.clock = clock;
    this.out = out;

    AnsiConsole.systemInstall();
  }

  public static LoggerBuilder builder(String name, Color color) {
    return new LoggerBuilder(name, color);
  }

  public void log(String message) {
    var t = ansi().fg(color).a("[%s] %s: %s"
        .formatted(LocalTime.now(clock).format(DateTimeFormatter.ISO_TIME), name, message)).reset();
    out.println(t);
  }

  public enum Color {
    GREEN,
    CYAN,
  }

  public static class LoggerBuilder {

    private final String name;
    private final Color color;
    private PrintStream out = System.out;
    private Clock clock = Clock.systemUTC();

    private LoggerBuilder(String name, Color color) {
      this.name = name;
      this.color = color;
    }

    public LoggerBuilder outputTo(PrintStream out) {
      this.out = out;
      return this;
    }

    public LoggerBuilder withClock(Clock clock) {
      this.clock = clock;
      return this;
    }

    public Logger build() {
      return new Logger(name, color, clock, out);
    }
  }
}
