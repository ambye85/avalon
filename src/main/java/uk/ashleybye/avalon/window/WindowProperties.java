package uk.ashleybye.avalon.window;

import uk.ashleybye.avalon.event.EventCallback;

public record WindowProperties(
    String title,
    int width,
    int height,
    boolean vSync,
    EventCallback eventCallback
) {

}
