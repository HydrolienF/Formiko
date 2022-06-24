package fr.formiko.usual.structures.listes;

import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.Liste;

import java.io.Serializable;

/**
*{@summary Integer List that can be used to compute score of an anthill.}
*@lastEditedVersion 2.23
*/
public class GInt extends Liste<Integer> implements Serializable {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Create an empty int list.}
  *@lastEditedVersion 2.23
  */
  public GInt(){
    super();
  }
  // GET SET ----------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return an element of this.}
  *@param ca id of the element
  *@return the ca element of the GInt, or 0 if not found.
  *@lastEditedVersion 2.23
  */
  public int getCase(int ca){
    if(ca<0){return -1;}
    try {
      return get(ca);
    }catch (Exception e) {
      return 0;
    }
  }
}
