package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

public class ObjetAId implements Serializable{
  protected static int ic;
  protected final int id;

  // CONSTRUCTEUR ---------------------------------------------------------------
  //Principal
  public ObjetAId(){
    id = ic; ic++;
  }
  // GET SET --------------------------------------------------------------------
  public int getId(){return id;}
  public static int getIc(){return ic;}
  public static int getI(){return getIc();}
  public static void ini(){ic=1;}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary Standard equals function with id.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof ObjetAId)){return false;}
    ObjetAId oi = (ObjetAId)o;
    if(getId() == oi.getId()){ return true;}
    return false;
  }
}
