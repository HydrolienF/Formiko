package fr.formiko.usual.exceptions;

import fr.formiko.usual.g;

/**
*{@summary exceptions class for a list item not found.}<br>
*@author Hydrolien
*@lastEditedVersion 1.31
*/
public class ListItemNotFoundException extends RuntimeException {
  /**
  *{@summary Constructs a new runtime exception with a detail message.}<br>
  *@param name Name of the thing that haven't been found.
  *@param id Id of the thing that haven't been found.
  *@lastEditedVersion 1.31
  */
  public ListItemNotFoundException(String name, int id){
    //erreur.erreur("aucune créature n'as pu être retiré","CCreature.remove",true);
    super(g.get("ListItemNotFoundException",1,"La/Le")+" "+name+" ("+id+") "+g.get("ListItemNotFoundException",2,"n'as pu être retiré"));//,"ListItemNotFoundException",b);
  }
  /**
  *{@summary Constructs a new runtime exception with a detail message.}<br>
  *@param name Name of the thing that haven't been found.
  *@lastEditedVersion 1.31
  */
  public ListItemNotFoundException(String name){
    this(name,-1);
  }
}
