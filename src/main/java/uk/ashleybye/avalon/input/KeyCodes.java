package uk.ashleybye.avalon.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSLASH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_CAPS_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_EQUAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F10;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F12;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F13;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F14;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F15;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F16;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F17;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F18;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F19;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F20;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F21;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F22;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F23;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F24;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F25;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DIVIDE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_EQUAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_MULTIPLY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_MENU;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_MINUS;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_NUM_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAUSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PRINT_SCREEN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SCROLL_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SEMICOLON;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SLASH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_WORLD_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_WORLD_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

public class KeyCodes {

  /**
   * The unknown key.
   */
  public static final int AVALON_KEY_UNKNOWN = GLFW_KEY_UNKNOWN;

  /**
   * Printable keys.
   */
  public static final int
      AVALON_KEY_SPACE = GLFW_KEY_SPACE,
      AVALON_KEY_APOSTROPHE = GLFW_KEY_APOSTROPHE,
      AVALON_KEY_COMMA = GLFW_KEY_COMMA,
      AVALON_KEY_MINUS = GLFW_KEY_MINUS,
      AVALON_KEY_PERIOD = GLFW_KEY_PERIOD,
      AVALON_KEY_SLASH = GLFW_KEY_SLASH,
      AVALON_KEY_0 = GLFW_KEY_0,
      AVALON_KEY_1 = GLFW_KEY_1,
      AVALON_KEY_2 = GLFW_KEY_2,
      AVALON_KEY_3 = GLFW_KEY_3,
      AVALON_KEY_4 = GLFW_KEY_4,
      AVALON_KEY_5 = GLFW_KEY_5,
      AVALON_KEY_6 = GLFW_KEY_6,
      AVALON_KEY_7 = GLFW_KEY_7,
      AVALON_KEY_8 = GLFW_KEY_8,
      AVALON_KEY_9 = GLFW_KEY_9,
      AVALON_KEY_SEMICOLON = GLFW_KEY_SEMICOLON,
      AVALON_KEY_EQUAL = GLFW_KEY_EQUAL,
      AVALON_KEY_A = GLFW_KEY_A,
      AVALON_KEY_B = GLFW_KEY_B,
      AVALON_KEY_C = GLFW_KEY_C,
      AVALON_KEY_D = GLFW_KEY_D,
      AVALON_KEY_E = GLFW_KEY_E,
      AVALON_KEY_F = GLFW_KEY_F,
      AVALON_KEY_G = GLFW_KEY_G,
      AVALON_KEY_H = GLFW_KEY_H,
      AVALON_KEY_I = GLFW_KEY_I,
      AVALON_KEY_J = GLFW_KEY_J,
      AVALON_KEY_K = GLFW_KEY_K,
      AVALON_KEY_L = GLFW_KEY_L,
      AVALON_KEY_M = GLFW_KEY_M,
      AVALON_KEY_N = GLFW_KEY_N,
      AVALON_KEY_O = GLFW_KEY_O,
      AVALON_KEY_P = GLFW_KEY_P,
      AVALON_KEY_Q = GLFW_KEY_Q,
      AVALON_KEY_R = GLFW_KEY_R,
      AVALON_KEY_S = GLFW_KEY_S,
      AVALON_KEY_T = GLFW_KEY_T,
      AVALON_KEY_U = GLFW_KEY_U,
      AVALON_KEY_V = GLFW_KEY_V,
      AVALON_KEY_W = GLFW_KEY_W,
      AVALON_KEY_X = GLFW_KEY_X,
      AVALON_KEY_Y = GLFW_KEY_Y,
      AVALON_KEY_Z = GLFW_KEY_Z,
      AVALON_KEY_LEFT_BRACKET = GLFW_KEY_LEFT_BRACKET,
      AVALON_KEY_BACKSLASH = GLFW_KEY_BACKSLASH,
      AVALON_KEY_RIGHT_BRACKET = GLFW_KEY_RIGHT_BRACKET,
      AVALON_KEY_GRAVE_ACCENT = GLFW_KEY_GRAVE_ACCENT,
      AVALON_KEY_WORLD_1 = GLFW_KEY_WORLD_1,
      AVALON_KEY_WORLD_2 = GLFW_KEY_WORLD_2;

