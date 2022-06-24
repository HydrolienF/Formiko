package fr.formiko.fusual;

import fr.formiko.usual.Time;

/**
*{@summary Time class with Formiko functions.}<br>
*For now it is used mostly for test.
*@author Hydrolien
*@lastEditedVersion 2.25
*/
public class FTime extends Time {
  /**
  *{@summary Main constructor.}<br>
  *@param timeFilePath path to the time file
  *@lastEditedVersion 2.25
  */
  public FTime(String timeFilePath, boolean toInitialize){
    super(timeFilePath, toInitialize);
  }
}
