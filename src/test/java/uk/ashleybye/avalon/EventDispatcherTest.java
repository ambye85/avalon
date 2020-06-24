package uk.ashleybye.avalon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import uk.ashleybye.avalon.event.Event;
import uk.ashleybye.avalon.event.EventCategory;
import uk.ashleybye.avalon.event.EventDispatcher;
import uk.ashleybye.avalon.event.EventType;

public class EventDispatcherTest {

  @Test
  void dispatcherDoesNotHandleEvent() {
    var spy = new CallbackSpy();
    var event = new UnhandledEvent();
    var dispatcher = new EventDispatcher(event);

    dispatcher.dispatch(EventStub.class, spy::someCallback);

    assertThat(spy.eventValue).matches("Default Value");
    assertThat(event.isHandled()).isFalse();
  }

  @Test
  void dispatcherHandlesEvent() {
    var spy = new CallbackSpy();
    var event = new EventStub();
    var dispatcher = new EventDispatcher(event);

    dispatcher.dispatch(EventStub.class, spy::someCallback);

    assertThat(spy.eventValue).matches("Event Stub");
    assertThat(event.isHandled()).isTrue();
  }
}

class UnhandledEvent extends Event {

  protected UnhandledEvent() {
    super(EventType.NONE, EventCategory.NONE);
  }

  @Override
  public String toString() {
    return null;
  }
}

class EventStub extends Event {

  public String value = "Event Stub";

  protected EventStub() {
    super(EventType.NONE, EventCategory.NONE);
  }

  @Override
  public String toString() {
    return null;
  }
}

class CallbackSpy {

  public String eventValue = "Default Value";

  public boolean someCallback(Event e) {
    var event = (EventStub) e;
    eventValue = event.value;
    return true;
  }
}
