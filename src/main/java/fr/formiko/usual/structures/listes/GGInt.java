package fr.formiko.usual.structures.listes;

import fr.formiko.usual.g;

import java.io.Serializable;

/**
*{@summary List of List of int.}<br>
*@author Hydrolien
*@lastEditedVersion 2.23
*/
public class GGInt extends Liste<GInt> implements Serializable {

  // CONSTRUCTORS ----------------------------------------------------------------

  // GET SET ----------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Do the sum of the itemNumber item.}<br>
  *@param itemNumber number of the item
  *@lastEditedVersion 2.23
  */
  public int sommeCase(int itemNumber){
    int total=0;
    for (GInt gi : this) {
      total+=gi.getCase(itemNumber);
    }
    if(total<-1){total=-1;}// -1 = at least 1 error.
    return total;
  }
}
