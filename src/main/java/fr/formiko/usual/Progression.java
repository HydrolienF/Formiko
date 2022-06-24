package fr.formiko.usual;

/**
*{@summary Update a progression state.}
*@lastEditedVersion 2.25
*@author Hydrolien
*/
public interface Progression {
  /***
  *{@summary Update downloading message.}
  *@param message the message
  *@lastEditedVersion 2.7
  */
  default void setDownloadingMessage(String message){}
  /***
  *{@summary Update downloading %age.}
  *@param state the state as a %age
  *@lastEditedVersion 2.7
  */
  default void setDownloadingValue(int state){}
  /***
  *{@summary Initialize the game launcher.}
  *@lastEditedVersion 2.7
  */
  default void iniLauncher(){}
  /***
  *{@summary Close the game launcher.}
  *@lastEditedVersion 2.7
  */
  default void closeLauncher(){}
  /***
  *{@summary Hide or show buttonRetry of FFrameLauncher.}
  *@lastEditedVersion 2.7
  */
  default void setButtonRetryVisible(boolean visible){}
}
