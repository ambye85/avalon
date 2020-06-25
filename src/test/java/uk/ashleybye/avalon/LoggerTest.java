package uk.ashleybye.avalon;

import static com.google.common.truth.Truth.assertThat;
import static uk.ashleybye.avalon.Logger.Color.CYAN;
import static uk.ashleybye.avalon.Logger.Color.GREEN;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

public class LoggerTest {

  public static void main(String[] args) {
    var l = Logger.builder("ASH", CYAN).build();
    l.log("Hey hey!");
  }

  @Test
  void logsInfoMessage() {
    var logContent = new ByteArrayOutputStream();
    Clock clock = Clock.fixed(Instant.parse("2020-05-13T16:37:08.00Z"),
        ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(0)));
    var logger = Logger.builder("NAME", GREEN)
        .withClock(clock)
        .outputTo(new PrintStream(logContent))
        .build();

    logger.log("log message");

    assertThat(logContent.toString()).contains("[16:37:08] NAME: log message");
  }
}
