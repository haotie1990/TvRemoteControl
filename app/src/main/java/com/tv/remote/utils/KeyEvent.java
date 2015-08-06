package com.tv.remote.utils;

import android.text.method.MetaKeyKeyListener;
import android.util.SparseArray;

public class KeyEvent{
    /**
     * Key code constant: Unknown key code.
     */
    public static final int KEYCODE_UNKNOWN = 0;
    /**
     * Key code constant: Soft Left key.
     * Usually situated below the display on phones and used as a multi-function
     * feature key for selecting a software defined function shown on the bottom left
     * of the display.
     */
    public static final int KEYCODE_SOFT_LEFT = 1;
    /**
     * Key code constant: Soft Right key.
     * Usually situated below the display on phones and used as a multi-function
     * feature key for selecting a software defined function shown on the bottom right
     * of the display.
     */
    public static final int KEYCODE_SOFT_RIGHT = 2;
    /**
     * Key code constant: Home key.
     * This key is handled by the framework and is never delivered to applications.
     */
    public static final int KEYCODE_HOME = 3;
    /**
     * Key code constant: Back key.
     */
    public static final int KEYCODE_BACK = 4;
    /**
     * Key code constant: Call key.
     */
    public static final int KEYCODE_CALL = 5;
    /**
     * Key code constant: End Call key.
     */
    public static final int KEYCODE_ENDCALL = 6;
    /**
     * Key code constant: '0' key.
     */
    public static final int KEYCODE_0 = 7;
    /**
     * Key code constant: '1' key.
     */
    public static final int KEYCODE_1 = 8;
    /**
     * Key code constant: '2' key.
     */
    public static final int KEYCODE_2 = 9;
    /**
     * Key code constant: '3' key.
     */
    public static final int KEYCODE_3 = 10;
    /**
     * Key code constant: '4' key.
     */
    public static final int KEYCODE_4 = 11;
    /**
     * Key code constant: '5' key.
     */
    public static final int KEYCODE_5 = 12;
    /**
     * Key code constant: '6' key.
     */
    public static final int KEYCODE_6 = 13;
    /**
     * Key code constant: '7' key.
     */
    public static final int KEYCODE_7 = 14;
    /**
     * Key code constant: '8' key.
     */
    public static final int KEYCODE_8 = 15;
    /**
     * Key code constant: '9' key.
     */
    public static final int KEYCODE_9 = 16;
    /**
     * Key code constant: '*' key.
     */
    public static final int KEYCODE_STAR = 17;
    /**
     * Key code constant: '#' key.
     */
    public static final int KEYCODE_POUND = 18;
    /**
     * Key code constant: Directional Pad Up key.
     * May also be synthesized from trackball motions.
     */
    public static final int KEYCODE_DPAD_UP = 19;
    /**
     * Key code constant: Directional Pad Down key.
     * May also be synthesized from trackball motions.
     */
    public static final int KEYCODE_DPAD_DOWN = 20;
    /**
     * Key code constant: Directional Pad Left key.
     * May also be synthesized from trackball motions.
     */
    public static final int KEYCODE_DPAD_LEFT = 21;
    /**
     * Key code constant: Directional Pad Right key.
     * May also be synthesized from trackball motions.
     */
    public static final int KEYCODE_DPAD_RIGHT = 22;
    /**
     * Key code constant: Directional Pad Center key.
     * May also be synthesized from trackball motions.
     */
    public static final int KEYCODE_DPAD_CENTER = 23;
    /**
     * Key code constant: Volume Up key.
     * Adjusts the speaker volume up.
     */
    public static final int KEYCODE_VOLUME_UP = 24;
    /**
     * Key code constant: Volume Down key.
     * Adjusts the speaker volume down.
     */
    public static final int KEYCODE_VOLUME_DOWN = 25;
    /**
     * Key code constant: Power key.
     */
    public static final int KEYCODE_POWER = 26;
    /**
     * Key code constant: Camera key.
     * Used to launch a camera application or take pictures.
     */
    public static final int KEYCODE_CAMERA = 27;
    /**
     * Key code constant: Clear key.
     */
    public static final int KEYCODE_CLEAR = 28;
    /**
     * Key code constant: 'A' key.
     */
    public static final int KEYCODE_A = 29;
    /**
     * Key code constant: 'B' key.
     */
    public static final int KEYCODE_B = 30;
    /**
     * Key code constant: 'C' key.
     */
    public static final int KEYCODE_C = 31;
    /**
     * Key code constant: 'D' key.
     */
    public static final int KEYCODE_D = 32;
    /**
     * Key code constant: 'E' key.
     */
    public static final int KEYCODE_E = 33;
    /**
     * Key code constant: 'F' key.
     */
    public static final int KEYCODE_F = 34;
    /**
     * Key code constant: 'G' key.
     */
    public static final int KEYCODE_G = 35;
    /**
     * Key code constant: 'H' key.
     */
    public static final int KEYCODE_H = 36;
    /**
     * Key code constant: 'I' key.
     */
    public static final int KEYCODE_I = 37;
    /**
     * Key code constant: 'J' key.
     */
    public static final int KEYCODE_J = 38;
    /**
     * Key code constant: 'K' key.
     */
    public static final int KEYCODE_K = 39;
    /**
     * Key code constant: 'L' key.
     */
    public static final int KEYCODE_L = 40;
    /**
     * Key code constant: 'M' key.
     */
    public static final int KEYCODE_M = 41;
    /**
     * Key code constant: 'N' key.
     */
    public static final int KEYCODE_N = 42;
    /**
     * Key code constant: 'O' key.
     */
    public static final int KEYCODE_O = 43;
    /**
     * Key code constant: 'P' key.
     */
    public static final int KEYCODE_P = 44;
    /**
     * Key code constant: 'Q' key.
     */
    public static final int KEYCODE_Q = 45;
    /**
     * Key code constant: 'R' key.
     */
    public static final int KEYCODE_R = 46;
    /**
     * Key code constant: 'S' key.
     */
    public static final int KEYCODE_S = 47;
    /**
     * Key code constant: 'T' key.
     */
    public static final int KEYCODE_T = 48;
    /**
     * Key code constant: 'U' key.
     */
    public static final int KEYCODE_U = 49;
    /**
     * Key code constant: 'V' key.
     */
    public static final int KEYCODE_V = 50;
    /**
     * Key code constant: 'W' key.
     */
    public static final int KEYCODE_W = 51;
    /**
     * Key code constant: 'X' key.
     */
    public static final int KEYCODE_X = 52;
    /**
     * Key code constant: 'Y' key.
     */
    public static final int KEYCODE_Y = 53;
    /**
     * Key code constant: 'Z' key.
     */
    public static final int KEYCODE_Z = 54;
    /**
     * Key code constant: ',' key.
     */
    public static final int KEYCODE_COMMA = 55;
    /**
     * Key code constant: '.' key.
     */
    public static final int KEYCODE_PERIOD = 56;
    /**
     * Key code constant: Left Alt modifier key.
     */
    public static final int KEYCODE_ALT_LEFT = 57;
    /**
     * Key code constant: Right Alt modifier key.
     */
    public static final int KEYCODE_ALT_RIGHT = 58;
    /**
     * Key code constant: Left Shift modifier key.
     */
    public static final int KEYCODE_SHIFT_LEFT = 59;
    /**
     * Key code constant: Right Shift modifier key.
     */
    public static final int KEYCODE_SHIFT_RIGHT = 60;
    /**
     * Key code constant: Tab key.
     */
    public static final int KEYCODE_TAB = 61;
    /**
     * Key code constant: Space key.
     */
    public static final int KEYCODE_SPACE = 62;
    /**
     * Key code constant: Symbol modifier key.
     * Used to enter alternate symbols.
     */
    public static final int KEYCODE_SYM = 63;
    /**
     * Key code constant: Explorer special function key.
     * Used to launch a browser application.
     */
    public static final int KEYCODE_EXPLORER = 64;
    /**
     * Key code constant: Envelope special function key.
     * Used to launch a mail application.
     */
    public static final int KEYCODE_ENVELOPE = 65;
    /**
     * Key code constant: Enter key.
     */
    public static final int KEYCODE_ENTER = 66;
    /**
     * Key code constant: Backspace key.
     * Deletes characters before the insertion point, unlike {@link #KEYCODE_FORWARD_DEL}.
     */
    public static final int KEYCODE_DEL = 67;
    /**
     * Key code constant: '`' (backtick) key.
     */
    public static final int KEYCODE_GRAVE = 68;
    /**
     * Key code constant: '-'.
     */
    public static final int KEYCODE_MINUS = 69;
    /**
     * Key code constant: '=' key.
     */
    public static final int KEYCODE_EQUALS = 70;
    /**
     * Key code constant: '[' key.
     */
    public static final int KEYCODE_LEFT_BRACKET = 71;
    /**
     * Key code constant: ']' key.
     */
    public static final int KEYCODE_RIGHT_BRACKET = 72;
    /**
     * Key code constant: '\' key.
     */
    public static final int KEYCODE_BACKSLASH = 73;
    /**
     * Key code constant: ';' key.
     */
    public static final int KEYCODE_SEMICOLON = 74;
    /**
     * Key code constant: ''' (apostrophe) key.
     */
    public static final int KEYCODE_APOSTROPHE = 75;
    /**
     * Key code constant: '/' key.
     */
    public static final int KEYCODE_SLASH = 76;
    /**
     * Key code constant: '@' key.
     */
    public static final int KEYCODE_AT = 77;
    /**
     * Key code constant: Number modifier key.
     * Used to enter numeric symbols.
     * This key is not Num Lock; it is more like {@link #KEYCODE_ALT_LEFT} and is
     * interpreted as an ALT key by {@link MetaKeyKeyListener}.
     */
    public static final int KEYCODE_NUM = 78;
    /**
     * Key code constant: Headset Hook key.
     * Used to hang up calls and stop media.
     */
    public static final int KEYCODE_HEADSETHOOK = 79;
    /**
     * Key code constant: Camera Focus key.
     * Used to focus the camera.
     */
    public static final int KEYCODE_FOCUS = 80;   // *Camera* focus
    /**
     * Key code constant: '+' key.
     */
    public static final int KEYCODE_PLUS = 81;
    /**
     * Key code constant: Menu key.
     */
    public static final int KEYCODE_MENU = 82;
    /**
     * Key code constant: Notification key.
     */
    public static final int KEYCODE_NOTIFICATION = 83;
    /**
     * Key code constant: Search key.
     */
    public static final int KEYCODE_SEARCH = 84;
    /**
     * Key code constant: Play/Pause media key.
     */
    public static final int KEYCODE_MEDIA_PLAY_PAUSE = 85;
    /**
     * Key code constant: Stop media key.
     */
    public static final int KEYCODE_MEDIA_STOP = 86;
    /**
     * Key code constant: Play Next media key.
     */
    public static final int KEYCODE_MEDIA_NEXT = 87;
    /**
     * Key code constant: Play Previous media key.
     */
    public static final int KEYCODE_MEDIA_PREVIOUS = 88;
    /**
     * Key code constant: Rewind media key.
     */
    public static final int KEYCODE_MEDIA_REWIND = 89;
    /**
     * Key code constant: Fast Forward media key.
     */
    public static final int KEYCODE_MEDIA_FAST_FORWARD = 90;
    /**
     * Key code constant: Mute key.
     * Mutes the microphone, unlike {@link #KEYCODE_VOLUME_MUTE}.
     */
    public static final int KEYCODE_MUTE = 91;
    /**
     * Key code constant: Page Up key.
     */
    public static final int KEYCODE_PAGE_UP = 92;
    /**
     * Key code constant: Page Down key.
     */
    public static final int KEYCODE_PAGE_DOWN = 93;
    /**
     * Key code constant: Picture Symbols modifier key.
     * Used to switch symbol sets (Emoji, Kao-moji).
     */
    public static final int KEYCODE_PICTSYMBOLS = 94;   // switch symbol-sets (Emoji,Kao-moji)
    /**
     * Key code constant: Switch Charset modifier key.
     * Used to switch character sets (Kanji, Katakana).
     */
    public static final int KEYCODE_SWITCH_CHARSET = 95;   // switch char-sets (Kanji,Katakana)
    /**
     * Key code constant: A Button key.
     * On a game controller, the A button should be either the button labeled A
     * or the first button on the bottom row of controller buttons.
     */
    public static final int KEYCODE_BUTTON_A = 96;
    /**
     * Key code constant: B Button key.
     * On a game controller, the B button should be either the button labeled B
     * or the second button on the bottom row of controller buttons.
     */
    public static final int KEYCODE_BUTTON_B = 97;
    /**
     * Key code constant: C Button key.
     * On a game controller, the C button should be either the button labeled C
     * or the third button on the bottom row of controller buttons.
     */
    public static final int KEYCODE_BUTTON_C = 98;
    /**
     * Key code constant: X Button key.
     * On a game controller, the X button should be either the button labeled X
     * or the first button on the upper row of controller buttons.
     */
    public static final int KEYCODE_BUTTON_X = 99;
    /**
     * Key code constant: Y Button key.
     * On a game controller, the Y button should be either the button labeled Y
     * or the second button on the upper row of controller buttons.
     */
    public static final int KEYCODE_BUTTON_Y = 100;
    /**
     * Key code constant: Z Button key.
     * On a game controller, the Z button should be either the button labeled Z
     * or the third button on the upper row of controller buttons.
     */
    public static final int KEYCODE_BUTTON_Z = 101;
    /**
     * Key code constant: L1 Button key.
     * On a game controller, the L1 button should be either the button labeled L1 (or L)
     * or the top left trigger button.
     */
    public static final int KEYCODE_BUTTON_L1 = 102;
    /**
     * Key code constant: R1 Button key.
     * On a game controller, the R1 button should be either the button labeled R1 (or R)
     * or the top right trigger button.
     */
    public static final int KEYCODE_BUTTON_R1 = 103;
    /**
     * Key code constant: L2 Button key.
     * On a game controller, the L2 button should be either the button labeled L2
     * or the bottom left trigger button.
     */
    public static final int KEYCODE_BUTTON_L2 = 104;
    /**
     * Key code constant: R2 Button key.
     * On a game controller, the R2 button should be either the button labeled R2
     * or the bottom right trigger button.
     */
    public static final int KEYCODE_BUTTON_R2 = 105;
    /**
     * Key code constant: Left Thumb Button key.
     * On a game controller, the left thumb button indicates that the left (or only)
     * joystick is pressed.
     */
    public static final int KEYCODE_BUTTON_THUMBL = 106;
    /**
     * Key code constant: Right Thumb Button key.
     * On a game controller, the right thumb button indicates that the right
     * joystick is pressed.
     */
    public static final int KEYCODE_BUTTON_THUMBR = 107;
    /**
     * Key code constant: Start Button key.
     * On a game controller, the button labeled Start.
     */
    public static final int KEYCODE_BUTTON_START = 108;
    /**
     * Key code constant: Select Button key.
     * On a game controller, the button labeled Select.
     */
    public static final int KEYCODE_BUTTON_SELECT = 109;
    /**
     * Key code constant: Mode Button key.
     * On a game controller, the button labeled Mode.
     */
    public static final int KEYCODE_BUTTON_MODE = 110;
    /**
     * Key code constant: Escape key.
     */
    public static final int KEYCODE_ESCAPE = 111;
    /**
     * Key code constant: Forward Delete key.
     * Deletes characters ahead of the insertion point, unlike {@link #KEYCODE_DEL}.
     */
    public static final int KEYCODE_FORWARD_DEL = 112;
    /**
     * Key code constant: Left Control modifier key.
     */
    public static final int KEYCODE_CTRL_LEFT = 113;
    /**
     * Key code constant: Right Control modifier key.
     */
    public static final int KEYCODE_CTRL_RIGHT = 114;
    /**
     * Key code constant: Caps Lock key.
     */
    public static final int KEYCODE_CAPS_LOCK = 115;
    /**
     * Key code constant: Scroll Lock key.
     */
    public static final int KEYCODE_SCROLL_LOCK = 116;
    /**
     * Key code constant: Left Meta modifier key.
     */
    public static final int KEYCODE_META_LEFT = 117;
    /**
     * Key code constant: Right Meta modifier key.
     */
    public static final int KEYCODE_META_RIGHT = 118;
    /**
     * Key code constant: Function modifier key.
     */
    public static final int KEYCODE_FUNCTION = 119;
    /**
     * Key code constant: System Request / Print Screen key.
     */
    public static final int KEYCODE_SYSRQ = 120;
    /**
     * Key code constant: Break / Pause key.
     */
    public static final int KEYCODE_BREAK = 121;
    /**
     * Key code constant: Home Movement key.
     * Used for scrolling or moving the cursor around to the start of a line
     * or to the top of a list.
     */
    public static final int KEYCODE_MOVE_HOME = 122;
    /**
     * Key code constant: End Movement key.
     * Used for scrolling or moving the cursor around to the end of a line
     * or to the bottom of a list.
     */
    public static final int KEYCODE_MOVE_END = 123;
    /**
     * Key code constant: Insert key.
     * Toggles insert / overwrite edit mode.
     */
    public static final int KEYCODE_INSERT = 124;
    /**
     * Key code constant: Forward key.
     * Navigates forward in the history stack.  Complement of {@link #KEYCODE_BACK}.
     */
    public static final int KEYCODE_FORWARD = 125;
    /**
     * Key code constant: Play media key.
     */
    public static final int KEYCODE_MEDIA_PLAY = 126;
    /**
     * Key code constant: Pause media key.
     */
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    /**
     * Key code constant: Close media key.
     * May be used to close a CD tray, for example.
     */
    public static final int KEYCODE_MEDIA_CLOSE = 128;
    /**
     * Key code constant: Eject media key.
     * May be used to eject a CD tray, for example.
     */
    public static final int KEYCODE_MEDIA_EJECT = 129;
    /**
     * Key code constant: Record media key.
     */
    public static final int KEYCODE_MEDIA_RECORD = 130;
    /**
     * Key code constant: F1 key.
     */
    public static final int KEYCODE_F1 = 131;
    /**
     * Key code constant: F2 key.
     */
    public static final int KEYCODE_F2 = 132;
    /**
     * Key code constant: F3 key.
     */
    public static final int KEYCODE_F3 = 133;
    /**
     * Key code constant: F4 key.
     */
    public static final int KEYCODE_F4 = 134;
    /**
     * Key code constant: F5 key.
     */
    public static final int KEYCODE_F5 = 135;
    /**
     * Key code constant: F6 key.
     */
    public static final int KEYCODE_F6 = 136;
    /**
     * Key code constant: F7 key.
     */
    public static final int KEYCODE_F7 = 137;
    /**
     * Key code constant: F8 key.
     */
    public static final int KEYCODE_F8 = 138;
    /**
     * Key code constant: F9 key.
     */
    public static final int KEYCODE_F9 = 139;
    /**
     * Key code constant: F10 key.
     */
    public static final int KEYCODE_F10 = 140;
    /**
     * Key code constant: F11 key.
     */
    public static final int KEYCODE_F11 = 141;
    /**
     * Key code constant: F12 key.
     */
    public static final int KEYCODE_F12 = 142;
    /**
     * Key code constant: Num Lock key.
     * This is the Num Lock key; it is different from {@link #KEYCODE_NUM}.
     * This key alters the behavior of other keys on the numeric keypad.
     */
    public static final int KEYCODE_NUM_LOCK = 143;
    /**
     * Key code constant: Numeric keypad '0' key.
     */
    public static final int KEYCODE_NUMPAD_0 = 144;
    /**
     * Key code constant: Numeric keypad '1' key.
     */
    public static final int KEYCODE_NUMPAD_1 = 145;
    /**
     * Key code constant: Numeric keypad '2' key.
     */
    public static final int KEYCODE_NUMPAD_2 = 146;
    /**
     * Key code constant: Numeric keypad '3' key.
     */
    public static final int KEYCODE_NUMPAD_3 = 147;
    /**
     * Key code constant: Numeric keypad '4' key.
     */
    public static final int KEYCODE_NUMPAD_4 = 148;
    /**
     * Key code constant: Numeric keypad '5' key.
     */
    public static final int KEYCODE_NUMPAD_5 = 149;
    /**
     * Key code constant: Numeric keypad '6' key.
     */
    public static final int KEYCODE_NUMPAD_6 = 150;
    /**
     * Key code constant: Numeric keypad '7' key.
     */
    public static final int KEYCODE_NUMPAD_7 = 151;
    /**
     * Key code constant: Numeric keypad '8' key.
     */
    public static final int KEYCODE_NUMPAD_8 = 152;
    /**
     * Key code constant: Numeric keypad '9' key.
     */
    public static final int KEYCODE_NUMPAD_9 = 153;
    /**
     * Key code constant: Numeric keypad '/' key (for division).
     */
    public static final int KEYCODE_NUMPAD_DIVIDE = 154;
    /**
     * Key code constant: Numeric keypad '*' key (for multiplication).
     */
    public static final int KEYCODE_NUMPAD_MULTIPLY = 155;
    /**
     * Key code constant: Numeric keypad '-' key (for subtraction).
     */
    public static final int KEYCODE_NUMPAD_SUBTRACT = 156;
    /**
     * Key code constant: Numeric keypad '+' key (for addition).
     */
    public static final int KEYCODE_NUMPAD_ADD = 157;
    /**
     * Key code constant: Numeric keypad '.' key (for decimals or digit grouping).
     */
    public static final int KEYCODE_NUMPAD_DOT = 158;
    /**
     * Key code constant: Numeric keypad ',' key (for decimals or digit grouping).
     */
    public static final int KEYCODE_NUMPAD_COMMA = 159;
    /**
     * Key code constant: Numeric keypad Enter key.
     */
    public static final int KEYCODE_NUMPAD_ENTER = 160;
    /**
     * Key code constant: Numeric keypad '=' key.
     */
    public static final int KEYCODE_NUMPAD_EQUALS = 161;
    /**
     * Key code constant: Numeric keypad '(' key.
     */
    public static final int KEYCODE_NUMPAD_LEFT_PAREN = 162;
    /**
     * Key code constant: Numeric keypad ')' key.
     */
    public static final int KEYCODE_NUMPAD_RIGHT_PAREN = 163;
    /**
     * Key code constant: Volume Mute key.
     * Mutes the speaker, unlike {@link #KEYCODE_MUTE}.
     * This key should normally be implemented as a toggle such that the first press
     * mutes the speaker and the second press restores the original volume.
     */
    public static final int KEYCODE_VOLUME_MUTE = 164;
    /**
     * Key code constant: Info key.
     * Common on TV remotes to show additional information related to what is
     * currently being viewed.
     */
    public static final int KEYCODE_INFO = 165;
    /**
     * Key code constant: Channel up key.
     * On TV remotes, increments the television channel.
     */
    public static final int KEYCODE_CHANNEL_UP = 166;
    /**
     * Key code constant: Channel down key.
     * On TV remotes, decrements the television channel.
     */
    public static final int KEYCODE_CHANNEL_DOWN = 167;
    /**
     * Key code constant: Zoom in key.
     */
    public static final int KEYCODE_ZOOM_IN = 168;
    /**
     * Key code constant: Zoom out key.
     */
    public static final int KEYCODE_ZOOM_OUT = 169;
    /**
     * Key code constant: TV key.
     * On TV remotes, switches to viewing live TV.
     */
    public static final int KEYCODE_TV = 170;
    /**
     * Key code constant: Window key.
     * On TV remotes, toggles picture-in-picture mode or other windowing functions.
     */
    public static final int KEYCODE_WINDOW = 171;
    /**
     * Key code constant: Guide key.
     * On TV remotes, shows a programming guide.
     */
    public static final int KEYCODE_GUIDE = 172;
    /**
     * Key code constant: DVR key.
     * On some TV remotes, switches to a DVR mode for recorded shows.
     */
    public static final int KEYCODE_DVR = 173;
    /**
     * Key code constant: Bookmark key.
     * On some TV remotes, bookmarks content or web pages.
     */
    public static final int KEYCODE_BOOKMARK = 174;
    /**
     * Key code constant: Toggle captions key.
     * Switches the mode for closed-captioning text, for example during television shows.
     */
    public static final int KEYCODE_CAPTIONS = 175;
    /**
     * Key code constant: Settings key.
     * Starts the system settings activity.
     */
    public static final int KEYCODE_SETTINGS = 176;
    /**
     * Key code constant: TV power key.
     * On TV remotes, toggles the power on a television screen.
     */
    public static final int KEYCODE_TV_POWER = 177;
    /**
     * Key code constant: TV input key.
     * On TV remotes, switches the input on a television screen.
     */
    public static final int KEYCODE_TV_INPUT = 178;
    /**
     * Key code constant: Set-top-box power key.
     * On TV remotes, toggles the power on an external Set-top-box.
     */
    public static final int KEYCODE_STB_POWER = 179;
    /**
     * Key code constant: Set-top-box input key.
     * On TV remotes, switches the input mode on an external Set-top-box.
     */
    public static final int KEYCODE_STB_INPUT = 180;
    /**
     * Key code constant: A/V Receiver power key.
     * On TV remotes, toggles the power on an external A/V Receiver.
     */
    public static final int KEYCODE_AVR_POWER = 181;
    /**
     * Key code constant: A/V Receiver input key.
     * On TV remotes, switches the input mode on an external A/V Receiver.
     */
    public static final int KEYCODE_AVR_INPUT = 182;
    /**
     * Key code constant: Red "programmable" key.
     * On TV remotes, acts as a contextual/programmable key.
     */
    public static final int KEYCODE_PROG_RED = 183;
    /**
     * Key code constant: Green "programmable" key.
     * On TV remotes, actsas a contextual/programmable key.
     */
    public static final int KEYCODE_PROG_GREEN = 184;
    /**
     * Key code constant: Yellow "programmable" key.
     * On TV remotes, acts as a contextual/programmable key.
     */
    public static final int KEYCODE_PROG_YELLOW = 185;
    /**
     * Key code constant: Blue "programmable" key.
     * On TV remotes, acts as a contextual/programmable key.
     */
    public static final int KEYCODE_PROG_BLUE = 186;
    /**
     * Key code constant: App switch key.
     * Should bring up the application switcher dialog.
     */
    public static final int KEYCODE_APP_SWITCH = 187;
    /**
     * Key code constant: Generic Game Pad Button #1.
     */
    public static final int KEYCODE_BUTTON_1 = 188;
    /**
     * Key code constant: Generic Game Pad Button #2.
     */
    public static final int KEYCODE_BUTTON_2 = 189;
    /**
     * Key code constant: Generic Game Pad Button #3.
     */
    public static final int KEYCODE_BUTTON_3 = 190;
    /**
     * Key code constant: Generic Game Pad Button #4.
     */
    public static final int KEYCODE_BUTTON_4 = 191;
    /**
     * Key code constant: Generic Game Pad Button #5.
     */
    public static final int KEYCODE_BUTTON_5 = 192;
    /**
     * Key code constant: Generic Game Pad Button #6.
     */
    public static final int KEYCODE_BUTTON_6 = 193;
    /**
     * Key code constant: Generic Game Pad Button #7.
     */
    public static final int KEYCODE_BUTTON_7 = 194;
    /**
     * Key code constant: Generic Game Pad Button #8.
     */
    public static final int KEYCODE_BUTTON_8 = 195;
    /**
     * Key code constant: Generic Game Pad Button #9.
     */
    public static final int KEYCODE_BUTTON_9 = 196;
    /**
     * Key code constant: Generic Game Pad Button #10.
     */
    public static final int KEYCODE_BUTTON_10 = 197;
    /**
     * Key code constant: Generic Game Pad Button #11.
     */
    public static final int KEYCODE_BUTTON_11 = 198;
    /**
     * Key code constant: Generic Game Pad Button #12.
     */
    public static final int KEYCODE_BUTTON_12 = 199;
    /**
     * Key code constant: Generic Game Pad Button #13.
     */
    public static final int KEYCODE_BUTTON_13 = 200;
    /**
     * Key code constant: Generic Game Pad Button #14.
     */
    public static final int KEYCODE_BUTTON_14 = 201;
    /**
     * Key code constant: Generic Game Pad Button #15.
     */
    public static final int KEYCODE_BUTTON_15 = 202;
    /**
     * Key code constant: Generic Game Pad Button #16.
     */
    public static final int KEYCODE_BUTTON_16 = 203;
    /**
     * Key code constant: Language Switch key.
     * Toggles the current input language such as switching between English and Japanese on
     * a QWERTY keyboard.  On some devices, the same function may be performed by
     * pressing Shift+Spacebar.
     */
    public static final int KEYCODE_LANGUAGE_SWITCH = 204;
    /**
     * Key code constant: Manner Mode key.
     * Toggles silent or vibrate mode on and off to make the device behave more politely
     * in certain settings such as on a crowded train.  On some devices, the key may only
     * operate when long-pressed.
     */
    public static final int KEYCODE_MANNER_MODE = 205;
    /**
     * Key code constant: 3D Mode key.
     * Toggles the display between 2D and 3D mode.
     */
    public static final int KEYCODE_3D_MODE = 206;
    /**
     * Key code constant: Contacts special function key.
     * Used to launch an address book application.
     */
    public static final int KEYCODE_CONTACTS = 207;
    /**
     * Key code constant: Calendar special function key.
     * Used to launch a calendar application.
     */
    public static final int KEYCODE_CALENDAR = 208;
    /**
     * Key code constant: Music special function key.
     * Used to launch a music player application.
     */
    public static final int KEYCODE_MUSIC = 209;
    /**
     * Key code constant: Calculator special function key.
     * Used to launch a calculator application.
     */
    public static final int KEYCODE_CALCULATOR = 210;
    /**
     * Key code constant: Japanese full-width / half-width key.
     */
    public static final int KEYCODE_ZENKAKU_HANKAKU = 211;
    /**
     * Key code constant: Japanese alphanumeric key.
     */
    public static final int KEYCODE_EISU = 212;
    /**
     * Key code constant: Japanese non-conversion key.
     */
    public static final int KEYCODE_MUHENKAN = 213;
    /**
     * Key code constant: Japanese conversion key.
     */
    public static final int KEYCODE_HENKAN = 214;
    /**
     * Key code constant: Japanese katakana / hiragana key.
     */
    public static final int KEYCODE_KATAKANA_HIRAGANA = 215;
    /**
     * Key code constant: Japanese Yen key.
     */
    public static final int KEYCODE_YEN = 216;
    /**
     * Key code constant: Japanese Ro key.
     */
    public static final int KEYCODE_RO = 217;
    /**
     * Key code constant: Japanese kana key.
     */
    public static final int KEYCODE_KANA = 218;
    /**
     * Key code constant: Assist key.
     * Launches the global assist activity.  Not delivered to applications.
     */
    public static final int KEYCODE_ASSIST = 219;
    /**
     * Key code constant: Brightness Down key.
     * Adjusts the screen brightness down.
     */
    public static final int KEYCODE_BRIGHTNESS_DOWN = 220;
    /**
     * Key code constant: Brightness Up key.
     * Adjusts the screen brightness up.
     */
    public static final int KEYCODE_BRIGHTNESS_UP = 221;
    /**
     * Key code constant: Audio Track key
     * Switches the audio tracks.
     */
    public static final int KEYCODE_MEDIA_AUDIO_TRACK = 222;