  /**
   * Function keys.
   */
  public static final int
      AVALON_KEY_ESCAPE = GLFW_KEY_ESCAPE,
      AVALON_KEY_ENTER = GLFW_KEY_ENTER,
      AVALON_KEY_TAB = GLFW_KEY_TAB,
      AVALON_KEY_BACKSPACE = GLFW_KEY_BACKSPACE,
      AVALON_KEY_INSERT = GLFW_KEY_INSERT,
      AVALON_KEY_DELETE = GLFW_KEY_DELETE,
      AVALON_KEY_RIGHT = GLFW_KEY_RIGHT,
      AVALON_KEY_LEFT = GLFW_KEY_LEFT,
      AVALON_KEY_DOWN = GLFW_KEY_DOWN,
      AVALON_KEY_UP = GLFW_KEY_UP,
      AVALON_KEY_PAGE_UP = GLFW_KEY_PAGE_UP,
      AVALON_KEY_PAGE_DOWN = GLFW_KEY_PAGE_DOWN,
      AVALON_KEY_HOME = GLFW_KEY_HOME,
      AVALON_KEY_END = GLFW_KEY_END,
      AVALON_KEY_CAPS_LOCK = GLFW_KEY_CAPS_LOCK,
      AVALON_KEY_SCROLL_LOCK = GLFW_KEY_SCROLL_LOCK,
      AVALON_KEY_NUM_LOCK = GLFW_KEY_NUM_LOCK,
      AVALON_KEY_PRINT_SCREEN = GLFW_KEY_PRINT_SCREEN,
      AVALON_KEY_PAUSE = GLFW_KEY_PAUSE,
      AVALON_KEY_F1 = GLFW_KEY_F1,
      AVALON_KEY_F2 = GLFW_KEY_F2,
      AVALON_KEY_F3 = GLFW_KEY_F3,
      AVALON_KEY_F4 = GLFW_KEY_F4,
      AVALON_KEY_F5 = GLFW_KEY_F5,
      AVALON_KEY_F6 = GLFW_KEY_F6,
      AVALON_KEY_F7 = GLFW_KEY_F7,
      AVALON_KEY_F8 = GLFW_KEY_F8,
      AVALON_KEY_F9 = GLFW_KEY_F9,
      AVALON_KEY_F10 = GLFW_KEY_F10,
      AVALON_KEY_F11 = GLFW_KEY_F11,
      AVALON_KEY_F12 = GLFW_KEY_F12,
      AVALON_KEY_F13 = GLFW_KEY_F13,
      AVALON_KEY_F14 = GLFW_KEY_F14,
      AVALON_KEY_F15 = GLFW_KEY_F15,
      AVALON_KEY_F16 = GLFW_KEY_F16,
      AVALON_KEY_F17 = GLFW_KEY_F17,
      AVALON_KEY_F18 = GLFW_KEY_F18,
      AVALON_KEY_F19 = GLFW_KEY_F19,
      AVALON_KEY_F20 = GLFW_KEY_F20,
      AVALON_KEY_F21 = GLFW_KEY_F21,
      AVALON_KEY_F22 = GLFW_KEY_F22,
      AVALON_KEY_F23 = GLFW_KEY_F23,
      AVALON_KEY_F24 = GLFW_KEY_F24,
      AVALON_KEY_F25 = GLFW_KEY_F25,
      AVALON_KEY_KP_0 = GLFW_KEY_KP_0,
      AVALON_KEY_KP_1 = GLFW_KEY_KP_1,
      AVALON_KEY_KP_2 = GLFW_KEY_KP_2,
      AVALON_KEY_KP_3 = GLFW_KEY_KP_3,
      AVALON_KEY_KP_4 = GLFW_KEY_KP_4,
      AVALON_KEY_KP_5 = GLFW_KEY_KP_5,
      AVALON_KEY_KP_6 = GLFW_KEY_KP_6,
      AVALON_KEY_KP_7 = GLFW_KEY_KP_7,
      AVALON_KEY_KP_8 = GLFW_KEY_KP_8,
      AVALON_KEY_KP_9 = GLFW_KEY_KP_9,
      AVALON_KEY_KP_DECIMAL = GLFW_KEY_KP_DECIMAL,
      AVALON_KEY_KP_DIVIDE = GLFW_KEY_KP_DIVIDE,
      AVALON_KEY_KP_MULTIPLY = GLFW_KEY_KP_MULTIPLY,
      AVALON_KEY_KP_SUBTRACT = GLFW_KEY_KP_SUBTRACT,
      AVALON_KEY_KP_ADD = GLFW_KEY_KP_ADD,
      AVALON_KEY_KP_ENTER = GLFW_KEY_KP_ENTER,
      AVALON_KEY_KP_EQUAL = GLFW_KEY_KP_EQUAL,
      AVALON_KEY_LEFT_SHIFT = GLFW_KEY_LEFT_SHIFT,
      AVALON_KEY_LEFT_CONTROL = GLFW_KEY_LEFT_CONTROL,
      AVALON_KEY_LEFT_ALT = GLFW_KEY_LEFT_ALT,
      AVALON_KEY_LEFT_SUPER = GLFW_KEY_LEFT_SUPER,
      AVALON_KEY_RIGHT_SHIFT = GLFW_KEY_RIGHT_SHIFT,
      AVALON_KEY_RIGHT_CONTROL = GLFW_KEY_RIGHT_CONTROL,
      AVALON_KEY_RIGHT_ALT = GLFW_KEY_RIGHT_ALT,
      AVALON_KEY_RIGHT_SUPER = GLFW_KEY_RIGHT_SUPER,
      AVALON_KEY_MENU = GLFW_KEY_MENU,
      AVALON_KEY_LAST = AVALON_KEY_MENU;
}
