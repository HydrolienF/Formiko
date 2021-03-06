package fr.formiko.usuel;

/**
*{@summary Color used by linux console.}<br>
*All String can be call by color.NAME.<br>
*To set back to normal color use color.NEUTRAL.<br>
*@author Hydrolien
*@version 1.39
*/
public class color {
  //colors
  public static String NEUTRAL = (char)27+"[0;m";

  public static String RED = (char)27+"[1;31m";
  public static String YELLOW = (char)27+"[1;33m";
  public static String GREEN = (char)27+"[1;32m";
  public static String WHITE = (char)27+"[1;37m";
  public static String BROWN = (char)27+"[0;m";
  public static String BLACK = (char)27+"[1;30m";
  public static String BLUE = (char)27+"[1;34m";
  public static String PURPLE = (char)27+"[1;35m";

  public static String UNDERLINE = (char)27+"[4;37m";

  public static String RED_BACKGROUND = (char)27+"[7;31m";
  public static String YELLOW_BACKGROUND = (char)27+"[7;33m";
  public static String GREEN_BACKGROUND = (char)27+"[7;32m";
  public static String WHITE_BACKGROUND = (char)27+"[7;37m";
  public static String BLACK_BACKGROUND = (char)27+"[7;30m";
  public static String BLUE_BACKGROUND = (char)27+"[7;34m";
  public static String PURPLE_BACKGROUND = (char)27+"[7;35m";

  public static String GREEN_FLASH = (char)27+"[5;32m";

  public static String NEUTRAL_CROSS_OUT = (char)27+"[9;3m";
}
