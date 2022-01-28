package fr.formiko.usuel.exceptions;

import fr.formiko.usuel.g;
/**
*{@summary exceptions class for an unexpected empty list.}<br>
*@author Hydrolien
*@lastEditedVersion 1.39
*/
public class EmptyListException extends RuntimeException{
  /**
  *{@summary Constructs a new runtime exception with a detail message.}<br>
  *@param list Name of the list that is empty.
  *@param action Action that the program were doing.
  *@lastEditedVersion 1.39
  */
  public EmptyListException(String list, String action){
    super(g.get("erreur",13,"La liste")+" "+list+" "+g.get("erreur",14,"est vide")+" "+g.get("EmptyListException",1,"impossible de")+" "+action);
    //erreur.erreur(g.get("erreur",13,"La liste")+liste+" "+g.get("erreur",14,"est vide")+" "+g.get("EmptyListException",1,"impossible de")+" "+action,"EmptyListException",b);
  }
  /***
  *{@summary Constructs a new runtime exception with a detail message.}<br>
  *Liste name is unknow.
  *@param action Action that the program were doing.
  *@lastEditedVersion 1.39
  */
  public EmptyListException(String action){ this("unknow list",action);}
}