    /**
     * Key code constant: Skyworth customer key.
     * //----range 226-249------!!Only for Skyworth!!
     * On TV remotes, reserve 25 keys for customer.
     */
    public static final int KEYCODE_ALTERNATE = 226;
    public static final int KEYCODE_VOICE = 227;
    public static final int KEYCODE_SHARE = 228;
    public static final int KEYCODE_ENTER_EPG = 229;
    public static final int KEYCODE_IMAGE_MODE = 230;
    public static final int KEYCODE_SOUND_MODE = 231;
    public static final int KEYCODE_DISPLAY_MODE = 232;

    public static final int KEYCODE_MEDIA_SONG_SYSTEM = 234;
    public static final int KEYCODE_MEDIA_RELATIONSHIP = 235;
    public static final int KEYCODE_MEDIA_BOOKING = 236;
    public static final int KEYCODE_MEDIA_FAVORITES = 237;
    public static final int KEYCODE_MEDIA_AUDIO_CONTROL = 238;
    public static final int KEYCODE_MEDIA_FUNCTION = 239;
    public static final int KEYCODE_MEDIA_ORIGINAL_SOUNDTRACK = 240;
    public static final int KEYCODE_MEDIA_SELECTED_SONGS = 241;
    public static final int KEYCODE_MEDIA_PRIORITY = 242;
    public static final int KEYCODE_MEDIA_DELETE = 243;
    public static final int KEYCODE_MEDIA_SCORE_DISPLAY = 244;

