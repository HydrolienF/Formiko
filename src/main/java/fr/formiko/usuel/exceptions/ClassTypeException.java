package fr.formiko.usuel.exceptions;

public class ClassTypeException extends RuntimeException {
  public ClassTypeException(String supposedClass, String realClass){
    super("Class sould be "+supposedClass+" but was "+realClass);
  }
}
