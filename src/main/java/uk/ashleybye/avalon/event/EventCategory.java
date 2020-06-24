package uk.ashleybye.avalon.event;

import uk.ashleybye.avalon.Bit;

public class EventCategory {

  public static final int NONE = 0;
  public static final int APPLICATION = Bit.bit(0);
  public static final int INPUT = Bit.bit(1);
  public static final int KEYBOARD = Bit.bit(2);
  public static final int MOUSE = Bit.bit(3);
  public static final int MOUSE_BUTTON = Bit.bit(4);
}