    // skyworth section, range 700-800   naming rule: KEYCODE_XXX
    public static final int KEYCODE_FACTORY_FACTORY_MODE = 700;
    public static final int KEYCODE_FACTORY_RESET = 701;
    public static final int KEYCODE_FACTORY_SOURCE_ADD = 702;
    public static final int KEYCODE_FACTORY_SOURCE_REDUCE = 703;
    public static final int KEYCODE_FACTORY_OUTSET = 704;
    public static final int KEYCODE_FACTORY_BUS_OFF = 705;
    public static final int KEYCODE_FACTORY_AGING_MODE = 706;
    public static final int KEYCODE_FACTORY_AUTO_ADC = 707;
    public static final int KEYCODE_FACTORY_AV1 = 708;
    public static final int KEYCODE_FACTORY_RF_AGC = 709;
    public static final int KEYCODE_FACTORY_AV2 = 710;
    public static final int KEYCODE_FACTORY_AV3 = 711;
    public static final int KEYCODE_FACTORY_S1 = 712;
    public static final int KEYCODE_FACTORY_YUV1 = 713;
    public static final int KEYCODE_FACTORY_YUV2 = 714;
    public static final int KEYCODE_FACTORY_VGA = 715;
    public static final int KEYCODE_FACTORY_HDMI1 = 716;
    public static final int KEYCODE_FACTORY_HDMI2 = 717;
    public static final int KEYCODE_FACTORY_HDMI3 = 718;
    public static final int KEYCODE_FACTORY_KALA_OK = 719;
    public static final int KEYCODE_FACTORY_UPLAYER = 720;
    public static final int KEYCODE_FACTORY_LAN = 721;
    public static final int KEYCODE_FACTORY_DREAM_PANEL = 722;
    public static final int KEYCODE_FACTORY_WHITE_BALANCE = 723;
    public static final int KEYCODE_FACTORY_ALONE_LISTEN = 724;
    public static final int KEYCODE_FACTORY_CA_CARD = 725;
    public static final int KEYCODE_FACTORY_BARCODE = 726;
    public static final int KEYCODE_FACTORY_SEARCH_UP = 727;
    public static final int KEYCODE_FACTORY_SEARCH_DOWN = 728;
    public static final int KEYCODE_SHUTTLE_LEFT_SPEED_1 = 732;
    public static final int KEYCODE_SHUTTLE_LEFT_SPEED_2 = 733;
    public static final int KEYCODE_SHUTTLE_LEFT_SPEED_3 = 734;
    public static final int KEYCODE_SHUTTLE_LEFT_SPEED_4 = 735;
    public static final int KEYCODE_SHUTTLE_LEFT_SPEED_5 = 736;
    public static final int KEYCODE_SHUTTLE_LEFT_SPEED_6 = 737;
    public static final int KEYCODE_SHUTTLE_RIGHT_SPEED_1 = 738;
    public static final int KEYCODE_SHUTTLE_RIGHT_SPEED_2 = 739;
    public static final int KEYCODE_SHUTTLE_RIGHT_SPEED_3 = 740;
    public static final int KEYCODE_SHUTTLE_RIGHT_SPEED_4 = 741;
    public static final int KEYCODE_SHUTTLE_RIGHT_SPEED_5 = 742;
    public static final int KEYCODE_SHUTTLE_RIGHT_SPEED_6 = 743;
    public static final int KEYCODE_F13 = 744;
    public static final int KEYCODE_F14 = 745;
    public static final int KEYCODE_F15 = 746;
    public static final int KEYCODE_F16 = 747;
    public static final int KEYCODE_F17 = 748;
    public static final int KEYCODE_F18 = 749;
    public static final int KEYCODE_F19 = 750;
    public static final int KEYCODE_F20 = 751;
    public static final int KEYCODE_F21 = 752;
    public static final int KEYCODE_F22 = 753;
    public static final int KEYCODE_F23 = 754;
    public static final int KEYCODE_F24 = 755;
    public static final int KEYCODE_TIME_SPOT = 756;
    public static final int KEYCODE_USER_SWITCH = 757;
    public static final int KEYCODE_VOICE_END = 758;
    public static final int KEYCODE_POWER_LONG = 759;
    // add for skyworth
    public static final int KEYCODE_COOCAA = 760;
    public static final int KEYCODE_HOME_LONG = 761;
    public static final int KEYCODE_BACK_LONG = 762;
    public static final int KEYCODE_CENTER_LONG = 763;
    public static final int KEYCODE_NEW_SHUTTLE_LEFT = 764;
    public static final int KEYCODE_NEW_SHUTTLE_RIGHT = 765;
    public static final int KEYCODE_SEEK_SIGNAL = 766;
    public static final int KEYCODE_CHILD_LOCK = 256;
    public static final int KEYCODE_LOCAL_MEDIA = 767;
    public static final int KEYCODE_RESEARCH = 768;
    public static final int KEYCODE_SETTING = 769;
    public static final int KEYCODE_MYSTEP = 770;
    public static final int KEYCODE_SOUNDBAR_MODE = 771;
    public static final int KEYCODE_TV_MODE = 772;
    //yiye add4 no
    public static final int KEYCODE_SOUNDBAR_MODE_PROGRAM_DOWN = 773;
    public static final int KEYCODE_SOUNDBAR_MODE_PROGRAM_UP = 774;
    public static final int KEYCODE_SOUNDBAR_MODE_VOLUME_DOWN = 775;
    public static final int KEYCODE_SOUNDBAR_MODE_VOLUME_UP = 776;
    public static final int KEYCODE_SOUNDBAR_MODE_MENU = 777;
    public static final int KEYCODE_SOUNDBAR_MODE_ENTER = 778;
    public static final int KEYCODE_SOUNDBAR_MODE_BACK = 779;
    public static final int KEYCODE_STB = 780;

