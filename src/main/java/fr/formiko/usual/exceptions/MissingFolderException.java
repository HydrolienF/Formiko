package fr.formiko.usual.exceptions;

/**
*{@summary exceptions class for an unexpected class type.}<br>
*@author Hydrolien
*@lastEditedVersion 1.46
*/
public class MissingFolderException extends RuntimeException {
  /**
  *{@summary Constructs a new runtime exception with a detail message.}<br>
  *@param folderName Name of the missinf folder.
  *@lastEditedVersion 1.46
  */
  public MissingFolderException(String folderName){
    super("Can not create all file of "+folderName+" folder");
  }
}
