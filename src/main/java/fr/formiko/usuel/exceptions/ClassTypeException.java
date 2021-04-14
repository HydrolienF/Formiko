package fr.formiko.usuel.exceptions;

/**
*{@summary exceptions class for an unexpected class type.}<br>
*@author Hydrolien
*@version 1.39
*/
public class ClassTypeException extends RuntimeException {
  /**
  *{@summary Constructs a new runtime exception with a detail message.}<br>
  *@param supposedClass Name of the class that program should have.
  *@param realClass Name of the class that program have.
  *@version 1.39
  */
  public ClassTypeException(String supposedClass, String realClass){
    super("Class sould be "+supposedClass+" but was "+realClass);
  }
}