    //liujian20141223
    public static final int KEYCODE_AUDIO_SETTING = 803;
    public static final int KEYCODE_SUBTITLE_SETTING = 804;


    //yiye add end
    // private static final int LAST_KEYCODE           = KEYCODE_STB;
    private static final int LAST_KEYCODE = KEYCODE_SUBTITLE_SETTING;

    // NOTE: If you add a new keycode here you must also add it to:
    //  isSystem()
    //  frameworks/native/include/android/keycodes.h
    //  frameworks/native/include/input/KeycodeLabels.h
    //  external/webkit/WebKit/android/plugins/ANPKeyCodes.h ?
    //  frameworks/base/core/res/res/values/attrs.xml
    //  emulator?
    //  LAST_KEYCODE
    //  KEYCODE_SYMBOLIC_NAMES
    //
    //  Also Android currently does not reserve code ranges for vendor-
    //  specific key codes.  If you have new key codes to have, you
    //  MUST contribute a patch to the open source project to define
    //  those new codes.  This is intended to maintain a consistent
    //  set of key code definitions across all Android devices.

    // Symbolic names of all key codes.
    private static final SparseArray<String> KEYCODE_SYMBOLIC_NAMES = new SparseArray<String>();

    private static void populateKeycodeSymbolicNames() {
        SparseArray<String> names = KEYCODE_SYMBOLIC_NAMES;
        names.append(KEYCODE_UNKNOWN, "KEYCODE_UNKNOWN");
        names.append(KEYCODE_SOFT_LEFT, "KEYCODE_SOFT_LEFT");
        names.append(KEYCODE_SOFT_RIGHT, "KEYCODE_SOFT_RIGHT");
        names.append(KEYCODE_HOME, "KEYCODE_HOME");
        names.append(KEYCODE_BACK, "KEYCODE_BACK");
        names.append(KEYCODE_CALL, "KEYCODE_CALL");
        names.append(KEYCODE_ENDCALL, "KEYCODE_ENDCALL");
        names.append(KEYCODE_0, "KEYCODE_0");
        names.append(KEYCODE_1, "KEYCODE_1");
        names.append(KEYCODE_2, "KEYCODE_2");
        names.append(KEYCODE_3, "KEYCODE_3");
        names.append(KEYCODE_4, "KEYCODE_4");
        names.append(KEYCODE_5, "KEYCODE_5");
        names.append(KEYCODE_6, "KEYCODE_6");
        names.append(KEYCODE_7, "KEYCODE_7");
        names.append(KEYCODE_8, "KEYCODE_8");
        names.append(KEYCODE_9, "KEYCODE_9");
        names.append(KEYCODE_STAR, "KEYCODE_STAR");
        names.append(KEYCODE_POUND, "KEYCODE_POUND");
        names.append(KEYCODE_DPAD_UP, "KEYCODE_DPAD_UP");
        names.append(KEYCODE_DPAD_DOWN, "KEYCODE_DPAD_DOWN");
        names.append(KEYCODE_DPAD_LEFT, "KEYCODE_DPAD_LEFT");
        names.append(KEYCODE_DPAD_RIGHT, "KEYCODE_DPAD_RIGHT");
        names.append(KEYCODE_DPAD_CENTER, "KEYCODE_DPAD_CENTER");
        names.append(KEYCODE_VOLUME_UP, "KEYCODE_VOLUME_UP");
        names.append(KEYCODE_VOLUME_DOWN, "KEYCODE_VOLUME_DOWN");
        names.append(KEYCODE_POWER, "KEYCODE_POWER");
        names.append(KEYCODE_CAMERA, "KEYCODE_CAMERA");
        names.append(KEYCODE_CLEAR, "KEYCODE_CLEAR");
        names.append(KEYCODE_A, "KEYCODE_A");
        names.append(KEYCODE_B, "KEYCODE_B");
        names.append(KEYCODE_C, "KEYCODE_C");
        names.append(KEYCODE_D, "KEYCODE_D");
        names.append(KEYCODE_E, "KEYCODE_E");
        names.append(KEYCODE_F, "KEYCODE_F");
        names.append(KEYCODE_G, "KEYCODE_G");
        names.append(KEYCODE_H, "KEYCODE_H");
        names.append(KEYCODE_I, "KEYCODE_I");
        names.append(KEYCODE_J, "KEYCODE_J");
        names.append(KEYCODE_K, "KEYCODE_K");
        names.append(KEYCODE_L, "KEYCODE_L");
        names.append(KEYCODE_M, "KEYCODE_M");
        names.append(KEYCODE_N, "KEYCODE_N");
        names.append(KEYCODE_O, "KEYCODE_O");
        names.append(KEYCODE_P, "KEYCODE_P");
        names.append(KEYCODE_Q, "KEYCODE_Q");
        names.append(KEYCODE_R, "KEYCODE_R");
        names.append(KEYCODE_S, "KEYCODE_S");
        names.append(KEYCODE_T, "KEYCODE_T");
        names.append(KEYCODE_U, "KEYCODE_U");
        names.append(KEYCODE_V, "KEYCODE_V");
        names.append(KEYCODE_W, "KEYCODE_W");
        names.append(KEYCODE_X, "KEYCODE_X");
        names.append(KEYCODE_Y, "KEYCODE_Y");
        names.append(KEYCODE_Z, "KEYCODE_Z");
        names.append(KEYCODE_COMMA, "KEYCODE_COMMA");
        names.append(KEYCODE_PERIOD, "KEYCODE_PERIOD");
        names.append(KEYCODE_ALT_LEFT, "KEYCODE_ALT_LEFT");
        names.append(KEYCODE_ALT_RIGHT, "KEYCODE_ALT_RIGHT");
        names.append(KEYCODE_SHIFT_LEFT, "KEYCODE_SHIFT_LEFT");
        names.append(KEYCODE_SHIFT_RIGHT, "KEYCODE_SHIFT_RIGHT");
        names.append(KEYCODE_TAB, "KEYCODE_TAB");
        names.append(KEYCODE_SPACE, "KEYCODE_SPACE");
        names.append(KEYCODE_SYM, "KEYCODE_SYM");
        names.append(KEYCODE_EXPLORER, "KEYCODE_EXPLORER");
        names.append(KEYCODE_ENVELOPE, "KEYCODE_ENVELOPE");
        names.append(KEYCODE_ENTER, "KEYCODE_ENTER");
        names.append(KEYCODE_DEL, "KEYCODE_DEL");
        names.append(KEYCODE_GRAVE, "KEYCODE_GRAVE");
        names.append(KEYCODE_MINUS, "KEYCODE_MINUS");
        names.append(KEYCODE_EQUALS, "KEYCODE_EQUALS");
        names.append(KEYCODE_LEFT_BRACKET, "KEYCODE_LEFT_BRACKET");
        names.append(KEYCODE_RIGHT_BRACKET, "KEYCODE_RIGHT_BRACKET");
        names.append(KEYCODE_BACKSLASH, "KEYCODE_BACKSLASH");
        names.append(KEYCODE_SEMICOLON, "KEYCODE_SEMICOLON");
        names.append(KEYCODE_APOSTROPHE, "KEYCODE_APOSTROPHE");
        names.append(KEYCODE_SLASH, "KEYCODE_SLASH");
        names.append(KEYCODE_AT, "KEYCODE_AT");
        names.append(KEYCODE_NUM, "KEYCODE_NUM");
        names.append(KEYCODE_HEADSETHOOK, "KEYCODE_HEADSETHOOK");
        names.append(KEYCODE_FOCUS, "KEYCODE_FOCUS");
        names.append(KEYCODE_PLUS, "KEYCODE_PLUS");
        names.append(KEYCODE_MENU, "KEYCODE_MENU");
        names.append(KEYCODE_NOTIFICATION, "KEYCODE_NOTIFICATION");
        names.append(KEYCODE_SEARCH, "KEYCODE_SEARCH");
        names.append(KEYCODE_MEDIA_PLAY_PAUSE, "KEYCODE_MEDIA_PLAY_PAUSE");
        names.append(KEYCODE_MEDIA_STOP, "KEYCODE_MEDIA_STOP");
        names.append(KEYCODE_MEDIA_NEXT, "KEYCODE_MEDIA_NEXT");
        names.append(KEYCODE_MEDIA_PREVIOUS, "KEYCODE_MEDIA_PREVIOUS");
        names.append(KEYCODE_MEDIA_REWIND, "KEYCODE_MEDIA_REWIND");
        names.append(KEYCODE_MEDIA_FAST_FORWARD, "KEYCODE_MEDIA_FAST_FORWARD");
        names.append(KEYCODE_MUTE, "KEYCODE_MUTE");
        names.append(KEYCODE_PAGE_UP, "KEYCODE_PAGE_UP");
        names.append(KEYCODE_PAGE_DOWN, "KEYCODE_PAGE_DOWN");
        names.append(KEYCODE_PICTSYMBOLS, "KEYCODE_PICTSYMBOLS");
        names.append(KEYCODE_SWITCH_CHARSET, "KEYCODE_SWITCH_CHARSET");
        names.append(KEYCODE_BUTTON_A, "KEYCODE_BUTTON_A");
        names.append(KEYCODE_BUTTON_B, "KEYCODE_BUTTON_B");
        names.append(KEYCODE_BUTTON_C, "KEYCODE_BUTTON_C");
        names.append(KEYCODE_BUTTON_X, "KEYCODE_BUTTON_X");
        names.append(KEYCODE_BUTTON_Y, "KEYCODE_BUTTON_Y");
        names.append(KEYCODE_BUTTON_Z, "KEYCODE_BUTTON_Z");
        names.append(KEYCODE_BUTTON_L1, "KEYCODE_BUTTON_L1");
        names.append(KEYCODE_BUTTON_R1, "KEYCODE_BUTTON_R1");
        names.append(KEYCODE_BUTTON_L2, "KEYCODE_BUTTON_L2");
        names.append(KEYCODE_BUTTON_R2, "KEYCODE_BUTTON_R2");
        names.append(KEYCODE_BUTTON_THUMBL, "KEYCODE_BUTTON_THUMBL");
        names.append(KEYCODE_BUTTON_THUMBR, "KEYCODE_BUTTON_THUMBR");
        names.append(KEYCODE_BUTTON_START, "KEYCODE_BUTTON_START");
        names.append(KEYCODE_BUTTON_SELECT, "KEYCODE_BUTTON_SELECT");
        names.append(KEYCODE_BUTTON_MODE, "KEYCODE_BUTTON_MODE");
        names.append(KEYCODE_ESCAPE, "KEYCODE_ESCAPE");
        names.append(KEYCODE_FORWARD_DEL, "KEYCODE_FORWARD_DEL");
        names.append(KEYCODE_CTRL_LEFT, "KEYCODE_CTRL_LEFT");
        names.append(KEYCODE_CTRL_RIGHT, "KEYCODE_CTRL_RIGHT");
        names.append(KEYCODE_CAPS_LOCK, "KEYCODE_CAPS_LOCK");
        names.append(KEYCODE_SCROLL_LOCK, "KEYCODE_SCROLL_LOCK");
        names.append(KEYCODE_META_LEFT, "KEYCODE_META_LEFT");
        names.append(KEYCODE_META_RIGHT, "KEYCODE_META_RIGHT");
        names.append(KEYCODE_FUNCTION, "KEYCODE_FUNCTION");
        names.append(KEYCODE_SYSRQ, "KEYCODE_SYSRQ");
        names.append(KEYCODE_BREAK, "KEYCODE_BREAK");
        names.append(KEYCODE_MOVE_HOME, "KEYCODE_MOVE_HOME");
        names.append(KEYCODE_MOVE_END, "KEYCODE_MOVE_END");
        names.append(KEYCODE_INSERT, "KEYCODE_INSERT");
        names.append(KEYCODE_FORWARD, "KEYCODE_FORWARD");
        names.append(KEYCODE_MEDIA_PLAY, "KEYCODE_MEDIA_PLAY");
        names.append(KEYCODE_MEDIA_PAUSE, "KEYCODE_MEDIA_PAUSE");
        names.append(KEYCODE_MEDIA_CLOSE, "KEYCODE_MEDIA_CLOSE");
        names.append(KEYCODE_MEDIA_EJECT, "KEYCODE_MEDIA_EJECT");
        names.append(KEYCODE_MEDIA_RECORD, "KEYCODE_MEDIA_RECORD");
        names.append(KEYCODE_F1, "KEYCODE_F1");
        names.append(KEYCODE_F2, "KEYCODE_F2");
        names.append(KEYCODE_F3, "KEYCODE_F3");
        names.append(KEYCODE_F4, "KEYCODE_F4");
        names.append(KEYCODE_F5, "KEYCODE_F5");
        names.append(KEYCODE_F6, "KEYCODE_F6");
        names.append(KEYCODE_F7, "KEYCODE_F7");
        names.append(KEYCODE_F8, "KEYCODE_F8");
        names.append(KEYCODE_F9, "KEYCODE_F9");
        names.append(KEYCODE_F10, "KEYCODE_F10");
        names.append(KEYCODE_F11, "KEYCODE_F11");
        names.append(KEYCODE_F12, "KEYCODE_F12");
        names.append(KEYCODE_NUM_LOCK, "KEYCODE_NUM_LOCK");
        names.append(KEYCODE_NUMPAD_0, "KEYCODE_NUMPAD_0");
        names.append(KEYCODE_NUMPAD_1, "KEYCODE_NUMPAD_1");
        names.append(KEYCODE_NUMPAD_2, "KEYCODE_NUMPAD_2");
        names.append(KEYCODE_NUMPAD_3, "KEYCODE_NUMPAD_3");
        names.append(KEYCODE_NUMPAD_4, "KEYCODE_NUMPAD_4");
        names.append(KEYCODE_NUMPAD_5, "KEYCODE_NUMPAD_5");
        names.append(KEYCODE_NUMPAD_6, "KEYCODE_NUMPAD_6");
        names.append(KEYCODE_NUMPAD_7, "KEYCODE_NUMPAD_7");
        names.append(KEYCODE_NUMPAD_8, "KEYCODE_NUMPAD_8");
        names.append(KEYCODE_NUMPAD_9, "KEYCODE_NUMPAD_9");
        names.append(KEYCODE_NUMPAD_DIVIDE, "KEYCODE_NUMPAD_DIVIDE");
        names.append(KEYCODE_NUMPAD_MULTIPLY, "KEYCODE_NUMPAD_MULTIPLY");
        names.append(KEYCODE_NUMPAD_SUBTRACT, "KEYCODE_NUMPAD_SUBTRACT");
        names.append(KEYCODE_NUMPAD_ADD, "KEYCODE_NUMPAD_ADD");
        names.append(KEYCODE_NUMPAD_DOT, "KEYCODE_NUMPAD_DOT");
        names.append(KEYCODE_NUMPAD_COMMA, "KEYCODE_NUMPAD_COMMA");
        names.append(KEYCODE_NUMPAD_ENTER, "KEYCODE_NUMPAD_ENTER");
        names.append(KEYCODE_NUMPAD_EQUALS, "KEYCODE_NUMPAD_EQUALS");
        names.append(KEYCODE_NUMPAD_LEFT_PAREN, "KEYCODE_NUMPAD_LEFT_PAREN");
        names.append(KEYCODE_NUMPAD_RIGHT_PAREN, "KEYCODE_NUMPAD_RIGHT_PAREN");
        names.append(KEYCODE_VOLUME_MUTE, "KEYCODE_VOLUME_MUTE");
        names.append(KEYCODE_INFO, "KEYCODE_INFO");
        names.append(KEYCODE_CHANNEL_UP, "KEYCODE_CHANNEL_UP");
        names.append(KEYCODE_CHANNEL_DOWN, "KEYCODE_CHANNEL_DOWN");
        names.append(KEYCODE_ZOOM_IN, "KEYCODE_ZOOM_IN");
        names.append(KEYCODE_ZOOM_OUT, "KEYCODE_ZOOM_OUT");
        names.append(KEYCODE_TV, "KEYCODE_TV");
        names.append(KEYCODE_WINDOW, "KEYCODE_WINDOW");
        names.append(KEYCODE_GUIDE, "KEYCODE_GUIDE");
        names.append(KEYCODE_DVR, "KEYCODE_DVR");
        names.append(KEYCODE_BOOKMARK, "KEYCODE_BOOKMARK");
        names.append(KEYCODE_CAPTIONS, "KEYCODE_CAPTIONS");
        names.append(KEYCODE_SETTINGS, "KEYCODE_SETTINGS");
        names.append(KEYCODE_TV_POWER, "KEYCODE_TV_POWER");
        names.append(KEYCODE_TV_INPUT, "KEYCODE_TV_INPUT");
        names.append(KEYCODE_STB_INPUT, "KEYCODE_STB_INPUT");
        names.append(KEYCODE_STB_POWER, "KEYCODE_STB_POWER");
        names.append(KEYCODE_AVR_POWER, "KEYCODE_AVR_POWER");
        names.append(KEYCODE_AVR_INPUT, "KEYCODE_AVR_INPUT");
        names.append(KEYCODE_PROG_RED, "KEYCODE_PROG_RED");
        names.append(KEYCODE_PROG_GREEN, "KEYCODE_PROG_GREEN");
        names.append(KEYCODE_PROG_YELLOW, "KEYCODE_PROG_YELLOW");
        names.append(KEYCODE_PROG_BLUE, "KEYCODE_PROG_BLUE");
        names.append(KEYCODE_APP_SWITCH, "KEYCODE_APP_SWITCH");
        names.append(KEYCODE_BUTTON_1, "KEYCODE_BUTTON_1");
        names.append(KEYCODE_BUTTON_2, "KEYCODE_BUTTON_2");
        names.append(KEYCODE_BUTTON_3, "KEYCODE_BUTTON_3");
        names.append(KEYCODE_BUTTON_4, "KEYCODE_BUTTON_4");
        names.append(KEYCODE_BUTTON_5, "KEYCODE_BUTTON_5");
        names.append(KEYCODE_BUTTON_6, "KEYCODE_BUTTON_6");
        names.append(KEYCODE_BUTTON_7, "KEYCODE_BUTTON_7");
        names.append(KEYCODE_BUTTON_8, "KEYCODE_BUTTON_8");
        names.append(KEYCODE_BUTTON_9, "KEYCODE_BUTTON_9");
        names.append(KEYCODE_BUTTON_10, "KEYCODE_BUTTON_10");
        names.append(KEYCODE_BUTTON_11, "KEYCODE_BUTTON_11");
        names.append(KEYCODE_BUTTON_12, "KEYCODE_BUTTON_12");
        names.append(KEYCODE_BUTTON_13, "KEYCODE_BUTTON_13");
        names.append(KEYCODE_BUTTON_14, "KEYCODE_BUTTON_14");
        names.append(KEYCODE_BUTTON_15, "KEYCODE_BUTTON_15");
        names.append(KEYCODE_BUTTON_16, "KEYCODE_BUTTON_16");
        names.append(KEYCODE_LANGUAGE_SWITCH, "KEYCODE_LANGUAGE_SWITCH");
        names.append(KEYCODE_MANNER_MODE, "KEYCODE_MANNER_MODE");
        names.append(KEYCODE_3D_MODE, "KEYCODE_3D_MODE");
        names.append(KEYCODE_CONTACTS, "KEYCODE_CONTACTS");
        names.append(KEYCODE_CALENDAR, "KEYCODE_CALENDAR");
        names.append(KEYCODE_MUSIC, "KEYCODE_MUSIC");
        names.append(KEYCODE_CALCULATOR, "KEYCODE_CALCULATOR");
        names.append(KEYCODE_ZENKAKU_HANKAKU, "KEYCODE_ZENKAKU_HANKAKU");
        names.append(KEYCODE_EISU, "KEYCODE_EISU");
        names.append(KEYCODE_MUHENKAN, "KEYCODE_MUHENKAN");
        names.append(KEYCODE_HENKAN, "KEYCODE_HENKAN");
        names.append(KEYCODE_KATAKANA_HIRAGANA, "KEYCODE_KATAKANA_HIRAGANA");
        names.append(KEYCODE_YEN, "KEYCODE_YEN");
        names.append(KEYCODE_RO, "KEYCODE_RO");
        names.append(KEYCODE_KANA, "KEYCODE_KANA");
        names.append(KEYCODE_ASSIST, "KEYCODE_ASSIST");
        names.append(KEYCODE_BRIGHTNESS_DOWN, "KEYCODE_BRIGHTNESS_DOWN");
        names.append(KEYCODE_BRIGHTNESS_UP, "KEYCODE_BRIGHTNESS_UP");
        names.append(KEYCODE_MEDIA_AUDIO_TRACK, "KEYCODE_MEDIA_AUDIO_TRACK");
        //liujian20141223
        //names.append(KEYCODE_AUDIO_SETTING, "KEYCODE_AUDIO_SETTING");
        //names.append(KEYCODE_SUBTITLE_SETTING, "KEYCODE_SUBTITLE_SETTING");
        names.append(803, "KEYCODE_AUDIO_SETTING");
        names.append(804, "KEYCODE_SUBTITLE_SETTING");

        // MStar Android Patch Begin
        // Common section, range 251-300   naming rule: KEYCODE_XXX
        names.append(251, "KEYCODE_SOUND_MODE");
        names.append(252, "KEYCODE_PICTURE_MODE");
        names.append(253, "KEYCODE_ASPECT_RATIO");
        names.append(254, "KEYCODE_CHANNEL_RETURN");
        names.append(255, "KEYCODE_SLEEP");
        names.append(256, "KEYCODE_EPG");
        names.append(257, "KEYCODE_LIST");
        names.append(258, "KEYCODE_SUBTITLE");
        names.append(259, "KEYCODE_FAVORITE");
        names.append(260, "KEYCODE_MTS");
        names.append(261, "KEYCODE_FREEZE");
        names.append(262, "KEYCODE_TTX");
        names.append(263, "KEYCODE_CC");
        names.append(264, "KEYCODE_TV_SETTING");
        names.append(265, "KEYCODE_SCREENSHOT");
        names.append(266, "KEYCODE_CLOUD");
        names.append(267, "KEYCODE_VOICE");
        names.append(268, "KEYCODE_USB");
        names.append(269, "KEYCODE_HDMI");
        names.append(270, "KEYCODE_DISPLAY_MODE");
        names.append(271, "KEYCODE_SONG_SYSTEM");
        names.append(272, "KEYCODE_GINGA_BACK");
        names.append(273, "KEYCODE_NETFLIX");
        names.append(274, "KEYCODE_AMAZONE");
        names.append(780, "KEYCODE_STB");
        // Mstar section, range 301-400   naming rule: KEYCODE_MSTAR_XXX
        names.append(301, "KEYCODE_MSTAR_BALANCE");
        names.append(302, "KEYCODE_MSTAR_INDEX");
        names.append(303, "KEYCODE_MSTAR_HOLD");
        names.append(304, "KEYCODE_MSTAR_UPDATE");
        names.append(305, "KEYCODE_MSTAR_REVEAL");
        names.append(306, "KEYCODE_MSTAR_SUBCODE");
        names.append(307, "KEYCODE_MSTAR_SIZE");
        names.append(308, "KEYCODE_MSTAR_CLOCK");
        names.append(309, "KEYCODE_MSTAR_STORE_UP");
        names.append(310, "KEYCODE_MSTAR_TRIANGLE_UP");
        names.append(311, "KEYCODE_MSTAR_MOVIE");
        names.append(312, "KEYCODE_MSTAR_FILE");
        names.append(313, "KEYCODE_MSTAR_STAR_PLUS");
        names.append(314, "KEYCODE_MSTAR_AUDIO_TRACK");
        names.append(315, "KEYCODE_MSTAR_OPTIONAL_TIME");
        names.append(316, "KEYCODE_MSTAR_LOOP");
        names.append(317, "KEYCODE_MSTAR_INBOX");
        names.append(318, "KEYCODE_MSTAR_VVOIP");
        names.append(319, "KEYCODE_MSTAR_PVR_BROWSER");
        // Haier section, range 401-500   naming rule: KEYCODE_HAIER_XXX
        names.append(401, "KEYCODE_HAIER_TASK");
        names.append(402, "KEYCODE_HAIER_TOOLS");
        names.append(403, "KEYCODE_HAIER_POWERSLEEP");
        names.append(404, "KEYCODE_HAIER_WAKEUP");
        names.append(405, "KEYCODE_HAIER_UNMUTE");
        names.append(406, "KEYCODE_HAIER_CLEANSEARCH");
        // Konka section, range 501-600   naming rule: KEYCODE_KONKA_XXXX
        names.append(501, "KEYCODE_KONKA_YPBPR");
        names.append(502, "KEYCODE_KONKA_THREEPOINT_LOONPRESS");
        names.append(503, "KEYCODE_KONKA_THREEPOINT_COLLECT");
        names.append(504, "KEYCODE_KONKA_THREEPOINT_DISPERSE");
        names.append(505, "KEYCODE_KONKA_VOICESWITCH");
        names.append(506, "KEYCODE_KONKA_FLYIMEFINGER_SELECT");
        names.append(507, "KEYCODE_KONKA_FLYIMEFINGER_CANCEL");
        names.append(508, "KEYCODE_KONKA_SOUNDOUTPUT_ENABLE");
        names.append(509, "KEYCODE_KONKA_SOUNDOUTPUT_DISABLE");
        names.append(510, "KEYCODE_KONKA_BESTV_EXIT");
        names.append(511, "KEYCODE_KONKA_BESTV_FORWARD");
        names.append(512, "KEYCODE_KONKA_BESTV_BACKWARD");
        names.append(513, "KEYCODE_KONKA_ENTER_FACTORY");
        names.append(514, "KEYCODE_KONKA_FACTORY_BAKE_TV");
        // Skyworth section, range 601-700   naming rule: KEYCODE_SKYWORTH_XXX
        names.append(KEYCODE_COOCAA, "KEYCODE_COOCAA");
        names.append(KEYCODE_HOME_LONG, "KEYCODE_HOME_LONG");
        names.append(KEYCODE_BACK_LONG, "KEYCODE_BACK_LONG");
        names.append(KEYCODE_CENTER_LONG, "KEYCODE_CENTER_LONG");
        names.append(KEYCODE_NEW_SHUTTLE_LEFT, "KEYCODE_NEW_SHUTTLE_LEFT");
        names.append(KEYCODE_SEEK_SIGNAL, "KEYCODE_SEEK_SIGNAL");
        names.append(KEYCODE_NEW_SHUTTLE_RIGHT, "KEYCODE_NEW_SHUTTLE_RIGHT");
        names.append(KEYCODE_LOCAL_MEDIA, "KEYCODE_LOCAL_MEDIA");
        names.append(KEYCODE_RESEARCH, "KEYCODE_RESEARCH");
        names.append(KEYCODE_SETTING, "KEYCODE_SETTING");
        names.append(KEYCODE_MYSTEP, "KEYCODE_MYSTEP");
        names.append(KEYCODE_SOUNDBAR_MODE, "KEYCODE_SOUNDBAR_MODE");
        names.append(KEYCODE_TV_MODE, "KEYCODE_TV_MODE");
        // Tcl section, range 4001-4099   naming rule: KEYCODE_TCL_XXX
        names.append(4001, "KEYCODE_TCL_MITV");
        names.append(4002, "KEYCODE_TCL_USB_MENU");
        names.append(4003, "KEYCODE_TCL_SWING_R1");
        names.append(4004, "KEYCODE_TCL_SWING_R2");
        names.append(4005, "KEYCODE_TCL_SWING_R3");
        names.append(4006, "KEYCODE_TCL_SWING_R4");
        names.append(4007, "KEYCODE_TCL_SWING_L1");
        names.append(4008, "KEYCODE_TCL_SWING_L2");
        names.append(4009, "KEYCODE_TCL_SWING_L3");
        names.append(4010, "KEYCODE_TCL_SWING_L4");
        names.append(4011, "KEYCODE_TCL_WIDGET");
        names.append(4012, "KEYCODE_TCL_VGR_LEFT");
        names.append(4013, "KEYCODE_TCL_VGR_RIGHT");
        names.append(4014, "KEYCODE_TCL_VGR_TAP");
        names.append(4015, "KEYCODE_TCL_VGR_WAVE");
        names.append(4016, "KEYCODE_TCL_VGR_WAVE_LEFT");
        names.append(4017, "KEYCODE_TCL_VGR_WAVE_RIGHT");
        names.append(4018, "KEYCODE_TCL_VGR_ACTIVE");
        names.append(4019, "KEYCODE_TCL_VGR_DEACTIVE");
        names.append(4020, "KEYCODE_TCL_BODY_SENSOR");
        names.append(4021, "KEYCODE_TCL_CIRCLE_CLOCKWISE");
        names.append(4022, "KEYCODE_TCL_CIRCLE_CTR_CLOCKWISE");
        names.append(4023, "KEYCODE_TCL_GESTURE_X");
        names.append(4024, "KEYCODE_TCL_GESTURE_ALPHA");
        names.append(4025, "KEYCODE_TCL_GESTURE_MUTE");
        names.append(4026, "KEYCODE_TCL_UP");
        names.append(4027, "KEYCODE_TCL_DOWN");
        names.append(4028, "KEYCODE_TCL_LEFT");
        names.append(4029, "KEYCODE_TCL_RIGHT");
        names.append(4030, "KEYCODE_TCL_UP_LEFT");
        names.append(4031, "KEYCODE_TCL_UP_RIGHT");
        names.append(4032, "KEYCODE_TCL_DOWN_LEFT");
        names.append(4033, "KEYCODE_TCL_DOWN_RIGHT");
        // Changhong section, range 4101-4199   naming rule: KEYCODE_CHANGHONG_XXX
        names.append(4101, "KEYCODE_CHANGHONGIR_MUTE");
        names.append(4102, "KEYCODE_CHANGHONGIR_INPUT");
        names.append(4103, "KEYCODE_CHANGHONGIR_DEL");
        names.append(4104, "KEYCODE_CHANGHONGIR_MENU");
        names.append(4105, "KEYCODE_CHANGHONGIR_CORN");
        names.append(4106, "KEYCODE_CHANGHONGIR_OK");
        names.append(4107, "KEYCODE_CHANGHONGIR_FLCK_FU");
        names.append(4108, "KEYCODE_CHANGHONGIR_FLCK_FD");
        names.append(4109, "KEYCODE_CHANGHONGIR_FLCK_FL");
        names.append(4110, "KEYCODE_CHANGHONGIR_FLCK_FR");
        names.append(4111, "KEYCODE_CHANGHONGIR_FLCK_SU");
        names.append(4112, "KEYCODE_CHANGHONGIR_FLCK_SD");
        names.append(4113, "KEYCODE_CHANGHONGIR_FLCK_SL");
        names.append(4114, "KEYCODE_CHANGHONGIR_FLCK_SR");
        names.append(4115, "KEYCODE_CHANGHONGIR_PINCH");
        names.append(4116, "KEYCODE_CHANGHONGIR_SPREAD");
        names.append(4117, "KEYCODE_CHANGHONGIR_VOICE");
        names.append(4118, "KEYCODE_CHANGHONGIR_HAND");
        names.append(4119, "KEYCODE_CHANGHONGIR_3D");
        names.append(4120, "KEYCODE_CHANGHONGIR_HELP");
        names.append(4121, "KEYCODE_CHANGHONGIR_APP");
        names.append(4122, "KEYCODE_CHANGHONGIR_MOUSE");
        names.append(4123, "KEYCODE_CHANGHONGIR_EPG");
        names.append(4124, "KEYCODE_CHANGHONGIR_HOME");
        names.append(4125, "KEYCODE_CHANGHONGIR_SETTINGS");
        // Hisense section, range 4201-4299   naming rule: KEYCODE_HISENSE_XXX
        names.append(4201, "KEYCODE_HISENSE_G_SENSOR");
        names.append(4202, "KEYCODE_HISENSE_LOW_BATTERY");
        names.append(4203, "KEYCODE_HISENSE_SLIDEUP");
        names.append(4204, "KEYCODE_HISENSE_SLIDEDOWN");
        names.append(4205, "KEYCODE_HISENSE_SLIDELEFT");
        names.append(4206, "KEYCODE_HISENSE_SLIDERIGHT");
        names.append(4207, "KEYCODE_HISENSE_RAPID_SLIDEUP");
        names.append(4208, "KEYCODE_HISENSE_RAPID_SLIDEDOWN");
        names.append(4209, "KEYCODE_HISENSE_RAPID_SLIDELEFT");
        names.append(4210, "KEYCODE_HISENSE_RAPID_SLIDERIGHT");
        names.append(4211, "KEYCODE_HISENSE_FAC_NEC_M");
        names.append(4212, "KEYCODE_HISENSE_FAC_NEC_IP");
        names.append(4213, "KEYCODE_HISENSE_FAC_NEC_SAVE");
        names.append(4214, "KEYCODE_HISENSE_FAC_NEC_3D");
        names.append(4215, "KEYCODE_HISENSE_FAC_NEC_PC");
        names.append(4216, "KEYCODE_HISENSE_FAC_NEC_LOGO");
        names.append(4217, "KEYCODE_HISENSE_FAC_NEC_YPBPR");
        names.append(4218, "KEYCODE_HISENSE_FAC_NEC_HDMI");
        names.append(4219, "KEYCODE_HISENSE_FAC_NEC_F1");
        names.append(4220, "KEYCODE_HISENSE_FAC_NEC_F2");
        names.append(4221, "KEYCODE_HISENSE_FAC_NEC_F3");
        names.append(4222, "KEYCODE_HISENSE_FAC_NEC_F4");
        names.append(4223, "KEYCODE_HISENSE_FAC_NEC_F5");
        names.append(4224, "KEYCODE_HISENSE_FAC_NEC_F6");
        names.append(4225, "KEYCODE_HISENSE_FAC_NEC_F7");
        names.append(4226, "KEYCODE_HISENSE_FAC_NEC_OK");
        names.append(4227, "KEYCODE_HISENSE_FAC_NEC_MAC");
        names.append(4228, "KEYCODE_HISENSE_FAC_NEC_AV");
        names.append(4229, "KEYCODE_HISENSE_FAC_NEC_PATTERN");
        names.append(4230, "KEYCODE_HISENSE_FAC_NEC_AGING");
        names.append(4231, "KEYCODE_HISENSE_FAC_NEC_BALANCE");
        names.append(4232, "KEYCODE_HISENSE_FAC_NEC_ADC");
        names.append(4233, "KEYCODE_HISENSE_FAC_NEC_RDRV_INCREASE");
        names.append(4234, "KEYCODE_HISENSE_FAC_NEC_RDRV_DECREASE");
        names.append(4235, "KEYCODE_HISENSE_FAC_NEC_GDRV_INCREASE");
        names.append(4236, "KEYCODE_HISENSE_FAC_NEC_GDRV_DECREASE");
        names.append(4237, "KEYCODE_HISENSE_FAC_NEC_BDRV_INCREASE");
        names.append(4238, "KEYCODE_HISENSE_FAC_NEC_BDRV_DECREASE");
        names.append(4239, "KEYCODE_HISENSE_FAC_NEC_RCUT_INCREASE");
        names.append(4240, "KEYCODE_HISENSE_FAC_NEC_RCUT_DECREASE");
        names.append(4241, "KEYCODE_HISENSE_FAC_NEC_GCUT_INCREASE");
        names.append(4242, "KEYCODE_HISENSE_FAC_NEC_GCUT_DECREASE");
        names.append(4243, "KEYCODE_HISENSE_FAC_NEC_BCUT_INCREASE");
        names.append(4244, "KEYCODE_HISENSE_FAC_NEC_BCUT_DECREASE");
        names.append(4245, "KEYCODE_HISENSE_PRODUCT_SCAN_START");
        names.append(4246, "KEYCODE_HISENSE_PRODUCT_SCAN_OVER");
        names.append(4247, "KEYCODE_HISENSE_TEST_BROAD_TV");
        names.append(4248, "KEYCODE_HISENSE_TEST_BROAD_DTV");
        names.append(4249, "KEYCODE_HISENSE_TEST_BROAD_AV1");
        names.append(4250, "KEYCODE_HISENSE_TEST_BROAD_AV2");
        names.append(4251, "KEYCODE_HISENSE_TEST_BROAD_AV3");
        names.append(4252, "KEYCODE_HISENSE_TEST_BROAD_SVIDEO1");
        names.append(4253, "KEYCODE_HISENSE_TEST_BROAD_SVIDEO2");
        names.append(4254, "KEYCODE_HISENSE_TEST_BROAD_SVIDEO3");
        names.append(4255, "KEYCODE_HISENSE_TEST_BROAD_SCART1");
        names.append(4256, "KEYCODE_HISENSE_TEST_BROAD_SCART2");
        names.append(4257, "KEYCODE_HISENSE_TEST_BROAD_SCART3");
        names.append(4258, "KEYCODE_HISENSE_TEST_BOARD_YPBPR1");
        names.append(4259, "KEYCODE_HISENSE_TEST_BOARD_YPBPR2");
        names.append(4260, "KEYCODE_HISENSE_TEST_BOARD_YPBPR3");
        names.append(4261, "KEYCODE_HISENSE_TEST_BOARD_VGA");
        names.append(4262, "KEYCODE_HISENSE_TEST_BOARD_HDMI1");
        names.append(4263, "KEYCODE_HISENSE_TEST_BOARD_HDMI2");
        names.append(4264, "KEYCODE_HISENSE_TEST_BOARD_HDMI3");
        names.append(4265, "KEYCODE_HISENSE_TEST_BOARD_HDMI4");
        names.append(4266, "KEYCODE_HISENSE_TEST_BOARD_HDMI5");
        names.append(4267, "KEYCODE_HISENSE_TEST_BOARD_DMP");
        names.append(4268, "KEYCODE_HISENSE_TEST_BOARD_EMP");
        names.append(4269, "KEYCODE_HISENSE_TEST_BOARD_AUTOCOLOR");
        names.append(4270, "KEYCODE_HISENSE_TEST_BOARD_SAVE");
        names.append(4271, "KEYCODE_HISENSE_TEST_BOARD_TELITEXT");
        names.append(4272, "KEYCODE_HISENSE_TEST_BOARD_SAPL");
        names.append(4273, "KEYCODE_HISENSE_TEST_BOARD_VCHIP");
        names.append(4274, "KEYCODE_HISENSE_TEST_BOARD_CCD");
        names.append(4275, "KEYCODE_HISENSE_TEST_BOARD_BTSC");
        names.append(4276, "KEYCODE_HISENSE_TEST_BOARD_FAC_OK");
        // MStar Android Patch End
    };

}