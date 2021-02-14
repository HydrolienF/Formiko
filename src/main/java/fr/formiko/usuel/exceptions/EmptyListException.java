package fr.formiko.usuel.exceptions;

import fr.formiko.usuel.g;

public class EmptyListException extends RuntimeException{
  public EmptyListException(String liste, String action){
    super(g.get("erreur",13,"La liste")+" "+liste+" "+g.get("erreur",14,"est vide")+" "+g.get("EmptyListException",1,"impossible de")+" "+action);
    //erreur.erreur(g.get("erreur",13,"La liste")+liste+" "+g.get("erreur",14,"est vide")+" "+g.get("EmptyListException",1,"impossible de")+" "+action,"EmptyListException",b);
  }
  public EmptyListException(String action){ this("unknow list",action);}
}
