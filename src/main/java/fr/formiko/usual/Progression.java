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
}
