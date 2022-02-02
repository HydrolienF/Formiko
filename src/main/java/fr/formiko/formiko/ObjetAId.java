package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
*{@summary Basic class for all Object with an id.}<br>
*@lastEditedVersion 2.18
*@author Hydrolien
*/
public class ObjetAId implements Serializable {
  protected static int ic;
  protected final int id;

  // CONSTRUCTORS --------------------------------------------------------------
  //Principal
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 1.x
  */
  public ObjetAId(){
    id = ic; ic++;
  }
  // GET SET -------------------------------------------------------------------
  public int getId(){return id;}
  public static int getIc(){return ic;}
  public static int getI(){return getIc();}
  public static void ini(){ic=1;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Standard equals function with id.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof ObjetAId)){return false;}
    ObjetAId oi = (ObjetAId)o;
    if(getId() == oi.getId()){ return true;}
    return false;
  }
  @Override
  public int hashCode(){return getId();}
  /**
  *{@summary Return a short string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toStringShort(){
    return toString(); // toString is small enoth.
  }
  /**
  *{@summary Return a string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  @Override
  public String toString(){
    return id+"";
  }
}
