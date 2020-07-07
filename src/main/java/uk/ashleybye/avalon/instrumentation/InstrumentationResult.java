package uk.ashleybye.avalon.instrumentation;

import java.time.Instant;

public record InstrumentationResult(String name, Instant start, Instant end, long threadId) {

}
