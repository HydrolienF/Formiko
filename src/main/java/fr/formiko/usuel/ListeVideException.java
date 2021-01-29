package fr.formiko.usuel.exception;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5

public class ListeVideException extends Exception{
  public ListeVideException(String liste, String action,boolean b){
    if(liste != null && liste !=""){ liste = " "+liste;}
    erreur.erreur(g.get("erreur",13,"La liste")+liste+" "+g.get("erreur",14,"est vide")+" "+g.get("ListeVideException",1,"impossible de")+" "+action,"ListeVideException",b);
  }
  public ListeVideException(String liste, String action){ this(liste,action,false);}
  public ListeVideException(String action){ this("",action);}
}
