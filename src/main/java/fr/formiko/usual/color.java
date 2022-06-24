package fr.formiko.usual;

/**
*{@summary Color used by Linux Mac &#38; Windows console.}<br>
*All String can be call by color.NAME.<br>
*To set back to normal color use color.NEUTRAL.<br>
*@author Hydrolien
*@lastEditedVersion 2.11
*/
public class color {
  public static String NEUTRAL;

  public static String RED;
  public static String YELLOW;
  public static String GREEN;
  public static String WHITE;
  public static String BROWN;
  public static String BLACK;
  public static String BLUE;
  public static String PURPLE;

  public static String UNDERLINE;

  public static String RED_BACKGROUND;
  public static String YELLOW_BACKGROUND;
  public static String GREEN_BACKGROUND;
  public static String WHITE_BACKGROUND;
  public static String BLACK_BACKGROUND;
  public static String BLUE_BACKGROUND;
  public static String PURPLE_BACKGROUND;

  public static String GREEN_FLASH;

  public static String NEUTRAL_CROSS_OUT;

  /**
  *{@summary Initialize color depending of os.}<br>
  *@lastEditedVersion 2.11
  */
  public static void iniColor(){
    char beginChar;
    if(Os.getOs().isWindows()){
      beginChar='\033';
    }else{
      beginChar=(char)27;
    }
    NEUTRAL = beginChar+"[0;m";

    RED = beginChar+"[1;31m";
    YELLOW = beginChar+"[1;33m";
    GREEN = beginChar+"[1;32m";
    WHITE = beginChar+"[1;37m";
    BROWN = beginChar+"[1;m";
    BLACK = beginChar+"[1;30m";
    BLUE = beginChar+"[1;34m";
    PURPLE = beginChar+"[1;35m";

    UNDERLINE = beginChar+"[4;37m";

    RED_BACKGROUND = beginChar+"[7;31m";
    YELLOW_BACKGROUND = beginChar+"[7;33m";
    GREEN_BACKGROUND = beginChar+"[7;32m";
    WHITE_BACKGROUND = beginChar+"[7;37m";
    BLACK_BACKGROUND = beginChar+"[7;30m";
    BLUE_BACKGROUND = beginChar+"[7;34m";
    PURPLE_BACKGROUND = beginChar+"[7;35m";

    GREEN_FLASH = beginChar+"[5;32m";

    NEUTRAL_CROSS_OUT = beginChar+"[9;3m";
  }
}
