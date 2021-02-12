package fr.formiko.usuel.exception;

import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5

//TODO to update.
public class ElementListeIntrouvableException extends Exception{
  private String message;
  public ElementListeIntrouvableException(String élément, int id,boolean b){
    //erreur.erreur("aucune créature n'as pu être retiré","CCreature.retirer",true);
    message = g.get("ElementListeIntrouvableException",1,"La/Le")+" "+élément+" ("+id+") "+g.get("ElementListeIntrouvableException",2,"n'as pu être retiré");//,"ElementListeIntrouvableException",b);
  }
  public ElementListeIntrouvableException(String e, int id){ this(e,id,false);}
}
