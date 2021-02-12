package fr.formiko.usuel.exceptions;

import fr.formiko.usuel.g;

//TODO to update.
public class ListItemNotFoundException extends RuntimeException {
  public ListItemNotFoundException(String élément, int id){
    //erreur.erreur("aucune créature n'as pu être retiré","CCreature.retirer",true);
    super(g.get("ListItemNotFoundException",1,"La/Le")+" "+élément+" ("+id+") "+g.get("ListItemNotFoundException",2,"n'as pu être retiré"));//,"ListItemNotFoundException",b);
  }
}
